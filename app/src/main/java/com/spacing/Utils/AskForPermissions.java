package com.spacing.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.spacing.SpacingApp;


public class AskForPermissions {
    private static boolean requiresPermissionCheckForLocation = false;
    private static boolean requiresPermissionCheckForStorage = false;

    public interface AskForGooglePermissionCallback {
        void openAskForPermissionDialog(String[] perms);
    }

    public interface IfPermissionHasBeenGivenManually {
        void closeGotoSettingsDialog();
    }

    public interface PermissionDeniedWithNeverAskAgain {
        void openGoToSettingPageAlert();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void getPermissions(Activity mActivity, String[] perms,
                                      AskForGooglePermissionCallback callback,
                                      IfPermissionHasBeenGivenManually callBackToCloseDialog
            , PermissionDeniedWithNeverAskAgain permissionDeniedWithNeverAskAgain) {
        //  Check if user has given the permissions from settings page manually,no need to go any further
        if (mActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && mActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            SpacingApp.putValue(SpacingApp.DONT_ASK_FOR_STORAGE_PERMISSION_AGAIN, false);
            SpacingApp.putValue(SpacingApp.DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN, false);
            callBackToCloseDialog.closeGotoSettingsDialog();
            return;
        }

        for (String perm : perms) {
            if (perm.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                //  This case will occur if one storage permission is given and denied with never ask again for location
                if (mActivity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED
//                        && SpacingApp.getBoolean(SpacingApp.DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN)
                        && mActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    permissionDeniedWithNeverAskAgain.openGoToSettingPageAlert();
                    break;
                }

                if (mActivity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                    requiresPermissionCheckForLocation = true;
                } else {
                    requiresPermissionCheckForLocation = false;
                    perms[0] = "";
                    SpacingApp.putValue(SpacingApp.DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN, false);
                }


            }
            if (perm.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // This case will occur if one Location permission is given and denied with never ask again for Storage
                //  In both case we do not need to do anything else other then showing goto settings dialog
                if (mActivity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED
                        && SpacingApp.getBoolean(SpacingApp.DONT_ASK_FOR_STORAGE_PERMISSION_AGAIN)
                        && mActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    permissionDeniedWithNeverAskAgain.openGoToSettingPageAlert();
                    break;
                }
                if (mActivity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                    requiresPermissionCheckForStorage = true;
                } else {
                    requiresPermissionCheckForStorage = false;
                    if (perms.length > 1)
                        perms[1] = "";
                    else perms[0] = "";
                    SpacingApp.putValue(SpacingApp.DONT_ASK_FOR_STORAGE_PERMISSION_AGAIN, false);
                }
            }
        }
//
        if (requiresPermissionCheckForStorage && !SpacingApp.getBoolean(SpacingApp.DONT_ASK_FOR_STORAGE_PERMISSION_AGAIN)) {
            if (!requiresPermissionCheckForLocation || SpacingApp.getBoolean(SpacingApp.DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN))
                callback.openAskForPermissionDialog(perms);
//            CustomAlertDialog alertDialogStorageInfo = new CustomAlertDialog(mActivity, () -> {
////                    If user has denied with never ask again for location and only denied for storage,so ask for storage only again
//                if (!requiresPermissionCheckForLocation || SpacingApp.getBoolean(SpacingApp.DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN))
//                    callback.openAskForPermissionDialog(perms);
//                else {
//                    CustomAlertDialog alertDialogLocationInfo = new CustomAlertDialog(mActivity, () -> callback.openAskForPermissionDialog(perms));
//                    alertDialogLocationInfo.displaySingleAlert(mActivity.getString(R.string.alert_location_title),
//                            mActivity.getString(R.string.alert_location_permission_description), mActivity.getString(R.string.button_ok));
//                }
//
//            });
//            alertDialogStorageInfo.displaySingleAlert(mActivity.getString(R.string.alert_photos_media_title),
//                    mActivity.getString(R.string.alert_storage_permission_description), mActivity.getString(R.string.button_ok));

        }
        if (requiresPermissionCheckForLocation
                && !SpacingApp.getBoolean(SpacingApp.DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN)) {
            //    If user has denied with never ask again for storage and only denied for location,so ask for location only again
            if (!requiresPermissionCheckForStorage || SpacingApp.getBoolean(SpacingApp.DONT_ASK_FOR_STORAGE_PERMISSION_AGAIN)) {
                callback.openAskForPermissionDialog(perms);
            }
//                CustomAlertDialog alertDialogLocationInfo = new CustomAlertDialog(mActivity, () -> ;
//                alertDialogLocationInfo.displaySingleAlert(mActivity.getString(R.string.alert_location_title),
//                        mActivity.getString(R.string.alert_location_permission_description), mActivity.getString(R.string.button_ok));
//            }
        }

        //  If user has denied with never ask again for storage and denied with never ask again for location as well
        if (SpacingApp.getBoolean(SpacingApp.DONT_ASK_FOR_STORAGE_PERMISSION_AGAIN)
                && SpacingApp.getBoolean(SpacingApp.DONT_ASK_FOR_LOOCATION_PERMISSION_AGAIN))
            permissionDeniedWithNeverAskAgain.openGoToSettingPageAlert();

    }
}

