package pers.sxqsachin.mtgsearcher;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.net.LocalConnection;
import pers.sxqsachin.mtgsearcher.util.LogUtil;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void test() throws Exception {
        OutputStream outputStream = new ByteArrayOutputStream();
        getObjectFromUrl("http://magiccards.info/scans/en/uqc/4.jpg", outputStream, new OnCompleteListener<Integer>() {
            @Override
            public void onComplete(Integer i) {
            }
        });
    }

    public static void  getObjectFromUrl(final String src, final OutputStream outputStream, final OnCompleteListener<Integer> listener) {
        if (src == null ||
                outputStream == null) {
            return;
        }

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

            long st = System.currentTimeMillis();
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
//
                    int by = inputStream.read();
                    if (by != -1) {
                        outputStream.write(by);
                        size++;
                    } else {
                        exit = true;
                    }
//
//                    if (readTimes > 1) {
//                        if (availableBytes % 1400 != 0) {
//                            exit = true;
//                        }
//                    }
                }
            }
            outputStream.flush();
            long et = System.currentTimeMillis();
            LogUtil.systemOut("TIME : " + (et-st));

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
    }
}
