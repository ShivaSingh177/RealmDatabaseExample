package com.realmdatabaseexample.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by esec-sruthi on 21/10/17.
 */

public class RealmModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String cls;
    private String sub;


    @Override
    public String toString() {
        return "RealmModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cls='" + cls + '\'' +
                ", sub='" + sub + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
