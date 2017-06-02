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


        setSize(300,200);
        setLocationRelativeTo(null);

        fc = new JFileChooser();
        buttons = new JPanel();
        setLayout(new GridBagLayout());

        openButton = new JButton("Open");
        openButton.setHorizontalAlignment(SwingConstants.LEFT);
        buttons.add(openButton);

        saveButton = new JButton("Save");
        saveButton.setHorizontalAlignment(SwingConstants.CENTER);
        buttons.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setHorizontalAlignment(SwingConstants.RIGHT);
        buttons.add(cancelButton);

        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);

       add(buttons,  new GridBagConstraints());

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
