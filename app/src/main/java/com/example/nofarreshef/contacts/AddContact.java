package com.example.nofarreshef.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AddContact extends AppCompatActivity {

    ImageView imageP;
    Bitmap bitmapP;
    ArrayList<Person> persons= new ArrayList<>();
    final int CAMERA_REQUSET_PERSON=1;

    TextView datePick;
    TextView timePick;
    TextView bestDay;
    String daysPick="";

    ArrayList<Person> personL = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        datePick = findViewById(R.id.date_output);
        timePick = findViewById(R.id.time_output);

        bestDay= findViewById(R.id.best_days_output);


        imageP = (ImageView)findViewById(R.id.picture_contact);


        Button takePicture = findViewById(R.id.take_pic);
        Button dateButton = findViewById(R.id.pick_birth_date);
        Button timeButton = findViewById(R.id.pick_time_date);
        Button dayButton = findViewById(R.id.pick_best_days);

        takePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAMERA_REQUSET_PERSON);
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(AddContact.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       datePick.setText("Birthday chosen: " + dayOfMonth+ "/" + (month+1) + "/" + year);
                    }
                },year,month,day);
                dpd.show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(AddContact.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       timePick.setText(hourOfDay + " : " + minute);
                    }
                },hour,minutes,true);
                tpd.show();
            }
        });
        //final String[] daysPick = new String[1];
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddContact.this);
                builder.setTitle("pick best day").setMultiChoiceItems(R.array.days, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        String [] days = getResources().getStringArray(R.array.days);
                        //daysPick[0] = daysPick[0] + "," + days[which];
                        daysPick = daysPick + ", " + days[which];

                    }
                });
                        builder.setPositiveButton("enter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //bestDay.setText(daysPick[0]);
                                daysPick=daysPick.substring(2);
                                bestDay.setText(daysPick);

                            }
                        }).create().show();
            }
        });

        try{
            FileInputStream fis = openFileInput("persons.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            personL = (ArrayList<Person>) ois.readObject();
            if(personL == null){
                personL = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Save Alert");
                builder.setMessage("Are you sure you want to save the content?");
                builder.setPositiveButton("Yes", new MyDialogListener());
                builder.setNegativeButton("No", new MyDialogListener());
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
                /*
                String nameP = name.getText().toString();
                String phoneP = phone.getText().toString();
                String mailP = mail.getText().toString();
                String addressP = address.getText().toString();
                String webP = web.getText().toString();
                String dateP = datePick.getText().toString();
                String timeP = timePick.getText().toString();
                String bestDayP = bestDay.getText().toString();
                Person person = new Person(nameP,phoneP,mailP,addressP,webP,dateP,timeP,bestDayP,bitmapP);
                persons.add(person);

                try{
                    FileOutputStream fos = openFileOutput("persons", MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(persons);
                    oos.close();

                    name.setText("");
                    phone.setText("");
                    mail.setText("");
                    address.setText("");
                    web.setText("");
                    datePick.setText("");
                    timePick.setText("");

                    imageP.setImageBitmap(null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */

            case R.id.action_back:
                Intent i = new Intent(AddContact.this, Contacts.class) ;
                startActivity(i);
                finish();
                return  true;


        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected  void onActivityResult(int requestCode, int resultCode,Intent data){
        if (requestCode == CAMERA_REQUSET_PERSON && resultCode == RESULT_OK) {
            bitmapP= (Bitmap)data.getExtras().get("data");
            imageP.setImageBitmap(bitmapP);
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private class MyDialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE){
                Person person = new Person();

                EditText name = findViewById(R.id.name);
                person.setName(name.getText().toString());
                EditText phone = findViewById(R.id.phone_number);
                person.setPhone(phone.getText().toString());
                EditText mail = findViewById(R.id.mail);
                person.setMail(mail.getText().toString());
                EditText address = findViewById(R.id.address);
                person.setAddress(address.getText().toString());
                EditText web = findViewById(R.id.website);
                person.setWebsite(web.getText().toString());
                person.setPhoto(bitmapP);
                person.setTime(timePick.getText().toString());
                person.setDate(datePick.getText().toString());
                person.setDays(daysPick);
                personL.add(person);

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
    }
}
