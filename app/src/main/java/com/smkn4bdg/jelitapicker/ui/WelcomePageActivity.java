package com.smkn4bdg.jelitapicker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.smkn4bdg.jelitapicker.R;
import com.smkn4bdg.jelitapicker.ui.login.DaftarActivity;
import com.smkn4bdg.jelitapicker.ui.login.LoginActivity;

public class WelcomePageActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button btnLogin;
//    Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        findView();
        if (firebaseAuth.getCurrentUser() != null) {
            // User is logged in
            System.out.println("Email : " +firebaseAuth.getCurrentUser().getEmail());
            btnLogin.setText("Masuk");
//            btnDaftar.setEnabled(false);
//            btnDaftar.setVisibility(View.INVISIBLE);
        }

//        btnDaftar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent regis = new Intent(WelcomePageActivity.this, DaftarActivity.class);
//                startActivity(regis);
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(WelcomePageActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }

    private void findView(){
        btnLogin = findViewById(R.id.btnlogin);
//        btnDaftar = findViewById(R.id.btndaftar);
        firebaseAuth = FirebaseAuth.getInstance();
    }

}