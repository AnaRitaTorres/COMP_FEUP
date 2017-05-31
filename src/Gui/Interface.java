package Gui;

import javax.swing.*;



public class Interface extends JFrame{

    private JTextPane writeCode;
    private JScrollPane writeCodeScroll;
    private TextLineNumber writeCodeNumber;
    private JTextPane printCode;
    private JScrollPane printCodeScroll;
    private TextLineNumber printCodeNumber;
    private JSplitPane divideEditors;

    public Interface(){
        writeCode = new JTextPane();
        writeCodeScroll = new JScrollPane(writeCode);
        writeCodeNumber = new TextLineNumber(writeCode);
        writeCodeScroll.setRowHeaderView(writeCodeNumber);

        printCode = new JTextPane();
        printCode.setEditable(false);
        printCodeScroll = new JScrollPane(printCode);
        printCodeNumber = new TextLineNumber(printCode);
        printCodeScroll.setRowHeaderView(printCodeNumber);


        divideEditors = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,writeCodeScroll,printCodeScroll);
        divideEditors.setResizeWeight(0.5);
        add(divideEditors);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setVisible(true);
    }

    public static void main(String[] args){
         Interface i = new Interface();
    }
}

