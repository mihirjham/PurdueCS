%{
#include <stdio.h>
#include "HashTable.h"
#include "ast.h"
#include "lineCounter.h"

void yyerror(const char *s);

struct objectNode *headObjectList = NULL;
int fromNL = 0;
//struct statementList *list = NULL;
argumentList *printList = NULL;
struct arrayNode *headArrayList = NULL;
int lastIndex = 0;
int fromIf = 0;
int ifList = 0;
%}

%token <string_val> ID QUOTEDSTRING BREAKLINE
%token <number_val> NUM
%token VAR EQUAL SEMICOLON NEWLINE DOC WRITE OPENPAR CLOSEPAR COMMA DOT START END OPENBRACE CLOSEBRACE COLON IF ELSE FUNCTION RETURN
%token OROR ANDAND NOT LESS LESSEQUAL GREATER GREATEREQUAL EQUALEQUAL NOTEQUAL TRUE FALSE WHILE BREAK CONTINUE DO OPENHARD CLOSEHARD ASSERT 

%type <tree_node_val> token factor expression condition boolean not_expression declaration assignment if_expression else_opt loop function
%type <object_val> definition definition_list object
%type <print_val> argument arg_list parameters  
%type <array_val> array member_list
%type <statement_val> statements statement
%type <id_val> id_list;

%union	{
		char *string_val;
		int number_val;
		struct objectNode *object_val;
		struct ASTNode *tree_node_val;
		struct argumentList *print_val;
		struct arrayNode *array_val;
		struct statementList *statement_val;
		struct idList *id_val;
	}

%%

goal:
	START statements END NEWLINE
	{
		list = $2;
		/*struct statementList *curr = list;
		while(curr)
		{
			fprintf(stderr, "Line=%d\tType=%d\n", curr->root->linenumber, curr->root->type);
			curr = curr->next;
		}*/
	}
	;

statements:
	statements statement
	{	
		struct statementList *curr = $1;
		while(curr && curr->next != NULL)
		{	
			curr = curr->next;
		}
		curr->next = $2;
		$$ = $1;
	}
	| statement
	{	
		$$ = $1;
	}
	;

