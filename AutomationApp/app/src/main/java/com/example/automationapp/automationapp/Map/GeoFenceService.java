//package com.example.automationapp.automationapp;
//
///**
// * Created by laiwf on 07/03/2017.
// */
//
//import android.app.Dialog;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Binder;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.widget.Toast;
//
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.Geofence;
//import com.google.android.gms.location.GeofencingRequest;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.places.Places;;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.Marker;
//
//import java.util.HashMap;
//
//public class GeoFenceService implements GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        ResultCallback<Status> {
//
//
//    protected static final String TAG = "GeoFenceService";
//
//    /**
//     * Provides the entry point to Google Play services.
//     */
//    protected GoogleApiClient mGoogleApiClient;
//
//    /**
//     * The list of geofences used in this sample.
//     */
//    protected Geofence mGeofence;
//
//    /**
//     * Used to keep track of whether geofences were added.
//     */
//    private boolean mGeofencesAdded;
//
//    /**
//     * Used when requesting to add or remove geofences.
//     */
//    private PendingIntent mGeofencePendingIntent;
//
//    /**
//     * Used to persist application state about whether geofences were added.
//     */
//    private SharedPreferences mSharedPreferences;
//
//
//
//    public GeoFenceService() {
//
//        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
//        mGeofencePendingIntent = null;
//
//        // Retrieve an instance of the SharedPreferences object.
//        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,
//                MODE_PRIVATE);
//
//        // Get the value of mGeofencesAdded from SharedPreferences. Set to false as a default.
//        mGeofencesAdded = mSharedPreferences.getBoolean(Constants.GEOFENCES_ADDED_KEY, false);
//
//        // Kick off the request to build GoogleApiClient.
//        buildGoogleApiClient();
//    }
//
//    /**
//     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the LocationServices API.
//     */
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .build();
//        mGoogleApiClient.connect();
//    }
//
//    /**
//     * Runs when a GoogleApiClient object successfully connects.
//     */
//    @Override
//    public void onConnected(Bundle connectionHint) {
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(5000); //5 seconds
//        mLocationRequest.setFastestInterval(3000); //3 seconds
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter
//
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//
//            }
//        });
//
//        Log.i(TAG, "Connected to GoogleApiClient");
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
//        // onConnectionFailed.
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
//    }
//
//    @Override
//    public void onConnectionSuspended(int cause) {
//        // The connection to Google Play services was lost for some reason.
//        Log.i(TAG, "Connection suspended");
//
//        // onConnected() will be called again automatically when the service reconnects
//    }
//
//    /**
//     * Builds and returns a GeofencingRequest. Specifies the list of geofences to be monitored.
//     * Also specifies how the geofence notifications are initially triggered.
//     */
//    private GeofencingRequest getGeofencingRequest() {
//        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
//
//        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
//        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
//        // is already inside that geofence.
//        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
//
//        // Add the geofences to be monitored by geofencing service.
//        builder.addGeofence(mGeofence);
//
//
//        // Return a GeofencingRequest.
//        return builder.build();
//    }
//
//    /**
//     * Adds geofences, which sets alerts to be notified when the device enters or exits one of the
//     * specified geofences. Handles the success or failure results returned by addGeofences().
//     */
//    public void addGeofencesButtonHandler() {
//        if (!mGoogleApiClient.isConnected()) {
//            Toast.makeText(getApplicationContext(), getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        try {
//            LocationServices.GeofencingApi.addGeofences(
//                    mGoogleApiClient,
//                    // The GeofenceRequest object.
//                    getGeofencingRequest(),
//                    // A pending intent that that is reused when calling removeGeofences(). This
//                    // pending intent is used to generate an intent when a matched geofence
//                    // transition is observed.
//                    getGeofencePendingIntent()
//            ).setResultCallback(this); // Result processed in onResult().
//        } catch (SecurityException securityException) {
//            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
//            logSecurityException(securityException);
//        }
//    }
//
//    /**
//     * Removes geofences, which stops further notifications when the device enters or exits
//     * previously registered geofences.
//     */
//    public void removeGeofencesButtonHandler() {
//        if (!mGoogleApiClient.isConnected()) {
//            Toast.makeText(getApplicationContext(), getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
//            return;
//        }
//        try {
//            // Remove geofences.
//            LocationServices.GeofencingApi.removeGeofences(
//                    mGoogleApiClient,
//                    // This is the same pending intent that was used in addGeofences().
//                    getGeofencePendingIntent()
//            ).setResultCallback(this); // Result processed in onResult().
//        } catch (SecurityException securityException) {
//            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
//            logSecurityException(securityException);
//        }
//    }
//
//    private void logSecurityException(SecurityException securityException) {
//        Log.e(TAG, "Invalid location permission. " +
//                "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
//    }
//
//    /**
//     * Runs when the result of calling addGeofences() and removeGeofences() becomes available.
//     * Either method can complete successfully or with an error.
//     *
//     * Since this activity implements the {@link com.google.android.gms.common.api.ResultCallback} interface, we are required to
//     * define this method.
//     *
//     * @param status The Status returned through a PendingIntent when addGeofences() or
//     *               removeGeofences() get called.
//     */
//    public void onResult(Status status) {
//        if (status.isSuccess()) {
//            // Update state and save in shared preferences.
//            mGeofencesAdded = !mGeofencesAdded;
//            SharedPreferences.Editor editor = mSharedPreferences.edit();
//            editor.putBoolean(Constants.GEOFENCES_ADDED_KEY, mGeofencesAdded);
//            editor.apply();
//            // Update the UI. Adding geofences enables the Remove Geofences button, and removing
//            // geofences enables the Add Geofences button.
//            Toast.makeText(getApplicationContext(), getString(mGeofencesAdded ? R.string.geofences_added : R.string.geofences_removed), Toast.LENGTH_SHORT).show();
//        } else {
//            // Get the status code for the error and log it using a user-friendly message.
//            String errorMessage = GeofenceErrorMessages.getErrorString(getApplicationContext(),
//                    status.getStatusCode());
//            Log.e(TAG, errorMessage);
//        }
//    }
//
//
//
//    /**
//     * Gets a PendingIntent to send with the request to add or remove Geofences. Location Services
//     * issues the Intent inside this PendingIntent whenever a geofence transition occurs for the
//     * current list of geofences.
//     *
//     * @return A PendingIntent for the IntentService that handles geofence transitions.
//     */
//    private PendingIntent getGeofencePendingIntent() {
//        // Reuse the PendingIntent if we already have it.
//        if (mGeofencePendingIntent != null) {
//            return mGeofencePendingIntent;
//        }
//        Intent intent = new Intent(getApplicationContext(), GeofenceTransitionsIntentService.class);
//        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
//        // addGeofences() and removeGeofences().
//        return PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//
//    /**
//     * This sample hard codes geofence data. A real app might dynamically create geofences based on
//     * the user's location.
//     */
//    public void populateGeofenceList(float lat,float lng) {
//
//        mGeofence=new Geofence.Builder()
//                // Set the request ID of the geofence. This is a string to identify this
//                // geofence.
//                .setRequestId("Current Location")
//
//                // Set the circular region of this geofence.
//                .setCircularRegion(
//                        lat,
//                        lng,
//                        Constants.GEOFENCE_RADIUS_IN_METERS
//                )
//
//                // Set the expiration duration of the geofence. This geofence gets automatically
//                // removed after this period of time.
//                .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
//
//                // Set the transition types of interest. Alerts are only generated for these
//                // transition. We track entry and exit transitions in this sample.
//                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
//                        Geofence.GEOFENCE_TRANSITION_EXIT)
//
//                // Create the geofence.
//                .build();
//        removeGeofencesButtonHandler();
//        addGeofencesButtonHandler();
//
//    }
//
//    /**
//     * Ensures that only one button is enabled at any time. The Add Geofences button is enabled
//     * if the user hasn't yet added geofences. The Remove Geofences button is enabled if the
//     * user has added geofences.
//     */
//
//
//    private boolean canAccessLocation() {
//        return (hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION));
//    }
//
//
//    private boolean hasPermission(String perm) {
//        return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
//    }
//
//}
//
//
//
