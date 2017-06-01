package com.zqu.library.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zqu.library.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsWeb extends Activity {
    private WebView webView;
    private static final int DIALOG_KEY = 0;
    private String linkview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);
        Intent intent = getIntent();
        linkview=intent.getStringExtra("link");
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        try {
            ProgressAsyncTask asyncTask=new ProgressAsyncTask(webView);
            asyncTask.execute(10000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    // 弹出"查看"对话框
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_KEY: {
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("获取数据中  请稍候...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                return dialog;
            }
        }
        return null;
    }

    class ProgressAsyncTask extends AsyncTask<Integer, Integer, Boolean> {

        private WebView webView;
        public ProgressAsyncTask(WebView webView) {
            super();
            this.webView=webView;

        }

        /**
         * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
         * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
         * 但是可以调用publish Progress方法触发onProgressUpdate对UI进行操作
         */
        @Override
        protected Boolean doInBackground(Integer... params) {
            return true;
        }

        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(Boolean result) {
            webView.loadUrl(linkview);

            removeDialog(DIALOG_KEY);
        }

        // 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
        @Override
        protected void onPreExecute() {
            showDialog(DIALOG_KEY);
        }

        /**
         * 这里的Intege参数对应AsyncTask中的第二个参数
         * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
         * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
         */
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }



}
