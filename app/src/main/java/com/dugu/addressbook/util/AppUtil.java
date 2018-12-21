package com.dugu.addressbook.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.dugu.addressbook.Constants;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AppUtil {

    private static Random random = new Random();
    private static int colorFlat = -1;

    //    格式化long类型的时间
    public static String formatTimeInMillis(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fmt = dateFormat.format(date);
        return fmt;
    }

    public static boolean isNullString(String string) {
        if (string == null || "".equals(string))
            return true;
        return false;
    }

    public static int getRandomColor() {
        int index = random.nextInt(Constants.COLOR_PROJECT.length);
        while (index == colorFlat)
            index = random.nextInt(Constants.COLOR_PROJECT.length);
        colorFlat = index;
        return Constants.COLOR_PROJECT[index];
    }

    public static int dp2px(@NonNull Context context, int dp) {

//        Log.d(TAG, "dp2px:densityDpi   " + context.getResources().getDisplayMetrics().densityDpi);
//        Log.d(TAG, "dp2px:xdpi    " + context.getResources().getDisplayMetrics().xdpi);
//        Log.d(TAG, "dp2px: ydpi  " + context.getResources().getDisplayMetrics().ydpi);
//        Log.d(TAG, "dp2px: " + context.getResources().getDisplayMetrics().density);
//        Log.d(TAG, "dp2px: " + context.getResources().getDisplayMetrics().density * dp);
        return (int) (context.getResources().getDisplayMetrics().density * dp);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        return baos.toByteArray();

        int bytes = bm.getByteCount();
        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bm.copyPixelsToBuffer(buf);

        return buf.array();
    }
}
