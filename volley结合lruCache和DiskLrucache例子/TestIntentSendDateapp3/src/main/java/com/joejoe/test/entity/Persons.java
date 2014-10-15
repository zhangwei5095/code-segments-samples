package com.joejoe.test.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joejoe on 2014/10/6.
 */
public class Persons  implements Parcelable{

    public ArrayList<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(ArrayList<Person> personList) {
        this.personList = personList;
    }

    ArrayList<Person> personList = new ArrayList<Person>();


    public Persons(){};

    public Persons(Parcel in) {
       //personList =  in.readParcelable(Person, Person.class.getClassLoader());
    }


    public static final Parcelable.Creator<Persons> CREATOR= new Parcelable.Creator<Persons>() {

        public Persons createFromParcel(Parcel in) {
            return new Persons(in);
        }

        public Persons[] newArray(int size) {
            return new Persons[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
