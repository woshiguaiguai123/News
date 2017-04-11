package util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xcdq on 2017/2/23.
 */

public class CommonUtil {
    // 准备格式化小数的类(DecimalFormat)，给定一个格式化规则，
    // "#.00"表示取所有整数部分并保留两位小数
    private static DecimalFormat format = new DecimalFormat("#.00");

    public static String getFileSize(long fileSize) {
        if (fileSize < 1024) {
            return fileSize + "B";
        } else if (fileSize < 1024 * 1024) {
            return format.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1024 * 1024 * 1024) {
            return format.format((double) fileSize / 1024 / 1024) + "MB";
        } else {
            return format.format((double) fileSize / 1024 / 1024 / 1024) + "GB";
        }
    }

    // 格式化日期时间
    public static String formatTime(long time) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}
