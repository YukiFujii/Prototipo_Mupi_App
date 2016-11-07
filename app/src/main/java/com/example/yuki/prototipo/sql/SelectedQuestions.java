package com.example.yuki.prototipo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.yuki.prototipo.Question;

import java.util.ArrayList;

/**
 * Created by yuki on 14/10/16.
 */

public class SelectedQuestions {

    private SQLiteDatabase conn;

    public SelectedQuestions(SQLiteDatabase conn)
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

    public void insert(Question question)
    {
        conn.insertOrThrow("SELECTED_QUESTIONS", null, fillContentValues(question));
    }

    public void update(Question question)
    {
        conn.update("SELECTED_QUESTIONS",fillContentValues(question),"_id = ?",new String[]{question.getId()+""});
    }

    public void delete(int id)
    {
        conn.delete("SELECTED_QUESTIONS","_id = ?",new String[]{""+id});
    }

    public ArrayAdapter<Question> searchSelectedQuestions (Context context)
    {
        ArrayAdapter<Question> arrayQuestions = new ArrayAdapter<Question>(context,android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query("SELECTED_QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                Question question = new Question();

                question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                question.setQuestionHeader(cursor.getString(cursor.getColumnIndex("QUESTION_HEADER")));
                question.setQuestionText(cursor.getString(cursor.getColumnIndex("QUESTION_TEXT")));
                question.setLevel(cursor.getString(cursor.getColumnIndex("LEVEL")));
                question.setWasVisualized(cursor.getInt(cursor.getColumnIndex("WAS_VISUALIZED")));

                arrayQuestions.add(question);

            }while (cursor.moveToNext());
        }

        return arrayQuestions;
    }

    public ArrayList<Integer> catchArrayQuestion ()
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();

        Cursor cursor = conn.query("SELECTED_QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        Log.i("Questoes no banco",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            do
            {
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

        ret = this.selectByLevel(ret,level);

        return ret;
    }

    public ArrayList<Integer> catchArrayQuestionLevel (String level)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();

        Cursor cursor = conn.query("SELECTED_QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        Log.i("Questoes no banco",""+cursor.getCount());

        if(cursor.getCount()>0)
        {
            do
            {
                    Log.i("Level da questao",""+cursor.getString(cursor.getColumnIndex("LEVEL")));
                    if(cursor.getString(cursor.getColumnIndex("LEVEL")).equals(level))
                        ret.add(cursor.getInt(cursor.getColumnIndex("_id")));

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

        return this.selectByIds(ret);
    }

    private ArrayList<Integer> selectByIds(ArrayList<Integer> arrayIds)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();


        for(int i=0;i < arrayIds.size();i++)
        {
            Cursor cursor = conn.query("SELECTED_QUESTIONS", null, "_id = ?", new String[]{"" + arrayIds.get(i)}, null, null, null);

            cursor.moveToFirst();

            if (cursor.getCount() > 0)
                ret.add(cursor.getInt(cursor.getColumnIndex("_id")));

        }

        return ret;
    }

    private ArrayList<Integer> selectByLevel(ArrayList<Integer> arrayIds,String level)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();


        for(int i=0;i < arrayIds.size();i++)
        {
            Cursor cursor = conn.query("SELECTED_QUESTIONS", null, "_id = ?", new String[]{"" + arrayIds.get(i)}, null, null, null);

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {

                if (cursor.getString(cursor.getColumnIndex("LEVEL")).equals(level))
                    ret.add(cursor.getInt(cursor.getColumnIndex("_id")));
            }
        }

        return ret;
    }

    public ArrayAdapter<Question> createArrayAdapterQuestion(Context context, ArrayList<Integer> arrayIds)
    {
        ArrayAdapter<Question> ret = new ArrayAdapter<Question>(context,android.R.layout.simple_list_item_1);

        for(int i=0;i < arrayIds.size();i++)
        {
            Cursor cursor = conn.query("SELECTED_QUESTIONS", null, "_id = ?", new String[]{"" + arrayIds.get(i)}, null, null, null);

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {

                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                question.setQuestionHeader(cursor.getString(cursor.getColumnIndex("QUESTION_HEADER")));
                question.setQuestionText(cursor.getString(cursor.getColumnIndex("QUESTION_TEXT")));
                question.setLevel(cursor.getString(cursor.getColumnIndex("LEVEL")));
                question.setWasVisualized(cursor.getInt(cursor.getColumnIndex("WAS_VISUALIZED")));

                ret.add(question);
            }
        }

        return ret;
    }

    public boolean hasQuestion(Question question)
    {
        Cursor cursor = conn.query("SELECTED_QUESTIONS",null,null,null,null,null,null);

        cursor.moveToFirst();

        if(cursor.getCount()>0)
        {
            do
            {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));

                if(id==question.getId())
                    return true;

            }while (cursor.moveToNext());

            return false;
        }

        return false;

    }
}
