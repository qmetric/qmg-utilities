package com.qmetric.utilities.file.zip;

import com.google.common.base.Optional;
import com.qmetric.utilities.file.FileUtils;
import com.qmetric.utilities.file.RuntimeIOException;
import com.qmetric.utilities.s3.BucketService;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.provider.zip.ZipFileObject;
import org.apache.commons.vfs.provider.zip.ZipFileSystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.zip.ZipInputStream;

/**
 * Creates or extracts zip files.
 * <p/>
 * Created: Jul 12, 2011, Author: Dom Farr
 */
public class ZipArchive
{
    private BucketService bucketService;

    public ZipArchive(final BucketService bucketService)
    {
        this.bucketService = bucketService;
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

    public void extract(final String bucketKey, final FileObject outputFolder)
    {
        Validate.notNull(bucketKey, "Bucket key cannot be null");
        Validate.notNull(outputFolder, "Output folder cannot be null");

        isTypeFolder(outputFolder);

        final Optional<InputStream> inputStream = bucketService.retrieveAsInputStream(bucketKey);

        if (!inputStream.isPresent()) {
            throw new RuntimeIOException("No file found: " + bucketKey);
        }

        final ZipInputStream zipInputStream = new ZipInputStream(inputStream.get());


    }

    //    public void extract(final FileObject zipFilePath, final FileObject outputFolder)
    //    {
    //        Validate.notNull(zipFilePath, "Zip file cannot be null");
    //        Validate.notNull(outputFolder, "Output folder cannot be null");
    //
    //        isTypeFolder(outputFolder);
    //
    //        final ZipFileObject zip = resolveZipFile(zipFilePath);
    //
    //        try
    //        {
    //            final FileObject[] files = zip.findFiles(Selectors.SELECT_FILES);
    //
    //            for (final FileObject file : files)
    //            {
    //                final FileObject destination = outputFolder.resolveFile(dropRootFromPathIfPresent(file));
    //                destination.copyFrom(file, Selectors.SELECT_SELF);
    //            }
    //        }
    //        catch (FileSystemException e)
    //        {
    //            throw new RuntimeException(e);
    //        }
    //        finally
    //        {
    //            close(zip);
    //        }
    //    }

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

    private void close(final ZipFileObject zip)
    {
        try
        {
            fileUtils.closeQuietly(zip.getFileSystem().getParentLayer());
            fileUtils.closeQuietly(zip);
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void close(final ZipArchiveOutputStream outputStream)
    {
        IOUtils.closeQuietly(outputStream);
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

    private ZipFileObject resolveZipFile(final FileObject zipFilePath)
    {
        return (ZipFileObject) fileUtils.resolveFile("zip:" + zipFilePath);
    }
}

