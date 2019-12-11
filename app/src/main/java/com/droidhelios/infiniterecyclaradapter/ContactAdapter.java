package com.droidhelios.infiniterecyclaradapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.infinite.LoadMoreAdapter;

import java.util.List;


public class ContactAdapter extends LoadMoreAdapter {
    private Activity activity;
    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts, Activity activity) {
        super(contacts, R.layout.item_loading);
        this.contacts = contacts;
        this.activity = activity;
    }

    @Override
    protected RecyclerView.ViewHolder onAbstractCreateViewHolder(ViewGroup parent, int var2) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_recycler_view_row, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    protected void onAbstractBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            Contact contact = contacts.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.phone.setText(contact.getEmail());
            userViewHolder.email.setText(contact.getPhone());
        }
    }


    private class UserViewHolder extends RecyclerView.ViewHolder {
        TextView phone;
        TextView email;

        UserViewHolder(View view) {
            super(view);
            phone = view.findViewById(R.id.txt_phone);
            email = view.findViewById(R.id.txt_email);
        }
    }
}