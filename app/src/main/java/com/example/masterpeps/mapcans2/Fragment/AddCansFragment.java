package com.example.masterpeps.mapcans2.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.masterpeps.mapcans2.HomeActivity;
import com.example.masterpeps.mapcans2.Model.Mapbase;
import com.example.masterpeps.mapcans2.R;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCansFragment extends Fragment {
    View fragmentView;
    private Firebase mRef;
    private double longitude, latitude;
    EditText displayName;
    Button addDisplayName;
    private onAddDisplayName _toGoToBackHome;
    public AddCansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Firebase.setAndroidContext(getActivity());
        longitude = getArguments().getDouble("longitude");
        latitude = getArguments().getDouble("latitude");
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_add_cans, container, false);
        init();
        return fragmentView;
    }
    private void init(){
        displayName = (EditText) fragmentView.findViewById(R.id.editText_addDetails);
        addDisplayName = (Button) fragmentView.findViewById(R.id.btn_addTrashcan);

        addDisplayName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if((displayName.getText().toString()).isEmpty()){
                    Toast.makeText(getActivity(), "Please add a display name.", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(user.getUid(), Context.MODE_PRIVATE);
                    String userName = sharedPreferences.getString("userName", "");
                    mRef = new Firebase("https://mapcans-825b5.firebaseio.com/Maps");
                    String latitude2 =  String.valueOf(latitude).replace(".", "");
                    String longitude2 = String.valueOf(longitude).replace(".","");
                    Firebase mRefChild = mRef.child("latLng" + latitude2 + ""  +longitude2);
                    Mapbase mapbase = new Mapbase(displayName.getText().toString(), String.valueOf(latitude), String.valueOf(longitude), userName, FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    mRefChild.setValue(mapbase);
                    _toGoToBackHome.toGoToBackHome();
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            _toGoToBackHome = (HomeActivity) activity;
        }catch (Exception ex){

            throw new RuntimeException(activity.toString() + " must implement UpdateProfile");

        }
    }
    @Override
    public void onDetach(){
        super.onDetach();
        _toGoToBackHome = null;
    }

    public interface onAddDisplayName {

        public void toGoToBackHome();
    }

}
