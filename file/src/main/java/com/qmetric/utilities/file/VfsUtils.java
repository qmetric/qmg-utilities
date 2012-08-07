package com.qmetric.utilities.file;

import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;

public class VfsUtils
{
    public FileSystemManager getFileSystemManager()
    {
        try
        {
            return VFS.getManager();
        }
        catch (final FileSystemException e)
        {
            throw new RuntimeIOException(e);
        }
    }
}
