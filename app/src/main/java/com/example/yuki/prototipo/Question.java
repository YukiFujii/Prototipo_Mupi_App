package com.example.yuki.prototipo;

import java.io.Serializable;

/**
 * Created by yuki on 14/10/16.
 */

public class Question implements Serializable
{
    private int id;
    private  String questionHeader;
    private  String questionText;
    private String level;
    private int foiVisualizado;

    public Question(){}

    public Question(int id, String questionHeader, String questionText, char level)
    {
        this.setId(id);
        this.setQuestionHeader(questionHeader);
        this.setQuestionText(questionText);
        this.setLevel(level);
        this.setFoiVisualizado(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionHeader() {
        return questionHeader;
    }

    public void setQuestionHeader(String questionHeader) {
        this.questionHeader = questionHeader;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getFoiVisualizado() {
        return foiVisualizado;
    }

    public void setFoiVisualizado(int foiVisualizado) {
        this.foiVisualizado = foiVisualizado;
    }

    public void setLevel(char level)
    {
        this.level = String.valueOf(level);
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getLevel()
    {
        return level;
    }

    public String toString()
    {
        String ret="";

        ret = ret+this.getQuestionHeader();

        return ret;
    }

}
