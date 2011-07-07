package com.qmetric.utilities.file;

import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils
{
    public static final int BUFFER_SIZE = 4096;

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
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        try
        {
            copy(inputStream, out);
            return out.toByteArray();
        }
        catch (IOException e)
        {
            throw new RuntimeIOException(e);
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

    private static FileSystemManager getFsManager()
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

    private static int copy(InputStream in, OutputStream out) throws IOException
    {
        try
        {
            int byteCount = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                throw new RuntimeIOException(e);
            }
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                throw new RuntimeIOException(e);
            }
        }
    }
}
