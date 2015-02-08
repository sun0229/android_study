package com.example.c.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by c on 2015-02-08.
 */
// Token <- ImageView로 사용
public class ThumbnailDownloader<Token> extends HandlerThread {
    private static final String TAG ="ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());  // 동시 접근시 처리
    Handler mHandler;
    Handler mResponseHandler;   //photoGalleryFragment
    Listener<Token> mListener;

    public interface Listener<Token>{
        void onThumbnameDownloaded(Token token, Bitmap thumbnail);
    }

    public void setListener(Listener<Token> listener){
        mListener = listener;
    }

    public ThumbnailDownloader(Handler handler){
        super(TAG);
        mResponseHandler = handler;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MESSAGE_DOWNLOAD){
                    Token token = (Token) msg.obj;
                    handleRequest(token);
                }
            }
        };
    }

    public void queueThumbnail(Token token, String url){
        requestMap.put(token, url);
        mHandler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
    }

    private void handleRequest(final Token token){
        final String url = requestMap.get(token);
        if (url == null)
            return;

        try {
            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (requestMap.get(token) != url){
                        return;
                    }

                    requestMap.remove(token);
                    mListener.onThumbnameDownloaded(token, bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clearQueue(){
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }
}











