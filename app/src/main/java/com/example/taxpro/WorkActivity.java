package com.example.taxpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Collection;
import java.util.List;

public class WorkActivity extends AppCompatActivity implements View.OnClickListener
{
    FireStoreAPI api;

    Button registerSaving_Btn;
    Button checkSavingList_Btn;
    Button closeSaving_Btn;

    EditText savingAmount_Dialog_Edit;

    ClassInfo classInfo;

    Spinner numberSpinner;
    Spinner nameSpinner;
    ArrayAdapter<String> numberAdapter;
    ArrayAdapter<String> nameAdapter;



    Saving saving;

    String[] arrayForSavingProduct;
    String[] arrayForStudentName;
    Integer[] arrayForStudentNumber;

    int size;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        Intent intent = getIntent();

        api = new FireStoreAPI();
        classInfo=ClassInfo.getInstance();



        System.out.println(classInfo+" In OnCreate, WorkActivity");

        registerSaving_Btn=findViewById(R.id.WorkActivity_Btn_RegisterSaving);
        checkSavingList_Btn=findViewById(R.id.WorkActivity_Btn_CheckSavingList);
        closeSaving_Btn=findViewById(R.id.WorkActivity_Btn_CloseSaving);




        registerSaving_Btn.setOnClickListener(this);
        checkSavingList_Btn.setOnClickListener(this);
        closeSaving_Btn.setOnClickListener(this);

        api.getListOfSavingProduct();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        classInfo.getListOfSavingProduct().clear();
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.WorkActivity_Btn_RegisterSaving:

                saving=new Saving();
                Log.d("!!!",classInfo.getListOfSavingProduct().toString() );
                Log.d("!!!",Integer.toString(classInfo.getListOfSavingProduct().size()));

                selectSavingProductDialog();

                break;
            case R.id.WorkActivity_Btn_CheckSavingList:


                break;
            case R.id.WorkActivity_Btn_CloseSaving:

                break;
        }

    }

    private void selectSavingProductDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkActivity.this);

        size=classInfo.getListOfSavingProduct().size();
        arrayForSavingProduct=classInfo.getListOfSavingProduct().toArray(new String[size]);

        builder.setTitle("예금 상품 선택")
                .setSingleChoiceItems(arrayForSavingProduct, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        saving.setType(arrayForSavingProduct[i]);
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Log.d("!!!", saving.getType());

                        api.getSavingProduct(new FireStoreGetCallback<Double>()
                        {
                            @Override
                            public void callback(Double object)
                            {
                                saving.setRate(object);
                            }
                        },saving.getType());

                        api.getListOfStudentName(new FireStoreGetCallback<List<String>>()
                        {
                            @Override
                            public void callback(List<String> object)
                            {
                                arrayForStudentName= object.toArray(new String[50]);
                            }
                        });

                        savingAmount_Dialog_Edit= findViewById(R.id.EnterSavingAmountDialog_edit_Amount);
                        enterSavingAmountDialog();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(WorkActivity.this, "예금 상품 선택을 취소하였습니다. ", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create();
        builder.show();
    }

    private void enterSavingAmountDialog()
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(WorkActivity.this);
        LayoutInflater inflater = WorkActivity.this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_work_bankteller_enter_saving_amount,null))
                .setView(savingAmount_Dialog_Edit)
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {


                        int amount = Integer.valueOf(savingAmount_Dialog_Edit.getText().toString());
                        saving.setAmount(amount);
                        Log.d("TAG!!!",Double.toString(saving.getRate()));
                        arrayForStudentNumber=Arrays. initializeArray(classInfo.getTheNumberOfStudent());

                        numberSpinner=findViewById(R.id.EnterStudentInfoDialog_spinner_Number);
                        numberAdapter=new ArrayAdapter<Integer>(WorkActivity.this,R.layout.support_simple_spinner_dropdown_item,arrayForStudentNumber);


                        nameSpinner=findViewById(R.id.EnterStudentInfoDialog_spinner_Name);
                        nameAdapter =new ArrayAdapter<String>(WorkActivity.this,R.layout.support_simple_spinner_dropdown_item,arrayForStudentName);
                        nameSpinner.setAdapter(nameAdapter);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

        builder.create();
        builder.show();
    }

    private void enterStudentInfoDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkActivity.this);
        LayoutInflater inflater = WorkActivity.this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_work_bankteller_enter_student_info,null))
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

        builder.create();
        builder.show();
    }

    private void checkDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkActivity.this);
        LayoutInflater inflater = WorkActivity.this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_work_bankteller_enter_student_info,null))
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

        builder.create();
        builder.show();
    }

    private int[] initializeArray(int size)
    {
        int[] array= new int[size];
        for (int i=1;i<=size; i++)
        {
            array[i-1]=i;
        }

        return array;
    }



    /*
     */
}