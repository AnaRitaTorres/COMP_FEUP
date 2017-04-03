package Esprima.rhino;

import static javax.script.ScriptContext.ENGINE_SCOPE;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Esprima {

    static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
    }

    static String writeNewJSONFile(String content,String fileName){
        String filePath="resources/JSONFiles/"+fileName+".json";

        try{
            PrintWriter writer = new PrintWriter(filePath, "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {

        }

        return filePath;
    }

    public static String readJS2JSON(String jsFile) throws ScriptException, IOException, NoSuchMethodException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");

        ScriptContext context = engine.getContext();

        engine.eval(readFile("src/Esprima/js/esprima.js"), context);

        context.setAttribute("__dirname", "/home/foo", ENGINE_SCOPE);
        context.setAttribute("__filename", "client.js", ENGINE_SCOPE);

        Invocable inv = (Invocable) engine;
        Object esprima = engine.get("esprima");

        String fileName=jsFile.split("\\.")[0];
        Object tree = inv.invokeMethod(esprima, "parse", readFile("resources/JSFiles/"+jsFile));

        Object JSON = engine.get("JSON");

        String json = (String) inv.invokeMethod(JSON, "stringify", tree, null, 2);
        String filePath=writeNewJSONFile(json,fileName);

        return filePath;
    }
}
