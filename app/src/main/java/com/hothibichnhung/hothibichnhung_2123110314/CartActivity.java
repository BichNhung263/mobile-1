package com.hothibichnhung.hothibichnhung_2123110314;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;
public class CartActivity extends AppCompatActivity {
    ListView listView;
    TextView txtTotal;
    Button btnCheckout;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listView = findViewById(R.id.listViewCart);
        txtTotal = findViewById(R.id.txtTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Gắn adapter với listener
        adapter = new CartAdapter(this, MyData.cartList, this::updateTotal);
        listView.setAdapter(adapter);

        // Tính tổng ban đầu
        updateTotal();

        // Xử lý khi bấm nút Thanh toán
        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });
    }

    // ✅ Hàm cập nhật tổng tiền
    private void updateTotal() {
        int total = 0;
        for (Product p : MyData.cartList) {
            total += p.getPrice();
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        txtTotal.setText("Tổng tiền: " + nf.format(total));
    }
}
