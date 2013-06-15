package org.xkit.demo.and.customizedservice.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn1 = (Button) findViewById(R.id.button_invoke);

		btn1.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_invoke:

//			IBinder binder = (IBinder) getSystemService("test.customized"); // this only exposes those system services defined by Android

			// can use "android.os.ServiceManager"
			// but this is a hidden class
			// so our workaround comes, just for test customized service, using at your own risk

			Method getService = null;
			try {
				getService = Class.forName("android.os.ServiceManager")
						.getMethod("getService", String.class);
			} catch (NoSuchMethodException nsme) {
				nsme.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}

			IBinder binder = null;
			try {
				if (getService != null) {
					binder = (IBinder) getService.invoke(null,
							new Object[] { new String("test.customized") });
				} else {
					Log.e(this.getClass().getName(), "can not find getService method!");
				}
			} catch (IllegalArgumentException iae) {
				iae.printStackTrace();
			} catch (IllegalAccessException iae) {
				iae.printStackTrace();
			} catch (InvocationTargetException ite) {
				ite.printStackTrace();
			}

			Parcel data = Parcel.obtain();
			Parcel reply = Parcel.obtain();

			if (binder == null) {
				Log.e(this.getClass().getName(), "can not get service!");
				break;
			}

			data.writeInt(android.os.Process.myPid());
			data.writeInt(100);

			try {
				binder.transact(0, data, reply, 0);
			} catch (RemoteException e) {
				Log.e(this.getClass().getName(), "do IPC error!");
				e.printStackTrace();
			}

			Log.d(this.getClass().getName(), "result from system service " + reply.readString() + " " + reply.readInt());

			break;
		default:
			break;
		}
	}

}
