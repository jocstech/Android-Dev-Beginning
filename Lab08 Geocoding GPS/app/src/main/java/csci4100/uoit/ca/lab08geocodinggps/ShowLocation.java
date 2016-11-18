package csci4100.uoit.ca.lab08geocodinggps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ShowLocation extends AppCompatActivity implements LocationListener {

    private String bestProvider;
    private Location location;
    private LocationManager locationManager;
    final private int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATIO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        init();
        closeKeyboard();
        permissionRequest();


    }

    private void init() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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


    private void getGeocoder(Location loc){
        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
        } catch (Exception e){
            e.printStackTrace();
            fillAllInformation("Unknown","Unknown","Unknown","Unknown","Unknown","Unknown","Unknown","Unknown");
        }
        if( addresses ==null || addresses.size()==0){
            fillAllInformation("Unknown","Unknown","Unknown","Unknown","Unknown","Unknown","Unknown","Unknown");
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

        fillAllInformation(addr1,addr2,city,prov,country,postal,phone,url);
    }


    private void fillAllInformation(String addr1,String addr2,String city,String pro,String country,String postal,String phone,String url){

        EditText addrs1 = (EditText)findViewById(R.id.editAddressLine1);
        EditText addrs2 = (EditText)findViewById(R.id.editAddressLine2);
        EditText citys = (EditText)findViewById(R.id.editLocality);
        EditText pros = (EditText)findViewById(R.id.editAdminArea);
        EditText countrys = (EditText)findViewById(R.id.editCountryName);
        EditText postals = (EditText)findViewById(R.id.editPostalCode);
        EditText phoneNum = (EditText)findViewById(R.id.editPhoneNumber);
        EditText urls = (EditText)findViewById(R.id.editURL);

        addrs1.setText(addr1);
        addrs2.setText(addr2);
        citys.setText(city);
        pros.setText(pro);
        countrys.setText(country);
        postals.setText(postal);
        phoneNum.setText(phone);
        urls.setText(url);

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
    public void onLocationChanged(Location location) {
        if(location!=null){
            this.location = location;
            getGeocoder(location);
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
