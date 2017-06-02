package Gui;

import Esprima.rhino.Esprima;
import Parser.Parser;
import Utils.ParserUt;
import javafx.stage.FileChooser;

import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static Parser.Parser.readEsprima;
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

    private File dir;

    public Interface() {

        super("JavaScript to Java");

        setSize(1000,1000);
        setLocationRelativeTo(null);

        javascriptTextArea = new JTextArea();
        javascriptNumber = new TextLineNumber(javascriptTextArea);
        javascriptScroll = new JScrollPane(javascriptTextArea);
        javascriptScroll.setRowHeaderView( javascriptNumber);

        javaTextArea = new JTextArea();
        javaTextArea.setDisabledTextColor(Color.BLACK);
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

            javaTextArea.setText("");
            String[] lines = javascriptTextArea.getText().split("\\n");
            if(lines.length!=0){
                try {
                    writeFile(lines);
                    String jsonPath = null;
                    try {
                        jsonPath = Esprima.readJS2JSON("out.js");
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    readEsprima(jsonPath);

                    javaTextArea.setText(ParserUt.getInstance().printString());
                    ParserUt.getInstance().resetString();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(actionEvent.getSource() == chooseFileButton){

            javaTextArea.setText("");
            javascriptTextArea.setText("");
            chooseFile();

            String jsonPath = null;
            try {
                jsonPath = Esprima.readJS2JSON("userFile.js");
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            readEsprima(jsonPath);

            javaTextArea.setText(ParserUt.getInstance().printString());
            ParserUt.getInstance().resetString();

        }
    }


    public void writeFile(String[] lines) throws IOException {

        File fout = new File("resources/JSFiles/out.js");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < lines.length; i++) {
            bw.write(lines[i]);
            bw.newLine();
        }

        bw.close();

    }

    public void chooseFile(){
        JFileChooser fc = new JFileChooser();
        File media = null;
        int result = fc.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fc.getSelectedFile();

            File newFile = new File( "resources/JSFiles/userFile.js");

            if(newFile.exists()){
                newFile.delete();
            }

            newFile =  new File("resources/JSFiles/userFile.js");

            try {
                Files.copy(selectedFile.toPath(),newFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            media = newFile;
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(media));
            String str;
            while ((str = in.readLine()) != null) {
                javascriptTextArea.append(str + "\n");
            }

        } catch (IOException e) {
        } finally {
            try { in.close(); } catch (Exception ex) { }
        }


    }

    public static void main(String[] args)  {
        Interface i = new Interface();

    }



}

