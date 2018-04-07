package pers.sxqsachin.mtgsearcher.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.net.URLConnection;

import pers.sxqsachin.mtgsearcher.MTGSearcher;
import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.net.LocalConnection;

/**
 *
 * URLFileManager
 *
 * Created by songxinqi-sachin on 16-6-5.
 */
public class URLFileManager {

    public static void  getObjectFromUrl(final String src, final OutputStream outputStream, final OnCompleteListener<Integer> listener) {
        getObjectFromUrl(src, outputStream, listener, true);
    }

    public static void  getObjectFromUrl(final String src, final OutputStream outputStream, final OnCompleteListener<Integer> listener, boolean onNewThread) {
        if (onNewThread) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int size = get(src, outputStream);
                    if (listener != null) {
                        listener.onComplete(size);
                    }
                }
            }).start();
        } else {
            int size = get(src, outputStream);
            if (listener != null) {
                listener.onComplete(size);
            }
        }
    }

    private static int get(String src, OutputStream outputStream) {
        long sleep = 5;
        int sleepIncrement = 5;
        int sleepTimes = 0;
        boolean exit = false;
        int size = 0;
        int readTimes = 0;

        URLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = LocalConnection.getLocalURLConnection(src, 5000);
            inputStream = connection.getInputStream();

            int b = inputStream.read();
            outputStream.write(b);
            size++;

            while (!exit) {
                int availableBytes = inputStream.available();
                if (availableBytes == 0) {
                    Thread.sleep(sleep);
                    sleep += sleepIncrement;
                    sleepTimes++;
                    if (sleepTimes > 25) {
                        exit = true;
                    }
                } else {
                    readTimes++;
                    sleep = sleep == sleepIncrement ? sleepIncrement : (sleep - sleepIncrement);
                    sleepTimes = sleepTimes <= 0 ? 0 : (sleepTimes - 1);

                    byte bytes[] = new byte[availableBytes];
                    int ret = inputStream.read(bytes, 0, bytes.length);
                    outputStream.write(bytes);
                    size += ret;

                    int by = inputStream.read();
                    if (by != -1) {
                        outputStream.write(by);
                        size++;
                    } else {
                        exit = true;
                    }
//
//                    if (rt > 1) {
//                        if (ab % 1400 != 0) {
//                            exit = true;
//                        }
//                    }
                }
            }
            outputStream.flush();

            inputStream.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
//            if (e instanceof SocketTimeoutException) {
//                try {
//                    if (outputStream != null) {
//                        outputStream.flush();
//                    }
//                    if (inputStream != null) {
//                        inputStream.close();
//                    }
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                get(src, outputStream);
//            }
        }

        return size;
    }
}
