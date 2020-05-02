package com.example.takemyattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    Intent intent;
    private String givenName;
    private Uri profileImage;
    private boolean sign_out = false;
    private final String TAG = "LoginActivity";
    private String fullName;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try{
            Intent intent = getIntent();
            sign_out = intent.getExtras().getBoolean("signOut");
        }
        catch(Exception e){
            Log.e(TAG,String.valueOf(e));
        }

        if(sign_out == true){
            signOut();
        }
        //Initializing Views
        signInButton = findViewById(R.id.signInButton);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoginActivity.this, "You've been signed out successfully",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            givenName = account.getGivenName();
            profileImage = account.getPhotoUrl();
            fullName = account.getDisplayName();
            email = account.getEmail();
//            Log.e(TAG,givenName + "    " + profileImage);
            Toast.makeText(LoginActivity.this, "Hello "+ givenName , Toast.LENGTH_SHORT).show();
            intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("name",givenName);
            intent.putExtra("image",profileImage);
            intent.putExtra("fullname", fullName);
            intent.putExtra("email", email);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            givenName = account.getGivenName();
            fullName = account.getDisplayName();
            email = account.getEmail();
            profileImage = account.getPhotoUrl();
            Toast.makeText(LoginActivity.this, "Hello "+ account.getGivenName(), Toast.LENGTH_SHORT).show();
//            Log.e("LoginActivity",givenName + "    " + profileImage);
            intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("name",givenName);
            intent.putExtra("image",profileImage);
            intent.putExtra("fullname", fullName);
            intent.putExtra("email", email);
            startActivity(intent);
        }
        super.onStart();
    }
}