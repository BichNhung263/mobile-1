package com.hothibichnhung.hothibichnhung_2123110314;

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

        // Hiển thị sản phẩm trong giỏ
        CartAdapter adapter = new CartAdapter(this, MyData.cartList);
        listCheckoutProducts.setAdapter(adapter);

        // Tính tổng tiền
        int total = 0;
        for (Product p : MyData.cartList) {
            total += p.getPrice();
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        txtTotalCheckout.setText("Tổng tiền: " + nf.format(total));

        // Xử lý xác nhận thanh toán
        btnConfirm.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đặt hàng thành công! Cảm ơn " + name, Toast.LENGTH_LONG).show();
                MyData.cartList.clear(); // Xóa giỏ hàng sau khi đặt
                finish(); // quay lại CartActivity
            }
        });
    }
}
