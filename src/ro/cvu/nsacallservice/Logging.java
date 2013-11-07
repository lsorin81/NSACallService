package ro.cvu.nsacallservice;

import java.util.Date;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.CallLog;
import android.util.Log;

public class Logging extends Service {
    private static final String TAG = Logging.class.getSimpleName();
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Log.i(TAG, "inside onCreate of Logging");
        printCallLog(getBaseContext().getContentResolver());
    }
    public void DeleteNumFromCallLog(ContentResolver resolver, String strNum){
        try
        {
            String strUriCalls = "content://call_log/calls";
            Uri UriCalls = Uri.parse(strUriCalls);
            //Cursor c = res.query(UriCalls, null, null, null, null);
            if(null != resolver)
            {
                resolver.delete(UriCalls, CallLog.Calls.NUMBER +"=?",new String[]{ strNum});
            }
        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }
    public void printCallLog(ContentResolver resolver){
        if(null != resolver){
            String[] strFields = {
                    android.provider.CallLog.Calls.NUMBER,
                    android.provider.CallLog.Calls.TYPE,
                    android.provider.CallLog.Calls.CACHED_NAME,
                    android.provider.CallLog.Calls.DATE,
                    android.provider.CallLog.Calls.DURATION
                    
            };
            String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
            Cursor mCallCursor = resolver.query(
                    android.provider.CallLog.Calls.CONTENT_URI,
                    strFields,
                    null,
                    null,
                    strOrder
            );
            if(mCallCursor.moveToFirst()){
                do{
                	Log.v(TAG, "---------------------");
                	if(!mCallCursor.isNull(0)){
                    	Log.i(TAG, mCallCursor.getString(0));
                    };
                    if(!mCallCursor.isNull(1)){
                    	switch(mCallCursor.getInt(1)){
                    	case 1:
                    		Log.i(TAG, "Incoming");
                    		break;
                    	case 2:
                    		Log.i(TAG, "Outgoing");
                    		break;
                    	default:
                    		Log.i(TAG, "Missed");
                    		break;
                    	}
                    }
                    if(!mCallCursor.isNull(4)){
                    	Log.i(TAG, mCallCursor.getString(4) + "sec");
                    }
                    if(!mCallCursor.isNull(2)){
                    	Log.i(TAG, mCallCursor.getString(2));
                    }
                    if(!mCallCursor.isNull(3)){
                    	Date d = new Date(mCallCursor.getLong(3));
                    	Log.i(TAG, d.toString());
                    }
                }while(mCallCursor.moveToNext());
            }
        }
    }
    

}