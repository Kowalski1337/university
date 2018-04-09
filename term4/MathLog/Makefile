all:
	    mkdir -p "bin"
	    javac -d bin -sourcepath . parser/*.java
	    javac -d bin -sourcepath . fastscanner/*.java
	    javac -d bin -sourcepath . HW0.java
	    javac -d bin -sourcepath . expression/*.java

run:
	    java -classpath bin HW0

clean:
	    rm -rf bin HW*.zip output.txt

zip:
	    jar -cMf HW0.zip parser/* fastscanner/* expression/* HW0.java Makefile