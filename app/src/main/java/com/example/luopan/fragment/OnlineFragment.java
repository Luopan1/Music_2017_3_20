package com.example.luopan.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luopan.Utils.GlobalConsts;
import com.example.luopan.adapter.MusicAdapter;
import com.example.luopan.application.MyApplication;
import com.example.luopan.entity.Music;
import com.example.luopan.model.LoadMusiclist;
import com.example.luopan.model.MusicListCallback;
import com.example.luopan.music_2017_3_20.R;
import com.example.luopan.service.PlayService.MusicBinder;

import java.util.List;

public class OnlineFragment extends Fragment {
    private ListView listview;
    private MusicAdapter adapter;
    private MusicBinder musicBinder;
    private  List<Music> mMusicList;
    MusicInfoReceiver receiver;

    private static final int REFRESH_COMPLETE = 0X110;
    public static final int REFRESH_ERROR = 0X111;
    private SwipeRefreshLayout mSwipeLayout;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    mMusicList=(List<Music>)msg.obj;
                    adapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        };
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onlinemuisc, null);
        listview = (ListView) view.findViewById(R.id.onlineMuisc);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly);
        listview.setFocusable(true);
        setListener();
        registReceiver();
        LoadMusiclist.getOnlineMusic(new MusicListCallback() {
            @Override
            public void onMusicListLoaded(List<Music> musics) {
                mMusicList=musics;
                setAdapter();
                Log.i("TAG-->MusicList",mMusicList.toString());
            }
        });
        return view;
    }

    private void registReceiver() {
        receiver = new MusicInfoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(OnlineFragment.REFRESH_ERROR+"");
        MyApplication.getApp().registerReceiver(receiver, filter);


    }

    class MusicInfoReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(OnlineFragment.REFRESH_ERROR+"")){
                Toast.makeText(MyApplication.getApp(),"网络连接错误",Toast.LENGTH_SHORT).show();

            }
        }
        }


    //添加监听
    private void setListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Music m=mMusicList.get(position);

                MyApplication app = MyApplication.getApp();
                app.setMusicList(mMusicList);
                app.setPosition(position);
                musicBinder.playMusic(m.getUrl());

                Intent intent=new Intent(GlobalConsts.ACTION_ONLINE_MUSIC);
                MyApplication.getApp().sendBroadcast(intent);

                //把当前播放列表和position存入Application

//                Intent mintent=new Intent(MyApplication.getApp(), SecondActivity.class);
//                startActivity(mintent);
            }
        });


        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {


                LoadMusiclist.getOnlineMusic(new MusicListCallback() {
                    @Override
                    public void onMusicListLoaded(List<Music> musics) {
                            mMusicList=musics;
                            Message msg= mHandler.obtainMessage();
                            msg.what=REFRESH_COMPLETE;
                            msg.obj=mMusicList;
                            mHandler.sendMessage(msg);
                    }
                });

            }
        });


    }


    //设置适配器
    public void setAdapter() {
        //自定义Adapter   给listView设置适配器
        adapter = new MusicAdapter(getActivity(), mMusicList, listview);
        listview.setAdapter(adapter);

    }

    /**
     * 当承载当前Fragment的Activity执行onDestory
     * 时也会级联执行Fragment.onDestroy()方法
     */
    public void onDestroy() {
        super.onDestroy();
        adapter.stopThread();
    }

    public void setBinder(MusicBinder musicBinder) {
        this.musicBinder = musicBinder;
        Log.i("TAG->onlineFragment", "musicBinder" + musicBinder.toString());
    }


}
