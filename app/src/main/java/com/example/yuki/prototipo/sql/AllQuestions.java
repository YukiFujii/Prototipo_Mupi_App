package com.example.yuki.prototipo.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.yuki.prototipo.Question;

/**
 * Created by yuki on 21/10/16.
 */

public final class AllQuestions {

    private static DataBase dataBase;
    private static SQLiteDatabase conn;
    private static UnselectedQuestions unselectedQuestions;
    private static SelectedQuestions selectedQuestions;

    public static boolean hasQuestion(Context context, Question question)
    {
        if(connectionBD(context))
        {
            unselectedQuestions = new UnselectedQuestions(conn);
            selectedQuestions = new SelectedQuestions(conn);

            if(unselectedQuestions.hasQuestion(question))
                return true;

            if(selectedQuestions.hasQuestion(question))
                return true;

            return false;
        }
        else
            return false;
    }

    private static boolean connectionBD(Context context)
    {
        try {

            dataBase = new DataBase(context);
            conn = dataBase.getWritableDatabase();

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }
}
