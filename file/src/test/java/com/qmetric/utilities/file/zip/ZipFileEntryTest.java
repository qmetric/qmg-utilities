package com.qmetric.utilities.file.zip;

import com.qmetric.utilities.file.FileUtils;
import com.qmetric.utilities.io.IOUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class ZipFileEntryTest
{
    private FileUtils fileUtils;

    private IOUtils ioUtils = new IOUtils();

    @Before
    public void context() throws FileSystemException
    {
        // use the real file system
        final FileSystemManager fileSystemManager = VFS.getManager();
        fileUtils = new FileUtils(fileSystemManager, ioUtils);
    }

    @Test
    public void shouldSuccessfullyCreateZipFileEntry() throws Exception
    {
        final FileObject fileObject = fileUtils.resolveFile("res:zip/expected.zip");

        final ZipFileEntry zipFileEntry = new ZipFileEntry(fileObject, "zip/expected.zip");

        assertThat(zipFileEntry.getZipEntryPath(), equalTo("zip/expected.zip"));
    }

    @Test
    public void shouldFailWhenFileDoesNotExist() throws Exception
    {
        final FileObject fileObject = fileUtils.resolveFile("/83475eqklrhlhwdr/sfhjwer98djbfbkfg.zkalhsdfhkjip");

        try
        {
            new ZipFileEntry(fileObject, "zip");
            fail("Failed because an exception was expected due to non-existent file");
        }
        catch (Exception e)
        {
            //expected
        }
    }

    @Test
    public void shouldFailWhenInputStreamIsNull()
    {
        try
        {
            final InputStream nullInputStream = null;

            new ZipFileEntry(nullInputStream, "zip");

            fail("Failed because an exception was expected due null input stream");
        }
        catch (Exception e)
        {
            //expected
        }
    }

    @Test
    public void shouldFailIfFileObjectNotAFile()
    {
        try
        {
            new ZipFileEntry(fileUtils.resolveFile("res:zip"), "zip");

            fail("Failed because an exception was expected because fileObject must be of type FILE. ");
        }
        catch (Exception e)
        {
            //expected
        }
    }
}
