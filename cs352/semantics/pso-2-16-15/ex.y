%{
	#include "def.h"
	#include <stdio.h>
	#include <errno.h>

	FILE *yyin;
	YYSTYPE yylval;
%}

%token <ptr> STR
%token <num> NUM

%%

script: script STR    {printf("%s\n", $2);}
				| script NUM  {printf("%d\n", $2);}
				|
				;

%%

int yyerror(char *s){
	printf("error\n");
}

int main(int argc, char **argv){
	if(argc == 2){
		FILE *fp = fopen(argv[1], "r");
		if(!fp){
			perror("Couldn't open file\n");
		}
		else{
			yyin = fp;
			yyparse();
		}
	}
	else{
		printf("incorrect usage\n");
	}
}
