package com.example.first;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


public class Myactivity extends AppCompatActivity implements View.OnClickListener {
    EditText eText;
    Button btn;

    private TextView mtw;
    private Object InputStream;
    private ProgressBar spinner;
    private String value = null;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eText = (EditText) findViewById(R.id.edittext);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);


        spinner = (ProgressBar)findViewById(R.id.determinateBar);

        spinner.setVisibility(View.VISIBLE); //set the bar visible

        mtw = findViewById(R.id.textView1);




        final MyViewModel model = new ViewModelProvider(this).get(MyViewModel.class);

        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // Update the UI, in this case, a TextView.
                mtw.setText(newName);
            }
        };






        model.getFact(new MyViewModel.OnFactGetListener() {
            @Override
            public void onFactGet(final String fact) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast msg = Toast.makeText(getBaseContext(),fact,Toast.LENGTH_LONG);
                        //msg.show();
                        model.getData().setValue(fact);
                    }
                });
            }
        });
        model.getData().observe(this, nameObserver);

        spinner.setVisibility(View.GONE);
    }




    @Override
    public void onClick(View v) {
        String str = eText.getText().toString();
        Toast msg = Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG);
        msg.show();
    }
}



