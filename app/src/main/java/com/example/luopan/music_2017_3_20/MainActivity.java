package com.example.luopan.music_2017_3_20;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.luopan.Utils.BitmapCallback;
import com.example.luopan.Utils.CommonUtils;
import com.example.luopan.Utils.GlobalConsts;
import com.example.luopan.Utils.MediaUtil;
import com.example.luopan.application.MyApplication;
import com.example.luopan.entity.Music;
import com.example.luopan.fragment.FavoriteFragment;
import com.example.luopan.fragment.LocalFragment;
import com.example.luopan.fragment.OnlineFragment;
import com.example.luopan.service.PlayService;
import com.example.luopan.service.PlayService.MusicBinder;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity{
    private RadioGroup radioGroup;
    private RadioButton radioOnline;
    private RadioButton radioLocal;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private MainPagerAdapter pagerAdapter;
    private ServiceConnection conn;
    private MusicInfoReceiver receiver;
    private MusicBinder musicBinder;
    ImageView musicPhoto;
    ProgressBar pb;
    TextView tv_start;
    TextView tv_end;
    TextView musicName;
    private ImageView mIv_playImage;
    private ImageButton mPlay;
    private ImageButton mNext;
    private ImageButton mPrevious;
    private RotateAnimation mAnim;
    RadioButton radioFavorite;

    private TextView tvPMTitle, tvPMSinger, tvPMLrc, tvPMCurrentTime, tvPMTotalTime;
    private ImageView ivPMAlbum, ivPMBackground;
    private SeekBar seekBar;

    private ImageLoader mImageLoader;
    private RelativeLayout mRelativeLayout;
    private ImageView mPremusic;
    private ImageView mStartMusic;
    private ImageView mNextMusic;
    private ImageView mIvPMBackground;

    /**
     * 判断是否更新播放按钮
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //初始化控件
        setViews();
        //设置适配器
        setAdapter();
        //设置监听器
        setListeners();
        //注册组件
        registComponents();
        creatImageLoader();
        ainimation();
    }

    private void ainimation() {

    }

    private void creatImageLoader() {
        mImageLoader = new ImageLoader(MyApplication.getRequestQueue(), new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {

                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });

    }


    /**
     * 绑定Service  或 注册组件等操作
     */
    private void registComponents() {
        //注册广播接收器
        receiver = new MusicInfoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalConsts.ACTION_MUSIC_STARTED);
        filter.addAction(GlobalConsts.ACTION_UPDATE_PROGRESS);
        filter.addAction(GlobalConsts.ACTION_STATR_MUSIC);
        filter.addAction(GlobalConsts.ACTION_PAUSE_MUSIC);
        filter.addAction(GlobalConsts.ACTION_LOCAL_MUSIC);
        filter.addAction(GlobalConsts.ACTION_ONLINE_MUSIC);
        filter.addAction(GlobalConsts.ACTION_NEXT_MUSIC);
        this.registerReceiver(receiver, filter);

        //绑定Service
        Intent intent = new Intent(this, PlayService.class);
        conn = new ServiceConnection() {
            //连接异常断开
            public void onServiceDisconnected(ComponentName name) {
            }

            //连接成功
            public void onServiceConnected(ComponentName name, IBinder binder) {
                musicBinder = (MusicBinder) binder;
                //把musicBinder交给两个Fragment
                LocalFragment f = (LocalFragment) fragments.get(0);
                f.setBinder(musicBinder);
                OnlineFragment f2 = (OnlineFragment) fragments.get(1);
                f2.setBinder(musicBinder);

            }
        };
        this.bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }


    @Override
    public void onBackPressed() {
        if(mRelativeLayout.getVisibility() == View.VISIBLE){
            //执行一个动画  把relativeLatyout收回去
            TranslateAnimation anim = new TranslateAnimation(0, 0, 0, mRelativeLayout.getHeight());
            anim.setDuration(350);
            mRelativeLayout.startAnimation(anim);
            mRelativeLayout.setVisibility(View.INVISIBLE);


        }else{
            moveTaskToBack(false);
        }
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        mIv_playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout.setVisibility(View.VISIBLE);
                ScaleAnimation anim = new ScaleAnimation(0.1f, 1f, 0.1f, 1f, 0, mRelativeLayout.getHeight());
                anim.setDuration(380);
                anim.setInterpolator(new BounceInterpolator());
                mRelativeLayout.startAnimation(anim);
            }
        });
        //viewPager控制radioGroup
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            //当页面被选中时 执行
            public void onPageSelected(int index) {
                switch (index) {
                    case 0:
                        radioLocal.setChecked(true);
                        break;
                    case 1:
                        radioOnline.setChecked(true);
                        break;
                    case 2:
                        radioFavorite.setChecked(true);
                }
            }

            //每当当viewpager滚动都会执行  执行频率非常高
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });

        //radioGroup控制viewpager
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0: //选中了新歌
                        viewPager.setCurrentItem(0);
                        radioLocal.setChecked(true);

                        break;
                    case R.id.radio1: //选中了热歌榜
                        viewPager.setCurrentItem(1);
                        radioOnline.setChecked(true);

                        break;
                    case R.id.radio2:
                        viewPager.setCurrentItem(2);
                        radioFavorite.setChecked(true);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){ //用户更改了seekbar的进度
                    int position=seekBar.getProgress();
                    musicBinder.seekTo(position);
                    tvPMCurrentTime.setText(CommonUtils.getFormattedTime(position));

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });

    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        //准备Fragment数据源   List<Fragment>
        fragments = new ArrayList<Fragment>();
        fragments.add(new LocalFragment());
        fragments.add(new OnlineFragment());
        fragments.add(new FavoriteFragment());
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    /**
     * 控件初始化
     */
    private void setViews() {
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativePlayMusic);
        musicName = (TextView) findViewById(R.id.musicName);
        tv_start = (TextView) findViewById(R.id.startTime);
        tv_end = (TextView) findViewById(R.id.endTime);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        musicPhoto = (ImageView) findViewById(R.id.musicPhoto);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioOnline = (RadioButton) findViewById(R.id.radio1);
        radioLocal = (RadioButton) findViewById(R.id.radio0);
        radioFavorite = (RadioButton) findViewById(R.id.radio2);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        mIv_playImage = (ImageView) findViewById(R.id.musicPhoto);
        mPlay = (ImageButton) findViewById(R.id.playmusic);
        mNext = (ImageButton) findViewById(R.id.nextmusic);
        mPrevious = (ImageButton) findViewById(R.id.Previous);

        tvPMTitle = (TextView) findViewById(R.id.tvPMTitle);
        tvPMSinger = (TextView) findViewById(R.id.tvPMSinger);
        tvPMLrc = (TextView) findViewById(R.id.tvPMLrc);
        tvPMCurrentTime = (TextView) findViewById(R.id.tvPMCurrentTime);
        tvPMTotalTime = (TextView) findViewById(R.id.tvPMTotalTime);
        ivPMAlbum = (ImageView) findViewById(R.id.ivPMAlbum);
        ivPMBackground = (ImageView) findViewById(R.id.ivPMBackground);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        mPremusic = (ImageView) findViewById(R.id.ivPMPre);
        mStartMusic = (ImageView)findViewById(R.id.ivPMStart);
        mNextMusic = (ImageView)findViewById(R.id.ivPMNext);
        mIvPMBackground = (ImageView)findViewById(R.id.ivPMBackground);




    }


    @Override
    protected void onDestroy() {
        //解绑Service
        this.unbindService(conn);
        //解除广播接收器
        this.unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void doClick(View v) {
        switch (v.getId()){

            case R.id.Previous:
            case R.id.ivPMPre:
                Music m = MyApplication.getApp().preMusic();
                if(m.getAlbumpic_small().startsWith("http://i.gtimg.cn/music/photo")){

                    ImageListener listener = ImageLoader.getImageListener(mIv_playImage, R.raw.qqmusic, R.raw.qqmusic);
                    mImageLoader.get(m.getAlbumpic_small(), listener);
                    ImageListener mlistener = ImageLoader.getImageListener(ivPMAlbum, R.raw.qqmusic, R.raw.qqmusic);
                    mImageLoader.get(m.getAlbumpic_small(), mlistener);
                }else{
                    MediaUtil.getArtwork(MyApplication.getApp(), m.getSongid(), Long.parseLong(m.getAlbummid()), true, true, new BitmapCallback() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap) {
                            mIv_playImage.setImageBitmap(bitmap);
                            ivPMAlbum.setImageBitmap(bitmap);
                        }
                    });


                }
                String path = m.getUrl();
                musicBinder.playMusic(path);
                mPlay.setImageResource(android.R.drawable.ic_media_pause);
                mStartMusic.setImageResource(android.R.drawable.ic_media_pause);
                break;

            case R.id.ivPMStart:
            case R.id.playmusic:
                musicBinder.startOrPause();
                break;


            case R.id.nextmusic:
            case R.id.ivPMNext:
                Music mm = MyApplication.getApp().nextMusic();
                if(mm.getAlbumpic_small().startsWith("http://i.gtimg.cn/music/photo")){
                    ImageListener listener = ImageLoader.getImageListener(mIv_playImage, R.raw.qqmusic, R.raw.qqmusic);
                    mImageLoader.get(mm.getAlbumpic_small(), listener);
                    ImageListener mlistener = ImageLoader.getImageListener(ivPMAlbum, R.raw.qqmusic, R.raw.qqmusic);
                    mImageLoader.get(mm.getAlbumpic_small(), mlistener);
                }else{
                    MediaUtil.getArtwork(MyApplication.getApp(), mm.getSongid(), Long.parseLong(mm.getAlbummid()), true, true, new BitmapCallback() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap) {
                            mIv_playImage.setImageBitmap(bitmap);
                            ivPMAlbum.setImageBitmap(bitmap);
                        }
                    });
                }

                String path1 = mm.getUrl();
                musicBinder.playMusic(path1);
                mPlay.setImageResource(android.R.drawable.ic_media_pause);
                mStartMusic.setImageResource(android.R.drawable.ic_media_pause);
                break;

        }
    }


    /**
     * viewpager的适配器
     */
    class MainPagerAdapter extends FragmentPagerAdapter {
        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        public int getCount() {
            return fragments.size();
        }
    }

    /**
     * 编写用于接收音乐信息相关的广播接收器
     */
    class MusicInfoReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(GlobalConsts.ACTION_UPDATE_PROGRESS)) {

                final Music m = MyApplication.getApp().getCurrentMusic();
                musicName.setText(m.getSongname());
                tvPMTitle.setText(m.getSongname());
                tvPMSinger.setText(m.getSingername());


                //接收到更新音乐进度广播
                int current = intent.getIntExtra("current", 0);
                int total = intent.getIntExtra("total", 0);
                pb.setMax(total);
                pb.setProgress(current);
                seekBar.setProgress(current);
                seekBar.setMax(total);
                tvPMTotalTime.setText(CommonUtils.getFormattedTime(total));
                tvPMCurrentTime.setText(CommonUtils.getFormattedTime(current));
                tv_start.setText(CommonUtils.getFormattedTime(current));
                tv_end.setText(CommonUtils.getFormattedTime(total));
                //顺便更新歌词信息

            } else if (GlobalConsts.ACTION_LOCAL_MUSIC.equals(action)) {
                //播放本地的音乐
                mPlay.setImageResource(android.R.drawable.ic_media_pause);
                mStartMusic.setImageResource(android.R.drawable.ic_media_pause);
                final Music m = MyApplication.getApp().getCurrentMusic();
                MediaUtil.getArtwork(MyApplication.getApp(), m.getSongid(), Long.parseLong(m.getAlbummid()), true, true, new BitmapCallback() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap) {
                        mIv_playImage.setImageBitmap(bitmap);
                        ivPMAlbum.setImageBitmap(bitmap);
                    }
                });
                mAnim = new RotateAnimation(0, 360, mIv_playImage.getWidth() / 2, mIv_playImage.getHeight() / 2);
                //无限循环
                mAnim.setRepeatCount(Animation.INFINITE);
                mAnim.setDuration(10000);
                mAnim.setInterpolator(new LinearInterpolator());
                mIv_playImage.startAnimation(mAnim);

            } else if (GlobalConsts.ACTION_PAUSE_MUSIC.equals(action)) {
                //音乐暂停播放
                mPlay.setImageResource(android.R.drawable.ic_media_play);
                mStartMusic.setImageResource(android.R.drawable.ic_media_play);


                mIv_playImage.clearAnimation();
            } else if (GlobalConsts.ACTION_STATR_MUSIC.equals(action)) {
                //只有点暂停和播放按钮时 才走这个方法 点上一首 下一首 不走这个
                //音乐开始播放
                mPlay.setImageResource(android.R.drawable.ic_media_pause);
                mStartMusic.setImageResource(android.R.drawable.ic_media_pause);
                mIv_playImage.startAnimation(mAnim);
            } else if (GlobalConsts.ACTION_ONLINE_MUSIC.equals(action)) {
                mPlay.setImageResource(android.R.drawable.ic_media_pause);
                mStartMusic.setImageResource(android.R.drawable.ic_media_pause);
                //播放网络的音乐
                final Music m = MyApplication.getApp().getCurrentMusic();
                Log.i("TAG-->play", m.toString());

                mAnim = new RotateAnimation(0, 360, mIv_playImage.getWidth() / 2, mIv_playImage.getHeight() / 2);
                //无限循环
                mAnim.setRepeatCount(Animation.INFINITE);
                mAnim.setDuration(10000);
                mAnim.setInterpolator(new LinearInterpolator());

                ImageListener listener = ImageLoader.getImageListener(mIv_playImage, R.raw.qqmusic, R.raw.qqmusic);
                mImageLoader.get(m.getAlbumpic_small(), listener);
                mIv_playImage.startAnimation(mAnim);
                ImageListener mlistener = ImageLoader.getImageListener(ivPMAlbum, R.raw.qqmusic, R.raw.qqmusic);
                mImageLoader.get(m.getAlbumpic_small(), mlistener);
            }else if (GlobalConsts.ACTION_NEXT_MUSIC.equals(action)){
                Music m = MyApplication.getApp().nextMusic();
                    //更换底部播放栏的专辑图片
                if(m.getAlbumpic_small().startsWith("http://i.gtimg.cn/music/photo")){
                    ImageListener listener = ImageLoader.getImageListener(mIv_playImage, R.raw.qqmusic, R.raw.qqmusic);
                    mImageLoader.get(m.getAlbumpic_small(), listener);
                    ImageListener mlistener = ImageLoader.getImageListener(ivPMAlbum, R.raw.qqmusic, R.raw.qqmusic);
                    mImageLoader.get(m.getAlbumpic_small(), mlistener);
                }else{
                    MediaUtil.getArtwork(MyApplication.getApp(), m.getSongid(), Long.parseLong(m.getAlbummid()), true, true, new BitmapCallback() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap) {
                            mIv_playImage.setImageBitmap(bitmap);
                            ivPMAlbum.setImageBitmap(bitmap);
                        }
                    });
                }
                //播放下一首歌曲
                String path = m.getUrl();
                musicBinder.playMusic(path);
            }
        }
    }



}




