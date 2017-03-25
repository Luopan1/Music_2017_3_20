package com.example.luopan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luopan.Utils.BitmapCallback;
import com.example.luopan.Utils.MediaUtil;
import com.example.luopan.application.MyApplication;
import com.example.luopan.entity.Music;
import com.example.luopan.music_2017_3_20.R;

import java.util.List;


/**
 * Created by 19233 on 2016/10/17.
 */

public class MyAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context context;
    private static List<Music> musics = null;
    LruCache<String,Bitmap> cache;
    Bitmap filebitmap;

    public MyAdapter(Context context, List<Music> musics,LruCache<String,Bitmap> cache) {
        super();
        this.context = context;
        this.musics = musics;
        this.cache=cache;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Music music = musics.get(position);
     final  ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_music, null);
            holder.tvPath = (TextView) convertView.findViewById(R.id.tv_music_item_title);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_music_item_path);
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvPath.setText(music.getSongname());
        holder.tvTitle.setText(music.getSingername());


        //TODO  每一次滑动listview时 都得走getView方法 所以这个方法肯定也得走 然后每次都去加载一次图片给缓存设置 同时还每次都要从缓存中读取  导致了图片的跳动
        MediaUtil.getArtwork(MyApplication.getApp(), music.getSongid(), Long.parseLong(music.getAlbummid()), true, true, new BitmapCallback() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap) {
                filebitmap=bitmap;
                if(cache.get(music.getSongname())==null){
                    Log.i("TAG-->adapter","第一次加载");
                    cache.put(music.getSongname(),filebitmap);
                    holder.iv_photo.setImageBitmap(bitmap);
                }else {
                    Log.i("TAG-->adapter","第二次加载");
                    holder.iv_photo.setImageBitmap(cache.get(music.getSongname()));
                }
            }
        });
        //TODO  打开应用的第一次 不滑动listview  不加载图片  需要解决 原因MediaUtil.getArtwork()是后台任务  不知道何时才能走回调
        //TODO  讲图片缓存到cache中 而我直接就开始给控件设置了从缓存中获取图片了
//            holder.iv_photo.setImageBitmap(cache.get(music.getSongname()));

        /**
         * 解决办法 新建一个方法  讲控件、bitmap和歌手名字传递进去 在这个方法里面建立缓存 讲图片给缓存进去 同时设置一个方法判断缓存的bitmap是否为空  如果为空 则给缓存加入图片 如果不
         * 为空 就不给缓存加入图片 直接从缓存中读取图片给控件
         *
         * */





        return convertView;
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Object getItem(int position) {
        // Auto-generated method stub
        return musics.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Auto-generated method stub
        return position;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    class ViewHolder {
        TextView tvPath;
        ImageView iv_photo;
        TextView tvTitle;
    }


}
