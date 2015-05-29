/*
 * CS250
 *
 * simple.l: simple parser for the simple "C" language
 *
 */

%token	<string_val> WORD

%token 	NOTOKEN LPARENT RPARENT LBRACE RBRACE LCURLY RCURLY COMA SEMICOLON EQUAL STRING_CONST LONG LONGSTAR VOID CHARSTAR CHARSTARSTAR INTEGER_CONST AMPERSAND OROR ANDAND EQUALEQUAL NOTEQUAL LESS GREAT LESSEQUAL GREATEQUAL PLUS MINUS TIMES DIVIDE PERCENT IF ELSE WHILE DO FOR CONTINUE BREAK RETURN

%union	{
		char   *string_val;
		int nargs;
		int my_nlabel;
	}

%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

extern int line_number;
const char * input_file;
char * asm_file;
FILE * fasm;

#define MAX_ARGS 5
int nargs;
char * args_table[MAX_ARGS];

#define MAX_GLOBALS 100
int nglobals = 0;
char * global_vars_table[MAX_GLOBALS];
int global_vars_type[MAX_GLOBALS];

#define MAX_STRINGS 100
int nstrings = 0;
char * string_table[MAX_STRINGS];

#define MAX_LOCALS 32
int nlocals = 0;
char * local_vars_table[MAX_LOCALS];
int local_vars_type[MAX_LOCALS];

char *regStk[]={ "rbx", "r10", "r13", "r14", "r15"};
char nregStk = sizeof(regStk)/sizeof(char*);
char *regStkByte[] = {"bl", "r10b", "r13b", "r14b", "r15b"};

char *regArgs[]={ "rdi", "rsi", "rdx", "rcx", "r8", "r9"};
char nregArgs = sizeof(regArgs)/sizeof(char*);


int top = 0;

int nargs =0;
 
int nlabel = 0;

int typeOfLoop = 0;

int ifCharStar = 0;
%}

%%

goal:	program
	;

program :
        function_or_var_list;

function_or_var_list:
        function_or_var_list function
        | function_or_var_list global_var
        | /*empty */
	;

function:
         var_type WORD
         {
		 fprintf(fasm, "\t.text\n");
		 fprintf(fasm, ".globl %s\n", $2);
		 fprintf(fasm, "%s:\n", $2);

		 fprintf(fasm, "# Save registers\n");
		 fprintf(fasm, "\tpushq %%rbx\n");
		 fprintf(fasm, "\tpushq %%r10\n");
		 fprintf(fasm, "\tpushq %%r13\n");
		 fprintf(fasm, "\tpushq %%r14\n");
		 fprintf(fasm, "\tpushq %%r15\n");
		 fprintf(fasm,"\t#Saving space for 32 local variables\n");
		 fprintf(fasm,"\tsubq\t$256, %%rsp\n");
	 	 nlocals = 0;
	 	 top = 0;
	 }
	 LPARENT arguments RPARENT
	 {
	 	int i;
		fprintf(fasm, "\t#Save Arguments\n");
		for(i=0; i<nlocals; i++)
		{
			fprintf(fasm, "\tmovq %%%s, %d(%%rsp)\n", regArgs[i], i*8);
		}
	 }
	 compound_statement
         {
		 fprintf(fasm, "\taddq\t$256, %%rsp\n");
		 fprintf(fasm, "# Restore registers\n");
		 fprintf(fasm, "\tpopq %%r15\n");
		 fprintf(fasm, "\tpopq %%r14\n");
		 fprintf(fasm, "\tpopq %%r13\n");
		 fprintf(fasm, "\tpopq %%r10\n");
		 fprintf(fasm, "\tpopq %%rbx\n");
		 fprintf(fasm, "\t#Restoring space for local variables\n");
		 fprintf(fasm, "\tret\n", $2);
	 }
	;

arg_list:
         arg
         | arg_list COMA arg
	 ;

