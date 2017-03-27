cd Parser
jjtree Parser.jjt
javacc Parser.jj
cd ..
javac -cp .:gson-2.8.0.jar Parser/*.java Main/*.java -d out
java -cp Parser Parser
