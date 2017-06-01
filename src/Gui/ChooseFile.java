package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by rita on 01-06-2017.
 */
public class ChooseFile extends JFrame implements ActionListener {

    private JFileChooser fc;
    private JButton openButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel buttons;

    public ChooseFile(){

        fc = new JFileChooser();
        buttons = new JPanel(new GridBagLayout());

        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;


        openButton = new JButton("Open");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        buttons.add(openButton,cs);

        saveButton = new JButton("Save");
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        buttons.add(saveButton,cs);

        cancelButton = new JButton("Cancel");
        cs.gridx = 2;
        cs.gridy = 0;
        cs.gridwidth = 3;
        buttons.add(cancelButton,cs);

        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);


        add(buttons);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,300);
        setVisible(true);
    }

    public JFileChooser getFc() {
        return fc;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == saveButton){

        }
        else if(actionEvent.getSource() == openButton){

            fc.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fc.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
            }

        }
        else if(actionEvent.getSource() == cancelButton){
            
        }
    }

    public static void main(String[] args){
        ChooseFile f = new ChooseFile();
    }


}
