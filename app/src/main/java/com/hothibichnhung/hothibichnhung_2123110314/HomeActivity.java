package com.hothibichnhung.hothibichnhung_2123110314;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    GridView gridProducts;
    LinearLayout layoutCategory;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gridProducts = findViewById(R.id.gridProducts);
        layoutCategory = findViewById(R.id.layoutCategory);

        loadCategories();
        loadProducts();
        View btnCart = findViewById(R.id.btnCart); // ánh xạ nút giỏ hàng

        // Sự kiện mở giỏ hàng
        btnCart.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(it);
        });
    }


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
            img.setLayoutParams(new LinearLayout.LayoutParams(80, 80));

            TextView txt = new TextView(this);
            txt.setText(categories[i]);
            txt.setGravity(Gravity.CENTER);

            item.addView(img);
            item.addView(txt);

            layoutCategory.addView(item);
        }
    }


    private void loadProducts() {
        productList = new ArrayList<>();
        productList.add(new Product("Mèo Con", "25.000.000đ", R.drawable.product1));
        productList.add(new Product("Chó Con", "15.000.000đ", R.drawable.product2));
        productList.add(new Product("Thức Ăn", "3.000.000đ", R.drawable.product4));
        productList.add(new Product("Cát Vệ Sinh", "55.000đ", R.drawable.product5));

        gridProducts.setAdapter(new ProductAdapter());
    }


    class Product {
        String name, price;
        int image;

        Product(String name, String price, int image) {
            this.name = name;
            this.price = price;
            this.image = image;
        }
    }

    // Adapter for GridView
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
        public View getView(int position, View convertView, android.view.ViewGroup parent) {
            LinearLayout layout = new LinearLayout(HomeActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(8, 8, 8, 8);
            layout.setGravity(Gravity.CENTER_HORIZONTAL);

            // Ảnh sản phẩm
            ImageView img = new ImageView(HomeActivity.this);
            img.setImageResource(productList.get(position).image);
            img.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 250));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Tên sản phẩm
            TextView txtName = new TextView(HomeActivity.this);
            txtName.setText(productList.get(position).name);
            txtName.setGravity(Gravity.CENTER);

            // Giá sản phẩm
            TextView txtPrice = new TextView(HomeActivity.this);
            txtPrice.setText(productList.get(position).price);
            txtPrice.setTextColor(0xFFFF0000);
            txtPrice.setGravity(Gravity.CENTER);

            // Icon giỏ hàng
            ImageView cartIcon = new ImageView(HomeActivity.this);
            cartIcon.setImageResource(R.drawable.ic_cart);
            LinearLayout.LayoutParams cartParams = new LinearLayout.LayoutParams(80, 80);
            cartParams.topMargin = 8;
            cartIcon.setLayoutParams(cartParams);
            cartIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


            // Thêm các view vào layout
            layout.addView(img);
            layout.addView(txtName);
            layout.addView(txtPrice);
            layout.addView(cartIcon);

            // Sự kiện click để mở chi tiết sản phẩm
            layout.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                intent.putExtra("name", productList.get(position).name);
                intent.putExtra("price", productList.get(position).price);
                intent.putExtra("image", productList.get(position).image);
                intent.putExtra("description", "Sản phẩm này rất đáng yêu và chất lượng cao.");
                startActivity(intent);
            });

            return layout;
        }
    }
}