arguments:
         arg_list
	 | /*empty*/
	 ;

arg: var_type WORD{
	//Initializing arguments of functions
	char *id = $2;
	fprintf(fasm,"\t#Adding argument %s to the stack\n",id);
	local_vars_table[nlocals] = id;
	local_vars_type[nlocals] = ifCharStar;
	nlocals++;
	//fprintf(fasm, "\tmovq\t%%%s,%d(%rsp)\n",regArgs[nargs],nargs*8);
	//nlocals++;
	//args++;
	

};

global_var: 
        var_type global_var_list SEMICOLON;

global_var_list: WORD {
        	//Initializing global variables
		char *id = $1;
		fprintf(fasm,"\t#Creating space for global variables\n");
		
		//if(nglobals==0)
		
		fprintf(fasm,"\t.data\n");
		fprintf(fasm,"%s:\n",id);
		fprintf(fasm,"\t.quad 0\n\n");
		global_vars_table[nglobals] = id;
		global_vars_type[nglobals] = ifCharStar;
		nglobals++;

	}
| global_var_list COMA WORD{
		char *id = $3;
		fprintf(fasm, "\t.data\n");
		fprintf(fasm,"%s:\n",id);
	        fprintf(fasm,"\t.quad 0\n\n");
	        global_vars_table[nglobals] = id;
		global_vars_type[nglobals] = ifCharStar;
	        nglobals++;
}

        ;

var_type: CHARSTAR{
	ifCharStar = 1;
}| CHARSTARSTAR{
	ifCharStar = 0;
}
| LONG{
	ifCharStar = 0;
}
| LONGSTAR{
	ifCharStar = 0;
}
| VOID{
	ifCharStar = 0;
};

assignment:
         WORD EQUAL expression{
		//Check if it is a local variable
		char *id = $1;
		int i;

		for(i=0; i < nlocals; i++)
		{
			if(!strcmp(id, local_vars_table[i]))
				break;
		}

		if(i < nlocals)
		{
			//It is a local variable
			
			int memPos = i*8;
			fprintf(fasm, "\t#Moving data into local variable\n");
			fprintf(fasm, "\tmovq\t%%%s, %d(%%rsp)\n", regStk[top-1], i*8);
			top--;
		}
		else
		{
			//It is a global variable
			char *id = $1;
			int j;
			for(j=0; i < nglobals; j++)
			{
				if(!strcmp(id,global_vars_table[j]))
					break;
			}		
		
			fprintf(fasm,"\t#Moving data into global variable\n");
			fprintf(fasm,"\tmovq\t%%%s, %s\n", regStk[top-1], global_vars_table[j]);
			top--;
		}
	}

	 | WORD LBRACE expression RBRACE EQUAL expression{
	 	//Check if it is a local variable
		char *id = $1;
		int i;

		for(i=0; i<nlocals; i++)
		{
			if(!strcmp(id, local_vars_table[i]))
				break;
		}
		if(i < nlocals)
		{
			//It is a local variable

			fprintf(fasm, "\tmovq %d(%%rsp), %%rax\n", i*8);
			if(local_vars_type[i])
				fprintf(fasm, "\timulq $1, %%%s\n", regStk[top-2]);
			else
				fprintf(fasm, "\timulq $8, %%%s\n", regStk[top-2]);
			
			fprintf(fasm, "\taddq %%%s, %%rax\n", regStk[top-2]);
			if(local_vars_type[i])
				fprintf(fasm, "\tmovb %%%s, (%%rax)\n", regStkByte[top-1]);
			else
				fprintf(fasm, "\tmovq %%%s, (%%rax)\n", regStk[top-1]);
			top=top-2;
		}
	 	else
		{
			//It is a global variable

			int j;
			for(j=0; j<nglobals; j++)
			{
				if(!strcmp(id, global_vars_table[j]))
					break;
			}
			
			fprintf(fasm, "\tmovq %s, %%rax\n", global_vars_table[j]);
			if(global_vars_type[j])
				fprintf(fasm, "\timulq $1, %%%s\n", regStk[top-2]);
			else
				fprintf(fasm, "\timulq $8, %%%s\n", regStk[top-2]);
			
			fprintf(fasm, "\taddq %%%s, %%rax\n", regStk[top-2]);
			if(global_vars_type[j])
				fprintf(fasm, "\tmovb %%%s, (%%rax)\n", regStkByte[top-1]);
			else
				fprintf(fasm, "\tmovq %%%s, (%%rax)\n", regStk[top-1]);
			top = top-2;
		}
	 }
	 ;
