%{
    #include <stdio.h>
    int yylex(void);
    void yyerror(char *);
%}

%token INTEGER

%%

program:
        program expr '\n'         { printf("%d\n", $2); }
        | 
        ;

expr:
         expr '+' term           { $$ = $1 + $3; }
        | term '-' expr           { $$ = $1 - $3; }
	| term			{$$ = $1;}
        ;

term:   INTEGER			{$$ = $1;}
	;
	

%%

void yyerror(char *s) {
    fprintf(stderr, "%s\n", s);
}

int main(void) {
    yyparse();
    return 0;
}
