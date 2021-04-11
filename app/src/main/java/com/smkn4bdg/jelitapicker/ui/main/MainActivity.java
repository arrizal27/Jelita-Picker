package com.smkn4bdg.jelitapicker.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.smkn4bdg.jelitapicker.request.RiwayatActivity;
import com.smkn4bdg.jelitapicker.Models.Pengepul;
import com.smkn4bdg.jelitapicker.ui.profile.ProfileActivity;
import com.smkn4bdg.jelitapicker.R;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mdbPicker;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth mfirebaseauth;
    private FirebaseUser mPicker;
    private static final String  PICKER = "pengepul";
    private final String TAG = this.getClass().getName().toUpperCase();
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
                        username.setText(mdatasnap.child("nama").getValue(String.class).toUpperCase());
                        pengepul.setEmail(mdatasnap.child("email").getValue(String.class));
                        kecamatan.setText(mdatasnap.child("kecamatan").getValue(String.class));
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
        kecamatan = findViewById(R.id.kecamatan);
        btnJerigen = findViewById(R.id.btn_jerigen);
        btnProfil = findViewById(R.id.btn_profil);
        btnHelp = findViewById(R.id.btn_help);
        btnRiwayat = findViewById(R.id.btn_riwayat);
        btnPoin = findViewById(R.id.btn_poin);
    }

}