%{
    #include <stdio.h>
    void yyerror(char *);
    int yylex(void);

    int sym[26];
%}

%token INTEGER ID
%left '+' '-'
%left '*' '/'

%%

program: L ;

L: 	L statement '\n'
        |
        ;

statement:
        expression                { printf("%d\n", $1); }
        | ID '=' expression       { sym[$1] = $3; }
        ;

expression:
        INTEGER
        | ID                            { $$ = sym[$1]; }
        | expression '+' expression     { $$ = $1 + $3; }
        | expression '-' expression     { $$ = $1 - $3; }
        | expression '*' expression     { $$ = $1 * $3; }
        | expression '/' expression     { $$ = $1 / $3; }
        | '(' expression ')'            { $$ = $2; }
        ;

%%

void yyerror(char *s) {
    fprintf(stderr, "%s\n", s);
}

int main(void) {
    yyparse();
}
