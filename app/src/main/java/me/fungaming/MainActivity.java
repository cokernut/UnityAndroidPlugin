package me.fungaming;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import top.cokernut.app.R;
import top.cokernut.webview.GameActivity;
import top.cokernut.webview.WebActivity;


public class MainActivity extends UnityPlayerActivity {

    private static final int REQUEST_CODE = 0x100;
    private String result;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void goQRCode(String ags) {
        Log.d("=========", ags);
        Intent intent = new Intent(this, CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        /*when(SPUtils.getInstance().getInt(Config.SCREEN_DIRECTION, ActivityInfo.SCREEN_ORIENTATION_SENSOR)) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE -> {
                bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_LANDSCAPE)
            }
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT -> {
                bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_PORTRAIT)
            }
            ActivityInfo.SCREEN_ORIENTATION_SENSOR -> {
                bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO)
            }
        }*/
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (null != data && null != data.getExtras()) {
                Bundle bundle = data.getExtras();
                if (bundle.containsKey(CaptureActivity.EXTRA_SCAN_RESULT)) {
                    //处理结果
                    result = bundle.getString(CaptureActivity.EXTRA_SCAN_RESULT);
                    QRCodeResult();
                } else {
                    Toast.makeText(this, getString(R.string.qr_fail), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    //调用Unity方法
    public void QRCodeResult() {
        Log.d("========QRCodeResult", result);
        UnityPlayer.UnitySendMessage("Login","QRCodeResult", result);
    }

    // 显示Toast消息
    public void ShowToast(final String message){
        Log.d("=========ShowToast", message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void startGameActivity(String path) {
        Log.d("======startGameActivity", path);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.URL,path);
        startActivity(intent);
    }


    public void startWebActivity(String url) {
        Log.d("======startGameActivity", url);
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(WebActivity.URL, url);
        startActivity(intent);
    }
}
