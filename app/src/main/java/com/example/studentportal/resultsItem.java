package com.example.studentportal;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class resultsItem implements Serializable {

    // getter method for our id
    public String getId() {
        return id;
    }

    // setter method for our id
    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    private String id;

    private String courseTitle;
    private String courseCode;
    private String classGroup;
    private String Reg_no;
    private String marks;
    private String grade;
    public resultsItem(String courseTitle, String courseCode, String classGroup, String Reg_no, String marks, String grade) {
        this.courseTitle = courseTitle;
        this.courseCode = courseCode;
        this.classGroup = classGroup;
        this.Reg_no = Reg_no;
        this.marks = marks;
        this.grade = grade;
    }

    public resultsItem(){

    }

    public String getReg_no(){
        return Reg_no;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public String getMarks() {
        return marks;
    }

    public String getGrade(){
        return grade;
    }

}
