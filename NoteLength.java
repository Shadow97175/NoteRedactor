package com.company;

/**
 * Created by gifin_000 on 04.03.2016.
 */
public class NoteLength {
    int division;
    boolean dot;

    public NoteLength(int division, boolean dot){
        this.division = division;
        this.dot = dot;
    }

    public NoteLength(){
        division = 0;
        dot = false;
    }

    double getSeconds(){
        return (dot ? 1.5 : 1) * Math.pow(2,-division);
    }

    public void next() {
        ++division;
        if (division > 4)
            division = 0;
    }
}
