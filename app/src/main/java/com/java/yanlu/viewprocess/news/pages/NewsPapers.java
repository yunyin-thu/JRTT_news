package com.java.yanlu.viewprocess.news.pages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class NewsPapers extends Fragment {
    List<SimpleNews> list_data = new ArrayList<>();
    View root = null;
    int last_page = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        if(root != null){
            ViewGroup parent = (ViewGroup) root.getParent();
            if(parent != null){
                parent.removeView(root);
            }
            return root;
        }

        root = inflater.inflate(R.layout.fragment_news_list,container,false);
        final ListView news_list = root.findViewById(R.id.news_list);
        final NewsListAdapter myadapter = new NewsListAdapter(getActivity());
        news_list.setAdapter(myadapter);
        news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();

                SimpleNews simpleNews = list_data.get(position);
                simpleNews.already_read = true;
                list_data.set(position,simpleNews);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myadapter.notifyDataSetChanged();
                    }
                });
                Manager.I.restore(simpleNews);

                System.out.println(simpleNews.content.equals(""));
                bundle.putString("title",simpleNews.title);
                bundle.putString("time",simpleNews.getTime());
                bundle.putString("source",simpleNews.getAuthors());
                bundle.putString("content",simpleNews.content);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
        RefreshLayout refreshLayout = (RefreshLayout)root.findViewById(R.id.refreshLayout_news);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                last_page = 1;
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        List<SimpleNews> simpleNews=null;
                        // simpleNews = Manager.I.fetchSimpleNews("all",2,Manager.I.SIZE);
                        simpleNews = Manager.I.fetchSimpleNews("paper",1,Manager.I.SIZE);
                        list_data = null;
                        list_data = simpleNews;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myadapter.notifyDataSetChanged();
                            }
                        });
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshlayout.finishRefresh(0/*,false*/);//传入false表示刷新失败

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        List<SimpleNews> simpleNews=null;
                        // simpleNews = Manager.I.fetchSimpleNews("all",2,Manager.I.SIZE);
                        simpleNews = Manager.I.fetchSimpleNews("paper",++ last_page,Manager.I.SIZE);
                        list_data.addAll(simpleNews);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myadapter.notifyDataSetChanged();
                            }
                        });
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshlayout.finishLoadMore(0/*,false*/);//传入false表示加载失败
            }
        });


        if(list_data.isEmpty()){
            Thread thread = new Thread(){
                @Override
                public void run() {
                    List<SimpleNews> simpleNews=null;
                    simpleNews = Manager.I.fetchSimpleNews("paper",1,Manager.I.SIZE);

                    list_data=simpleNews;


                }
            };
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return root;
    }

    public class NewsListAdapter extends BaseAdapter {
        Context context;
        public NewsListAdapter(Context context){
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
            if(convertview == null){
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
            title_view.setTextColor(Color.BLACK);
            source_view.setTextColor(Color.BLACK);
            time_view.setTextColor(Color.BLACK);
            if(simpleNews.already_read){
                title_view.setTextColor(Color.GRAY);
                source_view.setTextColor(Color.GRAY);
                time_view.setTextColor(Color.GRAY);
            }
            title_view.setText(title);
            source_view.setText(source);
            time_view.setText(time);
            return convertview;
        }
    }
}
