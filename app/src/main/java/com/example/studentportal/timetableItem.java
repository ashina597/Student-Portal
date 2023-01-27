package com.example.studentportal;

public class timetableItem {
    private String id;

    private String courseTitle;
    private String courseCode;
    private String Day;
    private String From;
    private String To;
    private String Lecturer;
    private String Venue;
    private String programme;

    public timetableItem(String courseTitle, String courseCode, String Day, String From, String To, String Lecturer, String Venue, String programme){
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.Day = Day;
        this.From = From;
        this.To = To;
        this.Lecturer = Lecturer;
        this.Venue = Venue;
        this.programme = programme;
    }

    public timetableItem(){

    }

    public String getCourseCode(){
        return courseCode;
    }

    public String getCourseTitle(){
        return courseTitle;
    }

    public String getDay(){
        return Day;
    }

    public String getFrom(){
        return From;
    }

    public String getTo(){
        return To;
    }

    public String getLecturer() {
        return Lecturer;
    }

    public String getVenue() {
        return Venue;
    }

    public String getProgramme() {
        return programme;
    }
}
