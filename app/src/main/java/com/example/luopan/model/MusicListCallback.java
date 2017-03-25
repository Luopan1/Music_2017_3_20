package com.example.luopan.model;

/**
 * Created by LuoPan on 2017/3/24 10:28.
 */

import com.example.luopan.entity.Music;

import java.util.List;

/**
 * 音乐列表的回调接口
 *  当音乐加载完毕后执行该回调方法
 */
public interface MusicListCallback {
    /**
     * 当音乐加载完毕后执行该回调方法
     */
    public void onMusicListLoaded(List<Music> musics);

}