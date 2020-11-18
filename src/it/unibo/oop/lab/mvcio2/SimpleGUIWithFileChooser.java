package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private final JFrame frame = new JFrame("Simple GUI CIAONE");
    private final Controller controller = new Controller();

    public SimpleGUIWithFileChooser() {

        final JPanel panelPrinc = new JPanel();
        panelPrinc.setLayout(new BorderLayout());
        frame.setContentPane(panelPrinc);

        final JButton save = new JButton("Salva");
        final JTextArea text = new JTextArea();
        final JPanel panelSec = new JPanel();
        final JTextField field = new JTextField();
        final JButton sfoglia = new JButton("Sfoglia...");

        panelSec.setLayout(new BorderLayout());
        panelSec.add(field, BorderLayout.CENTER);
        panelSec.add(sfoglia, BorderLayout.LINE_END);

        panelPrinc.add(text, BorderLayout.CENTER);
        panelPrinc.add(save, BorderLayout.SOUTH);
        panelPrinc.add(panelSec, BorderLayout.NORTH);

        field.setText(controller.getPath());
        field.setEditable(false);

        sfoglia.addActionListener(e -> {
            final JFileChooser choose = new JFileChooser();
            //choose.showSaveDialog(sfoglia);
            if (choose.APPROVE_OPTION == choose.showSaveDialog(sfoglia)) {
                controller.setFile(choose.getSelectedFile());
                field.setText(controller.getPath());
            } else {
                JOptionPane.showMessageDialog(sfoglia, "Si è verificato un errore");
            }
        });

        save.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                SimpleGUIWithFileChooser.this.controller.saveContent(text.getText());
            }
        });

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);

        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

    }

    private void display() {
        frame.setVisible(true);
    }

    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel. 
     *
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */

    public static void main(final String... args) {
        new SimpleGUIWithFileChooser().display();
    }

}
