package com.java.yanlu.viewprocess.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.yanlu.NewsActivity;
import com.java.yanlu.R;
import com.java.yanlu.dataprocess.Manager;
import com.java.yanlu.dataprocess.SimpleNews;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    List<SimpleNews> list_data = new ArrayList<>();
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history,container,false);
        final ListView history_list = root.findViewById(R.id.history_list);
        HistoryFragment.HistoryListAdapter myadapter = new HistoryFragment.HistoryListAdapter(getActivity());
        history_list.setAdapter(myadapter);
        history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();

                SimpleNews simpleNews = list_data.get(position);
                Manager.I.restore(simpleNews);

                bundle.putString("title",simpleNews.getTitle());
                bundle.putString("time",simpleNews.getTime());
                bundle.putString("source",simpleNews.getAuthors());
                bundle.putString("content",simpleNews.getContent());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
        Thread thread = new Thread(){
            @Override
            public void run() {
                List<SimpleNews> simpleNews=null;
                simpleNews = Manager.I.fetchHistorySimpleNews();
                System.out.println(simpleNews.size());

                for (int i = simpleNews.size() - 1; i >= 0; i--) {
                    list_data.add(simpleNews.get(i));
                }

            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return root;
    }
    public class HistoryListAdapter extends BaseAdapter {
        Context context;
        public HistoryListAdapter(Context context){
            this.context = context;
        }


        @Override
        public int getCount(){
            return list_data.size();
        }

        @Override
        public Object getItem(int position){
            return list_data.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position,View convertview, ViewGroup parent){
            if(convertview == null){//item公用
                convertview = getLayoutInflater().inflate(R.layout.fragment_news_item,parent,false);
            }
            SimpleNews simpleNews = (SimpleNews) getItem(position);
            String title = simpleNews.getTitle();
            if(title.length()>40){
                title = title.substring(0,40)+"...";
            }
            String source = simpleNews.getSource();
            String time = simpleNews.getTime();
            TextView title_view = (TextView)convertview.findViewById(R.id.title_in_news_item);
            TextView source_view = (TextView)convertview.findViewById(R.id.source_in_news_item);
            TextView time_view = (TextView)convertview.findViewById(R.id.time_in_news_item);
            title_view.setText(title);
            source_view.setText(source);
            time_view.setText(time);
            return convertview;
        }
    }
}