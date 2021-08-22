package com.example.taxpro;

public class Student
{
    private static Student student = new Student();

    private String region;
    private String school;
    private String grade;

    private String classCode;

    private String StudentCode;
    private String email;
    private String name;

    private String job;






    private Student() { }

    public static Student getInstance()
    {
        return student;
    }

    public void initializeInfo()
    {
        classCode=null;
        StudentCode=null;
        email=null;
        name=null;
        job=null;
    }

    public String getRegion() { return student.region; }
    public String getSchool() { return student.school; }
    public String getGrade() { return student.grade; }

    public String getClassCode() { return student.classCode; }
    public String getStudentCode() { return student.StudentCode; }
    public String getEmail() { return student.email; }
    public String getName() { return student.name; }
    public String getJob() { return student.job; }

    public void setRegion(String region) { student.region = region; }
    public void setSchool(String school) { student.school = school; }
    public void setGrade(String grade) { student.grade = grade; }

    public void setClassCode(String classCode) { student.classCode = classCode; }
    public void setStudentCode(String StudentCode) { student.StudentCode = StudentCode; }
    public void setEmail(String email) { student.email = email; }
    public void setName(String name) { student.name = name; }
    public void setJob(String job) { student.job = job; }



}
