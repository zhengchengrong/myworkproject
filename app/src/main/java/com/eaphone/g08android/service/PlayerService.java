package com.eaphone.g08android.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.eaphone.g08android.ui.live.HealthZhiBoDetailActivity;
import com.eaphone.g08android.ui.live.LiveConstats;

import java.io.IOException;

/**
 * Created by zhengchengrong on 2017/11/18.
 */
public class PlayerService extends Service implements Runnable,
        MediaPlayer.OnCompletionListener {
    /* 定于一个多媒体对象 */
    public static MediaPlayer mMediaPlayer = null;
    // 是否单曲循环
    private static boolean isLoop = false;
    // 用户操作
    private int MSG;

    /* 定义要播放的文件夹路径 */
//    private static final String MUSIC_PATH = new String("/sdcard/");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
		/* 监听播放是否完成 */
        mMediaPlayer.setOnCompletionListener(this);
    }


    public static void startCommand(Context context, String action) {
        Intent intent = new Intent(context, PlayerService.class);
        intent.setAction(action);
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        System.out.println("service onDestroy");
    }
    /*启动service时执行的方法*/
   // @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//		/*得到从startService传来的动作，后是默认参数，这里是我自定义的常量*/
//        MSG = intent.getIntExtra(LiveConstats.MSG, LiveConstats.PLAY_MAG);
//        if (MSG == LiveConstats.PLAY_MAG) {
//            playMusic();
//        }
//        if (MSG == LiveConstats.PAUSE) {
//            if (mMediaPlayer.isPlaying()) {// 正在播放
//                mMediaPlayer.pause();// 暂停
//            } else {// 没有播放
//                mMediaPlayer.start();
//            }
//        }
//
//        return super.onStartCommand(intent, flags, startId);
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case LiveConstats.ACTION_MEDIA_PLAY:
                    //playMusic();
                    playMusic2();
                    break;
                case LiveConstats.ACTION_MEDIA_PLAY_PAUSE:
                    pause();
                     break;
                case LiveConstats.ACTION_MEDIA_NEXT:
                    //next();
                    break;
                case LiveConstats.ACTION_MEDIA_PREVIOUS:
                   // prev();
                    break;

            }
        }
        return START_NOT_STICKY;
    }

    @SuppressWarnings("static-access")
    public void playMusic() {
        try {
			/* 重置多媒体 */
            mMediaPlayer.reset();
			/* 读取mp3文件 */
           // mMediaPlayer.setDataSource(MUSIC_PATH+HealthZhiBoDetailActivity.datas.get(HealthZhiBoDetailActivity.currentListItme).getUrl());
            mMediaPlayer.setDataSource(HealthZhiBoDetailActivity.datas.get(HealthZhiBoDetailActivity.currentListItme).getUrl());
//			Uri uri = Uri.parse(MUSIC_PATH+TestMediaPlayer.mMusicList.get(TestMediaPlayer.currentListItme));
//
//			mMediaPlayer.create(PlayerService.this,uri);
			/* 准备播放 */
            mMediaPlayer.prepare();
			/* 开始播放 */
            mMediaPlayer.start();
			/* 是否单曲循环 */
            mMediaPlayer.setLooping(isLoop);
            // 设置进度条最大值
            HealthZhiBoDetailActivity.musicProgress.setMax(PlayerService.mMediaPlayer
                    .getDuration());
            // new Thread(this).start();
        } catch (IOException e) {
        }

    }

    public void playMusic2() {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(HealthZhiBoDetailActivity.datas.get(HealthZhiBoDetailActivity.currentListItme).getUrl());
            mMediaPlayer.prepare(); // prepare自动播放
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pause() {
        if (mMediaPlayer.isPlaying()) {// 正在播放
            mMediaPlayer.pause();// 暂停
        }      }
    // 暂停
    public void pause2() {
        mMediaPlayer.pause();
    }


    // 刷新进度条
    @Override
    public void run() {
        int CurrentPosition = 0;// 设置默认进度条当前位置
        int total = mMediaPlayer.getDuration();//
        while (mMediaPlayer != null && CurrentPosition < total) {
            try {
                Thread.sleep(1000);
                if (mMediaPlayer != null) {
                    CurrentPosition = mMediaPlayer.getCurrentPosition();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HealthZhiBoDetailActivity.musicProgress.setProgress(CurrentPosition);
        }

    }
    public class PlayBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
		/* 播放完当前歌曲，自动播放下一首 */
//        if (++HealthZhiBoDetailActivity.currentListItme >= HealthZhiBoDetailActivity.datas
//                .size()) {
//            Toast.makeText(PlayerService.this, "已到最后一首歌曲", Toast.LENGTH_SHORT)
//                    .show();
//            HealthZhiBoDetailActivity.currentListItme--;
//            HealthZhiBoDetailActivity.musicProgress.setMax(0);
//        } else {
//            playMusic();
//        }
    }
}