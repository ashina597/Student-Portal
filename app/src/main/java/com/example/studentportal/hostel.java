package com.example.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class hostel extends AppCompatActivity {
    private FirebaseFirestore db;
    private ArrayList<Booked_room> roomArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        Student student = (Student) getIntent().getSerializableExtra("student");
        String reg_no = student.getReg_no();
        roomArrayList = new ArrayList<>();
        LinearLayout linearLayout;
        linearLayout =new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(50,100,50,50);
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        tableLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        tableLayout.setPadding(20, 20, 20, 20);


        db.collection("Booked_rooms").whereEqualTo("reg_no", reg_no).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            TableRow tableRow;
                            tableRow = new TableRow(getApplicationContext());
                            tableRow.setBackgroundResource(R.drawable.table_header);
                            TextView house_label, room_label;
                            house_label = new TextView(getApplicationContext());
                            room_label = new TextView(getApplicationContext());
                            house_label.setText("House");
                            room_label.setText("Room_no");
                            house_label.setTextColor(Color.WHITE);
                            room_label.setTextColor(Color.WHITE);
                            house_label.setPadding(50, 20, 50, 20);
                            room_label.setPadding(50, 20, 50, 20);
                            tableRow.addView(house_label);
                            tableRow.addView(room_label);
                            tableLayout.addView(tableRow);
                            for (DocumentSnapshot d : list) {
                                Booked_room room = d.toObject(Booked_room.class);
                                roomArrayList.add(room);
                                TableRow tableRow3;
                                tableRow3 = new TableRow(getApplicationContext());
                                tableRow3.setBackgroundResource(R.drawable.border);
                                TextView house, room_no;
                                house = new TextView(getApplicationContext());
                                room_no = new TextView(getApplicationContext());
                                house.setText(room.getHouse());
                                room_no.setText(room.getRoom());
                                house.setPadding(50, 20, 50, 20);
                                room_no.setPadding(50, 20, 50, 20);
                                tableRow3.addView(house);
                                tableRow3.addView(room_no);
                                tableLayout.addView(tableRow3);
                                linearLayout.addView(tableLayout);
                            }

                        }else{
                            TextView message;
                            message = new TextView(getApplicationContext());
                            message.setText("You have not booked any room yet");
                            message.setTextColor(Color.BLACK);
                            message.setPadding(20,20,20,20);
                            Button button;
                            button = new Button(getApplicationContext());
                            button.setText("Book Room");
                            button.setBackgroundColor(Color.rgb(100,0,0));
                            button.setTextColor(Color.WHITE);
                            button.setPadding(20,20,20,20);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(hostel.this, Book_hostel.class);
                                    i.putExtra("student", student);
                                    startActivity(i);
                                }
                            });
                            linearLayout.addView(message);
                            linearLayout.addView(button);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(hostel.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });
        setContentView(linearLayout);
    }
}