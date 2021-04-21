package com.java.yanlu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener{
    String title;
    String time;
    String source;
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        time = bundle.getString("time");
        source = bundle.getString("source");
        content = bundle.getString("content");
        TextView ttitle = (TextView) findViewById(R.id.title);
        TextView ttime = (TextView) findViewById(R.id.time);;
        TextView tsource = (TextView) findViewById(R.id.source);;
        TextView tcontent = (TextView) findViewById(R.id.content);
        ttitle.setTextColor(Color.BLACK);
        ttitle.setText(title);
        ttime.setTextColor(Color.GRAY);
        ttime.setText(time);
        tsource.setTextColor(Color.GRAY);
        tsource.setText(source);
        tcontent.setTextColor(Color.BLACK);
        tcontent.setText(content);
        Button button = (Button)findViewById(R.id.share_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "来自今日偷桃的新闻：\n"+title +" \n " +content);
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        shareIntent = Intent.createChooser(shareIntent, "分享文章到");
        startActivity(shareIntent);
    }
}