package com.sketcherex.upiappreplica.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sketcherex.upiappreplica.MainActivity;
import com.sketcherex.upiappreplica.R;

import java.util.HashMap;
import java.util.Map;

public class SampleActivity extends AppCompatActivity {

    private EditText upiIdView,amountView;
    private Button signOut,makePayment,seeTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        viewSetter();
        addListeners();
    }

    private void addListeners() {
        makePayment.setOnClickListener((view)->{

                int amount = Integer.parseInt(amountView.getText().toString());
                String upiId = upiIdView.getText().toString();
                String myNumber= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> transaction = new HashMap<>();
                transaction.put("from", myNumber);
                transaction.put("to", upiId);
                transaction.put("amount", amount);
                transaction.put("succesful",false);
                transaction.put("timestamp", FieldValue.serverTimestamp());
                db.collection("transactions").add(transaction).addOnCompleteListener((ref)->{
                    showToast("Successful transaction ");

                        }
                ).addOnFailureListener((ref)->{
                    showToast("Unsuccessfull transaction"+ ref.getCause());
//                    ref.getCause();
                });

        });

        signOut.setOnClickListener((view)->{

            AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> {

                showToast("Successful sign out");
                startActivity(new Intent(SampleActivity.this, MainActivity.class));
                finish();
            });
        });
        seeTransactions.setOnClickListener((view) -> {


        });
    }

    private void viewSetter() {
        upiIdView=findViewById(R.id.upiIdText);
        amountView=findViewById(R.id.Amount);
        signOut=findViewById(R.id.signOut);
        makePayment=findViewById(R.id.makePayment);
        seeTransactions=findViewById(R.id.seeTransactions);

    }
    private void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}

