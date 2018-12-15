package dtg.dogretriever.Bluetooth;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.maps.model.LatLng;
import java.util.Calendar;
import java.util.Date;
import dtg.dogretriever.Model.Coordinate;

public class MyLocation{

    public enum UserType {SMARTPHONE,STATIONARY}

    private UserType userType;
    private Coordinate currentLocation;
    private LocationManager locationManager;
    private Context context;


    public MyLocation(Context context,UserType userType){
        this.context = context;
        this.userType = userType;
        currentLocation = new Coordinate();
        switch (userType){
            case SMARTPHONE:
                updateToLastKnowLocation();
                break;
            case STATIONARY:
                readLocationFromLocal();
                break;
            default:
                break;
        }
    }

    private void readLocationFromLocal(){
        currentLocation.setLocation(setDummy());
    }

    private boolean checkGPSStatus(){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        return true;
    }

    @SuppressWarnings("MissingPermission")
    private void updateToLastKnowLocation(){
        Location lastLocation;
        if (userType== UserType.SMARTPHONE && checkGPSStatus()){
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
            lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
            currentLocation.setLocation(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()));
        }
    }

    public Coordinate getCurrentLocation() {
        if (userType == UserType.SMARTPHONE)
            updateToLastKnowLocation();

        Date currentTime = Calendar.getInstance().getTime();
        currentLocation.setTimeStamp(currentTime);
        return currentLocation;
    }


    public LatLng setDummy(){
        return new LatLng(32.113086,34.818021);
    }

}

