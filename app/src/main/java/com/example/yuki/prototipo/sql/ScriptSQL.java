package com.example.yuki.prototipo.sql;

/**
 * Created by yuki on 14/10/16.
 */

public class ScriptSQL {

    public static String getRepositorioDePerguntas()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS QUESTIONS (       ");
        sqlBuilder.append("_id                                   INTEGER");
        sqlBuilder.append("PRIMARY KEY                                , ");
        sqlBuilder.append("QUESTION_HEADER               VARCHAR (50) , ");
        sqlBuilder.append("QUESTION_TEXT               VARCHAR (1000) , ");
        sqlBuilder.append("LEVEL                          VARCHAR (1) , ");
        sqlBuilder.append("FOI_VISUALIZADO                      INTEGER" );
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getSelectedQuestions()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS SELECTED_QUESTIONS ( ");
        sqlBuilder.append("_id                                      INTEGER");
        sqlBuilder.append("PRIMARY KEY                                   , ");
        sqlBuilder.append("QUESTION_HEADER                  VARCHAR (50) , ");
        sqlBuilder.append("QUESTION_TEXT                  VARCHAR (1000) , ");
        sqlBuilder.append("LEVEL                             VARCHAR (1) , ");
        sqlBuilder.append("FOI_VISUALIZADO                         INTEGER" );
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }
}
