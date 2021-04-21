package com.java.yanlu.dataprocess;

import android.content.Context;
import android.graphics.Point;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    public static Manager I = null;
    public static Point point;
    public static boolean type[] = new boolean[6];
    public final int SIZE = 20;
    public final int MAX_PAGE = 10;
    public final int SEARCH_SIZE = 100;
    public static synchronized void CreateI(Context context) {
        try {
            I = new Manager(context);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }

    private FileStorage filestorage;

    private Manager(final Context context) throws IOException {
        this.filestorage = new FileStorage(context);
        point = new Point();
    }

    public void restore(SimpleNews simpleNews){
        filestorage.insertDetail(simpleNews);
    }

    public List<SimpleNews> fetchSimpleNews(final String type, final int page, final int size) {
        List<SimpleNews> list = null;
        try {
            list = API.GetSimpleNews(type, page, size);
        } catch (Exception e) {
            return new ArrayList<SimpleNews>();
        }
        if (list==null){
            return new ArrayList<SimpleNews>();
        }
        return list;
    }

    public List<SimpleNews> fetchHistorySimpleNews(){
        List<SimpleNews> list = null;
        try {
            list = filestorage.fetchRead();
        } catch (Exception e) {
            return new ArrayList<SimpleNews>();
        }
        if (list==null){
            return new ArrayList<SimpleNews>();
        }
        return list;
    }

    public DetailNews fetchDetailNews(final String news_ID){
        DetailNews news = null;
        try {
            news = filestorage.fetchDetail(news_ID);
        } catch (JSONException e) {
            return new DetailNews();
        }
//        System.out.println("news:"+news._id);
        if (news==null){
            try {
                news = API.GetDetailNews(news_ID);
            } catch (Exception e) {
                return new DetailNews();
            }
            System.out.println("api");
        }

        if (news==null){
            return new DetailNews();
        }
        else {
            filestorage.insertDetail(news);
        }
        return news;
    }

    public List<SimpleNews> fetchKeywordSimpleNews(boolean first,String keyword){
        List<SimpleNews> list = null;
        try {
            list = API.GetSimpleNewsKeyword(first,keyword);
        } catch (Exception e) {
            return new ArrayList<SimpleNews>();
        }
        if (list==null){
            return new ArrayList<SimpleNews>();
        }
        return list;
    }

    public ChartData fetchChartData(final String name){
        ChartData chartData = null;
        try {
            chartData = API.GetChartData(name);
        } catch (Exception e) {
            return new ChartData();
        }
        if (chartData==null)
            return new ChartData();
        return chartData;
    }

    public List<KnowledgeGraph> fetchKnowledgeGraph(final String name){
        List<KnowledgeGraph> knowledgeGraphs = null;
        try {
            knowledgeGraphs=API.GetKnowledgeGraph(name);
        } catch (Exception e) {
            return new ArrayList<KnowledgeGraph>();
        }
        if (knowledgeGraphs==null)
            return new ArrayList<KnowledgeGraph>();

        return knowledgeGraphs;
    }

    public void Delete(){
        filestorage.dropTables();
    }

    public boolean isConnected(){
        return API.isConnected();
    }
}
