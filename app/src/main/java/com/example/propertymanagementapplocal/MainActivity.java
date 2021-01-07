package com.example.propertymanagementapplocal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView name, email;
    private Toolbar toolbar;
    DatabaseHelper databaseHelper;
    FloatingActionButton btnadd;

    //ads
    private AdView mAdView;

    private TextView propertyListEmptyTextView;

    //new recycler part
    private RecyclerView recyclerView;
    private PropertyListRecyclerViewAdapter propertyListRecyclerViewAdapter;
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<PropertyModelClass> propertyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //App Id
        //MobileAds.initialize(this, "ca-app-pub-1722001050000485~8005397194");
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // banner ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        // ca-app-pub-1722001050000485~8005397194

        recyclerView = findViewById(R.id.apartmentRecyclerId);
        Logger.addLogAdapter(new AndroidLogAdapter());

        propertyList.addAll(databaseQueryClass.getAllProperty());

        propertyListRecyclerViewAdapter = new PropertyListRecyclerViewAdapter(this, propertyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(propertyListRecyclerViewAdapter);

        //viewVisibility

        propertyListEmptyTextView = findViewById(R.id.emptyListTextView);
        viewVisibility();


        //floating button
        btnadd = findViewById(R.id.addbtn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddProperty();

            }
        });


        //tool bar
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Property Management");

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {

                    //add Property
                    case R.id.property:
                        Toast.makeText(MainActivity.this, "Properties", Toast.LENGTH_SHORT).show();
                        openActivityAddProperty();
                        break;

                    //settings
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "settings", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        View hView = navigationView.getHeaderView(0);
        name = hView.findViewById(R.id.name);
        email = hView.findViewById(R.id.email);
        name.setText("Anand");
        email.setText("Anandkumar@gmail.com");

        /*
        // settings shared preferences
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String namev = pref.getString("ownerName","");
        String addv = pref.getString("OwnerAddress","");
        String mobv = pref.getString("ownerMobile", "");
        String upiv = pref.getString("ownerUpiId","");
        String curv = pref.getString("currencyType","₹");
        String rendv = pref.getString("RentalDueday","One Month");

        Toast.makeText(this,namev, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,addv, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,mobv, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,upiv, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,curv, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,rendv, Toast.LENGTH_SHORT).show();

         */

    }

    private void displayInterstitial() {

    }

    /*
    private void showRecord() {

        Adapter adapter = new RecyclerView.Adapter(MainActivity.this, databaseHelper.getAllData(Constants.C_ID + " DESC"));
        recyclerView.setAdapter(adapter);
    }

     */


    @Override
    protected void onResume() {
        super.onResume();
        // showRecord();
        propertyList.clear();
        propertyList.addAll(databaseQueryClass.getAllProperty());

        propertyListRecyclerViewAdapter = new PropertyListRecyclerViewAdapter(this, propertyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(propertyListRecyclerViewAdapter);

        //viewVisibility

        propertyListEmptyTextView = findViewById(R.id.emptyListTextView);
        viewVisibility();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        /*
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirm Exit...!!");
        alertDialogBuilder.setMessage("Are you sure you want to exit");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

         */
    }

    public void openActivityAddProperty() {
        Intent i = new Intent(this, AddProperty.class);
        startActivity(i);
    }

    public void viewVisibility() {
        if (propertyList.isEmpty())
            propertyListEmptyTextView.setVisibility(View.VISIBLE);
        else
            propertyListEmptyTextView.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // delete All properties
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_delete) {

            androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, You wanted to settings all Properties?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllProperties();
                            if (isAllDeleted) {
                                propertyList.clear();
                                propertyListRecyclerViewAdapter.notifyDataSetChanged();
                                viewVisibility();
                            }
                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(this, SettingPreferences.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
