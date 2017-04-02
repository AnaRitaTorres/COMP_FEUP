package Objects;

import Nodes.Expression;

import java.lang.reflect.Type;

public  class Literal extends Expression {
    //Aqui não estendi de BasicNode mas sim de expression, para poder ser usado como uma expressão!
	public Literal(){}
	public Literal createInstance(Type type){
	    return new Literal();
	};

}
