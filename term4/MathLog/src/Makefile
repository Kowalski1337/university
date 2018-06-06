all:
	    mkdir -p "bin"
	    javac -d bin -sourcepath . parser/*.java
	    javac -d bin -sourcepath . fastscanner/*.java
	    javac -d bin -sourcepath . HW3.java
	    javac -d bin -sourcepath . expression/*.java

run:
	    java -classpath bin HW3

clean:
	    rm -rf bin HW*.zip output.txt

zip:
	    jar -cMf HW3.zip parser/* fastscanner/* expression/* HW3.java Makefile