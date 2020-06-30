package org.techtown.setgooglemaps;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;


public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    LatLng latlng1;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private GoogleMap mMap;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1001;

    public String myUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        permissionCheck();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left2, R.anim.slide_left);

                finish();
            }
        });


    }
    public void permissionCheck() {
        int permssionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permssionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 승인이 필요합니다", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "위치 정보 사용을 위해 ACCESS_FINE_LOCATION 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                Toast.makeText(this, "위치 정보 사용을 위해 ACCESS_FINE_LOCATION 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onMapSearch(View view) {
        String addressString=null;
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;


        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this,Locale.KOREAN);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
                if(addressList.size()>0){
                    addressString=addressList.get(0).toString();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(addressString);
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            latlng1=latLng;
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Seoul and move the camera
        LatLng seoul = new LatLng(37.494870,126.960763);
        mMap.addMarker(new MarkerOptions().position(seoul).title("Korea, Seoul"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setOnMarkerClickListener(this);
        mMap.setMyLocationEnabled(true);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    { switch (requestCode) {
        case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "승인이 허가되어 있습니다.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "승인을 받아주세요.", Toast.LENGTH_LONG).show();
            }
            return;
        }
    }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        int idx=0;
        idx++;

        EditText locationSearch = (EditText) findViewById(R.id.editText);
        final String location = locationSearch.getText().toString();

//       //databaseReference.child(myUid).child("장소").child(String.valueOf(idx)).setValue(latlng1);
//        //databaseReference.child(myUid).child("장소").child(String.valueOf(idx)).setValue(location);
//        databaseReference.child(myUid).child("장소").child(location).setValue(latlng1);
//        Toast.makeText(this,marker.getTitle()+"\n"+marker.getPosition(),Toast.LENGTH_SHORT).show();
//        return true;

        final Context context = this;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("마커 삭제/데이터 등록");

        alertDialogBuilder
                .setMessage("마커를 삭제하시겠습니까? \n데이터 등록을하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("삭제",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                marker.remove();
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

        final int finalIdx = idx;
        alertDialogBuilder.setNeutralButton("등록", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                databaseReference.child(myUid).child("장소").child(location).setValue(latlng1);

            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();

        return true;
    }
}