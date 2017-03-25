package com.example.luopan.core;

public interface IMusicPlayer {
	/**
	 * 播放歌曲
	 */
	void play();

	/**
	 * 暂停播放
	 */
	void pause();

	/**
	 * 播放上一首
	 */
	void previous();

	/**
	 * 播放下一首
	 */
	void next();

	/**
	 * 播放指定的歌曲
	 *
	 * @param position
	 *            歌曲在列表中的索引位置
	 */
	void play(int position);

	/**
	 * 判断当前是否正在播放
	 *
	 * @return 返回true则表示正在播放，返回false则表示没有在播放
	 */
	boolean isPlaying();

	/**
	 * 播放指定的歌曲
	 *
	 * @param path
	 *            歌曲的路径
	 */
	void onLinePlay(String path);


	/**
	 * 获取当前播放到的位置
	 *
	 * @return 当前播放到的位置，值为播放到的时间的毫秒数
	 */
	int getCurrentPosition();

	/**
	 * 获取当前播放的歌曲的总时长
	 *
	 * @return 当前播放的歌曲的总时长，单位为毫秒
	 */
	int getDuration();

	/**
	 * 获取当前播放的歌曲的索引
	 *
	 * @return 当前播放的歌曲的索引
	 */
	int getCurrentMusicIndex();

	/***
	 *快进到指定位置播放
	 *
	 *
	 */
    void seekTo(int position);
	/***
	 *设置当前音乐播放的索引
	 *
	 *
	 */
	void setCurrentMusicIndex(int positon);
}
