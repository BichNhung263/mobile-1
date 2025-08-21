package com.hothibichnhung.hothibichnhung_2123110314;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    ImageView icCart;
    GridView gridProducts;
    LinearLayout layoutCategory;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gridProducts = findViewById(R.id.gridProducts);
        layoutCategory = findViewById(R.id.layoutCategory);
        icCart = findViewById(R.id.btnCart);

        loadCategories();
        loadProducts();

        // mở trang giỏ hàng
        icCart.setOnClickListener(v -> {
            Intent cartIntent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(cartIntent);
        });
    }

    // Load danh mục
    private void loadCategories() {
        String[] categories = {"Mèo Con", "Chó Con", "Thức Ăn", "Cát Vệ Sinh"};
        int[] icons = {R.drawable.product1, R.drawable.product2, R.drawable.product4, R.drawable.product5};

        for (int i = 0; i < categories.length; i++) {
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);
            item.setPadding(16, 8, 16, 8);

            ImageView img = new ImageView(this);
            img.setImageResource(icons[i]);
            img.setLayoutParams(new LinearLayout.LayoutParams(120, 120));

            TextView txt = new TextView(this);
            txt.setText(categories[i]);
            txt.setGravity(Gravity.CENTER);

            item.addView(img);
            item.addView(txt);

            layoutCategory.addView(item);
        }
    }

    // Load sản phẩm
    private void loadProducts() {
        productList = new ArrayList<>();
        productList.add(new Product("Mèo Con", 25000000, R.drawable.product1));
        productList.add(new Product("Chó Con", 15000000, R.drawable.product2));
        productList.add(new Product("Thức Ăn", 3000000, R.drawable.product4));
        productList.add(new Product("Cát Vệ Sinh", 55000, R.drawable.product5));

        gridProducts.setAdapter(new ProductAdapter());
    }

    // Adapter cho GridView
    class ProductAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Object getItem(int position) {
            return productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout layout = new LinearLayout(HomeActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(8, 8, 8, 8);
            layout.setGravity(Gravity.CENTER_HORIZONTAL);

            Product product = productList.get(position);

            // Ảnh sản phẩm
            ImageView img = new ImageView(HomeActivity.this);
            img.setImageResource(product.getImage());
            img.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 250));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Tên sản phẩm
            TextView txtName = new TextView(HomeActivity.this);
            txtName.setText(product.getName());
            txtName.setGravity(Gravity.CENTER);

            // Giá sản phẩm (format tiền VNĐ)
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            TextView txtPrice = new TextView(HomeActivity.this);
            txtPrice.setText(nf.format(product.getPrice()));
            txtPrice.setTextColor(0xFFFF0000);
            txtPrice.setGravity(Gravity.CENTER);

            // Icon giỏ hàng
            ImageView cartIcon = new ImageView(HomeActivity.this);
            cartIcon.setImageResource(R.drawable.ic_cart);
            LinearLayout.LayoutParams cartParams = new LinearLayout.LayoutParams(80, 80);
            cartParams.topMargin = 8;
            cartIcon.setLayoutParams(cartParams);
            cartIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            // Thêm view
            layout.addView(img);
            layout.addView(txtName);
            layout.addView(txtPrice);
            layout.addView(cartIcon);

            // Click sản phẩm -> sang chi tiết
            layout.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("image", product.getImage());
                intent.putExtra("description", "Sản phẩm này rất đáng yêu và chất lượng cao.");
                startActivity(intent);
            });

            // Click icon giỏ hàng -> thêm vào giỏ
            cartIcon.setOnClickListener(v -> {
                MyData.cartList.add(product);
            });

            return layout;
        }
    }
}
