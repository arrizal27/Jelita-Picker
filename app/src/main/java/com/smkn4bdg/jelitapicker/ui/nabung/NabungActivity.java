    package com.smkn4bdg.jelitapicker.ui.nabung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.smkn4bdg.jelitapicker.ui.setor.SetorActivity;
import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.ui.main.MainActivity;

    public class NabungActivity extends AppCompatActivity {
    private DatabaseReference mdbUsers;
    private FirebaseDatabase reference;
    private FirebaseAuth mfirebaseauth;
    private FirebaseUser mUser;
    private final String TAG = this.getClass().getName().toUpperCase();
    MaterialButton btnBack, btnTabung, btnSetor;

    TextView txtTabungan, txtMaksTabung;
    TextView tabunganMinyak, kapasitasMax;

    TextInputEditText txtJumlahMinyak;
    ProgressBar progressBarMinyak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nabung);
        findView();
        initdata();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnTabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //method tambah tabungan minyak
                nabungSuccess();
            }
        });

        btnSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NabungActivity.this, SetorActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void initdata(){
        FirebaseApp.initializeApp(this);
        reference = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mdbUsers = reference.getReference();
        mdbUsers.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.child("id").getValue().equals(mUser.getUid())){
                        tabunganMinyak.setText(String.valueOf(dataSnapshot.child("jml_minyak").getValue() + " Liter"));
                        progressBarMinyak.setProgress(Integer.valueOf(dataSnapshot.child("jml_minyak").getValue().toString()));

                        if(dataSnapshot.child("role").getValue().toString().equals("Rumah Tangga")){
                            kapasitasMax.setText(Integer.valueOf(5) + " Liter");
                            progressBarMinyak.setMax(5);
                        }
                        if (dataSnapshot.child("role").getValue().toString().equals("Pedagang")){
                            kapasitasMax.setText(Integer.valueOf(10) + " Liter");
                            progressBarMinyak.setMax(10);
                        }
                        if (dataSnapshot.child("role").getValue().toString().equals("Cafe dan Rumah Makan")){
                            kapasitasMax.setText(Integer.valueOf(15) + " Liter");
                            progressBarMinyak.setMax(15);
                        }
                        if (dataSnapshot.child("role").getValue().toString().equals("Hotel dan Penginapan")){
                            kapasitasMax.setText(Integer.valueOf(20) + " Liter");
                            progressBarMinyak.setMax(20);
                        }
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

    private void findView(){
        btnBack = findViewById(R.id.btn_back);
        btnTabung = findViewById(R.id.btn_tambah_minyak);
        btnSetor = findViewById(R.id.btn_setor);
        txtJumlahMinyak = findViewById(R.id.txt_tambah_minyak);
        progressBarMinyak = findViewById(R.id.progres_nabung);
        tabunganMinyak = findViewById(R.id.txt_tabungan);
        kapasitasMax = findViewById(R.id.txt_maks);
    }

    private void nabungSuccess() {

        FirebaseApp.initializeApp(this);
        reference = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mdbUsers = reference.getReference();

        mdbUsers.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("id").getValue().equals(mUser.getUid())) {
                        if (dataSnapshot.child("role").getValue().equals("Rumah Tangga")){
                            if (Integer.parseInt(dataSnapshot.child("jml_minyak").getValue().toString()) == 5){
                                btnTabung.setEnabled(false);
                                Toast toast = Toast.makeText(getApplicationContext(), "Tabungan anda sudah penuh silahkan setor terlebih dahulu!!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {
                                int tabung = Integer.valueOf(txtJumlahMinyak.getText().toString());
                                int minyakAwal = Integer.valueOf(dataSnapshot.child("jml_minyak").getValue().toString());
                                int hasilNabung = minyakAwal + tabung;
                                System.out.println(hasilNabung);
                                Toast toast = Toast.makeText(getApplicationContext(), "Tabungan Anda Bertambah " + tabung + " Liter" , Toast.LENGTH_SHORT);
                                toast.show();
                                mdbUsers.child("users").child(mUser.getUid()).child("jml_minyak").setValue(hasilNabung);
                                Intent intent = new Intent(NabungActivity.this, MainActivity.class);
                                startActivity(intent);

                                break;
                            }
                        }
                        if (dataSnapshot.child("role").getValue().equals("Pedagang")){
                            if (Integer.parseInt(dataSnapshot.child("jml_minyak").getValue().toString()) == 10){
                                btnTabung.setEnabled(false);
                                Toast toast = Toast.makeText(getApplicationContext(), "Tabungan anda sudah penuh silahkan setor terlebih dahulu!!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {
                                int tabung = Integer.valueOf(txtJumlahMinyak.getText().toString());
                                int minyakAwal = Integer.valueOf(dataSnapshot.child("jml_minyak").getValue().toString());
                                int hasilNabung = minyakAwal + tabung;
                                System.out.println(hasilNabung);
                                Toast toast = Toast.makeText(getApplicationContext(), "Tabungan Anda Bertambah " + tabung + " Liter" , Toast.LENGTH_SHORT);
                                toast.show();
                                mdbUsers.child("users").child(mUser.getUid()).child("jml_minyak").setValue(hasilNabung);
                                Intent intent = new Intent(NabungActivity.this, MainActivity.class);
                                startActivity(intent);

                                break;
                            }
                        }
                        if (dataSnapshot.child("role").getValue().equals("Cafe dan Rumah Makan")){
                            if (Integer.parseInt(dataSnapshot.child("jml_minyak").getValue().toString()) == 15){
                                btnTabung.setEnabled(false);
                                Toast toast = Toast.makeText(getApplicationContext(), "Tabungan anda sudah penuh silahkan setor terlebih dahulu!!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {
                                int tabung = Integer.valueOf(txtJumlahMinyak.getText().toString());
                                int minyakAwal = Integer.valueOf(dataSnapshot.child("jml_minyak").getValue().toString());
                                int hasilNabung = minyakAwal + tabung;
                                System.out.println(hasilNabung);
                                Toast toast = Toast.makeText(getApplicationContext(), "Tabungan Anda Bertambah " + tabung + " Liter" , Toast.LENGTH_SHORT);
                                toast.show();
                                mdbUsers.child("users").child(mUser.getUid()).child("jml_minyak").setValue(hasilNabung);
                                Intent intent = new Intent(NabungActivity.this, MainActivity.class);
                                startActivity(intent);

                                break;
                            }
                        }
                        if (dataSnapshot.child("role").getValue().equals("Hotel dan Penginapan")){
                            if (Integer.parseInt(dataSnapshot.child("jml_minyak").getValue().toString()) == 20){
                                btnTabung.setEnabled(false);
                                Toast toast = Toast.makeText(getApplicationContext(), "Tabungan anda sudah penuh silahkan setor terlebih dahulu!!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {
                                int tabung = Integer.valueOf(txtJumlahMinyak.getText().toString());
                                int minyakAwal = Integer.valueOf(dataSnapshot.child("jml_minyak").getValue().toString());
                                int hasilNabung = minyakAwal + tabung;
                                System.out.println(hasilNabung);
                                Toast toast = Toast.makeText(getApplicationContext(), "Tabungan Anda Bertambah " + tabung + " Liter", Toast.LENGTH_SHORT);
                                toast.show();
                                mdbUsers.child("users").child(mUser.getUid()).child("jml_minyak").setValue(hasilNabung);
                                Intent intent = new Intent(NabungActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}