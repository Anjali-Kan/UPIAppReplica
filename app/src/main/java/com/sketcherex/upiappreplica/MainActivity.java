package com.sketcherex.upiappreplica;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkSignIn();
    }

    private void checkSignIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
        } else {
            // not signed in
            startPhoneVerification();
        }
    }

    private void startPhoneVerification() {

        AuthUI au = AuthUI.getInstance();
        AuthUI.SignInIntentBuilder builder =au.createSignInIntentBuilder();
        ArrayList<AuthUI.IdpConfig> providerList= new ArrayList();
        providerList.add(new AuthUI.IdpConfig.PhoneBuilder().build());
        builder.setAvailableProviders(providerList);
        startActivityForResult(builder.build(),RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== RC_SIGN_IN)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode==RESULT_OK){

                //TODO add next activity
//                startActivity();
                showToast("Sign in succesfull!");
                finish();
            }
            else
            {

                if (response == null) {
                    // User pressed back button
                    showToast("Sign in cancelled!");
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showToast("Network unavailable!");
                    return;
                }
            }
        }
    }

    private void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
