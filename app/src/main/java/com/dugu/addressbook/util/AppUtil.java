package com.dugu.addressbook.util;

import com.dugu.addressbook.Constants;

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
}
