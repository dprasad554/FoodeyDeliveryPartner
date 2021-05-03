package com.geekhive.foodeydeliveryboy.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.geekhive.foodeydeliveryboy.R;
import com.geekhive.foodeydeliveryboy.beans.citylist.CityList;
import com.geekhive.foodeydeliveryboy.beans.signup.SignupOut;
import com.geekhive.foodeydeliveryboy.beans.superadminlist.SuperAdminList;
import com.geekhive.foodeydeliveryboy.utils.ConnectionDetector;
import com.geekhive.foodeydeliveryboy.utils.OnResponseListner;
import com.geekhive.foodeydeliveryboy.utils.SnackBar;
import com.geekhive.foodeydeliveryboy.utils.WebServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import static com.geekhive.foodeydeliveryboy.utils.util.IMAGE_DIRECTORY_NAME;
import static com.geekhive.foodeydeliveryboy.utils.util.REQUEST_FOR_STORAGE_PERMISSION;
import static com.geekhive.foodeydeliveryboy.utils.util.REQUEST_TAKE_PHOTO_ADHAR_BACK;
import static com.geekhive.foodeydeliveryboy.utils.util.REQUEST_TAKE_PHOTO_ADHAR_FRONT;
import static com.geekhive.foodeydeliveryboy.utils.util.REQUEST_TAKE_PHOTO_DRIVING;
import static com.geekhive.foodeydeliveryboy.utils.util.REQUEST_TAKE_PHOTO_PROFILE;
import static com.geekhive.foodeydeliveryboy.utils.util.REQUEST_TAKE_PHOTO_RESIDENCE;
import static com.geekhive.foodeydeliveryboy.utils.util.SELECT_FILE_ADHAR_BACK;
import static com.geekhive.foodeydeliveryboy.utils.util.SELECT_FILE_ADHAR_FRONT;
import static com.geekhive.foodeydeliveryboy.utils.util.SELECT_FILE_DRIVING;
import static com.geekhive.foodeydeliveryboy.utils.util.SELECT_FILE_PROFILE;
import static com.geekhive.foodeydeliveryboy.utils.util.SELECT_FILE_RESIDENCE;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    EditText et_DBFirstName, et_DBLastName, et_DBMobile, et_DBEmail, et_DBPassword, et_DBCPassword, et_bikeno;
    ImageView iv_AddImageFront, iv_uploadFront, iv_AddImageBack, iv_uploadBack, iv_AddImageDriving, iv_uploadDriving, iv_AddImageResidence, iv_uploadResidence, iv_AddProfilePic, iv_uploadProfile;
    String fileNameAdharFront, fileNameAdharBack, fileNameDriving, fileNameResidence, fileNameProfilePic;
    DialogInterface dialog;
    private String filePathAdharFront = "";
    private String filePathAdharBack = "";
    private String filePathDriving = "";
    private String filePathResidence = "";
    private String filePathProfile = "";

    private float minScale = 1f;
    private File mFileTemp;
    private String currentDateandTime = "";
    private String mImagePath = null;
    private Uri mSaveUri = null;
    private Uri mImageUri = null;
    private ContentResolver mContentResolver;
    //for camera permission
    private boolean isDeleted = false;
    Bitmap bitmap;
    Bitmap bitmapFromGallery;
    Uri fileUri;
    String mFileNameGallery;
    private String uriSting;

    ConnectionDetector mDetector;

    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;
    double lat;
    double lang;
    String lati;
    String longi;
    List<Address> addresses;
    Geocoder geocoder;
    boolean GpsStatus;
    private static final int REQUEST_CODE = 101;
    String city = "";
    Spinner spnCity,admin;
    ArrayList<String> categoryName;
    CityList cityList;
    SuperAdminList superAdminList;
    ArrayList<String> adminlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        geocoder = new Geocoder(this, Locale.getDefault());
        //Map Current Location Work
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mDetector = new ConnectionDetector(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.deliveryBoy));

        final TextView fronttitle = (TextView) findViewById(R.id.tv_Image);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-SemiBold.ttf");
        fronttitle.setTypeface(myCustomFont);

        final TextView front = (TextView) findViewById(R.id.tv_FrontSide);
        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-SemiBold.ttf");
        front.setTypeface(myCustomFont1);

        final TextView back = (TextView) findViewById(R.id.tv_backSide);
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-SemiBold.ttf");
        back.setTypeface(myCustomFont2);

        final TextView driving = (TextView) findViewById(R.id.tv_ImageDriving);
        Typeface myCustomFont3 = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-SemiBold.ttf");
        driving.setTypeface(myCustomFont3);

        final TextView Residence = (TextView) findViewById(R.id.tv_ImageResidence);
        Typeface myCustomFont4 = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-SemiBold.ttf");
        Residence.setTypeface(myCustomFont4);

        spnCity = findViewById(R.id.spnCity);
        admin = findViewById(R.id.admin);
        et_DBFirstName = findViewById(R.id.et_DBFirstName);
        et_DBLastName = findViewById(R.id.et_DBLastName);
        et_DBMobile = findViewById(R.id.et_DBMobile);
        et_DBEmail = findViewById(R.id.et_DBEmail);
        et_DBPassword = findViewById(R.id.et_DBPassword);
        et_DBCPassword = findViewById(R.id.et_DBCPassword);
        et_bikeno = findViewById(R.id.et_bikeno);
        iv_AddImageFront = findViewById(R.id.iv_AddImageFront);
        iv_uploadFront = findViewById(R.id.iv_uploadFront);
        iv_AddImageBack = findViewById(R.id.iv_AddImageBack);
        iv_uploadBack = findViewById(R.id.iv_uploadBack);
        iv_AddProfilePic = findViewById(R.id.iv_AddProfilePic);
        iv_uploadProfile = findViewById(R.id.iv_uploadProfile);

        iv_AddImageDriving = findViewById(R.id.iv_AddImageDriving);
        iv_uploadDriving = findViewById(R.id.iv_uploadDriving);
        iv_AddImageResidence = findViewById(R.id.iv_AddImageResidence);
        iv_uploadResidence = findViewById(R.id.iv_uploadResidence);

        iv_AddImageFront.setOnClickListener(this);
        iv_uploadFront.setOnClickListener(this);
        iv_AddImageBack.setOnClickListener(this);
        iv_uploadBack.setOnClickListener(this);
        iv_AddImageDriving.setOnClickListener(this);
        iv_uploadDriving.setOnClickListener(this);
        iv_AddImageResidence.setOnClickListener(this);
        iv_uploadResidence.setOnClickListener(this);
        iv_AddProfilePic.setOnClickListener(this);
        iv_uploadProfile.setOnClickListener(this);

        fetchLastLocation();
        CallCityList();
        GetAdminList();
    }

    private void fetchLastLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                return;
            } else {
                Task<Location> task = fusedLocationProviderClient.getLastLocation();
                task.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            try {
                                currentLocation = location;
                                lat = currentLocation.getLatitude();
                                lang = currentLocation.getLongitude();
                                lati = String.valueOf(lat);
                                longi = String.valueOf(lang);
                                addresses = geocoder.getFromLocation(lat, lang, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                if (addresses.size() != 0) {
                                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    city = addresses.get(0).getLocality();
                                } else {
                                    fetchLastLocation();
                                }

                                //CallSliderService();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } else {
            GPSStatus();
        }
    }

    public void GPSStatus() {
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (GpsStatus) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        try {
                            currentLocation = location;
                            lat = currentLocation.getLatitude();
                            lang = currentLocation.getLongitude();
                            lati = String.valueOf(lat);
                            longi = String.valueOf(lang);
                            addresses = geocoder.getFromLocation(lat, lang, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            city = addresses.get(0).getLocality();
                            //CallSliderService();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            new AlertDialog.Builder(this).setTitle("Location Permission").setMessage("Please enable location permission!")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent1);
                            dialog.dismiss();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void doSignUp(View view) {
        if (categoryName.size() != 0){
            for (int i=0; i<cityList.getCity().size(); i++){
                if (cityList.getCity().get(i).getCityName().equals(spnCity.getSelectedItem().toString())){
                    if (adminlist.size() != 0){
                        for (int j=0; j<superAdminList.getSuperUser().size(); j++){
                            if (superAdminList.getSuperUser().get(j).getFirstname().equals(admin.getSelectedItem().toString())){
                                CallSignUp(cityList.getCity().get(i).getId(),superAdminList.getSuperUser().get(j).getId());
                            }

                        }
                    }
                }
            }
        }

        //CallCityList();
        /*if (!(et_DBFirstName.getText().toString().equals("")) || !(et_DBFirstName.getText().toString().isEmpty())) {
            if (!(et_DBLastName.getText().toString().equals("")) || !(et_DBLastName.getText().toString().isEmpty())) {
                if (!(et_DBMobile.getText().toString().equals("")) || !(et_DBMobile.getText().toString().isEmpty())) {
                    if (!(et_DBEmail.getText().toString().equals("")) || !(et_DBEmail.getText().toString().isEmpty())) {
                        if (!(et_DBPassword.getText().toString().equals("")) || !(et_DBPassword.getText().toString().isEmpty())) {
                            if (!(et_DBCPassword.getText().toString().equals("")) || !(et_DBCPassword.getText().toString().isEmpty())) {
                                if ((et_DBPassword.getText().toString().equals(et_DBCPassword.getText().toString()))) {
                                    if (!(et_bikeno.getText().toString().equals("")) || !(et_bikeno.getText().toString().isEmpty())) {
                                        if (!filePathAdharFront.equals("")) {
                                            if (!filePathAdharBack.equals("")) {
                                                if (!filePathDriving.equals("")) {
                                                    if (!filePathResidence.equals("")) {
                                                        if (!filePathProfile.equals("")) {
                                                            if (this.mDetector.isConnectingToInternet()) {
                                                                new WebServices(this).SignUp(WebServices.Foodey_Services,
                                                                        WebServices.ApiType.signup, et_DBFirstName.getText().toString().trim(), et_DBLastName.getText().toString().trim(),
                                                                        et_DBMobile.getText().toString().trim(), et_DBEmail.getText().toString().trim(), et_DBPassword.getText().toString().trim(),
                                                                        et_bikeno.getText().toString().trim(), "6", fileNameAdharFront, fileNameAdharBack, fileNameDriving, fileNameResidence, fileNameProfilePic,
                                                                        filePathAdharFront, filePathAdharBack, filePathDriving, filePathResidence, filePathProfile);
                                                            } else {
                                                                SnackBar.makeText(this, getResources().getString(R.string.no_internet), SnackBar.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            SnackBar.makeText(this, "Please upload Residence", SnackBar.LENGTH_SHORT).show();
                                                        }

                                                    } else {
                                                        SnackBar.makeText(this, "Please upload Residence", SnackBar.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    SnackBar.makeText(this, "Please upload Driving License", SnackBar.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                SnackBar.makeText(this, "Please upload Adhar Back", SnackBar.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            SnackBar.makeText(this, "Please upload Adhar Front", SnackBar.LENGTH_SHORT).show();
                                        }

                                    }else {
                                        et_bikeno.setError("Please enter vehcile no");
                                    }
                                } else {
                                    et_DBCPassword.setError("Password mismatch");
                                }
                            } else {
                                et_DBCPassword.setError("Please enter confirm password");
                            }
                        } else {
                            et_DBPassword.setError("Please enter password");
                        }
                    } else {
                        et_DBEmail.setError("Please enter email");
                    }
                } else {
                    et_DBMobile.setError("Please enter mobile number");
                }
            } else {
                et_DBLastName.setError("Please enter last name");
            }
        } else {
            et_DBFirstName.setError("Please enter first name");
        }*/
    }

    private void CallSignUp(String city_id,String sub_admin_id){
        if (!(et_DBFirstName.getText().toString().equals("")) || !(et_DBFirstName.getText().toString().isEmpty())) {
            if (!(et_DBLastName.getText().toString().equals("")) || !(et_DBLastName.getText().toString().isEmpty())) {
                if (!(et_DBMobile.getText().toString().equals("")) || !(et_DBMobile.getText().toString().isEmpty())) {
                    if (!(et_DBEmail.getText().toString().equals("")) || !(et_DBEmail.getText().toString().isEmpty())) {
                        if (!(et_DBPassword.getText().toString().equals("")) || !(et_DBPassword.getText().toString().isEmpty())) {
                            if (!(et_DBCPassword.getText().toString().equals("")) || !(et_DBCPassword.getText().toString().isEmpty())) {
                                if ((et_DBPassword.getText().toString().equals(et_DBCPassword.getText().toString()))) {
                                    if (!(et_bikeno.getText().toString().equals("")) || !(et_bikeno.getText().toString().isEmpty())) {
                                        if (!filePathAdharFront.equals("")) {
                                            if (!filePathAdharBack.equals("")) {
                                                if (!filePathDriving.equals("")) {
                                                    if (!filePathResidence.equals("")) {
                                                        if (!filePathProfile.equals("")) {
                                                            if (this.mDetector.isConnectingToInternet()) {
                                                               /* startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                                SignUpActivity.this.finish();*/
                                                                new WebServices(this).SignUp(WebServices.Foodey_Services,
                                                                        WebServices.ApiType.signup, et_DBFirstName.getText().toString().trim(), et_DBLastName.getText().toString().trim(),
                                                                        et_DBMobile.getText().toString().trim(), et_DBEmail.getText().toString().trim(), et_DBPassword.getText().toString().trim(),
                                                                        et_bikeno.getText().toString().trim(),city_id, sub_admin_id, fileNameAdharFront, fileNameAdharBack, fileNameDriving, fileNameResidence, fileNameProfilePic,
                                                                        filePathAdharFront, filePathAdharBack, filePathDriving, filePathResidence, filePathProfile);
                                                            } else {
                                                                SnackBar.makeText(this, getResources().getString(R.string.no_internet), SnackBar.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            SnackBar.makeText(this, "Please upload Residence", SnackBar.LENGTH_SHORT).show();
                                                        }

                                                    } else {
                                                        SnackBar.makeText(this, "Please upload Residence", SnackBar.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    SnackBar.makeText(this, "Please upload Driving License", SnackBar.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                SnackBar.makeText(this, "Please upload Adhar Back", SnackBar.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            SnackBar.makeText(this, "Please upload Adhar Front", SnackBar.LENGTH_SHORT).show();
                                        }

                                    }else {
                                        et_bikeno.setError("Please enter vehcile no");
                                    }
                                } else {
                                    et_DBCPassword.setError("Password mismatch");
                                }
                            } else {
                                et_DBCPassword.setError("Please enter confirm password");
                            }
                        } else {
                            et_DBPassword.setError("Please enter password");
                        }
                    } else {
                        et_DBEmail.setError("Please enter email");
                    }
                } else {
                    et_DBMobile.setError("Please enter mobile number");
                }
            } else {
                et_DBLastName.setError("Please enter last name");
            }
        } else {
            et_DBFirstName.setError("Please enter first name");
        }
    }

    private void CallCityList() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetCityList(WebServices.Foodey_Services,
                    WebServices.ApiType.citylist);
            return;
        }
    }

    //GetSuperAdminList
    private void GetAdminList() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).GetSuperAdminList(WebServices.Foodey_Services,
                    WebServices.ApiType.adminlist);
            return;
        }
    }


    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.signup) {
            if (!isSucces) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {

                SignupOut signupOut = (SignupOut) response;
                if (!isSucces || signupOut == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(this, signupOut.getMessage(), SnackBar.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        } else if (apiType == WebServices.ApiType.citylist) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                cityList = (CityList) response;
                if (cityList != null) {
                    if (cityList.getCity() != null) {
                        categoryName = new ArrayList<>();
                        for (int i=0; i<cityList.getCity().size(); i++){
                            categoryName.add(cityList.getCity().get(i).getCityName());
                        }

                        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryName);
                        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spnCity.setAdapter(categoryAdapter);
                        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                    }

                } else {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }

            }
        }else if (apiType == WebServices.ApiType.adminlist) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                superAdminList = (SuperAdminList) response;
                if (superAdminList != null) {
                    if (superAdminList.getSuperUser() != null) {
                        adminlist = new ArrayList<>();
                        for (int i=0; i<superAdminList.getSuperUser().size(); i++){
                            adminlist.add(superAdminList.getSuperUser().get(i).getFirstname());
                        }

                        ArrayAdapter adminAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, adminlist);
                        adminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        admin.setAdapter(adminAdapter);
                        admin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                    }

                } else {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_AddImageFront:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_ADHAR_FRONT, REQUEST_TAKE_PHOTO_ADHAR_FRONT);
                }
                break;
            case R.id.iv_uploadFront:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_ADHAR_FRONT, REQUEST_TAKE_PHOTO_ADHAR_FRONT);
                }
                break;
            case R.id.iv_AddImageBack:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_ADHAR_BACK, REQUEST_TAKE_PHOTO_ADHAR_BACK);
                }
                break;
            case R.id.iv_uploadBack:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_ADHAR_BACK, REQUEST_TAKE_PHOTO_ADHAR_BACK);
                }
                break;
            case R.id.iv_AddImageDriving:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_DRIVING, REQUEST_TAKE_PHOTO_DRIVING);
                }
                break;
            case R.id.iv_uploadDriving:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_DRIVING, REQUEST_TAKE_PHOTO_DRIVING);
                }
                break;
            case R.id.iv_AddImageResidence:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_RESIDENCE, REQUEST_TAKE_PHOTO_RESIDENCE);
                }
                break;
            case R.id.iv_uploadResidence:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_RESIDENCE, REQUEST_TAKE_PHOTO_RESIDENCE);
                }
                break;

            case R.id.iv_AddProfilePic:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_PROFILE, REQUEST_TAKE_PHOTO_PROFILE);
                }
                break;
            case R.id.iv_uploadProfile:
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_PROFILE, REQUEST_TAKE_PHOTO_PROFILE);
                }
                break;
        }
    }

    private boolean mayRequestGalleryImages() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        requestPermissions(
                new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                REQUEST_FOR_STORAGE_PERMISSION);
        return false;
    }

    /**
     * This method calls if user if a user has denied a permission and selected the
     * Don't ask again option in the permission request dialog,
     * or if a device policy prohibits the permission
     */
    private void showPermissionRationaleSnackBar() {

        Snackbar.make(findViewById(android.R.id.content),
                "Please Grant Permissions",
                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(SignUpActivity.this,
                                new String[]{Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                REQUEST_FOR_STORAGE_PERMISSION);
                    }
                }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FOR_STORAGE_PERMISSION:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                        //selectImage();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale
                                (this, Manifest.permission.CAMERA)) {

                            showPermissionRationaleSnackBar();

                        } else {
                            Toast.makeText(this, "Go to settings and enable permission", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                break;

        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
        }

    }

    /**
     * This method shows Dialogue to select image from camera or Gallery
     */
    private void selectImage(final int selectfile, final int takephoto) {
        View view = getLayoutInflater().inflate(R.layout.take_image_popup, null);
        dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);
        final TextView chooseFromGallery = (TextView) view.findViewById(R.id.choose_from_gallery);
        final TextView tekePhoto = (TextView) view.findViewById(R.id.take_photo);
        final TextView select_photo = (TextView) view.findViewById(R.id.select_photo);
        final TextView cancel = (TextView) view.findViewById(R.id.cancel);
        new Runnable() {
            public void run() {
            }
        }.run();
        chooseFromGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), selectfile);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        tekePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(takephoto);
                    } catch (IOException ex) {
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = null;
                        try {
                            photoURI = FileProvider.getUriForFile(SignUpActivity.this, getApplicationContext().getPackageName() + ".provider", createImageFile(takephoto));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, takephoto);
                    }
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.show();
    }

    /**
     * This method uses to create image file to store the image in phone gallery
     */
    private File createImageFile(int phot) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "deliveryboy" + timeStamp + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (phot == SELECT_FILE_ADHAR_FRONT || phot == REQUEST_TAKE_PHOTO_ADHAR_FRONT) {
            filePathAdharFront = storageDir.getAbsolutePath() + "/" + imageFileName;
            File file = new File(filePathAdharFront);
            return file;
        } else if (phot == SELECT_FILE_ADHAR_BACK || phot == REQUEST_TAKE_PHOTO_ADHAR_BACK) {
            filePathAdharBack = storageDir.getAbsolutePath() + "/" + imageFileName;
            File file = new File(filePathAdharBack);
            return file;
        } else if (phot == SELECT_FILE_DRIVING || phot == REQUEST_TAKE_PHOTO_DRIVING) {
            filePathDriving = storageDir.getAbsolutePath() + "/" + imageFileName;
            File file = new File(filePathDriving);
            return file;
        } else if (phot == SELECT_FILE_RESIDENCE || phot == REQUEST_TAKE_PHOTO_RESIDENCE) {
            filePathResidence = storageDir.getAbsolutePath() + "/" + imageFileName;
            File file = new File(filePathResidence);
            return file;
        } else if (phot == SELECT_FILE_PROFILE || phot == REQUEST_TAKE_PHOTO_PROFILE) {
            filePathProfile = storageDir.getAbsolutePath() + "/" + imageFileName;
            File file = new File(filePathProfile);
            return file;
        } else {
            return null;
        }


    }

    /**
     * This method uses to decode file and
     * fetch the image
     */
    private void onSelectFromGalleryResult(Intent data, int resultImage) {

        Uri selectedImageUri = data.getData();
        String selectedImagePath = getRealPathFromURI(selectedImageUri);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        int scale = 1;
        while ((options.outWidth / scale) / 2 >= ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION && (options.outHeight / scale) / 2 >= ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            scale *= 2;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        compressImage(selectedImagePath, 2);
        Bitmap bm = BitmapFactory.decodeFile(uriSting, options);
        fetchImageName(selectedImagePath, resultImage);
    }

    /**
     * This method uses to get real path of selected
     * gallery image
     *
     * @param contentURI - uri path of image
     */
    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID);
            String path = cursor.getString(idx);
            cursor.close();
            return path;
        }
    }


    /**
     * This method uses to get file path and
     * file name
     *
     * @param selectedImagePath - image path
     */
    private void fetchImageName(String selectedImagePath, int id) {
        mFileNameGallery = "";
        StringTokenizer st = new StringTokenizer(selectedImagePath, "/");
        while (st.hasMoreTokens()) {
            mFileNameGallery = st.nextToken().toString();
        }
        if (id == SELECT_FILE_ADHAR_FRONT) {
            filePathAdharFront = selectedImagePath;
            fileNameAdharFront = mFileNameGallery;
        } else if (id == SELECT_FILE_ADHAR_BACK) {
            filePathAdharBack = selectedImagePath;
            fileNameAdharBack = mFileNameGallery;
        } else if (id == SELECT_FILE_DRIVING) {
            filePathDriving = selectedImagePath;
            fileNameDriving = mFileNameGallery;
        } else if (id == SELECT_FILE_RESIDENCE) {
            filePathResidence = selectedImagePath;
            fileNameResidence = mFileNameGallery;
        } else if (id == SELECT_FILE_PROFILE) {
            filePathProfile = selectedImagePath;
            fileNameProfilePic = mFileNameGallery;
        }

        selectedImagePath = selectedImagePath;
    }

    /**
     * This method uses to compress
     * the image without losing the quantity of image
     *
     * @param imageUri - image Uri path
     * @param flag     - scale type
     */
    public String compressImage(String imageUri, int flag) {
        int actualHeight = 1;
        int actualWidth = 1;
        String filename;
        OutputStream outputStream;
        FileNotFoundException e;
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        if (options.outHeight == 0){
            actualHeight = 1;
        } else {
            actualHeight = options.outHeight;
        }

        if (options.outWidth == 0){
            actualWidth = 1;
        } else {
            actualHeight = options.outWidth;
        }
        float imgRatio = (float) (actualWidth / actualHeight);
        float maxRatio = 612.0f / 816.0f;
        if (((float) actualHeight) > 816.0f || ((float) actualWidth) > 612.0f) {
            if (imgRatio < maxRatio) {
                actualWidth = (int) (((float) actualWidth) * (816.0f / ((float) actualHeight)));
                actualHeight = (int) 816.0f;
            } else if (imgRatio > maxRatio) {
                actualHeight = (int) (((float) actualHeight) * (612.0f / ((float) actualWidth)));
                actualWidth = (int) 612.0f;
            } else {
                actualHeight = (int) 816.0f;
                actualWidth = (int) 612.0f;
            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16384];
        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
            bitmapFromGallery = bmp;
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception2) {
            exception2.printStackTrace();
        }
        float ratioX = ((float) actualWidth) / ((float) options.outWidth);
        float ratioY = ((float) actualHeight) / ((float) options.outHeight);
        float middleX = ((float) actualWidth) / 2.0f;
        float middleY = ((float) actualHeight) / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        if (bmp != null){
            canvas.drawBitmap(bmp, middleX - ((float) (bmp.getWidth() / 2)), middleY - ((float) (bmp.getHeight() / 2)), new Paint(2));
        }
        /*canvas.drawBitmap(bmp, middleX - ((float) (bmp.getWidth() / 2)), middleY - ((float) (bmp.getHeight() / 2)), new Paint(2));*/
        try {
            int orientation = new ExifInterface(filePath).getAttributeInt("Orientation", 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90.0f);
            } else if (orientation == 3) {
                matrix.postRotate(180.0f);
            } else if (orientation == 8) {
                matrix.postRotate(270.0f);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (flag == 1) {
            filename = imageUri;
        } else {
            filename = getFilename();
        }
        try {
            OutputStream fileOutputStream = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            outputStream = fileOutputStream;
        } catch (FileNotFoundException e4) {
            e = e4;
            e.printStackTrace();
            return filename;
        }
        return filename;
    }

    /**
     * This method uses to create and
     * get file path
     */
    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        uriSting = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
        return uriSting;
    }

    /**
     * This method uses to get path uri
     * from internal storage
     *
     * @param contentURI - image uri
     */
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        }
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("_ID"));
    }

    /**
     * This method uses to calculate the size of image
     * from bitmap factory
     *
     * @param options   - Bitmap Factory options
     * @param reqWidth  - Width of image
     * @param reqHeight - height of image
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(((float) height) / ((float) reqHeight));
            int widthRatio = Math.round(((float) width) / ((float) reqWidth));
            if (heightRatio < widthRatio) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = widthRatio;
            }
        }
        while (((float) (width * height)) / ((float) (inSampleSize * inSampleSize)) > ((float) ((reqWidth * reqHeight) * 2))) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        if (requestCode == 100) {
            if (mDetector.isConnectingToInternet()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                String[] fileTemp = fileUri.getPath().split("/");
                /*fileName = fileTemp[fileTemp.length - 1];
                filePath = fileUri.getPath();*/
                compressImage(fileUri.getPath(), 1);

                //vI_ad_image_display.setImageBitmap(bitmap);

                return;
            }
            SnackBar.makeText((Context) this, (int) R.string.no_internet, SnackBar.LENGTH_SHORT).show();
        } else if (requestCode == SELECT_FILE_ADHAR_FRONT) {
            onSelectFromGalleryResult(data, SELECT_FILE_ADHAR_FRONT);
            if (mDetector.isConnectingToInternet()) {
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddImageFront.setImageBitmap(bitmapFromGallery);
            } else {
                SnackBar.makeText((Context) this, (int) R.string.no_internet, SnackBar.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO_ADHAR_FRONT && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView

            String[] fileTemp = filePathAdharFront.split("/");
            fileNameAdharFront = fileTemp[fileTemp.length - 1];
            Uri imageUri = Uri.parse(filePathAdharFront);
            filePathAdharFront = imageUri.getPath();
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddImageFront.setImageBitmap(BitmapFactory.decodeStream(ims));

            } catch (FileNotFoundException e) {
                return;
            }

            MediaScannerConnection.scanFile(this,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        } else if (requestCode == SELECT_FILE_ADHAR_BACK) {
            onSelectFromGalleryResult(data, SELECT_FILE_ADHAR_BACK);
            if (mDetector.isConnectingToInternet()) {
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddImageBack.setImageBitmap(bitmapFromGallery);
            } else {
                SnackBar.makeText((Context) this, (int) R.string.no_internet, SnackBar.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO_ADHAR_BACK && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView

            String[] fileTemp = filePathAdharBack.split("/");
            fileNameAdharBack = fileTemp[fileTemp.length - 1];
            Uri imageUri = Uri.parse(filePathAdharBack);
            filePathAdharBack = imageUri.getPath();
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddImageBack.setImageBitmap(BitmapFactory.decodeStream(ims));

            } catch (FileNotFoundException e) {
                return;
            }

            MediaScannerConnection.scanFile(this,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        } else if (requestCode == SELECT_FILE_DRIVING) {
            onSelectFromGalleryResult(data, SELECT_FILE_DRIVING);
            if (mDetector.isConnectingToInternet()) {
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddImageDriving.setImageBitmap(bitmapFromGallery);
            } else {
                SnackBar.makeText((Context) this, (int) R.string.no_internet, SnackBar.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO_DRIVING && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView

            String[] fileTemp = filePathDriving.split("/");
            fileNameDriving = fileTemp[fileTemp.length - 1];
            Uri imageUri = Uri.parse(filePathDriving);
            filePathDriving = imageUri.getPath();
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddImageDriving.setImageBitmap(BitmapFactory.decodeStream(ims));

            } catch (FileNotFoundException e) {
                return;
            }

            MediaScannerConnection.scanFile(this,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        } else if (requestCode == SELECT_FILE_RESIDENCE) {
            onSelectFromGalleryResult(data, SELECT_FILE_RESIDENCE);
            if (mDetector.isConnectingToInternet()) {
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddImageResidence.setImageBitmap(bitmapFromGallery);
            } else {
                SnackBar.makeText((Context) this, (int) R.string.no_internet, SnackBar.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO_RESIDENCE && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView

            String[] fileTemp = filePathResidence.split("/");
            fileNameResidence = fileTemp[fileTemp.length - 1];
            Uri imageUri = Uri.parse(filePathResidence);
            filePathResidence = imageUri.getPath();
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddImageResidence.setImageBitmap(BitmapFactory.decodeStream(ims));

            } catch (FileNotFoundException e) {
                return;
            }

            MediaScannerConnection.scanFile(this,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        } else if (requestCode == SELECT_FILE_PROFILE) {
            onSelectFromGalleryResult(data, SELECT_FILE_PROFILE);
            if (mDetector.isConnectingToInternet()) {
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddProfilePic.setImageBitmap(bitmapFromGallery);
            } else {
                SnackBar.makeText((Context) this, (int) R.string.no_internet, SnackBar.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO_PROFILE && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView

            String[] fileTemp = filePathProfile.split("/");
            fileNameProfilePic = fileTemp[fileTemp.length - 1];
            Uri imageUri = Uri.parse(filePathProfile);
            filePathProfile = imageUri.getPath();
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                iv_AddProfilePic.setImageBitmap(BitmapFactory.decodeStream(ims));

            } catch (FileNotFoundException e) {
                return;
            }

            MediaScannerConnection.scanFile(this,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }
    }
}
