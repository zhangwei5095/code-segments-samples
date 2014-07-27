package net.fqjj.www.imagewall;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.fqjj.www.imagewall.entity.ImageBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class Main extends ActionBarActivity implements View.OnClickListener{


    private final String dataURL = "http://fqjj.net/android/imagesdata.xml";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requetXmlData();
            }
        });

    }


    /***
     * 重写StringRequest解决乱码问题
     */
    private class myRequest extends StringRequest {

        public myRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String str = null;
            try {
                str = new String(response.data,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
        }
    }



    private void requetXmlData() {

        RequestQueue mQueue = Volley.newRequestQueue(this);

        myRequest stringRequest = new myRequest(Request.Method.GET, dataURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                        parserXML(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }
        );

        mQueue.add(stringRequest);
    }

    private void parserXML(String response) {

        XmlPullParser parser = Xml.newPullParser();
        ArrayList<ImageBean> beans = new ArrayList<ImageBean>();
        ImageBean image = null;

        try {

            parser.setInput(new ByteArrayInputStream(response.getBytes()), "utf-8");
            int eventType = parser.getEventType();

            while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("image")) {
                            image = new ImageBean();
                            image.setTitle(parser.getAttributeValue(0));
                            image.setUrl(parser.nextText());
                            beans.add(image);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("image")) {

                        }
                        break;
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("aaaaaa", String.valueOf(beans));

    }


    @Override
    public void onClick(View v) {
           Log.d("","");
    }
}

