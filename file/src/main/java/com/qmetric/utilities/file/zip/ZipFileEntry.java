package com.qmetric.utilities.file.zip;

import org.apache.commons.lang.Validate;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;

import java.io.InputStream;

/**
 * Encapsulates either an existent file or an input stream into a ZipEntry.
 * <p/>
 * Created: Jul 13, 2011, Author: Dom Farr
 */
public final class ZipFileEntry
{
    private final InputStream inputStream;

    private final String zipEntryPath;

    public ZipFileEntry(final FileObject fileObject, final String zipEntryPath)
    {
        try
        {
            Validate.isTrue(fileObject.getType().equals(FileType.FILE));
            inputStream = fileObject.getContent().getInputStream();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }

        this.zipEntryPath = zipEntryPath;
    }

    public ZipFileEntry(final InputStream inputStream, final String zipEntryPath)
    {
        Validate.notNull(inputStream, "Cannot have null input stream.");

        this.inputStream = inputStream;

        this.zipEntryPath = zipEntryPath;
    }

    public String getZipEntryPath()
    {
        return zipEntryPath;
    }

    public InputStream getInputStream()
    {
        return inputStream;
    }
}

