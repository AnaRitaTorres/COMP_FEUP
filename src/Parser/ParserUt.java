package Parser;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import Parser.Parser.PrintState;

public class ParserUt {
    private int numSpaces;
    private ByteArrayOutputStream baos;
    private static ParserUt instance=null;
    private static Parser.PrintState printState= Parser.PrintState.GLOBAL_VARIABLES;
    private boolean inFunction=false;

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
            baos.write("public class MainClass {\n".getBytes());
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

    public String printFile(){
        try {
            if(printState!=PrintState.MAIN){
                baos.reset();
                baos.write("//Main function doesn't exists.".getBytes());
            } else {
                subNumSpaces();
                baos.write("}\n".getBytes());
            }
            System.out.println(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toString();
    }

    public void clearLastSpaces(){
        String reverseBuffer = new StringBuilder(baos.toString()).reverse().toString();
        String[] parts=reverseBuffer.split("[(]",2);
        String first="("+new StringBuilder(parts[0]).reverse().toString();
        String second=new StringBuilder(parts[1]).reverse().toString();
        first=first.replaceAll("    ","");
        baos.reset();
        try {
            baos.write(second.getBytes());
            baos.write(first.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetString(){
        baos.reset();
        initializeBuffer();
    }

    public boolean getInFunction(){
        return inFunction;
    }

    public void setInFunction(boolean in){
        inFunction=in;
    }

    public PrintState getPrintState(){
        return printState;
    }

    public void setPrintState(PrintState state){
        printState=state;
    }
}
