package com.example.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
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
import java.util.Objects;

public class results extends AppCompatActivity {

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        tableLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        tableLayout.setPadding(20,100,20,20);


        Intent i = getIntent();
        String reg_no = i.getStringExtra("reg_no");

        db = FirebaseFirestore.getInstance();

        TableRow tableRow;
        tableRow = new TableRow(getApplicationContext());
        tableRow.setBackgroundResource(R.drawable.table_header);
        TextView code, title, marks, grade;
        code = new TextView(getApplicationContext());
        title = new TextView(getApplicationContext());
        marks = new TextView(getApplicationContext());
        grade = new TextView(getApplicationContext());
        code.setText("Code");
        title.setText("Title");
        marks.setText("Marks");
        grade.setText("Grade");
        code.setTextColor(Color.WHITE);
        title.setTextColor(Color.WHITE);
        marks.setTextColor(Color.WHITE);
        grade.setTextColor(Color.WHITE);
        code.setPadding(20, 20, 20, 20);
        title.setPadding(20, 20, 20, 20);
        marks.setPadding(20, 20, 20, 20);
        grade.setPadding(20, 20, 20, 20);
        tableRow.addView(code);
        tableRow.addView(title);
        tableRow.addView(marks);
        tableRow.addView(grade);
        tableLayout.addView(tableRow);

                db.collection("Registered_courses").whereEqualTo("reg_no", reg_no)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d : list) {
                                        resultsItem c = d.toObject(resultsItem.class);
                                        TableRow tableRow;
                                        tableRow = new TableRow(getApplicationContext());
                                        tableRow.setBackgroundResource(R.drawable.border);
                                        TextView code, title, marks, grade;
                                        code = new TextView(getApplicationContext());
                                        title = new TextView(getApplicationContext());
                                        marks = new TextView(getApplicationContext());
                                        grade = new TextView(getApplicationContext());
                                        assert c != null;
                                        if (!Objects.equals(c.getMarks(), "Not Graded"))
                                        {
                                            code.setText(c.getCourseCode());
                                            title.setText(c.getCourseTitle());
                                            marks.setText(c.getMarks());
                                            grade.setText(c.getGrade());
                                            code.setTextColor(Color.BLACK);
                                            title.setTextColor(Color.BLACK);
                                            marks.setTextColor(Color.BLACK);
                                            grade.setTextColor(Color.BLACK);
                                            code.setPadding(20, 20, 20, 20);
                                            title.setPadding(20, 20, 20, 20);
                                            marks.setPadding(20, 20, 20, 20);
                                            grade.setPadding(20, 20, 20, 20);
                                            tableRow.addView(code);
                                            tableRow.addView(title);
                                            tableRow.addView(marks);
                                            tableRow.addView(grade);
                                        }
                                        tableLayout.addView(tableRow);
//
                                    }

                                } else {
                                    Toast.makeText(results.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(results.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                            }
                        });
        setContentView(tableLayout);
    }
}