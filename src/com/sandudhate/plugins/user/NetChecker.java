
/*
	DroidScript Plugin class.
	(This is where you put your plugin code)
*/

package com.sandudhate.plugins.user;

import android.os.*;
import android.content.*;
import android.util.Log;
import android.graphics.*;
import java.io.*;
import java.lang.reflect.*;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetChecker
{
	public static String TAG = "NetChecker";	
	public static float VERSION = 1.0f;	
	private Method m_callscript;
	private Object m_parent;
	private Context _context;

	//Contruct plugin.
	public NetChecker()
	{
		Log.d( TAG, "Creating plugin object");
	}

	//Initialise plugin.
	public void Init( Context ctx, Object parent )
	{
		try {
			Log.d( TAG, "Initialising plugin object");

			//Save reference to parent (DroidScript).
			m_parent = parent;

			//Use reflection to get 'CallScript' method
			Log.d( TAG, "Getting CallScript method");
			m_callscript = parent.getClass().getMethod( "CallScript", Bundle.class );
		} 
		catch (Exception e) {
			Log.e( TAG, "Failed to Initialise plugin!", e );
		}
		_context = ctx;
	}

	//Call a function in the user's script.
	private void CallScript( Bundle b )
	{
		try {
			m_callscript.invoke( m_parent, b );
		} 
		catch (Exception e) {
			Log.e( TAG, "Failed to call script function!", e );
		}
	}

	//Handle commands from DroidScript.
	public String CallPlugin( Bundle b)
	{
		//Extract command.
		String cmd = b.getString("cmd");

		//Process commands.
		String ret = null;
		try {
			if( cmd.equals("GetVersion") ) 
				return GetVersion( );
			else if( cmd.equals("IsConnected") )
				return IsConnected( );
			else if( cmd.equals( "GetType" ) )
				return GetType( _context );
          else if( cmd.equals( "GetSubType" ) )
				return GetSubType( _context );
			else if( cmd.equals( "GetExtras" ) )
				return GetExtraInfo( _context );
			else if( cmd.equals( "GetReason") )
				return GetReason( _context );
			else if(cmd.equals( "GetState" ) )
				return GetState( _context);
          else if( cmd.equals( "GetDetailedState" ) )
				return GetDetailedState( _context );
			else if( cmd.equals( "IsRoaming" ) )
				return IsRoaming( _context );
		} 
		catch (Exception e) {
			Log.e( TAG, "Plugin command failed!", e);
		}
		return ret;
	}

	//Handle the GetVersion command.
	private String GetVersion( )
	{
		Log.d( TAG, "Got GetVersion" );
		return Float.toString( VERSION );
	}

	private String IsConnected( ){
		ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) 
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) 
				for (int i = 0; i < info.length; i++) 
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return "Yes";
					}
		}
		return "No";
    }
	
	public static String GetType( Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
			.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if(null != activeNetwork) {
		  int tn = activeNetwork.getType();
		  if(tn == ConnectivityManager.TYPE_MOBILE) return "Mobile Data";
		  if(tn == ConnectivityManager.TYPE_WIFI) return "WiFi";
		  if(tn == ConnectivityManager.TYPE_WIMAX) return "WiMAX";
		  if(tn == ConnectivityManager.TYPE_ETHERNET) return "Ethernet";
		  if(tn == ConnectivityManager.TYPE_BLUETOOTH) return "Bluetooth";
		  if(tn == ConnectivityManager.TYPE_VPN) return "VPN";
		}
		return "No Connection";
	}

	public static String GetSubType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) 
		context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo mnet = cm.getActiveNetworkInfo();
		if( null != mnet) {
			return mnet.getSubtypeName();
			
		}
		return "No Connection";
	}
	
	public static String GetExtraInfo(Context context) {
		ConnectivityManager cm = (ConnectivityManager) 
			context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mnet = cm.getActiveNetworkInfo();
		if( null != mnet) {
			return mnet.getExtraInfo();

		}
		return "No Connection";
	}
	
	public static String GetReason(Context context) {
		ConnectivityManager cm = (ConnectivityManager) 
			context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mnet = cm.getActiveNetworkInfo();
		if( null != mnet) {
			return mnet.getReason();

		}
		return "No Connection";	
	}
	
	public static String GetState(Context context) {
		ConnectivityManager cm = (ConnectivityManager) 
			context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mnet = cm.getActiveNetworkInfo();
		if( null != mnet) {
			return mnet.getState().name();
		}
		return "No Connection";	
	}
   
   public static String GetDetailedState(Context context) {
		ConnectivityManager cm = (ConnectivityManager) 
			context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mnet = cm.getActiveNetworkInfo();
		if( null != mnet) {
			return mnet.getDetailedState().name();

		}
		return "No Connection";	
	}
	
	public static String IsRoaming(Context context) {
		ConnectivityManager cm = (ConnectivityManager) 
			context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mnet = cm.getActiveNetworkInfo();
		if( null != mnet ) {
			boolean ir = mnet.isRoaming();
            if(ir == true) return "Yes";
			else return "No";
		}
		return "No Connection";
	}

}
