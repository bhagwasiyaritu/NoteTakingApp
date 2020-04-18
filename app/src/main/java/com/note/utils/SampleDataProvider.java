package com.note.utils;

import com.note.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleDataProvider {
    public static final String SAMPLE_TEXT_1 ="A Simple Note";

    private static Date getDate(int diffAmount){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MILLISECOND,diffAmount);
        return calendar.getTime();
    }

    public static List<NoteEntity> getSampleData(){
        List<NoteEntity> notesList = new ArrayList<>();

        notesList.add(new NoteEntity(getDate(0),SAMPLE_TEXT_1));
        return notesList;
    }
}
