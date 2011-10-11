package com.qmetric.utilities.file;

import org.apache.commons.vfs.FileObject;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by IntelliJ IDEA. User: dfarr Date: Jul 28, 2010 Time: 11:21:01 AM To change this template use File | Settings | File Templates.
 */
public class FileUtilsTest
{
    private static final String EXPECTED_FILE_CONTENTS = "some test text";

    private FileObject fileObject1 = mock(FileObject.class);
    private FileObject fileObject2 = mock(FileObject.class);

    private FileUtils fileUtils;

    @Before
    public void context()
    {
        fileUtils = new FileUtils();
    }

    @Test
    public void shouldReadTextFromVfsLocation()
    {
        assertThat(fileUtils.textFrom("res:test-file.txt"), equalTo(EXPECTED_FILE_CONTENTS));
    }

    @Test
    public void closeQuietly() throws Exception
    {
        fileUtils.closeQuietly(fileObject1, fileObject2);

        verify(fileObject1).close();
        verify(fileObject2).close();
    }

    @Test
    public void closeQuietlyShouldHandleNullFileObject()
    {
        fileUtils.closeQuietly(null);
    }
}