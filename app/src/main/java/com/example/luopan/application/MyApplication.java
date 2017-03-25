package com.example.luopan.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.luopan.entity.Music;

import java.util.List;


public class MyApplication extends Application{
	private List<Music> musics;
	private int position;
	private static MyApplication app;
	private static RequestQueue mRequest;

	private static boolean isPlaying;

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		mRequest= Volley.newRequestQueue(app);
	}

	public static MyApplication getApp(){
		return app;
	}

	public void setMusicList(List<Music> musics){
		this.musics = musics;
	}

	public void setPosition(int position){
		this.position = position;
	}

	/**
	 * 获取当前正在播放的音乐
	 * @return
	 */
	public Music getCurrentMusic(){
		return musics.get(position);
	}

	/**
	 * 切换到上一首 并且返回歌曲对象
	 * @return
	 */
	public Music preMusic(){
		position = position ==0 ?  0 : position - 1;
		return getCurrentMusic();
	}

	/**
	 * 切换到下一首  并且返回当前需要播放的Music
	 * @return
	 */
	public Music nextMusic(){
		position = position == musics.size()-1 ? 0 : position+1;
		return getCurrentMusic();
	}
	public static RequestQueue getRequestQueue(){
		return mRequest;
	}

	public static boolean setIsPlaying(boolean isPlaying){
		return isPlaying;
	}

	public static boolean getIsPlaying(){
		return  isPlaying;
	}

}


