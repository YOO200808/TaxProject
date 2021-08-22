package com.example.taxpro;

public class Student
{
    private static Student student = new Student();

    private String region;
    private static String school;
    private static String grade;

    private static String classCode;

    private static String StudentCode;
    private static String email;
    private static String name;

    private static String job;






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

    public String getRegion() { return region; }
    public String getSchool() { return school; }
    public String getGrade() { return grade; }

    public String getClassCode() { return classCode; }
    public String getStudentCode() { return StudentCode; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getJob() { return job; }

    public void setRegion(String region) { this.region = region; }
    public void setSchool(String school) { Student.school = school; }
    public void setGrade(String grade) { Student.grade = grade; }

    public void setClassCode(String classCode) { Student.classCode = classCode; }
    public void setStudentCode(String StudentCode) { Student.StudentCode = StudentCode; }
    public void setEmail(String email) { Student.email = email; }
    public void setName(String name) { Student.name = name; }
    public void setJob(String job) { Student.job = job; }



}
