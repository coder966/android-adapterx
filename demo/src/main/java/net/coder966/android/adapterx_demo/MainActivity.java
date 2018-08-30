package net.coder966.android.adapterx_demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initial data
        List<Person> firstList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            firstList.add(new Person("Person #"+i, i%2==0 ? Gender.MALE : Gender.FEMALE));
        }

        // more data
        List<Person> secondList = new ArrayList<>();
        for (int i = 21; i <= 40; i++) {
            firstList.add(new Person("Person #"+i, i%2==0 ? Gender.MALE : Gender.FEMALE));
        }


        // prepare the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // prepare the adapter
        MyAdapter adapter = new MyAdapter(firstList);

        // set the callback
        adapter.setOnLoadMoreListener((adapterx, lastItem) -> {

            // delay for 3 seconds just to demonstrate network delay
            new Handler().postDelayed(() -> {
                // when you get your data, load it
                adapterx.load(secondList);
            }, 3000);

        });

        // set the adapter to the recycler view
        recyclerView.setAdapter(adapter);
    }
}
