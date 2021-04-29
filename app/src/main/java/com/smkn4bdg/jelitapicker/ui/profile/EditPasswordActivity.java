package com.smkn4bdg.jelitapicker.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.smkn4bdg.jelitapicker.R;

public class EditPasswordActivity extends AppCompatActivity {

    private DatabaseReference mdbPicker;
    private FirebaseDatabase mfirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mPicker;
    TextInputEditText pwlama, pwbaru, confirmpw;

    MaterialButton simpanPw;
    MaterialButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        firebaseAuth = FirebaseAuth.getInstance();
        mdbPicker = FirebaseDatabase.getInstance().getReference();
        mPicker = firebaseAuth.getCurrentUser();

        findView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        simpanPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passbaru = pwbaru.getText().toString();
                String passlama = pwlama.getText().toString();
                String confirmpass = confirmpw.getText().toString();
                if (passbaru.equals(confirmpass)){
                    String currentEmail = mPicker.getEmail();
                    AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, passlama);
                    mPicker.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mPicker.updatePassword(passbaru)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(EditPasswordActivity.this, "Password was changed successfully", Toast.LENGTH_LONG).show();
                                                            Intent i = new Intent(EditPasswordActivity.this, EditSuccessActivity.class);
                                                            startActivity(i);
                                                            finish();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(EditPasswordActivity.this, "Authentication failed, wrong password?", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(EditPasswordActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void findView(){
        pwbaru = findViewById(R.id.pw_baru);
        pwlama = findViewById(R.id.pw_lama);
        confirmpw = findViewById(R.id.confirm_pw);
        simpanPw = findViewById(R.id.simpan_pw);
        back = findViewById(R.id.btnkembali);
    }

}