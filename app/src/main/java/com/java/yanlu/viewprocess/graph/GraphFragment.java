package com.java.yanlu.viewprocess.graph;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.java.yanlu.GraphActivity;
import com.java.yanlu.R;
import com.java.yanlu.dataprocess.KnowledgeGraph;
import com.java.yanlu.dataprocess.Manager;

import java.util.ArrayList;
import java.util.List;

public class GraphFragment extends Fragment {
    List<KnowledgeGraph> graph_list = new ArrayList<>();
    GraphFragment.GraphListAdapter graphadapter = new GraphFragment.GraphListAdapter(getActivity());
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final EditText editText = getActivity().findViewById(R.id.search_text_graph);
        final Button button = (Button) getActivity().findViewById(R.id.search_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String search_string = editText.getText().toString();
                System.out.println(search_string);
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        graph_list = Manager.I.fetchKnowledgeGraph(search_string);
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
                        graphadapter.notifyDataSetChanged();
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
        View root = inflater.inflate(R.layout.fragment_graph, container, false);
        final ListView list_view_search_graph = root.findViewById(R.id.list_view_search_graph);
        list_view_search_graph.setAdapter(graphadapter);
        list_view_search_graph.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();
                String label = graph_list.get(position).getLabel();
                Bitmap bitmap = graph_list.get(position).getBitmap();
                String introduction = graph_list.get(position).getIntroduction();
                String properties = graph_list.get(position).getProperties();
                String relations = graph_list.get(position).getRelations();
                bundle.putString("label",label);
                bundle.putParcelable("bitmap",bitmap);
                bundle.putString("introduction",introduction);
                bundle.putString("properties",properties);
                bundle.putString("relations",relations);

                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(getActivity(), GraphActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }
    public class GraphListAdapter extends BaseAdapter {
        Context context;
        public GraphListAdapter(Context context){
            this.context = context;
        }


        @Override
        public int getCount(){
            return graph_list.size();
        }

        @Override
        public Object getItem(int position){
            return graph_list.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position,View convertview, ViewGroup parent){
            if(convertview == null){//item公用
                convertview = getLayoutInflater().inflate(R.layout.fragment_graph_item,parent,false);
            }
            String name = ((KnowledgeGraph) getItem(position)).label;
            TextView textView = (TextView)convertview.findViewById(R.id.name_in_graph_item);
            textView.setText(name);
            return convertview;
        }
    }
}