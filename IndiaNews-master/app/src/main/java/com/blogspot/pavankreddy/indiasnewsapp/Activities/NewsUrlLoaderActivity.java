package com.blogspot.pavankreddy.indiasnewsapp.Activities;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebView;

import com.blogspot.pavankreddy.indiasnewsapp.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsUrlLoaderActivity extends AppCompatActivity {
    @BindView(R.id.webview)
    WebView webView;
    public static final String LINK_KEY = "WEB_LINK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_url_loader);

        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(LINK_KEY);
        if(!TextUtils.isEmpty(url) && isInternetAvailable())
        {
            webView.loadUrl(url);
        }
        else if(TextUtils.isEmpty(url))
        {
            new AlertDialog.Builder(this)
                    .setTitle("NO URL FOUND")
                    .setMessage("Press OK to Go Back!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                           finish();
                        }
                    })
                    .show();
        }
        else
        {
            showInternetUnavaialbeAlert();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        return true;

    }

    private boolean isInternetAvailable()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void showInternetUnavaialbeAlert()
    {
        new AlertDialog.Builder(this).setTitle("INTERNET UNAVAILABLE")
                .setMessage("Sorry Cannot Load Data With out Internet Connection!!")
                .setPositiveButton("YES", null)
                .setCancelable(true)
                .setIcon(R.drawable.ic_cancel_black_24dp).show();
    }
}
