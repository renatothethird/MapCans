package com.example.masterpeps.mapcans2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    Button loginBtn;
    TextView mapCans;
    EditText email, password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean isLogin;
    String password2, email2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    if(isLogin) {
                        Toast.makeText(MainActivity.this, "Signing in, please wait.", Toast.LENGTH_SHORT).show();
                    }
                    isLogin = false;
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    saveToSharedPreferences(firebaseAuth);
                    startActivity(intent);
                }
            }
        };
        init();
    }

    //for change password
    public void saveToSharedPreferences(FirebaseAuth firebaseAuth){
        SharedPreferences sharedPreferences = getSharedPreferences(firebaseAuth.getCurrentUser().getUid(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userPass", password2);
        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void init()
    {
        mapCans = (TextView)findViewById(R.id.textView_mapCans);
        email = (EditText)findViewById(R.id.editText_email);
        password = (EditText)findViewById(R.id.editText_password);
        loginBtn = (Button) findViewById(R.id.btn_Login);
        isLogin = false;
        String text = "<font color=#000>Map</font><font color=#4CAF50>Cans</font>";
        mapCans.setText(Html.fromHtml(text));
        loginBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                login();
            }
        });
    }

    public void login() {
        email2 = email.getText().toString();
        password2 = password.getText().toString();

        if (TextUtils.isEmpty(email2)) {
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password2)) {
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_LONG).show();

        } else {
            mAuth.signInWithEmailAndPassword(email2, password2)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Sign in failed. Check your username/password.", Toast.LENGTH_SHORT).show();
                            }else{
                                isLogin = true;
                            }
                        }
                    });
        }
    }

   /* public void loginButton(View view)
    {
        loginBtn = (Button)findViewById(R.id.btn_Login);
        Intent professorIntent = new Intent(getApplicationContext(), HomeActivity.class);
        professorIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(professorIntent);
        Toast.makeText(getApplicationContext(),
                "Redirecting...", Toast.LENGTH_LONG).show();
    }*/

    public void createAccountButton(View view)
    {
        loginBtn = (Button)findViewById(R.id.btn_CreateAccount);
        Intent professorIntent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        professorIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(professorIntent);
        Toast.makeText(getApplicationContext(),
                "Redirecting...", Toast.LENGTH_LONG).show();

    }
}
