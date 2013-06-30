package com.anmark.robberlanguage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public class MenuActivity extends Activity {

	private MediaPlayer mediaPlayer;
	static final String timeInSong = "timeInSong";
	private int timeInSongValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mediaPlayer = MediaPlayer
				.create(getApplicationContext(), R.raw.pirates);
		mediaPlayer.setLooping(true);
		if (savedInstanceState != null) {
			super.onRestoreInstanceState(savedInstanceState);
			System.out.println("notnull " + mediaPlayer.getCurrentPosition());
			timeInSongValue = savedInstanceState.getInt(timeInSong);
			System.out.println("notnullx " + timeInSongValue);
			mediaPlayer.seekTo(timeInSongValue);
		}

		// System.out.println("is null " +
		// savedInstanceState.getInt(timeInSong));
		System.out.println("is null current "
				+ mediaPlayer.getCurrentPosition());
		System.out.println("is nullx " + timeInSongValue);

		System.out.println("create " + mediaPlayer.getCurrentPosition());
		System.out.println("createx " + timeInSongValue);

		setContentView(R.layout.activity_menu);
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		System.out.println("saveinst " + mediaPlayer.getCurrentPosition());

		timeInSongValue = mediaPlayer.getCurrentPosition();
		System.out.println("saveinstx " + timeInSongValue);
		mediaPlayer.pause();
		savedInstanceState.putInt(timeInSong, timeInSongValue);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// mediaPlayer.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		System.out.println("pause " + mediaPlayer.getCurrentPosition());
		timeInSongValue = mediaPlayer.getCurrentPosition();
		System.out.println("pausex " + timeInSongValue);
		mediaPlayer.pause();
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println("stop" + mediaPlayer.getCurrentPosition());
		System.out.println("stopx " + timeInSongValue);
		mediaPlayer.stop();
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("resume " + mediaPlayer.getCurrentPosition());
		System.out.println("resumex " + timeInSongValue);
		mediaPlayer.seekTo(timeInSongValue);
		mediaPlayer.start();
	}

	/** Called when the user clicks the TranslateTo button */
	public void TranslateToPressed(View view) {
		Intent intent = new Intent(this, TranslateToActivity.class);
		startActivity(intent);
	}

	/** Called when the user clicks the TranslateFrom button */
	public void TranslateFromPressed(View view) {
		Intent intent = new Intent(this, TranslateFromActivity.class);
		startActivity(intent);
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.onCreateView(name, context, attrs);
	}

	/** Called when the user clicks the About button */
	public void AboutPressed(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this,
				R.style.CustomDialog);
		builder.setMessage(R.string.aboutText)
				.setCancelable(false)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton(R.string.aboutOk,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// do nothing
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/** Terminates app and kills process when the user clicks the Quit button */
	public void QuitPressed(View view) {
		finish();
	}

	public void onDestroy() {
		super.onDestroy(); // Always call the superclass
		/*
		 * if(mediaPlayer != null && isFinishing()){
		 * System.out.println("destroyx released"+ timeInSongValue);
		 * mediaPlayer.release(); }
		 */

		// mediaPlayer.release();
		System.out.println("destroy " + mediaPlayer.getCurrentPosition());
		System.out.println("destroyx " + timeInSongValue);
		// Bundle savedInstanceState = new Bundle();
		// savedInstanceState.putInt(timeInSong, timeInSongValue);
		// onSaveInstanceState(savedInstanceState);
		//
		// Stop method tracing that the activity started during onCreate()
		// android.os.Debug.stopMethodTracing();

	}
}