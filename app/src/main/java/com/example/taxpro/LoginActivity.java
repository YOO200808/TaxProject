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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private Student student;

    private String studentCode;
    private String password;

    private EditText studentCode_EditText;
    private EditText passWord_EditText;

    private Button signUp_Btn;
    private Button login_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        student = Student.getInstance();
        student.initializeInfo();




        mAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        studentCode_EditText = findViewById(R.id.Login_edit_StudentCode);
        passWord_EditText = findViewById(R.id.Login_edit_PASSWORD);


        signUp_Btn = findViewById(R.id.Login_btn_SignUp);
        login_Btn = findViewById(R.id.Login_btn_Login);


        signUp_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this, StudentCodeActivity.class));
            }
        });

        login_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                studentCode=studentCode_EditText.getText().toString();
                password = passWord_EditText.getText().toString();

                checkStudentCode(studentCode,password);

            }
        });

    }

    void signIn (String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                            {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            if(user.isEmailVerified())
                            {
                                if (user != null)
                                {
                                    Log.d("TAG", user.getEmail());
                                    student.setEmail(user.getEmail());
                                    startActivity(new Intent(LoginActivity.this, MainScreenActivity.class));
                                }
                                else
                                {
                                    Log.d("TAG", "회원정보가 없습니다.");
                                }
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "회원님이 등록하신 이메일이 인증되지 않았습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }


                            }
                        else
                            {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT).show();

                            }
                    }
                }
                );
    }

    void checkEmail(String classCode, String studentCode, String password)
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

                        if(document.get("Email") != "")
                        {
                            signIn(document.get("Email").toString(), password);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "해당 학생 코드는 등록되어 있지 않습니다. 먼저 회원가입 해주세요.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    void checkStudentCode(String studentCode, String password)
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
                                                student.setRegion(document_StudentCode.get("Region").toString());
                                                student.setSchool(document_StudentCode.get("School").toString());
                                                student.setGrade(document_StudentCode.get("Grade").toString());
                                                checkEmail(classCode,studentCode,password);

                                            }
                                            else
                                                Toast.makeText(LoginActivity.this, "올바르지 않은 학생코드입니다. 정확한 학생코드를 입력해주세요.", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }
                        else
                            Toast.makeText(LoginActivity.this, "올바르지 않은 학급코드입니다. 정확한 학급코드를 입력해주세요.", Toast.LENGTH_SHORT).show();



                    }
                });





    }







}