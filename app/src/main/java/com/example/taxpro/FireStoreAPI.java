package com.example.taxpro;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FireStoreAPI
{
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private ClassInfo classInfo=ClassInfo.getInstance();
    private Student student= Student.getInstance();
    private List<String> list;


    void openAccount(int attendanceNumber, String ownerOfAccount, boolean savingsOrNot)
    {
        Account account = new Account();
        if (savingsOrNot)
            db.collection("Info/Account/");

    }

    void getListOfSavingProduct()
    {
        db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/Information/")
                .document("Banking")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            list=(ArrayList<String>)task.getResult().get("ListOfSavingProduct");
                            for (String item:list)
                            {
                                classInfo.getListOfSavingProduct().add(item);
                            }

                        }
                        else
                        {

                        }
                    }
                });
    }

    void getSavingProduct(FireStoreGetCallback<Double> callback, String savingProduct)
    {
        db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/Information/Banking/ListOfSavingProduct")
                .document(savingProduct)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {
                        if (task.isSuccessful())
                        {

                            callback.callback(Double.valueOf(task.getResult().get("Rate").toString()));

                        }
                        else
                        {

                        }
                    }
                });

    }

    void getListOfStudentName(FireStoreGetCallback<List<String>> callback)
    {
        db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade())
                .document(student.getClassCode())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {
                        callback.callback((ArrayList<String>)task.getResult().get("StudentList"));
                    }
                });
    }

    void getStudentNumber(Student name)
    {

    }


}
