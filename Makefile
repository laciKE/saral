ANTLR = java -jar ../lib/antlr-4.4-complete.jar
COMPILER = bin/Compiler.class
PREPROCESSOR = bin/preprocessor
VISITOR = src/SaralBaseVisitor.java
GRAMMAR = Saral.g4
LIB_O = lib/libsaral.o
LIB_SO =  lib/libsaral.so
COMPILER_SRCS = $(VISITOR) src/*.java
PREPROCESSOR_SRCS = src/preprocessor.c
LIB_SRCS = src/libsaral.c
STDLIB_O = lib/libstd.o
STDLIB_SO = lib/libstd.so
STDLIB_SRCS = include/libstorage.cpp
LIBS = $(LIB_O) $(LIB_SO) $(STDLIB_O) $(STDLIB_SO)

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

$(LIB_SO): $(LIB_SRCS)
	mkdir -p lib
	gcc -shared -fPIC $(LIB_SRCS) -o $(LIB_SO)

$(LIB_O): $(LIB_SRCS)
	mkdir -p lib
	gcc -fPIC $(LIB_SRCS) -c -o $(LIB_O)

$(STDLIB_SO): $(STDLIB_SRCS)
	mkdir -p lib
	g++ -shared -fPIC $(STDLIB_SRCS) -o $(STDLIB_SO)

$(STDLIB_O): $(STDLIB_SRCS)
	mkdir -p lib
	g++ -fPIC $(STDLIB_SRCS) -c -o $(STDLIB_O)

clean:
	rm -f -r bin
	rm -f lib/*.o lib/*.so
	rm -f src/Saral*.java src/Saral*.tokens
