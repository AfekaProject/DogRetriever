package dtg.dogretriever.Presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import dtg.dogretriever.Bluetooth.Utils;
import dtg.dogretriever.R;
import dtg.dogretriever.Bluetooth.ScannerService;

public class ScannerActivity extends AppCompatActivity{

    private static final String TAG = ScannerActivity.class.getSimpleName();
    private boolean isBound = false;

    private ScannerService mBoundService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((ScannerService.LocalBinder)service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isBound) {
            // Release information about the service's state.
            unbindService(mConnection);
            isBound = false;
        }
    }



    public void onStartScanClick(View view) {
        if ((Utils.checkBluetoothStatus(this))) {
            Intent intent = new Intent(this, ScannerService.class);
            if (bindService(intent, mConnection, BIND_AUTO_CREATE))
                isBound=true;
            Log.println(Log.ASSERT,TAG, "click start scan button");

        }
    }


}
