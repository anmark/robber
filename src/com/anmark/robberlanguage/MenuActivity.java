package com.anmark.robberlanguage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class MenuActivity extends Activity {

	private MediaPlayer mediaPlayer;
	static final String timeInSong = "timeInSong";
	static final String playMusic = "playMusic";
	static final String toRobberLanguage = "toRobberLanguage";
	static final String fromRobberLanguage = "fromRobberLanguage";
	private int timeInSongValue;
	private static final int RESULT_SETTINGS = 1;
	private boolean playMusicValue, isInLandscape;
	private RelativeLayout mainLayout, mainLayoutLand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		if (savedInstanceState != null) {
			super.onRestoreInstanceState(savedInstanceState);
			System.out.println("notnull " + mediaPlayer.getCurrentPosition());
			timeInSongValue = savedInstanceState.getInt(timeInSong);
			playMusicValue = savedInstanceState.getBoolean(playMusic, playMusicValue);
			System.out.println("notnullx " + timeInSongValue);
			mediaPlayer.seekTo(timeInSongValue);
		}
		
		setContentView(R.layout.activity_menu);
		
		mainLayout = (RelativeLayout)findViewById(R.id.mainmenu);
		mainLayoutLand = (RelativeLayout)findViewById(R.id.mainmenuland);
		
		
		// Check orientation and set boolean to control layout animation onResume
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			isInLandscape = true;
		} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			isInLandscape = false;
		}

		// System.out.println("is null " +
		// savedInstanceState.getInt(timeInSong));
		System.out.println("is null current "
				+ mediaPlayer.getCurrentPosition());
		System.out.println("is nullx " + timeInSongValue);

		System.out.println("create " + mediaPlayer.getCurrentPosition());
		System.out.println("createx " + timeInSongValue);

	
	}
	
	/*
	public void onWindowFocusChanged (boolean hasFocus) {
		   super.onWindowFocusChanged(hasFocus);
		   if (hasFocus)
		    //  mainLayout.setLayoutAnimation(fadefromright);
		}
	 */
	public void init() {
		mediaPlayer = MediaPlayer
				.create(getApplicationContext(), R.raw.pirates);
		mediaPlayer.setLooping(true);
		// playMusicValue = true;
		loadSharedPrefs();
		/*if (playMusicValue && !mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}*/
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		System.out.println("saveinst " + mediaPlayer.getCurrentPosition());
		timeInSongValue = mediaPlayer.getCurrentPosition();
		System.out.println("saveinstx " + timeInSongValue);
		savedInstanceState.putInt(timeInSong, timeInSongValue);
		savedInstanceState.putBoolean(playMusic, playMusicValue);
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
		if(mediaPlayer.isPlaying() && playMusicValue){
			mediaPlayer.pause();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		System.out.println("stop" + mediaPlayer.getCurrentPosition());
		System.out.println("stopx " + timeInSongValue);
		if(mediaPlayer.isPlaying()){
			mediaPlayer.pause();
			mediaPlayer.release();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("resume " + mediaPlayer.getCurrentPosition());
		System.out.println("resumex " + timeInSongValue);
		
		if (playMusicValue) {
			mediaPlayer.seekTo(timeInSongValue);
			mediaPlayer.start();
		}
		
		
		// start layout animation when activity has been hidden
		if(isInLandscape){	
			mainLayoutLand.startLayoutAnimation();
		}
		else{
			mainLayout.startLayoutAnimation();
		}
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			isInLandscape = true;
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			isInLandscape = false;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.music_on_off:
			Intent intent = new Intent(this, SettingsPreferenceActivity.class);
			startActivityForResult(intent, RESULT_SETTINGS);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RESULT_SETTINGS:
			loadSharedPrefs();
			break;
		}
	}

	/** Called when the user clicks the TranslateTo button */
	public void TranslateToPressed(View view) {
		Intent intent = new Intent(this, TranslateActivity.class);
		intent.putExtra(toRobberLanguage, toRobberLanguage);
		startActivity(intent);
	}

	/** Called when the user clicks the TranslateFrom button */
	public void TranslateFromPressed(View view) {
		Intent intent = new Intent(this, TranslateActivity.class);
		intent.putExtra(fromRobberLanguage, fromRobberLanguage);
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

	private void loadSharedPrefs() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		playMusicValue = sharedPrefs.getBoolean(playMusic, false);
	}
}