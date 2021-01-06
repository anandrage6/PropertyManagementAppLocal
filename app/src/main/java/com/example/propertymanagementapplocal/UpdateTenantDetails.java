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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
    String dateStr1 = "01/01/1990", dateStr2 = "31/01/2099" ;
    String date1, date2;
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
                        //tvLeaseStart.setText(date);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date parsedDate = new Date();
                        try {
                            parsedDate = sdf.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat print = new SimpleDateFormat("dd/MM/yyyy");
                        //System.out.println(print.format(parsedDate));
                        dateStr1 = print.format(parsedDate);
                        // tvLeaseStart.setText(dateStr1);
                        //CheckDates(dateStr1, dateStr2);
                        // tvLeaseStart.setText(dateStr1);
                        SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
                        boolean b = false;
                        try {
                            if(dfDate.parse(dateStr1).before(dfDate.parse(dateStr2)))
                            {
                                b = true;//If start date is before end date
                                tvLeaseStart.setText(dateStr1);


                            }
                            else if(dfDate.parse(dateStr1).equals(dfDate.parse(dateStr2)))
                            {
                                b = true;//If two dates are equal
                                tvLeaseStart.setText(dateStr1);
                            }
                            else
                            {
                                b = false; //If start date is after the end date
                                Toast.makeText(getApplicationContext(), "Lease_start should not be greater than Lease_End", Toast.LENGTH_LONG).show();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


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
                        date2 = day + "/" + month + "/" + year;
                        //tvLeaseEnd.setText(date);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date parsedDate2 = new Date();
                        try {
                            parsedDate2 = sdf.parse(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat print = new SimpleDateFormat("dd/MM/yyyy");
                        //System.out.println(print.format(parsedDate));
                        dateStr2 = print.format(parsedDate2);
                        // CheckDates(dateStr1, dateStr2);
                        SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
                        boolean b = false;
                        try {
                            if(dfDate.parse(dateStr1).before(dfDate.parse(dateStr2)))
                            {
                                b = true;//If start date is before end date
                                tvLeaseEnd.setText(dateStr2);


                            }
                            else if(dfDate.parse(dateStr1).equals(dfDate.parse(dateStr2)))
                            {
                                b = true;//If two dates are equal
                                tvLeaseEnd.setText(dateStr2);
                            }
                            else
                            {
                                b = false; //If start date is after the end date
                                Toast.makeText(getApplicationContext(), "Lease_End should not be less than Lease_Start", Toast.LENGTH_LONG).show();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

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
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String upiv = pref.getString("ownerUpiId","");
        String curv = pref.getString("currencyType","₹");
        String rendv = pref.getString("RentalDueday","One Month");

        switch (curv) {
            case "ARS-$":
            case "AUD-$":
            case "BSD-$":
            case "BBD-$":
            case "BMD-$":
            case "BND-$":
            case "CAD-$":
            case "KYD-$":
            case "CLP-$":
            case "COP-$":
            case "SVC-$":
            case "FJD-$":
            case "GYD-$":
            case "HKD-$":
            case "LRD-$":
            case "MXN-$":
            case "NAD-$":
            case "NZD-$":
            case "SGD-$":
            case "SBD-$":
            case "SRD-$":
            case "TVD-$":
            case "USD-$":
                currencyId.setText("$");
                Log.e("dollar value ==> ", "$");

                currencyId2.setText("$");

                break;
            case "FKP-£":
            case "EGP-£":
            case "GIP-£":
            case "GGP-£":
            case "IMP-£":
            case "JEP-£":
            case "LBP-£":
            case "SHP-£":
            case "SYP-£":
            case "GBP-£":
                currencyId.setText("£");
                currencyId2.setText("£");

                break;
            case "ALL-Lek":
                currencyId.setText("Lek");
                currencyId2.setText("Lek");

                break;
            case "؋-AFN":
                currencyId.setText("؋");
                currencyId2.setText("؋");

                break;
            case "AWG-ƒ":
            case "ANG-ƒ":
                currencyId.setText("ƒ");
                currencyId2.setText("ƒ");


                break;
            case "AZN-₼":
                currencyId.setText("₼");
                currencyId2.setText("₼");

                break;
            case "BYR-p.":
                currencyId.setText("p.");
                currencyId2.setText("p.");

                break;
            case "BZD-BZ$":
                currencyId.setText("BZ$");
                currencyId2.setText("BZ$");

                break;
            case "BOB-$b":
                currencyId.setText("$b");
                currencyId2.setText("$b");

                break;
            case "BAM-KM":
                currencyId.setText("KM");
                currencyId2.setText("KM");

                break;
            case "BWP-P":
                currencyId.setText("P");
                currencyId2.setText("P");

                break;
            case "BGN-лв":
            case "KZT-лв":
            case "KGS-лв":
            case "UZS-лв":
                currencyId.setText("лв");
                currencyId2.setText("лв");

                break;
            case "BRL-R$":
                currencyId.setText("R$");
                currencyId2.setText("R$");

                break;
            case "KHR-៛":
                currencyId.setText("៛");
                currencyId2.setText("៛");

                break;
            case "CNY-¥":
            case "JPY-¥":
                currencyId.setText("¥");
                currencyId2.setText("¥");

                break;
            case "CRC-₡":
                currencyId.setText("₡");
                currencyId2.setText("₡");

                break;
            case "HRK-kn":
                currencyId.setText("kn");
                currencyId2.setText("kn");

                break;
            case "CUP-₱":
            case "PHP-₱":
                currencyId.setText("₱");
                currencyId2.setText("₱");

                break;
            case "CZK-Kč":
                currencyId.setText("Kč");
                currencyId2.setText("Kč");

                break;
            case "DKK-kr":
            case "EEK-kr":
            case "ISK-kr":
            case "NOK-kr":
            case "SEK-kr":
                currencyId.setText("kr");
                currencyId2.setText("kr");

                break;
            case "DOP-RD$":
                currencyId.setText("RD$");
                currencyId2.setText("RD$");

                break;
            case "EUR-€":
                currencyId.setText("€");
                currencyId2.setText("€");

                break;
            case "GEL-₾":
                currencyId.setText("₾");
                currencyId2.setText("₾");

                break;
            case "GHC-¢":
                currencyId.setText("¢");
                currencyId2.setText("¢");

                break;
            case "GTQ-Q":
                currencyId.setText("Q");
                currencyId2.setText("Q");

                break;
            case "HNL-L":
                currencyId.setText("L");
                currencyId2.setText("L");

                break;
            case "HUF-Ft":
                currencyId.setText("Ft");
                currencyId2.setText("Ft");

                break;
            case "INR-₹":
                currencyId.setText("₹");
                currencyId2.setText("₹");

                break;
            case "IDR-Rp":
                currencyId.setText("Rp");
                currencyId2.setText("Rp");

                break;
            case "﷼-IRR":
            case "﷼-OMR":
            case "QAR-﷼":
            case "﷼-SAR":
            case "﷼-YER":
                currencyId.setText("﷼");
                currencyId2.setText("﷼");

                break;
            case "ILS-₪":
                currencyId.setText("₪");
                currencyId2.setText("₪");

                break;
            case "JMD-J$":
                currencyId.setText("J$");
                currencyId2.setText("J$");

                break;
            case "KPW-₩":
            case "KRW-₩":
                currencyId.setText("₩");
                currencyId2.setText("₩");

                break;
            case "LAK-₭":
                currencyId.setText("₭");
                currencyId2.setText("₭");

                break;
            case "LVL-Ls":
                currencyId.setText("Ls");
                currencyId2.setText("Ls");

                break;
            case "LTL-Lt":
                currencyId.setText("Lt");
                currencyId2.setText("Lt");

                break;
            case "MKD-ден":
                currencyId.setText("ден");
                currencyId2.setText("ден");

                break;
            case "MYR-RM":
                currencyId.setText("RM");
                currencyId2.setText("RM");

                break;
            case "MUR-₨":
            case "NPR-₨":
            case "PKR-₨":
            case "SCR-₨":
            case "LKR-₨":
                currencyId.setText("₨");
                currencyId2.setText("₨");

                break;
            case "MNT-₮":
                currencyId.setText("₮");
                currencyId2.setText("₮");

                break;
            case "MZN-MT":
                currencyId.setText("MT");
                currencyId2.setText("MT");

                break;
            case "NIO-C$":
                currencyId.setText("C$");
                currencyId2.setText("C$");

                break;
            case "NGN-₦":
                currencyId.setText("₦");
                currencyId2.setText("₦");

                break;
            case "PAB-B/.":
                currencyId.setText("B/.");
                currencyId2.setText("B/.");

                break;
            case "PYG-Gs":
                currencyId.setText("Gs");
                currencyId2.setText("Gs");

                break;
            case "PEN-S/.":
                currencyId.setText("S/.");
                currencyId2.setText("S/.");

                break;

            case "PLN-zł":
                currencyId.setText("zł");
                currencyId2.setText("zł");

                break;
            case "RON-lei":
                currencyId.setText("lei");
                currencyId2.setText("lei");

                break;
            case "RUB-₽":
                currencyId.setText("₽");
                currencyId2.setText("₽");

                break;
            case "RSD-Дин.":
                currencyId.setText("Дин.");
                currencyId2.setText("Дин.");

                break;
            case "SOS-S":
                currencyId.setText("S");
                currencyId2.setText("S");

                break;
            case "ZAR-R":
                currencyId.setText("R");
                currencyId2.setText("R");

                break;
            case "CHF-CHF":
                currencyId.setText("CHF");
                currencyId2.setText("CHF");

                break;
            case "TWD-NT$":
                currencyId.setText("NT$");
                currencyId2.setText("NT$");

                break;
            case "THB-฿":
                currencyId.setText("฿");
                currencyId2.setText("฿");

                break;
            case "TTD-TT$":
                currencyId.setText("TT$");
                currencyId2.setText("TT$");

                break;
            case "TRL-₺":
                currencyId.setText("₺");
                currencyId2.setText("₺");

                break;
            case "UAH-₴":
                currencyId.setText("₴");
                currencyId2.setText("₴");

                break;
            case "UYU-$U":
                currencyId.setText("$U");
                currencyId2.setText("$U");

                break;
            case "VEF-Bs":
                currencyId.setText("Bs");
                currencyId2.setText("Bs");

                break;
            case "VND-₫":
                currencyId.setText("₫");
                currencyId2.setText("₫");

                break;
            case "ZWD-Z$":
                currencyId.setText("Z$");
                currencyId2.setText("Z$");

                break;
            default:
                currencyId.setText(curv);
                currencyId2.setText(curv);

                break;
        }


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