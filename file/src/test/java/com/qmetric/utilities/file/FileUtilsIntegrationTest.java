package com.qmetric.utilities.file;

import com.qmetric.utilities.io.IOUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FileUtilsIntegrationTest
{
    private static final String EXPECTED_FILE_CONTENTS = "some test text";

    private static final String FILE_PATH = "filePath/";

    private OutputStream outputStream = mock(OutputStream.class);

    private final FileObject fileObject1 = mock(FileObject.class);

    private final FileObject fileObject2 = mock(FileObject.class);

    private final IOUtils ioUtils = mock(IOUtils.class);

    private FileUtils fileUtils;

    @Before
    public void context() throws FileSystemException
    {
        // use the real file system
        final FileSystemManager fileSystemManager = VFS.getManager();
        fileUtils = new FileUtils(fileSystemManager, ioUtils);
    }

    @Test
    public void shouldReadTextFromVfsLocation()
    {
        assertThat(fileUtils.textFrom("res:test-file.txt"), equalTo(EXPECTED_FILE_CONTENTS));
    }
}
