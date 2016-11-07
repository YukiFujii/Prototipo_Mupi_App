package com.example.yuki.prototipo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.yuki.prototipo.sql.Filter;

public class Home extends AppCompatActivity {

    private Button btnEscolherQuestoes;
    private Button btnVisualizarQuestoesSalvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnEscolherQuestoes = (Button)findViewById(R.id.btnEscolherQuestoes);
        btnVisualizarQuestoesSalvas = (Button)findViewById(R.id.btnVisualizarQuestoesSalvas);

    }

    public void callUnselectedQuestions(View view)
    {
        Intent it = new Intent(this, Filter.class);
        it.putExtra("BUTTON","Choose");
        startActivityForResult(it, 0);
    }

    public void callselectedQuestions(View view)
    {
        Intent it = new Intent(this, Filter.class);
        it.putExtra("BUTTON","Visualize");
        startActivityForResult(it, 0);
    }

}
