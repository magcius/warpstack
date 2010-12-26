
NAME := WarpStack

JAR := build/$(NAME).jar

SOURCES := $(wildcard *.java)
CLASSES := $(SOURCES:.java=.class)
BUILD_CLASSES := $(foreach i,$(CLASSES),build/$(i))

.PHONY: clean love all

all: $(JAR)

clean:
	rm $(JAR) $(BUILD_CLASSES)

love:
	@echo "Don't know how to make love"

$(BUILD_CLASSES): $(SOURCES)
	javac -cp third-party/Server.jar:third-party/hMod.jar:. -d build $(SOURCES)

$(JAR): $(BUILD_CLASSES)
	jar cf $(JAR) $(foreach i,$(CLASSES),-C build $(i)) $(SOURCES)
