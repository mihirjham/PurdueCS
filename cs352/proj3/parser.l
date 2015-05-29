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

"if"		{
			return IF;
		}

"else"		{
			return ELSE;
		}

"true"		{
			return TRUE;
		}

"false"		{
			return FALSE;
		}

"while"		{
			return WHILE;
		}
		
"break"		{
			return BREAK;
		}

"continue"	{
			return CONTINUE;
		}

"do"		{
			return DO;
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

"["		{
			return OPENHARD;
		}

"]"		{
			return CLOSEHARD;
		}

:		{
			return COLON;
		}

;		{
			return SEMICOLON;
		}

"||"		{
			return OROR;
		}

"&&"		{
			return ANDAND;
		}

"!"		{
			return NOT;
		}

"<"		{
			return LESS;
		}

"<="		{
			return LESSEQUAL;
		}

">"		{
			return GREATER; 
		}

">="		{
			return GREATEREQUAL;
		}

"=="		{
			return EQUALEQUAL;
		}

"!="		{
			return NOTEQUAL;
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


[a-zA-Z][a-zA-Z0-9_]*	{
				yylval.string_val = strdup(yytext);
				return ID;
			}

%%
