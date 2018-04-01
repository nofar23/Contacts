package com.example.nofarreshef.contacts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<Person> persons;
    Context context;
    PersonAdapterListener listener;

    interface  PersonAdapterListener{
        void  onItemClicked(int position, View view);
        void  onItemLongClicked(int position, View view);
    }

    public  void setListener(PersonAdapterListener listener){
        this.listener=listener;
    }

    public PersonAdapter(List<Person> persons, Context context) {
        this.persons = persons;
        this.context = context;
    }

    public  class PersonViewHolder extends RecyclerView.ViewHolder{
        ImageView personPicture;
        TextView personName;
        public PersonViewHolder(final View itemView) {
            super(itemView);
            personPicture = itemView.findViewById(R.id.contact_picture_output);
            personName = itemView.findViewById(R.id.contact_name_output);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(getAdapterPosition(),v);

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClicked(getAdapterPosition(),v);
                    return false;
                }
            });
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        PersonViewHolder allViewHolder = new PersonViewHolder(view);
        return allViewHolder;
    }

    @Override
    public void onBindViewHolder(PersonAdapter.PersonViewHolder holder, int position) {
        holder.personPicture.setImageBitmap(persons.get(position).getPhoto());
        holder.personName.setText(persons.get(position).getName()+ "");

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
