package Objects;

import Nodes.Expression;
import Parser.ParserUt;

import java.lang.reflect.Type;

public  class Literal extends Expression {
	private String raw;

	public void print(){
		ParserUt.getInstance().writeToBuffer(raw);
	}

	public void print(String type){
		if(type.contains("String") && !raw.contains("\"")){
			raw = "\"" + raw + "\"";
		}else if(type.contains("Double") && !raw.contains(".")){
			raw += ".0";
		}
		print();
	}
}
