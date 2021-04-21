package com.java.yanlu.dataprocess;

import java.util.List;


public class SimpleNews {
    public String plain_json;

    public String _id;
    public String category;
    public String content;
    public List<String> authors;
    public String date;
    public String entities;
    public String geoInfo;
    public String id;
    public String lang;
    public String seg_text;
    public String source;
    public String tflag;
    private String time;
    public String title;
    public String type;
    public String urls;
    public boolean already_read = false;

    SimpleNews(){
        plain_json="";
        _id="";
        category="";
        content="";
        date="";
        entities="";
        geoInfo="";
        id="";
        lang="";
        seg_text="";
        source="";
        tflag="";
        time="";
        title="";
        type="";
        urls="";
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime() {
        try {
            if (time.length() >= 10) {
                return time.substring(0, 4) + "-" + time.substring(5, 7) + "-" + time.substring(8, 10);
            } else {
                return "0000-00-00";
            }
        } catch(Exception e) {
            return "0000-00-00";
        }
    }
    public String getAuthors(){
        String authorname = source+"\n";
        for (int i = 0 ;i<authors.size();i++){
            authorname += authors.get(i)+"\n";
        }
        return authorname;
    }
    public String getTitle(){
        return title;
    }
    public String getSource(){
        return source;
    }
    public String getContent() { return content; }
}

