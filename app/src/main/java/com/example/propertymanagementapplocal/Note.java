package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Note extends AppCompatActivity {

    Button saveBtn;
    EditText note;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        note = findViewById(R.id.noteEditTextView);

        //refFlatId = getIntent().getLongExtra(Config.COLUMN_FLATS_ID, -1);

        note.setText(getIntent().getStringExtra("CurrentNote"));
        b = new Bundle();
        saveBtn = findViewById(R.id.noteSaveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mynote = note.getText().toString();
                b.putString("Note", mynote);
                AddTenant addTenant = new AddTenant();
                //AddTenant addTenant = AddTenant.newInstance(refFlatId, this);
                addTenant.setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialogTheme);
                addTenant.setArguments(b);
               // addTenant.show(getSupportFragmentManager(),"add tenant");
                addTenant.show(getSupportFragmentManager(), Config.CREATE_TENANT);

            }

        });
    }
}