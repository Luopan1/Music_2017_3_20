package com.example.luopan.Utils;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.luopan.application.MyApplication;
import com.example.luopan.entity.Music;

import java.util.ArrayList;
import java.util.List;

public class GetMusicLists {
	 public static List<Music> getAllMusic(){
	        List<Music> musics= null;
	        musics = new ArrayList<Music>();
	        Music music = null;
	        try {
	        	 ContentResolver cr= MyApplication.getApp().getContentResolver();
				Log.i("TAG-->ContentResolver",cr.toString());
	             Cursor c= cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
	             if(c!= null&&c.getCount()>0){
	                 while (c.moveToNext()){
	                     music = new Music();
	                     music.setSongname( c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE)));
	                     music.setUrl( c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA)));
						 music.setSingername(c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
						 music.setAlbummid(c.getString(c.getColumnIndex( MediaStore.Audio.Media.ALBUM_ID)));
						 music.setSongid(Integer.parseInt(c.getString(c.getColumnIndex(MediaStore.Audio.Media._ID))));
						 music.setAlbumpic_small(c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA)));

	                  long time=  c.getLong(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
	                  if(time>2*60*1000){
	                	  musics.add(music);
	                  }
	                     
	                 }

	             }else{

	             }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }

			
	        return musics;
	    }
}
