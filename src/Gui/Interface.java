package Gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


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


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            //TODO:abrir pop up de escolha de ficheiro
            //TODO:fechar

        }
    }


    public static void main(String[] args) throws BadLocationException {
        Interface i = new Interface();

    }



}

