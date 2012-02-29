all: compile

compile: src/*.java
	mkdir classes;\
	javac -sourcepath src -classpath classes src/*.java -d classes

.PHONY: clean

clean: 
	rm -rf classes

.PHONY: run

run:
	java -classpath classes DbConsole
