package com.qmetric.utilities.uuid;

import java.security.SecureRandom;

/**
 * Provide method to generate UUID modelled after java.lang.UUID.randomUUID().
 */
public final class UUID
{
    /*
     * The random number generator used by this class to create random based UUIDs.
     */
    private static final SecureRandom NUMBER_GENERATOR = new SecureRandom();

    /*
     * Number of random bytes per UUID - must be dividable by 3.
     *
     * 15 bytes will make 20 chars in the UUID
     * 18 bytes will make 24 chars in the UUID
     */
    private static final int RANDOM_BYTES_PER_UUID = 18;

    /**
     * Default constructor.
     */
    private UUID()
    {
    }

    /**
     * Static factory to generate string representation of pseudo random UUID.
     *
     * @return a randomly generated UUID string.
     */
    public static String createUUID()
    {
        return UUIDCodec.encode(randomBytes());
    }

    /*
     * generate random bytes
     */
    protected static byte[] randomBytes()
    {
        final byte[] randomBytes = new byte[RANDOM_BYTES_PER_UUID];
        NUMBER_GENERATOR.nextBytes(randomBytes);
        return randomBytes;
    }
}