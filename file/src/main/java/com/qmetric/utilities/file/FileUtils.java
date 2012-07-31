package com.qmetric.utilities.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
    public String textFrom(String vfsLocation)
    {
        return new String(bytesFrom(vfsLocation));
    }

    public String textFrom(FileObject fileObject)
    {
        try
        {
            return new String(bytesFrom(fileObject), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get bytes from apache vfs location.
     *
     * @param vfsLocation The VFS location containing the text
     * @return byte array containing contents of InputStream
     */
    public byte[] bytesFrom(String vfsLocation)
    {
        return bytesFrom(inputStreamFrom(vfsLocation));
    }

    /**
     * Get bytes from apache vfs location.
     *
     * @param inputStream InputStream to read from
     * @return byte array containing contents of InputStream
     */
    public byte[] bytesFrom(InputStream inputStream)
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

    public byte[] bytesFrom(final FileObject file)
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
    public InputStream inputStreamFrom(String vfsLocation)
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

    public InputStream inputStreamFrom(FileObject fileObject)
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

    public InputStream inputStreamFromString(final String value)
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
    public OutputStream outputStreamFrom(String vfsLocation)
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

    public OutputStream outputStreamFrom(FileObject file)
    {
        try
        {
            return file.getContent().getOutputStream();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeIOException(e);
        }
    }

    public FileSystemManager getFsManager()
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

    public FileContent getFileContent(String vfsLocation)
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

    public FileObject resolveFile(String vfsLocation)
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

    public FileObject resolveFile(URL url)
    {
        return resolveFile(url.toString());
    }

    public FileObject resolveFile(final FileObject parent, final String child)
    {
        try
        {
            return parent.resolveFile(child);
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
    }

    public FileObject createFolder(final String vfsLocation)
    {
        final FileObject fileObject = resolveFile(vfsLocation);
        try
        {
            fileObject.createFolder();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
        return fileObject;
    }

    public FileObject createFolder(final FileObject parent, final String subFolder)
    {
        final FileObject newFolder = resolveFile(parent, subFolder);
        try
        {
            newFolder.createFolder();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
        return newFolder;
    }

    public FileObject createFile(final String vfsLocation)
    {
        final FileObject fileObject = resolveFile(vfsLocation);
        try
        {
            fileObject.createFile();
            return fileObject;
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
    }

    public FileObject createFile(final FileObject rootPath, final String filename)
    {
        try
        {
            final FileObject fileObject = resolveFile(rootPath, filename);

            fileObject.createFile();

            return fileObject;
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
    }

    public FileObject[] find(final String filename, final FileObject folder)
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

    public FileObject findFile(final String filename, final FileObject folder)
    {
        final FileObject[] files = find(filename, folder);

        if (files.length == 1)
        {
            return files[0];
        }
        else
        {
            throw new RuntimeException(String.format("Failed to find [%s] in zip [%s], find returned [%s]", filename, folder.getName(), StringUtils.join(files)));
        }
    }

    public void delete(final FileObject file)
    {
        try
        {
            file.delete();
        }
        catch (FileSystemException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void closeQuietly(final FileObject... files)
    {
        if (files != null)
        {
            for (FileObject fileObject : files)
            {
                if (fileObject != null)
                {
                    try
                    {
                        fileObject.close();
                    }
                    catch (FileSystemException e)
                    {
                        // ignore
                    }
                }
            }
        }
    }
}
