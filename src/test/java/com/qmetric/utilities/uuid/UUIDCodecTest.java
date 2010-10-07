package com.qmetric.utilities.uuid;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * UUID unit test cases.
 */
public class UUIDCodecTest
{
    @Test
    public void encode()
    {
        assertEquals("AAAAAAAAAAAAAAAAAAAA", UUIDCodec.encode(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                                                       (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                                                       (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}));

        assertEquals("____________________", UUIDCodec.encode(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                                                                       (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                                                                       (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}));

        assertEquals("ABCD", UUIDCodec.encode(new byte[] {(byte) 0x00, (byte) 0x10, (byte) 0x83}));
        assertEquals("EFGH", UUIDCodec.encode(new byte[] {(byte) 0x10, (byte) 0x51, (byte) 0x87}));
        assertEquals("IJKL", UUIDCodec.encode(new byte[] {(byte) 0x20, (byte) 0x92, (byte) 0x8b}));
        assertEquals("MNOP", UUIDCodec.encode(new byte[] {(byte) 0x30, (byte) 0xd3, (byte) 0x8f}));
        assertEquals("QRST", UUIDCodec.encode(new byte[] {(byte) 0x41, (byte) 0x14, (byte) 0x93}));
        assertEquals("UVWX", UUIDCodec.encode(new byte[] {(byte) 0x51, (byte) 0x55, (byte) 0x97}));
        assertEquals("YZab", UUIDCodec.encode(new byte[] {(byte) 0x61, (byte) 0x96, (byte) 0x9b}));
        assertEquals("cdef", UUIDCodec.encode(new byte[] {(byte) 0x71, (byte) 0xd7, (byte) 0x9f}));
        assertEquals("ghij", UUIDCodec.encode(new byte[] {(byte) 0x82, (byte) 0x18, (byte) 0xa3}));
        assertEquals("klmn", UUIDCodec.encode(new byte[] {(byte) 0x92, (byte) 0x59, (byte) 0xa7}));
        assertEquals("opqr", UUIDCodec.encode(new byte[] {(byte) 0xa2, (byte) 0x9a, (byte) 0xab}));
        assertEquals("stuv", UUIDCodec.encode(new byte[] {(byte) 0xb2, (byte) 0xdb, (byte) 0xaf}));
        assertEquals("wxyz", UUIDCodec.encode(new byte[] {(byte) 0xc3, (byte) 0x1c, (byte) 0xb3}));
        assertEquals("0123", UUIDCodec.encode(new byte[] {(byte) 0xd3, (byte) 0x5d, (byte) 0xb7}));
        assertEquals("4567", UUIDCodec.encode(new byte[] {(byte) 0xe3, (byte) 0x9e, (byte) 0xbb}));
        assertEquals("89-_", UUIDCodec.encode(new byte[] {(byte) 0xf3, (byte) 0xdf, (byte) 0xbf}));
    }

    @Test
    public void createUUID()
    {
        assertFalse("aaaaaaaaaaaaaaaaaaaa".equals(UUID.createUUID())); // highly unlikely ....
    }
}