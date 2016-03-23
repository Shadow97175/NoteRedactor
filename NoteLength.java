package com.company;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

public class NoteDesigner extends JApplet implements ChangeListener, ActionListener {

    NotePreviewPanel painter;

    public void init() {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
    }

    public void start() {
        initComponents();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Note Designer");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JApplet ap = new NoteDesigner();
        ap.init();
        ap.start();
        f.add("Center", ap);
        f.pack();
        f.setVisible(true);

    }

    public void initComponents() {

        setLayout(new BorderLayout());

        JPanel p = new JPanel();
        add("North", p);

        JButton addNoteButton = new JButton("Add note");
        p.add(addNoteButton);
        addNoteButton.addActionListener(this);

        JButton exitButton = new JButton("Exit");
        p.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        painter = new NotePreviewPanel();
        p.add("Center", painter);

    }

    public void stateChanged(ChangeEvent e) {
    }

    private JFrame getFrame() {
        return (JFrame) this.getParent().getParent().getParent().getParent();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AddNoteDialog ap = new AddNoteDialog(getFrame());
        ap.pack();
        ap.setVisible(true);
        Note note  = ap.getValue();
        ap.close();
        //JOptionPane.showMessageDialog(this,note.toString());
        if (note != null) painter.addNote(note);
    }
}

class NotePreviewPanel extends Component {

    private NoteString noteString;

    public NotePreviewPanel(){
        super();
        noteString = new NoteString();
    }

    public void addNote(Note note){
        noteString.addNote(note);
        repaint();
    }

    public Dimension getPreferredSize(){
        return new Dimension(1000, 400);
    }


    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension size = getSize();
        //Composite origComposite;

        int lines = noteString.size();
        int width = size.width;
        int height = size.height;


        for(int i=0;i<lines;i++) {
            int y = height - (i * (height / lines)) - 10;

            if (i > 8 && i % 2 == 1 && i < 20)
                g2.draw(new Line2D.Double(0, y, width, y));

            int x = 10;
            for(PositionedNote note : noteString.getNotes(i))
            {
                note.note.drawNote(g2,x + 30 * note.i, y - 5);

                if (i % 2 == 1 && (i <= 8 || i >= 20))
                    g2.draw(new Line2D.Double(x + 30 * note.i - 5, y, x + 30 * note.i + 25, y));


                //x += note.note.offset();
            }
        }
    }
}
