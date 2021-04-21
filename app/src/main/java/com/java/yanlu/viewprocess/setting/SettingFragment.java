package com.java.yanlu.viewprocess.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.java.yanlu.R;
import com.java.yanlu.dataprocess.Manager;

public class SettingFragment extends Fragment {
//    CheckBox news_box;
//    CheckBox paper_box;
//    CheckBox event_box;
//    CheckBox point_box;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        Button button = root.findViewById(R.id.clear_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manager.I.Delete();
            }
        });
//        news_box = root.findViewById(R.id.news_setting_box);
//        paper_box = root.findViewById(R.id.paper_setting_box);
//        event_box = root.findViewById(R.id.event_setting_box);
//        point_box = root.findViewById(R.id.point_setting_box);
//        news_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean if_checked) {
//                Manager.I.checkedArray[1] = if_checked;
//            }
//        });
//        paper_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean if_checked) {
//                Manager.I.checkedArray[2] = if_checked;
//            }
//        });
//        event_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean if_checked) {
//                Manager.I.checkedArray[3] = if_checked;
//            }
//        });
//        point_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean if_checked) {
//                Manager.I.checkedArray[4] = if_checked;
//            }
//        });
        return root;
    }
}