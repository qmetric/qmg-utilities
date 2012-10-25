package com.qmetric.utilities.file.zip;

import com.qmetric.utilities.file.FileUtils;
import com.qmetric.utilities.io.IOUtils;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.VFS;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ZipArchiveTest
{
    private static FileObject BASE_FOLDER;

    private FileObject outputFile;

    private FileUtils fileUtils;

    private IOUtils ioUtils = new IOUtils();

    @Before
    public void context() throws Exception
    {
        // use the real file system
        final FileSystemManager fileSystemManager = VFS.getManager();
        fileUtils = new FileUtils(fileSystemManager, ioUtils);

        BASE_FOLDER = fileUtils.createFolder("/tmp/ZipArchiveITest");

        outputFile = BASE_FOLDER.resolveFile("actualFile.zip");
    }

    @Test
    public void shouldWriteZipFileContainingExpectedFile() throws Exception
    {
        final String expectedFilePath = "welcome/Welcome.xsl";

        final FileObject welcomeXsl = fileUtils.resolveFile("res:" + expectedFilePath);

        assertTrue(welcomeXsl.exists());

        new ZipArchive().zip(outputFile, new ZipFileEntry(welcomeXsl, expectedFilePath));

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

        new ZipArchive().extract(zipFile, outputFolder);

        final FileObject[] files = outputFolder.findFiles(Selectors.SELECT_FILES);

        for (FileObject actual : files)
        {
            assertFileExists(actual);

            final String actualPath = actual.getName().getPathDecoded().replaceFirst(outputFolder.getName().getPath(), "");

            assertThat(fileUtils.bytesFrom(actual), equalTo(fileUtils.bytesFrom(zip.resolveFile(actualPath))));
        }
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

