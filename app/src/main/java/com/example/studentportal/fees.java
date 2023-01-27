package com.example.studentportal;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class fees extends AppCompatActivity {
    private FirebaseFirestore db;
    private ArrayList<feesItem> feesItemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
        HorizontalScrollView horizontal = new HorizontalScrollView(getApplicationContext());
        ScrollView vertical = new ScrollView(getApplicationContext());
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        tableLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        tableLayout.setPadding(20,100,20, 20);


        Intent i = getIntent();
        String reg_no = i.getStringExtra("reg_no");

        db = FirebaseFirestore.getInstance();
        feesItemArrayList = new ArrayList<>();


        db.collection("Fees").whereEqualTo("reg_no", reg_no)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            TableRow tableHeader;
                            tableHeader = new TableRow(getApplicationContext());
                            tableHeader.setBackgroundResource(R.drawable.table_header);
                            TextView Receipt, Date, Description, Debit, Credit, Balance;
                            Receipt = new TextView(getApplicationContext());
                            Date = new TextView(getApplicationContext());
                            Description = new TextView(getApplicationContext());
                            Debit = new TextView(getApplicationContext());
                            Credit = new TextView(getApplicationContext());
                            Balance = new TextView(getApplicationContext());
                            Receipt.setText("Receipt No");
                            Date.setText("Date");
                            Description.setText("Description");
                            Credit.setText("Amount");
                            Balance.setText("Balance");
                            Receipt.setTextColor(Color.WHITE);
                            Date.setTextColor(Color.WHITE);
                            Description.setTextColor(Color.WHITE);
                            Credit.setTextColor(Color.WHITE);
                            Balance.setTextColor(Color.WHITE);
                            Receipt.setPadding(10,10,10,10);
                            Date.setPadding(10,10,10,10);
                            Description.setPadding(10,10,10,10);
                            Debit.setPadding(10,10,10,10);
                            Credit.setPadding(10,10,10,10);
                            Balance.setPadding(10,10,10,10);
//                            tableHeader.addView(Receipt);
                            tableHeader.addView(Date);
                            tableHeader.addView(Description);
                            tableHeader.addView(Debit);
                            tableHeader.addView(Credit);
                            tableHeader.addView(Balance);
                            tableLayout.addView(tableHeader);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                feesItem fees = d.toObject(feesItem.class);
                                feesItemArrayList.add(fees);
                                TableRow tableRow;
                                tableRow = new TableRow(getApplicationContext());
                                tableRow.setBackgroundResource(R.drawable.border);
                                TextView receipt, date, description, debit, credit, balance;
                                receipt = new TextView(getApplicationContext());
                                date = new TextView(getApplicationContext());
                                description = new TextView(getApplicationContext());
                                debit = new TextView(getApplicationContext());
                                credit = new TextView(getApplicationContext());
                                balance = new TextView(getApplicationContext());
                                receipt.setText(fees.getReceipt_no());
                                date.setText(fees.getDate());
                                description.setText(fees.getDescription());
                                credit.setText(fees.getCredit());
                                balance.setText(fees.getBalance());
                                receipt.setTextColor(Color.BLACK);
                                date.setTextColor(Color.BLACK);
                                description.setTextColor(Color.BLACK);
                                credit.setTextColor(Color.BLACK);
                                balance.setTextColor(Color.BLACK);
                                receipt.setPadding(10,10,10,10);
                                date.setPadding(10,10,10,10);
                                description.setPadding(10,10,10,10);
                                debit.setPadding(10,10,10,10);
                                credit.setPadding(10,10,10,10);
                                balance.setPadding(10,10,10,10);
//                                tableRow.addView(receipt);
                                tableRow.addView(date);
                                tableRow.addView(description);
                                tableRow.addView(debit);
                                tableRow.addView(credit);
                                tableRow.addView(balance);
                                tableLayout.addView(tableRow);
//
                            }

                        } else {
                            Toast.makeText(fees.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(fees.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
        horizontal.addView(tableLayout);
        vertical.addView(horizontal);
        relativeLayout.addView(vertical);
        setContentView(relativeLayout);
    }
}