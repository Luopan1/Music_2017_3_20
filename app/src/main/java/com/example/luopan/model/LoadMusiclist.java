package com.example.luopan.model;

import android.content.Intent;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.luopan.Utils.UrlFactory;
import com.example.luopan.application.MyApplication;
import com.example.luopan.entity.Music;
import com.example.luopan.entity.Pagebean;
import com.example.luopan.entity.Root;
import com.example.luopan.entity.Showapi_res_body;
import com.example.luopan.fragment.OnlineFragment;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by LuoPan on 2017/3/24 10:29.
 */
public class LoadMusiclist {

    public static void getOnlineMusic(final MusicListCallback callback) {
        RequestQueue requestQueue = MyApplication.getRequestQueue();
        String path = UrlFactory.getOnLineMusicUrl();
        Log.i("TAG",path);

        try {
            StringRequest request = new StringRequest(Request.Method.GET, path, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {

                            Log.i("TAG-->result",s.toString());

                            try {
                                Gson gson = new Gson();
                                Root root = gson.fromJson(s, Root.class);
                                Showapi_res_body body=root.getShowapi_res_body();
                                Pagebean bean= body.getPagebean();
                                List<Music>  lists=bean.getSonglist();

                                callback.onMusicListLoaded(lists);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Intent intent =new Intent(OnlineFragment.REFRESH_ERROR+"");
                    MyApplication.getApp().sendBroadcast(intent);
                }
            });
            requestQueue.add(request);
            request.setRetryPolicy(new DefaultRetryPolicy(5000,2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception e) {
            Intent intent =new Intent(OnlineFragment.REFRESH_ERROR+"");
            MyApplication.getApp().sendBroadcast(intent);

        }

    }


    public static String getLrc( String  musicName){
        return null;
    }
}
