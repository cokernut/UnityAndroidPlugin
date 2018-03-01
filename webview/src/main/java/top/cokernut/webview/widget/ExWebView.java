package top.cokernut.webview.widget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

public class ExWebView extends BaseWebView {
    private WebViewInterface mWebViewInterface;
    private OnScrollListener mOnScrollListener;

    public ExWebView(Activity activity) {
        super(activity);
    }

    //设置回调
    public void setWebViewInterface(WebViewInterface baseWebViewInterface) {
        mWebViewInterface = baseWebViewInterface;
    }

    //设置WebView滑动监听
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    //回调方法接口
    public interface WebViewInterface {
        //页面加载完成
        void pageFinished();

        void pageStarted(String url);

        //加载错误
        void error();
    }

    public interface OnScrollListener {
        //滑动到顶部
        void onTop();
        //滑动到中间
        void onCenter();
        //返回
        void back();
    }

    @Override
    public void goBack() {
        mOnScrollListener.back();
        super.goBack();
    }

    @Override
    public void init() {
        super.init();
        setWebViewClient(new MyWebViewClient());
        setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollListener != null) {
            if (Math.abs(t - oldt) > 5) {
                if (this.getScrollY() <= 1) {
                    mOnScrollListener.onTop();
                } else {
                    mOnScrollListener.onCenter();
                }
            }
        }
    }

    private class MyWebViewClient extends BaseWebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mWebViewInterface != null) {
                mWebViewInterface.pageFinished();
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mWebViewInterface != null) {
                mWebViewInterface.pageStarted(url);
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            //错误处理
            if (mWebViewInterface != null) {
                mWebViewInterface.error();
            }
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            //http错误处理
            if (mWebViewInterface != null) {
                mWebViewInterface.error();
            }
            super.onReceivedHttpError(view, request, errorResponse);
        }
    }

    private class MyWebChromeClient extends BaseWebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }
}
