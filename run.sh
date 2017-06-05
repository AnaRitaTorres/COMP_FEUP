if [ ! -d "out" ] ; then
	mkdir out
fi

javac -cp gson-2.8.0.jar -sourcepath src -g src/Gui/Interface.java src/Esprima/rhino/* -d out 
cp -r src/Esprima/js out/Esprima/


case $# in
	0)
		java -cp gson-2.8.0.jar:out Gui.Interface
		;;
	1)
		java -cp gson-2.8.0.jar:out Parser.Parser $1
		;;
	2)
		java -cp gson-2.8.0.jar:out Parser.Parser $1 $2
		;;
	*)	
		echo For using with interface:
		echo $0
		echo For printing in terminal:
		echo $0 jsFile
		echo $0 jsFile jsonFile
		;;
esac



