package com.example.masterpeps.mapcans2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masterpeps.mapcans2.Model.User;
import com.example.masterpeps.mapcans2.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {
    TextView email, password,login;
    EditText name, contact, birthday;
    private Firebase mRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_create_account);
        init();
    }

    public void init()
    {
        email = (TextView)findViewById(R.id.editText_email);
        password = (TextView)findViewById(R.id.editText_password);
        login = (TextView)findViewById(R.id.textView_Login);
        name = (EditText)findViewById(R.id.editText_name);
        contact = (EditText)findViewById(R.id.editText_contact);
        birthday = (EditText) findViewById(R.id.editText_birthday);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void onCreateAccount(View view){
        createCustomAccount();
    }
    public void createCustomAccount() {
        String email2 = email.getText().toString();
        String password2 = password.getText().toString();
        final String name2 = name.getText().toString();
        final String contact2 = contact.getText().toString();
        final String birthday2 = birthday.getText().toString();
        //String confirmPassword = etConfirmPassword.getText().toString();
        //String firstName = etFirstName.getText().toString();
        //String lastName = etLastName.getText().toString();
        //displayName = firstName + " " + lastName;

        if (TextUtils.isEmpty(email2) || TextUtils.isEmpty(password2) ){
                //|| TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(firstName)||
                //TextUtils.isEmpty(lastName)){

            Toast.makeText(this, "Please fill out all the fields.", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(CreateAccountActivity.this, "Creating account, please wait...", Toast.LENGTH_LONG).show();
            //if(password2.equals(confirmPassword)){
                mAuth.createUserWithEmailAndPassword(email2, password2)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(CreateAccountActivity.this, "Sign up failed.", Toast.LENGTH_SHORT).show();
                                } else if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    //customAccountInformation(user, displayName);
                                    mRef = new Firebase("https://mapcans-825b5.firebaseio.com/User");
                                    Firebase mRefChild = mRef.child(user.getUid());
                                    User user1 = new User(user.getEmail(), name2, birthday2, contact2);
                                    mRefChild.setValue(user1);
                                    /*customAccountInformation(user, "juan dela cruz");*/
                                    mAuth.signOut();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }
                        });
           /* } else{
                Toast.makeText(this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
            }*/
        }
    }


    /*public void customAccountInformation(final FirebaseUser user, String displayName){

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();
        System.out.println("taena");
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            User user1 = new User(user.getEmail(), user.getDisplayName(), "01/01/1991", "09123456789");
                            //User user = new User(name, email);

                            mDatabase.child("user").child(user.getUid()).setValue(user1);
                        }
                    }
                });
    }*/
}
