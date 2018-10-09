package com.waifusims.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
//
//        mWebView.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                MainActivity.this.setProgress(newProgress * 1000);
//            }
//        });
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
//            }
//        });
        mWebView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });
//        mWebView.loadUrl("https://www.sanseido.biz/User/Dic/Index.aspx?st=0&DORDER=171615&DailyJJ=checkbox&TWords=%E3%82%A2%E3%83%8B%E3%83%A1");
        String baseUrl = "https://www.sanseido.biz/User/Dic/Index.aspx?st=0&DORDER=171615&DailyJJ=checkbox&TWords=%E3%82%A2%E3%83%8B%E3%83%A1";
        String html = null;
        try {
            html = new Test().execute(baseUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mWebView.loadDataWithBaseURL(baseUrl, html, null, null, null);

    }
        class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            test = html;
        }

    }
    private class Test extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                return Jsoup.connect(strings[0]).get().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
