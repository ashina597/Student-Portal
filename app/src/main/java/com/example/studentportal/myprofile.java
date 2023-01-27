package com.example.studentportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class myprofile extends AppCompatActivity {
    TextView reg_no,name, gender, phone_no, email, school,programme;
    Button update;
    private FirebaseFirestore db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        Student student = (Student) getIntent().getSerializableExtra("student");
        reg_no = findViewById(R.id.reg_no);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        phone_no = findViewById(R.id.phone_no);
        email = findViewById(R.id.email);
        school = findViewById(R.id.school);
        programme = findViewById(R.id.programme);
        update = findViewById(R.id.updateBtn);




        db = FirebaseFirestore.getInstance();

        db.collection("student_details").whereEqualTo("email", student.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override

                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Student s = d.toObject(Student.class);
                                s.setId(d.getId());
                                reg_no.setText("Reg_No: " + s.getReg_no());
                                name.setText("Name: " + s.getname());
                                gender.setText("Gender: " + s.getGender());
                                phone_no.setText("Phone_No: " + s.getPhone_no());
                                email.setText("Email: " + s.getEmail());
                                school.setText("School: " + s.getSchool());
                                programme.setText("Programme: " + s.getProgramme());

                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(myprofile.this, UpdateDetails.class);
                                        i.putExtra("student", s);
                                        startActivity(i);
                                    }
                                });
                            }
                        }
                    }
                });


    }
}