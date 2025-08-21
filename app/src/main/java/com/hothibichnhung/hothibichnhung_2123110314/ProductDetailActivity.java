package com.hothibichnhung.hothibichnhung_2123110314;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName, txtPrice, txtDescription;
    Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imgProduct = findViewById(R.id.imgProductDetail);
        txtName = findViewById(R.id.txtProductNameDetail);
        txtPrice = findViewById(R.id.txtProductPriceDetail);
        txtDescription = findViewById(R.id.txtProductDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // Nhận dữ liệu từ HomeActivity
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0);
        int image = getIntent().getIntExtra("image", 0);
        String description = getIntent().getStringExtra("description");

        // Format giá tiền VNĐ
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Hiển thị
        txtName.setText(name);
        txtPrice.setText(nf.format(price)); // ✅ hiện giá tiền đúng định dạng
        txtDescription.setText(description);
        imgProduct.setImageResource(image);

        // Nút thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(v -> {
            Product p = new Product(name, price, image);
            MyData.cartList.add(p);
            finish(); // Quay lại màn hình trước
        });
    }
}



