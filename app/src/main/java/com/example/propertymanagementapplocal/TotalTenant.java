package com.example.propertymanagementapplocal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class TotalTenant extends AppCompatActivity {

    private Toolbar mytoolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private long refFlatId;
    ViePageAdapter viePageAdapter;
    private long tenantId;
    Bundle bundle;
    int selectedTabPosition = 0;

    private DatabaseQueryClass databaseQueryClass;
    private FlatsModelClass mflatsModelClass;
    private PropertyModelClass mpropertyModelClass;
    private TenantModelClass mtenantModelClass;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_tenant);

        mytoolbar = findViewById(R.id.myToolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.myViewPager);


        refFlatId = getIntent().getLongExtra(Config.COLUMN_FLATS_ID, -1);
        Log.d("flatRefFId in Total Tenant:==>", String.valueOf(refFlatId));

        databaseQueryClass = new DatabaseQueryClass(getApplicationContext());

        //getting values by faltid
        mflatsModelClass = databaseQueryClass.getFlatsById(refFlatId);
        Log.e("flat.no ==> ", String.valueOf(mflatsModelClass.getFlaNo()));

        String flatNo = mflatsModelClass.getFlaNo();
        long pfId = mflatsModelClass.getpFId();
        Log.e("pFId ===> ", String.valueOf(pfId));

        //getting values by PfId
        mpropertyModelClass = databaseQueryClass.getPropertyById(pfId);
        String propertyName = mpropertyModelClass.getPropertyName();
        Log.e("propertyName =====> ", String.valueOf(propertyName));

        //getting values by flatid


        try {
            mtenantModelClass = databaseQueryClass.getTenantIdByFlatId(refFlatId);
            tenantId = mtenantModelClass.getTenantId();
            Log.e("tenantId ========> ", String.valueOf(tenantId));
        } catch (Exception e) {
            e.printStackTrace();
        }


        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle(propertyName + " / " + getResources().getString(R.string.flatno) + " " + flatNo);

        //((AppCompatActivity)getApplicationContext()).setSupportActionBar(mytoolbar);


    }

    @SuppressLint("LongLogTag")
    private void setupViewPager(ViewPager viewPager) {
        ViePageAdapter viePageAdapter = new ViePageAdapter(getSupportFragmentManager());
        //viePageAdapter.addFragnment(new OverView(),"OverView");

        bundle = new Bundle();
        bundle.putLong("1", refFlatId);
        bundle.putLong("2", tenantId);
        Log.d("flatRefFId in bundle Tenant:==>", String.valueOf(bundle));


        //refTenantId = getIntent().getLongExtra("TId", -1);
        //Log.d("TenantId in  TotalTenant:==>", String.valueOf(refTenantId));


        //OverView Pass data
        OverView oV = new OverView();
        oV.setArguments(bundle);
        viePageAdapter.addFragnment(oV, "Tenant");

        // Balances
        Balances balances = new Balances();
        balances.setArguments(bundle);
        viePageAdapter.addFragnment(balances, "Balances");

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

    @Override
    protected void onResume() {
        super.onResume();

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 selectedTabPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
        viewPager.setCurrentItem(selectedTabPosition);
    }
}