package Objects;

import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;
import Parser.Types;
import java.util.ArrayList;

public class FunctionDeclaration extends Expression{

    private Identifier id;
    private ArrayList<Identifier> params;
    private ArrayList<String> typesParams;
    private BlockStatement body;
    private Boolean generator; //ainda nao sei para que serve
    private Boolean expression; //ainda não sei para qe serve
    private FunctionState State;
    private ReturnStatement ret;

    public void print(){
        if(ParserUt.getInstance().getPrintState()==Parser.PrintState.GLOBAL_VARIABLES){
            ParserUt.getInstance().writeToBuffer("\n");
            ParserUt.getInstance().setPrintState(Parser.PrintState.FUNCTIONS);
        }

        if(params.size()>0){
            getParamsTypes();
        }
        else
            State=FunctionState.OK;
        ParserUt.getInstance().printSpaces();
        switch(State){
            case OK:
                ParserUt.getInstance().setInFunction(true);
                if(id.getName().equals("main")){ //TODO: public private etc

                    if(ret == null){
                        ParserUt.getInstance().writeToBuffer("public void main(String[] args) {\n");
                    } else { //escreve o type da função
                        ParserUt.getInstance().writeToBuffer("public ");
                        ret.printType();
                        ParserUt.getInstance().writeToBuffer(" main(String[] args) {\n");
                    }

                    ParserUt.getInstance().setPrintState(Parser.PrintState.MAIN);
                }
                else {
                    if(ret == null) {
                        ParserUt.getInstance().writeToBuffer("public void "); //é preciso verificar o type da função
                    }
                    else {
                        ParserUt.getInstance().writeToBuffer("public ");
                        ret.printType();
                        ParserUt.getInstance().writeToBuffer(" ");
                    }
                    id.print();
                    ParserUt.getInstance().writeToBuffer("(");
                    printArgs();
                    ParserUt.getInstance().writeToBuffer("){\n");
                }
                ParserUt.getInstance().addNumSpaces();
                body.print();
                ParserUt.getInstance().subNumSpaces();
                ParserUt.getInstance().printSpaces();
                ParserUt.getInstance().writeToBuffer("}\n\n");
                ParserUt.getInstance().setInFunction(false);
                break;
            case MISS_ARGUMENTS:
                ParserUt.getInstance().writeToBuffer("//Miss the types of some elements of the function.\n\n");
                break;
            case MISS_FUNCTION:
                ParserUt.getInstance().writeToBuffer("//Miss the function in json types.\n\n");
                break;
        }
    }

    private void printArgs(){
        if(params.size()==0){
            return;
        }
        int i=0;
        while (true) {
            ParserUt.getInstance().writeToBuffer(typesParams.get(i)+" ");
            params.get(i).print();
            i++;
            if (i < params.size()) {
                ParserUt.getInstance().writeToBuffer(",");
            } else {
                break;
            }
        }
    }

    private void getParamsTypes(){
        Types tmpTypes=Parser.getTypes();
        try{
            typesParams=tmpTypes.getTypeArgumentFunction(id.getName(),params);
            if(typesParams!=null){
                State=FunctionState.OK;
            }else
                State=FunctionState.MISS_FUNCTION;
        } catch(Exception e){
            State=FunctionState.MISS_ARGUMENTS;
        }
    }

    private enum FunctionState{
        OK,MISS_ARGUMENTS,MISS_FUNCTION
    }
}
