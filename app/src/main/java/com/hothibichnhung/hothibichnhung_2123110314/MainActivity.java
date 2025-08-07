package com.hothibichnhung.hothibichnhung_2123110314;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView txtResult;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://jsonplaceholder.typicode.com/users/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtResult = findViewById(R.id.txtResult);

        // Gọi hàm getData
        getData();
    }

    private void getData() {
        // Khởi tạo RequestQueue
        mRequestQueue = Volley.newRequestQueue(this);

        // Tạo StringRequest
        mStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Hiển thị kết quả
                        txtResult.setText(response);
                        Toast.makeText(getApplicationContext(), "Response: " + response, Toast.LENGTH_LONG).show();
                        Log.i(TAG, "Response: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtResult.setText("Error: " + error.toString());
                        Log.e(TAG, "Error: " + error.toString());
                    }
                });

        // Thêm request vào hàng đợi
        mRequestQueue.add(mStringRequest);
    }
}
