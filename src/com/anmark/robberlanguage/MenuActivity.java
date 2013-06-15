package com.anmark.robberlanguage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
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

	/** Called when the user clicks the About button */
	public void AboutPressed(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.aboutText).setCancelable(false)
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
		finish();
	}


}
