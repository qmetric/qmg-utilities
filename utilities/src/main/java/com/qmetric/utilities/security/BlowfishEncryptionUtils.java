// Copyright (c) 2011, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.security;

import org.apache.commons.lang.StringUtils;

// simple wrapper to defeat "static cling" and make testing simpler.
public class BlowfishEncryptionUtils
{
    private final BCrypt bCrypt = new BCrypt();

    public String hashPassword(final String rawPassword, final int salt)
    {
        return bCrypt.hashpw(rawPassword, bCrypt.gensalt(salt));
    }

    public boolean checkPassword(final String rawPassword, final String encryptedPassword)
    {
        return !StringUtils.isBlank(encryptedPassword) && bCrypt.checkpw(rawPassword, encryptedPassword);
    }
}