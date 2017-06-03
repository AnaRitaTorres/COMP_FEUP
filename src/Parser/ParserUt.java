package Parser;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ParserUt {
    private int numSpaces;
    private ByteArrayOutputStream baos;
    private static ParserUt instance=null;
    private PrintState printState=PrintState.GLOBAL_VARIABLES;

    protected ParserUt(){
        numSpaces=0;
        baos=new ByteArrayOutputStream();
        initializeBuffer();
    }

    public static ParserUt getInstance(){
        if(instance==null){
            instance=new ParserUt();
        }

        return instance;
    }

    public void initializeBuffer(){
        try {
            baos.write("public class MainClass{\n".getBytes());
            addNumSpaces();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNumSpaces(){
        numSpaces++;
    }

    public void subNumSpaces(){
        if(numSpaces>0){
            numSpaces--;
        }
    }

    public void printSpaces(){
        try{
            for(int i=0;i<numSpaces;i++){
                baos.write("    ".getBytes());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void writeToBuffer(String line){
        try {
            baos.write(line.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eraseCharactersFromBuffer(int numCharacters){
        String tmpBuffer=baos.toString();
        baos.reset();
        baos.write(tmpBuffer.getBytes(),0,tmpBuffer.length()-numCharacters);
    }

    public void printFile(){
        try {
            subNumSpaces();
            baos.write("}\n".getBytes());
            System.out.println(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String printString(){
        return baos.toString();
    }

    public void resetString(){
        baos.reset();
    }

    public enum PrintState{
        GLOBAL_VARIABLES,FUNCTIONS,MAIN
    }
}
