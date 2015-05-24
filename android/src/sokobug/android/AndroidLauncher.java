package sokobug.android;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import sokobug.Sokobug;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Sokobug(), config);
	}

	@Override
	public void onBackPressed() {

		final Dialog dialog = new Dialog(this);
		dialog.setTitle("Quit game?");
		
		LinearLayout linerLayout = new LinearLayout(this);
		linerLayout.setOrientation(LinearLayout.HORIZONTAL);

		Button buttonQuit = new Button(this);
		buttonQuit.setText("Quit");
		buttonQuit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		linerLayout.addView(buttonQuit);

		Button buttonResume = new Button(this);
		buttonResume.setText("Resume");
		buttonResume.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		linerLayout.addView(buttonResume);

		dialog.setContentView(linerLayout);
		dialog.show();
	}
}
