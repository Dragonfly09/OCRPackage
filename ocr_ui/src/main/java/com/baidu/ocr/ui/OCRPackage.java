package com.baidu.ocr.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;

/**
 * Created by xieqingting on 2018/12/3.
 */

public class OCRPackage {
    public static OCRPackage instance;
    private Context context;
    private boolean hasGotToken = false;
    public static CameraRoute cameraRoute;


    public OCRPackage(Context context) {
        if (context != null) {
            this.context = context;
        }
    }

    public static CameraRoute getCameraRoute() {
        if (cameraRoute == null) {
            cameraRoute = new CameraRoute();
        }
        return cameraRoute;
    }

    public static OCRPackage getInstance(Context ctx) {
        if (instance == null) {
            synchronized (OCRPackage.class) {
                if (instance == null) {
                    instance = new OCRPackage(ctx);
                }
            }
        }

        return instance;
    }

    public void init(@NonNull Activity activity,  @NonNull String ak, @NonNull String sk) {
        initAccessTokenWithAkSk(ak, sk);
        if (!checkTokenStatus()) {
            return;
        }

        getCameraRoute().startActivity(activity);


    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(context, "token还未成功获取", Toast.LENGTH_SHORT).show();
        }
        return hasGotToken;
    }

    private void initAccessTokenWithAkSk(String ak, String sk) {

        if (OCR.getInstance(context).getAccessToken() == null) {

            OCR.getInstance(context).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
                @Override
                public void onResult(AccessToken result) {
                    String token = result.getAccessToken();
                    Log.i("initAccessTokenWithAkSk", "token=" + token);
                    hasGotToken = true;
                }

                @Override
                public void onError(OCRError error) {
                    error.printStackTrace();
                }
            }, context, ak, sk);
        } else {
            hasGotToken = true;
        }
    }

}
