package com.example.shoprime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartChangeListener {

    private RecyclerView rvCart;
    private TextView tvTotal;
    private Button btnCheckout;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        tvTotal = findViewById(R.id.tvCartTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        adapter = new CartAdapter(CartManager.getInstance().getCartItems(), this);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(adapter);

        updateTotal();

        btnCheckout.setOnClickListener(v -> {
            if (CartManager.getInstance().getCartItems().isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, PaymentActivity.class));
            }
        });
    }

    private void updateTotal() {
        tvTotal.setText(String.format("$%.2f", CartManager.getInstance().getTotal()));
    }

    @Override
    public void onQuantityChanged() {
        updateTotal();
    }
}