
.PHONY: clean all

all: WarpStack.jar

WarpStack.class: WarpStack.java
	javac -cp third-party/Server.jar:third-party/hMod.jar -d build WarpStack.java

WarpStack.jar: WarpStack.class
	jar cf build/WarpStack.jar -C build WarpStack.class -C build 'WarpStack$$Listener.class' WarpStack.java

clean:
	rm build/*.class build/WarpStack.jar
