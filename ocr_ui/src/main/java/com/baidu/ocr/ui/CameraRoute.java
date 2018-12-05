package com.baidu.ocr.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.service.RecognizeService;
import com.baidu.ocr.ui.util.FileUtil;
import com.google.gson.Gson;

import java.util.List;


/**
 * Created by xieqingting on 2018/12/4.
 */

public class CameraRoute {
    private static final int REQUEST_CODE_GENERAL_BASIC = 100;
    private Activity activity = null;
    private Fragment fragment = null;

    public CameraRoute() {
    }

    public void startActivity(@NonNull Activity activity) {
        this.activity = activity;
        Intent intent = new Intent(activity.getBaseContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(activity.getApplicationContext()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_GENERAL);
        activity.startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
    }

    public void startActivity(@NonNull Fragment fragment) {
        this.fragment = fragment;
        Intent intent = new Intent(activity.getBaseContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(fragment.getActivity().getApplicationContext()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_GENERAL);
        fragment.startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data, final CallBack callBack) {

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_GENERAL_BASIC) {
            RecognizeService.recGeneralBasic(activity.getBaseContext(), FileUtil.getSaveFile(activity.getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            GeneralResult generalResult = new Gson().fromJson(result, GeneralResult.class);
                            List<? extends WordSimple> wordList = generalResult.getWordList();
                            StringBuilder sb = new StringBuilder();
                            if (wordList != null && wordList.size() > 0) {
                                for (WordSimple wordSimple : wordList) {
                                    WordSimple word = wordSimple;
                                    sb.append(word.getWords());
                                    sb.append("\n");
                                }
                                if (callBack != null) {
                                    callBack.onResult(sb.toString());
                                }

                            } else {
                                if (callBack != null) {
                                    callBack.onError("图片文字内容为空");
                                }

                            }

                        }
                    });
            releaseCall();
            return true;
        } else {
            releaseCall();
            return false;
        }

    }

    private void releaseCall() {
        if (activity != null) {
            activity = null;
        }
        if (fragment != null) {
            fragment = null;
        }
    }

}
