package com.example.testbed;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static java.lang.Double.parseDouble;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button;
    private Button add;
    private Button back;
    private EditText editText;
    private Marker marker;
    private String address;
    private String feature;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        add = findViewById(R.id.add_button);
        back = findViewById(R.id.b_button);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        marker = null;
        geocoder = new Geocoder(this);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();

                mOptions.title("좌표");
                Double latitude = point.latitude;
                Double longitude = point.longitude;

                if(marker != null){
                    marker.remove();
                    marker = null;
                }
                if(marker == null) {
                    mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                    mOptions.position(new LatLng(latitude, longitude));
                    marker = googleMap.addMarker(mOptions);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String In_address = editText.getText().toString();
                List<Address> addressList = null;

                try{
                    addressList = geocoder.getFromLocationName(In_address, 10);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                feature = In_address;
                //,를 기준으로 split.
                String []splitstr = addressList.get(0).toString().split(",");
                //split한 스트링에서 다시 주소만 분리.
                address = splitstr[0].substring(splitstr[0].indexOf("\"")+1, splitstr[0].length()-2);
                //split한 스트링에서 다시 위도만 분리.
                latitude = splitstr[10].substring(splitstr[10].indexOf("=")+1);
                //split한 스트링에서 다시 경도만 분리.
                longitude = splitstr[12].substring(splitstr[12].indexOf("=")+1);
                //분리한 각각의 스트링에서 좌표값으로 재처리.
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                //마커 생성
                MarkerOptions mOptions = new MarkerOptions();
                mOptions.title("검색 결과");
                mOptions.snippet(address);
                mOptions.position(point);

                if(marker != null){
                    marker.remove();
                    marker = null;
                }
                if(marker == null) {
                    //지도상에 마커 추가
                    marker = mMap.addMarker(mOptions);
                    //좌표로 카메라 이동
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16));
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,List_storage.class);
                Bundle bundle = new Bundle();
                bundle.putString("address",address);
                bundle.putString("latitude",latitude);
                bundle.putString("longitude",longitude);
                bundle.putString("feature",feature);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Add a marker in Seoul and move the camera
        LatLng Seoul = new LatLng(37.55, 126.99);
        if(marker != null){
            marker.remove();
            marker = null;
        }
        if(marker == null) {
            marker = mMap.addMarker(new MarkerOptions().position(Seoul).title("Marker in Seoul"));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Seoul,13));
    }
}
