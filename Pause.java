package com.company;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Created by gifin_000 on 26.03.2016.
 */
public class Pause implements Element{

    private NoteLength length;
    private int startTime;

    public Pause(NoteLength length, int startTime)
    {
        this.length = length;
        this.startTime = startTime;
    }

    @Override
    public void drawNote(Graphics2D g2, int x, int y) {

        switch (length.division)
        {
            case 0: drawFullPause(g2,x,y); break;
            case 1: drawHalfPause(g2,x,y); break;
            case 2: drawQuarterPause(g2,x,y); break;
            case 3: drawEighthPause(g2,x,y); break;
            case 4: drawSixteenthPause(g2,x,y); break;
        }

        if (length.dot)
        {
            Ellipse2D.Double circle;
            if(length.division == 0)
                circle = new Ellipse2D.Double(x + 22, y - 25, 4, 4);
            else
                circle = new Ellipse2D.Double(x + 22, y - 2, 4, 4);

            g2.fill(circle);
        }

    }

    private static void drawFullPause(Graphics2D g2, int x, int y) {
        g2.drawRect(x, y - 33, 20, 10);
    }

    private static void drawHalfPause(Graphics2D g2, int x, int y) {
        Rectangle rectangle = new Rectangle(x, y - 5, 20, 10);
        g2.fill(rectangle);
    }

    private static void drawQuarterPause(Graphics2D g2, int x, int y) {
        g2.draw(new Arc2D.Double(x + 14.5, y - 17, 7, 7, 90, -180, Arc2D.OPEN));
        g2.draw(new Arc2D.Double(x + 14.5, y - 10, 7, 7, 90, 180, Arc2D.OPEN));
        g2.draw(new Arc2D.Double(x + 14.5, y - 3, 5, 5, 90, 180, Arc2D.OPEN));
    }

    private static void drawEighthPause(Graphics2D g2, int x, int y) {

        Ellipse2D.Double circle = new Ellipse2D.Double(x + 9, y - 11, 5, 5);
        g2.fill(circle);
        g2.drawLine(x+15, y+5, x+20, y-10);
        g2.draw(new Arc2D.Double(x + 9.5, y - 15, 10, 10, -180, 180, Arc2D.OPEN));
    }

    private static void drawSixteenthPause(Graphics2D g2, int x, int y) {
        Ellipse2D.Double circle1 = new Ellipse2D.Double(x + 7.5, y - 9.5, 5, 5);
        g2.fill(circle1);
        g2.drawLine(x+15, y+5, x+20, y-15);
        g2.draw(new Arc2D.Double(x + 9, y - 12.5, 10, 10, -180, 180, Arc2D.OPEN));
        Ellipse2D.Double circle2 = new Ellipse2D.Double(x + 9, y - 16, 5, 5);
        g2.fill(circle2);
        g2.draw(new Arc2D.Double(x + 9.5, y - 20, 10, 10, -180, 180, Arc2D.OPEN));
    }

    @Override
    public int getLine() {
        return 13;
    }

    @Override
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    @Override
    public void drawLine(Graphics2D g2, int x, int y) {
        if(length.division == 0)
            g2.draw(new Line2D.Double(x - 5, y - 33, x + 25, y - 33));
        else
            g2.draw(new Line2D.Double(x - 5, y + 5, x + 25, y + 5));
    }

    @Override
    public int getStartTime() {
        return startTime;
    }

    @Override
    public void nextLength() {
        length.next();
    }
}
