package com.example.takemyattendance;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class AddClassActivity extends AppCompatActivity {

    private EditText subName;
    private EditText subCode;
    private EditText batch;
    private EditText sem;
    private EditText stream;
    private EditText section;
    private Button importButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        final Bundle  bundle = new Bundle();
        subName = findViewById(R.id.subName);
        subCode = findViewById(R.id.subCode);
        batch = findViewById(R.id.batch);
        sem = findViewById(R.id.sem);
        stream = findViewById(R.id.stream);
        section = findViewById(R.id.section);
        importButton = findViewById(R.id.importButton);

        subName.addTextChangedListener(importTextWatcher);
        subCode.addTextChangedListener(importTextWatcher);
        batch.addTextChangedListener(importTextWatcher);
        sem.addTextChangedListener(importTextWatcher);
        stream.addTextChangedListener(importTextWatcher);
        section.addTextChangedListener(importTextWatcher);

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new ImportDialogFragment();
                bundle.putString("subName", subName.getText().toString());
                bundle.putString("subCode", subCode.getText().toString());
                bundle.putString("batch", batch.getText().toString());
                bundle.putString("sem", sem.getText().toString());
                bundle.putString("stream", stream.getText().toString());
                bundle.putString("section", section.getText().toString());
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(),"importDialog");
            }
        });

    }

    private TextWatcher importTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String subNameInput = subName.getText().toString().trim();
            String subCodeInput = subCode.getText().toString().trim();
            String batchInput = batch.getText().toString().trim();
            String semInput = sem.getText().toString().trim();
            String sectionInput = section.getText().toString().trim();
            String streamInput = stream.getText().toString().trim();

            importButton.setEnabled(!subNameInput.isEmpty() && !subCodeInput.isEmpty() && !batchInput.isEmpty() && !sectionInput.isEmpty() && !semInput.isEmpty() && !streamInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };




}
