package com.example.yuki.prototipo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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

    public void chamarEscolherQuestoes(View view)
    {
        Intent it = new Intent(this, MainActivity.class);
        startActivityForResult(it, 0);
    }

}
