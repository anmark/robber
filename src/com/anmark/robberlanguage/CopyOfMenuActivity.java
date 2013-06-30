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

//android:configChanges="keyboardHidden|orientation|screenSize"
public class CopyOfMenuActivity extends Activity {

	private MediaPlayer mediaPlayer;
	static final String timeInSong = "timeInSong";
	private int timeInSongValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		if(savedInstanceState == null){
			mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pirates);
			mediaPlayer.setLooping(true);
        }
		else{
		restoreValue(savedInstanceState);
		}
		
		System.out.println("create "+ timeInSongValue);
		
		mediaPlayer.seekTo(timeInSongValue);
		mediaPlayer.start();

		setContentView(R.layout.activity_menu);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		restoreValue(savedInstanceState);
		System.out.println("restore "+ timeInSongValue);
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		mediaPlayer.pause();
		timeInSongValue = mediaPlayer.getCurrentPosition();
		savedInstanceState.putInt("timeInSongValue", timeInSongValue);
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume(); 
		System.out.println("resume "+ mediaPlayer.getCurrentPosition());
		System.out.println("resumex "+ timeInSongValue);
		//mediaPlayer.seekTo(timeInSongValue);
		mediaPlayer.start();				
	}
	
	
	private void restoreValue(Bundle savedInstanceState) {
	    
		if (savedInstanceState != null && savedInstanceState.containsKey(timeInSong))
	    {
	        timeInSongValue = savedInstanceState.getInt(timeInSong);
	    }
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
		AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
		builder.setMessage(R.string.aboutText).setCancelable(false).setIcon(R.drawable.ic_launcher)
		.setPositiveButton(R.string.aboutOk, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//do nothing
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/** Terminates app and kills process when the user clicks the Quit button */
	public void QuitPressed(View view) {
		//System.out.println("finish "+ timeInSongValue);
		finish();
	}

}
