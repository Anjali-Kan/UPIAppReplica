package com.sketcherex.upiappreplica.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;
import com.sketcherex.upiappreplica.MainActivity;
import com.sketcherex.upiappreplica.R;

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


        });

        signOut.setOnClickListener((view)->{

            AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> {

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
}
