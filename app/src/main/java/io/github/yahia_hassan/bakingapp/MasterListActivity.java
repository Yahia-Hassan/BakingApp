package io.github.yahia_hassan.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.github.yahia_hassan.bakingapp.POJO.Recipe;

public class MasterListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        setTitle("Recipe Steps");
        Intent intent  = getIntent();
        Recipe recipe = intent.getParcelableExtra("recipe");
        TextView textView = findViewById(R.id.master_list_text_view);
        textView.setText(recipe.getName());
    }
}
