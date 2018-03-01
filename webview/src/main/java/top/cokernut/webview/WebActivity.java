package top.cokernut.webview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import top.cokernut.webview.widget.ExWebView;

public class WebActivity extends Activity implements ExWebView.WebViewInterface, ExWebView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {

    private ExWebView mWebView =  null;
    private String mUrl = "";

    public static final String URL = "url";

    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        swipe = findViewById(R.id.swipe);
        mWebView = new ExWebView(this);
        mWebView.setWebViewInterface(this);
        mWebView.setOnScrollListener(this);
        swipe.addView(mWebView);
        swipe.setOnRefreshListener(this);
        swipe.setEnabled(false);
    }

    @Override
    protected void onStart() {
        if (getIntent().hasExtra(URL)) {
            mUrl = getIntent().getStringExtra(URL);
            mWebView.loadUrl(mUrl);
        }
        super.onStart();
    }

    @Override
    public void onTop() {
        swipe.setEnabled(false);
    }

    @Override
    public void onCenter() {
        swipe.setEnabled(false);
    }

    @Override
    public void back() {
        swipe.setRefreshing(false);
    }

    @Override
    public void pageStarted(String url) {
        swipe.setEnabled(false);
    }

    @Override
    public void pageFinished() {
        swipe.setRefreshing(false);
    }

    @Override
    public void error() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRefresh() {
        mWebView.reload();
    }

    @Override
    protected void onStop() {
        mWebView = null;
        super.onStop();
    }
}
