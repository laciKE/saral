grammar Saral;

INDENT : '__INDENT';
DEDENT : '__DEDENT';

init
	: ((extern_func_declaration | extern_proc_declaration) EOL)* statement*
	;

block
	: INDENT statement+ DEDENT
	;

func_block
	: INDENT statement+ ret DEDENT
	;

ret : RET expression;

statement
	: simple_statement EOL
	| block_statement
	;

simple_statement
	: assignment
	| var_declaration
	| var_definition
	| const_declaration
	| const_definition
	| array_declaration
	| func_definition
	| proc_definition
	| proc_call
	| WRITE var
	| READ var
	| WRITE2 var
	| READ2 var
	| expression
	;

block_statement
	: if_statement
	| while_statement
	| for_statement
	| block
	;

var 
	: ID # VarID
	| var LBRACK expression RBRACK #VarArray
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
	: FOR ID FROM val TO val EOL block
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

var_definition
	: VARIABLE type ID ('=' expression)?
	;
var_declaration
	: VARIABLE type ID
	;
const_definition
	: CONST type ID ('=' expression)?
	;
const_declaration
	: CONST type ID
	;
array_declaration
	: ARRAY type ID (LBRACK expression RBRACK)+
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
ARRAY : 'dimenzion'? 'funduš';
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
READ : 'vežmi';
WRITE : 'ciskaj';
READ2 : 'sluchaj';
WRITE2 : 'povidz';

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

ID: '_'?('a'..'z' | 'A'..'Z')([a-zA-Z0-9])* ;

EMPTY_LINE: {getCharPositionInLine()==0}? ((' '|'\t')* EOL) -> skip ;

EOL: '\r'? '\n' | '\r';
WS: (' ' | '\t')+ -> skip;

NUMBER : '0' | [1-9]DIGIT*;
DIGIT : [0-9];
