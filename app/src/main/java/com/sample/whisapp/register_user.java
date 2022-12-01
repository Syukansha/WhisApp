package com.sample.whisapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register_user extends Activity{
    Button btnRegister;
    EditText input1,input2,input3,input4;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        DatabaseHandler db = new DatabaseHandler(this);
        btnRegister = findViewById(R.id.button2);
        input1 = findViewById(R.id.editText);
        input2 = findViewById(R.id.editText2);
        input3 = findViewById(R.id.editText3);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input1.getText().toString();
                String password = input2.getText().toString();
                String email = input3.getText().toString();
                String value = "";


                if(db.checkUser(name) == true) {
                    db.addUser(new Users(name, password, email, value));
                    Toast.makeText(register_user.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), login_user.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(register_user.this, "Username has been existed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
