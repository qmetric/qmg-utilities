package com.qmetric.utilities.file;

import org.apache.commons.vfs.FileObject;
import org.junit.Test;

import static com.qmetric.utilities.file.FileUtils.textFrom;
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

    private FileObject fileObject = mock(FileObject.class);

    @Test
    public void shouldReadTextFromVfsLocation()
    {
        assertThat(textFrom("res:test-file.txt"), equalTo(EXPECTED_FILE_CONTENTS));
    }

    @Test
    public void closeQuietly() throws Exception
    {
        FileUtils.closeQuietly(fileObject);

        verify(fileObject).close();
    }

    @Test
    public void closeQuietlyShouldHandleNullFileObject()
    {
        FileUtils.closeQuietly(null);
    }
}