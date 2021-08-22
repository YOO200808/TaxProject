package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.*;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity
{

    private Student student;

    Button toScan_Btn;
    Button work_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        student = Student.getInstance();

        Log.d("TAG!!!In Main Screen", student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode());

        toScan_Btn=findViewById(R.id.MainScreen_btn_Scan);
        work_Btn=findViewById(R.id.MainScreen_btn_Work);



        toScan_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainScreenActivity.this, TestActivity_Scan.class));
            }
        });

        work_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainScreenActivity.this, WorkActivity.class));
            }
        });


    }
}