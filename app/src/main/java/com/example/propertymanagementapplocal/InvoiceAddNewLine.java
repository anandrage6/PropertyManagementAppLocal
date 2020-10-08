package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InvoiceAddNewLine extends AppCompatActivity {

    EditText title,details,amount;
    Button save, cancel;
    String strTitle, strDetails, strAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_add_new_line);

        title = findViewById(R.id.addLineTitleEditText);
        details = findViewById(R.id.addLineDetailsEditText);
        amount = findViewById(R.id.addLineAmountEditText);
        save = findViewById(R.id.addLineSaveButton);
        cancel = findViewById(R.id.addLineCancelButton);


        Bundle extra = getIntent().getExtras();
        strTitle = extra.getString("CurrentTitle");
        strDetails = extra.getString("CurrentDetails");
        strAmount = extra.getString("CurrentAmount");

        title.setText(strTitle);
        details.setText(strDetails);
        amount.setText(strAmount);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.putExtra("title", title.getText().toString());
                in.putExtra("details", details.getText().toString());
                in.putExtra("amount", amount.getText().toString());
                setResult(Activity.RESULT_OK, in);
                finish();
            }
        });


    }
}