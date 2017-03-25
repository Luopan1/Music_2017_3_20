package com.example.luopan.fragment;

import com.example.luopan.music_2017_3_20.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FavoriteFragment extends Fragment {
private ListView listview;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view=inflater.inflate(R.layout.favoritemuisc, null);
//	listview = (ListView) view.findViewById(R.id.favoriteMuisc);
	return view;
}
}
