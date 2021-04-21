package com.java.yanlu.viewprocess.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    List<SimpleNews> search_news_list = new ArrayList<>();
    SearchListAdapter search_adapter = new SearchListAdapter(getActivity());
    String search_string = "";
    boolean flag = false;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final EditText editText = getActivity().findViewById(R.id.search_edit_text_news);
        final Button button = (Button) getActivity().findViewById(R.id.search_button_news);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_string = editText.getText().toString();
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        search_news_list = Manager.I.fetchKeywordSimpleNews(true,search_string);
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        search_adapter.notifyDataSetChanged();
                    }
                });
                //从fragment跳转到activity中
            }
        });
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search,container,false);
        final ListView history_list = root.findViewById(R.id.search_list);
        history_list.setAdapter(search_adapter);
        history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();

                SimpleNews simpleNews = search_news_list.get(position);
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

        RefreshLayout refreshLayout = (RefreshLayout)root.findViewById(R.id.refreshLayout_search);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        List<SimpleNews> simpleNews=null;
                        simpleNews = Manager.I.fetchKeywordSimpleNews(false,search_string);
                        flag = (simpleNews.size() == 0);
                        search_news_list.addAll(simpleNews);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                search_adapter.notifyDataSetChanged();
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
                if(flag){
                    refreshlayout.finishLoadMore(false);
                }
                else{
                    refreshlayout.finishLoadMore(0/*,false*/);//传入false表示加载失败
                }
            }
        });
        return root;
    }
    public class SearchListAdapter extends BaseAdapter {
        Context context;
        public SearchListAdapter(Context context){
            this.context = context;
        }


        @Override
        public int getCount(){
            return search_news_list.size();
        }

        @Override
        public Object getItem(int position){
            return search_news_list.get(position);
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