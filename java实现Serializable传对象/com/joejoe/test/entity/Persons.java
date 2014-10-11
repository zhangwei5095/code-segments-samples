package com.joejoe.test.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joejoe on 2014/10/6.
 */
public class Persons  implements Serializable{

    List<Person> personList = new ArrayList<Person>();

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }


}
