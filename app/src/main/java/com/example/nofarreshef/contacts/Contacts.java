package com.example.nofarreshef.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Contacts extends AppCompatActivity {
    Button add;
    Button show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        add= findViewById(R.id.add_contact);
        show=findViewById(R.id.show_contact);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(Contacts.this, AddContact.class) ;
               startActivity(i);
               finish();

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Contacts.this,ContactList.class);
                startActivity(i);

            }
        });
    }
}
