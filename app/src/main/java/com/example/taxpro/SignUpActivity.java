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
import com.google.firebase.firestore.QuerySnapshot;

public class SignUpActivity extends AppCompatActivity
{
    private Context context;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private Student student;

    private String email;
    private String password;


    private EditText Email_EditText;
    private EditText PassWord_EditText;
    private Button SignUp_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = this.getApplicationContext();

        Intent intent = getIntent();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        student=Student.getInstance();


        Email_EditText = findViewById(R.id.SignUp_edit_email);
        PassWord_EditText = findViewById(R.id.SignUp_edit_PASSWORD);

        SignUp_Btn = findViewById(R.id.SignUp_btn_SignUp);


        SignUp_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                email = Email_EditText.getText().toString();
                password = PassWord_EditText.getText().toString();

                createAccount(email,password);
            }
        });



    }


    void createAccount(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(context, "회원가입 성공",
                                    Toast.LENGTH_SHORT).show();

                            registerEmail(email);
                            FirebaseUser user = mAuth.getCurrentUser();

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Log.d("TAG", "Email sent.");
                                            }
                                        }
                                    });

                            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                        }
                        else
                        {
                            Toast.makeText(context, "회원가입 실패",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    void registerEmail(String email)
    {
        db.collection("IntegratedManagement/"+student.getClassCode()+"/StudentList/")
                .document(student.getStudentCode())
                .update("Email",email);
    }


}