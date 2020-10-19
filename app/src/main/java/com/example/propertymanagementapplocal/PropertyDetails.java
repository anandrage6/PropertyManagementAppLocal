package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetails extends AppCompatActivity implements FlatsCreateListener {
    TextView locationTv,ownerNameTv, propertyNameTv;
    ImageView imagetv;
    private long propertyId;
    String strLocationTv, strCity, strState, strZipCode, strownerNameTv, stringimage, totalAddress, strPropertyName, strPropertyType, strDescription;
    private Toolbar toolbar;
    ImageButton editBtn;



    //floating add button
    //flats
    ImageButton flatAddBtn;

    //private TextView flatEmptyListTV;
     private LinearLayout linearLayout;

    private long refpropertyId;

    //visibility
    // private TextView emptylist;

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

        flatsrecyclerView = findViewById(R.id.flatsRecyclerViewid);

        //tool bar
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("");


      //visibility of emptylist
        linearLayout = findViewById(R.id.flatsemptyListHide);

        viewVisibility();



        editBtn = findViewById(R.id.propertyEditBtn);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Edit", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(PropertyDetails.this, UpdateProperty2.class);
                intent.putExtra("PROPERTYID",propertyId);
                intent.putExtra("IMAGE",stringimage);
                intent.putExtra("PROPERTYTYPE",strPropertyType);
                intent.putExtra("PROPERTYNAME",strPropertyName);
                intent.putExtra("OWNERNAME",strownerNameTv);
                intent.putExtra("ADDRESS",strLocationTv);
                intent.putExtra("CITY",strCity);
                intent.putExtra("STATE",strState);
                intent.putExtra("ZIPCODE",strZipCode);
                intent.putExtra("DESCRIPTION",strDescription);
                startActivity(intent);


            }
        });





        //retrive full details part
        propertyId = getIntent().getLongExtra(Config.COLUMN_PROPERTY_ID, -1);
        strLocationTv = getIntent().getStringExtra(Config.COLUMN_PROPERTY_ADDRESS);
        strCity = getIntent().getStringExtra(Config.COLUMN_PROPERTY_CITY);
        strState = getIntent().getStringExtra(Config.COLUMN_PROPERTY_STATE);
        strZipCode = getIntent().getStringExtra(Config.COLUMN_PROPERTY_ZIPCODE);
        totalAddress = strLocationTv+","+strCity+""+strState+"-"+strZipCode;
        strPropertyName = getIntent().getStringExtra(Config.COLUMN_PROPERTY_PROPERTYNAME);
        strPropertyType = getIntent().getStringExtra(Config.COLUMN_PROPERTY_PROPERTYTYPE);
        strDescription = getIntent().getStringExtra(Config.COLUMN_PROPERTY_DESCRIPTION);

        strownerNameTv = getIntent().getStringExtra(Config.COLUMN_PROPERTY_OWNERNAME);
        try {
            stringimage = getIntent().getStringExtra(Config.COLUMN_PROPERTY_IMAGE);
        }catch(Exception e){

        }

        propertyNameTv.setText(strPropertyName);
        locationTv.setText(totalAddress);
        ownerNameTv.setText(strownerNameTv);

        if(stringimage!= null) {
            imagetv.setImageURI(Uri.parse(stringimage));
        }


        refpropertyId = getIntent().getLongExtra(Config.COLUMN_PROPERTY_ID, -1);
        Log.d("PropertyDetRefId : ==> ", String.valueOf(refpropertyId));

        flatsList.addAll(databaseQueryClass.getAllFlatsByPFId(refpropertyId));
        Log.d("FlatList : ==> ", String.valueOf(flatsList.size()));

        //flats part
        flatsListRecyclerAdapter = new FlatsListRecyclerAdapter(this, flatsList);
        flatsrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        flatsrecyclerView.setAdapter(flatsListRecyclerAdapter);




        //flats button click
        flatAddBtn = findViewById(R.id.flatAddbtn);
        flatAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(Appartments.this, "button clicked", Toast.LENGTH_LONG).show();
                openActivityAddFlats();

            }
        });


        //navigation method

        String intentValue = getIntent().getStringExtra("intentPassed");

        /*
        if(!intentValue.isEmpty()){
            openActivityAddFlats();
        }


         */

    }

    private void openActivityAddFlats() {
        AddFlats addFlats = AddFlats.newInstance(refpropertyId, this);
        addFlats.show(getSupportFragmentManager(), Config.CREATE_FLAT);
        //Intent i = new Intent(this,addFlats.getClass());
        //this.startActivity(i);

    }

    @Override
    public void onFlatsCreated(FlatsModelClass flat) {

        flatsList.add(flat);
        flatsListRecyclerAdapter.notifyDataSetChanged();

        //flats emptylist
        //viewVisibility();

    }

    public void viewVisibility() {
        try {
            if (flatsList.isEmpty()) {
                linearLayout.setVisibility(View.GONE);
            } else {
               linearLayout.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    }