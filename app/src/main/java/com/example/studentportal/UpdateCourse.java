package com.example.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCourse extends AppCompatActivity {

    private FirebaseFirestore db;
    EditText courseCode, courseTitle, classGroup;
    Button dropBtn, confirmBtn;
    String code, title, group;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        courseCode = findViewById(R.id.courseCode);
        courseTitle = findViewById(R.id.courseTitle);
        classGroup = findViewById(R.id.classGroup);
        dropBtn = findViewById(R.id.droBtn);
        confirmBtn = findViewById(R.id.confirmBtn);
        db = FirebaseFirestore.getInstance();

        courseItem course = (courseItem) getIntent().getSerializableExtra("course");
        courseCode.setText(course.getCourseCode());
        courseTitle.setText(course.getCourseTitle());
        classGroup.setText(course.getClassGroup());

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = courseTitle.getText().toString();
                group = classGroup.getText().toString();
                code = courseCode.getText().toString();

                db.collection("Registered_courses").whereEqualTo("courseCode", code).whereEqualTo("courseTitle",title)
                        .whereEqualTo("classGroup", group).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    Toast.makeText(UpdateCourse.this, "data exists in firestore", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(UpdateCourse.this, "data  does not exists in firestore", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateCourse.this, "data  does not exists in firestore", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        dropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete(course);

            }
        });

    }


    public void Delete(courseItem course) {
        db.collection("Registered_courses").
                        document(course.getId()).

                        delete().
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateCourse.this, "Course has been deleted from Database.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateCourse.this, CourseDetails.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("reg_no", course.getReg_no());
                            startActivity(i);
                        } else {
                            Toast.makeText(UpdateCourse.this, "Fail to delete the course. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}