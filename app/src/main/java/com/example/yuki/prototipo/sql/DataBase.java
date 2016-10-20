package com.example.yuki.prototipo.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }


}

