package com.example.assignment1_auni;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etBaggageWeight, etExcessRate;
    private RadioGroup rgTicketClass;
    private RadioButton rbEconomy, rbBusiness;
    private TextView tvInputSummary, tvExcessWeight, tvExcessCharge, tvTotalPayable, tvPaymentStatus;
    private Button btnCalculate, btnClear;

    public static final String GITHUB_URL = "https://github.com/yourusername/ExcessBaggageCalculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Excess Baggage Calculation");
        }

        // Inisialisasi Komponen UI
        etBaggageWeight = findViewById(R.id.etBaggageWeight);
        etExcessRate = findViewById(R.id.etExcessRate);
        rgTicketClass = findViewById(R.id.rgTicketClass);
        rbEconomy = findViewById(R.id.rbEconomy);
        rbBusiness = findViewById(R.id.rbBusiness);

        // TextView Summary
        tvInputSummary = findViewById(R.id.tvInputSummary);
        tvExcessWeight = findViewById(R.id.tvExcessWeight);
        tvExcessCharge = findViewById(R.id.tvExcessCharge);
        tvTotalPayable = findViewById(R.id.tvTotalPayable);
        tvPaymentStatus = findViewById(R.id.tvPaymentStatus);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBaggageFee();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void calculateBaggageFee() {
        String weightStr = etBaggageWeight.getText().toString().trim();
        String rateStr = etExcessRate.getText().toString().trim();

        if (weightStr.isEmpty()) {
            etBaggageWeight.setError("Please enter baggage weight");
            Toast.makeText(this, "Please enter baggage weight in kg", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rateStr.isEmpty()) {
            etExcessRate.setError("Please enter rate per kg");
            Toast.makeText(this, "Please enter excess rate per kg", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double rate = Double.parseDouble(rateStr);

            if (weight < 0 || rate < 0) {
                Toast.makeText(this, "Values cannot be negative!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Dapatkan jenis kelas tiket
            String ticketClass = rbEconomy.isChecked() ? "Economy" : "Business";
            double allowance = rbEconomy.isChecked() ? 20.0 : 40.0;

            // Pengiraan
            double excessWeight = Math.max(0, weight - allowance);
            double excessCharge = excessWeight * rate;
            double totalPayable = excessCharge + (0.08 * excessCharge);

            // 1. Paparkan semula Input Pengguna
            tvInputSummary.setText(String.format(Locale.getDefault(),
                    "Class: %s | Weight: %.2f kg | Rate: RM %.2f", ticketClass, weight, rate));

            // 2. Paparkan Hasil Pengiraan
            tvExcessWeight.setText(String.format(Locale.getDefault(), "Excess Baggage Weight: %.2f kg", excessWeight));
            tvExcessCharge.setText(String.format(Locale.getDefault(), "Excess Baggage Charge: RM %.2f", excessCharge));
            tvTotalPayable.setText(String.format(Locale.getDefault(), "Total Amount Payable (incl. 8%% tax): RM %.2f", totalPayable));

            // 3. Semak Status Bayaran (Sama ada perlu bayar atau tidak)
            if (excessWeight <= 0) {
                tvPaymentStatus.setText("Status: No extra charge required! (Within allowance)");
                tvPaymentStatus.setTextColor(Color.parseColor("#2E7D32")); // Warna hijau
            } else {
                tvPaymentStatus.setText("Status: Payment Required");
                tvPaymentStatus.setTextColor(Color.parseColor("#C62828")); // Warna merah
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid numerical input entered", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etBaggageWeight.setText("");
        etExcessRate.setText("");
        rbEconomy.setChecked(true);
        tvInputSummary.setText("Ticket Class: Economy | Weight: 0.00 kg | Rate: RM 0.00");
        tvExcessWeight.setText("Excess Baggage Weight: 0.00 kg");
        tvExcessCharge.setText("Excess Baggage Charge: RM 0.00");
        tvTotalPayable.setText("Total Amount Payable (incl. 8% tax): RM 0.00");
        tvPaymentStatus.setText("Status: No calculation done");
        tvPaymentStatus.setTextColor(Color.parseColor("#0A3981"));
        etBaggageWeight.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Excess Baggage Fee Calculator App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out the Excess Baggage Fee Calculator app on GitHub: " + GITHUB_URL);
            startActivity(Intent.createChooser(shareIntent, "Share Application via"));
            return true;
        } else if (id == R.id.action_about) {
            Intent aboutIntent = new Intent(MainActivity.this, MainAbout.class);
            startActivity(aboutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}