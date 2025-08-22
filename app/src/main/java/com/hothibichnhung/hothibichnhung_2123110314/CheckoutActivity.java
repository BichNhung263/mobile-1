package com.hothibichnhung.hothibichnhung_2123110314;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckoutActivity extends AppCompatActivity {
    EditText edtName, edtPhone, edtAddress;
    TextView txtTotalCheckout;
    Button btnConfirm;
    ListView listCheckoutProducts;

    // URL MockAPI (bạn thay link của mình vào đây)
    private static final String MOCK_API_URL = "https://68959501039a1a2b288f943f.mockapi.io/Cart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        txtTotalCheckout = findViewById(R.id.txtTotalCheckout);
        btnConfirm = findViewById(R.id.btnConfirm);
        listCheckoutProducts = findViewById(R.id.listCheckoutProducts);

        // Adapter hiển thị sản phẩm
        CartAdapter adapter = new CartAdapter(this, MyData.cartList, null);
        listCheckoutProducts.setAdapter(adapter);

        // Tính tổng tiền
        updateTotal();

        // Xác nhận đặt hàng
        btnConfirm.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                sendOrderToMockAPI(name, phone, address);
            }
        });
    }

    private void updateTotal() {
        int total = 0;
        for (Product p : MyData.cartList) {
            total += p.getPrice();
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        txtTotalCheckout.setText("Tổng tiền: " + nf.format(total));
    }

    // Gửi dữ liệu lên MockAPI
    private void sendOrderToMockAPI(String name, String phone, String address) {
        OkHttpClient client = new OkHttpClient();

        try {
            // Chuyển giỏ hàng sang JSON
            JSONArray productsArray = new JSONArray();
            for (Product p : MyData.cartList) {
                JSONObject obj = new JSONObject();
                obj.put("name", p.getName());
                obj.put("price", p.getPrice());
                obj.put("image", p.getImage());
                productsArray.put(obj);
            }

            // JSON đơn hàng
            JSONObject order = new JSONObject();
            order.put("customerName", name);
            order.put("phone", phone);
            order.put("address", address);
            order.put("products", productsArray);

            // Gửi POST request
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(order.toString(), JSON);
            Request request = new Request.Builder()
                    .url(MOCK_API_URL)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() ->
                            Toast.makeText(CheckoutActivity.this, "Lỗi kết nối API!", Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        runOnUiThread(() -> {
                            Toast.makeText(CheckoutActivity.this,
                                    "Đặt hàng thành công! ",
                                    Toast.LENGTH_LONG).show();

                            // Xóa giỏ hàng
                            MyData.cartList.clear();

                            // Quay về Home
                            Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        });
                    } else {
                        runOnUiThread(() ->
                                Toast.makeText(CheckoutActivity.this, "API trả về lỗi!", Toast.LENGTH_SHORT).show()
                        );
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
