package com.example.yuki.prototipo;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuki.prototipo.sql.DataBase;
import com.example.yuki.prototipo.sql.Question_Repository;

public class ShowQuestion extends AppCompatActivity {

    private Button btnOk;
    private Button btnDelete;
    private TextView txtShowQuestion;
    private Question question;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Question_Repository repositorioDeQuestoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnOk = (Button)findViewById(R.id.btnOk);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        txtShowQuestion = (TextView)findViewById(R.id.txtShowQuestion);

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("QUESTION")))
            question = (Question) bundle.getSerializable("QUESTION");

        txtShowQuestion.setText(question.getQuestion());

    }

    public void buttonOk(View view)
    {
        finish();
    }

    public void buttonDelete(View view)
    {
        if(this.conexaoBD())
        {
            repositorioDeQuestoes = new Question_Repository(conn);
            repositorioDeQuestoes.delete(this.question.getId());
            finish();
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Não foi possível deletar questão.");
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

}
