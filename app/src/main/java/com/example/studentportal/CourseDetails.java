package com.example.studentportal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends AppCompatActivity {

    private RecyclerView courseRV;
    private ArrayList<courseItem> coursesArrayList;
    private CourseRVAdapter courseRVAdapter;
    private FirebaseFirestore db;
    TextView message;
    ProgressBar loadingPB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        // initializing our variables.
        courseRV = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idProgressBar);
        message = findViewById(R.id.text1);

        // initializing our variable for firebase firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        String reg_no = i.getStringExtra("reg_no");

        // creating our new array list
        coursesArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        courseRVAdapter = new CourseRVAdapter(coursesArrayList, this);

        // setting adapter to our recycler view.
        courseRV.setAdapter(courseRVAdapter);

        // getting the data from Firebase Firestore.

        db.collection("Registered_courses").whereEqualTo("reg_no", reg_no)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are
                            // hiding our progress bar and adding
                            // our data in a list.
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                               courseItem c = d.toObject(courseItem.class);
//                                c.setId(d.getId());

                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                coursesArrayList.add(c);
                                c.setId(d.getId());
                            }
                            // after adding the data to recycler view.
                            // we are calling recycler view notifuDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            courseRVAdapter.notifyDataSetChanged();
                        } else {
                            loadingPB.setVisibility(View.GONE);
                            message.setText("You have not registered for any unit");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingPB.setVisibility(View.GONE);
                        message.setText("Failed to load data. Please try again.");
                    }
                });
    }
}
