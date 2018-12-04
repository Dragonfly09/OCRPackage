package com.baidu.ocr.ui.util;

import android.content.Context;

import java.io.File;

/**
 * Created by xieqingting on 2018/11/21.
 */

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}
