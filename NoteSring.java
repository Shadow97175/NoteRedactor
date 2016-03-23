package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gifin_000 on 13.03.2016.
 */
public class NoteString {
    private List<PositionedNote>[] notes;

    public NoteString(){
        notes = new List[21];

        for(int i = 0; i < notes.length; ++i)
            notes[i] = new ArrayList<PositionedNote>();

    }

    private int count()
    {
        int sum = 0;
        for (List<PositionedNote> note : notes) sum += note.size();
        return sum;
    }


    public void addNote(Note note){
        int line = note.noteSound.ordinal() + (note.octave + 1) * 7;
        notes[line].add(new PositionedNote(note,count()));
    }


    public int size(){
        return notes.length;
    }

    public List<PositionedNote> getNotes(int i){
        return notes[i];
    }

}
