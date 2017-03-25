package com.example.luopan.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.luopan.Utils.GetMusicLists;
import com.example.luopan.Utils.GlobalConsts;
import com.example.luopan.adapter.MyAdapter;
import com.example.luopan.application.MyApplication;
import com.example.luopan.entity.Music;
import com.example.luopan.music_2017_3_20.R;
import com.example.luopan.service.PlayService.MusicBinder;

import java.util.List;

public class LocalFragment extends Fragment  {
	private List<Music> musics;
	private ListView listView;
	private MyAdapter adapter;
	private MusicBinder musicBinder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.localmusic, null);
		listView=(ListView) view.findViewById(R.id.localMusic);
		//添加监听
		musics= GetMusicLists.getAllMusic();
		setAdapter();
		setListeners();
		return view;
	}

	//添加监听
	private void setListeners() {



		//item点击事件
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final Music m=musics.get(position);

				//把当前播放列表和position存入Application
				MyApplication app = MyApplication.getApp();
				app.setMusicList(musics);
				app.setPosition(position);
				musicBinder.playMusic(m.getUrl());


				Intent intent=new Intent(GlobalConsts.ACTION_LOCAL_MUSIC);
				MyApplication.getApp().sendBroadcast(intent);

//
//				Intent mintent=new Intent(MyApplication.getApp(), SecondActivity.class);
//				startActivity(mintent);
			}
		});

	}

	//设置适配器
	public void setAdapter(){
		//自定义Adapter   给listView设置适配器
		 final int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取当前应用程序所分配的最大内存
		 final int cacheSize = maxMemory /2;

		LruCache<String,Bitmap> cache=new LruCache<>(cacheSize);
		adapter = new MyAdapter(getActivity(), musics,cache);
		listView.setAdapter(adapter);

	}

	/**
	 * 当承载当前Fragment的Activity执行onDestory
	 * 时也会级联执行Fragment.onDestroy()方法
	 */

	public void setBinder(MusicBinder musicBinder) {
		this.musicBinder = musicBinder;
	}


}
