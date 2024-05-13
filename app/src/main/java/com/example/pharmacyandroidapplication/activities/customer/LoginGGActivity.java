package com.example.pharmacyandroidapplication.activities.customer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pharmacyandroidapplication.R;
import com.example.pharmacyandroidapplication.models.Account;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class LoginGGActivity extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 20;

    @Override
    protected void onStart() {
        super.onStart();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("464697881053-jp8u3bclc504uuv3jvsr1nksitjti83t.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient( this,gso);

        googleSignIn();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void googleSignIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Debug message");

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firbaseAuth(account.getIdToken());
            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firbaseAuth(String idToken) {
        Log.d(TAG, "Debug message auth");

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;
                            String userID = user.getUid();
                            String userName = user.getDisplayName();
                            String userImg = Objects.requireNonNull(user.getPhotoUrl()).toString();

                            database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("accounts");

                            Account acc = new Account(userID, userName, userImg);
                            reference.child(userID).setValue(acc);




//                            HashMap<String, Object> map = new HashMap<>();
//                            map.put("id", user.getUid());
//                            map.put("name", user.getDisplayName());
//                            map.put("profile", user.getPhotoUrl().toString());

//                            database.getReference().child("accounts").child(user.getUid()).setValue(map);

                            Intent intent = new Intent(LoginGGActivity.this,CustomerHomepageActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginGGActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}