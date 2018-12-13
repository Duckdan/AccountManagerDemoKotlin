package com.study.yang.accoutmanagerdemokotlin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ((AutoCompleteTextView) findViewById(R.id.email)).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{}) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(Main2Activity.this, android.R.layout.simple_list_item_1, null);
                }
                return convertView;
            }
        });
        Intent intent = new Intent(this,MainActivity.class);

    }

}
