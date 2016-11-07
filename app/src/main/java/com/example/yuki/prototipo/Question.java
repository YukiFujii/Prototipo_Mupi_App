package com.example.yuki.prototipo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yuki on 14/10/16.
 */

public class Question implements Serializable
{
    private int id;
    private  String questionHeader;
    private  String questionText;
    private String level;
    private ArrayList<String> tags;
    private int wasVisualized;

    public Question(){}

    public Question(int id, String questionHeader, String questionText, char level)
    {
        this.setId(id);
        this.setQuestionHeader(questionHeader);
        this.setQuestionText(questionText);
        this.setLevel(level);
        this.setWasVisualized(0);
        this.tags = new ArrayList<String>();
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

    public int getWasVisualized() {
        return wasVisualized;
    }

    public void setWasVisualized(int wasVisualized) {
        this.wasVisualized = wasVisualized;
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

    public void addTag(String tag)
    {
        this.tags.add(tag);
    }

    public void addTag(ArrayList<String> arrayTag)
    {
        this.tags = arrayTag;
    }

    public ArrayList<String> getTags()
    {
        return this.tags;
    }
}
