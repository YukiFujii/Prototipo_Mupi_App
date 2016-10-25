package com.example.yuki.prototipo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yuki.prototipo.Question;
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
        values.put("QUESTION_HEADER",question.getQuestionHeader());
        values.put("QUESTION_TEXT",question.getQuestionText());
        values.put("LEVEL",question.getLevel());
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
                int foiVisualizado = cursor.getInt(cursor.getColumnIndex("FOI_VISUALIZADO"));

                // se foiVisualizado igual a 0 significa que questão não foi visualizada
                if(foiVisualizado==0)
                {
                    question = new Question();
                    question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    question.setQuestionHeader(cursor.getString(cursor.getColumnIndex("QUESTION_HEADER")));
                    question.setQuestionText(cursor.getString(cursor.getColumnIndex("QUESTION_TEXT")));
                    question.setLevel(cursor.getString(cursor.getColumnIndex("LEVEL")));
                    question.setFoiVisualizado(cursor.getInt(cursor.getColumnIndex("FOI_VISUALIZADO")));
                    break;
                }

            }while (cursor.moveToNext());
        }

        return question;
    }

    public Question catchNextQuestion (String level)
    {
        Question question = null;

        Cursor cursor = conn.query("QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        Log.i("Questoes no banco",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            do
            {
                int foiVisualizado = cursor.getInt(cursor.getColumnIndex("FOI_VISUALIZADO"));

                // se foiVisualizado igual a 0 significa que questão não foi visualizada
                if(foiVisualizado==0)
                {
                    Log.i("Level da questao",""+cursor.getString(cursor.getColumnIndex("LEVEL")));
                    if(cursor.getString(cursor.getColumnIndex("LEVEL")).equals(level))
                    {
                        question = new Question();
                        question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                        question.setQuestionHeader(cursor.getString(cursor.getColumnIndex("QUESTION_HEADER")));
                        question.setQuestionText(cursor.getString(cursor.getColumnIndex("QUESTION_TEXT")));
                        question.setLevel(cursor.getString(cursor.getColumnIndex("LEVEL")));
                        question.setFoiVisualizado(cursor.getInt(cursor.getColumnIndex("FOI_VISUALIZADO")));
                        break;
                    }
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
                    question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    question.setQuestionHeader(cursor.getString(cursor.getColumnIndex("QUESTION_HEADER")));
                    question.setQuestionText(cursor.getString(cursor.getColumnIndex("QUESTION_TEXT")));
                    question.setLevel(cursor.getString(cursor.getColumnIndex("LEVEL")));
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
