package com.example.yuki.prototipo;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yuki.prototipo.sql.DataBase;
import com.example.yuki.prototipo.sql.SelectedQuestions;

import java.util.ArrayList;

public class ScreenSelectedQuestions extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lstSavedQuestions;
    private SelectedQuestions selectedQuestions;
    private ArrayAdapter<Question> adpQuestions;
    private char level = '-';
    private String tag = "";
    private ArrayList<Integer> arrayQuestions;

    private DataBase dataBase;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstSavedQuestions = (ListView)findViewById(R.id.lstSavedQuestions);
        lstSavedQuestions.setOnItemClickListener(this);

        if(this.connectionBD())
        {
            selectedQuestions = new SelectedQuestions(conn);

            Bundle bundle = getIntent().getExtras();

            if ((bundle != null) && (bundle.containsKey("LEVEL")) && (bundle.containsKey("TAG")))
            {
                Log.i("Entrou","LEVEL & TAG");
                this.level = (char) bundle.getSerializable("LEVEL");
                this.tag = (String) bundle.getSerializable("TAG");
                this.arrayQuestions = this.selectedQuestions.catchArrayQuestionTagLevel(this.tag,Character.toString(this.level));
            }

            else if ((bundle != null) && (bundle.containsKey("LEVEL")))
            {
                Log.i("Entrou","LEVEL");
                this.level = (char) bundle.getSerializable("LEVEL");
                this.arrayQuestions = this.selectedQuestions.catchArrayQuestionLevel(Character.toString(this.level));

            }

            else if ((bundle != null) && (bundle.containsKey("TAG")))
            {
                Log.i("Entrou","TAG");
                this.tag = (String) bundle.getSerializable("TAG");
                this.arrayQuestions = this.selectedQuestions.catchArrayQuestionTag(this.tag);

            }

            else
            {
                Log.i("Entrou","SEM FILTRO");
                this.arrayQuestions = this.selectedQuestions.catchArrayQuestion();
            }

            adpQuestions = selectedQuestions.createArrayAdapterQuestion(this,this.arrayQuestions);
            lstSavedQuestions.setAdapter(adpQuestions);

        }
        else
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar com banco!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        adpQuestions = selectedQuestions.searchSelectedQuestions(this);
        lstSavedQuestions.setAdapter(adpQuestions);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Question question = adpQuestions.getItem(position);

        Intent it = new Intent(this, ShowQuestion.class);
        it.putExtra("SELECTED_QUESTIONS",question);

        if(this.level!='-')
            it.putExtra("LEVEL",this.level);

        if(this.tag!="")
            it.putExtra("TAG",this.tag);

        startActivityForResult(it,0);
        finish();
    }

}
