package com.qmetric.utilities.file.zip;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.Selectors;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Creates or extracts zip files.
 * <p/>
 * Created: Jul 12, 2011, Author: Dom Farr
 */
public final class ZipArchive
{
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

    public void extract(final FileObject zip, final FileObject outputFolder)
    {
        Validate.notNull(zip, "Zip file cannot be null");
        Validate.notNull(outputFolder, "Output folder cannot be null");
        Validate.isTrue(zip.getName().toString().contains("zip!"), String.format("Zip file must be a zip [%s]", zip.getName()));
        isTypeFolder(outputFolder);

        try
        {
            final FileObject[] files = zip.findFiles(Selectors.SELECT_FILES);

            for (FileObject file : files)
            {
                FileObject destination = outputFolder.resolveFile(dropRootFromPathIfPresent(file));
                destination.copyFrom(file, Selectors.SELECT_SELF);
            }
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            close(outputFolder);
            close(zip);
        }
    }

    private void addZipEntry(final ZipArchiveOutputStream zip, final String zipEntryPath, final InputStream inputStream) throws IOException
    {
        zip.putArchiveEntry(new ZipArchiveEntry(zipEntryPath));

        IOUtils.copy(inputStream, zip);

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

    private String dropRootFromPathIfPresent(final FileObject file) throws FileSystemException
    {
        if (file.getName().getPathDecoded().startsWith(File.separator))
        {
            return file.getName().getPathDecoded().replaceFirst(File.separator, "");
        }

        return file.getName().getPathDecoded();
    }

    private void close(final FileObject fileObject)
    {
        try
        {
            fileObject.close();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void close(final ZipArchiveOutputStream outputStream)
    {
        try
        {
            outputStream.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
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

