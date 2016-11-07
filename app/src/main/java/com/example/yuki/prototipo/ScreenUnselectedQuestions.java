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
import com.example.yuki.prototipo.sql.UnselectedQuestions;
import com.example.yuki.prototipo.sql.SelectedQuestions;

import java.util.ArrayList;


public class ScreenUnselectedQuestions extends AppCompatActivity {

    private TextView txtQuestionText;
    private TextView txtQuestionHeader;
    private Button btnSave;
    private Button btnDiscart;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private UnselectedQuestions unselectedQuestions;
    private SelectedQuestions selectedQuestions;
    private Question question;
    private char level = '-';
    private String tag = "";
    private ArrayList<Integer> arrayQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtQuestionText = (TextView) findViewById(R.id.txtQuestionText);
        txtQuestionHeader = (TextView) findViewById(R.id.txtQuestionHeader);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDiscart = (Button) findViewById(R.id.btnDiscart);

        if(this.connectionBD())
        {
            this.unselectedQuestions = new UnselectedQuestions(conn);

            //IMPROVISO!!!!
            this.buscarQuestoesDeFora();

            Bundle bundle = getIntent().getExtras();

            if ((bundle != null) && (bundle.containsKey("LEVEL")) && (bundle.containsKey("TAG")))
            {
                Log.i("Entrou","LEVEL & TAG");
                this.level = (char) bundle.getSerializable("LEVEL");
                this.tag = (String) bundle.getSerializable("TAG");
                this.arrayQuestions = this.unselectedQuestions.catchArrayQuestionTagLevel(this.tag,Character.toString(this.level));
                this.question = this.getQuestion(this.arrayQuestions);
            }

            else if ((bundle != null) && (bundle.containsKey("LEVEL")))
            {
                Log.i("Entrou","LEVEL");
                this.level = (char) bundle.getSerializable("LEVEL");
                this.arrayQuestions = this.unselectedQuestions.catchArrayQuestionLevel(Character.toString(this.level));
                this.question = this.getQuestion(this.arrayQuestions);
            }

            else if ((bundle != null) && (bundle.containsKey("TAG")))
            {
                Log.i("Entrou","TAG");
                this.tag = (String) bundle.getSerializable("TAG");
                this.arrayQuestions = this.unselectedQuestions.catchArrayQuestionTag(this.tag);

                this.question = this.getQuestion(this.arrayQuestions);
            }

            else
            {
                Log.i("Entrou","SEM FILTRO");
                this.arrayQuestions = this.unselectedQuestions.catchArrayQuestion();
                this.question = this.getQuestion(this.arrayQuestions);
            }

            if(this.question==null)
            {
                txtQuestionHeader.setText("Todas questões foram visualizadas!");
                btnDiscart.setEnabled(false);
                btnSave.setEnabled(false);
            }
            else
            {
                txtQuestionHeader.setText(this.question.getQuestionHeader());
                txtQuestionText.setText(this.question.getQuestionText());
            }

        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar com banco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

    private Question getQuestion(ArrayList<Integer> arrayQuestions)
    {
        Question question = null;

        if(!arrayQuestions.isEmpty())
            question = this.unselectedQuestions.catchQuestion(this.arrayQuestions.get(0));

        else
            this.unselectedQuestions.updateWasVisualized();

        return question;
    }

    private boolean connectionBD()
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
        this.selectedQuestions = new SelectedQuestions(conn);

        this.selectedQuestions.insert(this.question);
        this.unselectedQuestions.delete(this.question.getId());
    }

    public void btnSave(View view)
    {
        this.question.setWasVisualized(1);

        Log.i("setVisualizado",""+question.getWasVisualized());

        this.unselectedQuestions.update(this.question);

        this.saveQuestion();

        this.callScreenUnselectedQuestions();
    }

    public void btnDiscart(View view)
    {
        this.question.setWasVisualized(1);

        this.unselectedQuestions.update(this.question);

        this.callScreenUnselectedQuestions();
    }

    private void callScreenUnselectedQuestions()
    {
        Intent it = new Intent(this, ScreenUnselectedQuestions.class);
        if(this.level!='-')
            it.putExtra("LEVEL",this.level);

        if(this.tag!="")
            it.putExtra("TAG",this.tag);

        startActivityForResult(it, 0);
        finish();
    }


    //======================== Improvisso Para Testes ======================================
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

            q1.addTag("tag1");
            q1.addTag("primeiraTag");
            q1.addTag("TagQuestian1");
            q1.addTag("numero1");
            q1.addTag("bla");

            q3.addTag("bla");

            ArrayList<String> tagsQuestion4 = new ArrayList<String>();
            tagsQuestion4.add("bla");

            q4.addTag(tagsQuestion4);

            unselectedQuestions.insert(this,q1);
            unselectedQuestions.insert(this,q2);
            unselectedQuestions.insert(this,q3);
            unselectedQuestions.insert(this,q4);
            unselectedQuestions.insert(this,q5);
            unselectedQuestions.insert(this,q6);
            unselectedQuestions.insert(this,q7);
            unselectedQuestions.insert(this,q8);
    }

}
