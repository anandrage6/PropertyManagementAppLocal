package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetails extends AppCompatActivity implements FlatsCreateListener {
    TextView locationTv,ownerNameTv, propertyNameTv;
    ImageView imagetv;
    private long propertyId;
    String strLocationTv, strCity, strState, strZipCode, strownerNameTv, stringimage, totalAddress, strPropertyName;
    private Toolbar toolbar;

    //ImageButton editbutton;


    private long refpropertyId;

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);


    private List<FlatsModelClass> flatsList = new ArrayList<>();

    private RecyclerView flatsrecyclerView;
    private FlatsListRecyclerAdapter flatsListRecyclerAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        locationTv = findViewById(R.id.locationTextViewDetails);
        ownerNameTv = findViewById(R.id.ownernameTextViewDetails);
        imagetv = findViewById(R.id.imageViewDetails);
        propertyNameTv = findViewById(R.id.propertyNameTv);

        //tool bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // editbutton = findViewById(R.id.propertyEditBtn);

        //floating add button
        //flats
        ImageButton flatAddBtn;

        //retrive full details part
        flatsrecyclerView = findViewById(R.id.flatsRecyclerViewid);

        propertyId = getIntent().getLongExtra(Config.COLUMN_PROPERTY_ID, -1);
        strLocationTv = getIntent().getStringExtra(Config.COLUMN_PROPERTY_ADDRESS);
        strCity = getIntent().getStringExtra(Config.COLUMN_PROPERTY_CITY);
        strState = getIntent().getStringExtra(Config.COLUMN_PROPERTY_STATE);
        strZipCode = getIntent().getStringExtra(Config.COLUMN_PROPERTY_ZIPCODE);
        totalAddress = strLocationTv+","+strCity+""+strState+"-"+strZipCode;
        strPropertyName = getIntent().getStringExtra(Config.COLUMN_PROPERTY_PROPERTYNAME);

        strownerNameTv = getIntent().getStringExtra(Config.COLUMN_PROPERTY_OWNERNAME);
        stringimage = getIntent().getStringExtra(Config.COLUMN_PROPERTY_IMAGE);


        propertyNameTv.setText(strPropertyName);
        locationTv.setText(totalAddress);
        ownerNameTv.setText(strownerNameTv);
        imagetv.setImageURI(Uri.parse(stringimage));


        refpropertyId = getIntent().getLongExtra(Config.COLUMN_PROPERTY_ID, -1);

        flatsList.addAll(databaseQueryClass.getAllFlatsByPFId(refpropertyId));

        flatsListRecyclerAdapter = new FlatsListRecyclerAdapter(this, flatsList);
        flatsrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        flatsrecyclerView.setAdapter(flatsListRecyclerAdapter);
       // viewVisibility();



        //flats button click
        flatAddBtn = findViewById(R.id.flatAddbtn);
        flatAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddFlats();

            }
        });
    }

    private void openActivityAddFlats() {
        AddFlats addFlats = AddFlats.newInstance(refpropertyId, this);
        addFlats.show(getSupportFragmentManager(), Config.CREATE_FLAT);
        //Intent i = new Intent(getApplication(),addFlats.getClass());
      // startActivity(i);




    }

    @Override
    public void onFlatsCreated(FlatsModelClass flat) {

        flatsList.add(flat);
        flatsListRecyclerAdapter.notifyDataSetChanged();

    }
}