call :
         WORD LPARENT  call_arguments RPARENT {
		 char * funcName = $<string_val>1;
		 int nargs = $<nargs>3;
		 int i;
		 fprintf(fasm,"     # func=%s nargs=%d\n", funcName, nargs);
     		 fprintf(fasm,"     # Move values from reg stack to reg args\n");
		 for (i=nargs-1; i>=0; i--) {
			top--;
			fprintf(fasm, "\tmovq %%%s, %%%s\n",
			  regStk[top], regArgs[i]);
		 }
		 if (!strcmp(funcName, "printf")) {
			 // printf has a variable number of arguments
			 // and it need the following
			 fprintf(fasm, "\tmovl    $0, %%eax\n");
		 }
		 fprintf(fasm, "\tcall %s\n", funcName);
		 fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top]);
		 top++;
	 }
      ;

call_arg_list:
         expression {
		$<nargs>$=1;
	 }
         | call_arg_list COMA expression {
		$<nargs>$++;
	 }

	 ;

call_arguments:
         call_arg_list { $<nargs>$=$<nargs>1; }
	 | /*empty*/ { $<nargs>$=0;}
	 ;

expression :
         logical_or_expr
	 ;

logical_or_expr:
         logical_and_expr
	 | logical_or_expr OROR logical_and_expr{
	 	fprintf(fasm, "\n\t#check ||\n");
		if(top<=nregStk)
		{
			fprintf(fasm, "\torq %%%s, %%%s\n", regStk[top-1], regStk[top-2]);
			top--;
		}
	 }
	 ;

logical_and_expr:
         equality_expr
	 | logical_and_expr ANDAND equality_expr{
	 	fprintf(fasm, "\n\t#check &&\n");
		if(top<=nregStk)
		{
			fprintf(fasm, "\tandq %%%s, %%%s\n", regStk[top-1], regStk[top-2]);
			top--;
		}
	 }
	 ;

equality_expr:
         relational_expr
	 | equality_expr EQUALEQUAL relational_expr{
	 	fprintf(fasm, "\n\t#check if equal to\n");
		if(top<=nregStk)
		{
			fprintf(fasm, "\txorq %%rdx, %%rdx\n");
			fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
			fprintf(fasm, "\tmovq $1, %%rdx\n");
			fprintf(fasm, "\tmovq $0, %%%s\n", regStk[top-2]);
			fprintf(fasm, "\tcmove %%rdx, %%%s\n", regStk[top-2]);
			top--;
	 	}
	 }
	 | equality_expr NOTEQUAL relational_expr{
	 	fprintf(fasm, "\n\t#check if not equal to\n");
		if(top<=nregStk)
		{
			fprintf(fasm, "\txorq %%rdx, %%rdx\n");
			fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
			fprintf(fasm, "\tmovq $1, %%rdx\n");
			fprintf(fasm, "\tmovq $0, %%%s\n", regStk[top-2]);
			fprintf(fasm, "\tcmovne %%rdx, %%%s\n", regStk[top-2]);
			top--;
		}
	 }
	 ;

