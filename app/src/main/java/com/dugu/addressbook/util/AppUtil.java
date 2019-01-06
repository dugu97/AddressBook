package com.dugu.addressbook.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dugu.addressbook.Constants;

import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppUtil {

    //    格式化long类型的时间
    public static String formatTimeInMillis(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        Date date = cal.getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年 MM月 dd日");
        String fmt = dateFormat.format(date);
        return fmt;
    }

    public static boolean isNullString(String string) {
        if (string == null || "".equals(string))
            return true;
        return false;
    }

    public static boolean isNullList(List list) {
        if (list == null || list.size() == 0)
            return true;
        return false;
    }

    public static int getRandomColor(Long seed) {
        Long temp = Math.abs(seed);
        int index = (int) (temp % Constants.COLOR_PROJECT.length);
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

    public static void loadImageWithGlide(Context context, ImageView imageView, byte[] data) {
        if (data != null && data.length > 0)
            Glide.with(context)
                    .load(data)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
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

    public static void setRing(Context context, int type, String path, String title) {

        Uri oldRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE); //系统当前  通知铃声

        File sdfile = new File(path);
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, title);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
//        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
//        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_MUSIC, true);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile.getAbsolutePath());
        Uri newUri = null;
        String deleteId = "";
        try {
            Cursor cursor = context.getContentResolver().query(uri, null, MediaStore.MediaColumns.DATA + "=?", new String[] { path },null);
            if (cursor.moveToFirst()) {
                deleteId = cursor.getString(cursor.getColumnIndex("_id"));
            }
//            LogTool.e("AGameRing", "deleteId:" + deleteId);

            context.getContentResolver().delete(uri,
                    MediaStore.MediaColumns.DATA + "=\"" + sdfile.getAbsolutePath() + "\"", null);
            newUri = context.getContentResolver().insert(uri, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (newUri != null) {

            String ringStoneId = "";
            if (null != oldRingtoneUri) {
                ringStoneId = oldRingtoneUri.getLastPathSegment();
            }

            Uri setRingStoneUri;

            if (type == RingtoneManager.TYPE_RINGTONE || ringStoneId.equals(deleteId)) {
                setRingStoneUri = newUri;

            } else {
                setRingStoneUri = oldRingtoneUri;
            }



            RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, setRingStoneUri);

            switch (type) {
                case RingtoneManager.TYPE_RINGTONE:
                    Toast.makeText(context.getApplicationContext(), "设置来电铃声成功！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