statement:
	DOC DOT WRITE OPENPAR arg_list CLOSEPAR separator
	{
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)
			newItem->root = makeNode(Print, NULL, NULL, $5, linenumber-1);
		else
			newItem->root = makeNode(Print, NULL, NULL, $5, linenumber);
		newItem->next = NULL;
		newItem->errorReported = 0;	
		/*if(!list)
		{
			list = newItem;	
		}		
		else
		{
			struct statementList *curr = list;
			while(curr && curr->next != NULL)
				curr = curr->next;
			
			curr->next = newItem;
		}*/
		
		printList = NULL;
		
		$$ = newItem;
	}
	| declaration separator
	{	
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)
			newItem->linenumber = linenumber-1;
		else
			newItem->linenumber = linenumber;
		newItem->root = $1;
		newItem->next = NULL;
		newItem->errorReported = 0;
		
		/*if(!list)
		{
			list = newItem;	
		}		
		else
		{
			struct statementList *curr = list;
			while(curr && curr->next != NULL)
				curr = curr->next;
			
			curr->next = newItem;
		}*/
		fromNL = 0;
		$$ = newItem;
	}
	| assignment separator
	{
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)		
			newItem->linenumber = linenumber-1;
		else
			newItem->linenumber = linenumber;
		
		newItem->root = $1;
		newItem->next = NULL;
		newItem->errorReported = 0;
		/*if(!list)
		{
			list = newItem;	
		}		
		else
		{
			struct statementList *curr = list;
			while(curr && curr->next != NULL)
				curr = curr->next;
			
			curr->next = newItem;
		}*/		
		fromNL = 0;
		$$ = newItem;
	}
	| if_expression 
	{
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		newItem->linenumber = linenumber;
		newItem->root = $1;
		newItem->next = NULL;
		newItem->errorReported = 0;
		
		/*ASTNode *ifNode = $1;
		struct statementList *item = newItem;
		while(ifNode->right)
		{
			struct statementList *new = (struct statementList *)malloc(sizeof(struct statementList));
			new->linenumber = linenumber;
			new->root = ifNode->right;
			new->next = NULL;
			new->errorReported = 0;
			item->next = new;
			item = item->next;
			ifNode = ifNode->right;
		
		}*/
		/*if(!list)
		{
			list = newItem;
		}
		else
		{
			struct statementList *curr = list;
			while(curr && curr->next != NULL)
				curr = curr->next;

			curr->next = newItem;
		}*/
		$$ = newItem;
	}
	| loop
	{
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		newItem->root = $1;
		newItem->next = NULL;
		newItem->errorReported = 0;
		$$ = newItem;
	}
	| function
	{
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		newItem->root = $1;
		newItem->next = NULL;
		newItem->errorReported = 0;
		$$ = newItem;
	}
	| RETURN not_expression separator
	{
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)
			newItem->root = makeNode(Return, $2, NULL, NULL, linenumber-1); 
		else
			newItem->root = makeNode(Return, $2, NULL, NULL, linenumber);

		newItem->next = NULL;
		newItem->errorReported = 0;
		fromNL = 0;
		$$ = newItem;
	}
	| ASSERT OPENPAR not_expression CLOSEPAR separator
	{
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		newItem->root = makeNode(Assert, $3, NULL, NULL, $3->linenumber);
		newItem->next = NULL;
		newItem->errorReported = 0;
		fromNL = 0;
		$$ = newItem;
	}
	| ID OPENPAR parameters CLOSEPAR separator
	{	
		ASTNode *name;
		ASTNode *call;
		if(fromNL)
		{
			name = makeNode(FunctionName, NULL, NULL, strdup($1), linenumber-1);
			call = makeNode(FunctionCall, name, NULL, $3, linenumber-1);
		}
		else
		{
			name = makeNode(FunctionName, NULL, NULL, strdup($1), linenumber);
			call = makeNode(FunctionCall, name, NULL, $3, linenumber);
		}
		
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		newItem->root = call;
		newItem->next = NULL;
		$$ = newItem;
	}
	| NEWLINE 
	{ 
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		newItem->root = makeNode(Newline, NULL, NULL, NULL, linenumber);
		newItem->next = NULL;
		linenumber++;  
		
		$$ = newItem;
	}
	| BREAK separator
	{
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)
			newItem->root = makeNode(Break, NULL, NULL, NULL, linenumber-1);
		else
			newItem->root = makeNode(Break, NULL, NULL, NULL, linenumber);

		newItem->next = NULL;
		newItem->errorReported = 0;
		fromNL = 0;
		$$ = newItem;
	}
	| CONTINUE separator
	{
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)
			newItem->root = makeNode(Continue, NULL, NULL, NULL, linenumber-1);
		else
			newItem->root = makeNode(Continue, NULL, NULL, NULL, linenumber);
         	
		newItem->next = NULL;
		newItem->errorReported = 0;
		fromNL = 0;
		$$ = newItem;
	}
	;

declaration:
	VAR ID
	{	
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup($2), linenumber);

		$$ = makeNode(Declare, treeNode, NULL, NULL, linenumber);
	}
	| VAR ID EQUAL not_expression
	{	
		ASTNode *tree1 = makeNode(Variable, NULL, NULL, strdup($2), linenumber);		
		ASTNode *treeNode = makeNode(Declare, tree1, $4, NULL, linenumber);
		$$ = treeNode;
	}
    	| VAR ID EQUAL object 
	{	
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup($2), linenumber);
		ASTNode *fields = makeNode(Object, NULL, NULL, $4, linenumber);
		headObjectList = NULL;
		$$ = makeNode(Declare, treeNode, fields, NULL, linenumber);
	}
	| VAR ID EQUAL array 
	{
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup($2), linenumber);
		ASTNode *fields = makeNode(Array, NULL, NULL, $4, linenumber);
		headArrayList = NULL;
		lastIndex = 0;
		$$ = makeNode(Declare, treeNode, fields, NULL, linenumber);
	}
	;

assignment:
	ID EQUAL not_expression
	{	
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup($1), linenumber);
		$$ = makeNode(Assign, treeNode, $3, NULL, linenumber);
	}
	| ID DOT ID EQUAL not_expression
	{
		char *str = (char *)malloc(strlen($1) + strlen($3) + 2);
		strcpy(str, $1);
		strcat(str, ".");
		strcat(str, $3);
		
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup(str), linenumber);
		free(str);
		$$ = makeNode(Assign, treeNode, $5, NULL, linenumber);
	}
	| ID OPENHARD not_expression CLOSEHARD EQUAL not_expression
	{	
		ASTNode *treeNode = makeNode(ArrayAssign, $3, $6, strdup($1), linenumber);
		$$ = treeNode;
	}
	;

