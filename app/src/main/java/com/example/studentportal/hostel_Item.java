package com.example.studentportal;

public class hostel_Item {
    private String house;
    private String room_no;
    private String gender;
    private String accomodates;
    private String vacancies;
    private String id;

    public hostel_Item(String house, String room_no, String gender, String accomodates, String vacancies){
        this.house = house;
        this.room_no =room_no;
        this.gender =gender;
        this.accomodates = accomodates;
        this.vacancies = vacancies;
    }

    public hostel_Item(){

    }

    public String getHouse(){
        return house;
    }

    public String getGender(){
        return gender;
    }

    public String getRoom_no(){
        return room_no;
    }

    public String getAccomodates(){
        return accomodates;
    }

    public String getVacancies(){
        return vacancies;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id =id;
    }
}
