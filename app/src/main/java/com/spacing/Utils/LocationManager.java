package com.spacing.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.spacing.SpacingApp;


public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback {
    private static final String TAG = LocationManager.class.getName();

    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    private Location mLastLocation;
    private LocationManagerInterface mLocationManagerInterface;
    private LocationRequest mLocationRequest;
    private static LocationManager mInstance = null;

    private LocationManager() {
        mContext = SpacingApp.getContext();

        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }
    }

    public static LocationManager getInstance() {
        return new LocationManager();
    }

    public void setLocationManagerInterface(LocationManagerInterface locationManagerInterface) {
        mLocationManagerInterface = locationManagerInterface;
    }

    @Override
    public void onResult(Result result) {

    }

    public interface LocationManagerInterface<Location> {
        void onLocationUpdated(Location location);
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        if (mLocationManagerInterface != null) {
            mLocationManagerInterface.onLocationUpdated(location);
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }
            });
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setSmallestDisplacement(50);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void startLocationUpdates() {

        if (mLocationRequest == null) {
            createLocationRequest();
        }
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    public Location getLastLocation() {
        if (mGoogleApiClient != null) {
            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
        }

        return mLastLocation;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Connected");

        startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: " + connectionResult.toString());
    }

}
