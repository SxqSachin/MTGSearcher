package pers.sxqsachin.mtgsearcher.util;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * StringUtil
 *
 * Created by songxinqi-sachin on 16-7-6.
 */
public class StringUtil {

    public static String toUrl(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return URLEncoder.encode(string);
    }

    @NonNull
    public static String link(String... strings) {
        return linkWithConnector("", strings);
    }

    @NonNull
    public static String linkNewLine(String... strings) {
        return linkWithConnector("\n", strings);
    }

    @NonNull
    public static String linkWithSpace(String... strings) {
        return linkWithConnector(" ", strings);
    }

    @NonNull
    public static String linkWithConnector(String connector, String... strings) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            sb.append(str);
            if (!sb.toString().isEmpty()) {
                if (i != strings.length-1) {
                    if (!strings[i+1].isEmpty()) {
                        sb.append(connector);
                    }
                }
            }
        }

        return sb.toString();
    }

    @NonNull
    public static String createSpace(int iv) {
        if (iv <= 0) {
            return "";
        }
        if (iv == 1) {
            return " ";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iv; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
