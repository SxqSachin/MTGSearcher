package pers.sxqsachin.mtgsearcher.net;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import pers.sxqsachin.mtgsearcher.struct.mtg.condition.UrlCondition;

/**
 *
 * LocalConnection
 *
 * Created by songxinqi-sachin on 16-7-11.
 */
public class LocalConnection {
    public static Connection    getLocalConnection(String url) {
        return getLocalConnection(url, 5000);
    }

    public static Connection    getLocalConnection(String url, int timeout) {
        Connection connection = Jsoup.connect(url);
        connection.cookie("lang", "cn");
        connection.timeout(timeout);
        connection.userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13");

        return connection;
    }

    public static URLConnection getLocalURLConnection(String src) throws IOException {
        return getLocalURLConnection(src, 3820);
    }

    public static URLConnection getLocalURLConnection(String src, int timeout) throws IOException {
        URL url = new URL(src);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13");

        return connection;
    }
}
