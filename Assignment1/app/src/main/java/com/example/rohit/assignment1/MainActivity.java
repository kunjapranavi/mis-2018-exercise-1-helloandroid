package com.example.rohit.assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    EditText mUrl;
    Button mButtonOK;
    WebView mWebView;
    String result1;
    String result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUrl = (EditText) findViewById(R.id.text_url);

        mButtonOK = (Button) findViewById(R.id.button_ok);
        mWebView = (WebView) findViewById(R.id.webView_content);


        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


/*                AsyncHttpClient client = new AsyncHttpClient();
                client.get(mUrl.getText().toString().trim(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        updateWebView(response);
                    }
                });*/
                result2 = mUrl.getText().toString();
                new SimpleTask().execute(mUrl.getText().toString().trim());
            }
        });
    }

    private class SimpleTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // Create Show ProgressBar
        }

        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL urlp = new URL("http://google.co.in");
                URLConnection urlConnection = (HttpURLConnection) urlp.openConnection();
                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int numCharsRead;
                char[] charArray = new char[1024];
                StringBuffer sb = new StringBuffer();
                while ((numCharsRead = isr.read(charArray)) > 0) {
                    sb.append(charArray, 0, numCharsRead);
                }
                result1 = sb.toString();
/*Webview Code- Below*/
                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return result;
        }



        protected void onPostExecute(String result) {
            if (result1 != null) {
                TextView txttitle = (TextView) findViewById(R.id.textView);
                txttitle.setText(result1);
            } else {
                ((TextView) findViewById(R.id.textView)).setText("Enter Correct URL ");
            }
            // Dismiss ProgressBar
            updateWebView(result);

        }

        private void updateWebView(String result) {
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setLoadsImagesAutomatically(true);
            mWebView.loadData(result, "text/html; charset=utf-8", "utf-8");
        }
    }
}
