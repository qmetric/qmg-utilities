package com.qmetric.utilities.file.zip;

import com.google.common.base.Optional;
import com.qmetric.utilities.file.RuntimeIOException;
import com.qmetric.utilities.io.IOUtils;
import com.qmetric.utilities.s3.BucketService;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.Validate;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Creates or extracts zip files.
 * <p/>
 * Created: Jul 12, 2011, Author: Dom Farr
 */
public class ZipArchive
{
    private final IOUtils ioUtils;

    public ZipArchive(final IOUtils ioUtils)
    {
        this.ioUtils = ioUtils;
    }

    public void zip(final FileObject outputFile, final Collection<ZipFileEntry> entries)
    {
        zip(outputFile, entries.toArray(new ZipFileEntry[entries.size()]));
    }

    public void zip(final FileObject outputFile, final ZipFileEntry... zipEntries)
    {
        final ZipArchiveOutputStream zip = createZipOutputStream(outputFile);

        try
        {
            for (ZipFileEntry entryZip : zipEntries)
            {
                addZipEntry(zip, entryZip.getZipEntryPath(), entryZip.getInputStream());
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            close(zip);
        }
    }

    public void extract(final ZipInputStream zipInputStream, final FileObject outputFolder)
    {
        Validate.notNull(zipInputStream, "ZipInputStream cannot be null");
        Validate.notNull(outputFolder, "Output folder cannot be null");

        isTypeFolder(outputFolder);

        try
        {
            ZipEntry currentZipEntry = zipInputStream.getNextEntry();
            while (currentZipEntry != null)
            {
                final FileObject currentFile = outputFolder.resolveFile(currentZipEntry.getName());

                if (currentZipEntry.isDirectory())
                {
                    currentFile.createFolder();
                }
                else
                {
                    writeToOutput(zipInputStream, currentFile);
                }

                currentZipEntry = zipInputStream.getNextEntry();
            }
        }
        catch (IOException e)
        {
            throw new RuntimeIOException(e);
        }
        finally
        {
            ioUtils.closeQuietly(zipInputStream);
        }
    }

    private void writeToOutput(final ZipInputStream zipInputStream, final FileObject currentFile) throws IOException
    {
        currentFile.createFile();

        final BufferedOutputStream outputStream = new BufferedOutputStream(currentFile.getContent().getOutputStream());
        try
        {
            byte[] buffer = new byte[100];
            int read;
            while ((read = zipInputStream.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, read);
            }
        }
        finally
        {
            ioUtils.closeQuietly(outputStream);
        }
    }

    private void addZipEntry(final ZipArchiveOutputStream zip, final String zipEntryPath, final InputStream inputStream) throws IOException
    {
        zip.putArchiveEntry(new ZipArchiveEntry(zipEntryPath));

        ioUtils.copy(inputStream, zip);

        zip.closeArchiveEntry();
    }

    private ZipArchiveOutputStream createZipOutputStream(final FileObject outputZip)
    {
        try
        {
            return new ZipArchiveOutputStream(outputZip.getContent().getOutputStream());
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void close(final ZipArchiveOutputStream outputStream)
    {
        ioUtils.closeQuietly(outputStream);
    }

    private void isTypeFolder(final FileObject outputFolder)
    {
        try
        {
            Validate.isTrue(FileType.FOLDER.equals(outputFolder.getType()) || FileType.IMAGINARY.equals(outputFolder.getType()),
                            String.format("Output folder must be of type folder [%s]", outputFolder.getType()));
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
    }
}

