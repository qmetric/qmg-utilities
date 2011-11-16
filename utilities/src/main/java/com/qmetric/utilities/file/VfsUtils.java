package com.qmetric.utilities.file;

import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;

public class VfsUtils
{
    public FileSystemManager getFileSystemManager() throws FileSystemException
    {
        return VFS.getManager();
    }
}