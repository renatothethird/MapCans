package com.example.masterpeps.mapcans2.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masterpeps.mapcans2.HomeActivity;
import com.example.masterpeps.mapcans2.Model.User;
import com.example.masterpeps.mapcans2.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private onGoToChangePassword _toGotoChangePassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Firebase mRef;
    TextView changePassword;
    TextView name, email, birthday, contact;
    View fragmentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Firebase.setAndroidContext(this.getActivity());
        mAuth = FirebaseAuth.getInstance();
        fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    final String uId = firebaseAuth.getCurrentUser().getUid().toString();
                    mRef = new Firebase("https://mapcans-825b5.firebaseio.com/User/" + uId);
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, String> map = dataSnapshot.getValue(Map.class);
                            name.setText(map.get("userName"));
                            //System.out.print("wala pa");
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(uId, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName", map.get("userName").toString());
                            editor.apply();
                            email.setText(map.get("userEmail"));
                            contact.setText(map.get("userContact"));
                            birthday.setText(map.get("userBirthday"));
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }
        };
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void init()
    {
        name = (TextView)fragmentView.findViewById(R.id.editText_name);
        email = (TextView)fragmentView.findViewById(R.id.editText_email);
        birthday = (TextView)fragmentView.findViewById(R.id.editText_birthday);
        contact = (TextView)fragmentView.findViewById(R.id.editText_contact);
        changePassword = (TextView)fragmentView.findViewById(R.id.textView_changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _toGotoChangePassword.toGotoChangePassword();
            }
        });
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            _toGotoChangePassword = (onGoToChangePassword) activity;
        }catch (Exception ex){

            throw new RuntimeException(activity.toString() + " must implement UpdateProfile");

        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        _toGotoChangePassword = null;
    }

    public interface onGoToChangePassword {

        public void toGotoChangePassword();
    }

}
