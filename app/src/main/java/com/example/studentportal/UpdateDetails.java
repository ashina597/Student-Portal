package com.example.studentportal;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateDetails extends AppCompatActivity {

    TextView fname, mname, sname, phone, email,home;
    Button update;
    String first_name, middle_name, surname,reg_no, phone_no, email_address, gender, nationality,nationality_cert, nationality_no,
    dob, home_county, school, programme, parent_guardian, parent_guardian_phone_no, parent_guardian_email, id;
    public FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);



        fname = findViewById(R.id.firstName);
        mname = findViewById(R.id.middleName);
        sname = findViewById(R.id.surname);
        phone = findViewById(R.id.phone_no);
        email = findViewById(R.id.email);
        home = findViewById(R.id.homeCounty);
        update = findViewById(R.id.update);

        db = FirebaseFirestore.getInstance();

        Student student = (Student) getIntent().getSerializableExtra("student");

        fname.setText(student.getFirst_name());
        mname.setText(student.getMiddle_name());
        sname.setText(student.getSurname());
        phone.setText(student.getPhone_no());
        email.setText(student.getEmail());
        home.setText(student.getHome_county());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = fname.getText().toString();
                middle_name = mname.getText().toString();
                surname = sname.getText().toString();
                phone_no = phone.getText().toString();
                email_address = email.getText().toString();
                home_county = home.getText().toString();
                updatedetails(student, first_name, middle_name, surname, phone_no, email_address,home_county);
            }
        });


    }

    public void updatedetails(Student student, String first_name, String middle_name, String surname, String phone_no,
                              String email_address, String home_county){
        gender = student.getGender();
        nationality = student.getNationality();
        nationality_no = student.getNationality_no();
        nationality_cert = student.getNationality_cert();
        dob = student.getDOB();
        school = student.getSchool();
        programme = student.getProgramme();
        parent_guardian = student.getParent_guardian();
        parent_guardian_phone_no = student.getParent_guardian_phone_no();
        parent_guardian_email = student.getParent_guardian_email();
        reg_no = student.getReg_no();
        id = student.getId();



        Student updatestudent = new Student (id, first_name, middle_name, surname,reg_no, phone_no, email_address,
                gender, nationality,nationality_cert, nationality_no, dob, home_county, school, programme, parent_guardian,
                parent_guardian_phone_no, parent_guardian_email);

        db.collection("student_details").document(student.getId()).set(updatestudent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateDetails.this, "Student has been updated..", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UpdateDetails.this, myprofile.class);
                        i.putExtra("student", student);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateDetails.this, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}