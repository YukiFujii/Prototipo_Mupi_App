package com.example.yuki.prototipo.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.yuki.prototipo.Question;

/**
 * Created by yuki on 21/10/16.
 */

public final class FacadeSQL {

    private static DataBase dataBase;
    private static SQLiteDatabase conn;
    private static Question_Repository repositorioDeQuestoes;
    private static Selected_Questions saveQuestions;

    public static boolean hasQuestion(Context context, Question question)
    {
        if(conexaoBD(context))
        {
            repositorioDeQuestoes = new Question_Repository(conn);
            saveQuestions = new Selected_Questions(conn);

            if(repositorioDeQuestoes.hasQuestion(question))
                return true;

            if(saveQuestions.hasQuestion(question))
                return true;

            return false;
        }
        else
            return false;
    }

    public static boolean insertQuestionRepository(Context context, Question question)
    {
        if(conexaoBD(context))
        {
            repositorioDeQuestoes = new Question_Repository(conn);
            repositorioDeQuestoes.insert(context,question);
            return true;
        }
        else
            return false;
    }

    public static boolean deleteSelectedQuestions(Context context, Question question)
    {
        if(conexaoBD(context))
        {
            saveQuestions = new Selected_Questions(conn);
            saveQuestions.delete(context,question.getId());
            return true;
        }
        else
            return false;
    }

    private static boolean conexaoBD(Context context)
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
