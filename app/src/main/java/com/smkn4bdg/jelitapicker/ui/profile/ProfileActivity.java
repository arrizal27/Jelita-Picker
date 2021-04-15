package com.smkn4bdg.jelitapicker.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelitapicker.Models.Pengepul;
import com.smkn4bdg.jelitapicker.Models.User;
import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.ui.WelcomePageActivity;

public class ProfileActivity extends AppCompatActivity {
    MaterialButton back, editProfil;
    MaterialCardView btnLogout;
    private DatabaseReference mdbPicker;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mPicker;
    private final String TAG = this.getClass().getName().toUpperCase();
    Context context;
    TextView editPw, tvnama,tvkategori,tvusername, tvemail, tvpassword,tvalamat, tvkota, tvkecamatan, tvkelurahan,tvjk;
    String id, role;
    int poin, jmlminyak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        mdbPicker = FirebaseDatabase.getInstance().getReference();
        mPicker = firebaseAuth.getCurrentUser();

        userInformation(mPicker.getUid());
        findView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

        editPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditPasswordActivity.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent welcome = new Intent(ProfileActivity.this, WelcomePageActivity.class);
                startActivity(welcome);
                finish();
            }
        });
    }
    private void userInformation(String uID) {
        final Query q = mdbPicker.child("pengepul").child(uID);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Pengepul Pinfo = dataSnapshot.getValue(Pengepul.class);
                    setData(Pinfo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setData(Pengepul info) {
        id = info.getId();
        tvnama.setText(info.getNama());
        tvusername.setText(info.getUsername());
        tvemail.setText(info.getEmail());
        tvalamat.setText(info.getAlamat());
        tvkota.setText(info.getKota());
        tvkecamatan.setText(info.getKecamatan());
        tvkelurahan.setText(info.getKelurahan());
        tvjk.setText(info.getJenis_kel());


    }

    private void findView(){
        back = findViewById(R.id.btn_back);
        editProfil = findViewById(R.id.btn_edit);
        editPw = findViewById(R.id.btn_edit_pw);
        btnLogout = findViewById(R.id.btn_logout);
        tvnama = findViewById(R.id.nama);
        tvkategori = findViewById(R.id.kategori);
        tvusername = findViewById(R.id.username);
        tvemail = findViewById(R.id.email);
        tvpassword = findViewById(R.id.pass);
        tvalamat = findViewById(R.id.alamat);
        tvkota = findViewById(R.id.tvkota);
        tvkecamatan = findViewById(R.id.tvkecamatan);
        tvjk = findViewById(R.id.jk);
        tvkelurahan = findViewById(R.id.tvkelurahan);
    }
}