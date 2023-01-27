package com.example.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Book_hostel extends AppCompatActivity {

    TextView message;
    private ArrayList<hostel_Item> roomsArrayList;
    private ArrayList<Booked_room> bookHostelArrayList;
    FirebaseFirestore db;
    LinearLayout linearLayout;
    int accomodates, vacancies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hostel);
        Student student = (Student) getIntent().getSerializableExtra("student");

        linearLayout = (LinearLayout) findViewById(R.id.layout);
        String reg_no = student.getReg_no();
        String gender = student.getGender();
        db = FirebaseFirestore.getInstance();
        roomsArrayList = new ArrayList<>();
        bookHostelArrayList = new ArrayList<>();

        RadioGroup radioGroup;
        radioGroup = new RadioGroup(Book_hostel.this);

        db.collection("Hostel").whereEqualTo("gender", gender)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            TextView label;
                            label = new TextView(Book_hostel.this);
                            label.setText("Pick a room");
                            linearLayout.addView(label);

                            for (DocumentSnapshot d : list) {
                                hostel_Item room = d.toObject(hostel_Item.class);
                                roomsArrayList.add(room);

                                if (Integer.parseInt(room.getVacancies()) > 0) {
                                    RadioButton radioButton;
                                    radioButton = new RadioButton(Book_hostel.this);

                                    radioButton.setText(room.getHouse() + " " + room.getRoom_no());
                                    radioGroup.addView(radioButton);
                                }

                            }
                            radioGroup.setOrientation(RadioGroup.VERTICAL);
                            linearLayout.addView(radioGroup);
                        } else {

                            Toast.makeText(Book_hostel.this, "There are no rooms available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Book_hostel.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });


        RadioGroup rooms;
        rooms = new RadioGroup(Book_hostel.this);
        Button submit;
        submit = new Button(Book_hostel.this);
        linearLayout.removeView(submit);
        submit.setBackgroundColor(Color.rgb(100, 0, 0));
        submit.setTextColor(Color.WHITE);
        submit.setText("Book a Room");
        submit.setVisibility(View.INVISIBLE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton checked = (RadioButton) findViewById(checkedId);
                String check = checked.getText().toString();
                String booked[] = check.split(" ");
                String House = booked[0];
                String roomBooked = booked[1];
                submit.setVisibility(View.VISIBLE);
                rooms.removeAllViews();
                linearLayout.removeView(rooms);
                linearLayout.removeView(submit);
                linearLayout.addView(submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Booked_room booked_room = new Booked_room(reg_no, House, roomBooked);
                        bookRoom(booked_room);
                        db.collection("Hostel").whereEqualTo("house", House).whereEqualTo("room_no", roomBooked)
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (!queryDocumentSnapshots.isEmpty()) {
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot d : list) {
                                                hostel_Item hostel;
                                                hostel = d.toObject(hostel_Item.class);
                                                hostel.setId(d.getId());

                                                int vacancy = Integer.valueOf(hostel.getVacancies());
                                                vacancy = vacancy - 1;

                                                hostel_Item newHostel = new hostel_Item(House, roomBooked, hostel.getGender(), hostel.getAccomodates(), String.valueOf(vacancy));
                                                db.collection("Hostel").document(hostel.getId()).set(newHostel);
                                            }
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Book_hostel.this, "Failed to update the hostel database", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        Timer t = new Timer(false);
                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Intent i = new Intent(Book_hostel.this, hostel.class);
                                        i.putExtra("student", student);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    }
                                });
                            }
                        }, 5000);
                    }
                });

            }
        });


    }

    public void bookRoom(Booked_room bookedRoom) {
        linearLayout = (LinearLayout) findViewById(R.id.layout);
        db.collection("Fees").whereEqualTo("reg_no", bookedRoom.getReg_no()).whereEqualTo("Description", "Accommodation")
                .whereEqualTo("Balance", "0").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            db.collection("Booked_rooms").whereEqualTo("reg_no", bookedRoom.getReg_no()).get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if (!queryDocumentSnapshots.isEmpty()) {
                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                for (DocumentSnapshot d : list) {
                                                    Booked_room booked_room;
                                                    booked_room = d.toObject(Booked_room.class);
                                                    bookHostelArrayList.add(booked_room);
                                                    booked_room.setId(d.getId());

                                                    db.collection("Booked_rooms").document(booked_room.getId()).set(bookedRoom)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    TextView message;
                                                                    message = new TextView(Book_hostel.this);
                                                                    message.setText("You have Successfully changed your room");
                                                                    message.setTextColor(Color.BLACK);
                                                                    message.setPadding(20, 20, 20, 20);
                                                                    linearLayout.addView(message);

                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    TextView message;
                                                                    message = new TextView(Book_hostel.this);
                                                                    message.setText("Failed to change your room, please try again");
                                                                    message.setTextColor(Color.BLACK);
                                                                    message.setPadding(20, 20, 20, 20);
                                                                    linearLayout.addView(message);
                                                                }
                                                            });
                                                }
                                            } else {
                                                db.collection("Booked_rooms").add(bookedRoom).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        TextView message;
                                                        message = new TextView(Book_hostel.this);
                                                        message.setText("You have booked your room successfully");
                                                        message.setTextColor(Color.BLACK);
                                                        message.setPadding(20, 20, 20, 20);
                                                        linearLayout.addView(message);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        TextView message;
                                                        message = new TextView(Book_hostel.this);
                                                        message.setText("Failed to book your room, please try again");
                                                        message.setTextColor(Color.BLACK);
                                                        message.setPadding(20, 20, 20, 20);
                                                        linearLayout.addView(message);
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }else{
                            TextView message;
                            message = new TextView(Book_hostel.this);
                            message.setText("Please pay the Accommodation fees first");
                            message.setTextColor(Color.BLACK);
                            message.setPadding(20, 20, 20, 20);
                            linearLayout.addView(message);
                        }
                        }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        TextView message;
                        message = new TextView(Book_hostel.this);
                        message.setText("Failed to load your fees statement please try again");
                        message.setTextColor(Color.BLACK);
                        message.setPadding(20, 20, 20, 20);
                        linearLayout.addView(message);
                    }
                });
    }
}