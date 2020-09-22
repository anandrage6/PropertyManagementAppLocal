package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TenantDetails extends AppCompatActivity implements TenantCreateListener {
    FloatingActionButton btnadd;
    TextView name, phone, email;
    String strName, strPhone, strEmail;
    private long refFlatId;
    private long refPropertyId;
   // private String address;
   private RecyclerView tenantRecyclerView;
    private TenantListRecyclerAdapter tenantListRecyclerAdapter;

    private DatabaseQueryClass databaseQueryClass ;

    private List<TenantModelClass> tenantList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_details);


        //refPropertyId = getIntent().getLongExtra(Config.COLUMN_PROPERTY_ID, -1);
        //Log.d("TenantDetailsRefPId:==>", String.valueOf(refPropertyId));
        refFlatId = getIntent().getLongExtra(Config.COLUMN_FLATS_ID, -1);
        Log.d("TenantDetailsRefFId:==>", String.valueOf(refFlatId));

        /*
        name = findViewById(R.id.tenantNameTV);
        phone = findViewById(R.id.tenantPhoneTV);
        email = findViewById(R.id.tenantEmailTV);

         */

        //retrive full details part
        tenantRecyclerView = findViewById(R.id.tenantRecyclerId);

        databaseQueryClass = new DatabaseQueryClass(this);
        tenantList = new ArrayList<>();
        Log.d("Queryclass :==> ", String.valueOf(databaseQueryClass.getAllTenantsByFId(refFlatId)));
        tenantList.addAll(databaseQueryClass.getAllTenantsByFId(refFlatId));
        Log.d("TenantList : ==> ", String.valueOf(tenantList.size()));
        //List<TenantModelClass> allT = new ArrayList<TenantModelClass>();



        tenantListRecyclerAdapter = new TenantListRecyclerAdapter(this, tenantList);
        tenantRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        tenantRecyclerView.setAdapter(tenantListRecyclerAdapter);





        //floating button
        btnadd = findViewById(R.id.addbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddTenant();
               // viewVisibility();

            }
        });

    }

    public void viewVisibility() {
        if(tenantList.isEmpty())
            btnadd.setVisibility(View.VISIBLE);
        else
            btnadd.setVisibility(View.GONE);
    }

    private void openActivityAddTenant() {
        AddTenant addTenant = AddTenant.newInstance(refFlatId, this);
        addTenant.show(getSupportFragmentManager(), Config.CREATE_TENANT);
    }

    @Override
    public void onTenantCreated(TenantModelClass tenant) {
        tenantList.add(tenant);
        tenantListRecyclerAdapter.notifyDataSetChanged();
        //viewVisibility();

    }

}