relational_expr:
         additive_expr
	 | relational_expr LESS additive_expr{
	 	//Check if reg1<reg2
		fprintf(fasm, "\n\t#Check if it is less than\n");
		if(top<=nregStk)
		{
			fprintf(fasm, "\txorq %%rdx, %%rdx\n");
			fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
			fprintf(fasm, "\tmovq $0, %%rdx\n");
			fprintf(fasm, "\tmovq $1, %%%s\n", regStk[top-2]);
			fprintf(fasm, "\tcmovle %%rdx, %%%s\n", regStk[top-2]);
			top--;
		}
	 }
	 | relational_expr GREAT additive_expr{
	 	fprintf(fasm,"\n\t#Check if it is greater than\n");
		if(top<=nregStk)
		{
			fprintf(fasm, "\txorq %%rdx, %%rdx\n");
			fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
			fprintf(fasm, "\tmovq $0, %%rdx\n");
			fprintf(fasm, "\tmovq $1, %%%s\n", regStk[top-2]);
			fprintf(fasm, "\tcmovge %%rdx, %%%s\n", regStk[top-2]);
			top--;
	 	}
	 }
	 | relational_expr LESSEQUAL additive_expr{
	 	fprintf(fasm, "\n\t#check if it is less than equal to\n");
		if(top<=nregStk)
		{
			fprintf(fasm, "\txorq %%rdx, %%rdx\n");
			fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
			fprintf(fasm, "\tmovq $1, %%rdx\n");
			fprintf(fasm, "\tmovq $0, %%%s\n", regStk[top-2]);
			fprintf(fasm, "\tcmovge %%rdx, %%%s\n",regStk[top-2]);
			top--;
		}
	 }
	 | relational_expr GREATEQUAL additive_expr{
	 	fprintf(fasm, "\n\t#check if it is greater than equal to\n");
		if(top<=nregStk)
		{
			fprintf(fasm, "\txorq %%rdx, %%rdx\n");
			fprintf(fasm, "\tcmpq %%%s, %%%s\n", regStk[top-2], regStk[top-1]);
			fprintf(fasm, "\tmovq $1, %%rdx\n");
			fprintf(fasm, "\tmovq $0, %%%s\n", regStk[top-2]);
			fprintf(fasm, "\tcmovle %%rdx, %%%s\n", regStk[top-2]);
			top--;
	 	}
	 }
	 ;

additive_expr:
          multiplicative_expr
	  | additive_expr PLUS multiplicative_expr {
		fprintf(fasm,"\n\t# +\n");
		if (top<=nregStk) {
			fprintf(fasm, "\taddq %%%s,%%%s\n", 
				regStk[top-1], regStk[top-2]);
			top--;
		}
	  }
	  | additive_expr MINUS multiplicative_expr{
	  	fprintf(fasm,"\n\t# -\n");
		if(top<=nregStk)
		{
			fprintf(fasm, "\tsubq %%%s, %%%s\n", regStk[top-1], regStk[top-2]);
			top--;
		}
	  }
	  ;

multiplicative_expr:
          primary_expr
	  | multiplicative_expr TIMES primary_expr {
		fprintf(fasm,"\n\t# *\n");
		if (top<=nregStk) {
			fprintf(fasm, "\timulq %%%s,%%%s\n", 
				regStk[top-1], regStk[top-2]);
			top--;
		}
          }
	  | multiplicative_expr DIVIDE primary_expr{
	  	fprintf(fasm,"\n\t# /\n");
		if(top <= nregStk)
		{
			//fprintf(fasm, "\tidivq %%%s, %%%s\n", regStk[top-1], regStk[top-2]);
			fprintf(fasm, "\txorq %%rdx, %%rdx\n");
			fprintf(fasm, "\tmovq %%%s, %%rax\n", regStk[top-2]);
			fprintf(fasm, "\tidivq %%%s\n", regStk[top-1]);
			fprintf(fasm, "\tmovq %%rax, %%%s\n", regStk[top-2]);
			top--;
		}
	  }
	  | multiplicative_expr PERCENT primary_expr{
	  	fprintf(fasm, "\n\t#	%%\n");
		if(top <= nregStk)
		{
			fprintf(fasm,"\txorq %%rdx, %%rdx\n");
			fprintf(fasm, "\tmovq %%%s, %%rax\n", regStk[top-2]);
			fprintf(fasm, "\tidivq %%%s\n", regStk[top-1]);
			fprintf(fasm, "\tmovq %%rdx, %%%s\n", regStk[top-2]);
			top--;
		}
	  }
	  ;

