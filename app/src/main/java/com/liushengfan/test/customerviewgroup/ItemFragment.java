package com.liushengfan.test.customerviewgroup;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.liushengfan.test.customerviewgroup.view.ViewGroupScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liushengfan on 15/11/18.
 */
public class ItemFragment extends BaseUserHomeFragment{
    List<String> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
        listview = (ListView) root;
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        datas = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            datas.add("test" + i);
        }
        ArrayAdapter mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_list_item, datas);
        listview.setAdapter(mAdapter);
    }
}
