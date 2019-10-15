package com.example.lifeline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import id.zelory.compressor.Compressor;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewEventActivity extends AppCompatActivity implements OnMapReadyCallback {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private ImageView BannerImage;
    private EditText editTextPhone;
    private AutoCompleteTextView editTextLocation;
    String addresslat;
    String address;
    String addresslong;
    String uniqueId;
    //private DatePicker datePickerDate;
    //private DatePicker datePickerMonth;
    //private DatePicker datePickerYear;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mMapView;
    private Uri imageUri;
    //String download_uri;
    Uri download_uri;
    private Bitmap compressor;
    private StorageReference storageReference;
    //private FirebaseRe useReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Bitmap compressed;
    private String downloadURL;
    private StorageReference mStorageRefrence;
    private StorageReference mStorageReference1;
    private int edate,emonth,eyear;
    //private String edate;
    DatePicker datePicker;
    private ProgressDialog progressDialog;
    Button locationButton;
    //private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    //private static final String COARSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted=false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST=9001;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS=9002;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=9003;
    private ImageView marker;
    private TextView CurrentLocation;
    private Button SaveLocation;
    Marker currentMarker;
    Marker destinationMarker;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS=new LatLngBounds(
            new LatLng(-40,-168),new LatLng(71,136));
    PlacesClient placesClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        storageReference= FirebaseStorage.getInstance().getReference();
        mStorageReference1=FirebaseStorage.getInstance().getReference();
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        BannerImage = findViewById(R.id.banner_image);
        editTextPhone = findViewById(R.id.text_view_number);
        editTextLocation=findViewById(R.id.location);
        locationButton=findViewById(R.id.Location_Button);

        // Initialize Places.
        //Places.initialize(getApplicationContext(),"AIzaSyCMzOesb-YcWdbToqNhSln6G49Pfo7EGHA");

        // Create a new Places client instance.
        //PlacesClient placesClient = Places.createClient(this);

        String apikey="AIzaSyCMzOesb-YcWdbToqNhSln6G49Pfo7EGHA";
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(),apikey);
        }

        placesClient=Places.createClient(this);
        final AutocompleteSupportFragment autocompleteSupportFragment=(AutocompleteSupportFragment)getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final LatLng latLng=place.getLatLng();
                Toast.makeText(NewEventActivity.this,"Latitude:"+latLng.latitude+" Longitude:"+latLng.longitude,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

        //datePickerDate = findViewById(R.id.date_picker_date);
        datePicker = (DatePicker) findViewById(R.id.Event_Date);
        getLocationPermission();
        //marker=findViewById(R.id.Marker);
        //CurrentLocation=findViewById(R.id.Location);
        //String loc = getStringAddress(19.0667044,72.836966);
        //Toast.makeText(NewNoteActivity.this,loc,Toast.LENGTH_LONG).show();


       /* BannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        if(ContextCompat.checkSelfPermission(NewNoteActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED ) {
                    Toast.makeText(NewNoteActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();

                }
            }
        });*/

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = editTextLocation.getText().toString();
                GeoLocation geoLocation=new GeoLocation();
                geoLocation.getAddress(address,getApplicationContext(),new GeoHandler());


            }
        });



        BannerImage.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                   if (ContextCompat.checkSelfPermission(NewEventActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                                                       Toast.makeText(NewEventActivity.this, "Permission Confirmed", Toast.LENGTH_LONG).show();
                                                       ActivityCompat.requestPermissions(NewEventActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                                                   } else {

                                                       choseImage();

                                                   }

                                               } else {

                                                   choseImage();

                                               }

                                           }



                                       }

        );
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        initGoogleMap(savedInstanceState);

      /*  mGoogleApiClient=new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();*/

        //mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this,mGoogleApiClient,LAT_LNG_BOUNDS,null);
    }

    /*private void setupAutoCompleteFragment() {
        PlaceAutocomplete autocompleteFragment = (PlaceAutocomplete)
                .findViewById(R.id.location);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

            }

            @Override
            public void onError(Status status) {
                Log.e("Error", status.getStatusMessage());
            }
        });
    }*/




    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.google_map);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }

    /*public boolean isServicesOk(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(NewNoteActivity.this);
        if(available== ConnectionResult.SUCCESS){
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(NewNoteActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this,"You Cant make Map Request",Toast.LENGTH_SHORT).show();
        }

        return false;
    }


    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted=true;
            }else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void initMap()
    {
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(NewNoteActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted=false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0){
                    for (int i=0;i<grantResults.length;i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted=false;
                            return;
                        }
                    }
                    mLocationPermissionGranted=true;
                    initMap();

                }
            }
        }
    }*/

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //getChatrooms();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        //Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(NewEventActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            //Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            //Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(NewEventActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

   /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                    //getChatrooms();
                }
                else{
                    getLocationPermission();
                }
            }
        }

    }*/

    /*protected void onResume()
    {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){

            }else {
                getLocationPermission();
            }
        }
    }*/
    private void choseImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(100000, 75000)
                .start(NewEventActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                BannerImage.setImageURI(imageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_event_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_event:
                saveEvent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void saveEvent(){
        final String title = editTextTitle.getText().toString();
        final String descrption = editTextDescription.getText().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        final String edate = String.valueOf(day + "/" + month + "/" + year);
        final String phone=editTextPhone.getText().toString();
        final String addressfinal=editTextLocation.getText().toString();

        //int day = datePickerDate.getDayOfMonth();
        //int month = datePickerDate.getMonth()+1;
        //int year = datePickerDate.getYear();

        if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(descrption)&&imageUri!=null&&!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(addressfinal)){
            File newFile = new File(imageUri.getPath());
            try {
                compressed = new Compressor(NewEventActivity.this)
                        .setMaxHeight(125)
                        .setMaxWidth(125)
                        .setQuality(50)
                        .compressToBitmap(newFile);
            } catch (IOException e) {


                e.printStackTrace();


            }


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


            compressed.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            //final StorageReference ref = storageRef.child("images/mountains.jpg");


            byte[] thumbData = byteArrayOutputStream.toByteArray();

            UploadTask image_path = storageReference.child("Event_Image").child(title).putBytes(thumbData);
            //final StorageReference storageReference2 = mStorageReference1.child("Event_Image_1").child(title);
            //UploadTask image_path = storageReference.child("Event_Image").child(new Random().toString()).putFile();


            //final StorageReference mstorageRefrence = storageReference.child("Event_Image").child(new Random().toString()).putBytes(thumbData).;

            /*storageReference.child("Event").child(title).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        storeEvent(task,editTextTitle,editTextDescription);
                        Toast.makeText(NewNoteActivity.this,"Event Added",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        String error =task.getException().getMessage();
                        Toast.makeText(NewNoteActivity.this,"Error in Uploading image : " +error,Toast.LENGTH_SHORT ).show();
                    }

                }
            });*/

            image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {


                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){
                       /*CollectionReference notebookRef = FirebaseFirestore.getInstance()
                                .collection("Events");
                        //notebookRef.add(new Note(Title,Descrption,Image));
                        Toast.makeText(NewNoteActivity.this,"Event Added",Toast.LENGTH_SHORT).show();
                        finish();*/
                        //storeEvent(task,editTextTitle,editTextDescription);
                        Toast.makeText(NewEventActivity.this,"Event Added",Toast.LENGTH_SHORT).show();
                        finish();
                        final Uri download_uri;
                        final String title = editTextTitle.getText().toString();
                        final String descrption = editTextDescription.getText().toString();
                        final String addressfinal=editTextLocation.getText().toString();
                        if(task!=null){
                            //download_uri=task.getResult().getDownloadUrl();
                            mStorageReference1.child("Event_Image").child(title).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Map<String,String> eventData = new HashMap<>();
                                    eventData.put("Title",title);
                                    eventData.put("Description",descrption);
                                    eventData.put("Date",edate);
                                    eventData.put("Image",task.getResult().toString());
                                    eventData.put("Phone",phone);
                                    eventData.put("Address",addressfinal);
                                    eventData.put("Latitude",addresslat);
                                    eventData.put("Longitude",addresslong);

                                    firebaseFirestore.collection("Events").add(eventData);

                                }
                            });
                        }else {
                            download_uri=imageUri;
                        }

                        /*Map<String,String> eventData = new HashMap<>();
                        eventData.put("Title",title);
                        eventData.put("Description",descrption);
                        eventData.put("Image",download_uri.toString());

                        firebaseFirestore.collection("Events").add(eventData);*/

                    }else {
                        String error =task.getException().getMessage();
                        Toast.makeText(NewEventActivity.this,"Error in Uploading image : " +error,Toast.LENGTH_SHORT ).show();
                    }




                }

            });

        }




        if(title.trim().isEmpty() || descrption.trim().isEmpty() || phone.trim().isEmpty()||addressfinal.trim().isEmpty()){
            Toast.makeText(this,"Please insert Title,Description,Phone Number,Image and Address",Toast.LENGTH_SHORT).show();
            return;
        }

       /* CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("Events");
        //notebookRef.add(new Note(title,descrption));
        Toast.makeText(this,"Event Added",Toast.LENGTH_SHORT).show();
        finish();*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){

            }else {
                getLocationPermission();
            }
        }
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //map.addMarker(new MarkerOptions().position(new LatLng(addr)).title("Marker"));
        //map.addMarker(new MarkerOptions().position())
        map.setMyLocationEnabled(true);

        //Projection projection = map.getProjection();
        //LatLng markerLocation = marker.getPosition();
    }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    public String getStringAddress(Double lat,Double lng){
        String address="";
        String city="";
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses=geocoder.getFromLocation(lat,lng,1);
            address=addresses.get(0).getAddressLine(0);
            city=addresses.get(0).getLocality();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return address + " "+city;
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 1:
                    Bundle bundlelat = msg.getData();
                    Bundle bundlelong = msg.getData();
                    Bundle bundle = msg.getData();
                    //addresslong=bundlelat.getString("addresslong");
                    //addresslat=bundlelong.getString("addresslat");
                    address = bundle.getString("address");
                    addresslat = address.substring(0, 9);
                    addresslong = address.substring(10, 19);
                    break;
                default:
                    address = null;
                    addresslat = null;
                    addresslong = null;
                    Toast.makeText(NewEventActivity.this, "Enter Valid Location", Toast.LENGTH_LONG).show();


            }

            //Toast.makeText(NewNoteActivity.this,address,Toast.LENGTH_LONG).show();
            Toast.makeText(NewEventActivity.this,"Latitude: "+addresslat+ " Longitude:" +addresslong,Toast.LENGTH_LONG).show();
        }
    }

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

    }*/

    /*private void storeEvent(Task task, EditText editTextTitle, EditText editTextDescription) {
        //UploadTask.TaskSnapshot download_uri;
        Uri download_uri;
        final String title = editTextTitle.getText().toString();
        final String descrption = editTextDescription.getText().toString();
        if(task!=null){
            download_uri=task.getResult().getClass();
            //download_uri=task.getResult().getMetadata().getReference().;
            //downloadURL = download_uri.toString();
            //download_uri=task.getReference().getDownloadUrl();
        }else {
            //download_uri=imageUri;
            Toast.makeText(this,"Please insert Title and Description",Toast.LENGTH_SHORT).show();
        }

        Map<String,String> eventData = new HashMap<>();
        eventData.put("Title",title);
        eventData.put("Description",descrption);
        eventData.put("Image",download_uri.toString());
        //eventData.put("Image",downloadURL);

        firebaseFirestore.collection("Events").add(eventData);
    }*/

   /* Map<String,String> eventData = new HashMap<>();
    eventData.put("Title",title);
    eventData.put("Description",descrption);
    eventData.put("Name",);*/

   /* private void storeEvent() {
        uniqueId = UUID.randomUUID().toString();
        ur_firebase_reference = storageReference.child("user_photos/" + uniqueId);
        Uri file = Uri.fromFile(new File(mphotofile.getAbsolutePath()));
        UploadTask uploadTask = ur_firebase_reference.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ur_firebase_reference.getDownloadUrl();
            }
        }).
        //UploadTask.TaskSnapshot download_uri;

        final String title = editTextTitle.getText().toString();
        final String descrption = editTextDescription.getText().toString();
        if(task!=null){
            //download_uri=task.getResult().getUploadSessionUri();
            download_uri=task.getResult().getMetadata().getReference().;
            //downloadURL = download_uri.toString();
            //download_uri=task.getReference().getDownloadUrl();
        }else {
            //download_uri=imageUri;
            Toast.makeText(this,"Please insert Title and Description",Toast.LENGTH_SHORT).show();
        }

        Map<String,String> eventData = new HashMap<>();
        eventData.put("Title",title);
        eventData.put("Description",descrption);
        eventData.put("Image",download_uri);
        //eventData.put("Image",downloadURL);

        firebaseFirestore.collectio("Events").add(eventData);
    }*/
}
