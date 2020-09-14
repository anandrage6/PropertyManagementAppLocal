package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TenantDetails extends AppCompatActivity {
    FloatingActionButton btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_details);

        //floating button
        btnadd = findViewById(R.id.addbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddTenant();

            }
        });

    }

    private void openActivityAddTenant() {
        Intent i = new Intent(this, AddTenants.class);
        startActivity(i);
    }
}