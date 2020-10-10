package com.example.propertymanagementapplocal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class AddT extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static long FlatId;
    //private static long propertyId;
    //private static String address;
    //private static TenantCreateListener tenantCreateListener;

    private Toolbar toolbar;
    String note, rentIsPaid;

    EditText edtTenantName, edtPhoneNumber, edtEmail, edtRent, edtSecurityDeposit;
    Spinner spinRentIsPaid;
    TextView tvValue, tvNotes, tvLeaseStart, tvLeaseEnd;
    Button saveBtn, cancelBtn;
    ImageButton incrementBtn, decrementBtn;
    int count = 0;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                //Bundle b = getIntent().getExtras();
                String strnote = data.getStringExtra("note");
                Log.d("StrNote Result : ", strnote);
                tvNotes.setText(strnote);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_t);

        //tool bar
        toolbar = findViewById(R.id.toolbar);


        //id's
        edtTenantName = findViewById(R.id.tenantNameEditText);
        edtPhoneNumber = findViewById(R.id.tenentPhoneEditText);
        edtEmail = findViewById(R.id.tenantEmailEditText);
        tvLeaseStart = findViewById(R.id.tenantLeaseStart);
        tvLeaseEnd = findViewById(R.id.tenantLeaseEnd);
        edtRent = findViewById(R.id.tenentRentEditText);
        edtSecurityDeposit = findViewById(R.id.tenantSecurityDepositEditText);
        spinRentIsPaid = findViewById(R.id.tenantRentSpin);
        tvValue = findViewById(R.id.tenantTotalOccupantsValueTextView);
        tvNotes = findViewById(R.id.tenantNotesTextView);
        incrementBtn = findViewById(R.id.tenantIncrementBtn);
        decrementBtn = findViewById(R.id.tenantDecrementBtn);
        saveBtn = findViewById(R.id.tenantSaveButton);
        cancelBtn = findViewById(R.id.tenantCancelButton);

       /*
        if (getArguments() != null) {
            String strNotes = this.getArguments().getString("Note");
            tvNotes.setText(strNotes);
        }
        */



        tvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AddT.this, Note.class);
                i.putExtra("CurrentNote", tvNotes.getText().toString());
                startActivityForResult(i, 1);
            }
        });



       Intent  in = getIntent();
       FlatId = in.getLongExtra("flatId", -1);
       Log.d("RefFlatId in AddT : ==>", String.valueOf(FlatId));



        //increment value part
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                tvValue.setText("" + count);

            }
        });

        //decrement value part
        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= 0) count = 0;
                else count--;
                tvValue.setText("" + count);
            }
        });

        //spinner part

        ArrayAdapter<CharSequence> rentAdapter = ArrayAdapter.createFromResource(this, R.array.Rent_is_Paid, android.R.layout.simple_spinner_item);
        rentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRentIsPaid.setAdapter(rentAdapter);
        spinRentIsPaid.setOnItemSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //startDate Picker
        tvLeaseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddT.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        tvLeaseStart.setText(date);


                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //EndDate Picker
        tvLeaseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddT.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        tvLeaseEnd.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //save data function
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void getData() {

        String name = edtTenantName.getText().toString();
        String phone = edtPhoneNumber.getText().toString();
        String email = edtEmail.getText().toString();
        String leaseStart = tvLeaseStart.getText().toString();
        String leaseEnd = tvLeaseEnd.getText().toString();
        String rentisPaid = rentIsPaid;
        String totalOccupants = tvValue.getText().toString();
        String notes = tvNotes.getText().toString();
        String rentAmount = edtRent.getText().toString();
        String securityDeposit = edtSecurityDeposit.getText().toString();
        TenantModelClass tenant = new TenantModelClass(-1, name, phone, email, leaseStart, leaseEnd, rentisPaid, totalOccupants, notes, rentAmount, securityDeposit);

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

        //Log.d("propertyId", String.valueOf(propertyId));
        //Log.d("FlatId", String.valueOf(FlatId));


        long id = databaseQueryClass.insertTenant(tenant, FlatId);
        Log.e("Result tenant id : ==> ", String.valueOf(id));


        if (id > 0) {
            tenant.setTenantId(id);

        }

        /*
        FragmentManager fm = getSupportFragmentManager();
        OverView overView = new OverView();
        Bundle b = new Bundle();
        b.putLong("id", id);
        b.putLong("rFId", FlatId);
        overView.setArguments(b);
        fm.beginTransaction().replace(R.id.addTenant, overView).commit();

         */
        //getSupportFragmentManager().findFragmentById(R.id.addTenant);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        rentIsPaid = "" + adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}