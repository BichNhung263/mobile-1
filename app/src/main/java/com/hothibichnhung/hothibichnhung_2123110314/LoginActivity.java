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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    String API_URL = "https://68959501039a1a2b288f943f.mockapi.io/user"; // URL API MockAPI

    EditText objEmail, objPass;
    Button btnLogin, btnRegister;
    OkHttpClient client = new OkHttpClient();

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

        objEmail = findViewById(R.id.etEmail);
        objPass = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Xử lý đăng nhập
        btnLogin.setOnClickListener(v -> {
            String txtEmail = objEmail.getText().toString().trim();
            String txtPass = objPass.getText().toString().trim();

            if (txtEmail.isEmpty() || txtPass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi API kiểm tra tài khoản
            Request request = new Request.Builder().url(API_URL).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() ->
                            Toast.makeText(LoginActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        runOnUiThread(() ->
                                Toast.makeText(LoginActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show()
                        );
                        return;
                    }

                    String data = response.body().string();
                    try {
                        JSONArray arr = new JSONArray(data);
                        boolean found = false;
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject user = arr.getJSONObject(i);
                            String email = user.getString("email");
                            String pass = user.getString("password");

                            if (email.equals(txtEmail) && pass.equals(txtPass)) {
                                found = true;
                                break;
                            }
                        }

                        boolean finalFound = found;
                        runOnUiThread(() -> {
                            if (finalFound) {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent it = new Intent(getApplicationContext(), HomeActivity.class);
                                it.putExtra("email", txtEmail);
                                startActivity(it);
                            } else {
                                Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        runOnUiThread(() ->
                                Toast.makeText(LoginActivity.this, "Lỗi xử lý dữ liệu", Toast.LENGTH_SHORT).show()
                        );
                    }
                }
            });
        });

        // Chuyển sang màn hình đăng ký
        btnRegister.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(it);
        });
    }
}
