package csci4100.uoit.ca.lab09maps;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ShowLocation extends AppCompatActivity implements OnMapReadyCallback , LocationListener {

    private String bestProvider;
    private Location location;
    private LocationManager locationManager;
    final private int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATIO = 1;

    private GoogleMap mMap;
    private String label;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        init();
        closeKeyboard();
        permissionRequest();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    private void init() {

        label = "Unknown";
        //location.setLatitude(0);
        //location.setLongitude(0);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true);
        locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true), 4000, 1, this);
    }

    private void permissionRequest(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATIO);
            }
            return;
        }

        if(location!=null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            bestProvider = locationManager.getBestProvider(criteria, true);
            locationManager.requestSingleUpdate(bestProvider, this, null);
            location = locationManager.getLastKnownLocation(bestProvider);
        }
    }


    private String getGeocoder(Location loc){
        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
        } catch (Exception e){
            e.printStackTrace();
            //fillAllInformation("Unknown","Unknown","Unknown","Unknown","Unknown","Unknown","Unknown","Unknown");
        }
        if( addresses ==null || addresses.size()==0){
            //fillAllInformation("Unknown","Unknown","Unknown","Unknown","Unknown","Unknown","Unknown","Unknown");
        }

        Address address = addresses.get(0);

        String addr1 = checkUnknown(address.getAddressLine(0));
        String addr2 = checkUnknown(address.getAddressLine(1));
        String city = checkUnknown(address.getLocality());
        String prov = checkUnknown(address.getAdminArea());
        String country = checkUnknown(address.getCountryName());
        String postal = checkUnknown(address.getPostalCode());
        String phone = checkUnknown(address.getPhone());
        String url = checkUnknown(address.getUrl());

        return "Address Line 1: "+addr1+"\nAddress Line 2: "+addr2+"\nCity: "+city+"\nProvince: "+prov+"\nCountry: "+country+"\nPostal Code: "+postal+"\nPhone Number: "+phone+"\nURL: "+url;
    }


    private void moveTocurrentLocation(GoogleMap map){

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());



        CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15f);

        map.moveCamera(center);
        map.animateCamera(zoom);

        marker = map.addMarker(new MarkerOptions().position(latLng).title("Current Location").snippet(label).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        marker.showInfoWindow();

    }

    private String checkUnknown(String str){
        if (str==null)
            return "Unknown";
        else return str;
    }

    private void closeKeyboard(){
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(getWindow().getAttributes().token, 0);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mapInit();

        if(location==null){
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Unknown Location"));
        } else {
            moveTocurrentLocation(mMap);
        }
    }


    private void mapInit(){
        UiSettings setting = mMap.getUiSettings();
        setting.setZoomControlsEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getApplicationContext());
                info.setOrientation(LinearLayout.VERTICAL);
                info.setPadding(10,10,10,10);


                TextView title = new TextView(getApplicationContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getApplicationContext());
                snippet.setTextColor(Color.BLUE);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            this.location = location;
            label = getGeocoder(location);
            moveTocurrentLocation(mMap);
            //fillAllInformation(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()),"","","","","");
            Toast.makeText(this,"Location Updated: \nLat:"+String.valueOf(location.getLatitude())+" Lng:"+String.valueOf(location.getLongitude()),Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"No Location Data!",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        bestProvider = s;
    }

    @Override
    public void onProviderDisabled(String s) {

    }

}