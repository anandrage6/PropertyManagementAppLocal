package com.example.propertymanagementapplocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Note extends AppCompatActivity {

    Button saveBtn;
    EditText note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        note = findViewById(R.id.noteEditTextView);
        saveBtn = findViewById(R.id.noteSaveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* FragmentManager fm = getSupportFragmentManager();
                AddTenant at = new AddTenant();
                Bundle b = new Bundle();
                b.putString("Note", note.toString());
                at.setArguments(b);

                */
                Intent intent = new Intent(Note.this, AddTenant.class);
                intent.getStringExtra(note.getText().toString());

            }
        });
    }
}