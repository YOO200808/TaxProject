package com.example.taxpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentCodeActivity extends AppCompatActivity
{
    private Student student;

    private String studentCode;
    private Button button;
    private EditText editText;



    Intent intent = getIntent();

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_code);


        student= Student.getInstance();


        db = FirebaseFirestore.getInstance();

        Context context = this;
        button = (Button) findViewById(R.id.StudentCode_btn);
        editText = findViewById(R.id.StudentCode_edit);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                studentCode=editText.getText().toString();

                Log.d("TAG", studentCode.substring(0,6));

                checkStudentCode(studentCode);
            }
        });
    }

    void checkStudentCode(String studentCode)
    {
        String classCode = studentCode.substring(0,6);

        db.collection("IntegratedManagement")
                .document(classCode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {
                        DocumentSnapshot document_ClassCode = task.getResult();
                        if(document_ClassCode.exists())
                        {
                            student.setClassCode(document_ClassCode.get("classCode").toString());

                            db.collection("IntegratedManagement/"+classCode+"/StudentList")
                                    .document(studentCode)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                                        {
                                            DocumentSnapshot document_StudentCode=task.getResult();

                                            if (document_StudentCode.exists())
                                            {

                                                student.setStudentCode(document_StudentCode.get("StudentCode").toString());
                                                checkEmail(classCode,studentCode);

                                            }
                                            else
                                                Toast.makeText(StudentCodeActivity.this, "올바르지 않은 학생코드입니다. 정확한 학생코드를 입력해주세요.", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }
                        else
                            Toast.makeText(StudentCodeActivity.this, "올바르지 않은 학급코드입니다. 정확한 학급코드를 입력해주세요.", Toast.LENGTH_SHORT).show();



                    }
                });





    }

    void checkEmail(String classCode, String studentCode)
    {
        DocumentReference studentCode_Document =
                db.collection("IntegratedManagement/"+classCode+"/StudentList").document(studentCode);

        studentCode_Document
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {
                        DocumentSnapshot document= task.getResult();

                        if(document.get("Email") == "")
                        {
                            startActivity(new Intent(StudentCodeActivity.this, SignUpActivity.class));
                        }
                        else
                        {
                            Toast.makeText(StudentCodeActivity.this, "해당 학생 코드는 이미 등록되었습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}