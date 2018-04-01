package com.example.nofarreshef.contacts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ContactList extends AppCompatActivity {
    ArrayList<Person> personL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);


        try{
            FileInputStream fis = openFileInput("persons.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            personL = (ArrayList<Person>)ois.readObject();
            ois.close();
            if(personL != null){
                final PersonAdapter adapter = new PersonAdapter(personL,this);
                adapter.setListener(new PersonAdapter.PersonAdapterListener() {
                    @Override
                    public void onItemClicked(int position, View view) {
                        Intent i = new Intent(ContactList.this, ShowContact.class);
                        i.putExtra("persons",personL.get(position));
                        startActivity(i);
                    }

                    @Override
                    public void onItemLongClicked(int position, View view) {
                        personL.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                });
                recyclerView.setAdapter(adapter);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void setFile(){
        Person person = new Person();



        try{
            FileOutputStream fos = openFileOutput("persons.dat", Activity.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(personL);
            oos.close();

            //imageP.setImageBitmap(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
