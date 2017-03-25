package com.example.luopan.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.example.luopan.music_2017_3_20.R;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageLoader {
	private HashMap<String, SoftReference<Bitmap>> cache = new HashMap<String, SoftReference<Bitmap>>();
	private Context context;
	private List<ImageLoadTask> tasks = new ArrayList<ImageLoadTask>();
	private Thread workThread;
	private boolean isLoop = true;
	private AbsListView listView;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_LOAD_IMAGE_SUCCESS:
				ImageLoadTask task = (ImageLoadTask) msg.obj;
				Bitmap b = task.bitmap;
				ImageView imageView=(ImageView) listView.findViewWithTag(task.path);
				if(imageView!=null){
					if(b!=null){
						imageView.setImageBitmap(b);
					}else{
						imageView.setImageResource(R.mipmap.ic_launcher);
					}
				}
				break;
			}
		}
	};
	public static final int HANDLER_LOAD_IMAGE_SUCCESS=1;

	
	public ImageLoader(Context context, AbsListView listView) {
		this.context = context;
		this.listView = listView;
		workThread = new Thread() {
			public void run() {
				while (isLoop) {
					if (!tasks.isEmpty()) {
						ImageLoadTask task = tasks.remove(0);
						Bitmap bitmap = loadBitmap(task.path);
						task.bitmap = bitmap;
						Message msg = new Message();
						msg.what = HANDLER_LOAD_IMAGE_SUCCESS;
						msg.obj = task;
						handler.sendMessage(msg);
					} else {
						try {
							synchronized (workThread) {
								workThread.wait();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 

					}
				}
			}
		};
		workThread.start();
		
	}
	
	public Bitmap loadBitmap(String path) {
		try {
			InputStream is = HttpUtils.getInputStream(path);
			Bitmap b = BitmapUtils.loadBitmap(is, 50, 50);

			cache.put(path, new SoftReference<Bitmap>(b));

			String filename = path.substring(path.lastIndexOf("/")+1);
			File file = new File(context.getCacheDir(), "images/"+filename);
			BitmapUtils.save(b, file);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	public void displayImage(String picPath, ImageView ivAlbum) {
		ivAlbum.setTag(picPath);
		SoftReference<Bitmap> ref=cache.get(picPath);
		if(ref != null){
			Bitmap b = ref.get();  
			if(b!=null){
				ivAlbum.setImageBitmap(b);
				return;
			}
		}
		
		String filename = picPath.substring(picPath.lastIndexOf("/")+1);
		File file = new File(context.getCacheDir(), "images/"+filename);
		Bitmap b = BitmapUtils.loadBitmap(file);
		if(b!=null){
			ivAlbum.setImageBitmap(b);

			cache.put(picPath, new SoftReference<Bitmap>(b));
			return;
		}



		ImageLoadTask task = new ImageLoadTask();
		task.path = picPath;
		tasks.add(task);

		synchronized (workThread) {
			workThread.notify();
		}

		
	}


	public void stopThread(){
		isLoop = false;
		synchronized (workThread) {
			workThread.notify();
		}
	}
	

	class ImageLoadTask {
		String path;
		Bitmap bitmap;
	}

	
}
