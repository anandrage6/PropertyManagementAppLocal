package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
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
    String notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        note = findViewById(R.id.noteEditTextView);
        saveBtn = findViewById(R.id.noteSaveButton);

        Bundle extra = getIntent().getExtras();
         notes = extra.getString("CurrentNote");
         note.setText(notes);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent();
                in.putExtra("note", note.getText().toString());
                setResult(Activity.RESULT_OK, in);
                finish();
            }
        });
    }
}