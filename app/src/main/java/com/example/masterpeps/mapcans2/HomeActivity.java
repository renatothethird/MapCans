package com.example.masterpeps.mapcans2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.masterpeps.mapcans2.Fragment.AddCansFragment;
import com.example.masterpeps.mapcans2.Fragment.FAQFragment;
import com.example.masterpeps.mapcans2.Fragment.ForgotPasswordFragment;
import com.example.masterpeps.mapcans2.Fragment.MapFragment;
import com.example.masterpeps.mapcans2.Fragment.MapFragment2;
import com.example.masterpeps.mapcans2.Fragment.ProfileFragment;
import com.example.masterpeps.mapcans2.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.onGoToChangePassword
                ,MapFragment2.onGoToAddCans2, ForgotPasswordFragment.onGoBackToProfile, AddCansFragment.onAddDisplayName{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String userPassword;
    private double longitude, latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
    }

    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }
    public void setLatitudeLongitude(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Override
    public void toGoBackToProfile(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
    }

    @Override
    public void toGotoChangePassword(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,  new ForgotPasswordFragment()).commit();
    }

    @Override
    public void toGotoAddCans2(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle latLangBundle = new Bundle();
        latLangBundle.putDouble("latitude", latitude);
        latLangBundle.putDouble("longitude",longitude);
        AddCansFragment addCansFragment = new AddCansFragment();
        addCansFragment.setArguments(latLangBundle);
        fragmentManager.beginTransaction().replace(R.id.content_frame, addCansFragment).commit();
    }

    @Override
    public void toGoToBackHome(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new MapFragment2()).commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_profile) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
        } else if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MapFragment2()).commit();
        }else if (id == R.id.nav_faq) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FAQFragment()).commit();
        }else if (id == R.id.nav_logout) {
            final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Are you sure you want to Log Out?");
            dlgAlert.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });

            dlgAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mAuth.signOut();
                    startActivity(homeIntent);
                }
            });

            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
