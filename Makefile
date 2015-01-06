ANTLR = java -jar ../lib/antlr-4.4-complete.jar
COMPILER = bin/Compiler.class
PREPROCESSOR = bin/preprocessor
VISITOR = src/SaralBaseVisitor.java
GRAMMAR = Saral.g4
LIB_O = lib/libsaral.o 
LIB_SO =  lib/libsaral.so
LIBS = $(LIB_O) $(LIB_SO)
COMPILER_SRCS = $(VISITOR) src/*.java
PREPROCESSOR_SRCS = src/preprocessor.c
LIBS_SRCS = src/libsaral.c

default: $(COMPILER)

all: $(VISITOR) $(LIBS) $(PREPROCESSOR) $(COMPILER)

$(PREPROCESSOR): $(PREPROCESSOR_SRCS)
	mkdir -p bin
	gcc $(PREPROCESSOR_SRCS) -o $(PREPROCESSOR)

$(VISITOR): grammar/$(GRAMMAR)
	cd grammar &&	$(ANTLR) $(GRAMMAR) -o ../src/ -visitor && cd ..

$(COMPILER): $(PREPROCESSOR) $(VISITOR) $(COMPILER_SRCS) $(LIBS)
	mkdir -p bin
	javac -classpath lib/antlr-4.4-complete.jar src/*.java -d bin/

$(LIB_SO): $(LIBS_SRCS)
	mkdir -p lib
	gcc -shared -fPIC $(LIBS_SRCS) -o $(LIB_SO)

$(LIB_O): $(LIBS_SRCS)
	mkdir -p lib
	gcc -fPIC $(LIBS_SRCS) -c -o $(LIB_O)

clean:
	rm -f -r bin
	rm -f lib/*.o lib/*.so
	rm -r src/Saral*.java src/Saral*.tokens
