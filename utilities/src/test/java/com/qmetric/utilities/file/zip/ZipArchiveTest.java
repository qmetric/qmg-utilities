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
    public void shouldWriteZipFileContainingAllFilesInRequestedEntries() throws Exception
    {
        final FileObject fileObject = resolveFile("res:document/template/welcome/Welcome.xsl");

        new ZipArchive().zip(outputFile, new ZipFileEntry(fileObject, "document/template/welcome"));

        final FileObject actual = resolveFile("zip:" + outputFile.getName().getPath()).resolveFile("document/template/welcome/Welcome.xsl");

        assertFileExists(actual);

        assertThat(bytesFrom(actual), equalTo(bytesFrom(fileObject)));
    }

    @Test
    public void shouldWriteZipFileContainingSingleFileFromSteam() throws Exception
    {
        final String expectedFilePath = "res:document/template/welcome/Welcome.xsl";

        new ZipArchive().zip(outputFile, new ZipFileEntry(inputStreamFrom(expectedFilePath), "document/template/welcome"));

        final FileObject actual = resolveFile("zip:" + outputFile.getName().getPath()).resolveFile("document/template/welcome/Welcome.xsl");

        assertFileExists(actual);

        assertThat(bytesFrom(actual), equalTo(bytesFrom(resolveFile(expectedFilePath))));
    }

    @Test
    public void shouldExtractZipToOutputFolder() throws Exception
    {
        FileObject zip = resolveFile("zip:" + resolveFile("res:zip/scheduleTemplate.zip").getName().getPath());

        final FileObject outputFolder = BASE_FOLDER;

        new ZipArchive().extract(zip, outputFolder);

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

