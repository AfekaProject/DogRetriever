package dtg.dogretriever.Presenter;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


import java.util.ArrayList;

import dtg.dogretriever.Bluetooth.Beacon;
import dtg.dogretriever.Bluetooth.MyLocation;
import dtg.dogretriever.Bluetooth.PermissionPopUp;
import dtg.dogretriever.DebugUtils;
import dtg.dogretriever.Model.Dog;
import dtg.dogretriever.R;
import dtg.dogretriever.Bluetooth.ScannerService;
import dtg.dogretriever.View.DogScanAdapter;

public class ScannerActivity extends AppCompatActivity implements ScannerService.OnBeaconEventListener, ServiceConnection{
    //log vars
    private static final String TAG = ScannerActivity.class.getSimpleName();

   //service vars
    private boolean isBound = false;
    private ScannerService mBoundService;

    //view vars
    ListView dogScanList;
    DogScanAdapter dogScanAdapter;
    //beacon vars
    private ArrayList<Beacon> beaconsFound;
    private ArrayList<Dog> dogsInListView;

    //debugs vars
    private ArrayList<Dog> dogArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        beaconsFound = new ArrayList<>();
        dogsInListView = new ArrayList<>();
        dogScanList = findViewById(R.id.scanner_scannedList);
        dogScanAdapter = new DogScanAdapter(getBaseContext(),dogsInListView);

        dogScanList.setAdapter(dogScanAdapter);
        dogArrayList = DebugUtils.createDogArrayList();

        MyLocation mLocation = new MyLocation(this,MyLocation.UserType.SMARTPHONE);
        Log.println(Log.ASSERT,TAG,mLocation.getLastLocation().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isBound) {
            // Release information about the service's state.
            unbindService(this);
            isBound = false;
        }
    }


    public void onStartScanClick(View view) {
        if ((PermissionPopUp.checkBluetoothStatus(this))) {
            Intent intent = new Intent(this, ScannerService.class);
            if (bindService(intent, this, BIND_AUTO_CREATE))
                isBound=true;
            Log.println(Log.ASSERT,TAG, "click start scan button");

        }
        dogsInListView.clear();
        dogScanAdapter.notifyDataSetChanged();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mBoundService = ((ScannerService.LocalBinder)iBinder).getService();
        mBoundService.setBeaconEventListener(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mBoundService = null;
        isBound = false;
    }
    @Override
    public void onBeaconIdentifier(String deviceAddress, int rssi, String instanceId) {
        final long now = System.currentTimeMillis();
        //if beacon already have been Identified
        Log.println(Log.ASSERT,TAG, "Eddystone(" + deviceAddress + ") id = " + instanceId);
        for (Beacon item : beaconsFound){
            if (instanceId.equals(item.getId())) {
                item.update(deviceAddress, rssi, now);
                return;
            }
        }
        //create new beacon obj
        Beacon beacon = new Beacon(deviceAddress, rssi, instanceId, now);
        beaconsFound.add(beacon);
        //relate to dog and view in listView
        Dog dog = searchRelateDog(instanceId);
        if (ScannerService.DEBUG_SCAN) {
            Log.println(Log.ASSERT,TAG, "Eddystone(" + deviceAddress + ") id = " + instanceId);
            if (dog != null)
                Log.println(Log.ASSERT,TAG, dog.toString());
        }
        if (dog != null){
            dogsInListView.add(dog);
            dogScanAdapter.notifyDataSetChanged();
        }
    }

    private Dog searchRelateDog (String id){
        // work with debug arrayList. toDo change to FireBase
        for (Dog dog : dogArrayList){
            if (id.equals(dog.getCollarId()))
                return dog;
        }
        return null;
    }
}