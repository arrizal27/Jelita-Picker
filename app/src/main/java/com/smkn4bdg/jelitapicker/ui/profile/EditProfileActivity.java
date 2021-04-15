package com.smkn4bdg.jelitapicker.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
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

public class EditProfileActivity extends AppCompatActivity {
    MaterialButton simpan;
    MaterialButton back;
    private DatabaseReference mdbPicker;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mPicker;
    TextInputEditText nama, username, email, notelp, alamat, kota, kelurahan, kecamatan;
    String id, jeniskel, role;
    int poin, jmlminyak;
    private final String TAG = this.getClass().getName().toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        mdbPicker = FirebaseDatabase.getInstance().getReference();
        mPicker = firebaseAuth.getCurrentUser();

        userInformation(mPicker.getUid());
        findView();
        email.setEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pengepul pengepul = new Pengepul();
                pengepul.setNama(nama.getText().toString());
                pengepul.setUsername(username.getText().toString());
                pengepul.setNo_telp(notelp.getText().toString());
                pengepul.setAlamat(alamat.getText().toString());
                pengepul.setKota(kota.getText().toString());
                pengepul.setEmail(email.getText().toString());
                pengepul.setKecamatan(kecamatan.getText().toString());
                pengepul.setKelurahan(kelurahan.getText().toString());
                pengepul.setId(id);
                pengepul.setJenis_kel(jeniskel);
                mdbPicker.child("pengepul").child(mPicker.getUid()).setValue(pengepul).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(EditProfileActivity.this, EditSuccessActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
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
        notelp.setText(info.getNo_telp());
        nama.setText(info.getNama());
        username.setText(info.getUsername());
        email.setText(info.getEmail());
        alamat.setText(info.getAlamat());
        kota.setText(info.getKota());
        kecamatan.setText(info.getKecamatan());
        kelurahan.setText(info.getKelurahan());
        jeniskel = info.getJenis_kel();

    }

    private void findView(){
        simpan = findViewById(R.id.btnsimpan);
        back = findViewById(R.id.btnkembali);
        nama = findViewById(R.id.nama);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        notelp = findViewById(R.id.notelp);
        alamat = findViewById(R.id.alamat);
        kota = findViewById(R.id.kota);
        kecamatan = findViewById(R.id.kecamatan);
        kelurahan = findViewById(R.id.kelurahan);

    }
}