package com.example.yuki.prototipo.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yuki.prototipo.Question;

import java.util.ArrayList;

/**
 * Created by yuki on 14/10/16.
 */

public class Selected_Questions {

    private SQLiteDatabase conn;

    public Selected_Questions(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Question question)
    {
        ContentValues values = new ContentValues();

        values.put("_id",question.getId());
        values.put("QUESTION",question.getQuestion());

        return values;
    }

    public void inserir(Question question)
    {
        conn.insertOrThrow("SELECTED_QUESTIONS", null, preencheContentValues(question));
    }

    public void alterar(Question question)
    {
        conn.update("SELECTED_QUESTIONS",preencheContentValues(question),"_id = ?",new String[]{question.getId()+""});
    }

    public void excluir(long id)
    {
        conn.delete("SELECTED_QUESTIONS","_id = ?",new String[]{id+""});
    }

    public ArrayList<Question> buscarQuestoesSelecionadas ()
    {
        ArrayList<Question> arrayQuestoes = new ArrayList<Question>();

        Cursor cursor = conn.query("SELECTED_QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                Question question = new Question();

                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));

                arrayQuestoes.add(question);

            }while (cursor.moveToNext());
        }

        return arrayQuestoes;
    }
}
