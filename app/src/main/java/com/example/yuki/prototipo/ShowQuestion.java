package com.example.yuki.prototipo;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuki.prototipo.sql.DataBase;
import com.example.yuki.prototipo.sql.UnselectedQuestions;
import com.example.yuki.prototipo.sql.SelectedQuestions;

public class ShowQuestion extends AppCompatActivity {

    private Button btnOk;
    private Button btnDelete;
    private TextView txtShowQuestion;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private UnselectedQuestions unselectedQuestions;
    private SelectedQuestions selectedQuestions;
    private Question question;
    private char level='-';
    private String tag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_show_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.btnOk = (Button)findViewById(R.id.btnOk);
        this.btnDelete = (Button)findViewById(R.id.btnDelete);
        this.txtShowQuestion = (TextView)findViewById(R.id.txtShowQuestion);

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("SELECTED_QUESTIONS")))
            this.question = (Question) bundle.getSerializable("SELECTED_QUESTIONS");

        if ((bundle != null) && (bundle.containsKey("UNSELECTED_QUESTIONS")))
            this.question = (Question) bundle.getSerializable("UNSELECTED_QUESTIONS");

        if ((bundle != null) && (bundle.containsKey("LEVEL")))
            this.level = (char) bundle.getSerializable("LEVEL");

        if ((bundle != null) && (bundle.containsKey("TAG")))
            this.tag = (String) bundle.getSerializable("TAG");

        this.txtShowQuestion.setText(this.question.getQuestionText());

    }

    private boolean connectionBD()
    {
        try {

            this.dataBase = new DataBase(this);
            this.conn = this.dataBase.getWritableDatabase();

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }

    private void callScreenSelectedQuestions()
    {
        Intent it = new Intent(this, ScreenSelectedQuestions.class);

        if(this.level != '-')
            it.putExtra("LEVEL",this.level);

        if(this.tag!= "")
            it.putExtra("TAG",this.tag);

        startActivityForResult(it,0);
        finish();
    }

    public void buttonOk(View view)
    {
        this.callScreenSelectedQuestions();
    }

    public void buttonDelete(View view)
    {

        if(this.connectionBD())
        {
            this.unselectedQuestions = new UnselectedQuestions(conn);
            this.unselectedQuestions.insert(this,this.question);

            this.selectedQuestions = new SelectedQuestions(conn);
            this.selectedQuestions.delete(this.question.getId());

            this.callScreenSelectedQuestions();
        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Falha ao deletar questão.");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

    }

}
