package com.qmetric.utilities.uuid;

/**
 * This class provides methods for encoding and decoding UUIDs in a format that is similar to MIME base64. The difference is that we are not
 * using "+" and "/" beside the letters and numbers, but "-" and "_". This is required to avoid problems in the URL string. In contrast to
 * the specification, this class doesn't insert line breaks. This makes the class suitable for usage in URL parameter encoding or number
 * encoding for storing in database tables.
 * <p/>
 * The input to the <code>encode</code> methods is always a byte array. Strictly speaking the output represents a sequence of characters,
 * but since these characters are from a subset of both the Unicode and ASCII character repertoires, it is possible to express the output
 * either as a String or as a byte array.
 * @deprecated this class is no longer needed. to get uuid without dashes use use com.qmetric.utilities.uuid.StrippedUUID
 */
@Deprecated
public final class UUIDCodec
{
    /**
     * A static array that maps 6-bit integers to a specific char.
     */
    private static final char[] ENC_TABLE = {
            //   0   1   2   3   4   5   6   7
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', // 0
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', // 1
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', // 2
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', // 3
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', // 4
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', // 5
            'w', 'x', 'y', 'z', '0', '1', '2', '3', // 6
            '4', '5', '6', '7', '8', '9', '-', '_'  // 7
    };

