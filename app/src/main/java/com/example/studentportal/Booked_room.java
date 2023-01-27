package com.example.studentportal;

public class Booked_room {
    private String reg_no;
    private String house;
    private String room;
    private String id;

    public Booked_room(String reg_no, String house, String room){
        this.reg_no = reg_no;
        this.house = house;
        this.room = room;
    }

    public Booked_room() {

    }

    public String getReg_no(){
        return  reg_no;
    }

    public String getHouse(){
        return house;
    }

    public String getRoom(){
        return room;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id =id;
    }
}
