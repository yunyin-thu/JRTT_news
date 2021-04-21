package com.java.yanlu.dataprocess;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private SQLiteDatabase db;
    private String db_path;


    private static final String TABLE_NAME_DETAIL = "news_detail";
    private static final String KEY_ID = "news_id"; // string
    private static final String KEY_DETAIL = "detail_json"; // text

    FileStorage(Context context) throws IOException {
        db_path = context.getFilesDir().getPath() + "/data.db";
        db = SQLiteDatabase.openOrCreateDatabase(db_path, null);


        // dropTables();
        createTables();
    }

    private void createTables() {
        final String detail_table = String.format("CREATE TABLE IF NOT EXISTS `%s`(%s string primary key, %s text)",
                TABLE_NAME_DETAIL, KEY_ID, KEY_DETAIL);
        db.execSQL(detail_table);
    }
    void dropTables() {
        db.execSQL(String.format("DROP TABLE IF EXISTS `%s`", TABLE_NAME_DETAIL));
        final String detail_table = String.format("CREATE TABLE IF NOT EXISTS `%s`(%s string primary key, %s text)",
                TABLE_NAME_DETAIL, KEY_ID, KEY_DETAIL);
        db.execSQL(detail_table);
    }

    void insertDetail(SimpleNews simpleNews) {
        String cmd = String.format("INSERT OR REPLACE INTO `%s`(%s, %s) VALUES(%s, %s)",
                TABLE_NAME_DETAIL, KEY_ID, KEY_DETAIL,
                DatabaseUtils.sqlEscapeString(simpleNews._id),
                DatabaseUtils.sqlEscapeString(simpleNews.plain_json));
        db.execSQL(cmd);
    }

    DetailNews fetchDetail(String news_ID) throws JSONException {
        String cmd = String.format("SELECT * FROM `%s` WHERE %s=%s",
                TABLE_NAME_DETAIL, KEY_ID, DatabaseUtils.sqlEscapeString(news_ID));
        Cursor cursor = db.rawQuery(cmd, null);
        DetailNews detailNews = null;
        if (cursor.moveToFirst()) {
            detailNews = API.GetDetailNewsFromJson(new JSONObject(cursor.getString(cursor.getColumnIndex(KEY_DETAIL))), true);
        }
        cursor.close();
        return detailNews;
    }

    List<SimpleNews> fetchRead() throws JSONException {
        String cmd = String.format("SELECT * FROM `%s`",
                TABLE_NAME_DETAIL);
        Cursor cursor = db.rawQuery(cmd, null);
        List<SimpleNews> results = new ArrayList<SimpleNews>();
        while(cursor.moveToNext()) {
            results.add(API.GetSimpleNewsFromJson(new JSONObject(cursor.getString(cursor.getColumnIndex(KEY_DETAIL))), true));
        }
        cursor.close();
        return results;
    }


}

