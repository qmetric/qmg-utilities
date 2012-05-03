package com.qmetric.utilities.file;

import org.apache.commons.vfs.FileSelectInfo;

/**
 * Finds a particular file by name, ignoring case.
 * <p/>
 * Created: Jul 14, 2011, Author: Dom Farr
 */
public final class FileSelector implements org.apache.commons.vfs.FileSelector
{
    private final String filename;

    public FileSelector(final String filename)
    {
        this.filename = filename.toLowerCase();
    }

    @Override public boolean includeFile(final FileSelectInfo fileSelectInfo) throws Exception
    {
        return fileSelectInfo.getFile().getName().getBaseName().toLowerCase().equals(filename);
    }

    @Override public boolean traverseDescendents(final FileSelectInfo fileSelectInfo) throws Exception
    {
        return true;
    }
}
