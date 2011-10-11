package com.qmetric.utilities.file.zip;

import com.qmetric.utilities.file.FileUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystem;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.provider.zip.ZipFileObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZipArchiveTest
{
    private static FileObject BASE_FOLDER;

    private FileObject outputFile;

    private FileUtils fileUtils;

    @Before
    public void context() throws Exception
    {
        fileUtils = new FileUtils();

        BASE_FOLDER = fileUtils.createFolder("/tmp/ZipArchiveITest");

        outputFile = BASE_FOLDER.resolveFile("actualFile.zip");
    }

    @Test
    public void shouldWriteZipFileContainingExpectedFile() throws Exception
    {
        final String expectedFilePath = "welcome/Welcome.xsl";

        final FileObject welcomeXsl = fileUtils.resolveFile("res:" + expectedFilePath);

        assertTrue(welcomeXsl.exists());

        new ZipArchive(new FileUtils()).zip(outputFile, new ZipFileEntry(welcomeXsl, expectedFilePath));

        final FileObject actual = fileUtils.resolveFile("zip:" + outputFile.getName().getPath()).resolveFile(expectedFilePath);

        assertFileExists(actual);

        assertThat(fileUtils.bytesFrom(actual), equalTo(fileUtils.bytesFrom(welcomeXsl)));
    }

    @Test
    public void shouldExtractZipToOutputFolder() throws Exception
    {
        final FileObject zipFile = fileUtils.resolveFile("res:zip/expected.zip");

        final FileObject zip = fileUtils.resolveFile("zip:" + zipFile.getName().getPath());

        final FileObject outputFolder = BASE_FOLDER;

        new ZipArchive(new FileUtils()).extract(zipFile, outputFolder);

        final FileObject[] files = outputFolder.findFiles(Selectors.SELECT_FILES);

        for (FileObject actual : files)
        {
            assertFileExists(actual);

            final String actualPath = actual.getName().getPathDecoded().replaceFirst(outputFolder.getName().getPath(), "");

            assertThat(fileUtils.bytesFrom(actual), equalTo(fileUtils.bytesFrom(zip.resolveFile(actualPath))));
        }
    }

    @Test
    public void shouldCloseFileObjectAfterExtract() throws Exception
    {
        final FileObject parentLayer = mock(FileObject.class);
        final FileSystem fileSystem = mock(FileSystem.class);
        when(fileSystem.getParentLayer()).thenReturn(parentLayer);
        
        final FileObject zipFileObject = mock(ZipFileObject.class);
        when(zipFileObject.getFileSystem()).thenReturn(fileSystem);
        when(zipFileObject.findFiles(Mockito.<org.apache.commons.vfs.FileSelector>any())).thenReturn(new FileObject[] {});

        final FileUtils mockFileUtils = mock(FileUtils.class);

        when(mockFileUtils.resolveFile(Mockito.anyString())).thenReturn(zipFileObject);

        new ZipArchive(mockFileUtils).extract(zipFileObject, BASE_FOLDER);

        Mockito.verify(mockFileUtils).closeQuietly(zipFileObject);
        Mockito.verify(mockFileUtils).closeQuietly(parentLayer);
    }

    private void assertFileExists(final FileObject actual) throws FileSystemException
    {
        assertTrue(String.format("File [%s] was not found", actual), actual.exists());
    }

    @After
    public void cleanUpAfterEachTest() throws Exception
    {
        outputFile.close();
        BASE_FOLDER.delete(Selectors.SELECT_ALL);
    }
}

