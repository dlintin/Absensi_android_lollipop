package com.borax12.materialdaterangepickerexample;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    UserSessionManager session;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    Button btn_logout;
    TextView txt_id, txt_username;
    String id, username;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        if(session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        String name = user.get(UserSessionManager.KEY_NAME);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getFragmentManager();

        // tampilan default awal ketika aplikasii dijalankan
        if (savedInstanceState == null) {
            fragment = new Root();
            callFragment(fragment);
        }

        txt_id = findViewById(R.id.txt_id);
        txt_username = findViewById(R.id.txt_username);
        btn_logout = findViewById(R.id.btn_logout);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

       /* txt_id.setText("ID : " + id);
        txt_username.setText("USERNAME : " + username);

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

        */

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        // Untuk memanggil layout dari menu yang dipilih
        if (id == R.id.blm_scan) {
            fragment = new blm_scan();
            callFragment(fragment);
        } else if (id == R.id.last) {
            fragment = new terbaru();
            callFragment(fragment);
        } else if (id == R.id.nav_manage) {
            fragment = new Tools();
            callFragment(fragment);

        } else if (id == R.id.nav_send) {
            fragment = new Send();
            callFragment(fragment);
        } else if (id == R.id.stat_absensi) {
            fragment = new cari_stat_absensi();
            callFragment(fragment);
        }else if (id == R.id.jml_absensi) {
            fragment = new Send();
            callFragment(fragment);
        }else if (id == R.id.btn_logout) {
            session.logoutUser();
            Intent intent = new Intent(MainActivity.this, Login.class);
            finish();
            startActivity(intent);

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // untuk mengganti isi kontainer menu yang dipiih
    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container, fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}
