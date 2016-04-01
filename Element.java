package com.company;

import java.awt.*;

/**
 * Created by gifin_000 on 26.03.2016.
 */
public interface Element {

    void drawNote(Graphics2D g2, int x, int y);

    int getLine();

    void drawLine(Graphics2D g2, int x, int y);

    int getStartTime();

    void nextLength();

    void setStartTime(int i);
}
