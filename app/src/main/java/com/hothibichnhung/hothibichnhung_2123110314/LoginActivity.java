package com.hothibichnhung.hothibichnhung_2123110314;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//Đăng nhập
        Button btnNext = findViewById(R.id.btnLogin);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText objEmail = findViewById(R.id.etEmail);
                String txtEmail = objEmail.getText().toString();

                EditText objPass = findViewById(R.id.etPassword);
                String txtPass = objPass.getText().toString();

                if (txtEmail.equals("BichNhung") && txtPass.equals("263"))
                {
                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                    it.putExtra("email", txtEmail);
                    it.putExtra("pass", txtPass);
                    startActivity(it);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
//Đăng Ký
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getApplicationContext(), RegisterActivity.class);

                startActivity(it);

            }
        });
    }
}
