package util;

import android.util.Log;

/**
 * Created by xcdq on 2017/2/13.
 */

public class LogUtil {
    private static boolean debug;

    public static void d(Object object, String msg) {
        if (debug) {
            Log.d(object.getClass().getSimpleName(), msg);
        }

    }
}
