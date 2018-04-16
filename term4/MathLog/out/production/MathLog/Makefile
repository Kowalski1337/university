all:
	    mkdir -p "bin"
	    javac -d bin -sourcepath . parser/*.java
	    javac -d bin -sourcepath . fastscanner/*.java
	    javac -d bin -sourcepath . HW2.java
	    javac -d bin -sourcepath . expression/*.java

run:
	    java -classpath bin HW2

clean:
	    rm -rf bin HW*.zip output.txt

zip:
	    jar -cMf HW2.zip parser/* fastscanner/* expression/* HW2.java Makefile