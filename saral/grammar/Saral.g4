grammar Saral;

INDENT : '__INDENT';
DEDENT : '__DEDENT';

init
	: ((extern_func_declaration | extern_proc_declaration) EOL)* statements
	;

statements : statement+;

block
	: INDENT statements DEDENT
	;

func_block
	: INDENT statements ret DEDENT
	;

ret : RET expression;

statement
	: simple_statement EOL
	| block_statement EOL?
	;

simple_statement
	: assignment
	| var_declaration
	| var_definition
	| const_definition
	| array_declaration
	| func_definition
	| proc_definition
	| proc_call
	| write
	| read
	| write2
	| read2
	;

block_statement
	: if_statement
	| while_statement
	| for_statement
	| block
	;

var 
	: ID # VarID
	| ID LBRACK expression RBRACK #VarArray
	;

val 
	: var # ValVar
	| INT # ValInt
	| FLOAT # ValFloat
	| STRING # ValString
	| CHAR # ValChar
	| BOOL # ValBool
	;
	
assignment
	: var '=' expression
	;

if_statement
	: IF expression THEN EOL block (ELSE EOL block)?
	;

while_statement
	: WHILE expression DO EOL block
	;

for_statement
	: FOR var FROM val TO val EOL block
	;


func_definition
	: FUNCTION type ID LPAR arglist RPAR EOL func_block
	;
proc_definition
	: FUNCTION ID LPAR arglist RPAR EOL block
	;
extern_func_declaration
	: EXTERN FUNCTION type ID LPAR arglist RPAR
	;
extern_proc_declaration
	: EXTERN FUNCTION ID LPAR arglist RPAR
	;

func_call
	: FUNC_CALL ID LPAR param_list RPAR
	;
proc_call
	: PROC_CALL ID LPAR param_list RPAR
	;

arglist
	: (type ID (',' type ID)*)?
	;
param_list
	: (expression (',' expression)*)?
	;

read
	: 'vežmi' var
	;
write
	: 'ciskaj' var
	;
read2
	: 'sluchaj' var
	;
write2
	: 'povidz' var
	;

var_definition
	: VARIABLE type ID '=' expression
	;
var_declaration
	: VARIABLE type ID
	;
const_definition
	: CONST type ID '=' expression
	;
array_declaration
	: ARRAY type ID LBRACK expression RBRACK
	;


type
	: ARRAY type # typeArray
	| (INT_T | BOOL_T | FLOAT_T | CHAR_T | STRING_T) # typeBasic
	;

expression
	: LPAR expression RPAR # Paren
	| func_call # Func
	| op=SUB expression # UnaryMinus
	| expression op=(MUL | DIV | IDIV | MOD) expression # Mul
	| expression op=(ADD | SUB) expression # Add
	| expression op=(EQ | NE | LE | GE | GT | LT) expression # Compare
	| op=NOT expression # BoolNot
	| expression op=AND expression # BoolAnd
	| expression op=OR expression # BoolOr
	| val # Value
	;

VARIABLE : 'meňak';
CONST : 'furt';
ARRAY : 'dimenzion'? WS* 'funduš';
WHILE : 'kým';
DO : 'rob'; 
FOR : 'zrob s meňakom';
FROM : 'od';
TO : 'do';
IF : 'keď';
THEN : 'potom';
ELSE : 'inak';
FUNCTION: 'bar';
EXTERN : 'inakši';
RET : 'vrac';
FUNC_CALL : 'vrac mi z baru';
PROC_CALL : 'paľ do baru';

LPAR : '(';
RPAR : ')';
LBRACK: '[';
RBRACK: ']';

AND : 'a';
OR : 'alebo';
NOT : 'ne';

ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
IDIV: ':';
MOD: '%';

EQ: '==';
NE: '<>';
LE: '<=';
GE: '>=';
GT: '>';
LT: '<';

BOOL_T : 'logický';
INT_T : 'neskutočné numeralio';
FLOAT_T: 'skutočné numeralio';
CHAR_T: 'písmeno';
STRING_T: 'slovo';

BOOL : 'pravda' | 'ošaľ' | 'skoroošaľ';
INT : NUMBER;
FLOAT : NUMBER '.' DIGIT*;
STRING : '"' (~'"' | EOL)* '"';
CHAR : '\'' (~'\'') '\'';

ID : '_'?(LETTER)(LETTER | DIGIT | '_')* ;
LETTER : [a-zA-ZľščťžýáíéäúôóďĺĽŠČŤŽÝÁÍÉÄÚÔÓĎĹ];


EMPTY_LINE : {getCharPositionInLine()==0}? ((' '|'\t')* EOL) -> skip ;

EOL : '\r'? '\n' | '\r';
WS : (' ' | '\t')+ -> skip;

NUMBER : '0' | [1-9]DIGIT*;
DIGIT : [0-9];
