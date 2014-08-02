package com.joejoe.sqllist.mysqllite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    ListView listView;
    Cursor cursor;
    SQLiteDatabase db;
    MyOpenHelper sqlHelper;

    public static final int DATA_BASE_VERSION_ONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) this.findViewById(R.id.show);
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
                + "/test.db3", null);

        try {
            db.execSQL("insert into info values(null, 'jo', 123)");

        } catch (SQLiteException e) {

            String sql = "create table  info(item_id integer primary key autoincrement, " +
                    "name varchar(255), " +
                    "psd varchar(255))";

            db.execSQL(sql);
            db.execSQL("insert into info values(null, 'jo', 123)");
        }

        findViewById(R.id.bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 需要查询的列包含_id项 兼容低版的SDK
                cursor = db.rawQuery("select item_id as _id, name, psd from info", null);
                inflateList(cursor, "name", "psd");
            }
        });


        // 使用数据据工具类
        sqlHelper = new MyOpenHelper(this, "mydb.db3", DATA_BASE_VERSION_ONE);

        findViewById(R.id.bn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = sqlHelper.getReadableDatabase();
                insertData(database, "jojo", "male");
                Cursor cursor = database.rawQuery("select _id, name, sex from tbs", null);
                inflateList(cursor, "name", "sex");
            }
        });

    }

    private void insertData(SQLiteDatabase db, String word
            , String detail) {
        db.execSQL("insert into tbs values(null , ? , ?)"
                , new String[]{word, detail});
    }

    private void inflateList(Cursor cursor, String str, String str2) {

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this,
                R.layout.line, cursor,
                new String[]{str, str2}
                , new int[]{R.id.my_title, R.id.my_content},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class MyOpenHelper extends SQLiteOpenHelper {

        String sql = "create table  tbs (_id integer primary key autoincrement, " +
                "name varchar(255), " +
                "sex varchar(255))";


        public MyOpenHelper(Context context, String name, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sqlHelper != null) {
            sqlHelper.close();
        }
    }
}
