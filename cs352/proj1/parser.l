%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "y.tab.h"
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

\"[^\n]*\"	{
			return QUOTEDSTRING;
		}

\(		{
			return OPENPAR;
		}

\)		{
			return CLOSEPAR;
		}

;		{
			return SEMICOLON;
		}

[+\-*/]		{
			return OPERATOR;
		}
	
\.		{
			return DOT;
		}

\n		{
			return NEWLINE;
		}

[ \t]		{
			/*Discard whitespaces*/
		}

[0-9][0-9]*	{
			return NUM;
		}

[a-zA-Z][a-zA-z0-9_]*	{
				return ID;
			}

%%
