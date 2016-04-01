package com.company;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

public class NoteDesigner extends JApplet implements ChangeListener, ActionListener {

    private NotePreviewPanel painter;

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

    private void initComponents() {

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
        Element note  = ap.getValue();
        ap.close();
        //JOptionPane.showMessageDialog(this,note.toString());
        if (note != null) painter.addNote(note);
    }
}

class NotePreviewPanel extends Component{

    private NoteString noteString;

    public NotePreviewPanel(){
        super();
        noteString = new NoteString();
        addMouseListener(new MouseListener() {

            private int i;

            private int checkNote(int x, int y)
            {
                int startTime = (x - 10) / 20;
                int height = getHeight();
                int lines = noteString.size();
                int line = (height - y - 15) * lines / height;

                //System.err.println(startTime);
                //System.err.println(line);

                return noteString.findNote(startTime, line);
            }

            private Note createNote(int x, int y)
            {
                int startTime = (x - 10) / 20;
                int height = getHeight();
                int lines = noteString.size();
                int line = (height - y - 15) * lines / height;

                //System.err.println("Create note y: " + line);

                return new Note(startTime, line);
            }

            private int calcStartTime(int x)
            {
                return (x - 10) / 20;
            }


            @Override
            public void mouseClicked(MouseEvent e) {

                int x = e.getX();
                int y = e.getY() - 15;

                int i = checkNote(x, y);

                //System.err.println(i);

                if (i != -1)
                {
                    if (SwingUtilities.isLeftMouseButton(e))
                        noteString.getNote(i).nextLength();
                    if (SwingUtilities.isRightMouseButton(e))
                        noteString.removeNote(i);
                }
                else
                {
                    noteString.addNote(createNote(x, y));
                }


                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY() - 15;

                i = checkNote(x,y);
                //System.err.println(i);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                int x = e.getX();
                int y = e.getY() - 15;

                if (checkNote(x,y) == -1 && i != -1)
                {
                    //System.err.println(noteString.getNote(i).getStartTime() + " " + calcStartTime(x));
                    noteString.getNote(i).setStartTime(calcStartTime(x));
                }

                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //System.err.println("Entered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //System.err.println("Exited");
            }
        });
    }

    public void addNote(Element note){
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
        }


        int y;
        int line = 0;
        for(int i = 0; i < noteString.count(); i++)
        {
            Element note = noteString.getNote(i);
            int x = 10 + 20 * note.getStartTime();
            if(i == 0 || note instanceof Note) {
                line = note.getLine();
            }
            else
            {
                //System.err.println(line);
                if (line % 2 == 0) {
                    --line;
                }
            }
            y = height - (line * (height / lines)) - 15;
            //System.err.println("Draw y: " + line);
            note.drawNote(g2, x, y);
            if (line % 2 == 1 && (line <= 8 || line >= 20))
                note.drawLine(g2,x,y);
        }
    }
}
