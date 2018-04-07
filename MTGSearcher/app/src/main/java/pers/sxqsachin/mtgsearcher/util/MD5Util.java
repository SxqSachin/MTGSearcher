package pers.sxqsachin.mtgsearcher.util;

import java.security.MessageDigest;

/**
 *
 * MD5Tools
 *
 * Created by songxinqi-sachin on 16-6-2.
 */
public class MD5Util {
    private final static char   HEX[]   =
            {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    public static String    string2MD5(String text) {

        try {
            byte[] btInput = text.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();

            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = HEX[byte0 >>> 4 & 0xf];
                str[k++] = HEX[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
