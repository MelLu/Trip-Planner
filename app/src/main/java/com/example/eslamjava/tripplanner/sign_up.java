package com.example.eslamjava.tripplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dto.UserData;
import sqliteDB.DB_Adapter;

public class sign_up extends AppCompatActivity {
    TextView password_text;
    TextView name_text;
    TextView email_text;
    Button singn_up;
    TextView password_confirm_text;
    TextView login_view;
    String email;
    String password;
    String password_confirm;
    String name;
    boolean flag_validate;
    UserData userdata;
    DB_Adapter dataoperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        login_view=(TextView)findViewById(R.id.link_login);
        password_text  =(TextView)findViewById(R.id.input_password) ;
        password_confirm_text =(TextView)findViewById(R.id.input_password_confirm);
        email_text=(TextView)findViewById(R.id.input_email);
        name_text=(TextView)findViewById(R.id.input_name);
        singn_up=(Button)findViewById(R.id.btn_signup);
        singn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataoperation=new DB_Adapter(sign_up.this);
                dataoperation.open();
                Log.i("database","open database");
                flag_validate= validate();
                if( flag_validate==true)
                {
                    userdata=new UserData(name,email,password);
                    int id= dataoperation.insert_user(userdata);
                    System.out.print("datainsert");

                    Log.i("database","closedatabase: "+id);
                    if (id>0){

                        Intent i = new Intent(getApplicationContext(),Login_Activity .class);
                        startActivity(i);
                        finish();
                    }



                }
                dataoperation.close();


            }
        });
        login_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Login_Activity .class);
                startActivity(i);
            }
        });
    }
    public boolean validate() {
        boolean valid = true;
        name=name_text.getText().toString();
        email = email_text.getText().toString();
        password =  password_text.getText().toString();
        password_confirm=password_confirm_text.getText().toString();
        Pattern pattern = Pattern.compile(new String ("^[a-zA-Z\\s]*$"));
        Matcher matcher = pattern.matcher(name);
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_text.setError("Enter a valid email address");
            email_text.requestFocus();
            valid = false;
        } else {
            email_text.setError(null);

        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_text.setError("Between 4 and 10 alphanumeric characters");
            password_text.requestFocus();
            valid = false;
        } else {
            password_text.setError(null);

        }

        if(!matcher.matches()||name.isEmpty())
        {
            name_text.setError("Please Enter your name only character !");
            name_text.requestFocus();
            valid=false;
        }
        else
        {
            name_text.setError(null);
        }
        if(password_confirm.isEmpty()||!password_confirm.equals(password))
        {
            password_confirm_text.setError("Enter correct password !");
            password_confirm_text.requestFocus();
            valid=false;
        }
        else
        {
            password_confirm_text.setError(null);

        }

        return valid;
    }


}
