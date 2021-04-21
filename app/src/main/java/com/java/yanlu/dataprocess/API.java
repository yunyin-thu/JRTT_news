package com.java.yanlu.dataprocess;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class API {
    private static String GetBodyFromURL(String url) throws IOException {
        URL cs = new URL(url);
        URLConnection urlConn = cs.openConnection();
        urlConn.setConnectTimeout(10 * 1000);
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String inputLine, body = "";
        while ((inputLine = in.readLine()) != null)
            body = body + inputLine;
        in.close();
        return body;
    }

    static SimpleNews GetSimpleNewsFromJson(JSONObject json_news, boolean from_disk) throws JSONException {
        SimpleNews news = new SimpleNews();
        JSONArray list;
        news.plain_json = json_news.toString();

        news._id = json_news.optString("_id");
//        news.aminer_id = json_news.optString("aminer_id");
        news.authors = new ArrayList<String>();
        list = json_news.optJSONArray("authors");
        if(list!=null)
            for(int i=0;i<list.length();i++){
                JSONObject temp = list.optJSONObject(i);
                String name = temp.optString("name");
                news.authors.add(name);
            }
//        news.category = json_news.optString("category");
        news.content = json_news.optString("content");
//        news.date = json_news.optString("date");
//        news.doi = json_news.optString("doi");
//        news.entities = json_news.optString("entities");
//        news.geoInfo = json_news.optString("geoInfo");
//        news.id = json_news.optString("id");
//        news.influence = json_news.optString("influence");
        news.lang = json_news.optString("lang");
//        news.pdf = json_news.optString("pdf");
//        news.regionIDs = json_news.optString("regionIDs");
//        news.related_events = json_news.optString("related_events");
//        news.seg_text = json_news.optString("seg_text");
        news.source = json_news.optString("source");
//        news.tflag = json_news.optString("tflag");
        news.setTime(json_news.optString("time"));
        news.title = json_news.optString("title");
//        news.type = json_news.optString("type");
//        news.urls = json_news.optString("urls");
//        news.year = json_news.optString("year");


        return news;
    }

    public static ArrayList<SimpleNews> GetSimpleNews(final String type, final int page, final int size) throws IOException, JSONException {
//        String URL_String = new String(String.format("https://covid-dashboard.aminer.cn/api/events/list?type=paper&page=5&size=5"));
        String URL_String = new String(String.format("https://covid-dashboard.aminer.cn/api/events/list?type=%s&page=%d&size=%d",type, page, size));
//        if (category > 0)
//            URL_String = URL_String + String.format("&category=%d", category);
        String body = GetBodyFromURL(URL_String);

        if(body.equals("")) {
            Log.d("warning"," In GetSimpleNews body=\"\"");
            return new ArrayList<SimpleNews>();
        }
        ArrayList<SimpleNews> result = new ArrayList<SimpleNews>();
        JSONObject allData = new JSONObject(body);
        JSONArray list = allData.getJSONArray("data");

        for (int t = 0; t < list.length(); t++) {
            JSONObject json_news = list.getJSONObject(t);
//            System.out.println(json_news.names());
            result.add(GetSimpleNewsFromJson(json_news, false));
        }

        return result;
    }

    public static DetailNews GetDetailNews(final String newsId) throws IOException, JSONException {
        String URL_String = new String(String.format("https://covid-dashboard.aminer.cn/api/event/%s", newsId));
        String body = GetBodyFromURL(URL_String);

        JSONObject allData;
        allData = new JSONObject(body);
        JSONObject json_news = allData.getJSONObject("data");
        if (json_news!=null)
            return GetDetailNewsFromJson(json_news, false);
        return new DetailNews();
    }

    static DetailNews GetDetailNewsFromJson(JSONObject json_news, boolean from_disk) throws JSONException {
        JSONArray list;
        DetailNews news = new DetailNews();
        news.plain_json = json_news.toString();
//        news.from_disk = from_disk;

        news._id = json_news.optString("_id");
        news.aminer_id = json_news.optString("aminer_id");

        news.authors = new ArrayList<String>();
        list = json_news.optJSONArray("authors");
        if(list!=null)
            for(int i=0;i<list.length();i++){
                JSONObject temp = list.optJSONObject(i);
                String name = temp.optString("name");
                news.authors.add(name);
            }

        news.category = json_news.optString("category");
        news.content = json_news.optString("content");
        news.date = json_news.optString("date");
        news.doi = json_news.optString("doi");

//        news.entities = json_news.optString("entities");

        news.expert = json_news.optString("expert");
        news.geoInfo = json_news.optString("geoInfo");
        news.id = json_news.optString("id");
        news.influence = json_news.optString("influence");
        news.lang = json_news.optString("lang");
        news.pdf = json_news.optString("pdf");

        news.regionIDs = new ArrayList<String>();
        list = json_news.optJSONArray("regionIDs");
        if(list!=null)
            for(int i=0;i<list.length();i++){
                String name = list.get(i).toString();
                news.regionIDs.add(name);
            }
//        news.regionIDs = json_news.optString("regionIDs");

        news.related_events = new ArrayList<String>();
        list = json_news.optJSONArray("related_events");
        if(list!=null)
            for(int i=0;i<list.length();i++){
                JSONObject temp = list.optJSONObject(i);
                String name = temp.optString("id");
                news.related_events.add(name);
            }
//        news.related_events = json_news.optString("related_events");

        news.seg_text = json_news.optString("seg_text");
        news.source = json_news.optString("source");
        news.setTime(json_news.optString("time"));
        news.tflag = json_news.optString("tflag");
        news.title = json_news.optString("title");
        news.type = json_news.optString("type");

        news.urls = new ArrayList<String>();
        list = json_news.optJSONArray("urls");
        if(list!=null)
            for(int i=0;i<list.length();i++){
                String name = list.get(i).toString();
                news.urls.add(name);
            }
//        news.urls = json_news.optString("urls");
        news.year = json_news.optString("year");

        return news;
    }
    private static int keypage=1;

    public static ArrayList<SimpleNews> GetSimpleNewsKeyword(boolean first,String keyword) throws IOException, JSONException {
        if (first)
            keypage=1;
        int size = Manager.I.SEARCH_SIZE;
        int begin_page = keypage;
        ArrayList<SimpleNews> result = new ArrayList<SimpleNews>();
        while (result.size()==0){
            String URL_String = new String(String.format("https://covid-dashboard.aminer.cn/api/events/list?type=%s&page=%d&size=%d","all", keypage, size));
            String body = GetBodyFromURL(URL_String);
            keypage++;
            if(body.equals("")) {
                return new ArrayList<SimpleNews>();
            }
            JSONObject allData = new JSONObject(body);
            JSONArray list = allData.getJSONArray("data");

            for (int t = 0; t < list.length(); t++) {
                JSONObject json_news = list.getJSONObject(t);
                SimpleNews simpleNews = GetSimpleNewsFromJson(json_news, false);
                if (simpleNews.title.contains(keyword) || simpleNews.content.contains(keyword)) {
                    result.add(simpleNews);
                }
            }
            if(keypage - begin_page > Manager.I.MAX_PAGE){
                break;
            }
        }
        return result;
    }

    static ChartData GetChartData(String name) throws IOException, JSONException {
        String URL_String = new String("https://covid-dashboard.aminer.cn/api/dist/epidemic.json");
        String body = GetBodyFromURL(URL_String);

        ChartData chart = new ChartData();
        if (body.equals("")){
            return chart;
        }

        JSONObject allData;
        allData = new JSONObject(body);
        chart.name = name;
        if(chart.domestic.containsKey(name))
            name = chart.domestic.get(name);
        JSONObject country = allData.optJSONObject(name);
//        System.out.println(country);

        if (country==null)
            return chart;

        chart.begin = country.optString("begin");
        JSONArray allList = country.getJSONArray("data");
        for(int i=0;i<allList.length();i++){
            JSONArray list = allList.getJSONArray(i);
            chart.confirmed.add(list.getInt(0));
            chart.cured.add(list.getInt(2));
            chart.dead.add(list.getInt(3));
        }

        return chart;
    }

    static List<KnowledgeGraph> GetKnowledgeGraph(String name) throws IOException, JSONException {
        List<KnowledgeGraph> knowledgeGraph = new ArrayList<KnowledgeGraph>();
        String url = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity="+name;
        String body = GetBodyFromURL(url);
        JSONObject allData = new JSONObject(body);
        JSONArray list = allData.optJSONArray("data");


        if (list!=null){
            for (int i = 0;i<list.length();i++){
                KnowledgeGraph know = new KnowledgeGraph();
                JSONObject oneGraph = list.getJSONObject(i);

                know.label = oneGraph.optString("label");
                know.url = oneGraph.optString("url");
                know.img = oneGraph.optString("img");
                JSONObject info = oneGraph.optJSONObject("abstractInfo");
                know.introduction = info.optString("enwiki")+
                        info.optString("baidu")+
                        info.optString("zhwiki");

                JSONObject covid = info.optJSONObject("COVID");
                know.properties = covid.optJSONObject("properties");
                know.relations = covid.optJSONArray("relations");
                if (know.img.contains("http")){
                    String imgpath = know.img;
                    Bitmap tmpBitmap = null;
                    InputStream is = new java.net.URL(imgpath).openStream();
                    tmpBitmap = BitmapFactory.decodeStream(is);
                    is.close();
//                    know.bitmap = tmpBitmap;
                    Matrix matrix = new Matrix();
                    matrix.setScale(300.0f/(float)tmpBitmap.getWidth(), 300.0f/(float)tmpBitmap.getWidth());
                    if (tmpBitmap.getWidth()>300)
                        know.bitmap = Bitmap.createBitmap(tmpBitmap,0,0,tmpBitmap.getWidth(),tmpBitmap.getHeight(),matrix,true);
                    else
                        know.bitmap=tmpBitmap;                }

                knowledgeGraph.add(know);
            }
        }

        return knowledgeGraph;
    }

    public static boolean isConnected() {
        URL url;
        try {
            url = new URL("https://www.baidu.com/");
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(500);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return true;
        } catch (IOException e) {
        }
        return false;
    }


}
