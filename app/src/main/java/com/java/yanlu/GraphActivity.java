package com.java.yanlu;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Bundle bundle = getIntent().getExtras();
        String label = bundle.getString("label");
        Bitmap bitmap = bundle.getParcelable("bitmap");
        String introduction = bundle.getString("introduction");
        String properties = bundle.getString("properties");
        String relations = bundle.getString("relations");
        String total = properties + "\n"+ relations;
        TextView label_view = (TextView) findViewById(R.id.label_graph);
        ImageView image_view = (ImageView) findViewById(R.id.image_graph);
        TextView introduction_view = (TextView) findViewById(R.id.introduction_graph);
        TextView properties_view = (TextView) findViewById(R.id.properties_graph);
        label_view.setText(label);
        label_view.setTextColor(Color.BLACK);
        if(bitmap != null)
            image_view.setImageBitmap(bitmap);
        introduction_view.setText(introduction);
        introduction_view.setTextColor(Color.BLACK);
        properties_view.setText(total);
        properties_view.setTextColor(Color.BLACK);
    }
}