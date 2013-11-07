package ro.cvu.nsacallservice;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i(TAG, "inside onCreate()");
		Intent startLoggingService = new Intent(this, Logging.class);
        startService(startLoggingService);
        // finish();
	}

	
}