    /**
     * A static array that maps ASCII code points to a 6-bit integer, or -1 for an invalid code point.
     */
    private static final byte[] DEC_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                             -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1,
                                             52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8,
                                             9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26,
                                             27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
                                             51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                             -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                             -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                             -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                             -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                                             -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,};

    // Private constructor for "all static methods" class.
    private UUIDCodec()
    {

    }

    /**
     * Encodes <i>data</i> as a String using base64 like encoding.
     *
     * @param data The bytes that have to be encoded.
     *
     * @return the encoded string.
     */
    public static String encode(final byte[] data)
    {
        return new String(encodeAsByteArray(data));
    }

    /**
     * Encodes <i>data</i> as a byte array using base64 like encoding. The characters 'A'-'Z', 'a'-'z', '0'-'9', '#', '_', and '=' in the
     * output are mapped to their ASCII code points. Line breaks in the output are represented as CR LF (codes 13 and 10).
     *
     * @param data The bytes that have to be encoded.
     *
     * @return the encoded byte array.
     */
    public static byte[] encodeAsByteArray(final byte[] data)
    {
        int i = 0, j = 0;
        int len = data.length;
        int delta = len % 3;
        int outlen = ((len + 2) / 3) * 4 + (len == 0 ? 2 : 0);
        byte[] output = new byte[outlen];
        byte a, b, c;

        for (int count = len / 3; count > 0; count--)
        {
            a = data[i++];
            b = data[i++];
            c = data[i++];
            output[j++] = (byte) (ENC_TABLE[(a >>> 2) & 0x3F]);
            output[j++] = (byte) (ENC_TABLE[((a << 4) & 0x30) + ((b >>> 4) & 0x0F)]);
            output[j++] = (byte) (ENC_TABLE[((b << 2) & 0x3C) + ((c >>> 6) & 0x03)]);
            output[j++] = (byte) (ENC_TABLE[c & 0x3F]);
        }

        if (delta == 1)
        {
            a = data[i];
            output[j++] = (byte) (ENC_TABLE[(a >>> 2) & 0x3F]);
            output[j++] = (byte) (ENC_TABLE[((a << 4) & 0x30)]);
            output[j++] = (byte) '=';
            output[j++] = (byte) '=';
        }
        else if (delta == 2)
        {
            a = data[i++];
            b = data[i];
            output[j++] = (byte) (ENC_TABLE[(a >>> 2) & 0x3F]);
            output[j++] = (byte) (ENC_TABLE[((a << 4) & 0x30) + ((b >>> 4) & 0x0F)]);
            output[j++] = (byte) (ENC_TABLE[((b << 2) & 0x3C)]);
            output[j++] = (byte) '=';
        }

        if (j != outlen)
        {
            throw new InternalError("Bug in UUIDCodec.java: incorrect length calculated for base64 output");
        }

        return output;
    }

    /**
     * Decodes a UUID-encoded String. Characters with ASCII code points <= 32 (this includes whitespace and newlines) are ignored.
     *
     * @param message The bytes that have to be decoded.
     *
     * @return the decoded data.
     *
     * @throws IllegalArgumentException if data contains invalid characters, i.e. not codes 0-32, 'A'-'Z', 'a'-'z', '#', '_'. or '=', or is
     * incorrectly padded.
     */
    public static byte[] decode(final String message) throws IllegalArgumentException
    {
        return decode(message.getBytes());
    }

    /**
     * Decodes a byte array containing UUID-encoded ASCII. Characters with ASCII code points <= 32 (this includes whitespace and newlines)
     * are ignored.
     *
     * @param data The bytes that have to be decoded.
     *
     * @return the decoded data.
     *
     * @throws IllegalArgumentException if data contains invalid characters, i.e. not codes 0-32, 'A'-'Z', 'a'-'z', '#', '_'. or '=', or is
     * incorrectly padded.
     */
    public static byte[] decode(final byte[] data)
    {
        int padCount = 0;
        int i, len = data.length;
        int real_len = 0;

        for (i = len - 1; i >= 0; --i)
        {
            if (data[i] > ' ')
            {
                real_len++;
            }

            if (data[i] == 0x3D)// ASCII '='
            {
                padCount++;
            }
        }

        if (real_len % 4 != 0)
        {
            throw new IllegalArgumentException("Length not a multiple of 4");
        }

        int ret_len = (real_len / 4) * 3 - padCount;
        byte[] ret = new byte[ret_len];

        i = 0;
        byte[] t = new byte[4];
        int output_index = 0;
        int j = 0;
        t[0] = t[1] = t[2] = t[3] = 0x3D;// ASCII '='

        while (i < len)
        {
            byte c = data[i++];
            if (c > ' ')
            {
                t[j++] = c;
            }

            if (j == 4)
            {
                output_index += decode(ret, output_index, t[0], t[1], t[2], t[3]);
                j = 0;
                t[0] = t[1] = t[2] = t[3] = 0x3D;// ASCII '='
            }
        }

        if (j > 0)
        {
            decode(ret, output_index, t[0], t[1], t[2], t[3]);
        }

        return ret;
    }

    /**
     * Given a block of 4 encoded bytes <code>{ a, b, c, d }</code>, this method decodes up to 3 bytes of output, and stores them starting
     * at <code>ret[ret_offset]</code>.
     *
     * @param ret output buffer
     * @param ret_off buffer offset
     * @param a encoded byte #1
     * @param b encoded byte #2
     * @param c encoded byte #3
     * @param d encoded byte #4
     *
     * @return the number of bytes converted.
     *
     * @throws IllegalArgumentException if a, b, c or d contain invalid characters, or are incorrectly padded.
     */
    private static int decode(final byte[] ret, int ret_off, final byte a, final byte b, final byte c, final byte d)
    {
        byte da = DEC_TABLE[a];
        byte db = DEC_TABLE[b];
        byte dc = DEC_TABLE[c];
        byte dd = DEC_TABLE[d];

        if (da == -1 || db == -1 || (dc == -1 && c != 0x3D) || (dd == -1 && d != 0x3D))
        {
            throw new IllegalArgumentException(
                    "invalid char [" + (a & 0xFF) + "," + (b & 0xFF) + "," + (c & 0xFF) + "," + (d & 0xFF) + "]");
        }

        ret[ret_off++] = (byte) (da << 2 | db >>> 4);

        if (c == 0x3D)// ASCII '='
        {
            return 1;
        }

        ret[ret_off++] = (byte) (db << 4 | dc >>> 2);

        if (d == 0x3D)// ASCII '='
        {
            return 2;
        }

        ret[ret_off] = (byte) (dc << 6 | dd);

        return 3;
    }

    public static int getIndexOfChar(final char c)
    {
        for (int i=0;i<ENC_TABLE.length;i++)
        {
            if (ENC_TABLE[i]==c)
            {
                return i;
            }
        }

        return -1;
    }
}