package com.example.yuki.prototipo;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yuki.prototipo.sql.DataBase;
import com.example.yuki.prototipo.sql.Question_Repository;
import com.example.yuki.prototipo.sql.Selected_Questions;

public class SavedQuestions extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lstSavedQuestions;
    private Selected_Questions selectedQuestions;
    private ArrayAdapter<Question> adpQuestions;

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

        if(this.conexaoBD())
        {
            selectedQuestions = new Selected_Questions(conn);
            adpQuestions = selectedQuestions.buscarQuestoesSelecionadas(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Question question = adpQuestions.getItem(position);

        Intent it = new Intent(this, ShowQuestion.class);
        it.putExtra("QUESTION",question);

        startActivityForResult(it,0);

    }

}
