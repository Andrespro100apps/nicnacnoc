package com.nursing.nandapro.nicnoc;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nursing.nandapro.nicnoc.utils.Prefs;

import com.github.clans.fab.FloatingActionMenu;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class SecongPage extends Activity {

    //private MaxAdView adView;
    private InterstitialAd interstitial;
    Prefs prefs;
    @Override
    protected void onResume() {
        // UnityAds.show(MainActivity.this, interstitial_id);

        // UnityBanners.loadBanner(MainActivity.this, banner_id);
        if (interstitial != null) {
            interstitial.show(SecongPage.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }        super.onResume();

    }
    RecyclerView rcvMain;
    ArrayList<String> imgArrylist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FloatingActionMenu materialDesignFAM;
        com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5, floatingActionButton6, floatingActionButton7;

        //Quitamos barra de notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Quitamos barra de titulo de la aplicacion
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secong_page);
/*
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
            }
        } );
        //LoadBannerAd();
*/
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.share);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_home);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_info);
        floatingActionButton4 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_rate);
        floatingActionButton5 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_face);
        //floatingActionButton6 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_youtu);
        floatingActionButton7 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floating_inste);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareenviar();

            }
        });floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Home button
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }finally {

                }
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Info button
                //Info button
                Intent twitterIntent = getOpenTwitterIntent(SecongPage.this);
                startActivity(twitterIntent);
            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(SecongPage.this);
                // Setting Dialog Message
                alertDialog.setTitle("Valora");
                alertDialog.setMessage(" \n" +
                        "Your comment is very important to me, please VALUE or QUALIFY ..." +
                        "\n" +
                        "Or leave a comment to improve this application " +
                        "You'll only take a few minutes. Thanks for your help..."  +"\n" + "\n" +
                        "Tu comentario es muy importante para mi, por favor VALORA o CALIFICA... " +
                        "O deja un comentario para poder mejorar esta alicación...Solo te va a tomar unos pocos minutos. Gracias por tu ayuda!");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));

                        } catch (android.content.ActivityNotFoundException anfe) {
                        }finally {

                        }
                    }
                });
                alertDialog.show();
            }
        });
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent facebookIntent = getOpenFacebookIntent(SecongPage.this);
                startActivity(facebookIntent);

            }
        });

        floatingActionButton7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Intent instagramIntent = getOpenInstagramIntent(SecongPage.this);
                startActivity(instagramIntent);
            }
        });


        prefs = new Prefs(getApplicationContext());

        //0 muestra los anuncios 1 no muestra por eso no se pone nada
        if (prefs.getPremium()==0){

            if (prefs.getRemoveAd()==0){
            //initializing admob and loading the banner ad.
            //sobre la edad
            Bundle extras = new Bundle();
            extras.putString("max_ad_content_rating", "MA");

            AdRequest request = new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build();
            // Buscar AdView como recurso y cargar una solicitud.
            AdView adView = (AdView)this.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            adView.setVisibility(View.VISIBLE);


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

        }  }

        //imgArrylist.add("https://static.wixstatic.com/media/148cba_ba733ff12bc74dc8a84a6889a8972b44~mv2.jpg");
        //     imgArrylist.add("https://static.wixstatic.com/media/148cba_b63c51f3bca04aac847eb76069d36cae~mv2.jpg");
        //  imgArrylist.add("https://static.wixstatic.com/media/148cba_3886d27ff42048ea8ca022abf27066dc~mv2.jpg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_d5de909e4197454aafdc408f601f3027~mv2.jpeg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_4313073c8ddc48c0a36e4ff5e266fd39~mv2.png");
        //imgArrylist.add("https://static.wixstatic.com/media/b13363_641a143098b24df58715c845a539eb0c~mv2.png");

        //imgArrylist.add("https://static.wixstatic.com/media/148cba_edeabc0b19494d39a1a2ee3f17aeb72f~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_42572153bd1548c39b5b47bb871a5ca9~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_dadbb20fd1274aff885caa6529f65665~mv2.png");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_8e28232c59294bb0a4c4222a3ad567d6~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_7b3f71976c094c31a6328d3ac057c410~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_a7dbcab4de5d409e89a77cd2ef19a2a8~mv2.jpeg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_f9cc47e5c22442f9825145365cff23bc~mv2.jpeg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_ca3bbf5e87fd49ec98fc6c8c14874e7a~mv2.png");
        // imgArrylist.add("https://static.wixstatic.com/media/148cba_23cd183144c54bbfb6eb1f27fb072de4~mv2.jpg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_6d28938838df49d9b2a36d11034d0bc8~mv2.png");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_11c1243f340d4987a6472d45f63e2ecf~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_945a61f7885e4810accd23f7cf73213b~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_ae90758ad345439cb84ae69841a87d05~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_2dfe93b7034f4b8f8c7972d942f5ba9c~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_dfea8f76df1048fcaeba2d4cb7c84abe~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_efcd5511877f45408e4310f6a91250b4~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_616e649e402641ae879cbf96c233f49e~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_9ce51d4d9e62497891f6e408bf8cb9a6~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_bf625f7a046a4f73993d1b818c6a2185~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_1cb884c2bd734c44a810e522444516db~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_00eb835aa49b4a28ac220381f64d9e68~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_728e927267aa4dabb1c969a97d2d58cf~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_6c4f903d6a83471786e8ce87712a5978~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_ec7e1298ab734ed38b98629ab4452a4e~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_3bbcf74d245e427cb86c8be82277b35b~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_2724d59257354849aa90c5e4a7ae2367~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_1061fecdd8cb4202baedd3888f63e99f~mv2.jpg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_d95148bed634472a98979601d647a1fd~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_20efd4a9cc874d918b408e9122b11ef5~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_1db702c348054442be53448367c0841e~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_66aa32219c074105b8f583811b354745~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_b22987a68ff145e3bc27fc2fd3814762~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_41d1978f7b3345ddbc417d9f2f72622c~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_435e2be518d14a0794ea8105c6a9edb2~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_f587b54e99b7466381f0df1351b6b6d0~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_71c093edd629426e94a23fb1e45a4deb~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_b15b3b7793fd471a8a7536b4a44fa40f~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_f64ea53e30814533b0d88d73fb6b9e57~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_82dbafff1a7d484bb332f89bc3b19b57~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_31666ee265304e688101b3062294386e~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_0c0ef58d9f7b4dbfae45e0c9c15998d2~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_ba44b840ab9d47628c1bdd3556e27991~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_059fea10b1494af8b8d457f0b1c210f6~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_93f12a627de043ebb8501d7478fa3a71~mv2.jpg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_39aa26a96d73404c9e6b4c11b859ef2d~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_4cad9d88432547eaa1e0be8e7c13ca5f~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_5e44bc4dfcd841c2ac0a8418cf8fa0c5~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_97385e6a8f9149158daca6eaba7cc6c9~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_9c5f226e1091431aa537f9f49a609308~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_23387ee50867458385310c0517d99081~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_b3291e527e0e448db3a1c13bd8f3c3bc~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_ec0ccf7275bf44f7b624fd7ea1086423~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_94a22f979a1d4443b6007deec56d20c9~mv2.jpeg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_cb85db2f939c4cf298f6fa08f9b7e38d~mv2.jpeg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_e0661f5cb2fe497d805c872ecb499e74~mv2.png");


        imgArrylist.add("https://static.wixstatic.com/media/b13363_25a4039ec97e474eb02be6dd5cdd3fc7~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_d972c7ab2ec44274ad00d23b5c9ec448~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_c1887125d30547d58aa95bb4d64e1749~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_cae59ed5193d4ab6879ccce6ed95d1bc~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_f19d9a45da0848be998a989f460b583a~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_cabac774d36840e5bc6cf2b5a5bb0383~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_2348061987524917afd7c3c87431b8cb~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_3d354456ce89402b97fd73cb8f42a477~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_fca2df37058c4e97a00b39e4410fcdc5~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_009a52cf9bae4a6693044c8179a63ca0~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_f4bf2c6b39af451cb3802def513619eb~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_7383108994d34780b26e55717b5dd391~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_085dc42917e846dea6f6fa9563069181~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_a0c0be3f73894bc9befa64158d04e03a~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_8be9aa7fec304d3f911fca54fba77f4f~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_b9f6f31b27cf4c5586afbcf6f8c03894~mv2.png");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_b143fde9dd3d4f3f9face6cad13eb1bf~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_657f71fd95ff4671a28549f52a561713~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_d0ea760e947041e2bf7ff79c4f3bb959~mv2.webp");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_c7f3ddbdc6904bfb938b58ce99cea71f~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_413cb9345ca64e1b950704c5b4b5be2a~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_c8600d922ebc4ec0a2289efd05adaf8b~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_e6b632abc84b4132912f42d4b46559aa~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_d54f636db6da47e0b24c8ea5ceee62fe~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_5493e5d429fc4a9a8921d3de62000592~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_b5ee6f8fc71e4367b77181815898849c~mv2.png");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_1cb388e55e934d9cbef913bc4c74f607~mv2.png");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_827fe30f61134ccfb51d1d8a66a2fe14~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_cb586e647621482996d68800195d846e~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_192bd8a498054ba384f9f358590e5c97~mv2.jpg");
        imgArrylist.add("hhttps://static.wixstatic.com/media/b13363_6627ec4115894217b547b028bdc00533~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_fde6c4b5237140ee810846220b7a2a88~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_d25a58ae511f4f7f9b6925ecebc261c3~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_e14310670c894d428088daf76bcf692d~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_7a02bbc69ade48958c412ee6f6be8b82~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_8a3456c50b7946e4bd128278f6b2ad84~mv2.jpg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_c98803960c6844e2989cebd25f03a9c2~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_d9070323ca864cb7849636bced2524bc~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_d8c72c0801d244a6b4c060236ea78c1b~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_8ae284d21c8647f78797bfa5dfbcda17~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_ef2c47abf7864c52954374b706991009~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_78c5f02668ae45fc9e2a01452d2b1571~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_a250db580fcf49d1872bfee16c5cafa4~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_ee9f26d1eb064436b5f2e01fbc79bb61~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_66eef386267340a6853b13cc0f0a1de0~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_b52a1a92ccaf45fbbfea7b416b839ccb~mv2.jpg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_0816a5c159c2436c94f309081fa3f2e9~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_8f69318e359b4c57bcb0806f2edf556b~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_46d5b266902e4c64b4b259647b5df9ab~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_0dcab19a8b274423a8cc13798c456130~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_bc9143bcf82c455c91bfd795cb944e60~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_9310b27949f545b99e7b9ef6b489327f~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_01db5c09d21d4e3ab62858f69444d6b9~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_79d7d98bfe5848df9b9885086bab07dd~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_c4bf776155004e47801c74dcc693122f~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_b9e96fc780994ef9864d96b6543ced94~mv2.jpg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_bf1f02e6879f4efc81d452aaedb47a9f~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_cc04185c40ed436db1bbf62c355c19f4~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_13246ad00e094941a127ae4cb77f72a5~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_c461bf8d19e8491bbf34d9b9f27ceb6c~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_804c48a29aa44ac3a9af831c4532306d~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_fa739797cfe842efb08a48cdf00cc334~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_f4a031e7d57d48bb8f9d50153268f9a6~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_e90fd5d8e45841cea7e05c24cc317a3c~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_8abc8f42739f46da9ded8611c762602e~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_691de1fb88c34e7d96f218c4a60e0823~mv2.jpg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_40580aba48c342d581236603dbab816e~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_60f83b01054a45888f9cd59dc1111c0a~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_edec0721358d4ac58f5d04507b5bc181~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_645858adcf7f423c8fcb0e1fc0f34cd0~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_d0b7a815ecf84ebd9a19a7109eed4d2b~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_a438d79c3ef045bfa23ca62da6ea28da~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_1b6a2de8e7d44d838f843d20d4a82518~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_6b68cbc5cdb14cc1a2347a01423522a0~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_8a4cf0bd2080452996251796e29b9a32~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_67b26e22f6a84ba38701c338f87abcfe~mv2.jpg");

        imgArrylist.add("https://static.wixstatic.com/media/b13363_4541201ee7104406a3f1b90a563f38c5~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_a4de9afb6def4db09fec9673418456bd~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_1dc0e06e55c24949bd66c86801cd164f~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_5833803a06e6488db14213b8a5738511~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_22e2c3fc9c6d4dc7b9e0a6dcf4ba5f9b~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_b5a845acabb94fb8aa8962a46262c0c4~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_559eca1d5b8f42a2ab538bc7e72bc9f5~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_431cbb87654949398eff0a60a3f9bed0~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_e40317c979864acdb94cdad279d7b7ca~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_75fc7d2b0247457fa4fbca1fd4a5b173~mv2.jpg");

