package com.smkn4bdg.jelitapicker.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelitapicker.Models.Pengepul;
import com.smkn4bdg.jelitapicker.Models.User;
import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.request.RiwayatActivity;
import com.smkn4bdg.jelitapicker.ui.help.HelpActivity;
import com.smkn4bdg.jelitapicker.ui.profile.ProfileActivity;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mdbPicker;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth mfirebaseauth;
    private FirebaseUser mPicker;
    private static final String  PICKER = "pengepul";
    public static final String EXTRA_POINT = "";
    private final String TAG = this.getClass().getName().toUpperCase();
    String pointed;
    Pengepul pengepul = new Pengepul();
    //    Intent intent = getIntent();
//    String email = intent.getStringExtra("email");
    ImageView fotoProfil;
    TextView username, poin, kategori, tabunganMinyak, kapasitasMax, kecamatan;
    ProgressBar progressBarMinyak;

    MaterialButton btnNabung;
    MaterialCardView btnHelp, btnProfil, btnJerigen, btnRiwayat, btnPoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String email =  mfirebaseauth.getCurrentUser().getEmail();
        findView();
        getdata();

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(i);
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RiwayatActivity.class);
                startActivity(i);
            }
        });


        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profile);
            }
        });

    }

    private void getdata(){
        //read database
        FirebaseApp.initializeApp(this);
        mfirebaseInstance = FirebaseDatabase.getInstance();
        mPicker = FirebaseAuth.getInstance().getCurrentUser();
        mdbPicker = mfirebaseInstance.getReference();
        mdbPicker.child(PICKER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mdatasnap : snapshot.getChildren()){
                    if (mdatasnap.child("id").getValue().equals(mPicker.getUid())){
                        System.out.println(mdatasnap.child("username").getValue(String.class));
                        username.setText(mdatasnap.child("username").getValue(String.class).toUpperCase());
                        if (mdatasnap.child("foto").getValue().toString().isEmpty()){

                        }
                        else{
                            Picasso.get().load(mdatasnap.child("foto").getValue(String.class)).into(fotoProfil);
                        }
                        kecamatan.setText(mdatasnap.child("kecamatan").getValue(String.class));
//                        pointed = mdatasnap.child("poin").getValue().toString() + " Poin";
//                        tabunganMinyak.setText(String.valueOf(mdatasnap.child("jml_minyak").getValue() + " Liter"));
//
//                        if(mdatasnap.child("role").getValue().toString().equals("Rumah Tangga")){
//                            kapasitasMax.setText(Integer.valueOf(5) + " Liter");
//                            progressBarMinyak.setMax(5);
//                        }
//                        else if (mdatasnap.child("role").getValue().toString().equals("Pedagang")){
//                            kapasitasMax.setText(Integer.valueOf(10) + " Liter");
//                            progressBarMinyak.setMax(10);
//                        }
//                        else if (mdatasnap.child("role").getValue().toString().equals("Cafe dan Rumah Makan")){
//                            kapasitasMax.setText(Integer.valueOf(15) + " Liter");
//                            progressBarMinyak.setMax(15);
//                        }
//                        else if (mdatasnap.child("role").getValue().toString().equals("Hotel dan Penginapan")){
//                            kapasitasMax.setText(Integer.valueOf(20) + " Liter");
//                            progressBarMinyak.setMax(20);
//                        }
//                        else{
//
//                        }
//
//                        progressBarMinyak.setProgress(Integer.valueOf(mdatasnap.child("jml_minyak").getValue().toString()));
                        pengepul.setEmail(mdatasnap.child("email").getValue(String.class));



//                        tabunganMinyak.setText(mdatasnap.child("jml_minyak").getValue(String.class));
//                        Log.d(username.toString(), "kategori");
                        pengepul.setEmail(mdatasnap.child("username").getValue(String.class));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void findView() {
        fotoProfil = findViewById(R.id.img_profil);
        username = findViewById(R.id.username);
//        tabunganMinyak = findViewById(R.id.txt_tabungan);
//        kapasitasMax = findViewById(R.id.txt_maks);
//        progressBarMinyak = findViewById(R.id.progres_nabung);
        btnProfil = findViewById(R.id.btn_profil);
        btnHelp = findViewById(R.id.btn_help);
        kecamatan = findViewById(R.id.kecamatan);
        btnRiwayat = findViewById(R.id.btn_riwayat);

    }

}