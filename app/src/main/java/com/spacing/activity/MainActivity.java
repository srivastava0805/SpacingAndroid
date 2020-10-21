package com.spacing.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.spacing.R;
import com.spacing.SpacingApp;
import com.spacing.Utils.AskForPermissions;
import com.spacing.Utils.LocationManager;
import com.spacing.fragment.FragmentVerifyPhoneNumber;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationManager.LocationManagerInterface {

    private ConstraintLayout lookingForProperty;
    private ConstraintLayout lookingToPostProperty;
    private String address;
    private String city;
    private GotUpdateLocationInterface getCityCallback;
    private ProgressDialog loadingCityProgressBar;
    private String waitingMessage = "Please wait..";
    private String startBudget;
    private String endBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.looking_for_step1);
        findViews();
    }

    private void findViews() {
        lookingForProperty = findViewById(R.id.constraintlayout_lookingfor_homesearchparent);
        setActionsOnView();
    }

    private void setActionsOnView() {
        lookingForProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentVerifyPhoneNumber fragment = new FragmentVerifyPhoneNumber();
                fragmentTransaction.add(R.id.container_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public void onBackClicked(View view) {
        onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String permission = permissions[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setProgressBarAndGetLocation();
            }
            boolean showRationale = shouldShowRequestPermissionRationale(permission);
            if (!showRationale && permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
//                    Save user has checked "never ask again" in Shared prefs
                SpacingApp.putValue(SpacingApp.DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN, true);
            } else {
                SpacingApp.putValue(SpacingApp.DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN, false);
            }
        }
    }

    @Override
    public void onLocationUpdated(Object loc) {
        Log.e("Location ", "" + loc);


        Location location = (Location) loc;
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            address = addresses.get(0).getAddressLine(0).split(",")[0];
            city = addresses.get(0).getLocality();
            loadingCityProgressBar.dismiss();
            getCityCallback.onUpdated(city);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getcity() {
        if (city != null)
            return city;
        else {
            waitingMessage = "Please wait Trying to get your city..";
            return null;
        }
    }

    public String getLocality() {
        if (address != null)
            return address;
        else {
            waitingMessage = "Please wait Trying to get your locality..";
            return null;
        }
    }


    public void checkPermissionAndGetLocation(GotUpdateLocationInterface gotUpdateLocationInterface) {
        getCityCallback = gotUpdateLocationInterface;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            AskForPermissions.getPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    new AskForPermissions.AskForGooglePermissionCallback() {
                        @Override
                        public void openAskForPermissionDialog(String[] perms) {
                            ActivityCompat.requestPermissions(MainActivity.this, perms, SpacingApp.ASK_LOCATION_PERMISSION_REQUEST_CODE);
                        }
                    }, null, new AskForPermissions.PermissionDeniedWithNeverAskAgain() {
                        @Override
                        public void openGoToSettingPageAlert() {

                        }
                    });
        } else {
            setProgressBarAndGetLocation();

        }
    }

    private void setProgressBarAndGetLocation() {
        loadingCityProgressBar = new ProgressDialog(this);
        loadingCityProgressBar.setCancelable(true);
        loadingCityProgressBar.setMessage(waitingMessage);
        loadingCityProgressBar.show();
        LocationManager.getInstance().setLocationManagerInterface(this);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLocality(String locality) {
        this.address = locality;
    }

    public void setEndBudget(String startBudget) {
        this.startBudget = startBudget;
    }

    public void setStartBudget(String endBudget) {
        this.endBudget = endBudget;
    }

    public String getStartBudget() {
        return startBudget;
    }

    public String getEndBudget() {
        return endBudget;
    }


    public interface GotUpdateLocationInterface {
        void onUpdated(String city);
    }


}