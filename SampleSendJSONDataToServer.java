package com.joejoe.testcase.mytestcase;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 示例发送JSON数据到服务端
 * @author joejoeZ
 *
 */

public class SampleSendJSONDataToServer extends ActionBarActivity implements View.OnClickListener {

    private static final String path = "http://192.168.11.101:8080/Test/httpClientRequest.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_client_get_response);
    }

    @Override
    public void onClick(View v) {
        getRequest();
    }

    private void getRequest() {

        new Thread() {

            @Override
            public void run() {
                HttpPost httpPost = new HttpPost(path);
                BasicHttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
                HttpConnectionParams.setSoTimeout(httpParams, 5000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
                ArrayList<NameValuePair> requestParams = new ArrayList<NameValuePair>();
                String jsonStr = "{name:joejoe, age:21, sex:male}";
                requestParams.add(new BasicNameValuePair("data", jsonStr));

                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(requestParams, HTTP.UTF_8));
                    HttpResponse response = httpClient.execute(httpPost);
                    int statusCode = response.getStatusLine().getStatusCode();

                    if (statusCode == HttpStatus.SC_OK) {
                        HttpEntity entity = response.getEntity();
                        String content = EntityUtils.toString(entity);
                        Log.d("content", content);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }.start();
    }
}
