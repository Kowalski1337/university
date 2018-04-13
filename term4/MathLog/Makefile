all:
	    mkdir -p "bin"
	    javac -d bin -sourcepath . parser/*.java
	    javac -d bin -sourcepath . fastscanner/*.java
	    javac -d bin -sourcepath . HW1.java
	    javac -d bin -sourcepath . expression/*.java

run:
	    java -classpath bin HW1

clean:
	    rm -rf bin HW*.zip output.txt

zip:
	    jar -cMf HW1.zip parser/* fastscanner/* expression/* HW1.java Makefile