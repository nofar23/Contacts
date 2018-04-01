package com.example.nofarreshef.contacts;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ShowContact extends AppCompatActivity {

    Person person;
    TextView nameOutput;
    TextView phoneOutput;
    TextView emailOutput;
    TextView addressOutput;
    ImageView pictureOutput;
    TextView websiteOutput;
    TextView birthdayOutput;
    TextView timeOutput;
    TextView bestDayOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);

        nameOutput = findViewById(R.id.name_output);
        phoneOutput = findViewById(R.id.phone_output);
        emailOutput = findViewById(R.id.email_output);
        addressOutput = findViewById(R.id.address_output);
        pictureOutput = findViewById(R.id.picture_output);
        websiteOutput = findViewById(R.id.web_output);
        birthdayOutput = findViewById(R.id.birthday_output);
        timeOutput = findViewById(R.id.time_output);
        bestDayOutput = findViewById(R.id.days_output);

        Intent intent = getIntent();
        person = (Person)intent.getExtras().get("persons");
        showPerson(person);

        phoneOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"+person.getPhone()));
                startActivity(i);
            }
        });

        emailOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL,new String[] {person.getMail()});
                i.putExtra(Intent.EXTRA_SUBJECT,"Email from contact");
                i.putExtra(Intent.EXTRA_TEXT, "Email text");
                i.setType("text/html");
                startActivity(i);
            }
        });

        addressOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:0,0?q="+Uri.encode(person.getAddress()));
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
            }
        });

        websiteOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("http://"+person.getWebsite()));
                startActivity(i);
            }
        });




    }



    private void showPerson(Person person) {
        if(person.getName() != null){
            nameOutput.setText(person.getName());
        }
        if(person.getPhone() != null){
            phoneOutput.setText(person.getPhone());
            phoneOutput.setTextColor(Color.BLUE);
        }
        if(person.getMail() != null){
            emailOutput.setText(person.getMail());
            emailOutput.setTextColor(Color.BLUE);
        }
        if(person.getAddress() != null){
            addressOutput.setText(person.getAddress());
            addressOutput.setTextColor(Color.BLUE);
        }
        if(person.getPhoto() != null){
            pictureOutput.setImageBitmap(person.getPhoto());
        }
        if(person.getWebsite() != null){
            websiteOutput.setText(person.getWebsite());
            websiteOutput.setTextColor(Color.BLUE);
        }
        if(person.getDate() != null){
            birthdayOutput.setText(person.getDate());
        }
        if(person.getTime() != null){
            timeOutput.setText(person.getTime());
        }
        if(person.getDays() != null){
            bestDayOutput.setText(person.getDays());
        }

    }
}
