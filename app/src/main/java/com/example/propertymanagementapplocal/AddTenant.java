package com.example.propertymanagementapplocal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.orhanobut.logger.Logger;

import java.util.Calendar;

public class AddTenant extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static long FlatId;
    private static long propertyId;
    //private static String address;
    private static TenantCreateListener tenantCreateListener;

    private Toolbar toolbar;
    String note, rentIsPaid;

    EditText edtTenantName, edtPhoneNumber, edtEmail, edtRent, edtSecurityDeposit;
    Spinner spinRentIsPaid;
    TextView tvValue, tvNotes, tvLeaseStart, tvLeaseEnd;
    Button saveBtn, cancelBtn;
    ImageButton incrementBtn, decrementBtn;
    int count = 0;

    //default constructor
    public AddTenant() {

    }

    public static AddTenant newInstance(long refFlatId, TenantCreateListener listener) {
        //Log.d("RefPropertyId : == >", String.valueOf(refPropertyId));
        //propertyId = refPropertyId;
        FlatId = refFlatId;
        tenantCreateListener = listener;
        AddTenant addTenant = new AddTenant();
        addTenant.setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialogTheme);
        return addTenant;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_tenant, container, false);

        //tool bar
        toolbar = view.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("");



        //note = getArguments().getString("Note");

        /*
        //tvNotes.setText(note);
        tvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TenantNotes.class);
                //i.putExtra("cNote", note);
                startActivity(i);
            }
        });

         */


        //id's
        edtTenantName = view.findViewById(R.id.tenantNameEditText);
        edtPhoneNumber = view.findViewById(R.id.tenentPhoneEditText);
        edtEmail = view.findViewById(R.id.tenantEmailEditText);
        tvLeaseStart = view.findViewById(R.id.tenantLeaseStart);
        tvLeaseEnd = view.findViewById(R.id.tenantLeaseEnd);
        edtRent = view.findViewById(R.id.tenentRentEditText);
        edtSecurityDeposit = view.findViewById(R.id.tenantSecurityDepositEditText);
        spinRentIsPaid = view.findViewById(R.id.tenantRentSpin);
        tvValue = view.findViewById(R.id.tenantTotalOccupantsValueTextView);
        tvNotes = view.findViewById(R.id.tenantNotesTextView);
        incrementBtn = view.findViewById(R.id.tenantIncrementBtn);
        decrementBtn = view.findViewById(R.id.tenantDecrementBtn);
        saveBtn = view.findViewById(R.id.tenantSaveButton);
        cancelBtn = view.findViewById(R.id.tenantCancelButton);

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

        ArrayAdapter<CharSequence> rentAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Rent_is_Paid, android.R.layout.simple_spinner_item);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
        return view;
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

        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

        //Log.d("propertyId", String.valueOf(propertyId));
        //Log.d("FlatId", String.valueOf(FlatId));

        long id = databaseQueryClass.insertTenant(tenant, FlatId);
        Log.e("Result tenant id : ==> ", String.valueOf(id));


        if (id > 0) {
            tenant.setTenantId(id);
            tenantCreateListener.onTenantCreated(tenant);
            getDialog().dismiss();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        rentIsPaid = "" + adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }

    }
}
