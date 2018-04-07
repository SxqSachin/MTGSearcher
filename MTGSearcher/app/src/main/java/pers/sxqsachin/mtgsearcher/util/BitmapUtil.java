package pers.sxqsachin.mtgsearcher.util;

import android.app.backup.BackupDataInputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Trace;
import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * BitmapUtil
 *
 * Created by songxinqi-sachin on 16-6-6.
 */
public class BitmapUtil {

    @Nullable
    public static Bitmap    getFromFile(File source) {
        if (source == null) {
            return null;
        }

        Bitmap bitmap = null;

        ByteArrayInputStream bais = getInputStreamFromBitmapFile(source);
        if (null != bais) {
            try {
                bitmap = BitmapFactory.decodeStream(bais);
                bais.close();
            } catch (IOException | OutOfMemoryError e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    /**
     *
     * Compress the bitmap
     *
     * @link
     *
     * @param source    will NOT be recycled after compress
     * @param quality   0...100
     * @return          the compressed bitmap
     */
    public static Bitmap    compress(Bitmap source, int quality) {
        if (null == source) {
            throw new NullPointerException();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        source.compress(Bitmap.CompressFormat.JPEG, quality, baos);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeStream(bais);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        try {
            baos.close();
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     *
     * @param source    will NOT be recycled after ratio
     * @param ratio     > 0
     * @return          the ratio bitmap
     */
    @Deprecated
    public static Bitmap    ratio(Bitmap source, float ratio) {
        if (null == source) {
            throw new NullPointerException();
        }

        if (ratio < 0) {
            throw new IllegalArgumentException("ratio must be > 0");
        }

        Bitmap bitmap = null;

        if (Float.compare(ratio, 1) == 0) {
            return source;
        }

        ByteArrayInputStream bais = getInputStreamFromBitmap(source);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;

        /**
         * will not return bitmap because the opts.inJustDecodeBounds is true
         */
        BitmapFactory.decodeStream(bais, null, opts);

        int width = opts.outWidth;
        int height = opts.outHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(ratio, ratio);

        try {
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Bitmap.createBitmap(source, 0, 0, width, height, matrix, true);
    }

    @Nullable
    public static Bitmap    ratio(File source, float ratio) {
        if (null == source) {
            throw new NullPointerException();
        }

        if (ratio < 0) {
            throw new IllegalArgumentException("ratio must be > 0");
        }

        Bitmap bitmap = null;

        ByteArrayInputStream bais = getInputStreamFromBitmapFile(source);
        if (null != bais) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(bais, null, opts);
            bais.reset();

            int width = opts.outWidth;
            int height = opts.outHeight;

            Matrix matrix = new Matrix();
            matrix.postScale(ratio, ratio);

            opts.inJustDecodeBounds = false;

            Bitmap tmp = null;
            try {
                tmp = BitmapFactory.decodeStream(bais, null, opts);
                bais.close();
            } catch (IOException | OutOfMemoryError e) {
                e.printStackTrace();
            }
            if (null != tmp) {
                bitmap = Bitmap.createBitmap(tmp, 0, 0, width, height, matrix, true);
                tmp.recycle();
            }
        }

        return bitmap;
    }

    @Deprecated
    public static ByteArrayInputStream   getInputStreamFromBitmap(Bitmap bitmap) {
        if (null == bitmap) {
            throw new NullPointerException();
        }

        ByteArrayOutputStream baos = getOutputStreamFromBitmap(bitmap);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bais;
    }

    @Deprecated
    public static ByteArrayOutputStream getOutputStreamFromBitmap(Bitmap bitmap) {
        if (null == bitmap) {
            throw new NullPointerException();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        return baos;
    }

    @Nullable
    public static ByteArrayInputStream  getInputStreamFromBitmapFile(File file) {
        if (null == file) {
            throw new NullPointerException();
        }

        ByteArrayInputStream bais = null;

        try {
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            FileInputStream fis = new FileInputStream(file);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte bytes[] = new byte[fis.available()];
            fis.read(bytes, 0, bytes.length);
            baos.write(bytes);
            baos.flush();

            bais = new ByteArrayInputStream(baos.toByteArray());

            fis.close();
            baos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bais;
    }

    @Nullable
    public static ByteArrayOutputStream getOutputStreamFromBitmapFile(File file) {
        if (null == file) {
            throw new NullPointerException();
        }

        ByteArrayOutputStream baos = null;

        try {
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            FileInputStream fis = new FileInputStream(file);

            baos = new ByteArrayOutputStream();
            byte bytes[] = new byte[1024 * 4];
            while (fis.read(bytes) != -1) {
                baos.write(bytes);
            }
            baos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos;
    }
}
