package com.geekhive.foodeydeliveryboy.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.core.app.ActivityCompat;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.orderHistory.OrderHistory;
import com.geekhive.foodeydeliveryboy.beans.otpverify.VerifyOtp;
import com.geekhive.foodeydeliveryboy.trackingbackground.Servicecall;
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
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RestaurantReachActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, OnResponseListner, LocationListener {
    SlideToActView pickup;
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
    ImageView vI_adeot_image;
    TextView vT_adeot_name;
    TextView vT_adeot_place;
    TextView vT_adeot_status;
    OrderHistory orderListOut;
    double latitude, longitude;
    LocationManager locationManager;
    String mprovider;
    private final int CALL_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reach_restaurant_activity);
        mDetector = new ConnectionDetector(this);
        lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        lang = Double.parseDouble(getIntent().getStringExtra("lang"));
        position_ = getIntent().getIntExtra("position", 0);
        vI_adeot_image = findViewById(R.id.vI_adeot_image);
        vT_adeot_name = findViewById(R.id.vT_adeot_name);
        vT_adeot_place = findViewById(R.id.vT_adeot_place);
        vT_adeot_status = findViewById(R.id.vT_adeot_status);
        pickup = findViewById(R.id.pickup);
        need_help = findViewById(R.id.need_help);
        pickup.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                //overridePendingTransition(R.anim.left_in, R.anim.left_out);
                /*InitializeOtppopup();
                initializeOtpPopup();*/
                if (Math.floor(lat * 1000) == Math.floor(Double.parseDouble(orderListOut.getOrderHistory().get(position_).getAddress().getLatitude() + "") * 1000) || Math.floor(lang * 1000) == Math.floor(Double.parseDouble(orderListOut.getOrderHistory().get(position_).getAddress().getLongitude() + "") * 1000)) {
                    InitializeOtppopup();
                    initializeOtpPopup();
                } else {
                    SnackBar.makeText(RestaurantReachActivity.this, "Reach the location to confirm order", SnackBar.LENGTH_SHORT).show();
                    pickup.resetSlider();
                }
            }
        });

        vT_adeot_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber();
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

                    createMarker(lat, lang, "", "", R.drawable.map_2, googleMap);
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


    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).OrderHistory(WebServices.Foodey_Services,
                    WebServices.ApiType.restaurantList, Prefs.getUserId(this));
        }

    }

    private void CallOtpService(String otp, String order_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).VerifyDeliveryOtp(WebServices.Foodey_Services,
                    WebServices.ApiType.otpD, otp, Prefs.getUserId(this), order_id);
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.restaurantList) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                orderListOut = (OrderHistory) response;
                if (!isSucces || orderListOut == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    latitude = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getResturant().getLat());
                    longitude = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getResturant().getLong());
                    vT_adeot_name.setText(orderListOut.getOrderHistory().get(position_).getResturant().getName());
                    vT_adeot_place.setText(orderListOut.getOrderHistory().get(position_).getResturant().getAddress());

                }
            }
        }
        if (apiType == WebServices.ApiType.otpD) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                VerifyOtp verifyOtp = (VerifyOtp) response;
                if (!isSucces || verifyOtp == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (verifyOtp.getMessage() != null) {
                        if (!verifyOtp.getMessage().isEmpty()) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            calendar.add(Calendar.SECOND, 1);
                            Intent intent = new Intent(getApplicationContext(), Servicecall.class);
                            intent.putExtra("order_id", orderListOut.getOrderHistory().get(position_).getCart().getOrderId());
                            intent.putExtra("latitude", String.valueOf(latitude));
                            intent.putExtra("longitude", String.valueOf(longitude));
                            PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1212, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            if (alarmManager != null) {
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5000, pendingIntent);
                            }
                            startService(intent);
                            Intent intenti = new Intent(RestaurantReachActivity.this, PickOrderActivity.class);
                            intenti.putExtra("position", position_);
                            intenti.putExtra("lat", orderListOut.getOrderHistory().get(position_).getResturant().getLat());
                            intenti.putExtra("lang", orderListOut.getOrderHistory().get(position_).getResturant().getLong());
                            startActivity(intenti);
                        } else {
                            SnackBar.makeText(this, "Please check otp entered", SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(this, "Seems like otp entered is incorrect", SnackBar.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }

    private void InitializeOtppopup() {
        OtpPopup = new Dialog(this);
        OtpPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        OtpPopup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        OtpPopup.setContentView(R.layout.otp_submit_popup);
        OtpPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        OtpPopup.setCancelable(false);
        OtpPopup.setCanceledOnTouchOutside(false);
    }

    private void initializeOtpPopup() {
        OtpPopup.setContentView(R.layout.otp_submit_popup);
        OtpPopup.setCancelable(false);
        OtpPopup.setCanceledOnTouchOutside(false);
        OtpPopup.show();

        final TextView need_help = OtpPopup.findViewById(R.id.need_help);
        final Button submit_otp = OtpPopup.findViewById(R.id.submit_otp);
        final EditText etOtp = OtpPopup.findViewById(R.id.etOtp);
        final TextView otpId = OtpPopup.findViewById(R.id.otpId);
        otpId.setText(orderListOut.getOrderHistory().get(position_).getCart().getOrderId());


        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        OtpPopup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        OtpPopup.getWindow().setGravity(Gravity.BOTTOM);


        need_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //vT_instructions_txt.setVisibility(View.VISIBLE);

                if (OtpPopup.isShowing()) {
                    OtpPopup.dismiss();
                    Intent intent = new Intent(RestaurantReachActivity.this, GetOTPActivity.class);
                    intent.putExtra("position", position_);
                    intent.putExtra("lat", orderListOut.getOrderHistory().get(position_).getResturant().getLat());
                    intent.putExtra("lang", orderListOut.getOrderHistory().get(position_).getResturant().getLong());
                    startActivity(intent);
                }


            }
        });
        submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OtpPopup.isShowing()) {
                    OtpPopup.dismiss();
                    if (!etOtp.getText().toString().equals("") || !etOtp.getText().toString().isEmpty()) {
                        CallOtpService(etOtp.getText().toString(), orderListOut.getOrderHistory().get(position_).getCart().getOrderId());
                    }
                }
            }

        });
        OtpPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        OtpPopup.setCancelable(true);
        OtpPopup.setCanceledOnTouchOutside(true);
        OtpPopup.show();
    }

    private void checkLocationService() {
        GPSTracker gps = new GPSTracker(RestaurantReachActivity.this);
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

    public void createMarkerPoint(double latitude, double longitude) {
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

    public void doMap(View view) {
        double lat = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getResturant().getLat());
        double longi = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getResturant().getLong());
        String label = orderListOut.getOrderHistory().get(position_).getResturant().getName();
        String uriBegin = "geo:" + lat + "," + longi;
        String query = lat + "," + longi + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(mapIntent);
    }

    public void doMapCust(View view) {
        double lat = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getAddress().getLatitude() + "");
        double longi = Double.parseDouble(orderListOut.getOrderHistory().get(position_).getAddress().getLongitude() + "");
        String label = orderListOut.getOrderHistory().get(position_).getUser().getFirstname();
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = lat + "," + longi + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(mapIntent);
    }

    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(RestaurantReachActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST);

                    return;
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + orderListOut.getOrderHistory().get(position_).getResturant().getPhone()));
                    startActivity(callIntent);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == CALL_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + orderListOut.getOrderHistory().get(position_).getResturant().getPhone()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            } else {
                Toast.makeText(RestaurantReachActivity.this, "Please allow calling permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
