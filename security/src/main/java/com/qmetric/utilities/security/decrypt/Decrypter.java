package com.qmetric.utilities.security.decrypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Decrypter
{

    private final StandardPBEStringEncryptor decrypter;

    public Decrypter(final String secret)
    {
        this.decrypter = new StandardPBEStringEncryptor();
        decrypter.setAlgorithm("PBEWithMD5AndDES");
        decrypter.setPassword(secret);
    }

    public final String decrypt(final String data)
    {
        return decrypter.decrypt(data);
    }
}
