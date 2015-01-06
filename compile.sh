#!/bin/bash
if [ $2 ] ; then
	output=$2
else
    output="a.out"
fi ;

DIR=`dirname $0`

echo "Compiling $1 into $output"

$DIR/bin/preprocessor $1 $DIR/include/ > $1.prep && \
java -cp $DIR/bin:$DIR/lib/antlr-4.4-complete.jar:$CLASSPATH Compiler $1.prep > $1.ll && \
opt -S -std-compile-opts $1.ll > $1.opt.ll && \
llvm-as $1.opt.ll && \
llc $1.opt.bc -o $1.s && \
#lli -load=$DIR/lib/libsaral.so $1.opt.bc
g++ $DIR/lib/libsaral.o $DIR/lib/libstd.o $1.s -o $output && \
strip $output
rm -f $1.prep $1.ll $1.opt.ll $1.opt.bc $1.s