primary_expr:
	  STRING_CONST {
		  // Add string to string table.
		  // String table will be produced later
		  string_table[nstrings]=$<string_val>1;
		  fprintf(fasm, "\t#top=%d\n", top);
		  fprintf(fasm, "\n\t# push string %s top=%d\n",
			  $<string_val>1, top);
		  if (top<nregStk) {
		  	fprintf(fasm, "\tmovq $string%d, %%%s\n", 
				nstrings, regStk[top]);
			//fprintf(fasm, "\tmovq $%s,%%%s\n", 
			//	$<string_val>1, regStk[top]);
			top++;
		  }
		  nstrings++;
	  }
          | call 
	  | WORD {
		  // Assume it is a global variable
		  // TODO: Implement also local variables
		  char * id = $<string_val>1;
		  int i;

		  for(i=0; i < nlocals; i++)
		  {
		  	if(!strcmp(id, local_vars_table[i]))
				break;
		  }

		  if(i < nlocals)
		  {
		  	//It is a local variable
		  	int memPos = i*8;
			fprintf(fasm, "\tmovq\t%d(%%rsp),%%%s\n", memPos, regStk[top]);
		  }
		  else
		  {
		 	fprintf(fasm, "\tmovq %s,%%%s\n", id, regStk[top]);
		  }
		  top++;  
	  }
	  | WORD LBRACE expression RBRACE{
	  	char *id = $<string_val>1;
		int i;

		for(i=0; i<nlocals; i++)
		{
			if(!strcmp(id, local_vars_table[i]))
				break;
		}

		if(i<nlocals)
		{
			//It is a local variable
			fprintf(fasm, "\tmovq %d(%%rsp), %%rax\n", i*8);
			
			if(local_vars_type[i])
				fprintf(fasm, "\timulq $1, %%%s\n", regStk[top-1]);
			else
				fprintf(fasm, "\timulq $8, %%%s\n", regStk[top-1]);

			fprintf(fasm, "\taddq %%%s, %%rax\n", regStk[top-1]);
			if(local_vars_type[i])
				fprintf(fasm, "\tmovb (%%rax), %%%s\n", regStkByte[top-1]);
			else
				fprintf(fasm, "\tmovq (%%rax), %%%s\n", regStk[top-1]);
		}
		else
		{
			//It is a global variable
			int j;
			for(j=0; j<nglobals; j++)
			{
				if(!strcmp(id, global_vars_table[j]))
					break;
			}
			
			fprintf(fasm, "\tmovq %s, %%rax\n", id);
			if(global_vars_type[j])
				fprintf(fasm, "\timulq $1, %%%s\n", regStk[top-1]);
			else
				fprintf(fasm, "\timulq $8, %%%s\n", regStk[top-1]);
			fprintf(fasm, "\taddq %%%s, %%rax\n", regStk[top-1]);
			if(global_vars_type[j])
				fprintf(fasm, "\tmovb (%%rax), %%%s\n", regStkByte[top-1]);
			else
				fprintf(fasm, "\tmovq (%%rax), %%%s\n", regStk[top-1]);
		}
	  }
	  | AMPERSAND WORD{
	  	char *id = $<string_val>2;
		int i;

		for(i=0; i<nlocals; i++)
		{
			if(!strcmp(id, local_vars_table[i]))
				break;
		}
		
		if(i<nlocals)
		{
			//It is a local variable
			fprintf(fasm, "\tmovq %%rsp, %%%s\n",regStk[top]);
			fprintf(fasm, "\taddq $%d, %%%s\n", i*8, regStk[top]);
		}
		else
		{
			//It is a global variable
			fprintf(fasm, "\tmovq $%%%s, %%%\n", $<string_val>2, regStk[top]);
		}
	  	top++;
	  }
	  | INTEGER_CONST {
		  fprintf(fasm, "\n\t# push %s\n", $<string_val>1);
		  if (top<nregStk) {
			fprintf(fasm, "\tmovq $%s,%%%s\n", 
				$<string_val>1, regStk[top]);
			top++;
		  }
	  }
	  | LPARENT expression RPARENT
	  ;

