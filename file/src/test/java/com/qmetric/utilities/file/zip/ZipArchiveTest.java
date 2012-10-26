package com.qmetric.utilities.file.zip;

import com.google.common.base.Optional;
import com.qmetric.utilities.file.FileUtils;
import com.qmetric.utilities.io.IOUtils;
import com.qmetric.utilities.s3.BucketService;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.VFS;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

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

    private IOUtils ioUtils = new IOUtils();

    private ZipArchive zipArchive;

    public static final String[] EXPECTED_ENTRIES =
            new String[] {"common/insurer/qgl1/blackandwhite/img_logo.png", "common/insurer/qgl1/large/img_logo.png", "common/insurer/qgl1/medium/img_logo.png",
                          "common/insurer/qgl1/small/img_logo.png", "common/insurer/qgl1/x-small/img_logo.png", "schedule/assets/bottom-of-page.png", "schedule/Schedule.xsl",
                          "schedule/SchedulePreProcessing.xsl", "schedule/SchedulePropertySets.xsl", "schedule/SVGIcons.xsl"};

    @Before
    public void context() throws Exception
    {
        // use the real file system
        final FileSystemManager fileSystemManager = VFS.getManager();
        fileUtils = new FileUtils(fileSystemManager, ioUtils);

        BASE_FOLDER = fileUtils.createFolder("/tmp/ZipArchiveITest");

        outputFile = BASE_FOLDER.resolveFile("actualFile.zip");

        zipArchive = new ZipArchive(ioUtils);
    }

    @Test
    public void shouldWriteZipFileContainingExpectedFile() throws Exception
    {
        final String expectedFilePath = "welcome/Welcome.xsl";

        final FileObject welcomeXsl = fileUtils.resolveFile("res:" + expectedFilePath);

        assertTrue(welcomeXsl.exists());

        zipArchive.zip(outputFile, new ZipFileEntry(welcomeXsl, expectedFilePath));

        final FileObject actual = fileUtils.resolveFile("zip:" + outputFile.getName().getPath()).resolveFile(expectedFilePath);

        assertFileExists(actual);

        assertThat(fileUtils.bytesFrom(actual), equalTo(fileUtils.bytesFrom(welcomeXsl)));
    }

    @Test
    public void shouldExtractZipToOutputFolderWithCorrectStructure() throws IOException
    {
        final FileObject outputFolder = BASE_FOLDER;

        final FileObject zipFile = fileUtils.resolveFile("res:zip/testExtraction.zip");

        zipArchive.extract(new ZipInputStream(zipFile.getContent().getInputStream()), outputFolder);

        for (final String entry : EXPECTED_ENTRIES)
        {
            assertFileExists(outputFolder.resolveFile(entry));
        }
    }

    @Test
    public void shouldExtractZipToOutputFolderWithCorrectContents() throws FileSystemException
    {
        final FileObject outputFolder = BASE_FOLDER;

        final FileObject zipFile = fileUtils.resolveFile("res:zip/testExtraction.zip");

        zipArchive.extract(new ZipInputStream(zipFile.getContent().getInputStream()), outputFolder);

        for (final String entry : EXPECTED_ENTRIES)
        {
            final FileObject extractedFile = outputFolder.resolveFile(entry);

            assertThat(fileUtils.bytesFrom(extractedFile), equalTo(fileUtils.bytesFrom("res:extract/expected/" + entry)));
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

