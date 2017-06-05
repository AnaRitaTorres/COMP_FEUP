package Gui;

import Parser.ParserUt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;

import Parser.Parser;


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
    private JButton chooseJSFileButton=null;
    private JRadioButton jsonButton=null;
    private JButton chooseJsonFileButton=null;

    private JOptionPane inputInvalid=null;

    private boolean json=false;
    private Parser parser=null;

    public Interface() throws FileNotFoundException {

        super("JavaScript to Java");

        setSize(1000,1000);
        setLocationRelativeTo(null);

        javascriptTextArea = new JTextArea();
        javascriptNumber = new TextLineNumber(javascriptTextArea);
        javascriptScroll = new JScrollPane(javascriptTextArea);
        javascriptScroll.setRowHeaderView( javascriptNumber);
        javascriptScroll.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Javascript"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),

                        javascriptScroll.getBorder()));

        javaTextArea = new JTextArea();
        javaTextArea.setDisabledTextColor(Color.BLACK);
        javaTextArea.setEnabled(false);
        javaNumber = new TextLineNumber(javaTextArea);
        javaScroll = new JScrollPane(javaTextArea);
        javaScroll.setRowHeaderView( javaNumber);
        javaScroll.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Java"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                        javaScroll.getBorder()));



        menuOptions = new JPanel();

        runButton = new JButton("Run");
        runButton.addActionListener(this);

        chooseJSFileButton = new JButton("Choose JS File");
        chooseJSFileButton.addActionListener(this);

        chooseJsonFileButton = new JButton("Choose JSON File");
        chooseJsonFileButton.addActionListener(this);


        jsonButton = new JRadioButton("Insert Type Json File");
        jsonButton.addActionListener(this);

        menuOptions.add(runButton);
        menuOptions.add(chooseJSFileButton);
        menuOptions.add(chooseJsonFileButton);
        menuOptions.add(jsonButton);


        chooseJsonFileButton.setVisible(false);

        divideEditors = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,javascriptScroll,javaScroll);
        divideEditors.setResizeWeight(0.5);
        divideEditors.setEnabled(false);

        menu = new JSplitPane(JSplitPane.VERTICAL_SPLIT,menuOptions,divideEditors);
        menu.setResizeWeight(0.04);
        menu.setEnabled(false);

        add(menu);

        parser=new Parser();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String javaCode=null;

        File dir = new File("resources/JSFiles");
        File dir1 = new File("resources/JSONFiles");

        if(!dir.exists()){
            dir.mkdirs();
        }
        else if(!dir1.exists()){
            dir1.mkdirs();
        }


        if (actionEvent.getSource() == runButton){

            javaTextArea.setText("");
            String[] lines = javascriptTextArea.getText().split("\\n");

            if(javascriptTextArea.getText().length()==0){
                inputInvalid.showMessageDialog(this,
                        "Input is Empty!","Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(lines.length!=0){
                try {
                    writeFile(lines);
                    javaCode=parser.start("userInput.js",null);

                    javaTextArea.setText(javaCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        else if(actionEvent.getSource() == chooseJSFileButton){

            javaTextArea.setText("");
            javascriptTextArea.setText("");
            boolean chooseFile=chooseFile("resources/JSFiles/userFile.js");

            if(chooseFile){
                if(json)
                    javaCode=parser.start("userFile.js", "types.json");
                else
                    javaCode=parser.start("userFile.js",null);

                javaTextArea.setText(javaCode);
            }

        }
        else if(actionEvent.getSource() == chooseJsonFileButton){

            chooseFile("resources/JSONFiles/types.json");
            chooseJSFileButton.setEnabled(true);
            json=true;
        }
        else if(actionEvent.getSource() == jsonButton){
            if(jsonButton.isSelected()) {
                chooseJsonFileButton.setVisible(true);
                chooseJSFileButton.setEnabled(false);
                runButton.setEnabled(false);
            }
            else {
                chooseJsonFileButton.setVisible(false);
                chooseJSFileButton.setEnabled(true);
                runButton.setEnabled(true);
            }
        }
    }


    public void writeFile(String[] lines) throws IOException {
        File fout = new File("resources/JSFiles/userInput.js");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < lines.length; i++) {
            bw.write(lines[i]);
            bw.newLine();
        }

        bw.close();
    }

    public boolean chooseFile(String path){
        JFileChooser fc = new JFileChooser();
        File media = null;
        boolean fileWasSelected=false;
        int result = fc.showOpenDialog(this);




        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fc.getSelectedFile();
            if(selectedFile.exists())
                fileWasSelected=true;

            File newFile = new File(path);

            if(newFile.exists()){
                newFile.delete();
            }

            newFile =  new File(path);

            try {
                Files.copy(selectedFile.toPath(),newFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            media = newFile;
        }

        BufferedReader in = null;
        if(fileWasSelected && media.getPath().equals("resources/JSFiles/userFile.js")) {
            try {
                in = new BufferedReader(new FileReader(media));
                String str;
                while ((str = in.readLine()) != null) {
                    javascriptTextArea.append(str + "\n");
                }

            } catch (IOException e) {
            } finally {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
        }
        else if (fileWasSelected && media.getPath().equals("resources/JSONFiles/types.json")){

        }

        return fileWasSelected;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Interface i = new Interface();
    }



}