compound_statement:
	 LCURLY statement_list RCURLY
	 ;

statement_list:
         statement_list statement
	 | /*empty*/
	 ;

local_var:
        var_type local_var_list SEMICOLON;

local_var_list: WORD{
	char *id = $1;
	fprintf(fasm, "\t#Creating space for local variable %s\n",id);
	local_vars_table[nlocals] = id;
	local_vars_type[nlocals] = ifCharStar;
	//int memPos = nlocals*8;
	nlocals++;
}
        | local_var_list COMA WORD{
		//Edited for nested function
		local_vars_table[nlocals] = $3;
		local_vars_type[nlocals] = ifCharStar;
		nlocals++;
	}
        ;

statement:
         assignment SEMICOLON
	 | call SEMICOLON { top=0; }
	 | local_var
	 | compound_statement
	 | IF LPARENT expression RPARENT{
	 	
		fprintf(fasm, "\tcmpq $0, %%%s\n", regStk[top-1]);
		$<my_nlabel>1=nlabel;
		fprintf(fasm, "\tje else_%d\n",$<my_nlabel>1); 
	 }
	 statement{
	 	fprintf(fasm, "\tjmp endif_%d\n", $<my_nlabel>1);
	 	fprintf(fasm, "else_%d:\n", $<my_nlabel>1);
	 }
	 else_optional{
	 	fprintf(fasm, "endif_%d:\n", $<my_nlabel>1); 
	 	nlabel++;
	 }
	 | WHILE LPARENT {
		// act 1
		$<my_nlabel>1=nlabel;
		nlabel++;
		fprintf(fasm, "start_loop_%d:\n", $<my_nlabel>1);
         	typeOfLoop = 1;
	 }
         expression RPARENT {
		// act2
		fprintf(fasm, "\tcmpq $0, %%rbx\n");
		fprintf(fasm, "\tje end_loop_%d\n", $<my_nlabel>1);
		top--;
         }
         statement {
		// act3
		fprintf(fasm, "\tjmp start_loop_%d\n", $<my_nlabel>1);
		fprintf(fasm, "end_loop_%d:\n", $<my_nlabel>1);
	 }
	 | DO{
	 	$<my_nlabel>1=nlabel;
		nlabel++;
	 	fprintf(fasm, "start_loop_%d:\n", $<my_nlabel>1);
	 }statement WHILE LPARENT expression RPARENT{
	 	fprintf(fasm, "\tcmpq $0, %%%s\n", regStk[top-1]);
		fprintf(fasm, "\tje end_loop_%d\n", $<my_nlabel>1);
	 	fprintf(fasm, "\tjmp start_loop_%d\n", $<my_nlabel>1);
	 }
	 SEMICOLON
	 {
	 	fprintf(fasm, "end_loop_%d:\n", $<my_nlabel>1);
	 }
	 | FOR LPARENT{
	 	$<my_nlabel>1=nlabel;
		nlabel++;
		typeOfLoop = 0;
	 }assignment SEMICOLON{
	 	fprintf(fasm, "start_loop_%d:\n", $<my_nlabel>1);
	 }expression{
		fprintf(fasm, "\tcmpq $0, %%%s\n", regStk[top-1]);
		fprintf(fasm, "\tje end_loop_%d\n", $<my_nlabel>1);
		fprintf(fasm, "\tjmp for_statement_%d\n", $<my_nlabel>1);
		fprintf(fasm, "for_increment_%d:\n", $<my_nlabel>1);
	}SEMICOLON assignment RPARENT{
		top=0;
		fprintf(fasm, "\tjmp start_loop_%d\n", $<my_nlabel>1);
		fprintf(fasm, "for_statement_%d:\n", $<my_nlabel>1);
	}statement{
		fprintf(fasm, "\tjmp for_increment_%d\n", $<my_nlabel>1);
		fprintf(fasm, "end_loop_%d:\n", $<my_nlabel>1);
		top=0;
	}
	 | jump_statement
	 ;

