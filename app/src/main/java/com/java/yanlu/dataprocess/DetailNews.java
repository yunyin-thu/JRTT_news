package com.java.yanlu.dataprocess;

import java.util.ArrayList;
import java.util.List;

public class DetailNews extends SimpleNews{

    public class String_String{
        public String name;
        public String value;
    }

//    public String plain_json;

    //    public String _id;
    public String aminer_id;
    public List<String> authors;
    //    public String category;
//    public String content;
//    public String date;
    public String doi;
    public List<String_String> entities;
    public String expert;
    //    public String geoInfo;
//    public String id;
    public String influence;
    //    public String lang;
    public String pdf;
    public List<String> regionIDs;
    public List<String> related_events;
    //    public String seg_text;
    public String sitename;
//    public String source;
//    public String tflag;
//    public String time;
//    public String title;
//    public String type;

    public List<String> urls;
    public String year;

    DetailNews(){
        super();
        aminer_id = "";
        authors = new ArrayList<String>();
        doi="";
        entities=new ArrayList<String_String>();
        expert="";
        influence="";
        pdf="";
        regionIDs=new ArrayList<String>();
        related_events=new ArrayList<String>();
        sitename="";
    }

}

