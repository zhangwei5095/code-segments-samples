package com.joejoe.test;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joejoe.test.entity.Person;
import com.joejoe.test.entity.Persons;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                Persons p = new Persons();

                for(int i = 0; i < 10;i++) {
                    Person person = new Person();
                    person.setAge(i);
                    person.setName("name" + i);
                    person.setSex("sex" + i);
                    p.getPersonList().add(person);
                }

                intent.putExtra("data", (java.io.Serializable) p);
                startActivity(intent);
            }
        });


    }


}