else_optional:
         ELSE{
		$<my_nlabel>1 = nlabel;
	 }
	 statement{
	 	fprintf(fasm, "\tjmp endif_%d\n", $<my_nlabel>1);
	 }
	 | /* empty */
         ;

jump_statement:
         CONTINUE SEMICOLON{
	 	fprintf(fasm, "\t#continue statement %s\n", $<string_val>1);
		$<my_nlabel>1 = nlabel;
		if(typeOfLoop)
			fprintf(fasm, "\tjmp start_loop_%d\n", $<my_nlabel>1-1);
	 	else
			fprintf(fasm, "\tjmp for_increment_%d\n", $<my_nlabel>1-1);
	 }
	 | BREAK SEMICOLON{
	 	fprintf(fasm, "\t#break statement\n");
	 	$<my_nlabel>1 = nlabel;
		fprintf(fasm, "\tjmp end_loop_%d\n", $<my_nlabel>1-1);
	 }
	 | RETURN expression SEMICOLON {
		 fprintf(fasm, "\tmovq %%rbx, %%rax\n");
		 top = 0;
	 	 fprintf(fasm, "\taddq $256, %%rsp\n");
		 fprintf(fasm, "\tpopq %%r15\n");
		 fprintf(fasm, "\tpopq %%r14\n");
		 fprintf(fasm, "\tpopq %%r13\n");
		 fprintf(fasm, "\tpopq %%r10\n");
		 fprintf(fasm, "\tpopq %%rbx\n");
	 	 fprintf(fasm, "\tret\n");
	 }
	 ;

%%

void yyset_in (FILE *  in_str );

int
yyerror(const char * s)
{
	fprintf(stderr,"%s:%d: %s\n", input_file, line_number, s);
}


int
main(int argc, char **argv)
{
	printf("-------------WARNING: You need to implement global and local vars ------\n");
	printf("------------- or you may get problems with top------\n");
	
	// Make sure there are enough arguments
	if (argc <2) {
		fprintf(stderr, "Usage: simple file\n");
		exit(1);
	}

	// Get file name
	input_file = strdup(argv[1]);

	int len = strlen(input_file);
	if (len < 2 || input_file[len-2]!='.' || input_file[len-1]!='c') {
		fprintf(stderr, "Error: file extension is not .c\n");
		exit(1);
	}

	// Get assembly file name
	asm_file = strdup(input_file);
	asm_file[len-1]='s';

	// Open file to compile
	FILE * f = fopen(input_file, "r");
	if (f==NULL) {
		fprintf(stderr, "Cannot open file %s\n", input_file);
		perror("fopen");
		exit(1);
	}

	// Create assembly file
	fasm = fopen(asm_file, "w");
	if (fasm==NULL) {
		fprintf(stderr, "Cannot open file %s\n", asm_file);
		perror("fopen");
		exit(1);
	}

	// Uncomment for debugging
	//fasm = stderr;

	// Create compilation file
	// 
	yyset_in(f);
	yyparse();

	// Generate string table
	int i;
	for (i = 0; i<nstrings; i++) {
		fprintf(fasm, "string%d:\n", i);
		fprintf(fasm, "\t.string %s\n\n", string_table[i]);
	}

	fclose(f);
	fclose(fasm);

	return 0;
}

