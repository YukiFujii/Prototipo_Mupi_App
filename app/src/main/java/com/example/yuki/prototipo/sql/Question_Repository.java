package com.example.yuki.prototipo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.yuki.prototipo.Question;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yuki on 14/10/16.
 */

public class Question_Repository {

    private SQLiteDatabase conn;

    public Question_Repository(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Question question)
    {
        ContentValues values = new ContentValues();

        values.put("_id",question.getId());
        values.put("QUESTION",question.getQuestion());
        values.put("FOI_VISUALIZADO",question.getFoiVisualizado());

        return values;
    }

    public void insert(Context context, Question question)
    {
        Log.i("Inserindo questao","true");
        Log.i("Tem questao",""+this.hasQuestion(question));

        if(!(FacadeSQL.hasQuestion(context,question)))
            conn.insertOrThrow("QUESTIONS", null, preencheContentValues(question));
    }

    public void insert(Question question)
    {
            conn.insertOrThrow("QUESTIONS", null, preencheContentValues(question));
    }

    public void update(Question question)
    {
        conn.update("QUESTIONS",preencheContentValues(question),"_id = ?",new String[]{question.getId()+""});
    }

    public void delete(int id)
    {
        conn.delete("QUESTIONS","_id = ?",new String[]{""+id});
    }

    public Question catchNextQuestion ()
    {
        Question question = null;

        Cursor cursor = conn.query("QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        Log.i("Questoes no banco",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            do
            {
                int foiVisualizado = cursor.getInt(2);

                // se foiVisualizado igual a 0 significa que questão não foi visualizada
                if(foiVisualizado==0)
                {
                    question = new Question();
                    question.setId(cursor.getInt(0));
                    question.setQuestion(cursor.getString(1));
                    question.setFoiVisualizado(cursor.getInt(2));
                    break;
                }

            }while (cursor.moveToNext());
        }

        return question;
    }


    public void updateFoiVisualizado ()
    {
        Question question;

        Cursor cursor = conn.query("QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                    question = new Question();
                    question.setId(cursor.getInt(0));
                    question.setQuestion(cursor.getString(1));;

                    question.setFoiVisualizado(0);

                    this.update(question);

            }while (cursor.moveToNext());
        }
    }

    public boolean hasQuestion(Question question)
    {
        Cursor cursor = conn.query("QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                int id = cursor.getInt(0);

                if(id==question.getId())
                    return true;

            }while (cursor.moveToNext());

            return false;
        }

        return false;

    }
}
