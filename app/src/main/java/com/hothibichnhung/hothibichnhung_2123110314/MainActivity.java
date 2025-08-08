package com.hothibichnhung.hothibichnhung_2123110314;

import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
=======
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
>>>>>>> 08a1d20 (Data)
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

<<<<<<< HEAD
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
=======
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText nameEdt, jobEdt;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;
>>>>>>> 08a1d20 (Data)

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

<<<<<<< HEAD
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
=======
        nameEdt = findViewById(R.id.idEdtName);
        jobEdt = findViewById(R.id.idEdtJob);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);

        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEdt.getText().toString().isEmpty() || jobEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                postData(nameEdt.getText().toString(), jobEdt.getText().toString());
            }
        });
    }

    private void postData(String name, String job) {
        loadingPB.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://68959501039a1a2b288f943f.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        DataModal modal = new DataModal(name, job);

        Call<DataModal> call = retrofitAPI.createPost(modal);

        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                loadingPB.setVisibility(View.GONE);
                jobEdt.setText("");
                nameEdt.setText("");
                DataModal responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code()
                        + "\nName : " + responseFromAPI.getName()
                        + "\nJob : " + responseFromAPI.getJob();
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                loadingPB.setVisibility(View.GONE);
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
>>>>>>> 08a1d20 (Data)
    }
}
