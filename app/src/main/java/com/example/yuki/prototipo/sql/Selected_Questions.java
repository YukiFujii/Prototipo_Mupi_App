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
        values.put("QUESTION_HEADER",question.getQuestionHeader());
        values.put("QUESTION_TEXT",question.getQuestionText());
        values.put("LEVEL",question.getLevel());
        values.put("FOI_VISUALIZADO",question.getFoiVisualizado());

        return values;
    }

    public void insert(Question question)
    {
        conn.insertOrThrow("SELECTED_QUESTIONS", null, preencheContentValues(question));
    }

    public void update(Question question)
    {
        conn.update("SELECTED_QUESTIONS",preencheContentValues(question),"_id = ?",new String[]{question.getId()+""});
    }

    public void delete(int id)
    {
        conn.delete("SELECTED_QUESTIONS","_id = ?",new String[]{""+id});
    }

    public ArrayAdapter<Question> buscarQuestoesSelecionadas (Context context)
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
                question.setFoiVisualizado(cursor.getInt(cursor.getColumnIndex("FOI_VISUALIZADO")));
                Log.i("Valor de foiVisualizado",""+question.getFoiVisualizado());
                //question.setFoiVisualizado(1);

                arrayQuestions.add(question);

            }while (cursor.moveToNext());
        }

        return arrayQuestions;
    }

    public ArrayAdapter<Question> filterLevel(Context context, ArrayAdapter<Question> arrayQuestions,String level)
    {
        ArrayAdapter<Question> ret = new ArrayAdapter<Question>(context,android.R.layout.simple_list_item_1);

        if(arrayQuestions.getCount()>0)
        {
            Log.i("ArrayQuestion", arrayQuestions.getCount()+"");
            for(int i=0;i<arrayQuestions.getCount();i++)
            {
                if(arrayQuestions.getItem(i).getLevel().equals(level))
                {
                    Log.i("Item"+i,"level igual a "+level);
                    ret.add(arrayQuestions.getItem(i));
                }
            }
        }

        Log.i("Novo array possui",ret.getCount()+" elementos.");
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
