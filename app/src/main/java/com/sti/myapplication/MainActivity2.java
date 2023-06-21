package com.sti.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView studentListView = null;
    private ArrayList<Student> students;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbHelper = new DBHelper(this);
        students = dbHelper.RetrieveUsers();

        studentListView = findViewById(R.id.students_listview);
        StudentAdapter adapter = new StudentAdapter(this, R.layout.student_item_layout, students);
        studentListView.setAdapter(adapter);
        studentListView.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Student stud = dbHelper.retrieveUserByPosition(position);
        if (stud != null) {
            String username = stud.getUsername();
            String password = stud.getPassword();
        }
        else {
            //
        }

        TextView studentIdTextView = view.findViewById(R.id.student_id);
        String studentNum = studentIdTextView.getText().toString();

        String studentPassword = stud.getPassword();

        TextView studentNameTextView = view.findViewById(R.id.student_name);
        String studentName = studentNameTextView.getText().toString();

        Intent intents = new Intent(this, MainActivity.class);
        intents.putExtra("studentNum", studentNum);
        intents.putExtra("studentName", studentName);
        intents.putExtra("studentPassword", studentPassword);
        intents.putExtra("isButtonVisible", false);

        startActivity(intents);
    }
}