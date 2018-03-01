package top.cokernut.webview.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;

import top.cokernut.webview.BuildConfig;


public class BaseWebView extends WebView {
    protected Context mContext;
    protected Activity mActivity;

    public BaseWebView(Activity activity) {
        super(activity);
        mContext = activity;
        mActivity = activity;
        init();
    }

    public void init() {
        WebSettings ws = getSettings();
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //水平滚动条不显示
        setHorizontalScrollBarEnabled(false);
        //垂直滚动条不显示
        setVerticalScrollBarEnabled(false);
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        ws.setJavaScriptEnabled(true);
        //是否开启本地DOM存储
        ws.setDomStorageEnabled(true);
        //开启 database storage API 功能
        ws.setDatabaseEnabled(true);
        String cacheDirPath = mContext.getFilesDir().getAbsolutePath() + File.separator + "cache/";
        // 每个 Application 只调用一次 WebSettings.setAppCachePath()
        // 设置缓存路径
        ws.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        ws.setAppCacheEnabled(true);
        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        ws.setSupportZoom(false);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        ws.setBuiltInZoomControls(false);
        //隐藏原生的缩放控件
        ws.setDisplayZoomControls(false);
        //设置可以访问文件
        ws.setAllowFileAccess(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        //跨域问题
        ws.setAllowFileAccessFromFileURLs(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        //跨域问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (BuildConfig.DEBUG) {
                setWebContentsDebuggingEnabled(true);
            }
        }
        //设置浏览器标识
        String ua = ws.getUserAgentString();
        ws.setUserAgentString(ua + " Browser_Type/Android_APP");
        //注入带有Java方法的JS对象，名字可以自定义
        //addJavascriptInterface(new InJavaScriptObj(), "WebViewObject");
        setWebViewClient(new BaseWebViewClient());
        setWebChromeClient(new BaseWebChromeClient());
    }

    protected class BaseWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    protected class BaseWebChromeClient extends WebChromeClient {

        @Override
        public void onCloseWindow(WebView window) {
            mActivity.finish();
            super.onCloseWindow(window);
        }
    }
}