if_expression:
	IF OPENPAR not_expression CLOSEPAR OPENBRACE NEWLINE{linenumber++;} statements CLOSEBRACE else_opt
	{	
		struct statementList *curr = $8;
		int sList = 0;
		while(curr)
		{
			if(curr->root->type != Newline)
				sList++;
			curr = curr->next;

		}
		$$ = makeNode(If, $3, $10, $8, $3->linenumber);
	}
	| IF OPENPAR not_expression CLOSEPAR OPENBRACE NEWLINE{linenumber++;} CLOSEBRACE else_opt
	{
		$$ = makeNode(If, $3, $9, NULL, $3->linenumber);
	}
	;
else_opt:
	ELSE if_expression
	{
		$$ = $2;
	}
	| ELSE OPENBRACE NEWLINE{linenumber++;} statements CLOSEBRACE NEWLINE 
	{
		linenumber++;
		struct statementList *curr = $5;
		int sList = 0;
		while(curr)
		{
			if(curr->root->type != Newline)
				sList++;
			curr = curr->next;
		}
		$$ = makeNode(Else, NULL, NULL, $5, linenumber);
	}
	| ELSE OPENBRACE NEWLINE{linenumber++;} CLOSEBRACE NEWLINE
	{
		linenumber++;
		$$ = makeNode(Else, NULL, NULL, NULL, linenumber);
	}
	| /* empty */
	{
		$$ = NULL;
	}
	;

loop:
	WHILE OPENPAR not_expression CLOSEPAR OPENBRACE NEWLINE {linenumber++;} statements CLOSEBRACE NEWLINE
	{
		linenumber++;
		struct statementList *curr = $8;
		int sList = 0;
		while(curr)
		{
			if(curr->root->type != Newline)
				sList++;
			curr = curr->next;
		}
		$$ = makeNode(While, $3, NULL, $8, $3->linenumber);
	}
	| WHILE OPENPAR not_expression CLOSEPAR OPENBRACE NEWLINE{linenumber++;} CLOSEBRACE NEWLINE
	{
		linenumber++;
		$$ = makeNode(While, $3, NULL, NULL, $3->linenumber);
	}
	| DO OPENBRACE NEWLINE{linenumber++;} statements CLOSEBRACE NEWLINE{linenumber++;} WHILE OPENPAR not_expression CLOSEPAR separator
	{
		struct statementList *curr = $5;
		int sList = 0;
		while(curr)
		{
			if(curr->root->type != Newline)
				sList++;
			curr = curr->next;
		}
		fromNL = 0;
		$$ = makeNode(DoWhile, $11, NULL, $5, $11->linenumber);
	}
	| DO OPENBRACE NEWLINE {linenumber++;} CLOSEBRACE NEWLINE{linenumber++;} WHILE OPENPAR not_expression CLOSEPAR separator
	{
		
		$$ = makeNode(DoWhile, $10, NULL, NULL, $10->linenumber);
		fromNL = 0;
	}
	;

function:
	FUNCTION ID OPENPAR id_list CLOSEPAR OPENBRACE NEWLINE{linenumber++;} statements CLOSEBRACE NEWLINE
	{
		linenumber++;
		
		struct functionList *newFunc = (struct functionList *)malloc(sizeof(struct functionList));
		newFunc->functionName = strdup($2);
		newFunc->numberOfParameters = 0;
		struct idList *item = $4;
		if(item->id)
		{
			newFunc->parameters = $4;
			while(item)
			{
				newFunc->numberOfParameters++;
				item = item->next;
			}
		}
		else
		{
			newFunc->parameters = NULL;
		}

		newFunc->statements = $9;
		newFunc->next = NULL;
		struct functionList *funcs = functions;
		while(funcs)
		{
			if(!strcmp(funcs->functionName, newFunc->functionName) && (funcs->numberOfParameters == newFunc->numberOfParameters))
			{
				fprintf(stderr, "Line %d, Type Violation\n", $4->linenumber);
				break;
			}
			funcs = funcs->next;
		}

		funcs = functions;
		while(funcs && funcs->next != NULL)
		{
			funcs = funcs->next;
		}
		if(funcs)
			funcs->next = newFunc;
		else
			functions = newFunc;
		
		$$ = makeNode(FunctionDefinition, NULL, NULL, strdup($2), $4->linenumber);
	}
	;

