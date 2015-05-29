%{
#include <stdio.h>

void yyerror(const char *s);

%}

%token VAR ID EQUAL NUM SEMICOLON NEWLINE DOC WRITE OPENPAR CLOSEPAR OPERATOR COMMA DOT QUOTEDSTRING START END


%%

goal:
	START statements END NEWLINE
	;

statements:
	statements statement
	| statement
	;

statement:
	DOC DOT WRITE OPENPAR arg_list CLOSEPAR separator
	{
		//fprintf(stderr, "Found document.write statement\n");
	}
	| declaration separator
	{
		//fprintf(stderr, "Found declaration statement\n");
	}
	| assignment separator
	{
		//fprintf(stderr, "Found assignment statement\n");
	}
	| NEWLINE
	;

declaration:
	VAR ID
	| VAR assignment
	;

assignment:
	ID EQUAL expression
	;

arg_list:
	arg_list COMMA argument
	| argument
	| /*empty*/
	;

argument:
	expression
	;


expression:
	expression OPERATOR TOKEN
	{
		//fprintf(stderr, "Found arithmeic expression\n");
	}
	| TOKEN
	| QUOTEDSTRING
	;
TOKEN:
	ID
	{
		//fprintf(stderr, "Found ID\n");
	}
	| NUM
	{
		//fprintf(stderr, "Found num\n");
	}
	| OPENPAR expression CLOSEPAR
	;

separator:
	NEWLINE
	| SEMICOLON
	;

%%

void yyerror(const char *s)
{
	fprintf(stderr, "%s\n", s);
}

FILE *yyin;
int main(int argc, char **argv)
{
	if(argc == 2)
	{
		FILE *file;
		file = fopen(argv[1], "r");

		if(!file)
			fprintf(stderr, "Error: unable to open file\n");
		
		yyin = file;
		yyparse();
	}
	else
	{
		fprintf(stderr, "Format: ./parser <filename>\n");
	}
}
