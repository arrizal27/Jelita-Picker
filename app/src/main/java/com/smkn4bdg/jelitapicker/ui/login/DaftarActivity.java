package com.smkn4bdg.jelitapicker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelitapicker.Models.Pengepul;
import com.smkn4bdg.jelitapicker.Models.User;
import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.ui.WelcomePageActivity;

public class DaftarActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbPicker;
    private static final String TAG = "DaftarActivity";
    TextInputEditText nama,username,pass,emailhp, alamat, kota, kecamatan, kelurahan, no_telp;
    Spinner role,jk;
    Button btndaftar;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        findView();
        fillSpinner();

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(DaftarActivity.this, WelcomePageActivity.class);
                startActivity(back);
                finish();
            }
        });

    }

    private void findView(){
        nama = findViewById(R.id.txt_nama);
        username = findViewById(R.id.txt_username);
        pass = findViewById(R.id.txt_password);
        emailhp = findViewById(R.id.email);
        jk = findViewById(R.id.dropdown_jk);
        btndaftar = findViewById(R.id.btn_daftar);
        back = findViewById(R.id.back_daftar);
        alamat = findViewById(R.id.alamat);
        kota = findViewById(R.id.kota);
        no_telp = findViewById(R.id.txt_notelp);
        kecamatan = findViewById(R.id.kecamatan);
        kelurahan = findViewById(R.id.kelurahan);
        dbPicker = FirebaseDatabase.getInstance().getReference("pengepul");
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void daftar(){
        //get data from Form daftar
        String namaFinal = nama.getText().toString();
        String usernameFinal = username.getText().toString();
        String passFinal = pass.getText().toString();
        String emailhpFinal = emailhp.getText().toString();
        String jkFinal = jk.getSelectedItem().toString();
        String telpFinal = no_telp.getText().toString();
        String alamatFinal = alamat.getText().toString();
        String kotaFinal = kota.getText().toString();
        String kecamatanFinal = kecamatan.getText().toString();
        String kelurahanFinal = kelurahan.getText().toString();


        if (TextUtils.isEmpty(namaFinal)) {
            showToast("Enter Your Name!");
            return;
        }
        if (TextUtils.isEmpty(usernameFinal)) {
            showToast("Enter Username!");
            return;
        }
        if (TextUtils.isEmpty(passFinal)) {
            showToast("Enter Your Password!");
            return;
        }
        if (TextUtils.isEmpty(telpFinal)) {
            showToast("Enter Your Phone Number!");
            return;
        }
        if (TextUtils.isEmpty(emailhpFinal)) {
            showToast("Enter email address!");
            return;
        }
        if (TextUtils.isEmpty(alamatFinal)) {
            showToast("Enter Your Adress!");
            return;
        }
        if (TextUtils.isEmpty(kotaFinal)) {
            showToast("Enter Your City!");
            return;
        }
        if (TextUtils.isEmpty(kecamatanFinal)) {
            showToast("Enter Kecamatan!");
            return;
        }
        if (TextUtils.isEmpty(kelurahanFinal)) {
            showToast("Enter Kelurahan!");
            return;
        }


        dbPicker.orderByChild("email").equalTo(emailhpFinal).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    showToast("Email Already Exist!");
                }
                else{
                    dbPicker.orderByChild("kecamatan").equalTo(kecamatanFinal).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                showToast("Pengepul di kecamatan ini sudah ada!");
                            }
                            else{
                                firebaseAuth.createUserWithEmailAndPassword(emailhpFinal, passFinal)
                                        .addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                Log.d(TAG, "New user registration: " + task.isSuccessful());
                                                if (!task.isSuccessful()) {
                                                    DaftarActivity.this.showToast("Authentication failed. " + task.getException());
                                                } else {
                                                    String id = firebaseAuth.getUid();
                                                    Pengepul pengepul = new Pengepul(id, namaFinal,"", jkFinal, alamatFinal, kotaFinal,kelurahanFinal,
                                                            kecamatanFinal, usernameFinal, passFinal, emailhpFinal, telpFinal);
                                                    dbPicker.child(id).setValue(pengepul);
                                                    DaftarActivity.this.startActivity(new Intent(DaftarActivity.this, DaftarBerhasilActivity.class));
                                                    DaftarActivity.this.finish();

                                                }
                                            }
                                        });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fillSpinner(){
        String[] jenis_kelamin = {"Laki - Laki","Perempuan"};

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(DaftarActivity.this, android.R.layout.simple_spinner_item, jenis_kelamin);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jk.setAdapter(genderAdapter);

    }

    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

}