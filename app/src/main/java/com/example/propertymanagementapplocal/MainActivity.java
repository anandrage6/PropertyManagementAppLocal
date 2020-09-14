package com.example.propertymanagementapplocal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView name,email;
    private Toolbar toolbar;
    DatabaseHelper databaseHelper;
    FloatingActionButton btnadd;

    //new recycler part
    private RecyclerView recyclerView;
    private PropertyListRecyclerViewAdapter propertyListRecyclerViewAdapter;
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<PropertyModelClass> propertyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.apartmentRecyclerId);
        Logger.addLogAdapter(new AndroidLogAdapter());

        propertyList.addAll(databaseQueryClass.getAllProperty());

        propertyListRecyclerViewAdapter = new PropertyListRecyclerViewAdapter(this, propertyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(propertyListRecyclerViewAdapter);

        //viewVisibility();


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

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.property:
                        Toast.makeText(MainActivity.this, "Properties", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.help:
                        Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });

        View hView =  navigationView.getHeaderView(0);
        name = hView.findViewById(R.id.name);
        email = hView.findViewById(R.id.email);
        name.setText("Anand");
        email.setText("Anandkumar@gmail.com");

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
    public void openActivityAddProperty(){
        Intent i = new Intent(this, AddProperty.class);
        startActivity(i);
    }



}
