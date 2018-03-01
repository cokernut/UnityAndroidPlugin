package top.cokernut.webview.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.webkit.WebView;

import top.cokernut.webview.R;

public class GameWebView extends BaseWebView {
    public GameWebView(Activity activity) {
        super(activity);
    }

    @Override
    public void goBack() {
        super.goBack();
    }

    @Override
    public void init() {
        super.init();
        setWebViewClient(new MyWebViewClient());
    }

    private class MyWebViewClient extends BaseWebViewClient {

        @SuppressLint("ResourceAsColor")
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            view.setBackgroundColor(R.color.transparent);
            //ImageLoader.loadBG(mContext, view, R.mipmap.bg_game);
        }
    }
}
