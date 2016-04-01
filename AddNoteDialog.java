package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gifin_000 on 11.03.2016.
 */
public class AddNoteDialog extends JDialog {

    private Element note;

    public AddNoteDialog(JFrame parent)
    {
        super(parent);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
    }

    private void initComponents() {

        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS)
        );
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1,BoxLayout.X_AXIS));
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2,BoxLayout.X_AXIS));
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3,BoxLayout.X_AXIS));
        JPanel p4 = new JPanel();
        p4.setLayout(new BoxLayout(p4,BoxLayout.X_AXIS));

        setSize(new Dimension(300,100));

        final JLabel noteSound = new JLabel("Note");
        p1.add(noteSound);
        final JComboBox noteSoundInput = new JComboBox();
        for(NoteSound s : NoteSound.values())
            noteSoundInput.addItem(s.toString());
        noteSoundInput.addItem("Pause");
        p1.add(noteSoundInput);
        add(p1);

        JLabel octave = new JLabel("Octave");
        p2.add(octave);
        final JComboBox octaveInput = new JComboBox();
        for(int i = -1; i <= 1; i++)
            octaveInput.addItem(i >= 0 ? i + 1 : i);
        octaveInput.setSelectedIndex(1);
        p2.add(octaveInput);
        add(p2);

        JLabel length = new JLabel("Length");
        p3.add(length);
        final JComboBox lengthInput = new JComboBox();
        for(int i = 0; i < 5; i++)
            lengthInput.addItem("1/" + (1 << i));
        lengthInput.setSelectedIndex(1);
        p3.add(lengthInput);
        final JCheckBox dotInput = new JCheckBox();
        p3.add(dotInput);
        add(p3);

        JButton addNoteButton = new JButton("Add");
        p4.add(addNoteButton);

        addNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int length = (int) (Math.log(Integer.parseInt(lengthInput.getSelectedItem().toString().substring(2))) / Math.log(2));
                boolean dot = dotInput.isSelected();
                NoteLength noteLength = new NoteLength(length, dot);

                if (noteSoundInput.getSelectedIndex() == 7)
                {
                    AddNoteDialog.this.note = new Pause(noteLength, 0);
                }
                else {
                    NoteSound sound = NoteSound.valueOf(noteSoundInput.getSelectedItem().toString());
                    int octave = Integer.parseInt(octaveInput.getSelectedItem().toString());
                    octave = octave > 0 ? (octave - 1) : octave;
                    AddNoteDialog.this.note = new Note(octave, sound, noteLength, 0);
                }
                setVisible(false);
                dispose();
            }
        });

        JButton cancelNoteButton = new JButton("Cancel");
        p4.add(cancelNoteButton);
        add(p4);
        cancelNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

    }

    public Element getValue() {
        return note;
    }

    public void close() {
        setVisible(false);
        dispose();
    }
}
