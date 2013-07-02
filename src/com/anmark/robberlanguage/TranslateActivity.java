package com.anmark.robberlanguage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TranslateActivity extends Activity {

	private String translatedText = "";
	private EditText input;
	private TextView output;
	private TextView translate_inputText;
	private TextView translate_outputText;
	private Boolean translateToRobber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.activity_translate);
		
		// Get views
		translate_inputText = (TextView) findViewById(R.id.translate_inputText);
		translate_outputText = (TextView) findViewById(R.id.translate_outputText);
		input = (EditText) findViewById(R.id.translate_input);
		output = (TextView) findViewById(R.id.translate_output);

		if (extras != null) {
			// If null then must be opposite translation
			if (extras.getString(MenuActivity.fromRobberLanguage) == null) {
				setTitle(R.string.title_activity_translate_to);
				translateToRobber = true;
				translate_inputText.setText(R.string.translateTo_inputText);
				translate_outputText.setText(R.string.translateTo_outputText);
			
				//translateTo_inputText.setText())
				//setContentView(R.layout.activity_translate_to);
			} else {
				setTitle(R.string.title_activity_translate_from);
				translateToRobber = false;
				translate_inputText.setText(R.string.translateFrom_inputText);
				translate_outputText.setText(R.string.translateFrom_outputText);
				//setContentView(R.layout.activity_translate_from);
			}
		}// there is extras

		// Show the Up button in the action bar.
		setupActionBar();

		// Add context menu to translated text
		registerForContextMenu(output);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.translate, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button
			NavUtils.navigateUpFromSameTask(this);
			return true;
			// If clear pressed in options menu, clear translated text
		case R.id.menuclear:
			output.setText("");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Translate input text to Robber Language
	public String translateTo(String input) {

		String output = "";

		char con[] = "bcdfghjklmnpqrstvwxz".toCharArray();
		char robber[] = input.toCharArray();

		for (int i = 0; i < robber.length; i++) {
			String temp = "";
			for (int y = 0; y < con.length; y++) {
				if (robber[i] == con[y]) {
					temp = "o" + Character.toString(robber[i]);
					break;
				}
			}
			output = output + Character.toString(robber[i]) + temp;
		}

		return output;
	}

	// Translate Robber Language to regular text
	public String translateFrom(String input) {
		// Code for translation fetched from
		// http://www.sweclockers.com/forum/10-programmering-och-digitalt-skapande/1214106-skoluppgift-i-java-rovarspraket/
		String output = input.replaceAll(
				"(?iu)(([b-d]|[f-h]|[j-n]|[p-t]|[v-x]|z)o\\2)", "$2");
		return output;
	}

	/** Called when the user clicks the Translate button */
	public void TranslatePressed(View view) {
		// Get input text
		String textToTranslate = input.getText().toString();

		// Clear input
		input.setText("");

		// Display short toast if input is empty
		if (textToTranslate.isEmpty()) {
			final Context context = getApplicationContext();
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context,
					context.getString(R.string.nothingToTranslate), duration);
			toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 150);
			toast.show();

		}
		// Do translation and display it
		else {
			if(translateToRobber){
				translatedText = translateTo(textToTranslate) + " ";
			}
			else{
				translatedText = translateFrom(textToTranslate) + " ";
			}
			output.append(translatedText);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// Create context menu with one item, clear
		menu.setHeaderTitle(R.string.title_context_menu);
		menu.add(0, v.getId(), 0, R.string.title_clear);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		TextView output = (TextView) findViewById(R.id.translate_output);
		// If clear selected, clear translated text
		if (item.getTitle().equals(getString(R.string.title_clear))) {
			output.setText("");
		}
		return true;
	}
	private void saveSharedPrefs() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		//playMusicValue = sharedPrefs.getString(key, defValue);
	}
	private void loadSharedPrefs() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		//playMusicValue = sharedPrefs.getString(key, defValue);
	}

}
