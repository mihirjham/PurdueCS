%{
#include <stdio.h>
#include "HashTable.h"
#include "lineCounter.h"

void yyerror(const char *s);

enum NodeType
{
	NUM_NODE,
	STRING_NODE,
	OBJECT_NODE,
	UNINITIALIZED_NODE,
	UNDEFINED_NODE
};

struct dataNode
{
	enum NodeType type;
	char *strValue;
	int intValue;
};

struct objectNode
{	
	char *fieldName;
	struct dataNode *data;
	struct objectNode *next;
};

struct objectNode *headObjectList = NULL;
int fromNL = 0;

%}

%token <string_val> ID QUOTEDSTRING BREAKLINE
%token <number_val> NUM
%token VAR EQUAL SEMICOLON NEWLINE DOC WRITE OPENPAR CLOSEPAR COMMA DOT START END OPENBRACE CLOSEBRACE COLON

%type <node_val> token factor expression 
%type <object_val> definition definition_list object
%type <string_val> assignment

%union	{
		char *string_val;
		int number_val;
		struct dataNode *node_val;
		struct objectNode *object_val;
	}

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
		if($1 != NULL)
		{
			if(!isDefined($1))
			{
				if(fromNL)
					fprintf(stderr, "Line %d, type violation\n", linenumber-1);
				else
					fprintf(stderr, "Line %d, type violation\n", linenumber);

				fromNL = 0;
			}
		}
	}
	| NEWLINE { linenumber++; };
	;

declaration:
	VAR ID
	{	
		if(isDefined($2))
		{
			removeSubKey($2);
		}
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = UNINITIALIZED_NODE;
		insertItem($2, node);
		setDefined($2);
	}
	| VAR ID EQUAL expression
	{	
		struct dataNode *node;
		int ret = find($2, (void  **)&node);
		
		if(ret && node->type == OBJECT_NODE)
		{
			removeSubKey($2);
		}
		node = $4;
		insertItem($2, node);
		setDefined($2);
	}
    	| VAR ID EQUAL object 
	{	
		if(isDefined($2))
		{
			removeSubKey($2);	
		}
		struct objectNode *p = headObjectList;
	
		while(p != NULL)
		{
			if(p->fieldName == NULL)
			{	
				p = p->next;
				continue;
			}
			
			char *str = (char *)malloc(strlen($2) + strlen(p->fieldName) + 2);
			strcpy(str, $2);
			strcat(str, ".");
			strcat(str, p->fieldName);
			
			insertItem(str, p->data);
			free(str);
			p = p->next;
		}
		
		p = headObjectList;
		while(p != NULL)
		{
			struct objectNode *del = p;
			p = p->next;
			free(del->fieldName);
			free(del);
		}
		headObjectList = NULL;

		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = OBJECT_NODE;
		insertItem($2, node);
		setDefined($2);
	}
	;

assignment:
	ID EQUAL expression
	{	
		struct dataNode *node;
		int ret = find($1, (void  **)&node);
		
		if(ret && node->type == OBJECT_NODE)
		{
			removeSubKey($1);
		}
		node = $3;
		insertItem($1, node);
		$$ = $1;
	}
	| ID DOT ID EQUAL expression
	{
		char *str = (char *)malloc(strlen($1) + strlen($3) + 2);
		strcpy(str, $1);
		strcat(str, ".");
		strcat(str, $3);
		
		struct dataNode *node = $5; 
		
		int ret = find($1, (void **)&node);
		if(ret && node->type == OBJECT_NODE)
		{
			node = $5;
			insertItem(str, node);
		}
		else
		{
			fprintf(stderr, "Line %d, type violation\n", linenumber);
			//node = (struct dataNode *)malloc(sizeof(struct dataNode);
			//node->type = UNDEFINED_NODE;	
		}
		free(str);
		$$ = $1;
	}
	;

object: 
	OPENBRACE definition_list CLOSEBRACE 
	{
		$$ = $2;
	}
	;

definition_list:
	definition_list COMMA newline_opt definition newline_opt
	{
		if(headObjectList == NULL)
		{
			headObjectList = $4;
		}
		else
		{
			struct objectNode *p = headObjectList;
			while(p && p->next != NULL)
			{
				p = p->next;
			}
			p->next = $4;
		}
		$$ = headObjectList;
	}
	| newline_opt definition newline_opt 
	{
		if(headObjectList == NULL)
		{
			headObjectList = $2;
		}
		else
		{
			struct objectNode *p = headObjectList;
			while(p && p->next != NULL)
			{
				p = p->next;
			}
			p->next = $2;
		}
		$$ = headObjectList;
	}
	| /* empty */
	{
		headObjectList = NULL;
		$$ = headObjectList;
	}
	;

definition:
	ID COLON expression
	{
		struct objectNode *field = (struct objectNode *)malloc(sizeof(struct objectNode));
		field->fieldName = strdup($1);
		field->data = $3;
		field->next = NULL;
		$$ = field;
		//fprintf(stderr, "definition encountered\n");
	}
	;

newline_opt:
	newline_opt NEWLINE { linenumber++;}
	| /*empty */ 
	;

arg_list:
	arg_list COMMA argument
	| argument
	| /*empty*/
	;

