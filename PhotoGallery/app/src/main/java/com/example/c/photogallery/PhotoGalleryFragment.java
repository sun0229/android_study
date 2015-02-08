package com.example.c.photogallery;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by c on 2015-02-08.
 */
public class PhotoGalleryFragment extends Fragment {
    private static final String TAG ="PhotoGalleryFragment >>>>>>>> ";

    GridView mGridView;
    ArrayList<GalleryItem> mItems;
    ThumbnailDownloader<ImageView> mThumbnailDownloader;

    class FetchItemTask extends AsyncTask<Void, Void, ArrayList<GalleryItem>> {

        @Override
        protected ArrayList<GalleryItem> doInBackground(Void... params) {
            return new FlickrFetchr().fetchItems();
            //Log.i("phtoGallery", "contents:"+result);
        }

        protected void onPostExecute(ArrayList<GalleryItem> galleryItems){
            mItems = galleryItems;
            setupAdapter();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);    // 회전에 관련된..
        new FetchItemTask().execute();

        mThumbnailDownloader = new ThumbnailDownloader<ImageView>(new Handler());
        mThumbnailDownloader.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            @Override
            public void onThumbnameDownloaded(ImageView imageView, Bitmap thumbnail) {
                if (isVisible()){
                    imageView.setImageBitmap(thumbnail);
                }
            }
        });

        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();   // handler 가동
        Log.i(TAG, "Background Thread started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.quit();    // 핸들러스레드는 반드시 외부에서 종료시켜줘야 함.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mGridView = (GridView) v.findViewById(R.id.gridView);
        setupAdapter();

        return v;
    }

    void setupAdapter(){
        if (getActivity() == null || mGridView == null)
            return;

        if (mItems != null){
            //mGridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(), android.R.layout.simple_gallery_item, mItems));
            mGridView.setAdapter(new GalleryItemAdapter(mItems));
        }else{
            mGridView.setAdapter(null);
        }
    }

    private class GalleryItemAdapter extends ArrayAdapter<GalleryItem>{

        public GalleryItemAdapter(ArrayList<GalleryItem> items) {
            super(getActivity(),0,items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.gallery_item, parent, false);
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.gallery_item_imageView);
            //imageView.setImageResource(R.drawable.placeholder); // 이미지가 바뀌기 전 기본 이미지 먼저 설정 해 준다.

            GalleryItem item = getItem(position);
            mThumbnailDownloader.queueThumbnail(imageView, item.getUrl());

            return convertView;
        }
    }
}













