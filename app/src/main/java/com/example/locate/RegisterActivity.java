package com.example.locate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText register_name,register_contact;
    Button btn_register;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME="mypref";
    private static final String KEY_NAME="name";
    private static final String KEY_CONTACT="contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_name= findViewById(R.id.register_name);
        register_contact= findViewById(R.id.register_contact);
        btn_register= findViewById(R.id.register_save);

        sharedPreferences= getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name= sharedPreferences.getString(KEY_NAME,null);

        if(name!=null){
            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
        }

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString(KEY_NAME,register_name.getText().toString());
                editor.putString(KEY_CONTACT,register_contact.getText().toString());
                editor.apply();

                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
            }
        });


    }

}