//
        imgArrylist.add("https://static.wixstatic.com/media/b13363_aea3774653854bcb825de679f3720106~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_7ce97e637fad46f7962e6e2484218a92~mv2.jpeg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_98eb0b4bcb2d42a9b647389496dd35b3~mv2.jpeg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_de07d2caef894914be6646a5c4b55260~mv2.jpeg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_e86968f9bb034a43ad01248e89a6f129~mv2.jpeg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_432f6593f17646feb4958318ddf4ed57~mv2.jpeg");
        imgArrylist.add("https://static.wixstatic.com/media/b13363_d9f0b227a891402d8fdb5bb6baeadff0~mv2.jpeg");



        //hasta aqui

        // imgArrylist.add("https://static.wixstatic.com/media/148cba_e1205c3beba34806a26df7a0fd7726c3~mv2.jpg");
        // imgArrylist.add("https://static.wixstatic.com/media/148cba_e35d7518c6cd4862b6897c3a7124f704~mv2.jpg");
        //imgArrylist.add("https://static.wixstatic.com/media/148cba_e1205c3beba34806a26df7a0fd7726c3~mv2.jpg");
        //  imgArrylist.add("https://static.wixstatic.com/media/148cba_81c5747821b0497ea76ee670128ab650~mv2.jpg");
