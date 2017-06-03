package Objects;

import Nodes.Expression;
import Parser.ParserUt;

public class CatchClause extends Expression{
     private Identifier param;
     private BlockStatement body;

     public void print(){
          ParserUt.getInstance().writeToBuffer("catch(Exception "+param.getName()+"){\n");
          ParserUt.getInstance().addNumSpaces();
          body.print();
          ParserUt.getInstance().subNumSpaces();
          ParserUt.getInstance().printSpaces();
          ParserUt.getInstance().writeToBuffer("}");
     }
}
