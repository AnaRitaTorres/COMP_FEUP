package Objects;

import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

public class VariableDeclarator extends Expression {

    private Expression id;
    private Expression init;

    public VariableDeclarator(){};

    public void print(){

        id.print();

        if(init != null) {
            ParserUt.getInstance().writeToBuffer(" = ");
            try {
                String type = Parser.getVarType(id.getName());
                init.print(type);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public Expression getId(){
        return id;
    }
}
