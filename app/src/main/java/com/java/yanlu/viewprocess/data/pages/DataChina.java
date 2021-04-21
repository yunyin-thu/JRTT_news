package com.java.yanlu.viewprocess.data.pages;

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

import com.java.yanlu.DataActivity;
import com.java.yanlu.R;
import com.java.yanlu.dataprocess.ChartData;

import java.util.List;

public class DataChina extends Fragment {
    List<String> list_data = ChartData.domestic_zh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        System.out.println("yunyinlll");
        View root = inflater.inflate(R.layout.fragment_data_list,container,false);
        final ListView news_list = root.findViewById(R.id.data_list);
        final DataListAdapter myadapter = new DataListAdapter(getActivity(),list_data);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myadapter.notifyDataSetChanged();
            }
        });
        news_list.setAdapter(myadapter);
        news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println(list_data.size());
                Bundle bundle = new Bundle();
                bundle.putString("area",list_data.get(position));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(getActivity(), DataActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
    public class DataListAdapter extends BaseAdapter {
        Context context;
        List<String> list;
        public DataListAdapter(Context context, List<String> list){
            this.context = context;
            this.list = list;
        }


        @Override
        public int getCount(){
            return list.size();
        }

        @Override
        public Object getItem(int position){
            return list.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position,View convertview, ViewGroup parent){
            if(convertview == null){
                convertview = getLayoutInflater().inflate(R.layout.fragment_data_item,parent,false);
            }
            String str = (String)getItem(position);
            TextView textView = (TextView)convertview.findViewById(R.id.area_in_data_item);
            textView.setText(str);
            return convertview;
        }
    }
}
