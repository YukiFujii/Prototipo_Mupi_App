package com.example.yuki.prototipo.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class DataBase extends SQLiteOpenHelper implements Serializable
{
    public DataBase (Context context)
    {
        super(context,"PROTOTIPO",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(ScriptSQL.getRepositorioDePerguntas());
        db.execSQL(ScriptSQL.getSelectedQuestions());
        db.execSQL(ScriptSQL.getTagQuestion());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }


}