id_list:
	id_list COMMA ID
	{
		struct idList *item = (struct idList *)malloc(sizeof(struct idList));
		item->id = strdup($3);
		item->next = NULL;

		struct idList *currItem = $1;
		while(currItem && currItem->next != NULL)
			currItem = currItem->next;
		
		currItem->next = item;
		$$ = $1;
	}
	| ID
	{
		struct idList *item = (struct idList *)malloc(sizeof(struct idList));
		item->id = strdup($1);
		item->linenumber = linenumber;
		item->next = NULL;
		$$ = item;
	}
	| /*empty*/
	{
		struct idList *item = (struct idList *)malloc(sizeof(struct idList));
		item->id = NULL;
		item->next = NULL;
		item->linenumber = linenumber;
		$$ = item;
	}
	;

object: 
	OPENBRACE definition_list CLOSEBRACE 
	{
		$$ = $2;
	}
	;

array:
	OPENHARD member_list CLOSEHARD
	{
		$$ = $2;
	}
	;

member_list:
	member_list COMMA newline_opt not_expression newline_opt
	{
		struct arrayNode *node = (struct arrayNode *)malloc(sizeof(struct arrayNode));
		node->index = lastIndex++;
		node->data = $4;
		node->next = NULL;

		if(headArrayList == NULL)
		{
			headArrayList = node;
		}
		else
		{
			struct arrayNode *p = headArrayList;

			while(p && p->next != NULL)
			{
				p = p->next;
			}
			p->next = node;
		}

		$$ = headArrayList;
	}
	| newline_opt not_expression newline_opt
	{
		struct arrayNode *node = (struct arrayNode *)malloc(sizeof(struct arrayNode));
		node->index = lastIndex++;
		node->data = $2;
		node->next = NULL;

		if(headArrayList == NULL)
		{
			headArrayList = node;
		}
		else
		{
			struct arrayNode *p = headArrayList;

			while(p && p->next != NULL)
			{
				p = p->next;
			}
			p->next = node;
		}

		$$ = headArrayList;
	}
	| /* empty */
	{
		headArrayList = NULL;
		$$ = headArrayList;
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
	ID COLON not_expression
	{
		struct objectNode *field = (struct objectNode *)malloc(sizeof(struct objectNode));
		field->fieldName = strdup($1);
		field->data = $3;
		field->next = NULL;
		$$ = field;	
	}
	;

newline_opt:
	newline_opt NEWLINE { linenumber++;}
	| /*empty */ 
	;

arg_list:
	arg_list COMMA argument
	{
		if(printList == NULL)
		{	
			printList = $3;
		}
		else
		{	
			argumentList *p = printList;
			while(p && p->next != NULL)
				p = p->next;		
			
			p->next = $3;		
		}
		
		$$ = printList;		
	}
	| argument
	{
		if(printList == NULL)
		{	
			printList = $1;
		}
		else
		{	
			argumentList *p = printList;
			while(p && p->next != NULL)
				p = p->next;		
			
			p->next = $1;		
		}
		
		$$ = printList;	
	}
	| /*empty*/
	{
		printList = NULL;
		$$ = printList;	
	}
	;

argument:
	not_expression
	{
		argumentList *arg = (argumentList *)malloc(sizeof(argumentList));
		arg->tree = $1;
		arg->next = NULL;		
		$$ = arg;
	}
	;

parameters:
	parameters COMMA not_expression
	{
		argumentList *newArg = (argumentList *)malloc(sizeof(argumentList));
		newArg->tree = $3;
		newArg->next = NULL;

		argumentList *params = $1;
		while(params && params->next != NULL)
			params = params->next;

		params->next = newArg;
		$$ = $1;
	}
	| not_expression
	{
		argumentList *newArg = (argumentList *)malloc(sizeof(argumentList));
		newArg->tree = $1;
		newArg->next = NULL;
		$$ = newArg;
	}
	| /* empty */
	{
		$$ = NULL;
	}

not_expression:
	/*NOT not_expression 
	{ 
		$$ = makeNode(Not, NULL, NULL, $2, linenumber);
	}
	|*/ boolean
	{
		$$ = $1;
	}
	;

boolean:
	boolean ANDAND condition
	{
		$$ = makeNode(AndAnd, $1, $3, NULL, linenumber);
	}
	| boolean OROR condition
	{
		$$ = makeNode(OrOr, $1, $3, NULL, linenumber);
	}
	| condition
	{
		$$ = $1;
	}
	;
condition:
	condition LESS expression 
	{
		$$ = makeNode(Less, $1, $3, NULL, linenumber);
	}
	| condition LESSEQUAL expression
	{
		$$ = makeNode(LessEqual, $1, $3, NULL, linenumber);
	}
	| condition GREATER expression
	{
		$$ = makeNode(Greater, $1, $3, NULL, linenumber);
	}
	| condition GREATEREQUAL expression
	{
		$$ = makeNode(GreaterEqual, $1, $3, NULL, linenumber);
	}
	| condition EQUALEQUAL expression
	{
		$$ = makeNode(EqualEqual, $1, $3, NULL, linenumber);
	}
	| condition NOTEQUAL expression
	{
		$$ = makeNode(NotEqual, $1, $3, NULL, linenumber);
	}
	| expression
	{	
		$$ = $1;
	}
	;
expression:
	expression '+' factor
	{
		$$ = makeNode(Add, $1, $3, NULL, linenumber);
	}
	| expression '-' factor
	{

		$$ = makeNode(Subtract, $1, $3, NULL, linenumber);
	}
	| factor
	{
		$$ = $1;
	}
	;

factor:
	factor '*' token
	{
		ASTNode *treeNode = makeNode(Multiply, $1, $3, NULL, linenumber);
		$$ = treeNode;
	}
	| factor '/' token
	{

		$$ = makeNode(Divide, $1, $3, NULL, linenumber);
	}
	| token
	{
		$$ = $1;
	}
	;

token:
	ID
	{	
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup($1), linenumber);		
		$$ = treeNode;
	}
	| NUM
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = NUM_NODE;
		node->intValue = $1;
		
		ASTNode *treeNode = makeNode(Integer, NULL, NULL, node, linenumber);		
		
		$$ = treeNode;
	}
	| QUOTEDSTRING
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = STRING_NODE;
		node->strValue = strdup($1);
		
		ASTNode *treeNode = makeNode(String, NULL, NULL, node, linenumber);		
		$$ = treeNode;
	}
	| BREAKLINE
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = STRING_NODE;
		node->strValue = strdup($1);
		
		ASTNode *treeNode = makeNode(String, NULL, NULL, node, linenumber);		
		$$ = treeNode;
	}
	| OPENPAR not_expression CLOSEPAR
	{
		$$ = $2;
	}
	| ID DOT ID
	{
		char *var = (char *)malloc(strlen($1) + strlen($3) + 2);
		strcpy(var, $1);
		strcat(var, ".");
		strcat(var, $3);
		
		ASTNode *treeNode = makeNode(ObjectVariable, NULL, NULL, strdup(var), linenumber);		
		free(var);		
		$$ = treeNode;
	}
	| ID OPENHARD expression CLOSEHARD
	{	
		ASTNode *treeNode = makeNode(ArrayVariable, $3, NULL, strdup($1), linenumber);
		$$ = treeNode;
	}
	| TRUE
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = BOOLEAN_NODE;
		node->booleanValue = 1;
		
		$$ = makeNode(Boolean, NULL, NULL, node, linenumber);
	}
	| FALSE 
	{
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = BOOLEAN_NODE;
		node->booleanValue = 0;

		$$ = makeNode(Boolean, NULL, NULL, node, linenumber);
	}
	| ID OPENPAR parameters CLOSEPAR
	{
		ASTNode *functionName = makeNode(FunctionName, NULL, NULL, strdup($1), linenumber);
		$$ = makeNode(FunctionCall, functionName, NULL, $3, linenumber);
	}
	| NOT ID
	{
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup($2), linenumber);
		$$ = makeNode(Not, NULL, NULL, treeNode, linenumber);
	}
	| NOT OPENPAR not_expression CLOSEPAR
	{
		$$ = makeNode(Not, NULL, NULL, $3, linenumber);
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
		globalBucket = HashTableInit();
		setBucket(globalBucket);
		linenumber = 1;
		list = NULL;
		callstack = NULL;
		yyparse();
		
		struct statementList *curr = list;
		while(curr)
		{
			ASTNode *ret = walkAST(curr->root);
			if(ret && ret->type == Return)
				break;
			curr = curr->next;
		}
		
		fclose(file);
	}
	else
	{
		fprintf(stderr, "Format: ./parser <filename>\n");
	}
}
