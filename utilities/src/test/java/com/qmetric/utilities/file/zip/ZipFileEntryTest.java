package com.qmetric.utilities.file.zip;

import org.apache.commons.vfs.FileObject;
import org.junit.Test;

import java.io.InputStream;

import static com.qmetric.utilities.file.FileUtils.resolveFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created: Jul 13, 2011, Author: Dom Farr
 */

public class ZipFileEntryTest
{
    @Test
    public void shouldSuccessfullyCreateZipFileEntry() throws Exception
    {
        final FileObject fileObject = resolveFile("res:zip/expected.zip");

        final ZipFileEntry zipFileEntry = new ZipFileEntry(fileObject, "zip/expected.zip");

        assertThat(zipFileEntry.getZipEntryPath(), equalTo("zip/expected.zip"));
    }

    @Test
    public void shouldFailWhenFileDoesNotExist() throws Exception
    {
        final FileObject fileObject = resolveFile("/83475eqklrhlhwdr/sfhjwer98djbfbkfg.zkalhsdfhkjip");

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
            new ZipFileEntry(resolveFile("res:zip"), "zip");

            fail("Failed because an exception was expected because fileObject must be of type FILE. ");
        }
        catch (Exception e)
        {
            //expected
        }
    }
}
