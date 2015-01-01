#!/bin/bash
if [ $2 ] ; then
    $2="a.out" ;
fi ;

DIR=`dirname $0`

echo "Compiling $1 into $2"

java -cp $DIR/bin:$CLASSPATH Compiler $1 > $1.ll && \
opt -S -std-compile-opts $1.ll > $1.opt.ll && \
llvm-as $1.opt.ll && \
llc $1.opt.bc -o $1.s && \
#lli -load=$DIR/lib/libsaral.so $1.opt.bc
gcc $DIR/lib/libsaral.o $1.s -o $2
