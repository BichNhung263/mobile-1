package com.hothibichnhung.hothibichnhung_2123110314;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {
    EditText edtName, edtPhone, edtAddress;
    TextView txtTotalCheckout;
    Button btnConfirm;
    ListView listCheckoutProducts;

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

        // ✅ Adapter hiển thị sản phẩm (chỉ xem, không xóa)
        CartAdapter adapter = new CartAdapter(this, MyData.cartList, null);
        listCheckoutProducts.setAdapter(adapter);

        // ✅ Cập nhật tổng tiền
        updateTotal();

        // ✅ Xử lý xác nhận thanh toán
        btnConfirm.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đặt hàng thành công! Cảm ơn " + name, Toast.LENGTH_LONG).show();

                // ✅ Xóa giỏ hàng
                MyData.cartList.clear();

                // ✅ Quay về trang Home
                Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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
}
