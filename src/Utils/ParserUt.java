package Utils;


public class ParserUt {
    private int numSpaces;
    private static ParserUt instance=null;

    protected ParserUt(){
        numSpaces=0;
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
        for(int i=0;i<numSpaces;i++){
            System.out.print("  ");
        }
    }
}
