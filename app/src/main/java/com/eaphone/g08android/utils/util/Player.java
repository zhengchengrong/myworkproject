package com.eaphone.g08android.utils.util;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Player implements OnBufferingUpdateListener, OnCompletionListener,
        OnPreparedListener {

	public MediaPlayer mediaPlayer; // ý�岥����
	private SeekBar seekBar; // �϶���
	private Timer mTimer = new Timer(); // ��ʱ��

	// ��ʼ��������
	public Player(SeekBar seekBar) {
		super();
		this.seekBar = seekBar;
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// ����ý��������
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ÿһ�봥��һ��
		mTimer.schedule(timerTask, 0, 1000);
	}

	// ��ʱ��
	TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			if (mediaPlayer == null)
				return;
			if (mediaPlayer.isPlaying() && seekBar.isPressed() == false) {
				handler.sendEmptyMessage(0); // ������Ϣ
			}
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int position = mediaPlayer.getCurrentPosition();
			int duration = mediaPlayer.getDuration();
			if (duration > 0) {
				// ������ȣ���ȡ���������̶�*��ǰ���ֲ���λ�� / ��ǰ����ʱ����
				long pos = seekBar.getMax() * position / duration;
				seekBar.setProgress((int) pos);
			}
		};
	};

	public void play() {
		mediaPlayer.start();
	}

	/**
	 * 
	 * @param url
	 *            url��ַ
	 */
	public void playUrl(String url) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url); // ��������Դ
			mediaPlayer.prepare(); // prepare�Զ�����
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

	// ��ͣ
	public void pause() {
		mediaPlayer.pause();
	}

	// ֹͣ
	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
		Log.e("mediaPlayer", "onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.e("mediaPlayer", "onCompletion");
	}

	/**
	 * �������
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		seekBar.setSecondaryProgress(percent);
		int currentProgress = seekBar.getMax()
				* mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
		Log.e(currentProgress + "% play", percent + " buffer");
	}

}
