package com.example.masterpeps.mapcans2.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masterpeps.mapcans2.HomeActivity;
import com.example.masterpeps.mapcans2.MainActivity;
import com.example.masterpeps.mapcans2.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {
    private onGoBackToProfile _toGoBackToProfile;
    View fragmentView;
    EditText currentPassword,newPassword,confirmPassword;
    Button changePassword;
    private FirebaseUser user;
    boolean isCorrectPassword;
    String userPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(user.getUid(), Context.MODE_PRIVATE);
        userPassword = sharedPreferences.getString("userPass", "");
        fragmentView =inflater.inflate(R.layout.fragment_forgot_password, container, false);
        init();
        return fragmentView;
    }


    public void init()
    {
        currentPassword = (EditText) fragmentView.findViewById(R.id.editText_currentPassword);
        newPassword = (EditText) fragmentView.findViewById(R.id.editText_newPassword);
        confirmPassword = (EditText) fragmentView.findViewById(R.id.editText_confirmPassword);
        changePassword = (Button)fragmentView.findViewById(R.id.btn_changePassword);
        isCorrectPassword = false;
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserPassword();

            }
        });
    }

    public void changeUserPassword(){
        if(TextUtils.isEmpty(currentPassword.getText().toString()) ||
                TextUtils.isEmpty(newPassword.getText().toString()) ||
                TextUtils.isEmpty(confirmPassword.getText().toString())){
            Toast.makeText(getActivity(), "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
        }else {
            if (!(currentPassword.getText().toString().equals(userPassword))) {
                Toast.makeText(getActivity(), "Incorrect current password.", Toast.LENGTH_SHORT).show();
            } else {
                    if (!(newPassword.getText().toString().equals(confirmPassword.getText().toString()))) {
                        Toast.makeText(getActivity(), "New password and password confirmation does not match.", Toast.LENGTH_SHORT).show();
                    } else {
                        user.updatePassword(newPassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Password has been changed", Toast.LENGTH_SHORT).show();
                                            ((HomeActivity) getActivity()).setUserPassword(newPassword.getText().toString());
                                            _toGoBackToProfile.toGoBackToProfile();
                                        }
                                    }
                                });
                    }
            }
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            _toGoBackToProfile = (onGoBackToProfile) activity;
        }catch (Exception ex){

            throw new RuntimeException(activity.toString() + " must implement UpdateProfile");

        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        _toGoBackToProfile = null;
    }

    public interface onGoBackToProfile {

        public void toGoBackToProfile();
    }

}
