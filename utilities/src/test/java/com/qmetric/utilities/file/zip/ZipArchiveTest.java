package com.qmetric.utilities.file.zip;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.Selectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.qmetric.utilities.file.FileUtils.bytesFrom;
import static com.qmetric.utilities.file.FileUtils.createFolder;
import static com.qmetric.utilities.file.FileUtils.inputStreamFrom;
import static com.qmetric.utilities.file.FileUtils.resolveFile;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ZipArchiveTest
{
    private static final FileObject BASE_FOLDER = createFolder("/tmp/ZipArchiveITest");

    private FileObject outputFile;

    @Before
    public void context() throws Exception
    {
        outputFile = BASE_FOLDER.resolveFile("actualFile.zip");
    }

    @Test
    public void shouldWriteZipFileContainingExpectedFile() throws Exception
    {
        final String expectedFilePath = "welcome/Welcome.xsl";

        final FileObject welcomeXsl = resolveFile("res:" + expectedFilePath);

        assertTrue(welcomeXsl.exists());

        new ZipArchive().zip(outputFile, new ZipFileEntry(welcomeXsl, expectedFilePath));

        final FileObject actual = resolveFile("zip:" + outputFile.getName().getPath()).resolveFile(expectedFilePath);

        assertFileExists(actual);

        assertThat(bytesFrom(actual), equalTo(bytesFrom(welcomeXsl)));
    }

    @Test
    public void shouldExtractZipToOutputFolder() throws Exception
    {
        final FileObject zipFile = resolveFile("res:zip/expected.zip");

        final FileObject zip = resolveFile("zip:" + zipFile.getName().getPath());

        final FileObject outputFolder = BASE_FOLDER;

        new ZipArchive().extract(zipFile, outputFolder);

        final FileObject[] files = outputFolder.findFiles(Selectors.SELECT_FILES);

        for (FileObject actual : files)
        {
            assertFileExists(actual);

            final String actualPath = actual.getName().getPathDecoded().replaceFirst(outputFolder.getName().getPath(), "");

            assertThat(bytesFrom(actual), equalTo(bytesFrom(zip.resolveFile(actualPath))));
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

