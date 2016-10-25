package com.example.yuki.prototipo;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuki.prototipo.sql.DataBase;
import com.example.yuki.prototipo.sql.Question_Repository;
import com.example.yuki.prototipo.sql.Selected_Questions;


public class MainActivity extends AppCompatActivity {

    private TextView txtQuestion;
    private Button btnSave;
    private Button btnDiscart;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Question_Repository repositorioDeQuestoes;
    private Selected_Questions saveQuestions;
    private Question question;
    private char level = '-';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("Abriu programa","true");

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDiscart = (Button) findViewById(R.id.btnDiscart);

        if(this.conexaoBD())
        {
            repositorioDeQuestoes = new Question_Repository(conn);

            //IMPROVISO!!!!
            this.buscarQuestoesDeFora();

            Bundle bundle = getIntent().getExtras();

            if ((bundle != null) && (bundle.containsKey("LEVEL")))
            {
                this.level = (char) bundle.getSerializable("LEVEL");
                this.question = this.repositorioDeQuestoes.catchNextQuestion(Character.toString(this.level));
            }
            else
                this.question = this.repositorioDeQuestoes.catchNextQuestion();

            if(this.question==null)
            {
                txtQuestion.setText("Todas questões foram visualizadas!");
                btnDiscart.setEnabled(false);
                btnSave.setEnabled(false);
                this.repositorioDeQuestoes.updateFoiVisualizado();
            }
            else
                txtQuestion.setText(this.question.getQuestionHeader());

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

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }


    private void saveQuestion()
    {
        this.saveQuestions = new Selected_Questions(conn);

        this.saveQuestions.insert(this.question);
        this.repositorioDeQuestoes.delete(this.question.getId());

        Log.i("Deletando questao do ID",""+question.getId());
    }

    public void btnSave(View view)
    {
        this.question.setFoiVisualizado(1);

        Log.i("setVisualizado",""+question.getFoiVisualizado());

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
        if(this.level!='-')
            it.putExtra("LEVEL",this.level);
        startActivityForResult(it, 0);
        finish();
    }

    private void buscarQuestoesDeFora()
    {

            Question q1 = new Question(1,"Question header 1","Question text 1",'E');
            Question q2 = new Question(2,"Question header 2","Question text 2",'M');
            Question q3 = new Question(3,"Question header 3","Question text 3",'H');
            Question q4 = new Question(4,"Question header 4","Question text 4",'E');
            Question q5 = new Question(5,"Question header 5","Question text 5",'M');
            Question q6 = new Question(6,"Question header 6","Question text 6",'H');
            Question q7 = new Question(7,"Question header 7","Question text 7",'E');
            Question q8 = new Question(8,"Question header 8","Question text 8",'M');

            repositorioDeQuestoes.insert(this,q1);
            repositorioDeQuestoes.insert(this,q2);
            repositorioDeQuestoes.insert(this,q3);
            repositorioDeQuestoes.insert(this,q4);
            repositorioDeQuestoes.insert(this,q5);
            repositorioDeQuestoes.insert(this,q6);
            repositorioDeQuestoes.insert(this,q7);
            repositorioDeQuestoes.insert(this,q8);
    }

}
