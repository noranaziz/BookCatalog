Main.class: Main.java Catalog.class Reader.class Book.class
	javac -g Main.java

Catalog.class: Catalog.java
	javac -g Catalog.java

Reader.class: Reader.java
	javac -g Reader.java

Book.class: Book.java
	javac -g Book.java

run: Main.java
	java Main

clean:
	rm *.class
	rm readers.dat

debug: Main.class
	jdb Main
