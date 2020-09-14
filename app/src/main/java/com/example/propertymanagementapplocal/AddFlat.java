package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/*
public class AddFlat extends AppCompatActivity {

    private static long propertyId;
    private static FlatsCreateListener flatsCreateListener;

    EditText edtFlatNo;
    Button flatsave;

    public AddFlat() {
    }

    public static AddFlat newInstance(long id, FlatsCreateListener listener) {

        propertyId = id;
        flatsCreateListener = listener;
        AddFlat addFlats = new AddFlat();
        //addFlats.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return addFlats;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flat);

        edtFlatNo = findViewById(R.id.flatNoEditText);
        flatsave = findViewById(R.id.flatSavebtn);

        flatsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void getData() {
        String flatNo = edtFlatNo.getText().toString();

        FlatsModelClass flats = new FlatsModelClass(-1, flatNo );

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

        long id = databaseQueryClass.insertFlats(flats, propertyId);

        if(id>0){
            flats.setFlatId(id);
            flatsCreateListener.onFlatsCreated(flats);
            Intent i = new Intent(this, PropertyDetails.class);
            startActivity(i);
        }
    }
}

 */