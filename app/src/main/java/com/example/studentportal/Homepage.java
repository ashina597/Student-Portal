package com.example.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {
    GridView gridView;
    String reg;
    ArrayList itemsArrayList =new ArrayList<>();
    private FirebaseFirestore db;
    ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        loadingPB = findViewById(R.id.idProgressBar);
        gridView = findViewById(R.id.gridview);

        db = FirebaseFirestore.getInstance();

        db.collection("student_details").whereEqualTo("email", email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @SuppressLint("SuspiciousIndentation")
                        @Override

                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                loadingPB.setVisibility(View.GONE);
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                Student s = d.toObject(Student.class);
                                s.setId(d.getId());
                                reg = s.getReg_no();
                                    itemsArrayList.add(new Items("COURSE", R.drawable.course));
                                    itemsArrayList.add(new Items("EXAM", R.drawable.exam));
                                    itemsArrayList.add(new Items("RESULTS", R.drawable.results));
                                    itemsArrayList.add(new Items("FEES", R.drawable.fees));
                                    itemsArrayList.add(new Items("HOSTEL", R.drawable.hostel));
                                    itemsArrayList.add(new Items("MY PROFILE", R.drawable.profile));

                                    Homepage_adapter adapter = new Homepage_adapter(getApplicationContext(), itemsArrayList);
                                    gridView.setAdapter(adapter);
                                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            if(i == 0)
                                            {
                                                Intent intent = new Intent(Homepage.this, Courses.class);
                                                intent.putExtra("student", s);
                                                startActivity(intent);
                                            }
                                            if (i == 1)
                                            {
                                                Intent intent = new Intent(Homepage.this, exam.class);
                                                intent.putExtra("student", s);
                                                startActivity(intent);
                                            }
                                            if (i == 2)
                                            {
                                                Intent intent = new Intent(Homepage.this, results.class);
                                                intent.putExtra("reg_no", s.getReg_no());
                                                startActivity(intent);
                                            }
                                            if (i == 3)
                                            {
                                                Intent intent = new Intent(Homepage.this,fees.class);
                                                intent.putExtra("reg_no", s.getReg_no());
                                                startActivity(intent);
                                            }
                                            if (i == 4)
                                            {
                                                Intent intent = new Intent(Homepage.this, hostel.class);
                                                intent.putExtra("student", s);
                                                startActivity(intent);
                                            }
                                            if (i == 5)
                                            {
                                                Intent intent = new Intent(Homepage.this, myprofile.class);
                                                intent.putExtra("student", s);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                    }
                                } else {
                                    // if the snapshot is empty we are displaying a toast message.
                                    Toast.makeText(Homepage.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                                    }

                                    }
                                    }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // if we do not get any data or any error we are displaying
                                        // a toast message that we do not get any data
                                        Toast.makeText(Homepage.this, "Fail to get your details.", Toast.LENGTH_SHORT).show();
                                    }
                                    });
    }
}