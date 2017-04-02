cd Parser
jjtree Parser.jjt
javacc Parser.jj
cd ..
javac -cp .:gson-2.8.0.jar  Objects/*.java Main/*.java Parser/*.java -d out
cd out
java  Parser -jar ../gson-2.8.0.jar 
