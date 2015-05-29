%{
	#include "def.h"
	#include "y.tab.h"
	#include <stdio.h>

%}

%%

[a-zA-Z]+  {yylval.ptr = strdup(yytext); return STR;}
[0-9]+     {yylval.num = atoi(yytext); return NUM;}
.          {}
\n         {}

%%
