package com.company;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Created by gifin_000 on 04.03.2016.
 */
public class Note implements Element {
    private int octave;
    private NoteSound noteSound;
    private NoteLength length;
    private int startTime;

    public Note()
    {
        octave = 1;
        noteSound = NoteSound.DO;
        length = new NoteLength();
        startTime = 0;
    }

    public Note(int octave, NoteSound noteSound, NoteLength length, int startTime)
    {
        this.octave = octave;
        this.noteSound = noteSound;
        this.length = length;
        this.startTime = startTime;
    }

    public Note(int startTime, int line)
    {
        this.startTime = startTime;
        this.length = new NoteLength();

        octave = line / 7 - 1;
        noteSound = NoteSound.values()[line % 7];

    }

    public void drawNote(Graphics2D g2, int x, int y)
    {
        switch (length.division)
        {
            case 0: drawFullNote(g2,x,y); break;
            case 1: drawHalfNote(g2,x,y); break;
            case 2: drawQuaterNote(g2,x,y); break;
            case 3: drawEighthNote(g2,x,y); break;
            case 4: drawSixteenthNote(g2,x,y); break;
        }

        if (length.dot)
        {
            Ellipse2D.Double circle = new Ellipse2D.Double(x + 22, y - 2, 4, 4);
            g2.fill(circle);
        }

    }

    public int getLine()
    {
        return noteSound.ordinal() + (octave + 1) * 7;
    }

    @Override
    public void drawLine(Graphics2D g2, int x, int y) {
        g2.draw(new Line2D.Double(x - 5, y + 5, x + 25, y + 5));
    }

    private static void drawFullNote(Graphics2D g2, int x, int y) {
        g2.drawOval(x, y, 20, 10);
    }

    private static void drawHalfNote(Graphics2D g2, int x, int y) {
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 20, 10);
        g2.fill(circle);
    }

    private static void drawQuaterNote(Graphics2D g2, int x, int y) {
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 20, 10);
        g2.fill(circle);
        g2.drawLine(x+20, y+5, x+20, y-15);
    }

    private static void drawEighthNote(Graphics2D g2, int x, int y) {

        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 20, 10);
        g2.fill(circle);
        g2.drawLine(x+20, y+5, x+20, y-15);
        g2.draw(new Arc2D.Double(x + 14.5, y - 15, 10, 10, 90, -180, Arc2D.OPEN));
    }

    private static void drawSixteenthNote(Graphics2D g2, int x, int y) {
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 20, 10);
        g2.fill(circle);
        g2.drawLine(x+20, y+5, x+20, y-15);
        g2.draw(new Arc2D.Double(x + 14.5, y - 15, 13, 10, 90, -180, Arc2D.OPEN));
        g2.draw(new Arc2D.Double(x + 14.5, y - 12, 10, 7, 90, -180, Arc2D.OPEN));
    }

    @Override
    public int getStartTime() {
        return startTime;
    }

    @Override
    public void nextLength() {
        length.next();
    }

    @Override
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }


    public String toString(){
        return "Octave: " + octave + ", " + noteSound.toString() + ", " + "Length in seconds: " + length.getSeconds();
    }
}
