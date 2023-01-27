package com.example.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Timetable extends AppCompatActivity {

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);


        RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
        HorizontalScrollView horizontal = new HorizontalScrollView(getApplicationContext());
        ScrollView vertical = new ScrollView(getApplicationContext());
                TableLayout tableLayout = new TableLayout(this);
                tableLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                tableLayout.setPadding(20,100,20,20);




                Student student = (Student) getIntent().getSerializableExtra("student");

                db = FirebaseFirestore.getInstance();

                TableRow tableRow;
                tableRow = new TableRow(getApplicationContext());
                tableRow.setBackgroundResource(R.drawable.table_header);
                TextView code, title, day, from , to, venue, lecturer;
                code = new TextView(getApplicationContext());
                title = new TextView(getApplicationContext());
                day = new TextView(getApplicationContext());
                from = new TextView(getApplicationContext());
                to = new TextView(getApplicationContext());
                venue = new TextView(getApplicationContext());
                code.setText("Code");
                title.setText("Title");
                day.setText("Day");
                from.setText("From");
                to.setText("To");
                venue.setText("Venue");
                code.setTextColor(Color.WHITE);
                title.setTextColor(Color.WHITE);
                day.setTextColor(Color.WHITE);
                from.setTextColor(Color.WHITE);
                to.setTextColor(Color.WHITE);
                venue.setTextColor(Color.WHITE);
                code.setPadding(20, 20, 20, 20);
                title.setPadding(20, 20, 20, 20);
                day.setPadding(20, 20, 20, 20);
                from.setPadding(20, 20, 20, 20);
                to.setPadding(20, 20, 20, 20);
                venue.setPadding(20, 20, 20, 20);
                tableRow.addView(code);
                tableRow.addView(title);
                tableRow.addView(day);
                tableRow.addView(from);
                tableRow.addView(to);
                tableRow.addView(venue);
                tableLayout.addView(tableRow);

                db.collection("Courses").whereEqualTo("programme", student.getProgramme())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d : list) {
                                        timetableItem c = d.toObject(timetableItem.class);
                                        TableRow tableRow;
                                        tableRow = new TableRow(getApplicationContext());
                                        tableRow.setBackgroundResource(R.drawable.border);
                                        TextView code, title, day, from , to, venue;
                                        code = new TextView(getApplicationContext());
                                        title = new TextView(getApplicationContext());
                                        day = new TextView(getApplicationContext());
                                        from = new TextView(getApplicationContext());
                                        to = new TextView(getApplicationContext());
                                        venue = new TextView(getApplicationContext());
                                        code.setText(c.getCourseCode());
                                        title.setText(c.getCourseTitle());
                                        day.setText(c.getDay());
                                        from.setText(c.getFrom());
                                        to.setText(c.getTo());
                                        venue.setText(c.getVenue());
                                        code.setTextColor(Color.BLACK);
                                        title.setTextColor(Color.BLACK);
                                        day.setTextColor(Color.BLACK);
                                        from.setTextColor(Color.BLACK);
                                        to.setTextColor(Color.BLACK);
                                        venue.setTextColor(Color.BLACK);
                                        code.setPadding(20, 20, 20, 20);
                                        title.setPadding(20, 20, 20, 20);
                                        day.setPadding(20, 20, 20, 20);
                                        from.setPadding(20, 20, 20, 20);
                                        to.setPadding(20, 20, 20, 20);
                                        venue.setPadding(20, 20, 20, 20);
                                        tableRow.addView(code);
                                        tableRow.addView(title);
                                        tableRow.addView(day);
                                        tableRow.addView(from);
                                        tableRow.addView(to);
                                        tableRow.addView(venue);
                                        tableLayout.addView(tableRow);
//
                                    }

                                } else {
                                    Toast.makeText(com.example.studentportal.Timetable.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(com.example.studentportal.Timetable.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                            }
                        });
                horizontal.addView(tableLayout);
                vertical.addView(horizontal);
                relativeLayout.addView(vertical);
                setContentView(relativeLayout);
            }
        }
