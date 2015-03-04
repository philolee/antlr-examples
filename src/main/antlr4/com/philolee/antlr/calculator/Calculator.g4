grammar Calculator;

prog:   stat+ ;

stat:   expr # printExpr
    |   ID EQL expr  # assign
    ;

expr:   expr op=(MUL|DIV) expr      # MulDiv
    |   expr op=(ADD|SUB) expr      # AddSub
    |   INT                         # int
    |   ID                          # id
    |   '(' expr ')'                # parens
    ;

MUL :   '*' ; // assigns token name to '*' used above in grammar
DIV :   '/' ;
ADD :   '+' ;
SUB :   '-' ;
EQL :   '=' ;
ID  :   [a-zA-Z]+ ;      // match identifiers
INT :   [0-9]+ ;         // match integers
NEWLINE:'\r'? '\n' ;     // return newlines to parser (is end-statement signal)
WS  :   [ \t]+ -> skip ; // toss out whitespace