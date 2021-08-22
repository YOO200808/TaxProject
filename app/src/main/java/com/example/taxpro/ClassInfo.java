package com.example.taxpro;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo
{
    private static ClassInfo classInfo = new ClassInfo();
    private int theNumberOfStudent=22;

    private static List<String> listOfSavingProduct = new ArrayList<String>();
    private ClassInfo() { }

    public static ClassInfo getInstance() { return classInfo; }

    public  List<String> getListOfSavingProduct() { return listOfSavingProduct;}

    public int getTheNumberOfStudent() { return theNumberOfStudent; }

    public void setTheNumberOfStudent(int theNumberOfStudent) { this.theNumberOfStudent = theNumberOfStudent; }
}
