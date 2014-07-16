package com.joejoe.testcase.mytestcase;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 转换JSON和XML的代码片断
 *
 * @author joejoeZ
 */

public class SampleParseJSONAndXML extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //testJSON() ;
        //testJSON2();
        //showData(parseJsonData());
        showData(parseXMLData());
    }

    private void testJSON2() {

        String jsonstr = "[{ name:joejoe, age:21}, {name:fofo ,age:18} ]";
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            JSONArray jsonArray = new JSONArray(jsonstr);

            for (int i = 0; i < jsonArray.length(); i++) {

                HashMap map = new HashMap();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                map.put("name", jsonObject.getString("name"));
                map.put("age", jsonObject.getString("age"));
                map.put("none", jsonObject.optString("haha", "aaaaahahah"));
                Log.d("map", (String) map.get("name"));
                Log.d("map", (String) map.get("age"));
                Log.d("map", (String) map.get("none"));
                list.add(map);
            }

            Log.d("list", String.valueOf(list));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void testJSON() {

        String jsonstr = "{name : [1,2,3,5]}";

        try {
            JSONObject json = new JSONObject(jsonstr);
            String subNode = json.getString("name");
            Log.d("json", subNode);
            JSONArray jsonArray = new JSONArray(subNode);

            for (int i = 0; i < jsonArray.length(); i++) {
                int item = jsonArray.getInt(i);
                Log.d("getInt", String.valueOf(item));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showData(List list) {

        List<String> userlist = new ArrayList<String>();

        for (Object obj : list) {
            User user = (User) obj;
            userlist.add(user.getId() + "==" + user.getName() + "==" + user.getImage());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.layout_array_item, userlist);
        ListView listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(adapter);

    }

    private List parseXMLData() {

        List list = null;

        try {
            InputStream inputStream = this.getAssets().open("imgdata.xml");
            try {
                list = parseXML(inputStream);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List parseXML(InputStream stream) throws XmlPullParserException, IOException {

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(stream, "utf-8");
        int eventType = parser.getEventType();
        List<User> userlist = null;
        User user = null;

        // XmlPullParser是一种垂直式的解析 依次查看每一个已知的节点 然后作相应的处理
        while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    // 根结点
                    if (parser.getName().equals("contacts")) {
                        userlist = new ArrayList<User>();
                        // 节点名为 contact
                    } else if (parser.getName().equals("contact")) {
                        user = new User();
                        // 获取节点列表上的第一个属性
                        user.setId(Integer.parseInt(parser.getAttributeValue(0)));
                        // 节点名为name
                    } else if (parser.getName().equals("name")) {
                        user.setName(parser.nextText());
                    } else if (parser.getName().equals("image")) {
                        user.setImage(parser.getAttributeValue(0));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("contact")) {
                        userlist.add(user);
                    }
                    break;
            }

        }
        return userlist;
    }


    private List parseJsonData() {

        String result = "";
        List<Map<String, String>> jsonList = null;

        try {
            InputStream inputStream = this.getAssets().open("imgdata.json");
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            result = EncodingUtils.getString(buffer, "gbk");
            Log.d("result", result);
            JSONObject jsonObject = null;
            jsonList = new ArrayList<Map<String, String>>();

            try {
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    Map map = new HashMap();
                    jsonObject = jsonArray.getJSONObject(i);
                    map.put("name", jsonObject.get("name"));
                    map.put("id", jsonObject.get("id"));
                    map.put("image", jsonObject.get("image"));
                    // opt 数据没有则返回空串 也可以填默认值
                    map.put("test", jsonObject.optString("notexsit", "set"));
                    // get不存在的数据会报错
                    // map.put("test", jsonObject.getString("notexsit"));
                    jsonList.add(map);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("jsonList", jsonList.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonList;
    }
}
