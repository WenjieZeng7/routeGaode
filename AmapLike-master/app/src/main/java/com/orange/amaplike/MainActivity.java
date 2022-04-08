package com.orange.amaplike;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocationClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AMapLocationClient.updatePrivacyAgree(this, true);
        AMapLocationClient.updatePrivacyShow(this, true, true);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
            }

    @OnClick({R.id.goin_text})
    public void onViewclick(View view){
        if (view.getId()==R.id.goin_text){
            startActivity(new Intent(MainActivity.this,RoutePlanActivity.class));
        }
    }
}
