%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "y.tab.h"
#include "lineCounter.h"

%}
%%

"<script type=\"text/JavaScript\">"	{
						return START;
					}

"</script>"	{
			return END;
		}

"var"		{
			return VAR;
		}

"document"	{
			return DOC;
		}

"write"		{
			return WRITE;
		}

=		{
			return EQUAL;
		}

,		{
			return COMMA;
		}

\n		{
			return NEWLINE;
		}

\"[^\"\n]*\"	{
			if(!strcmp(yytext, "\"<br />\""))
			{
				char *p = yytext;
				p++;
				while(*p != '"')
					p++;
				
				*p = '\0';
				yytext++;
				yylval.string_val = strdup(yytext);
				return BREAKLINE;
			
			}
			
			char *p = yytext;
			p++;
			while(*p != '"')
				p++;
			
			*p = '\0';			
			yytext++;
			yylval.string_val = strdup(yytext);
			return QUOTEDSTRING;
		}

\(		{
			return OPENPAR;
		}

\)		{
			return CLOSEPAR;
		}

\{		{
			return OPENBRACE;
		}

\}		{
			return CLOSEBRACE;
		}

:		{
			return COLON;
		}

;		{
			return SEMICOLON;
		}

[+\-*/]		{
			return *yytext;
		}
	
\.		{
			return DOT;
		}


[ \t]		{
			/*Discard whitespaces*/
		}

[0-9][0-9]*	{
			
			yylval.number_val = atoi(yytext);
			return NUM;
		}

[a-zA-Z][a-zA-z0-9_]*	{
				yylval.string_val = strdup(yytext);
				return ID;
			}

%%
