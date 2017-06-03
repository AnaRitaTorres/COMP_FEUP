package Objects;

import Nodes.Expression;
import Parser.ParserUt;

import java.lang.reflect.Type;

public  class Literal extends Expression {
	private String raw;
    //Aqui não estendi de BasicNode mas sim de expression, para poder ser usado como uma expressão!
	public Literal(){}
	public Literal createInstance(Type type){
	    return new Literal();
	};

	public void print(){
		ParserUt.getInstance().writeToBuffer(raw);
	}

}
