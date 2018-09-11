/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.dgcheshang.cheji.netty.certificate;


public final class HexBin
{

    public HexBin()
    {
    }

    public static String encode(byte abyte0[])
    {
        if(abyte0 == null)
            return null;
        int i = abyte0.length;
        int j = i * 2;
        char ac[] = new char[j];
        for(int l = 0; l < i; l++)
        {
            int k = abyte0[l];
            if(k < 0)
                k += 256;
            ac[l * 2] = lookUpHexAlphabet[k >> 4];
            ac[l * 2 + 1] = lookUpHexAlphabet[k & 15];
        }
        return new String(ac);
    }

    public static byte[] decode(String s)
    {
        if(s == null)
            return null;
        int i = s.length();
        if(i % 2 != 0)
            return null;
        char ac[] = s.toCharArray();
        int j = i / 2;
        byte abyte0[] = new byte[j];
        for(int k = 0; k < j; k++)
        {
            byte byte0 = hexNumberTable[ac[k * 2]];
            if(byte0 == -1)
                return null;
            byte byte1 = hexNumberTable[ac[k * 2 + 1]];
            if(byte1 == -1)
                return null;
            abyte0[k] = (byte)(byte0 << 4 | byte1);
        }

        return abyte0;
    }

    private static final int BASELENGTH = 255;
    private static final int LOOKUPLENGTH = 16;
    private static final byte hexNumberTable[];
    private static final char lookUpHexAlphabet[];

    static 
    {
        hexNumberTable = new byte[255];
        lookUpHexAlphabet = new char[16];
        for(int i = 0; i < 255; i++)
            hexNumberTable[i] = -1;

        for(int j = 57; j >= 48; j--)
            hexNumberTable[j] = (byte)(j - 48);

        for(int k = 70; k >= 65; k--)
            hexNumberTable[k] = (byte)((k - 65) + 10);

        for(int l = 102; l >= 97; l--)
            hexNumberTable[l] = (byte)((l - 97) + 10);

        for(int i1 = 0; i1 < 10; i1++)
            lookUpHexAlphabet[i1] = (char)(48 + i1);

        for(int j1 = 10; j1 <= 15; j1++)
            lookUpHexAlphabet[j1] = (char)((65 + j1) - 10);

    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: E:\myeclipseworkspace8\TcpConsole_hex\WebRoot\WEB-INF\lib\xerces-2.6.2.jar
	Total time: 64 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/