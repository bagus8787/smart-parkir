package com.example.loginfirebase.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginfirebase.HomeActivity;
import com.example.loginfirebase.Login;
import com.example.loginfirebase.MenuIOT;
import com.example.loginfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAdmin extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private Button btnLogin, btnRegister, btnBack;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        initView();
        login();

    }

    private void login() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginAdmin.this, LoginAdmin.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(LoginAdmin.this, HomeActivity.class);
                startActivity(b);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menampung imputan user
                final String emailUser = edtEmail.getText().toString().trim();
                final String passwordUser = edtPassword.getText().toString().trim();

                //validasi email dan password
                // jika email kosong
                if (emailUser.isEmpty()) {
                    edtEmail.setError("Email tidak boleh kosong");
                }
                // jika email not valid
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()) {
                    edtEmail.setError("Email tidak valid");
                }
                // jika password kosong
                else if (passwordUser.isEmpty()) {
                    edtPassword.setError("Password tidak boleh kosong");
                }
                //jika password kurang dari 6 karakter
                else if (passwordUser.length() < 6) {
                    edtPassword.setError("Password minimal terdiri dari 6 karakter");
                } else auth.signInWithEmailAndPassword(emailUser, passwordUser)
                        .addOnCompleteListener(LoginAdmin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // ketika gagal locin maka akan do something
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginAdmin.this,
                                            "Gagal login karena " + task.getException().getMessage()
                                            , Toast.LENGTH_LONG).show();
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", emailUser);
                                    bundle.putString("pass", passwordUser);
                                    startActivity(new Intent(LoginAdmin.this, ListParkirAdminActivity.class)
                                            .putExtra("emailpass", bundle));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    private void initView() {
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnBack = findViewById(R.id.btn_back);
        btnRegister = findViewById(R.id.btn_register);
        auth = FirebaseAuth.getInstance();
    }


}
