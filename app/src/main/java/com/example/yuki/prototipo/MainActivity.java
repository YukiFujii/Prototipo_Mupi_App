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
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDiscart = (Button) findViewById(R.id.btnDiscart);

        if(this.conexaoBD())
        {
            //IMPROVISO!!!!
            this.buscarQuestoesDeFora();

            this.question = this.repositorioDeQuestoes.catchNextQuestion();

            if(this.question==null)
            {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Todas questões foram visualizadas!");
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }
            else
                txtQuestion.setText(this.question.getQuestion());

        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar com banco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

    private boolean conexaoBD()
    {
        try {

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioDeQuestoes = new Question_Repository(conn);

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }


    private void saveQuestion()
    {
        saveQuestions = new Selected_Questions(conn);

        saveQuestions.inserir(this.question);
    }

    public void btnSave(View view)
    {
        this.question.setFoiVisualizado(1);

        this.repositorioDeQuestoes.update(this.question);

        this.saveQuestion();

        this.chamarMainActivity();
    }

    public void btnDiscart(View view)
    {
        this.question.setFoiVisualizado(1);

        this.repositorioDeQuestoes.update(this.question);

        this.chamarMainActivity();
    }

    private void chamarMainActivity()
    {
        Intent it = new Intent(this, MainActivity.class);
        startActivityForResult(it, 0);
        finish();
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

            repositorioDeQuestoes.insert(q1);
            repositorioDeQuestoes.insert(q2);
            repositorioDeQuestoes.insert(q3);
            repositorioDeQuestoes.insert(q4);
            repositorioDeQuestoes.insert(q5);
            repositorioDeQuestoes.insert(q6);
            repositorioDeQuestoes.insert(q7);
            repositorioDeQuestoes.insert(q8);
    }

}
