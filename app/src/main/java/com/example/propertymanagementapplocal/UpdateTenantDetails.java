package com.example.propertymanagementapplocal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class UpdateTenantDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static long tenantId;
    private static int tenantItemPosition;
    private static TenantUpdateListener tenantUpdateListener;
    private TenantModelClass tenantModelClass;
    private DatabaseQueryClass databaseQueryClass;

    EditText edtTenantName, edtPhoneNumber, edtEmail, edtRent, edtSecurityDeposit;
    Spinner spinRentIsPaid;
    TextView tvValue, tvNotes, tvLeaseStart, tvLeaseEnd, currencyId, currencyId2;
    Button saveBtn, cancelBtn;
    ImageButton incrementBtn, decrementBtn;
    int count = 0;

    String tenantnameStr, phoneStr, emailStr, rentStr, securityDepositStr, valueStr, notesStr, leaseStartStr, leaseEndStr, rentIsPaidStr;

    ArrayList<String> rentIsPaidList;
    ArrayAdapter<String> rentIsPaidAdapter;

    // Empty constructor
    public UpdateTenantDetails() {

    }

    //new instance
    public static UpdateTenantDetails newInstance(long id, int position, TenantUpdateListener listener) {

        tenantId = id;
        tenantItemPosition = position;
        tenantUpdateListener = listener;

        UpdateTenantDetails updateTenant = new UpdateTenantDetails();
        return updateTenant;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tenant_details);


        //id's
        edtTenantName = findViewById(R.id.tenantNameEditTextUpdate);
        edtPhoneNumber = findViewById(R.id.tenentPhoneEditTextUpdate);
        edtEmail = findViewById(R.id.tenantEmailEditTextUpdate);
        tvLeaseStart = findViewById(R.id.tenantLeaseStartUpdate);
        tvLeaseEnd = findViewById(R.id.tenantLeaseEndUpdate);
        edtRent = findViewById(R.id.tenentRentEditTextUpdate);
        edtSecurityDeposit = findViewById(R.id.tenantSecurityDepositEditTextUpdate);
        spinRentIsPaid = findViewById(R.id.tenantRentSpinUpdate);
        tvValue = findViewById(R.id.tenantTotalOccupantsValueTextViewUpdate);
        tvNotes = findViewById(R.id.tenantNotesTextViewUpdate);
        incrementBtn = findViewById(R.id.tenantIncrementBtnUpdate);
        decrementBtn = findViewById(R.id.tenantDecrementBtnUpdate);
        saveBtn = findViewById(R.id.tenantSaveButtonUpdate);
        cancelBtn = findViewById(R.id.tenantCancelButtonUpdate);
        currencyId = findViewById(R.id.currencyId);
        currencyId2 = findViewById(R.id.currencyId2);


        databaseQueryClass = new DatabaseQueryClass(getApplicationContext());

        tenantModelClass = databaseQueryClass.getTenantById(tenantId);

        edtTenantName.setText(tenantModelClass.getTenantName());
        edtPhoneNumber.setText(tenantModelClass.getTenantphone());
        edtEmail.setText(tenantModelClass.getTenantEmail());
        edtRent.setText(tenantModelClass.getRentAmount());
        edtSecurityDeposit.setText(tenantModelClass.getSecurityDeposit());

        //Lease startdate
        tvLeaseStart.setText(tenantModelClass.getLeaseStart());

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //startDate Picker
        tvLeaseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTenantDetails.this, new DatePickerDialog.OnDateSetListener() {
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

        //lease enddate
        tvLeaseEnd.setText(tenantModelClass.getLeaseEnd());
        //EndDate Picker
        tvLeaseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTenantDetails.this, new DatePickerDialog.OnDateSetListener() {
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

        //rentis paid
        final String strRentispaid = tenantModelClass.getRentIsPaid();
        rentIsPaidList = new ArrayList<>();
        rentIsPaidList.add(strRentispaid);
        List<String> Listrentispaid = Arrays.asList(getResources().getStringArray(R.array.Rent_is_Paid));
        rentIsPaidAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, rentIsPaidList);
        spinRentIsPaid.setAdapter(rentIsPaidAdapter);
        rentIsPaidAdapter.addAll(Listrentispaid);
        spinRentIsPaid.setOnItemSelectedListener(this);

        //Total Occupants
        tvValue.setText("" + tenantModelClass.getTotalOccupants());
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

        //Notes
        tvNotes.setText(tenantModelClass.getNotes());
        tvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateTenantDetails.this, Note.class);
                i.putExtra("CurrentNote", tvNotes.getText().toString());
                startActivityForResult(i, 1);
            }
        });

        //save button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenantnameStr = edtTenantName.getText().toString();
                phoneStr = edtPhoneNumber.getText().toString();
                emailStr = edtEmail.getText().toString();
                rentStr = edtRent.getText().toString();
                securityDepositStr = edtSecurityDeposit.getText().toString();
                leaseStartStr = tvLeaseStart.getText().toString();
                leaseEndStr = tvLeaseEnd.getText().toString();
                notesStr = tvNotes.getText().toString();
                valueStr = tvValue.getText().toString();

                //setting values
                tenantModelClass.setTenantName(tenantnameStr);
                tenantModelClass.setTenantphone(phoneStr);
                tenantModelClass.setTenantEmail(emailStr);
                tenantModelClass.setRentAmount(rentStr);
                tenantModelClass.setSecurityDeposit(securityDepositStr);
                tenantModelClass.setLeaseStart(leaseStartStr);
                tenantModelClass.setLeaseEnd(leaseEndStr);
                tenantModelClass.setNotes(notesStr);
                tenantModelClass.setTotalOccupants(valueStr);
                tenantModelClass.setRentIsPaid(rentIsPaidStr);


                long id = databaseQueryClass.updateTenantInfo(tenantModelClass);
                if (id > 0) {
                    tenantUpdateListener.onTenantInfoUpdated(tenantModelClass, tenantItemPosition);
                    //getDialog().dismiss();
                    //Intent i = new Intent(UpdateTenantDetails.this, TenantFullDetails.class);
                    // startActivity(i);
                    finish();
                }

            }
        });

        //cancel button
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(UpdateTenantDetails.this);
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
                        Toast.makeText(UpdateTenantDetails.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //preferences setting
        //preferences settings
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String upiv = pref.getString("ownerUpiId","");
        String curv = pref.getString("currencyType","₹");
        String rendv = pref.getString("RentalDueday","One Month");

        currencyId.setText(curv);
        currencyId2.setText(curv);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        rentIsPaidStr = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    public void onBackPressed() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
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
                Toast.makeText(UpdateTenantDetails.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}