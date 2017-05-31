package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Interface extends JFrame{

    private JTextPane writeCode;
    private JScrollPane writeCodeScroll;
    private TextLineNumber writeCodeNumber;
    private JLabel writeCodeLabel;
    private JTextPane printCode;
    private JScrollPane printCodeScroll;
    private TextLineNumber printCodeNumber;
    private JLabel printCodeLabel;
    private JTextPane insertFile;
    private JSplitPane divideEditors;
    private JSplitPane javascript;


    public Interface(){

        writeCode = new JTextPane();
        writeCodeLabel = new JLabel("JavaScript");
        writeCodeScroll = new JScrollPane(writeCode);
        writeCodeNumber = new TextLineNumber(writeCode);
        writeCodeScroll.setRowHeaderView(writeCodeNumber);

        printCode = new JTextPane();
        printCode.setEditable(false);
        printCodeLabel = new JLabel("Java");
        printCodeScroll = new JScrollPane(printCode);
        printCodeNumber = new TextLineNumber(printCode);
        printCodeScroll.setRowHeaderView(printCodeNumber);

        divideEditors = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,writeCodeScroll,printCodeScroll);
        divideEditors.setResizeWeight(0.5);
        divideEditors.setEnabled(false);
        add(divideEditors);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setVisible(true);
    }


    public static void main(String[] args){
         Interface i = new Interface();
    }
}

