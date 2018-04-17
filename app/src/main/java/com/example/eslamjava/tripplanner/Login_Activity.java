package com.example.eslamjava.tripplanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import sqliteDB.DB_Adapter;

public class Login_Activity extends AppCompatActivity {
    TextView sign_link;
    TextView email_text;
    TextView password_text;
    Button login_btn;
    boolean flag_valid;
    private int RC_SIGN_IN = 10;

    ImageView facebook_button;
    CallbackManager callbackManager;
    ProgressDialog progressDialog;

    String email;
    String password;
    ImageView  google_sign_in_button;
    Session_mangment session_mangement;
    GoogleApiClient mGoogleSignInClient;
    DB_Adapter adapterOperation = new DB_Adapter(Login_Activity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_);
        progressDialog = new ProgressDialog(this);


        session_mangement = new Session_mangment(getApplicationContext());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        sign_link = (TextView) findViewById(R.id.link_signup);
        email_text = (TextView) findViewById(R.id.email);
        password_text = (TextView) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_btn);

        google_sign_in_button = (ImageView ) findViewById(R.id.google_sign_in_button);
        // google_sign_in_button.setSize(SignInButton.SIZE_STANDARD);

        google_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_valid = validate();


                if (flag_valid) {
                    adapterOperation.open();
                    boolean searchUserFlag = adapterOperation.searchUser(email);

                    if (searchUserFlag) {
                        session_mangement.createLoginSession(email, " ");
                        progressDialog.setMessage("Login Sucessfully");
                        progressDialog.show();
                        Intent i = new Intent(getApplicationContext(), Navigation_info_Activity.class);
                        startActivity(i);
                        finish();
                    } else {
                        progressDialog.setMessage("Login Faild");
                        progressDialog.show();
                    }
                }
                adapterOperation.close();


            }
        });
        sign_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), sign_up.class);
                startActivity(i);


            }
        });
        callbackManager = CallbackManager.Factory.create();
        facebook_button = (ImageView) findViewById(R.id.login_button);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Profile profile = Profile.getCurrentProfile();


                session_mangement.createLoginSession(profile.toString(), profile.getName());
                progressDialog.setMessage("Login Sucessfully");
                progressDialog.show();
                Intent i = new Intent(getApplicationContext(), Navigation_info_Activity.class);
                startActivity(i);
                finish();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("info", "error login " + error.getMessage());

            }
        });

    }

    public boolean validate() {
        boolean valid = true;

        email = email_text.getText().toString();
        password = password_text.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_text.setError("Enter a valid email address");
            email_text.requestFocus();
            valid = false;
        } else {
            email_text.setError(null);
            email_text.requestFocus();
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_text.setError("Between 4 and 10 alphanumeric characters");
            password_text.requestFocus();
            valid = false;
        } else {
            password_text.setError(null);
            password_text.requestFocus();
        }

        return valid;
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
//            Toast.makeText(this, "Name: " + account.getDisplayName(), Toast.LENGTH_LONG).show();
//            Toast.makeText(this, "Email: " + account.getEmail(), Toast.LENGTH_LONG).show();
            session_mangement.createLoginSession(account.getEmail(), account.getDisplayName());
            progressDialog.setMessage("Login Sucessfully");
            progressDialog.show();
            Intent i = new Intent(getApplicationContext(), Navigation_info_Activity.class);
            startActivity(i);
            finish();

        } else {
            GoogleSignInAccount account = result.getSignInAccount();
            Toast.makeText(Login_Activity.this,"Pleas open internet",Toast.LENGTH_LONG).show();


        }

    }

}

