package com.sample.whisapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_user extends Activity {
    Button b1,b2;
    EditText ed1,ed2;
    Intent i=new Intent(Intent.ACTION_VIEW);
    //TextView tx1;

    int counter = 3;

    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    public static final String EMAIL_KEY = "email_key";

    SharedPreferences sharedpreferences;
    String usernameS, passwordS, email;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        DatabaseHandler db = new DatabaseHandler(this);
        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2= (EditText)findViewById(R.id.editText2);

        b2 = (Button)findViewById(R.id.button2);
        //tx1 = (TextView)findViewById(R.id.textView3);
        //tx1.setVisibility(View.GONE);

        Log.d("Reading: ", "Reading all users..");
        /*List<Users> users = db.getAllUsers();
        for (Users us : users) {
            String log = " ,Name: " + us.getName() + " ,Password: " +
                    us.getPassword() + "Email: " + us.getEmail();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }*/
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = ed1.getText().toString();
                String password = ed2.getText().toString();

                usernameS = sharedpreferences.getString(NAME_KEY, null);
                passwordS = sharedpreferences.getString(PASSWORD_KEY, null);
                email = sharedpreferences.getString(EMAIL_KEY, null);

                try{
                    if(username.length() > 0 && password.length() >0)
                    {
                        DatabaseHandler dbUser = new DatabaseHandler(login_user.this);
                        dbUser.open();

                        if(dbUser.Login(username,password))
                        {
                            //int userid = dbUser.getUserID(username,password);
                            Toast.makeText(login_user.this,"Successfully Logged In", Toast.LENGTH_LONG).show();

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(NAME_KEY, ed1.getText().toString());
                            editor.putString(PASSWORD_KEY, ed2.getText().toString());
                            //editor.putString(USER_ID_KEY, String.valueOf(userid));
                            editor.apply();

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(login_user.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
                        }
                        dbUser.close();
                    }

                }catch(Exception e)
                {
                    Toast.makeText(login_user.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), register_user.class);
                startActivity(i);
            }
        });
    }
}
