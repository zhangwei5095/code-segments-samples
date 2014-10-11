package com.joejoe.test.entity;

import java.io.Serializable;

/**
 * Created by joejoe on 2014/10/6.
 */
public class Person implements Serializable{

    int age;
    String sex;
    String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
