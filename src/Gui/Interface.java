package Gui;

import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class Interface extends JFrame implements ActionListener{

    private JTextArea javascriptTextArea=null;
    private JScrollPane javascriptScroll=null;
    private TextLineNumber javascriptNumber=null;

    private JTextArea javaTextArea=null;
    private JScrollPane javaScroll=null;
    private TextLineNumber javaNumber=null;

    private JSplitPane divideEditors=null;
    private JSplitPane menu=null;

    private JPanel menuOptions=null;
    private JButton runButton=null;
    private JButton chooseFileButton=null;

    public Interface() {

        super("JavaScript to Java");

        setSize(1000,1000);
        setLocationRelativeTo(null);

        javascriptTextArea = new JTextArea();
        javascriptNumber = new TextLineNumber(javascriptTextArea);
        javascriptScroll = new JScrollPane(javascriptTextArea);
        javascriptScroll.setRowHeaderView( javascriptNumber);

        javaTextArea = new JTextArea();
        javaTextArea.setEnabled(false);
        javaNumber = new TextLineNumber(javaTextArea);
        javaScroll = new JScrollPane(javaTextArea);
        javaScroll.setRowHeaderView( javaNumber);


        menuOptions = new JPanel();
        runButton = new JButton("Run");
        runButton.addActionListener(this);

        chooseFileButton = new JButton("Choose File");
        chooseFileButton.addActionListener(this);

        menuOptions.add(runButton);
        menuOptions.add(chooseFileButton);


        divideEditors = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,javascriptScroll,javaScroll);
        divideEditors.setResizeWeight(0.5);
        divideEditors.setEnabled(false);

        menu = new JSplitPane(JSplitPane.VERTICAL_SPLIT,menuOptions,divideEditors);
        menu.setResizeWeight(0.04);
        menu.setEnabled(false);

        add(menu);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == runButton){
            //TODO:recolher o código do editor de texto
            //TODO:tranformá-lo em java
            //TODO:imprimi-lo no outro editor
        }
        else if(actionEvent.getSource() == chooseFileButton){
           chooseFile();

        }
    }


    public void chooseFile(){
        JFileChooser fc = new JFileChooser();

        int result = fc.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            //gets file
            File selectedFile = fc.getSelectedFile();

            System.out.println( selectedFile.getName());
            //creates folder
            File dir = new File("fileFromUser");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File newFile = new File(dir.toPath() + "/userFile.js");

            try {
                Files.copy(selectedFile.toPath(),newFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args)  {
        Interface i = new Interface();

    }



}

