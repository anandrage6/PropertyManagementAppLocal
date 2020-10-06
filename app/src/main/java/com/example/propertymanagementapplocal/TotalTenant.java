package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TotalTenant extends AppCompatActivity {

    private Toolbar mytoolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private long refFlatId;
    private long refTenantId;
    Bundle bundle;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_tenant);

       mytoolbar = findViewById(R.id.myToolBar);
        tabLayout =  findViewById(R.id.tabLayout);
        viewPager =  findViewById(R.id.myViewPager);



        refFlatId = getIntent().getLongExtra(Config.COLUMN_FLATS_ID, -1);
        Log.d("flatRefFId in Total Tenant:==>", String.valueOf(refFlatId));


        //refTenantId = getIntent().getExtras().getLong("2");
       // Log.d("Tenant in Total Tenant:==>", String.valueOf(refTenantId));




        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle("Tenant");
        //((AppCompatActivity)getApplicationContext()).setSupportActionBar(mytoolbar);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
    }

    @SuppressLint("LongLogTag")
    private void setupViewPager(ViewPager viewPager){
        ViePageAdapter viePageAdapter = new ViePageAdapter(getSupportFragmentManager());
        //viePageAdapter.addFragnment(new OverView(),"OverView");

        bundle = new Bundle();
        bundle.putLong("1", refFlatId);
        Log.d("flatRefFId in bundle Tenant:==>", String.valueOf(bundle));




        //OverView Pass data
        OverView oV = new OverView();
        oV.setArguments(bundle);
        viePageAdapter.addFragnment(oV, "OverView");

        //Invoices Pass Data
        InvoicesFragment IF = new InvoicesFragment();
        IF.setArguments(bundle);
        viePageAdapter.addFragnment(IF, "Invoices");

        //payments
        PaymentFragment pF = new PaymentFragment();
        pF.setArguments(bundle);
        viePageAdapter.addFragnment(pF, "Payments");


        //documents
        Documents d = new Documents();
        d.setArguments(bundle);
        viePageAdapter.addFragnment(d, "Documents");

        viewPager.setAdapter(viePageAdapter);

    }
/*
    public void switchContent(int list, Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(list, frag, frag.toString());
        ft.addToBackStack(null);
        ft.commit();
    }


 */
}