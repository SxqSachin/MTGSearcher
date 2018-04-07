package pers.sxqsachin.mtgsearcher.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * HtmlReader
 *
 * Created by songxinqi-sachin on 16-7-8.
 */
public class HtmlReader {
    public static String read(String src) {
        URL url;
        String temp;
        final StringBuilder sb = new StringBuilder();
        try
        {
            url = new URL(src);
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));// 读取网页全部内容
            while ((temp = in.readLine()) != null)
            {
                sb.append(temp);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
