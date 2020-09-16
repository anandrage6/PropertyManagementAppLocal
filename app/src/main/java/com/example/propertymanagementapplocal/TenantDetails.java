package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TenantDetails extends AppCompatActivity implements TenantCreateListener {
    FloatingActionButton btnadd;
    private long refFlatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_details);

        refFlatId = getIntent().getLongExtra(Config.COLUMN_FLATS_ID, -1);

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
        AddTenant addTenant = AddTenant.newInstance(refFlatId, this);
        addTenant.show(getSupportFragmentManager(), Config.CREATE_TENANT);
    }

    @Override
    public void onTenantCreated(TenantModelClass tenant) {

    }
}