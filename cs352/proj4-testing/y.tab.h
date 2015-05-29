/* A Bison parser, made by GNU Bison 2.4.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006,
   2009, 2010 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     ID = 258,
     QUOTEDSTRING = 259,
     BREAKLINE = 260,
     NUM = 261,
     VAR = 262,
     EQUAL = 263,
     SEMICOLON = 264,
     NEWLINE = 265,
     DOC = 266,
     WRITE = 267,
     OPENPAR = 268,
     CLOSEPAR = 269,
     COMMA = 270,
     DOT = 271,
     START = 272,
     END = 273,
     OPENBRACE = 274,
     CLOSEBRACE = 275,
     COLON = 276,
     IF = 277,
     ELSE = 278,
     FUNCTION = 279,
     OROR = 280,
     ANDAND = 281,
     NOT = 282,
     LESS = 283,
     LESSEQUAL = 284,
     GREATER = 285,
     GREATEREQUAL = 286,
     EQUALEQUAL = 287,
     NOTEQUAL = 288,
     TRUE = 289,
     FALSE = 290,
     WHILE = 291,
     BREAK = 292,
     CONTINUE = 293,
     DO = 294,
     OPENHARD = 295,
     CLOSEHARD = 296
   };
#endif
/* Tokens.  */
#define ID 258
#define QUOTEDSTRING 259
#define BREAKLINE 260
#define NUM 261
#define VAR 262
#define EQUAL 263
#define SEMICOLON 264
#define NEWLINE 265
#define DOC 266
#define WRITE 267
#define OPENPAR 268
#define CLOSEPAR 269
#define COMMA 270
#define DOT 271
#define START 272
#define END 273
#define OPENBRACE 274
#define CLOSEBRACE 275
#define COLON 276
#define IF 277
#define ELSE 278
#define FUNCTION 279
#define OROR 280
#define ANDAND 281
#define NOT 282
#define LESS 283
#define LESSEQUAL 284
#define GREATER 285
#define GREATEREQUAL 286
#define EQUALEQUAL 287
#define NOTEQUAL 288
#define TRUE 289
#define FALSE 290
#define WHILE 291
#define BREAK 292
#define CONTINUE 293
#define DO 294
#define OPENHARD 295
#define CLOSEHARD 296




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
{

/* Line 1685 of yacc.c  */
#line 30 "parser.y"

		char *string_val;
		int number_val;
		struct objectNode *object_val;
		struct ASTNode *tree_node_val;
		struct argumentList *print_val;
		struct arrayNode *array_val;
		struct statementList *statement_val;
	


/* Line 1685 of yacc.c  */
#line 145 "y.tab.h"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


