package com.qmetric.utilities.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static org.apache.commons.io.IOUtils.toByteArray;

public class FileUtils
{
    private static FileSystemManager fileSystemManager;

    /**
     * Get text from a apache vfs location. example, from a resource location res:myfile.xml
     *
     * @param vfsLocation The VFS location containing the text
     * @return A String containing the text
     */
    public static String textFrom(String vfsLocation)
    {
        return new String(bytesFrom(vfsLocation));
    }

    /**
     * Get bytes from apache vfs location.
     *
     * @param vfsLocation The VFS location containing the text
     * @return byte array containing contents of InputStream
     */
    public static byte[] bytesFrom(String vfsLocation)
    {
        return bytesFrom(inputStreamFrom(vfsLocation));
    }

    /**
     * Get bytes from apache vfs location.
     *
     * @param inputStream InputStream to read from
     * @return byte array containing contents of InputStream
     */
    public static byte[] bytesFrom(InputStream inputStream)
    {
        try
        {
            return toByteArray(inputStream);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static byte[] bytesFrom(final FileObject file)
    {
        try
        {
            return toByteArray(file.getContent().getInputStream());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get bytes from apache vfs location.
     *
     * @param vfsLocation VFS location to read from
     * @return InputStream for VFS location
     */
    public static InputStream inputStreamFrom(String vfsLocation)
    {
        try
        {
            return getFileContent(vfsLocation).getInputStream();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeIOException(e);
        }
    }

    public static InputStream inputStreamFrom(FileObject fileObject)
    {
        try
        {
            return fileObject.getContent().getInputStream();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeIOException(e);
        }
    }

    public static InputStream inputStreamFromString(final String value)
    {
        try
        {
            return IOUtils.toInputStream(value, "UTF-8");
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to convert string to input stream", e);
        }
    }

    /**
     * Get bytes from apache vfs location.
     *
     * @param vfsLocation VFS location to read from
     * @return InputStream for VFS location
     */
    public static OutputStream outputStreamFrom(String vfsLocation)
    {
        try
        {
            return getFileContent(vfsLocation).getOutputStream();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeIOException(e);
        }
    }

    public static FileSystemManager getFsManager()
    {
        try
        {
            if (fileSystemManager == null)
            {
                fileSystemManager = VFS.getManager();
            }
        }
        catch (FileSystemException e)
        {
            throw new RuntimeIOException(e);
        }

        return fileSystemManager;
    }

    public static FileContent getFileContent(String vfsLocation)
    {
        try
        {
            return resolveFile(vfsLocation).getContent();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeIOException(e);
        }
    }

    public static FileObject resolveFile(String vfsLocation)
    {
        try
        {
            return getFsManager().resolveFile(vfsLocation);
        }
        catch (FileSystemException e)
        {
            throw new RuntimeIOException(e);
        }
    }

    public static FileObject resolveFile(URL url)
    {
        return resolveFile(url.toString());
    }

    public static FileObject createFolder(final String folderPath)
    {
        try
        {
            final FileObject fileObject = FileUtils.resolveFile(folderPath);
            fileObject.createFolder();
            return fileObject;
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static FileObject[] find(final String filename, final FileObject folder)
    {
        final FileObject[] files;

        try
        {
            files = folder.findFiles(new FileSelector(filename));
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }

        return files;
    }

    private static int copy(InputStream in, OutputStream out) throws IOException
    {
        return copy(in, out);
    }
}
