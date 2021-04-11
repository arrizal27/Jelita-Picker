package com.smkn4bdg.jelitapicker.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bdg.jelitapicker.Models.User;
import com.smkn4bdg.jelitapicker.ui.WelcomePageActivity;
import com.smkn4bdg.jelitapicker.Models.Pengepul;
import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.ui.login.DaftarBerhasilActivity;

public class DaftarActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbPicker;
    private static final String TAG = "DaftarActivity";
    EditText nama,username,pass,nohp, email;
    Button btndaftar;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        findView();

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
        nohp = findViewById(R.id.txt_telp);
        email = findViewById(R.id.txt_email);
        btndaftar = findViewById(R.id.btn_daftar);
        back = findViewById(R.id.back_daftar);

        dbPicker = FirebaseDatabase.getInstance().getReference("pengepul");
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void daftar(){
        //get data from Form daftar
        String namaFinal = nama.getText().toString();
        String usernameFinal = username.getText().toString();
        String passFinal = pass.getText().toString();
        String nohpFinal = nohp.getText().toString();
        String emailFinal = email.getText().toString();

        //data default
        String jenisKelamin = "tidak disebutkan";
        String noTlp = "08**********";
        String alamat = "Jl. jalan";
        String kelurahan = "Bojong";
        String kecamatan = "Kaler";
        String kota = "Bandung";

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
        if (TextUtils.isEmpty(emailFinal)) {
            showToast("Enter email address!");
            return;
        }


        dbPicker.orderByChild("email").equalTo(emailFinal).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    showToast("Email Already Exist!");
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(emailFinal, passFinal)
                            .addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "New user registration: " + task.isSuccessful());
                                    if (!task.isSuccessful()) {
                                        DaftarActivity.this.showToast("Authentication failed. " + task.getException());
                                    } else {
                                        String id = firebaseAuth.getUid();
                                        Pengepul pengepul = new Pengepul(id, namaFinal, "",jenisKelamin, alamat, kota,
                                                kelurahan, kecamatan, usernameFinal, passFinal, emailFinal, nohpFinal);

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

    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

}