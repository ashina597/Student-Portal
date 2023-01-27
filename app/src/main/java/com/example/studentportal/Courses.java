package com.example.studentportal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Courses extends AppCompatActivity {

    private EditText courseTitle, courseCode, classGroup;

    private Button submitCourseBtn, viewCoursesBtn, timetable;

    private String title, code, group;

    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        db = FirebaseFirestore.getInstance();
        Student student = (Student) getIntent().getSerializableExtra("student");
        String reg_no = student.getReg_no();


        courseTitle = findViewById(R.id.courseTitle);
        courseCode = findViewById(R.id.courseCode);
        classGroup = findViewById(R.id.classGroup);
        submitCourseBtn = findViewById(R.id.idBtnSubmitCourse);
        viewCoursesBtn = findViewById(R.id.idBtnViewCourses);
        timetable =findViewById(R.id.idBtnViewTimetable);

        viewCoursesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Courses.this, CourseDetails.class);
                i.putExtra("reg_no", reg_no);
                startActivity(i);
            }
        });

        submitCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = courseTitle.getText().toString();
                code = courseCode.getText().toString();
                group = classGroup.getText().toString();


                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(title)) {
                    courseTitle.setError("Please enter Course Name");
                } else if (TextUtils.isEmpty(code)) {
                    courseCode.setError("Please enter Course Description");
                } else if (TextUtils.isEmpty(group)) {
                    classGroup.setError("Please enter Class Group");
                } else if(group.equals("I") || group.equals("II")){
                    addDataToFirestore(reg_no, title, code, group);
                }
                else {
                    classGroup.setError("Enter a valid class group (either I or II");
                }
            }
        });

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Courses.this, Timetable.class);
                i.putExtra("student", student);
                startActivity(i);
            }
        });
    }


    private void addDataToFirestore( String reg_no, String title, String code, String group) {
        db.collection("Results").whereEqualTo("reg_no", reg_no).whereEqualTo("courseCode", code)
                .whereEqualTo("courseTitle", title).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()){
                            db.collection("Courses").whereEqualTo("courseCode", code).whereEqualTo("courseTitle", title)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @SuppressLint("WrongViewCast")
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if (!queryDocumentSnapshots.isEmpty()) {

                                                courseItem courses = new courseItem(title, code, group, reg_no);
                                                CollectionReference dbCourses = db.collection("Registered_courses");

                                                dbCourses.whereEqualTo("reg_no", reg_no).whereEqualTo("courseCode", code).get()
                                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                if (queryDocumentSnapshots.isEmpty()) {
                                                                    dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                            courseTitle.setText("");
                                                                            courseCode.setText("");
                                                                            classGroup.setText("");
                                                                            TextView message;
                                                                            message = findViewById(R.id.text);
                                                                            message.setTextColor(Color.BLACK);
                                                                            message.setText("Course registered successfully");
                                                                            message.setVisibility(View.VISIBLE);
                                                                            Timer t = new Timer(false);
                                                                            t.schedule(new TimerTask() {
                                                                                @Override
                                                                                public void run() {
                                                                                    runOnUiThread(new Runnable() {
                                                                                        public void run() {
                                                                                            message.setVisibility(View.INVISIBLE);
                                                                                        }
                                                                                    });
                                                                                }
                                                                            }, 5000);
                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            TextView message;
                                                                            message = findViewById(R.id.text);
                                                                            message.setText("Course not added, please try again");
                                                                            message.setVisibility(View.VISIBLE);
                                                                            Timer t = new Timer(false);
                                                                            t.schedule(new TimerTask() {
                                                                                @Override
                                                                                public void run() {
                                                                                    runOnUiThread(new Runnable() {
                                                                                        public void run() {
                                                                                            message.setVisibility(View.INVISIBLE);
                                                                                        }
                                                                                    });
                                                                                }
                                                                            }, 5000);
                                                                        }
                                                                    });
                                                                } else {
                                                                    TextView message;
                                                                    message = findViewById(R.id.text);
                                                                    message.setText("This course is already Registered");
                                                                    message.setVisibility(View.VISIBLE);
                                                                    Timer t = new Timer(false);
                                                                    t.schedule(new TimerTask() {
                                                                        @Override
                                                                        public void run() {
                                                                            runOnUiThread(new Runnable() {
                                                                                public void run() {
                                                                                    message.setVisibility(View.INVISIBLE);
                                                                                }
                                                                            });
                                                                        }
                                                                    }, 5000);
                                                                }
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {

                                                            }
                                                        });

                                            } else {
                                                TextView message;
                                                message = findViewById(R.id.text);
                                                message.setVisibility(View.VISIBLE);
                                                message.setText("Invalid Course details. Please confirm the course code and course title.");
                                                Timer t = new Timer(false);
                                                t.schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        runOnUiThread(new Runnable() {
                                                            public void run() {
                                                                message.setVisibility(View.INVISIBLE);
                                                            }
                                                        });
                                                    }
                                                }, 5000);
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            TextView message;
                                            message = findViewById(R.id.text);
                                            message.setText("Failed to get Data from Database");
                                            message.setVisibility(View.VISIBLE);
                                            Timer t = new Timer(false);
                                            t.schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    runOnUiThread(new Runnable() {
                                                        public void run() {
                                                            message.setVisibility(View.INVISIBLE);
                                                        }
                                                    });
                                                }
                                            }, 5000);
                                        }
                                    });
                        }else{
                            TextView message;
                            message = findViewById(R.id.text);
                            message.setText("This course is already graded");
                            message.setVisibility(View.VISIBLE);
                            Timer t = new Timer(false);
                            t.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            message.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                            }, 5000);
                        }
                    }
                });
    }
}
