.PHONY: all main clean

all: clean main run
	
main:
	javac -cp lib/cage-1.0.jar Main.java -d out

clean: 
	rm -rf out/*

run: 
	java -cp lib/cage-1.0.jar:out Main


