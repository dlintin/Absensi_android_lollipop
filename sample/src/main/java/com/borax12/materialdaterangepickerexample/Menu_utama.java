package com.borax12.materialdaterangepickerexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import com.borax12.materialdaterangepickerexample.CONF.UserSessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Menu_utama extends AppCompatActivity {

    UserSessionManager session;

    @BindView(R.id.nelumscan)
    CardView nelumscan;
    @BindView(R.id.terbaru)
    CardView terbaru;
    @BindView(R.id.berdasarkan_tanggal)
    CardView berdasarkanTanggal;
    @BindView(R.id.perorang)
    CardView perorang;
    @BindView(R.id.status)
    CardView status;
    @BindView(R.id.akumulasi)
    CardView akumulasi;
    @BindView(R.id.logout)
    CardView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        ButterKnife.bind(this);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        if(session.checkLogin())
            finish();

    }

    @OnClick(R.id.nelumscan)
    public void onNelumscanClicked() {
        Intent intent = new Intent(Menu_utama.this, Activity_belum_scan.class);
        startActivity(intent);
    }

    @OnClick(R.id.terbaru)
    public void onTerbaruClicked() {
        Intent intent = new Intent(Menu_utama.this, Activity_terbaru.class);
        startActivity(intent);
    }

    @OnClick(R.id.berdasarkan_tanggal)
    public void onBerdasarkanTanggalClicked() {
        Intent intent = new Intent(Menu_utama.this, Activity_range_tanggal.class);
        startActivity(intent);
    }

    @OnClick(R.id.perorang)
    public void onPerorangClicked() {
    }

    @OnClick(R.id.status)
    public void onStatusClicked() {
        Intent intent = new Intent(Menu_utama.this, Activity_status.class);
        startActivity(intent);
    }

    @OnClick(R.id.akumulasi)
    public void onAkumulasiClicked() {
        Intent intent = new Intent(Menu_utama.this, Activity_akumulasi.class);
        startActivity(intent);
    }

    @OnClick(R.id.logout)
    public void onLogoutClicked() {
        session.logoutUser();
        Intent intent = new Intent(Menu_utama.this, Login.class);
        finish();
        startActivity(intent);
    }
}