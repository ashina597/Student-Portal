package com.example.studentportal;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class courseItem implements Serializable{
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Exclude
        private String id;

        private String courseTitle;
        private String courseCode;
        private String classGroup;
        private String reg_no;

        public courseItem(String courseTitle, String courseCode, String classGroup, String reg_no) {
            this.courseTitle = courseTitle;
            this.courseCode = courseCode;
            this.classGroup = classGroup;
            this.reg_no = reg_no;
        }

        public courseItem(){

        }

        public String getReg_no(){
            return reg_no;
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

    }

