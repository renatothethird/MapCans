package com.example.masterpeps.mapcans2.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.masterpeps.mapcans2.HomeActivity;
import com.example.masterpeps.mapcans2.Model.Mapbase;
import com.example.masterpeps.mapcans2.Model.User;
import com.example.masterpeps.mapcans2.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapFragment2 extends Fragment implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    ImageView addCans, deleteCans;
    private Firebase mRef;
    private onGoToAddCans2 _toGotoAddCans;
    private double longitude, latitude;
    private boolean isAddMap = false, isDeleteTrash;
    String sLatitude, sLongitude;
    Marker selectedMarker;
    private ArrayList<Marker> mMarkers = new ArrayList<>();
    public MapFragment2(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Firebase.setAndroidContext(getActivity());
        mView = inflater.inflate(R.layout.fragment_map_fragment2, container, false);
        init();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView !=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }
    @Override
    public void onMapReady(final GoogleMap mMap){
        mRef = new Firebase("https://mapcans-825b5.firebaseio.com/Maps");
        MapsInitializer.initialize(getContext());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }

        if (mMap != null) {


            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                @Override
                public void onMyLocationChange(Location arg0) {
                    // TODO Auto-generated method stub

                    CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(arg0.getLatitude(), arg0.getLongitude()));
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(17.5f);

                    mMap.moveCamera(center);
                    mMap.animateCamera(zoom);
                }
            });

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if(isAddMap) {
                        longitude = latLng.longitude;
                        latitude = latLng.latitude;
                        isAddMap = false;
                        ((HomeActivity)getActivity()).setLatitudeLongitude(latitude, longitude);
                        _toGotoAddCans.toGotoAddCans2();
                    }else{  //do nothing
                    }
                }
            });



           mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    isDeleteTrash = true;
                    //if(isDeleteTrash){
                      //  Toast.makeText(getContext(),"welcome home",Toast.LENGTH_SHORT);
                        /*isDeleteTrash = false;*/
                    sLatitude  =  String.valueOf(marker.getPosition().latitude).replace(".", "");
                    sLongitude = String.valueOf(marker.getPosition().longitude).replace(".","");
                    selectedMarker = marker;
                    Toast.makeText(getActivity(), "Marker selected.", Toast.LENGTH_SHORT).show();
                        /*String latitude =  String.valueOf(marker.getPosition().latitude).replace(".", "");
                        String longitude = String.valueOf(marker.getPosition().longitude).replace(".","");
                        Firebase mRefChild = mRef.child("latLng" + latitude + "" +longitude);
                        System.out.println("latLng" + latitude + "" +longitude);
                        mRefChild.removeValue();
                        marker.remove();*/
                   // }

                }
            });

        }


        mRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
            ArrayList<Mapbase> mapbases = new ArrayList<>();
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                mapbases.add(new Mapbase((String) dataSnapshot.child("displayName").getValue(),(String) dataSnapshot.child("latitude").getValue(),
                        (String) dataSnapshot.child("longitude").getValue(), (String) dataSnapshot.child("placedBy").getValue()
                        ,(String) dataSnapshot.child("uId").getValue()));
                LatLng latLng = new LatLng(Double.parseDouble((String) dataSnapshot.child("latitude").getValue()), Double.parseDouble((String) dataSnapshot.child("longitude").getValue()));
                int height = 100;
                int width = 100;
                /*BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.map_pointer_small);
                Bitmap b=bitmapdraw.getBitmap();*/
                //Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title((String) dataSnapshot.child("displayName").getValue()).snippet("Placed by: " + dataSnapshot.child("placedBy").getValue()));
                mMarkers.add(marker);
                //getAllUsers((Map<String,Object>) dataSnapshot.getValue(), mMap);
                /*for (com.firebase.client.DataSnapshot snapshot: dataSnapshot) {

                    //System.out.println((String) snapshot.getValue());
                }*/
               /* for(Mapbase mapbase: mapbases){
                   // LatLng latLng = new LatLng(Double.parseDouble(mapbase.getLatitude()), Double.parseDouble(mapbase.getLongitude()));
                    //Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(mapbase.displayName).snippet("Placed by: " + mapbase.getDisplayName()));
                }*/
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void init()
    {
        deleteCans = (ImageView)mView.findViewById(R.id.btn_goToDeleteCans);
        deleteCans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //enable delete trash
                if(isDeleteTrash){
                    isDeleteTrash = false;
                    Firebase mRefChild = mRef.child("latLng" + sLatitude + "" + sLongitude);
                    mMarkers.clear();
                    mRefChild.removeValue();
                    selectedMarker.remove();
                    Toast.makeText(getActivity(), "Trash can marker has been deleted from the map.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        addCans = (ImageView)mView.findViewById(R.id.btn_goToAddCans2);
        addCans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enable add trash
                isAddMap = true;
                Toast.makeText(getActivity(), "Click a place on the map to add a trash can.", Toast.LENGTH_SHORT).show();
            }
        });
    }

/*    private void getAllUsers(Map<String,Object> users, GoogleMap mMap){
        ArrayList<Mapbase> mapbases = new ArrayList<>();

        for(Map.Entry<String, Object> entry : users.entrySet()){
            Map singleUser = (Map) entry.getValue();
            mapbases.add(new Mapbase((String) singleUser.get("displayName"),(String) singleUser.get("latitude"),
                    (String) singleUser.get("longitude"), (String) singleUser.get("placedBy"), (String) singleUser.get("uId")));
        }

        for(Mapbase mapbase: mapbases){
            LatLng latLng = new LatLng(Double.parseDouble(mapbase.getLatitude()), Double.parseDouble(mapbase.getLongitude()));
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(mapbase.displayName).snippet("Placed by: " + mapbase.getDisplayName()));
        }
    }*/

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            _toGotoAddCans = (onGoToAddCans2) activity;
        }catch (Exception ex){

            throw new RuntimeException(activity.toString() + " must implement UpdateProfile");

        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        _toGotoAddCans = null;
    }

    public interface onGoToAddCans2 {

        public void toGotoAddCans2();
    }
}

