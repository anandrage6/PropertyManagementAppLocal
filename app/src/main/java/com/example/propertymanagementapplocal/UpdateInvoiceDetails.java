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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateInvoiceDetails extends AppCompatActivity {

    private static long invoiceId;

    private static int invoiceItemPosition;

    private static InvoiceUpdateListener invoiceUpdateListener;

    private InvoiceModelClass mInvoiceModelClass;

    private DatabaseQueryClass databaseQueryClass;


    EditText rent, waterBill, electricityBill, maintananceBill;
    TextView amount, details, title, paymentdue, invoiceIssued, note, addLine, currencyId, currencyId2, currencyId3, currencyId4;
    Button save, cancel;
    LinearLayout linearLayout;

    String strtitle, strDetails, strInvoiceIssued, amountStr, strPaymentdue, strNotes;

    String Title, Amount, Details;

    double strAmount;
    double strRent, strWaterBill, strElectricityBill, strMaintananceBill;

    //constructor
    public UpdateInvoiceDetails() {

    }

    //new Instance

    public static UpdateInvoiceDetails newInstance(long id, int position, InvoiceUpdateListener listener) {

        invoiceId = id;
        invoiceItemPosition = position;
        invoiceUpdateListener = listener;

        UpdateInvoiceDetails updateInvoice = new UpdateInvoiceDetails();
        return updateInvoice;

    }


    //notes activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                //Bundle b = getIntent().getExtras();
                String strnote = data.getStringExtra("note");

                //Log.d("StrNote Result : ", strnote);
                note.setText(strnote);

            }
        }


        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Title = data.getStringExtra("title");
                Details = data.getStringExtra("details");
                Amount = data.getStringExtra("amount");

                amount.setText(Amount);
                details.setText(Details);
                title.setText(Title);

                if (!Amount.isEmpty() && !Details.isEmpty() && !Title.isEmpty()) {
                    linearLayout.setVisibility(linearLayout.VISIBLE);
                } else {
                    linearLayout.setVisibility(linearLayout.GONE);

                }

            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_invoice_details);

        title = findViewById(R.id.invoicetitleTextViewUpdate);
        details = findViewById(R.id.invoiceDetailsTextViewUpdate);
        amount = findViewById(R.id.invoiceAmountTextViewUpdate);
        rent = findViewById(R.id.invoiceRentEditTextUpdate);
        invoiceIssued = findViewById(R.id.invoiceIssuedSelectDateUpdate);
        paymentdue = findViewById(R.id.invoicePaymentdueSelectDateUpdate);
        note = findViewById(R.id.invoiceNoteTextViewUpdate);
        save = findViewById(R.id.invoiceSaveButtonUpdate);
        cancel = findViewById(R.id.invoiceCancelButtonUpdate);
        linearLayout = findViewById(R.id.hidelayout);
        addLine = findViewById(R.id.addlineTextviewUpdate);
        waterBill = findViewById(R.id.invoiceWaterEditTextUpdate);
        electricityBill = findViewById(R.id.invoiceElectricityEditTextUpdate);
        maintananceBill = findViewById(R.id.invoiceMaintananceEditTextUpdate);
        currencyId = findViewById(R.id.currencyId);
        currencyId2 = findViewById(R.id.currencyId2);
        currencyId3 = findViewById(R.id.currencyId3);
        currencyId4 = findViewById(R.id.currencyId4);


        databaseQueryClass = new DatabaseQueryClass(getApplicationContext());

        mInvoiceModelClass = databaseQueryClass.getInvoiceById(invoiceId);

        title.setText(mInvoiceModelClass.getTitle());
        details.setText(mInvoiceModelClass.getDetails());
        amount.setText(mInvoiceModelClass.getAmount());
        rent.setText(mInvoiceModelClass.getRent());
        invoiceIssued.setText(mInvoiceModelClass.getInvoiceIssued());
        paymentdue.setText(mInvoiceModelClass.getPaymentDue());
        note.setText(mInvoiceModelClass.getNotes());
        waterBill.setText(mInvoiceModelClass.getWaterBill());
        electricityBill.setText(mInvoiceModelClass.getElectricityBill());
        maintananceBill.setText(mInvoiceModelClass.getMaintenanceCharges());


        //notes
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateInvoiceDetails.this, InvoiceNote.class);
                i.putExtra("CurrentNote", note.getText().toString());
                startActivityForResult(i, 1);

            }
        });

        //add new line
        addLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateInvoiceDetails.this, InvoiceAddNewLine.class);
                i.putExtra("CurrentTitle", title.getText().toString());
                i.putExtra("CurrentDetails", details.getText().toString());
                i.putExtra("CurrentAmount", amount.getText().toString());
                startActivityForResult(i, 2);

            }
        });

        //select dates

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        //startDate Picker
        invoiceIssued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateInvoiceDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        invoiceIssued.setText(date);


                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //EndDate Picker
        paymentdue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateInvoiceDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        paymentdue.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //hidelayout addnewine
        if (!title.getText().toString().isEmpty() && !details.getText().toString().isEmpty() && !amount.getText().toString().isEmpty()) {
            linearLayout.setVisibility(linearLayout.VISIBLE);
        } else {
            linearLayout.setVisibility(linearLayout.GONE);
        }

        //update invoice

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strtitle = title.getText().toString();
                strDetails = details.getText().toString();

                try {
                    strAmount = Double.parseDouble(amount.getText().toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }

                amountStr = amount.getText().toString();
                strRent = Double.parseDouble(rent.getText().toString());
                strInvoiceIssued = invoiceIssued.getText().toString();
                strPaymentdue = paymentdue.getText().toString();
                strNotes = note.getText().toString();
                try {
                    strWaterBill = Double.parseDouble(waterBill.getText().toString());
                }catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    strElectricityBill = Double.parseDouble(electricityBill.getText().toString());
                }catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    strMaintananceBill = Double.parseDouble(maintananceBill.getText().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                mInvoiceModelClass.setTitle(strtitle);
                mInvoiceModelClass.setDetails(strDetails);
                mInvoiceModelClass.setAmount(amountStr);
                mInvoiceModelClass.setRent(String.valueOf(strRent + strAmount + strWaterBill + strElectricityBill + strMaintananceBill));
                mInvoiceModelClass.setInvoiceIssued(strInvoiceIssued);
                mInvoiceModelClass.setPaymentDue(strPaymentdue);
                mInvoiceModelClass.setNotes(strNotes);
                mInvoiceModelClass.setWaterBill(String.valueOf(strWaterBill));
                mInvoiceModelClass.setElectricityBill(String.valueOf(strElectricityBill));
                mInvoiceModelClass.setMaintenanceCharges(String.valueOf(strMaintananceBill));

                long id = databaseQueryClass.updateInvoiceInfo(mInvoiceModelClass);
                if (id > 0) {
                    invoiceUpdateListener.onInvoiceInfoUpdated(mInvoiceModelClass, invoiceItemPosition);
                    //getDialog().dismiss();
                    //Intent i = new Intent(UpdateFlat.this, PropertyDetails.class);
                    //startActivity(i);
                    finish();
                }
            }
        });


        //cancel button

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(UpdateInvoiceDetails.this);
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
                        Toast.makeText(UpdateInvoiceDetails.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //preferences settings
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
                currencyId2.setText("$");
                currencyId3.setText("$");
                currencyId4.setText("$");

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
                currencyId3.setText("£");
                currencyId4.setText("£");

                break;
            case "ALL-Lek":
                currencyId.setText("Lek");
                currencyId2.setText("Lek");
                currencyId3.setText("Lek");
                currencyId4.setText("Lek");

                break;
            case "؋-AFN":
                currencyId.setText("؋");
                currencyId2.setText("؋");
                currencyId3.setText("؋");
                currencyId4.setText("؋");

                break;
            case "AWG-ƒ":
            case "ANG-ƒ":
                currencyId.setText("ƒ");
                currencyId2.setText("ƒ");
                currencyId3.setText("ƒ");
                currencyId4.setText("ƒ");


                break;
            case "AZN-₼":
                currencyId.setText("₼");
                currencyId2.setText("₼");
                currencyId3.setText("₼");
                currencyId4.setText("₼");

                break;
            case "BYR-p.":
                currencyId.setText("p.");
                currencyId2.setText("p.");
                currencyId3.setText("p.");
                currencyId4.setText("p.");

                break;
            case "BZD-BZ$":
                currencyId.setText("BZ$");
                currencyId2.setText("BZ$");
                currencyId3.setText("BZ$");
                currencyId4.setText("BZ$");

                break;
            case "BOB-$b":
                currencyId.setText("$b");
                currencyId2.setText("$b");
                currencyId3.setText("$b");
                currencyId4.setText("$b");

                break;
            case "BAM-KM":
                currencyId.setText("KM");
                currencyId2.setText("KM");
                currencyId3.setText("KM");
                currencyId4.setText("KM");

                break;
            case "BWP-P":
                currencyId.setText("P");
                currencyId2.setText("P");
                currencyId3.setText("P");
                currencyId4.setText("P");

                break;
            case "BGN-лв":
            case "KZT-лв":
            case "KGS-лв":
            case "UZS-лв":
                currencyId.setText("лв");
                currencyId2.setText("лв");
                currencyId3.setText("лв");
                currencyId4.setText("лв");

                break;
            case "BRL-R$":
                currencyId.setText("R$");
                currencyId2.setText("R$");
                currencyId3.setText("R$");
                currencyId4.setText("R$");

                break;
            case "KHR-៛":
                currencyId.setText("៛");
                currencyId2.setText("៛");
                currencyId3.setText("៛");
                currencyId4.setText("៛");

                break;
            case "CNY-¥":
            case "JPY-¥":
                currencyId.setText("¥");
                currencyId2.setText("¥");
                currencyId3.setText("¥");
                currencyId4.setText("¥");

                break;
            case "CRC-₡":
                currencyId.setText("₡");
                currencyId2.setText("₡");
                currencyId3.setText("₡");
                currencyId4.setText("₡");

                break;
            case "HRK-kn":
                currencyId.setText("kn");
                currencyId2.setText("kn");
                currencyId3.setText("kn");
                currencyId4.setText("kn");

                break;
            case "CUP-₱":
            case "PHP-₱":
                currencyId.setText("₱");
                currencyId2.setText("₱");
                currencyId3.setText("₱");
                currencyId4.setText("₱");


                break;
            case "CZK-Kč":
                currencyId.setText("Kč");
                currencyId2.setText("Kč");
                currencyId3.setText("Kč");
                currencyId4.setText("Kč");

                break;
            case "DKK-kr":
            case "EEK-kr":
            case "ISK-kr":
            case "NOK-kr":
            case "SEK-kr":
                currencyId.setText("kr");
                currencyId2.setText("kr");
                currencyId3.setText("kr");
                currencyId4.setText("kr");

                break;
            case "DOP-RD$":
                currencyId.setText("RD$");
                currencyId2.setText("RD$");
                currencyId3.setText("RD$");
                currencyId4.setText("RD$");

                break;
            case "EUR-€":
                currencyId.setText("€");
                currencyId2.setText("€");
                currencyId3.setText("€");
                currencyId4.setText("€");

                break;
            case "GEL-₾":
                currencyId.setText("₾");
                currencyId2.setText("₾");
                currencyId3.setText("₾");
                currencyId4.setText("₾");

                break;
            case "GHC-¢":
                currencyId.setText("¢");
                currencyId2.setText("¢");
                currencyId3.setText("¢");
                currencyId4.setText("¢");

                break;
            case "GTQ-Q":
                currencyId.setText("Q");
                currencyId2.setText("Q");
                currencyId3.setText("Q");
                currencyId4.setText("Q");

                break;
            case "HNL-L":
                currencyId.setText("L");
                currencyId2.setText("L");
                currencyId3.setText("L");
                currencyId4.setText("L");

                break;
            case "HUF-Ft":
                currencyId.setText("Ft");
                currencyId2.setText("Ft");
                currencyId3.setText("Ft");
                currencyId4.setText("Ft");

                break;
            case "INR-₹":
                currencyId.setText("₹");
                currencyId2.setText("₹");
                currencyId3.setText("₹");
                currencyId4.setText("₹");

                break;
            case "IDR-Rp":
                currencyId.setText("Rp");
                currencyId2.setText("Rp");
                currencyId3.setText("Rp");
                currencyId4.setText("Rp");

                break;
            case "﷼-IRR":
            case "﷼-OMR":
            case "QAR-﷼":
            case "﷼-SAR":
            case "﷼-YER":
                currencyId.setText("﷼");
                currencyId2.setText("﷼");
                currencyId3.setText("﷼");
                currencyId4.setText("﷼");

                break;
            case "ILS-₪":
                currencyId.setText("₪");
                currencyId2.setText("₪");
                currencyId3.setText("₪");
                currencyId4.setText("₪");

                break;
            case "JMD-J$":
                currencyId.setText("J$");
                currencyId2.setText("J$");
                currencyId3.setText("J$");
                currencyId4.setText("J$");

                break;
            case "KPW-₩":
            case "KRW-₩":
                currencyId.setText("₩");
                currencyId2.setText("₩");
                currencyId3.setText("₩");
                currencyId4.setText("₩");

                break;
            case "LAK-₭":
                currencyId.setText("₭");
                currencyId2.setText("₭");
                currencyId3.setText("₭");
                currencyId4.setText("₭");

                break;
            case "LVL-Ls":
                currencyId.setText("Ls");
                currencyId2.setText("Ls");
                currencyId3.setText("Ls");
                currencyId4.setText("Ls");
                break;
            case "LTL-Lt":
                currencyId.setText("Lt");
                currencyId2.setText("Lt");
                currencyId3.setText("Lt");
                currencyId4.setText("Lt");

                break;
            case "MKD-ден":
                currencyId.setText("ден");
                currencyId2.setText("ден");
                currencyId3.setText("ден");
                currencyId4.setText("ден");

                break;
            case "MYR-RM":
                currencyId.setText("RM");
                currencyId2.setText("RM");
                currencyId3.setText("RM");
                currencyId4.setText("RM");

                break;
            case "MUR-₨":
            case "NPR-₨":
            case "PKR-₨":
            case "SCR-₨":
            case "LKR-₨":
                currencyId.setText("₨");
                currencyId2.setText("₨");
                currencyId3.setText("₨");
                currencyId4.setText("₨");

                break;
            case "MNT-₮":
                currencyId.setText("₮");
                currencyId2.setText("₮");
                currencyId3.setText("₮");
                currencyId4.setText("₮");

                break;
            case "MZN-MT":
                currencyId.setText("MT");
                currencyId2.setText("MT");
                currencyId3.setText("MT");
                currencyId4.setText("MT");

                break;
            case "NIO-C$":
                currencyId.setText("C$");
                currencyId2.setText("C$");
                currencyId3.setText("C$");
                currencyId4.setText("C$");

                break;
            case "NGN-₦":
                currencyId.setText("₦");
                currencyId2.setText("₦");
                currencyId3.setText("₦");
                currencyId4.setText("₦");
                break;
            case "PAB-B/.":
                currencyId.setText("B/.");
                currencyId2.setText("B/.");
                currencyId3.setText("B/.");
                currencyId4.setText("B/.");

                break;
            case "PYG-Gs":
                currencyId.setText("Gs");
                currencyId2.setText("Gs");
                currencyId3.setText("Gs");
                currencyId4.setText("Gs");

                break;
            case "PEN-S/.":
                currencyId.setText("S/.");
                currencyId2.setText("S/.");
                currencyId3.setText("S/.");
                currencyId4.setText("S/.");

                break;

            case "PLN-zł":
                currencyId.setText("zł");
                currencyId2.setText("zł");
                currencyId3.setText("zł");
                currencyId4.setText("zł");

                break;
            case "RON-lei":
                currencyId.setText("lei");
                currencyId2.setText("lei");
                currencyId3.setText("lei");
                currencyId4.setText("lei");

                break;
            case "RUB-₽":
                currencyId.setText("₽");
                currencyId2.setText("₽");
                currencyId3.setText("₽");
                currencyId4.setText("₽");

                break;
            case "RSD-Дин.":
                currencyId.setText("Дин.");
                currencyId2.setText("Дин.");
                currencyId3.setText("Дин.");
                currencyId4.setText("Дин.");

                break;
            case "SOS-S":
                currencyId.setText("S");
                currencyId2.setText("S");
                currencyId3.setText("S");
                currencyId4.setText("S");

                break;
            case "ZAR-R":
                currencyId.setText("R");
                currencyId2.setText("R");
                currencyId3.setText("R");
                currencyId4.setText("R");

                break;
            case "CHF-CHF":
                currencyId.setText("CHF");
                currencyId2.setText("CHF");
                currencyId3.setText("CHF");
                currencyId4.setText("CHF");

                break;
            case "TWD-NT$":
                currencyId.setText("NT$");
                currencyId2.setText("NT$");
                currencyId3.setText("NT$");
                currencyId4.setText("NT$");

                break;
            case "THB-฿":
                currencyId.setText("฿");
                currencyId2.setText("฿");
                currencyId3.setText("฿");
                currencyId4.setText("฿");

                break;
            case "TTD-TT$":
                currencyId.setText("TT$");
                currencyId2.setText("TT$");
                currencyId3.setText("TT$");
                currencyId4.setText("TT$");

                break;
            case "TRL-₺":
                currencyId.setText("₺");
                currencyId2.setText("₺");
                currencyId3.setText("₺");
                currencyId4.setText("₺");

                break;
            case "UAH-₴":
                currencyId.setText("₴");
                currencyId2.setText("₴");
                currencyId3.setText("₴");
                currencyId4.setText("₴");

                break;
            case "UYU-$U":
                currencyId.setText("$U");
                currencyId2.setText("$U");
                currencyId3.setText("$U");
                currencyId4.setText("$U");

                break;
            case "VEF-Bs":
                currencyId.setText("Bs");
                currencyId2.setText("Bs");
                currencyId3.setText("Bs");
                currencyId4.setText("Bs");

                break;
            case "VND-₫":
                currencyId.setText("₫");
                currencyId2.setText("₫");
                currencyId3.setText("₫");
                currencyId4.setText("₫");

                break;
            case "ZWD-Z$":
                currencyId.setText("Z$");
                currencyId2.setText("Z$");
                currencyId3.setText("Z$");
                currencyId4.setText("Z$");

                break;

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
                Toast.makeText(UpdateInvoiceDetails.this, "You Clicked on Cancel", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}