argument:
	expression
	{
		if($1->type == NUM_NODE)
		{
			printf("%d", $1->intValue);
		}
		else if($1->type == STRING_NODE)
		{
			if(!strcmp($1->strValue, "<br />"))
				printf("\n");
			else
				printf("%s", $1->strValue);
		}
		else
		{
			printf("undefined");
		}
	}
	;


expression:
	expression '+' factor
	{
		if($1->type == NUM_NODE && $3->type == NUM_NODE)
		{
			struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
			node->type = NUM_NODE;
			node->intValue =  $1->intValue + $3->intValue;
			$$ = node;
		}
		else if($1->type == STRING_NODE && $3->type == STRING_NODE)
		{
			struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
			node->type = STRING_NODE;
			
			char *str = (char *)malloc(strlen($1->strValue) + strlen($3->strValue) + 1);
			strcpy(str, $1->strValue);
			strcat(str, $3->strValue);
			node->strValue =  strdup(str);
			free(str);
			$$ = node;	
		}
		else
		{
			struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
			node->type = UNDEFINED_NODE;
			if(($1->type == NUM_NODE && $3->type == STRING_NODE) || ($1->type == STRING_NODE && $3->type == NUM_NODE))
			{
					fprintf(stderr, "Line %d, type violation\n", linenumber);
			}
			$$ = node;
		}
	}
	| expression '-' factor
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		if($1->type == NUM_NODE && $3->type == NUM_NODE)
		{
			node->type = NUM_NODE;
			node->intValue =  $1->intValue - $3->intValue;
		}
		else
		{
			node->type = UNDEFINED_NODE;
			if($1->type == STRING_NODE || $3->type == STRING_NODE)
				fprintf(stderr, "Line %d, type violation\n", linenumber);
		}
		$$ = node;
	}
	| factor
	{
		$$ = $1;
	}
	;

factor:
	factor '*' token
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		
		if($1->type == NUM_NODE && $3->type == NUM_NODE)
		{
			node->type = NUM_NODE;
			node->intValue =  $1->intValue * $3->intValue;
		}
		else
		{
			node->type = UNDEFINED_NODE;
			if($1->type == STRING_NODE || $3->type == STRING_NODE)
				fprintf(stderr, "Line %d, type violation\n", linenumber);
		}
		$$ = node;
	}
	| factor '/' token
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		
		if($1->type == NUM_NODE && $3->type == NUM_NODE)
		{
			node->type = NUM_NODE;
			node->intValue =  $1->intValue / $3->intValue;
		}
		else
		{
			node->type = UNDEFINED_NODE;
			if($1->type == STRING_NODE || $3->type == STRING_NODE)
				fprintf(stderr, "Line %d, type violation\n", linenumber);
		}
		$$ = node;
	}
	| token
	{
		$$ = $1;
	}
	;

token:
	ID
	{
		struct dataNode *node;
		int ret = find($1, (void **)&node);
		
		if(!ret)
		{
			node = (struct dataNode *)malloc(sizeof(struct dataNode));
			node->type = UNDEFINED_NODE;
			fprintf(stderr, "Line %d, type violation\n", linenumber);
		}
		else
		{
			if(!isDefined($1))
			{
				fprintf(stderr, "Line %d, type violation\n", linenumber);
				node->type = UNDEFINED_NODE;
			}
			
			if(node->type == UNINITIALIZED_NODE)
			{
				fprintf(stderr, "Line %d, %s has no value\n", linenumber, $1);
			}
			else if(node->type == OBJECT_NODE)
			{
				fprintf(stderr, "Line %d, type violation\n", linenumber);
			}
		}
		
		$$ = node;
	}
	| NUM
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = NUM_NODE;
		node->intValue = $1;
		$$ = node;
	}
	| QUOTEDSTRING
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = STRING_NODE;
		node->strValue = strdup($1);
		$$ = node;
	}
	| BREAKLINE
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = STRING_NODE;
		node->strValue = strdup($1);
		$$ = node;	
	}
	| OPENPAR expression CLOSEPAR
	{
		$$ = $2;
	}
	| ID DOT ID
	{
		char *str = (char *)malloc(strlen($1) + strlen($3) + 2);
		strcpy(str, $1);
		strcat(str, ".");
		strcat(str, $3);
		
		struct dataNode *node;
		int ret = find(str, (void **)&node);
		
		if(!ret)
		{
			node = (struct dataNode *)malloc(sizeof(struct dataNode));
			node->type = UNDEFINED_NODE;
			fprintf(stderr, "Line %d, type violation\n", linenumber);
		}
		else
		{
			if(!isDefined($1))
			{
				node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				fprintf(stderr, "Line %d, type violation\n", linenumber);
			}

			if(node->type == UNINITIALIZED_NODE)
			{
				fprintf(stderr, "Line %d, %s has no value\n", linenumber, str);	
			}
		}
		
		free(str);
		$$ = node;
	}
	;

separator:
	NEWLINE
	{
		linenumber++;
		fromNL = 1;
	}
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
		{
			fprintf(stderr, "Error: unable to open file\n");
			return 0;
		}
		yyin = file;
		HashTableInit();
		linenumber = 1;
		yyparse();
	}
	else
	{
		fprintf(stderr, "Format: ./parser <filename>\n");
	}
}
