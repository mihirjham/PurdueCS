%{
    #include <stdio.h>
    int yylex(void);
    void yyerror(char *);
%}

%token ID
%token INT

%%

program:
        program expr '\n'	{ printf("%d\n", $2); }
	|
        ;

expr:
        expr '+' term   {$$ = $1 + $3; }
	| expr '-' term {$$ = $1 - $3; }
	| term
        ;

term:	factor
	| term '*' factor {$$ = $1 * $3; }
	| term '/' factor {$$ = $1 / $3; }

factor:
	'(' expr ')'    {$$ = $2;}
	| INT
	;


%%

void yyerror(char *s) {
    fprintf(stderr, "%s\n", s);
}

int main(void) {
    yyparse();
    return 0;
}

