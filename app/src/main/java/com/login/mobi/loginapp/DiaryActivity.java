package com.login.mobi.loginapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class DiaryActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static int REQUEST_LOCATION = 1;
    static public Location mLastLocation;
    static private OnCurrentLocationChangeListener mOnCurrentLocationChangeListener;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    protected TextView mOutput;
    protected Button mLocateButton;
    protected Button mMapFragButton;
    protected Button mMapViewButton;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Geocoder mGeocoder;

    public static void setOnCurrentLocationChangeListener(OnCurrentLocationChangeListener
                                                                  mOnCurrentLocationChangeListener) {
        DiaryActivity.mOnCurrentLocationChangeListener = mOnCurrentLocationChangeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        mLatitudeText = (TextView) findViewById((R.id.latitudeinput));
        mLongitudeText = (TextView) findViewById((R.id.longitudeinput));
        mLocateButton = (Button) findViewById(R.id.locate);
        mMapFragButton = (Button) findViewById(R.id.map_frag);
        mMapViewButton = (Button) findViewById(R.id.map_view);
        mOutput = (TextView) findViewById((R.id.output));

        mLatitudeText.setText("Latitude not available yet");
        mLongitudeText.setText("Longitude not available yet");
        mLocateButton.setEnabled(false);
        mMapFragButton.setEnabled(false);
        mMapViewButton.setEnabled(false);
        mOutput.setText("");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        Log.d("xxxx", "90");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("xxxx", "93");

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Log.d("xxxx", "99");

            if (mLastLocation != null) {
                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onConnected(null);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        if (mOnCurrentLocationChangeListener != null) {
            mOnCurrentLocationChangeListener.onCurrentLocationChange(location);
        }
    }

    public void onStartClicked(View v) {
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
            mLocateButton.setEnabled(true);
            mMapFragButton.setEnabled(true);
            mMapViewButton.setEnabled(true);
            mOutput.setText("GoogleApiClient has started. You can see the location icon in status bar");
        } else {
            mGoogleApiClient.disconnect();
            mLocateButton.setEnabled(false);
            mMapFragButton.setEnabled(false);
            mMapViewButton.setEnabled(false);
            mOutput.setText("GoogleApiClient has stopped. Location icon in status has gone.");
        }
    }

    public void onLocateClicked(View v) {
        mGeocoder = new Geocoder(this);
        try {
            List<Address> addresses = mGeocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);

            if (addresses.size() == 1) {
                Address address = addresses.get(0);
                StringBuilder addressLines = new StringBuilder();
                //see herehttps://stackoverflow
                // .com/questions/44983507/android-getmaxaddresslineindex-returns-0-for-line-1
                if (address.getMaxAddressLineIndex() > 0) {
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        addressLines.append(address.getAddressLine(i) + "\n");
                    }
                } else {
                    addressLines.append(address.getAddressLine(0));
                }
                mOutput.setText(addressLines);
            } else {
                mOutput.setText("WARNING! Geocoder returned more than 1 addresses!");
            }
        } catch (Exception e) {
            mOutput.setText("WARNING! Geocoder.getFromLocation() didn't work!");
        }
    }

    public void onMapFragClicked(View v) {
        startActivity(new Intent(this, MapFragActivity.class));
    }

    public void onMapViewClicked(View v) {
        startActivity(new Intent(this, MapFragActivity.class));
    }

    public interface OnCurrentLocationChangeListener {
        void onCurrentLocationChange(Location location);
    }

}
