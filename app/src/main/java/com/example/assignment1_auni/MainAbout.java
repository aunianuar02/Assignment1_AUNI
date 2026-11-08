package com.example.assignment1_auni;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainAbout extends AppCompatActivity {

    private TextView tvGithubUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_about);

        // button back (Up Navigation) pada ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About AuniAir");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvGithubUrl = findViewById(R.id.tvGithubUrl);

        //  URL dari MainActivity
        tvGithubUrl.setText(MainActivity.GITHUB_URL);

        // 1. TextView URL
        tvGithubUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithubLink();
            }
        });

    }

    private void openGithubLink() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.GITHUB_URL));
        startActivity(browserIntent);
    }

    // Fungsi Apabila Button Back pada ActionBar Ditekan
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
