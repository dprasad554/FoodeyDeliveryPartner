package com.geekhive.foodeydeliveryboy.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.cakeorderHistory.CakeOrderHistory;
import com.geekhive.foodeydeliveryboy.beans.newOrderList.Cake;
import com.geekhive.foodeydeliveryboy.beans.orderHistory.OrderHistory;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.GPSTracker;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.Prefs;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.ncorti.slidetoact.SlideToActView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CakeReachDropLocation extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, OnResponseListner, LocationListener {

    //SlideToActView sta;

    SlideToActView vB_reached_cd;


    Dialog OtpPopup;
    TextView need_help;
    GoogleMap googleMap;
    double lat;
    double lang;
    int position_;
    int AUTOCOMPLETE_REQUEST_CODE = 1;

    Geocoder geocoder;
    List<Address> addresses;

    ConnectionDetector mDetector;
    String state;
    String country;
    String cart_id;
    String order_id;
    PlacesClient placesClient;

    TextView orderId;
    TextView customerName;
    TextView customerAddress;
    CakeOrderHistory orderListOut;
    double latitude, longitude;
    LocationManager locationManager;
    String mprovider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reach_droplocation_activity);
        mDetector = new ConnectionDetector(this);
        latitude = Double.parseDouble(getIntent().getStringExtra("lat"));
        longitude = Double.parseDouble(getIntent().getStringExtra("lang"));
        position_ = getIntent().getIntExtra("position", 0);
        orderId = findViewById(R.id.orderId);
        customerName = findViewById(R.id.customerName);
        customerAddress = findViewById(R.id.customerAddress);
        vB_reached_cd = findViewById(R.id.vB_reached_cd);
        vB_reached_cd.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                /*overridePendingTransition(R.anim.left_in, R.anim.left_out);
                Intent intent=new Intent(CakeReachDropLocation.this, CakeFinishDeliveryActivity.class);
                intent.putExtra("position", position_);
                intent.putExtra("lat", getIntent().getStringExtra("lat"));
                intent.putExtra("lang", getIntent().getStringExtra("lang"));
                startActivity(intent);*/
                if(Math.floor(latitude * 1000) == Math.floor(lat * 1000) || Math.floor(longitude * 1000) == Math.floor(lang * 1000)){
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    Intent intent=new Intent(CakeReachDropLocation.this, CakeFinishDeliveryActivity.class);
                    intent.putExtra("position", position_);
                    intent.putExtra("lat", getIntent().getStringExtra("lat"));
                    intent.putExtra("lang", getIntent().getStringExtra("lang"));
                    startActivity(intent);
                } else {
                    SnackBar.makeText(CakeReachDropLocation.this, "It seems like you are yet to reach customer location", SnackBar.LENGTH_SHORT).show();
                    vB_reached_cd.resetSlider();
                }

            }
        });

        geocoder = new Geocoder(this, Locale.getDefault());
        Places.initialize(getApplicationContext(), "AIzaSyB_RBQ5Le6REjSn35XB_f7_ufQmzETVOYY");
        // Create a new Places client instance.
        placesClient = Places.createClient(this);
        final List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        if (googleMap != null) {
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    createMarker(latitude, longitude, "", "", R.drawable.map_2, googleMap);
                    try {
                        addresses = geocoder.getFromLocation(lang, lang, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        state = addresses.get(0).getAdminArea();
                        country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }

        checkLocationService();
        CallService();

    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).OrderCakeHistory(WebServices.Foodey_Services,
                    WebServices.ApiType.restaurantList, Prefs.getUserId(this));
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.restaurantList) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                orderListOut = (CakeOrderHistory) response;
                if (!isSucces || orderListOut == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    latitude = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getAddress().getLatitude() + "");
                    longitude = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getAddress().getLongitude() + "");

                    orderId.setText(orderListOut.getOrderHistory().get(position_).getCakeCart().getOrderId());
                    String custName = orderListOut.getOrderHistory().get(position_).getUser().getFirstname() + " " +
                            orderListOut.getOrderHistory().get(position_).getUser().getLastname();
                    customerName.setText(custName);
                    String address = orderListOut.getOrderHistory().get(position_).getAddress().getHouse() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getApartment() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getStreet() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getLandmark() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getArea() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getCity() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getState() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getCountry() + ", "
                            + orderListOut.getOrderHistory().get(position_).getAddress().getPincode();
                    customerAddress.setText(address);
                }
            }
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lang = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void checkLocationService() {
        GPSTracker gps = new GPSTracker(CakeReachDropLocation.this);
        if (gps.canGetLocation()) {
            this.lat = gps.getLatitude();
            this.lang = gps.getLongitude();
            try {
                addresses = geocoder.getFromLocation(lat, lang, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("latitude", "" + this.lat);
            Log.e("longitude", "" + this.lang);
        }
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (this.googleMap == null) {
            ((MapFragment) this.getFragmentManager().findFragmentById(R.id.vf_aaa_map)).getMapAsync(this);
            if (this.googleMap != null) {
                this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lang), 17.0f));
        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(new LatLng(lat,
                lang)).zoom(17.0f).build()));
        map.setMapType(1);
        //map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_in_night));
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            map.getUiSettings().setZoomControlsEnabled(true);
//            map.getUiSettings().set
            LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));
            if (location != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17.0f));
                map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(17.0f).build()));
            }
            createMarker(lat, lang, "", "", R.drawable.map_2, map);
            this.googleMap = map;

            try {
                addresses = geocoder.getFromLocation(lat, lang, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID, GoogleMap map) {

        return map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        createMarker(marker.getPosition().latitude, marker.getPosition().longitude, marker.getTitle(), marker.getSnippet(), 0, googleMap);
        createMarkerPoint(marker.getPosition().latitude, marker.getPosition().longitude);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        createMarker(marker.getPosition().latitude, marker.getPosition().longitude, marker.getTitle(), marker.getSnippet(), 0, googleMap);
        createMarkerPoint(marker.getPosition().latitude, marker.getPosition().longitude);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        createMarker(marker.getPosition().latitude, marker.getPosition().longitude, marker.getTitle(), marker.getSnippet(), 0, googleMap);
        createMarkerPoint(marker.getPosition().latitude, marker.getPosition().longitude);
    }

    public void createMarkerPoint(double latitude, double longitude){
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
