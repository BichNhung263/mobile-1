package com.hothibichnhung.hothibichnhung_2123110314;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    LinearLayout layoutCart;
    TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        layoutCart = findViewById(R.id.layoutCart);
        txtTotal = findViewById(R.id.txtTotal);

        // Ví dụ tạm: hiện giỏ hàng có 2 sản phẩm test
        addCartItem("Mèo Con", "25.000.000đ");
        addCartItem("Cát Vệ Sinh", "55.000đ");

        updateTotal();
    }

    // Hàm thêm item vào layout
    private void addCartItem(String name, String price) {
        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setPadding(16, 16, 16, 16);

        TextView txtName = new TextView(this);
        txtName.setText(name);
        txtName.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        TextView txtPrice = new TextView(this);
        txtPrice.setText(price);

        item.addView(txtName);
        item.addView(txtPrice);

        layoutCart.addView(item);
    }

    // Hàm cập nhật tổng tiền (ở đây chỉ là ví dụ đơn giản)
    private void updateTotal() {
        txtTotal.setText("Tổng tiền: 25.055.000đ");
    }
}