/*

        imgArrylist.add("https://static.wixstatic.com/media/148cba_1a9eb2fd03824d49aa0a8a4d78bea152~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/148cba_95cc797b89554b098d4f77c5b3a3f86c~mv2.jpg");
        imgArrylist.add("https://static.wixstatic.com/media/148cba_f433c4149a8844688346f554b7b61b8b~mv2.jpg");

*/


        rcvMain=findViewById(R.id.rcvMain);
        rcvMain.setLayoutManager(new GridLayoutManager(this,2));
        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(SecongPage.this,imgArrylist);
        rcvMain.setAdapter(recyclerViewAdapter);


        materialDesignFAM.bringToFront();
    }
    /*
        private void LoadBannerAd() {
            adView = new MaxAdView(getResources().getString(R.string.banner), this);
            adView.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd ad) {

                }

                @Override
                public void onAdCollapsed(MaxAd ad) {

                }

                @Override
                public void onAdLoaded(MaxAd ad) {

                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {

                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                }
            });

            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = getResources().getDimensionPixelSize(R.dimen.banner_height);
            adView.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.TOP));
            adView.setBackgroundColor(Color.TRANSPARENT);


            ViewGroup rootView = findViewById( android.R.id.content );
            rootView.addView( adView );
            adView.loadAd();

        }
    */
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

    public void calificar(){
        //   Toasty.success(this,getResources().getString(R.string.valorar), Toast.LENGTH_SHORT,true).show();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SecongPage.this);
        // Setting Dialog Message
        alertDialog.setTitle("CALIFICAR QUALIFY");
        alertDialog.setMessage(" \n" +
                "Your comment is very important to me, please VALUE or QUALIFY ..." +
                "\n" +
                "Or leave a comment to improve this application " +
                "You'll only take a few minutes. Thanks for your help..."  +"\n" + "\n" +
                "Tu comentario es muy importante para mi, por favor VALORA o CALIFICA... " +
                "O deja un comentario para poder mejorar esta alicación...Solo te va a tomar unos pocos minutos. Gracias por tu ayuda!");

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

    public void info(){
        // interstitial.show();
        //  click.start();
        //   Toasty.success(this,"INFO SELEC :)", Toast.LENGTH_SHORT,true).show();

        AlertDialog.Builder a = new AlertDialog.Builder(SecongPage.this);
        a.setMessage("Muchas gracias por probar mi aplicacion somos de bajo recursos y cada compro nos ayuda mucho no saben cuanto por eso les agradesco mucho de corazon, comente que le gustaria que subieramos leemos todos sus comentarios."+
                        "\n" +
                        "\n" +
                        "T\n" +
                        "Thank you very much for testing my application, we are low-income and each purchase helps us a lot. You do not know how much for that I thank you very much from my heart, comment that you would like us to upload we read all your comments" +"\n" + "\n Version 1.0.3")
                .setCancelable(false)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a.create();
        a.setTitle("About");
        a.show();
    }

    public void home(){
        //interstitial.show();
        //Toasty.success(this, getResources().getString(R.string.valorar ), Toast.LENGTH_SHORT,true).show();
        // click.start();
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5430061106099021564")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5430061106099021564")));
        }finally {

        }
    }


    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/206722942682943")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/pg/andrespro100")); //catches and opens a url to the desired page
        }
    }


    public static Intent getOpenInstagramIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.instagram.android", 0); //Checks if Instagram is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/andrespro100")); //Trys to make intent with Instagram's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/andrespro100")); //catches and opens a url to the desired page
        }
    }
    public static Intent getOpenTwitterIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.twitter.android", 0); //Checks if Twitter is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/Andrespro_100")); //Trys to make intent with Twitter's's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/Andrespro_100")); //catches and opens a url to the desired page
        }
    }


}