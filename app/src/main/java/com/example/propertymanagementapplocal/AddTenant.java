package com.example.propertymanagementapplocal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class AddTenant extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static long FlatId;
    private static long propertyId;
    private static String address;
    private static TenantCreateListener tenantCreateListener;

    private Toolbar toolbar;
    String note, rentIsPaid;

    EditText edtTenantName, edtPhoneNumber, edtEmail, edtRent, edtSecurityDeposit;
    Spinner spinRentIsPaid;
    TextView tvValue, tvNotes, tvLeaseStart, tvLeaseEnd;
    Button saveBtn, cancelBtn;
    ImageButton incrementBtn, decrementBtn;
    int count = 0;

    String strDate1 = "01/01/0001";
    String strDate2 = "01/02/00002";

    Date date1, date2;

    double drent, damount;

    //Awesome Validation
    AwesomeValidation awesomeValidation;

    //default constructor
    public AddTenant() {

    }

    public static AddTenant newInstance(long refFlatId, TenantCreateListener listener) {
        //Log.d("RefPropertyId : == >", String.valueOf(refPropertyId));
        //propertyId = refPropertyId;
        FlatId = refFlatId;
        tenantCreateListener = listener;
        AddTenant addTenant = new AddTenant();
        return addTenant;
    }


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
        setContentView(R.layout.activity_add_tenant);

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


        //Awesome Validation

        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(AddTenant.this, R.id.tenantNameEditText, "[a-zA-Z\\s]+", R.string.err_tenantName);
        awesomeValidation.addValidation(AddTenant.this, R.id.tenentPhoneEditText, RegexTemplate.TELEPHONE, R.string.err_telphone);
        awesomeValidation.addValidation(AddTenant.this, R.id.tenantEmailEditText, android.util.Patterns.EMAIL_ADDRESS, R.string.err_email);


        tvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddTenant.this, Note.class);
                i.putExtra("CurrentNote", tvNotes.getText().toString());
                startActivityForResult(i, 1);
            }
        });



        /*
       Intent  in = getIntent();
       FlatId = in.getLongExtra("flatId", -1);
       Log.d("RefFlatId in AddT : ==>", String.valueOf(FlatId));

         */


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
                final DatePickerDialog datePickerDialog = new DatePickerDialog(AddTenant.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        strDate1 = day + "/" + month + "/" + year;

                        //tvLeaseStart.setText(strDate1);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
                        try {
                            date1 = simpleDateFormat.parse(strDate1);
                            date2 = simpleDateFormat.parse(strDate2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(date1.compareTo(date2) < 0){

                            Toast.makeText(getApplicationContext(), "Lease Start is greater than Lease End", Toast.LENGTH_LONG).show();
                            //tvLeaseStart.setText(strDate1);
                        }else if(date1.compareTo(date2) == 0){
                            tvLeaseStart.setText(strDate1);
                        }else{
                            tvLeaseStart.setText(strDate1);
                            //Toast.makeText(getApplicationContext(), "Lease Start is greater than Lease End", Toast.LENGTH_LONG).show();
                        }

                    }
                }, year, month, day);

                //disable future date
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();
            }
        });

        //EndDate Picker
        tvLeaseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTenant.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                         strDate2 = day + "/" + month + "/" + year;

                       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
                        try {
                            date1 = simpleDateFormat.parse(strDate1);
                            date2 = simpleDateFormat.parse(strDate2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(date1.compareTo(date2) < 0){
                            tvLeaseEnd.setText(strDate2);
                        }else if(date1.compareTo(date2) ==0){
                            tvLeaseEnd.setText(strDate2);
                        }else{
                            Toast.makeText(getApplicationContext(), "Lease End is less than Lease Start", Toast.LENGTH_LONG).show();
                        }

                    }
                }, year, month, day);

                //disable past date
               // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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

        if (awesomeValidation.validate()) {
            String name = edtTenantName.getText().toString();
            String phone = edtPhoneNumber.getText().toString();
            String email = edtEmail.getText().toString();
            String leaseStart = tvLeaseStart.getText().toString();
            String leaseEnd = tvLeaseEnd.getText().toString();
            String rentisPaid = rentIsPaid;
            String totalOccupants = tvValue.getText().toString();
            String notes = tvNotes.getText().toString();
            drent = Double.parseDouble(edtRent.getText().toString());
            String rentAmount = String.valueOf(drent);
            damount = Double.parseDouble(edtSecurityDeposit.getText().toString());
            String securityDeposit = String.valueOf(damount);

            if (phone.length() == 10) {
                TenantModelClass tenant = new TenantModelClass(-1, name, phone, email, leaseStart, leaseEnd, rentisPaid, totalOccupants, notes, rentAmount, securityDeposit);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

                //Log.d("propertyId", String.valueOf(propertyId));
                //Log.d("FlatId", String.valueOf(FlatId));


                long id = databaseQueryClass.insertTenant(tenant, FlatId);
                Log.e("Result tenant id : ==> ", String.valueOf(id));


                if (id > 0) {
                    tenant.setTenantId(id);
                    tenantCreateListener.onTenantCreated(tenant);
                    finish();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Please Enter Valid 10 digits Number", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        rentIsPaid = "" + adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}