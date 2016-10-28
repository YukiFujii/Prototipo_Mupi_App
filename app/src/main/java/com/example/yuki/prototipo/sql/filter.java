package com.example.yuki.prototipo.sql;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.yuki.prototipo.MainActivity;
import com.example.yuki.prototipo.Question;
import com.example.yuki.prototipo.R;
import com.example.yuki.prototipo.SavedQuestions;
import com.example.yuki.prototipo.ShowQuestion;

public class filter extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spnLevel;
    private EditText editTag;
    private Button btnBuscar;
    private char level = '-';
    private String chamadoPor;

    private ArrayAdapter<String> adpLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spnLevel = (Spinner)findViewById(R.id.spnLevel);
        editTag = (EditText)findViewById(R.id.editTag);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("BUTTON")))
            this.chamadoPor = (String) bundle.getSerializable("BUTTON");
        else
            finish();

        adpLevel = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        adpLevel.add("ALL");
        adpLevel.add("EASY");
        adpLevel.add("MEDIUM");
        adpLevel.add("HARD");

        spnLevel.setAdapter(adpLevel);

        spnLevel.setOnItemSelectedListener(this);

    }

    public void buttonBuscar(View view)
    {
        if(this.chamadoPor.equals("Escolher"))
        {
            Intent it = new Intent(this, MainActivity.class);
            if (this.level != '-')
                it.putExtra("LEVEL", level);
            if(!this.editTag.getText().toString().equals(""))
            {
                it.putExtra("TAG",this.editTag.getText().toString());
                Log.i("Filtrando por tag",this.editTag.getText().toString());
            }
            startActivityForResult(it, 0);
            finish();
        }
        else
        {
            Intent it = new Intent(this, SavedQuestions.class);
            if (this.level != '-')
                it.putExtra("LEVEL", level);
            if(!this.editTag.getText().toString().equals(""))
            {
                it.putExtra("TAG",this.editTag.getText().toString());
                Log.i("Filtrando por tag",this.editTag.getText().toString());
            }
            startActivityForResult(it, 0);
            finish();
        }
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id)
    {
        switch (position)
        {
            case 1:
                this.level = 'E';
                break;

            case 2:
                this.level = 'M';
                break;

            case 3:
                this.level = 'H';
                break;

            default:
                this.level = '-';
                break;
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}
