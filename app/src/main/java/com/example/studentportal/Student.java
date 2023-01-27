package com.example.studentportal;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Student implements Serializable {

    private String id;
    private String first_name;
    private String surname;
    private String middle_name;
    private String reg_no;
    private String phone_no;
    private String email;
    private String gender;
    private String nationality;
    private String nationality_no;
    private String nationality_cert;
    private String dob;
    private String home_county;
    private String school;
    private String programme;
    private String parent_guardian;
    private String parent_guardian_phone_no;
    private String parent_guardian_email;

    public Student ( String id, String first_name, String middle_name, String surname, String reg_no, String phone_no,
                   String email, String gender, String nationality, String nationality_no,String nationality_cert, String dob,
                   String home_county, String school, String programme, String parent_guardian,
                   String parent_guardian_phone_no, String parent_guardian_email) {
        this.id = id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.surname = surname;
        this.reg_no = reg_no;
        this.phone_no = phone_no;
        this.email = email;
        this.gender = gender;
        this.nationality = nationality;
        this.nationality_no = nationality_no;
        this.nationality_cert = nationality_cert;
        this.dob = dob;
        this.home_county = home_county;
        this.school = school;
        this.programme = programme;
        this.parent_guardian = parent_guardian;
        this.parent_guardian_phone_no = parent_guardian_phone_no;
        this.parent_guardian_email = parent_guardian_email;
    }

    public Student(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public String getSurname() {
        return surname;
    }

    public String getname(){
        return first_name+" "+middle_name+" "+surname ;
    }

    public String getReg_no() {
        return reg_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getNationality() {
        return nationality;
    }

    public String getNationality_no() {
        return nationality_no;
    }

    public String getNationality_cert() {
        return nationality_cert;
    }

    public String getDOB() {
        return dob;
    }

    public String getHome_county() {
        return home_county;
    }

    public String getSchool() {
        return school;
    }

    public String getProgramme() {
        return programme;
    }

    public String getParent_guardian() {
        return parent_guardian;
    }

    public String getParent_guardian_email() {
        return parent_guardian_email;
    }

    public String getParent_guardian_phone_no() {
        return parent_guardian_phone_no;
    }
}
