package com.example.yuki.prototipo;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuki.prototipo.sql.DataBase;
import com.example.yuki.prototipo.sql.Question_Repository;
import com.example.yuki.prototipo.sql.Selected_Questions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtQuestion;
    private Button btnSave;
    private Button btnDiscart;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Question_Repository repositorioDeQuestoes;
    private Selected_Questions saveQuestions;

    private ArrayList<Question> arrayQuestions;
    private int index =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDiscart = (Button) findViewById(R.id.btnDiscart);


        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("INDEX")))
            index = (int) bundle.getSerializable("INDEX");

        Log.i("Index",""+index);

        try
        {

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioDeQuestoes = new Question_Repository(conn);

            arrayQuestions = repositorioDeQuestoes.buscarQuestoes();

            for (;;)
            {
                if (arrayQuestions.size() == 0)
                {
                    buscarQuestoesDeFora();
                    arrayQuestions = repositorioDeQuestoes.buscarQuestoes();
                }
                else
                    break;
            }


            txtQuestion.setText(arrayQuestions.get(this.index).getQuestion());

        }
        catch (Exception ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar banco: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

    private void saveQuestion()
    {
        saveQuestions = new Selected_Questions(conn);

        Question aux = arrayQuestions.get(index);

        saveQuestions.inserir(aux);
    }

    public void btnSave(View view)
    {

        if(!(index+1 == this.arrayQuestions.size()))
        {
            saveQuestion();

            this.index++;

            ArrayList<Question> aux = saveQuestions.buscarQuestoesSelecionadas();
            Log.i("Questões salvas",""+aux.size());

            Intent it = new Intent(this, MainActivity.class);
            it.putExtra("INDEX", this.index);
            startActivityForResult(it, 0);
            finish();
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Não existem mais questões no repositório");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

    public void btnDiscart(View view)
    {
        this.index++;

        if(index < this.arrayQuestions.size())
        {
            Intent it = new Intent(this, MainActivity.class);
            it.putExtra("INDEX", this.index);
            startActivityForResult(it, 0);
            finish();
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Não existem mais questões no repositório");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

    private void buscarQuestoesDeFora()
    {

            Question q1 = new Question(1, "Questão 1.");
            Question q2 = new Question(2, "Questão 2.");
            Question q3 = new Question(3, "Questão 3.");
            Question q4 = new Question(4, "Questão 4.");
            Question q5 = new Question(5, "Questão 5.");
            Question q6 = new Question(6, "Questão 6.");
            Question q7 = new Question(7, "Questão 7.");
            Question q8 = new Question(8, "Questão 8.");

            repositorioDeQuestoes.inserir(q1);
            repositorioDeQuestoes.inserir(q2);
            repositorioDeQuestoes.inserir(q3);
            repositorioDeQuestoes.inserir(q4);
            repositorioDeQuestoes.inserir(q5);
            repositorioDeQuestoes.inserir(q6);
            repositorioDeQuestoes.inserir(q7);
            repositorioDeQuestoes.inserir(q8);
    }

}
