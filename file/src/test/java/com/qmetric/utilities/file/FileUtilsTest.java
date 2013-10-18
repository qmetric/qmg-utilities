package com.qmetric.utilities.file;

import com.qmetric.utilities.io.IOUtils;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileUtilsTest
{
    private static final String EXPECTED_FILE_CONTENTS = "some test text";

    private static final String FILE_PATH = "filePath/";

    private OutputStream outputStream = mock(OutputStream.class);

    private final FileObject fileObject1 = mock(FileObject.class);

    private final FileObject fileObject2 = mock(FileObject.class);

    private final IOUtils ioUtils = mock(IOUtils.class);

    private final FileSystemManager fileSystemManager = mock(FileSystemManager.class);

    private FileUtils fileUtils = new FileUtils(fileSystemManager, ioUtils);

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

    @Test
    public void shouldCopySourceFileContentsToOutputStream() throws Exception
    {
        final FileContent fileContent = mock(FileContent.class);
        final InputStream inputStream = mock(InputStream.class);
        when(fileObject1.getContent()).thenReturn(fileContent);
        when(fileContent.getInputStream()).thenReturn(inputStream);
        when(fileSystemManager.resolveFile(FILE_PATH)).thenReturn(fileObject1);

        fileUtils.copyFileToStream(outputStream, FILE_PATH);

        verify(fileSystemManager).resolveFile(FILE_PATH);
        verify(ioUtils).copy(inputStream, outputStream);
    }

    @Test
    public void shouldCloseSourceFileToEnsureResourcesAreClearedUp() throws Exception
    {
        final FileContent fileContent = mock(FileContent.class);
        final InputStream inputStream = mock(InputStream.class);
        when(fileObject1.getContent()).thenReturn(fileContent);
        when(fileContent.getInputStream()).thenReturn(inputStream);
        when(fileSystemManager.resolveFile(FILE_PATH)).thenReturn(fileObject1);

        fileUtils.copyFileToStream(outputStream, FILE_PATH);

        verify(fileObject1).close();
    }

    @Test
    public void shouldCreateFileObjectInstanceFromFileInstance() throws Exception
    {
        final File sourceFile = mock(File.class);

        fileUtils.toFileObject(sourceFile);

        verify(fileSystemManager).toFileObject(eq(sourceFile));
    }
}
