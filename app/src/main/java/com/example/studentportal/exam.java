package com.example.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class exam extends AppCompatActivity {

    TableLayout table;
    private FirebaseFirestore db;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
        HorizontalScrollView horizontal = new HorizontalScrollView(getApplicationContext());
        ScrollView vertical = new ScrollView(getApplicationContext());

        TableLayout tableLayout = new TableLayout(getApplicationContext());
        tableLayout.setPadding(50,50,50,50);


        Student student = (Student) getIntent().getSerializableExtra("student");
        String reg_no = student.getReg_no();

        db = FirebaseFirestore.getInstance();

        db.collection("Registered_courses").whereEqualTo("reg_no", reg_no)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            TableRow tableHeader;
                            tableHeader = new TableRow(getApplicationContext());
                            tableHeader.setBackgroundResource(R.drawable.table_header);
                            TextView code_label, title_label, group_label;
                            code_label = new TextView(getApplicationContext());
                            title_label = new TextView(getApplicationContext());
                            group_label = new TextView(getApplicationContext());
                            code_label.setText("Course Code");
                            title_label.setText("Course Title");
                            group_label.setText("Class Group");
                            code_label.setTextColor(Color.WHITE);
                            title_label.setTextColor(Color.WHITE);
                            group_label.setTextColor(Color.WHITE);
                            code_label.setPadding(10,10,10,10);
                            title_label.setPadding(10,10,10,10);
                            group_label.setPadding(10,10,10,10);
                            tableHeader.addView(code_label);
                            tableHeader.addView(title_label);
                            tableHeader.addView(group_label);
                            tableLayout.addView(tableHeader);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                courseItem c = d.toObject(courseItem.class);
                                TableRow tableRow;
                                tableRow = new TableRow(getApplicationContext());
                                tableRow.setBackgroundResource(R.drawable.border);
                                TextView code, title, group;
                                code = new TextView(getApplicationContext());
                                title = new TextView(getApplicationContext());
                                group = new TextView(getApplicationContext());
                                code.setText(c.getCourseCode());
                                title.setText(c.getCourseTitle());
                                group.setText(c.getClassGroup());
                                code.setPadding(10,10,10,10);
                                title.setPadding(10,10,10,10);
                                group.setPadding(10,10,10,10);
                                tableRow.addView(code);
                                tableRow.addView(title);
                                tableRow.addView(group);
                                tableLayout.addView(tableRow);
//
                            }

                                db.collection("Fees").whereEqualTo("reg_no", reg_no)
                                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                if (!queryDocumentSnapshots.isEmpty()){
                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                    TableRow tableRow1;
                                                    tableRow1 = new TableRow(getApplicationContext());
                                                    tableRow1.setBackgroundResource(R.drawable.border);
                                                    TextView title, bal;
                                                    title = new TextView(getApplicationContext());
                                                    bal = new TextView(getApplicationContext());
                                                    for (DocumentSnapshot d : list) {
                                                        feesItem fees = d.toObject(feesItem.class);
                                                        title.setText("Fee Balance");
                                                        bal.setText(fees.getBalance());
                                                        title.setPadding(10,10,10,10);
                                                    }
                                                    tableRow1.addView(title);
                                                    tableRow1.addView(bal);
                                                    tableLayout.addView(tableRow1);
                                                    TableRow button;
                                                    button = new TableRow(getApplicationContext());
                                                    button.setPadding(50,50,50,50);
                                                    Button generate;
                                                    generate = new Button(getApplicationContext());
                                                    generate.setText("Generate Exam Card");
                                                    generate.setTextColor(Color.WHITE);
                                                    generate.setBackgroundColor(Color.rgb(97,0,0));
                                                    generate.setPadding(20,20,20,20);
                                                    button.addView(generate);
                                                    tableLayout.addView(button);

                                                    generate.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            Toast.makeText(exam.this, "This page is currently under construction, you will be redirected to the school's website.", Toast.LENGTH_SHORT).show();
                                                            Timer t = new Timer(false);
                                                            t.schedule(new TimerTask() {
                                                                @Override
                                                                public void run() {
                                                                    runOnUiThread(new Runnable() {
                                                                        public void run() {
                                                                            Uri uri = Uri.parse("https://students.umma.ac.ke/");
                                                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                                            startActivity(intent);
                                                                        }
                                                                    });
                                                                }
                                                            }, 5000);
                                                        }
                                                    });

                                                    TableRow viewExam;
                                                    viewExam = new TableRow(getApplicationContext());
                                                   viewExam.setPadding(50,50,50,50);
                                                    Button viewExamTimetable;
                                                    viewExamTimetable = new Button(getApplicationContext());
                                                    viewExamTimetable.setText("View Exam Timetable");
                                                    viewExamTimetable.setTextColor(Color.WHITE);
                                                    viewExamTimetable.setBackgroundColor(Color.rgb(97,0,0));
                                                    viewExamTimetable.setPadding(20,20,20,20);
                                                    viewExam.addView(viewExamTimetable);
                                                    tableLayout.addView(viewExam);

                                                    viewExamTimetable.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            Intent i = new Intent(exam.this, examTimetable.class);
                                                            i.putExtra("student", student);
                                                            startActivity(i);
                                                        }
                                                    });

                                                }else{
                                                    Toast.makeText(exam.this, "your fee balance is not in the database", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(exam.this, "Failed to get you fee balance", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                        } else {
                            TextView message;
                            message = new TextView(getApplicationContext());
                            message.setText("You have not registered for any unit");
                            message.setPadding(20,20,20,20);
                            message.setTextColor(Color.BLACK);
                            tableLayout.addView(message);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(exam.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });



        horizontal.addView(tableLayout);
        vertical.addView(horizontal);
        relativeLayout.addView(vertical);
        setContentView(relativeLayout);



    }
}