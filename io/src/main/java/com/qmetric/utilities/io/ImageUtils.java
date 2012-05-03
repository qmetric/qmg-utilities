// Copyright (c) 2011, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.io;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import java.io.IOException;

// simple wrapper to defeat "static cling" and make testing simpler.
public class ImageUtils
{
    public Image getInstance(final byte[] bytes) throws IOException, BadElementException
    {
        return Image.getInstance(bytes);
    }
}