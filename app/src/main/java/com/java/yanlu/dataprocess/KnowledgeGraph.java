package com.java.yanlu.dataprocess;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class KnowledgeGraph {
    public String label;
    public String url;
    public String introduction;
    public JSONObject properties;
    public JSONArray relations;


    public String img;
    public Bitmap bitmap = null;

    public KnowledgeGraph(){
        label="";
        url="";
        introduction="";
        properties=new JSONObject();
        relations=new JSONArray();
        img="";
    }

    public String getLabel(){
        return label;
    }
    public String getIntroduction(){
        return introduction;
    }
    public String getProperties(){
        String pro = "属性：\n";
        Iterator iterator = properties.keys();
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            String value = properties.optString(key);
            pro += key+"："+value+"\n";
        }
        if (pro.equals("属性：\n")){
            return "";
        }
        return pro;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public String  getRelations(){
        String re="关系：\n";
        for (int i = 0;i<relations.length();i++){
            JSONObject onerelation = relations.optJSONObject(i);
            boolean forward = onerelation.optBoolean("forward");
            String arrow = " ← ";
            if (forward)
                arrow = " → ";
            arrow = onerelation.optString("relation")+arrow+onerelation.optString("label");
            re += arrow+"\n";
        }
        if (re.equals("关系：\n")){
            return "";
        }
        return re;
    }


}
