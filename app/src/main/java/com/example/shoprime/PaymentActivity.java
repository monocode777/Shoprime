package com.example.shoprime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView tvAmount = findViewById(R.id.tvPaymentAmount);
        Button btnPay = findViewById(R.id.btnPay);

        double total = CartManager.getInstance().getTotal();
        tvAmount.setText(String.format("$%.2f", total));

        btnPay.setOnClickListener(v -> {
            // Simulación de procesamiento de pago
            Toast.makeText(this, "Procesando pago...", Toast.LENGTH_SHORT).show();
            
            // Simular éxito después de un breve momento (aquí es instantáneo)
            CartManager.getInstance().checkout();
            Toast.makeText(this, "¡Pago exitoso! Pedido registrado.", Toast.LENGTH_LONG).show();
            
            // Volver al inicio
            finish();
        });
    }
}