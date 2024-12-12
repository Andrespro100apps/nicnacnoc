package com.nursing.nandapro.nicnoc;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.nativead.NativeAd;
import com.nursing.nandapro.nicnoc.Activitys.MainActivity;
import com.nursing.nandapro.nicnoc.DiagnosticoBusqueda.DiagnosticosBusqueda;
import com.nursing.nandapro.nicnoc.Fragments.RewardedFragment;
import com.nursing.nandapro.nicnoc.R;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nursing.nandapro.nicnoc.utils.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Libro2diagnostico extends AppCompatActivity {

    List<ListElement> elements;
    Cursor c = null;
    private InterstitialAd interstitial;
    Prefs prefs;
    NativeAd mNativeAd;

    protected void onResume() {
        // UnityAds.show(MainActivity.this, interstitial_id);

        // UnityBanners.loadBanner(MainActivity.this, banner_id);
        if (interstitial != null) {
            interstitial.show(Libro2diagnostico.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }        super.onResume();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Quitamos barra de notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_diagnosticos);

        //Título del action bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getIntent().getStringExtra("Titulo"));
        ab.setSubtitle(getIntent().getStringExtra("Subtitulo"));

        //lista
        init();
        prefs = new Prefs(getApplicationContext());

        //sobre la edad
        Bundle extras = new Bundle();
        extras.putString("max_ad_content_rating", "MA");

        AdRequest request = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();
        // Buscar AdView como recurso y cargar una solicitud.
        if (prefs.getPremium()==0){
        if (prefs.getRemoveAd()==0){
            //initializing admob and loading the banner ad.
            lanzarwifi();

            //sobre la edad
           // lanzarwifi();
            // Buscar AdView como recurso y cargar una solicitud.
            AdView adView = (AdView)this.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setVisibility(View.VISIBLE);
          //  adView.setVisibility(View.VISIBLE);

            MobileAds.initialize(this, initializationStatus -> {
            });

            // AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(this,(getResources().getString(R.string.interaid)), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            interstitial = interstitialAd;
                            Log.i(TAG, "onAdLoaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.i(TAG, loadAdError.getMessage());
                            interstitial = null;
                        }
                    });

            MobileAds.initialize(this);
            AdLoader adLoader = new AdLoader.Builder(this, getResources().getString(R.string.nativeid))
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            //   View dialogView = getLayoutInflater().inflate(R.layout.activity_main, null);

                            //  TemplateView template = dialogView.findViewById(R.id.my_template);

                            // template.setVisibility(View.GONE);

                            if(mNativeAd!=null) {
                                NativeTemplateStyle styles = new
                                        NativeTemplateStyle.Builder().build();
                                TemplateView template = findViewById(R.id.my_template);


                                template.setStyles(styles);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);

                            }
                            // NativeTemplateStyle styles = new
                            //      NativeTemplateStyle.Builder().build();
                            //    TemplateView template = findViewById(R.id.my_template);
                            //  template.setVisibility(View.VISIBLE);

                            // template.setStyles(styles);
                            // template.setNativeAd(nativeAd);
                        }
                    })
                    .build();

            adLoader.loadAd(new AdRequest.Builder().build());




        }
        }
    }



    public void lanzarwifi() {
        //9 Toast.makeText(this, "Incompleto :c", Toast.LENGTH_SHORT).show();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null){
            //  if(isConnected())
            // showDialog();
            // Toast.makeText(getApplicationContext(),"INTERNET AVAILABLE",Toast.LENGTH_SHORT).show();
            // else if(isConnected())
            //
            //  info();

        }
        else{
            showDialog();
            Toast.makeText(getApplicationContext(),"INTERNET NOT AVAILABLE",Toast.LENGTH_SHORT).show();
        }
    }
    void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alert_dialog, null);

        Button acceptButton = view.findViewById(R.id.acceptButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: accept button");
                //  connectGooglePlayBilling();
                lanzarsuscricion();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: cancel button" );

                lanzarinicio();
            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                // .setCanceledOnTouchOutside(false);
                .create();
        // alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();



    }

    public void lanzarsuscricion() {
        //9 Toast.makeText(this, "Incompleto :c", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Libro2.class );
        startActivity(i);
/*
        if(isConnected())
            Toast.makeText(getApplicationContext(),"INTERNET AVAILABLE",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"INTERNET NOT AVAILABLE",Toast.LENGTH_SHORT).show();
        //  info();
        showDialog();
*/
    }

    public void lanzarinicio() {
        //9 Toast.makeText(this, "Incompleto :c", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
/*
        if(isConnected())
            Toast.makeText(getApplicationContext(),"INTERNET AVAILABLE",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"INTERNET NOT AVAILABLE",Toast.LENGTH_SHORT).show();
        //  info();
        showDialog();
*/
    }

    public void init(){
        String nDominio = getIntent().getStringExtra("nDominio");
        String nClase = getIntent().getStringExtra("nClase");
        elements = new ArrayList<>();

        DatabaseHelper myDbHelper = new DatabaseHelper(com.nursing.nandapro.nicnoc.Libro2diagnostico.this, MainActivity.namedb2);
        try {
            myDbHelper.createDataBase();
            myDbHelper.openDataBase();
        } catch (IOException ioe) {
        }
        c = myDbHelper.query("Diagnosticos", null, "ClaseN like '"+nClase+"' and " +"DominioN like '"+nDominio+"'"  , null, null, null, null);
        if (c.moveToFirst()) {
            do {
                elements.add(new ListElement(c.getString(0),
                        c.getString(1),c.getString(2),
                        c.getString(3),c.getString(4), false));
            } while (c.moveToNext());
        }


        ListAdapter listAdapter = new ListAdapter(elements, this, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListElement item) {

                String nCodigo = item.getDominio();
                Intent mIntent  = new Intent(com.nursing.nandapro.nicnoc.Libro2diagnostico.this, Libro2descripcion.class);
                mIntent.putExtra("nCodigo",nCodigo);

                mIntent.putExtra("Titulo",getIntent().getStringExtra("Titulo"));
                mIntent.putExtra("Subtitulo",getIntent().getStringExtra("Subtitulo"));

                startActivity(mIntent);
                if (interstitial != null) {
                    interstitial.show(Libro2diagnostico.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
                overridePendingTransition(R.anim.left_in, R.anim.left_out);

            }
        });


        RecyclerView recyclerView = findViewById(R.id.listRecyclerview);
        recyclerView.setHasFixedSize(true);
        // puede ser cuadricula
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    //region Menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            shareenviar();
        }

        if(id == R.id.valorar) {
           // calificar();
            Intent mIntent  = new Intent(Libro2diagnostico.this, Favoritos.class);
            mIntent.putExtra("Base", "Libro2");
            startActivity(mIntent);
        }

        if (id == R.id.action_about) {
            info();
        }

        if (id == R.id.action_search) {
            Intent mIntent = new Intent(Libro2diagnostico.this, DiagnosticosBusqueda.class);
            mIntent.putExtra("Base", "Libro2");
            startActivity(mIntent);
        }
        if(id == R.id.favoritos){
            Intent mIntent  = new Intent(Libro2diagnostico.this, Favoritos.class);
            mIntent.putExtra("Base", "Libro2");
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void shareenviar(){
        try {
            final String appPackageName = getPackageName();
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id="+appPackageName;
            String shareSub = "enviando/share link";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share Using"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
    public void info(){
        AlertDialog.Builder a = new AlertDialog.Builder(com.nursing.nandapro.nicnoc.Libro2diagnostico.this);
        a.setMessage("This application is designed to have a good time between friends or family the sound is HD made by @ANDRESPRO100" + "\n Version 1.5")
                .setCancelable(false)
                .setPositiveButton("Acept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a.create();
        a.setTitle("About");
        a.show();
    }
    public void calificar(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(com.nursing.nandapro.nicnoc.Libro2diagnostico.this);
        // Setting Dialog Message
        alertDialog.setTitle("CALIFICAR QUALIFY");
        alertDialog.setMessage(" \n" +
                "Your comment is very important to me, please VALUE or QUALIFY ..." +
                "\n" +
                "Or leave a comment to improve this application " +
                "You'll only take a few minutes. Thanks for your help..."  +"\n" + "\n" +
                "تعليقك مهم جدًا بالنسبة لي ، يرجى RATE أو RATE ... " +
                "أو اترك تعليقاً لتحسين هذا التطبيق ... لن يستغرق الأمر سوى بضع دقائق. شكرا لمساعدتك!");

        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("===>> RATE <<===", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                }
            }
        });
        alertDialog.show();

    }
    //endregion
}
