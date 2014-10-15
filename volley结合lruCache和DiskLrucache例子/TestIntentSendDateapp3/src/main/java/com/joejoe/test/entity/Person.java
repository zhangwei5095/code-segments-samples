package com.joejoe.test.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by joejoe on 2014/10/6.
 */
public class Person implements Parcelable {

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

    public Person(){};

    public Person(Parcel in) {
        age = in.readInt();
        sex = in.readString();
        name = in.readString();

    }

    public static final Parcelable.Creator<Person> CREATOR= new Parcelable.Creator<Person>() {

        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(sex);
        dest.writeString(name);
    }
}
