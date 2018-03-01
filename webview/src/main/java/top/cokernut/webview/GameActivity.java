package top.cokernut.webview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import top.cokernut.webview.widget.GameWebView;


public class GameActivity extends Activity {
    private GameWebView mWebView =  null;

    public String gameUrl = "";

    public static final String URL = "url";
    public static final String FILE_PREFIX = "file://";

    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mLayout = findViewById(R.id.layout);
        mWebView = new GameWebView(this);
        mLayout.addView(mWebView);
        gameUrl = FILE_PREFIX + getIntent().getStringExtra(URL);
        mWebView.loadUrl(gameUrl);
    }

    @Override
    protected void onResume() {
        if (mWebView != null) {
            mWebView.onResume();
            mWebView.resumeTimers();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mWebView != null) {
            mWebView.onPause();
            mWebView.pauseTimers();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadUrl("about:blank");
            mWebView = null;
        }
        super.onDestroy();
    }
}
