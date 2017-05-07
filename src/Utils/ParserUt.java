package Utils;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ParserUt {
    private int numSpaces;
    private ByteArrayOutputStream baos;
    private static ParserUt instance=null;

    protected ParserUt(){
        numSpaces=0;
        baos=new ByteArrayOutputStream();
    }

    public static ParserUt getInstance(){
        if(instance==null){
            instance=new ParserUt();
        }

        return instance;
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

    public void printFile(){
        System.out.println(baos.toString());
    }
}
