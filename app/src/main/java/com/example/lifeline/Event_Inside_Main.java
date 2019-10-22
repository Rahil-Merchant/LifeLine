package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class Event_Inside_Main extends AppCompatActivity implements OnMapReadyCallback {
    private EventAdapter adapter;
    TextView Title;
    TextView description;
    String Title_pass;
    int position_pass;
    String image_pass;
    String Description_pass;
    String Phone_pass;
    String Address_pass;
    String Latitude_pass;
    String Longitude_pass;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mMapView;
    Double lat;
    Double lon;
    Button checkbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__inside__main);
        checkbutton=findViewById(R.id.check_Button);
        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Event_Inside_Main.this,CheckDonor.class));
            }
        });
        TextView Title=findViewById(R.id.text);
        TextView description = findViewById(R.id.text_view_description);
        TextView address=findViewById(R.id.text_view_Address);
        ImageView Image=findViewById(R.id.image_view_banner);
        TextView Phone=findViewById(R.id.text_view_phone_number);
        Intent intent =getIntent();
        Title_pass = intent.getStringExtra(HospitalEventMain.Extra_Title);
        position_pass = intent.getIntExtra(HospitalEventMain.Extra_pos,0);
        image_pass=intent.getStringExtra(HospitalEventMain.Extra_image);
        Description_pass=intent.getStringExtra(HospitalEventMain.Extra_description);
        Phone_pass=intent.getStringExtra(HospitalEventMain.Extra_Phone);
        Address_pass=intent.getStringExtra(HospitalEventMain.Extra_Address);
        Latitude_pass=intent.getStringExtra(HospitalEventMain.Extra_Latitude);
        Longitude_pass=intent.getStringExtra(HospitalEventMain.Extra_Longitude);
        Title.setText(Title_pass);
        Glide.with(this)
                .load(image_pass)
                .into(Image);
        description.setText(Description_pass);
        Phone.setText(Phone_pass);
        address.setText(Address_pass);
        lat=Double.parseDouble(Latitude_pass);
        lon=Double.parseDouble(Longitude_pass);

        //position1.setText(""+position_pass)

        /*if(savedInstanceState!=null){
            mapViewBundle=savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView=(MapView)findViewById(R.id.google_map);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
        */

        initGoogleMap(savedInstanceState);

    }
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
        /*if(checkMapServices()){
            if(mLocationPermissionGranted){

            }else {
                getLocationPermission();
            }
        }*/
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
        //map.addMarker(new MarkerOptions().position().title("Marker"));
        //map.addMarker(new MarkerOptions().position())
        map.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(Title_pass));
        map.setMyLocationEnabled(true);
        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(lat, lon));
        map.moveCamera(point);
        map.animateCamera(point);


        //Projection projection = map.getProjection();
        //LatLng markerLocation = marker.getPosition();
    }


    /*@Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
        Note note = documentSnapshot.toObject(Note.class);
        note.getImage();
        note.getDescription();
        Title.setText(note.getTitle());
        //Toast.makeText(Event_Inside_Main.this,+position,Toast.LENGTH_SHORT).show();
    }*/



}
