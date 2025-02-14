package com.nursing.nandapro.nicnoc;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;


import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;
import java.util.ArrayList;

public class Walpapers extends Activity {

    // private MaxAdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Quitamos barra de notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Quitamos barra de titulo de la aplicacion
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walpapers);



        String[] permissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                File f = new File(Environment.getExternalStorageDirectory() + "/Wallpaper 4k");

                if (!f.exists()) {
                    f.mkdirs();
                    f.mkdir();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent mainintent = new Intent(Walpapers.this, SecongPage.class);
                        startActivity(mainintent);
                        finish();

                    }
                }, 2000);

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                finish();
            }
        });


        //  adView.bringToFront();


    }



}