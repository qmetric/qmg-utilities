// Copyright (c) 2011, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// simple wrapper to defeat "static cling" and make testing simpler.
public class FileUtils
{
    public FileInputStream openInputStream(final File file) throws IOException
    {
        return org.apache.commons.io.FileUtils.openInputStream(file);
    }

    public FileOutputStream openOutputStream(final File file) throws IOException
    {
        return org.apache.commons.io.FileUtils.openOutputStream(file);
    }
}