package com.droidhelios.infiniterecyclaradapter;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.infinite.listener.OnLoadMoreItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements OnLoadMoreItems {

    private List<Contact> contacts = new ArrayList<>();
    private ContactAdapter mAdapter;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDummyData();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContactAdapter(contacts, this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(this);
    }


    @Override
    public void onLoadMore() {
        Log.d("@infinite", "onLoadMore");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("@infinite", "more data loaded");
                mAdapter.finish();

                //Generating more data
                setDummyData();
                mAdapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    private void setDummyData() {
        //set dummy data
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.setPhone(phoneNumberGenerating());
            contact.setEmail("droidhelios" + i + "@gmail.com");
            contacts.add(contact);
        }
    }

    private String phoneNumberGenerating() {
        int low = 100000000;
        int high = 999999999;
        int randomNumber = random.nextInt(high - low) + low;

        return "0" + randomNumber;
    }

}