package com.sti.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper dbHelper;
    Button submit, cancel, next, update;
    TextView label, err_username, err_password;
    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        submit = findViewById(R.id.btn_submit);
        next = findViewById(R.id.btn_next);
        //cancel = findViewById(R.id.btn_cancel);
        //update = findViewById(R.id.update_button);

        label = findViewById(R.id.tv_label);
        err_username = findViewById(R.id.tv_username);
        err_password = findViewById(R.id.tv_password);

        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

        next.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_next) {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }

        else if (id == R.id.btn_submit) {
            Log.d("SubmitButton", "Submitted");
            boolean isValid = true;
            String in_username = username.getText().toString();
            String in_password = password.getText().toString();

            Log.d("SubmitButton", "LENGTH: " + in_username.length());

            err_username.setVisibility(View.INVISIBLE);
            err_password.setVisibility(View.INVISIBLE);

            if (in_username.length() < 1) {
                err_username.setVisibility(View.VISIBLE);
                isValid = false;
            }

            if (in_password.length() < 1) {
                err_password.setVisibility(View.VISIBLE);
                isValid = false;
            }

            if (isValid) {
                Student student = new Student(in_username, in_password);

                if (getIntent().getBooleanExtra("isButtonVisible", true)) {
                    long check = dbHelper.InsertUser(student);
                    Log.d("Insert_User", check + "");
                    if (check > 0) {
                        username.setText("");
                        password.setText("");
                    }
                } else {

                    String studentNumID = getIntent().getStringExtra("studentNum");
                    String studUsername = username.getText().toString();
                    String studPassword = password.getText().toString();

                    dbHelper.UpdateUser(studentNumID, studUsername, studPassword);
                    dbHelper.close();

                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isButtonVisible = getIntent().getBooleanExtra("isButtonVisible", true);

        Button cancel = findViewById(R.id.btn_cancel);
        Intent intent = getIntent();

        String un = intent.getStringExtra("studentName");
        String pw = intent.getStringExtra("studentPassword");

        username.setText(un);
        password.setText(pw);

        if (!isButtonVisible) {
            cancel.setVisibility(View.VISIBLE);
            submit.setText("Update");
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}