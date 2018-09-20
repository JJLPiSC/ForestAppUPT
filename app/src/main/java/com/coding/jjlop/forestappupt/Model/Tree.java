package com.coding.jjlop.forestappupt.Model;


import com.google.firebase.database.PropertyName;

public class Tree {
    @PropertyName("id_t")
    private String id_t;
    @PropertyName("name")
    private String name;
    @PropertyName("value")
    private String value;
    @PropertyName("i_perd")
    private String i_perd;
    @PropertyName("d_plant")
    private String dp;
    @PropertyName("l_water")
    private String l_water;
    @PropertyName("alias")
    private String alias;
    @PropertyName("lat")
    private String lat;
    @PropertyName("lng")
    private String lng;

    public Tree() {
        //Requiered for FireBase
    }

    public Tree(String id_t, String name,String value, String i_perd) {
        this.id_t = id_t;
        this.name = name;
        this.value = value;
        this.i_perd = i_perd;
    }

    public Tree(String id_t, String name, String value, String i_perd, String dp,String l_water, String alias, String lat, String Lng) {
        this.id_t = id_t;
        this.name = name;
        this.value = value;
        this.i_perd = i_perd;
        this.dp = dp;
        this.l_water = l_water;
        this.alias = alias;
    }

    public String getL_water() {
        return l_water;
    }

    public void setL_water(String l_water) {
        this.l_water = l_water;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId_t() {
        return id_t;
    }

    public void setId_t(String id_t) {
        this.id_t = id_t;
    }

    public String getI_perd() {
        return i_perd;
    }

    public void setI_perd(String i_perd) {
        this.i_perd = i_perd;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id_t='" + id_t + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", i_perd='" + i_perd + '\'' +
                '}';
    }
}
