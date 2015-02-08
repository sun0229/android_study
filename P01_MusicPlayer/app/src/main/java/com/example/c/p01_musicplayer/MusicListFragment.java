package com.example.c.p01_musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c on 2015-02-01.
 */
public class MusicListFragment extends ListFragment {
    private ArrayList<Music> mMusic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String path = Environment.getExternalStorageDirectory().toString();
        path += "/Samsung/Music";

        File f = new File(path);
        File[] files = f.listFiles();

        //String[] list = new String[files.length];
        ArrayList<File> fileList = new ArrayList<File>();

        for (int i=0;i<files.length;i++){
            //list[i] = files[i].getName();
            fileList.add(files[i]);
        }

        MyAdapter adapter = new MyAdapter(fileList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        ListView listview = (ListView) v.findViewById(R.id.listView);



        return v;
    }

    private class MyAdapter extends ArrayAdapter<File>{

        public MyAdapter(List<File> objects) {
            super(getActivity(), 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.activity_list,null);
            }

            File m = getItem(position);


            return convertView;
        }
    }
}
