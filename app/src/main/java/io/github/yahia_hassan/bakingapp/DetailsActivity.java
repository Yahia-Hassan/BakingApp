package io.github.yahia_hassan.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Step;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        ArrayList<Step> stepArrayList = intent.getParcelableArrayListExtra("steps_list");
        int position = intent.getIntExtra("position", 0);

        TextView textView = findViewById(R.id.details_text_view);
        textView.setText(stepArrayList.get(position).getShortDescription());
    }
}
