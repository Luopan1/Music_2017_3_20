package com.example.luopan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luopan.Utils.ImageLoader;
import com.example.luopan.entity.Music;
import com.example.luopan.music_2017_3_20.R;

import java.util.List;

public class MusicAdapter extends BaseAdapter {
	private Context context;
	private List<Music> musics;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	
	
	public MusicAdapter(Context context, List<Music> musics, ListView listView) {
		this.context = context;
		this.musics = musics;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = new ImageLoader(context, listView);
	}


	@Override
	public int getCount() {
		return musics.size();
	}

	@Override
	public Music getItem(int position) {
		return musics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_music, null);
			holder = new ViewHolder();
			holder.tvPath = (TextView) convertView.findViewById(R.id.tv_music_item_title);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_music_item_path);
			holder.iv_photo=(ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		Music m = getItem(position);
		holder.tvTitle.setText(m.getSongname());
		holder.tvPath.setText(m.getSingername());
		imageLoader.displayImage(m.getAlbumpic_small(), holder.iv_photo);
		
		
		return convertView;
	}

	public void stopThread(){
		imageLoader.stopThread();
	}

	class ViewHolder {
		TextView tvPath;
		ImageView iv_photo;
		TextView tvTitle;
	}


}
