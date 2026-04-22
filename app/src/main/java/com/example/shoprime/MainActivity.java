package com.example.shoprime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private static final int REQ_ADD_PRODUCT = 101;
    private static final int REQ_EDIT_PRODUCT = 102;

    private ProductAdapter adapter;
    private List<Product> productList;
    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userRole = getIntent().getStringExtra("ROLE");
        if (userRole == null) userRole = "BUYER";

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("SHOPRIME");
        }

        RecyclerView rvProducts = findViewById(R.id.rvProducts);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Limpiar menú antes de inflar para evitar duplicados o errores visuales
        bottomNav.getMenu().clear();
        
        if ("SELLER".equals(userRole)) {
            bottomNav.inflateMenu(R.menu.bottom_menu_seller);
        } else {
            bottomNav.inflateMenu(R.menu.bottom_menu_buyer);
        }

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_shop || id == R.id.nav_inventory) {
                return true;
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                Toast.makeText(this, "Perfil - Próximamente", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_add_product) {
                startActivityForResult(new Intent(this, AddProductActivity.class), REQ_ADD_PRODUCT);
                return true;
            } else if (id == R.id.nav_sales) {
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            }
            return false;
        });

        productList = new ArrayList<>();
        productList.add(new Product("1", "Smartphone X Gold", 999.99, "Edición especial con acabados en oro."));
        productList.add(new Product("2", "Reloj Luxury", 499.00, "Elegancia y precisión en tu muñeca."));
        productList.add(new Product("3", "Perfume Prime", 120.00, "Fragancia exclusiva de Shoprime."));

        adapter = new ProductAdapter(productList, this);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(adapter);
    }

    @Override
    public void onProductClick(Product product) {
        if ("SELLER".equals(userRole)) {
            onEditProduct(product);
        } else {
            Toast.makeText(this, "Producto: " + product.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAddToCart(Product product) {
        if ("SELLER".equals(userRole)) {
            Toast.makeText(this, "Modo Vendedor: No puedes comprar", Toast.LENGTH_SHORT).show();
        } else {
            CartManager.getInstance().addProduct(product);
            Toast.makeText(this, "Añadido: " + product.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEditProduct(Product product) {
        if ("SELLER".equals(userRole)) {
            Intent intent = new Intent(this, AddProductActivity.class);
            intent.putExtra("product", product);
            startActivityForResult(intent, REQ_EDIT_PRODUCT);
        }
    }

    @Override
    public void onDeleteProduct(Product product) {
        if ("SELLER".equals(userRole)) {
            productList.remove(product);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Product product = (Product) data.getSerializableExtra("product");
            if (product != null) {
                if (requestCode == REQ_ADD_PRODUCT) {
                    productList.add(product);
                } else if (requestCode == REQ_EDIT_PRODUCT) {
                    for (int i = 0; i < productList.size(); i++) {
                        if (productList.get(i).getId().equals(product.getId())) {
                            productList.set(i, product);
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}