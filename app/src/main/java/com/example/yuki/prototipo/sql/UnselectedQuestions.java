package com.example.yuki.prototipo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yuki.prototipo.Question;

import java.util.ArrayList;

/**
 * Created by yuki on 14/10/16.
 */

public class UnselectedQuestions {

    private SQLiteDatabase conn;

    public UnselectedQuestions(SQLiteDatabase conn)
    {
        this.conn = conn;
    }

    private ContentValues fillContentValues(Question question)
    {
        ContentValues values = new ContentValues();

        values.put("_id",question.getId());
        values.put("QUESTION_HEADER",question.getQuestionHeader());
        values.put("QUESTION_TEXT",question.getQuestionText());
        values.put("LEVEL",question.getLevel());
        values.put("WAS_VISUALIZED",question.getWasVisualized());

        return values;
    }

    private ContentValues fillTagQuestions(Question question,int posicao)
    {
        ContentValues values = new ContentValues();

        values.put("_id_QUESTION",question.getId());
        values.put("TAG",question.getTags().get(posicao));

        return values;
    }

    public void insert(Context context, Question question)
    {
        Log.i("Inserindo questao","true");
        Log.i("Tem questao",""+this.hasQuestion(question));

        if(!(AllQuestions.hasQuestion(context,question)))
        {
            conn.insertOrThrow("UNSELECTED_QUESTIONS", null, fillContentValues(question));

            for (int i=0;i<question.getTags().size();i++)
            {
                Log.i("Inserindo Tag",question.getTags().get(i));
                conn.insertOrThrow("TAG_QUESTIONS",null,fillTagQuestions(question,i));
            }
        }
    }

    public void insert(Question question)
    {
            conn.insertOrThrow("UNSELECTED_QUESTIONS", null,fillContentValues(question));
    }

    public void update(Question question)
    {
        conn.update("UNSELECTED_QUESTIONS",fillContentValues(question),"_id = ?",new String[]{question.getId()+""});
    }

    public void delete(int id)
    {
        conn.delete("UNSELECTED_QUESTIONS","_id = ?",new String[]{""+id});
    }

    public Question catchQuestion(int id)
    {
        Question question = null;

        Cursor cursor = conn.query("UNSELECTED_QUESTIONS",null,"_id = ?",new String[]{""+id},null,null,null);

        cursor.moveToFirst();

        Log.i("Questoes no banco",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            do
            {
                int wasVisualized = cursor.getInt(cursor.getColumnIndex("WAS_VISUALIZED"));

                // se foiVisualizado igual a 0 significa que questão não foi visualizada
                if(wasVisualized==0)
                {
                    question = new Question();
                    question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    question.setQuestionHeader(cursor.getString(cursor.getColumnIndex("QUESTION_HEADER")));
                    question.setQuestionText(cursor.getString(cursor.getColumnIndex("QUESTION_TEXT")));
                    question.setLevel(cursor.getString(cursor.getColumnIndex("LEVEL")));
                    question.setWasVisualized(cursor.getInt(cursor.getColumnIndex("WAS_VISUALIZED")));
                    break;
                }

            }while (cursor.moveToNext());
        }

        return question;


    }

    public ArrayList<Integer> catchArrayQuestion ()
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();

        Cursor cursor = conn.query("UNSELECTED_QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        Log.i("Questoes no banco",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            do
            {
                int wasVisualized = cursor.getInt(cursor.getColumnIndex("WAS_VISUALIZED"));

                // se foiVisualizado igual a 0 significa que questão não foi visualizada
                if(wasVisualized==0)
                    ret.add(cursor.getInt(cursor.getColumnIndex("_id")));

            }while (cursor.moveToNext());
        }

        return ret;
    }

    public ArrayList<Integer> catchArrayQuestionTagLevel (String tag,String level)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();

        Cursor cursor = conn.query("TAG_QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        Log.i("Tags no banco",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            do
            {
                if(cursor.getString(cursor.getColumnIndex("TAG")).equals(tag))
                    ret.add(cursor.getInt(cursor.getColumnIndex("_id_QUESTION")));

            }while (cursor.moveToNext());
        }

        ret = this.removeVisualized(ret);

        ret = this.selectByLevel(ret,level);

        return ret;
    }

    public ArrayList<Integer> catchArrayQuestionLevel (String level)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();

        Cursor cursor = conn.query("UNSELECTED_QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        Log.i("Questoes no banco",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            do
            {
                int wasVisualized = cursor.getInt(cursor.getColumnIndex("WAS_VISUALIZED"));

                // se foiVisualizado igual a 0 significa que questão não foi visualizada
                if(wasVisualized==0)
                {
                    Log.i("Level da questao",""+cursor.getString(cursor.getColumnIndex("LEVEL")));
                    if(cursor.getString(cursor.getColumnIndex("LEVEL")).equals(level))
                        ret.add(cursor.getInt(cursor.getColumnIndex("_id")));
                }

            }while (cursor.moveToNext());
        }

        return ret;
    }

    public ArrayList<Integer> catchArrayQuestionTag (String tag)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();

        Cursor cursor = conn.query("TAG_QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        Log.i("Tags no banco",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            do
            {
                    if(cursor.getString(cursor.getColumnIndex("TAG")).equals(tag))
                        ret.add(cursor.getInt(cursor.getColumnIndex("_id_QUESTION")));

            }while (cursor.moveToNext());
        }

        return this.removeVisualized(ret);
    }

    private ArrayList<Integer> selectByLevel(ArrayList<Integer> arrayIds,String level)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();


        for(int i=0;i < arrayIds.size();i++)
        {
            Cursor cursor = conn.query("UNSELECTED_QUESTIONS", null, "_id = ?", new String[]{"" + arrayIds.get(i)}, null, null, null);

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {

                if (cursor.getString(cursor.getColumnIndex("LEVEL")).equals(level))
                    ret.add(cursor.getInt(cursor.getColumnIndex("_id")));
            }
        }

        return ret;
    }

    private ArrayList<Integer> removeVisualized(ArrayList<Integer> arrayIds)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();


        for(int i=0;i < arrayIds.size();i++)
        {
            Cursor cursor = conn.query("UNSELECTED_QUESTIONS", null, "_id = ?", new String[]{"" + arrayIds.get(i)}, null, null, null);

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                int wasVisualized = cursor.getInt(cursor.getColumnIndex("WAS_VISUALIZED"));

                // se foiVisualizado igual a 0 significa que questão não foi visualizada
                if (wasVisualized == 0)
                    ret.add(cursor.getInt(cursor.getColumnIndex("_id")));
            }
        }

        return ret;
    }

    public void updateWasVisualized ()
    {
        Question question;

        Cursor cursor = conn.query("UNSELECTED_QUESTIONS",null,null,null,null,null,null);

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
                    question.setWasVisualized(0);

                    this.update(question);

            }while (cursor.moveToNext());
        }
    }

    public boolean hasQuestion(Question question)
    {
        Cursor cursor = conn.query("UNSELECTED_QUESTIONS",null,null,null,null,null,null);

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
