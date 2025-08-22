package com.hothibichnhung.hothibichnhung_2123110314;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<Product> cartList;
    private CartUpdateListener listener;

    // Interface để báo cho Activity biết khi giỏ hàng thay đổi
    public interface CartUpdateListener {
        void onCartUpdated();
    }

    public CartAdapter(Context context, List<Product> cartList, CartUpdateListener listener) {
        this.context = context;
        this.cartList = cartList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.imgProduct);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        ImageView btnRemove = convertView.findViewById(R.id.btnRemove);

        Product p = cartList.get(position);
        img.setImageResource(p.getImage());
        txtName.setText(p.getName());

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        txtPrice.setText(nf.format(p.getPrice()));

        //  Nút xóa sản phẩm
        btnRemove.setOnClickListener(v -> {
            cartList.remove(position);
            notifyDataSetChanged();
            if (listener != null) {
                listener.onCartUpdated(); //  báo về CartActivity cập nhật lại tổng tiền
            }
        });

        return convertView;
    }
}
