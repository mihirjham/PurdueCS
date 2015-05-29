/* A Bison parser, made by GNU Bison 2.4.3.  */

/* Skeleton implementation for Bison's Yacc-like parsers in C
   
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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.4.3"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1

/* Using locations.  */
#define YYLSP_NEEDED 0



/* Copy the first part of user declarations.  */

/* Line 189 of yacc.c  */
#line 1 "parser.y"

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


/* Line 189 of yacc.c  */
#line 91 "y.tab.c"

/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 1
#endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Enabling the token table.  */
#ifndef YYTOKEN_TABLE
# define YYTOKEN_TABLE 0
#endif


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

/* Line 214 of yacc.c  */
#line 30 "parser.y"

		char *string_val;
		int number_val;
		struct objectNode *object_val;
		struct ASTNode *tree_node_val;
		struct argumentList *print_val;
		struct arrayNode *array_val;
		struct statementList *statement_val;
	


/* Line 214 of yacc.c  */
#line 221 "y.tab.c"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif


/* Copy the second part of user declarations.  */


/* Line 264 of yacc.c  */
#line 233 "y.tab.c"

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#elif (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
typedef signed char yytype_int8;
#else
typedef short int yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(msgid) dgettext ("bison-runtime", msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(msgid) msgid
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(e) ((void) (e))
#else
# define YYUSE(e) /* empty */
#endif

/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(n) (n)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int yyi)
#else
static int
YYID (yyi)
    int yyi;
#endif
{
  return yyi;
}
#endif

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#     ifndef _STDLIB_H
#      define _STDLIB_H 1
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (YYID (0))
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined _STDLIB_H \
       && ! ((defined YYMALLOC || defined malloc) \
	     && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef _STDLIB_H
#    define _STDLIB_H 1
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
	 || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

/* Copy COUNT objects from FROM to TO.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(To, From, Count) \
      __builtin_memcpy (To, From, (Count) * sizeof (*(From)))
#  else
#   define YYCOPY(To, From, Count)		\
      do					\
	{					\
	  YYSIZE_T yyi;				\
	  for (yyi = 0; yyi < (Count); yyi++)	\
	    (To)[yyi] = (From)[yyi];		\
	}					\
      while (YYID (0))
#  endif
# endif

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)				\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack_alloc, Stack, yysize);			\
	Stack = &yyptr->Stack_alloc;					\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (YYID (0))

#endif

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  18
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   225

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  46
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  34
/* YYNRULES -- Number of rules.  */
#define YYNRULES  84
/* YYNRULES -- Number of states.  */
#define YYNSTATES  182

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   296

#define YYTRANSLATE(YYX)						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,    44,    42,     2,    43,     2,    45,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const yytype_uint16 yyprhs[] =
{
       0,     0,     3,     8,    11,    13,    21,    24,    27,    29,
      31,    33,    36,    39,    42,    47,    52,    57,    61,    67,
      74,    75,    86,    87,    97,   100,   101,   109,   110,   117,
     118,   119,   130,   131,   141,   142,   143,   157,   158,   159,
     172,   176,   180,   186,   190,   191,   197,   201,   202,   206,
     209,   210,   214,   216,   217,   219,   221,   225,   229,   231,
     235,   239,   243,   247,   251,   255,   257,   261,   265,   267,
     271,   275,   277,   279,   281,   283,   285,   289,   293,   298,
     300,   302,   305,   310,   312
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const yytype_int8 yyrhs[] =
{
      47,     0,    -1,    17,    48,    18,    10,    -1,    48,    49,
      -1,    49,    -1,    11,    16,    12,    13,    71,    14,    79,
      -1,    50,    79,    -1,    51,    79,    -1,    52,    -1,    58,
      -1,    10,    -1,    37,    79,    -1,    38,    79,    -1,     7,
       3,    -1,     7,     3,     8,    73,    -1,     7,     3,     8,
      65,    -1,     7,     3,     8,    66,    -1,     3,     8,    73,
      -1,     3,    16,     3,     8,    73,    -1,     3,    40,    73,
      41,     8,    73,    -1,    -1,    22,    13,    73,    14,    19,
      10,    53,    48,    20,    55,    -1,    -1,    22,    13,    73,
      14,    19,    10,    54,    20,    55,    -1,    23,    52,    -1,
      -1,    23,    19,    10,    56,    48,    20,    10,    -1,    -1,
      23,    19,    10,    57,    20,    10,    -1,    -1,    -1,    36,
      13,    73,    14,    19,    10,    59,    48,    20,    10,    -1,
      -1,    36,    13,    73,    14,    19,    10,    60,    20,    10,
      -1,    -1,    -1,    39,    19,    10,    61,    48,    20,    10,
      62,    36,    13,    73,    14,    79,    -1,    -1,    -1,    39,
      19,    10,    63,    20,    10,    64,    36,    13,    73,    14,
      79,    -1,    19,    68,    20,    -1,    40,    67,    41,    -1,
      67,    15,    70,    73,    70,    -1,    70,    73,    70,    -1,
      -1,    68,    15,    70,    69,    70,    -1,    70,    69,    70,
      -1,    -1,     3,    21,    73,    -1,    70,    10,    -1,    -1,
      71,    15,    72,    -1,    72,    -1,    -1,    73,    -1,    74,
      -1,    74,    26,    75,    -1,    74,    25,    75,    -1,    75,
      -1,    75,    28,    76,    -1,    75,    29,    76,    -1,    75,
      30,    76,    -1,    75,    31,    76,    -1,    75,    32,    76,
      -1,    75,    33,    76,    -1,    76,    -1,    76,    42,    77,
      -1,    76,    43,    77,    -1,    77,    -1,    77,    44,    78,
      -1,    77,    45,    78,    -1,    78,    -1,     3,    -1,     6,
      -1,     4,    -1,     5,    -1,    13,    73,    14,    -1,     3,
      16,     3,    -1,     3,    40,    76,    41,    -1,    34,    -1,
      35,    -1,    27,     3,    -1,    27,    13,    73,    14,    -1,
      10,    -1,     9,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,    43,    43,    56,    66,    73,    99,   125,   151,   172,
     180,   189,   202,   218,   224,   230,   237,   248,   253,   264,
     272,   272,   285,   285,   291,   295,   295,   308,   308,   314,
     320,   320,   333,   333,   338,   338,   338,   355,   355,   355,
     368,   375,   382,   406,   431,   438,   455,   473,   480,   491,
     492,   496,   513,   531,   538,   552,   559,   563,   567,   573,
     577,   581,   585,   589,   593,   597,   603,   607,   612,   619,
     624,   629,   636,   641,   651,   660,   669,   673,   684,   689,
     697,   705,   710,   717,   722
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || YYTOKEN_TABLE
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "ID", "QUOTEDSTRING", "BREAKLINE", "NUM",
  "VAR", "EQUAL", "SEMICOLON", "NEWLINE", "DOC", "WRITE", "OPENPAR",
  "CLOSEPAR", "COMMA", "DOT", "START", "END", "OPENBRACE", "CLOSEBRACE",
  "COLON", "IF", "ELSE", "FUNCTION", "OROR", "ANDAND", "NOT", "LESS",
  "LESSEQUAL", "GREATER", "GREATEREQUAL", "EQUALEQUAL", "NOTEQUAL", "TRUE",
  "FALSE", "WHILE", "BREAK", "CONTINUE", "DO", "OPENHARD", "CLOSEHARD",
  "'+'", "'-'", "'*'", "'/'", "$accept", "goal", "statements", "statement",
  "declaration", "assignment", "if_expression", "$@1", "$@2", "else_opt",
  "$@3", "$@4", "loop", "$@5", "$@6", "$@7", "$@8", "$@9", "$@10",
  "object", "array", "member_list", "definition_list", "definition",
  "newline_opt", "arg_list", "argument", "not_expression", "boolean",
  "condition", "expression", "factor", "token", "separator", 0
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,    43,    45,    42,    47
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    46,    47,    48,    48,    49,    49,    49,    49,    49,
      49,    49,    49,    50,    50,    50,    50,    51,    51,    51,
      53,    52,    54,    52,    55,    56,    55,    57,    55,    55,
      59,    58,    60,    58,    61,    62,    58,    63,    64,    58,
      65,    66,    67,    67,    67,    68,    68,    68,    69,    70,
      70,    71,    71,    71,    72,    73,    74,    74,    74,    75,
      75,    75,    75,    75,    75,    75,    76,    76,    76,    77,
      77,    77,    78,    78,    78,    78,    78,    78,    78,    78,
      78,    78,    78,    79,    79
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     4,     2,     1,     7,     2,     2,     1,     1,
       1,     2,     2,     2,     4,     4,     4,     3,     5,     6,
       0,    10,     0,     9,     2,     0,     7,     0,     6,     0,
       0,    10,     0,     9,     0,     0,    13,     0,     0,    12,
       3,     3,     5,     3,     0,     5,     3,     0,     3,     2,
       0,     3,     1,     0,     1,     1,     3,     3,     1,     3,
       3,     3,     3,     3,     3,     1,     3,     3,     1,     3,
       3,     1,     1,     1,     1,     1,     3,     3,     4,     1,
       1,     2,     4,     1,     1
};

/* YYDEFACT[STATE-NAME] -- Default rule to reduce with in state
   STATE-NUM when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       0,     0,     0,     0,     0,    10,     0,     0,     0,     0,
       0,     0,     0,     4,     0,     0,     8,     9,     1,     0,
       0,     0,    13,     0,     0,     0,    84,    83,    11,    12,
       0,     0,     3,     6,     7,    72,    74,    75,    73,     0,
       0,    79,    80,    17,    55,    58,    65,    68,    71,     0,
       0,     0,     0,     0,     0,    34,     2,     0,     0,     0,
      81,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    47,    50,    15,    16,
      14,    53,     0,     0,     0,     0,    77,     0,    76,     0,
      57,    56,    59,    60,    61,    62,    63,    64,    66,    67,
      69,    70,    18,     0,     0,     0,     0,     0,     0,    52,
      54,     0,     0,     0,     0,    78,    82,    19,    50,    40,
       0,    49,    50,    50,    41,    50,     0,     0,    20,    30,
       0,    38,     0,     0,    46,     0,    43,     5,    51,     0,
       0,     0,     0,    35,     0,    50,    48,    50,     0,    29,
       0,     0,     0,     0,    45,    42,    29,     0,    23,     0,
      33,     0,     0,    21,     0,    24,    31,     0,     0,    25,
       0,     0,     0,     0,     0,    39,     0,     0,    36,     0,
      28,    26
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     2,    12,    13,    14,    15,    16,   139,   140,   158,
     172,   173,    17,   141,   142,    84,   152,    85,   144,    78,
      79,   106,   104,   122,   105,   108,   109,   110,    44,    45,
      46,    47,    48,    28
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -82
static const yytype_int16 yypact[] =
{
      19,   160,    32,    -6,    42,   -82,    33,    46,    53,    59,
      59,    45,    60,   -82,    59,    59,   -82,   -82,   -82,    38,
      78,    38,    79,    50,    38,    38,   -82,   -82,   -82,   -82,
      82,    93,   -82,   -82,   -82,    -3,   -82,   -82,   -82,    38,
      16,   -82,   -82,   -82,    49,   188,    43,    44,   -82,   107,
      77,    12,    88,   112,   118,   101,   -82,   135,    38,   127,
     -82,    38,    38,    38,    38,    38,    38,    38,    38,    38,
      38,    38,    38,    38,    38,   137,    17,    -8,   -82,   -82,
     -82,    38,   125,   129,   160,   126,   -82,   -19,   -82,   138,
     188,   188,    43,    43,    43,    43,    43,    43,    44,    44,
     -82,   -82,   -82,    38,    15,    18,    -1,   180,    91,   -82,
     -82,   143,   145,    73,   156,   -82,   -82,   -82,   -82,   -82,
     147,   -82,   -82,   -82,   -82,   -82,    59,    38,   149,   152,
     163,   -82,    18,    38,   164,   180,   164,   -82,   -82,   160,
     155,   160,   161,   -82,   144,   -82,   -82,   -82,    97,   165,
     120,   177,   153,   178,   164,   164,   165,    34,   -82,   182,
     -82,   193,    38,   -82,   185,   -82,   -82,    38,   194,   189,
     196,    59,   160,   191,    59,   -82,   140,   202,   -82,   203,
     -82,   -82
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
     -82,   -82,   -81,   -11,   -82,   -82,    65,   -82,   -82,    67,
     -82,   -82,   -82,   -82,   -82,   -82,   -82,   -82,   -82,   -82,
     -82,   -82,   -82,    92,   -68,   -82,    98,   -13,   -82,    51,
     136,    54,    56,   -10
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If zero, do what YYDEFACT says.
   If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -51
static const yytype_int16 yytable[] =
{
      29,    32,    19,   113,    33,    34,    43,   -44,    50,   107,
      20,    53,    54,    57,   123,    35,    36,    37,    38,    60,
     -50,   120,   115,    70,    71,    39,    59,   -50,   121,    61,
     118,    76,    18,   -44,    21,   119,     1,    58,    80,    40,
     124,    35,    36,    37,    38,    22,    41,    42,    89,    23,
     132,    39,    77,   164,   134,   135,     7,   136,   148,    24,
     150,   102,    52,     3,    30,    40,    25,     4,    26,    27,
       5,     6,    41,    42,    62,    63,     3,   154,    31,   155,
       4,    49,     7,     5,     6,    70,    71,    51,    72,    73,
     117,   176,    55,   130,   125,     7,     8,     9,    10,    11,
       3,    81,    32,    56,     4,   126,   127,     5,     6,     8,
       9,    10,    11,    90,    91,    74,   137,   156,    75,     7,
     146,   -37,   147,     3,    98,    99,    82,     4,   100,   101,
       5,     6,    83,     8,     9,    10,    11,    32,    86,    32,
     159,    88,     7,     3,   111,   103,   114,     4,   112,   168,
       5,     6,   116,   128,   170,   129,     8,     9,    10,    11,
     179,   175,     7,     3,   178,    32,   131,     4,   133,   -22,
       5,     6,   -32,   143,   121,   149,     8,     9,    10,    11,
     153,   151,     7,    35,    36,    37,    38,   160,   157,   161,
     121,   162,   166,    39,    87,   169,     8,     9,    10,    11,
      92,    93,    94,    95,    96,    97,   167,    40,   171,   -27,
     174,   177,   180,   181,    41,    42,    64,    65,    66,    67,
      68,    69,   165,   163,   145,   138
};

static const yytype_uint8 yycheck[] =
{
      10,    12,     8,    84,    14,    15,    19,    15,    21,    77,
      16,    24,    25,    16,    15,     3,     4,     5,     6,     3,
       3,     3,    41,    42,    43,    13,    39,    10,    10,    13,
      15,    19,     0,    41,    40,    20,    17,    40,    51,    27,
      41,     3,     4,     5,     6,     3,    34,    35,    61,    16,
     118,    13,    40,    19,   122,   123,    22,   125,   139,    13,
     141,    74,    12,     3,    19,    27,    13,     7,     9,    10,
      10,    11,    34,    35,    25,    26,     3,   145,    18,   147,
       7,     3,    22,    10,    11,    42,    43,     8,    44,    45,
     103,   172,    10,    20,   107,    22,    36,    37,    38,    39,
       3,    13,   113,    10,     7,    14,    15,    10,    11,    36,
      37,    38,    39,    62,    63,     8,   126,    20,    41,    22,
     133,    20,   135,     3,    70,    71,    14,     7,    72,    73,
      10,    11,    14,    36,    37,    38,    39,   148,     3,   150,
      20,    14,    22,     3,    19,     8,    20,     7,    19,   162,
      10,    11,    14,    10,   167,    10,    36,    37,    38,    39,
      20,   171,    22,     3,   174,   176,    10,     7,    21,    20,
      10,    11,    20,    10,    10,    20,    36,    37,    38,    39,
      36,    20,    22,     3,     4,     5,     6,    10,    23,    36,
      10,    13,    10,    13,    58,    10,    36,    37,    38,    39,
      64,    65,    66,    67,    68,    69,    13,    27,    14,    20,
      14,    20,    10,    10,    34,    35,    28,    29,    30,    31,
      32,    33,   157,   156,   132,   127
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,    17,    47,     3,     7,    10,    11,    22,    36,    37,
      38,    39,    48,    49,    50,    51,    52,    58,     0,     8,
      16,    40,     3,    16,    13,    13,     9,    10,    79,    79,
      19,    18,    49,    79,    79,     3,     4,     5,     6,    13,
      27,    34,    35,    73,    74,    75,    76,    77,    78,     3,
      73,     8,    12,    73,    73,    10,    10,    16,    40,    73,
       3,    13,    25,    26,    28,    29,    30,    31,    32,    33,
      42,    43,    44,    45,     8,    41,    19,    40,    65,    66,
      73,    13,    14,    14,    61,    63,     3,    76,    14,    73,
      75,    75,    76,    76,    76,    76,    76,    76,    77,    77,
      78,    78,    73,     8,    68,    70,    67,    70,    71,    72,
      73,    19,    19,    48,    20,    41,    14,    73,    15,    20,
       3,    10,    69,    15,    41,    73,    14,    15,    10,    10,
      20,    10,    70,    21,    70,    70,    70,    79,    72,    53,
      54,    59,    60,    10,    64,    69,    73,    73,    48,    20,
      48,    20,    62,    36,    70,    70,    20,    23,    55,    20,
      10,    36,    13,    55,    19,    52,    10,    13,    73,    10,
      73,    14,    56,    57,    14,    79,    48,    20,    79,    20,
      10,    10
};

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrorlab


/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  However,
   YYFAIL appears to be in use.  Nevertheless, it is formally deprecated
   in Bison 2.4.2's NEWS entry, where a plan to phase it out is
   discussed.  */

#define YYFAIL		goto yyerrlab
#if defined YYFAIL
  /* This is here to suppress warnings from the GCC cpp's
     -Wunused-macros.  Normally we don't worry about that warning, but
     some users do, and we want to make it easy for users to remove
     YYFAIL uses, which will produce warnings from Bison 2.5.  */
#endif

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)					\
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    {								\
      yychar = (Token);						\
      yylval = (Value);						\
      yytoken = YYTRANSLATE (yychar);				\
      YYPOPSTACK (1);						\
      goto yybackup;						\
    }								\
  else								\
    {								\
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;							\
    }								\
while (YYID (0))


#define YYTERROR	1
#define YYERRCODE	256


/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#define YYRHSLOC(Rhs, K) ((Rhs)[K])
#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)				\
    do									\
      if (YYID (N))                                                    \
	{								\
	  (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;	\
	  (Current).first_column = YYRHSLOC (Rhs, 1).first_column;	\
	  (Current).last_line    = YYRHSLOC (Rhs, N).last_line;		\
	  (Current).last_column  = YYRHSLOC (Rhs, N).last_column;	\
	}								\
      else								\
	{								\
	  (Current).first_line   = (Current).last_line   =		\
	    YYRHSLOC (Rhs, 0).last_line;				\
	  (Current).first_column = (Current).last_column =		\
	    YYRHSLOC (Rhs, 0).last_column;				\
	}								\
    while (YYID (0))
#endif


/* YY_LOCATION_PRINT -- Print the location on the stream.
   This macro was not mandated originally: define only if we know
   we won't break user code: when these are the locations we know.  */

#ifndef YY_LOCATION_PRINT
# if defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL
#  define YY_LOCATION_PRINT(File, Loc)			\
     fprintf (File, "%d.%d-%d.%d",			\
	      (Loc).first_line, (Loc).first_column,	\
	      (Loc).last_line,  (Loc).last_column)
# else
#  define YY_LOCATION_PRINT(File, Loc) ((void) 0)
# endif
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */

#ifdef YYLEX_PARAM
# define YYLEX yylex (YYLEX_PARAM)
#else
# define YYLEX yylex ()
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (YYID (0))

# define YY_SYMBOL_PRINT(Title, Type, Value, Location)			  \
do {									  \
  if (yydebug)								  \
    {									  \
      YYFPRINTF (stderr, "%s ", Title);					  \
      yy_symbol_print (stderr,						  \
		  Type, Value); \
      YYFPRINTF (stderr, "\n");						  \
    }									  \
} while (YYID (0))


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_value_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# else
  YYUSE (yyoutput);
# endif
  switch (yytype)
    {
      default:
	break;
    }
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (yytype < YYNTOKENS)
    YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
#else
static void
yy_stack_print (yybottom, yytop)
    yytype_int16 *yybottom;
    yytype_int16 *yytop;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (YYID (0))


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_reduce_print (YYSTYPE *yyvsp, int yyrule)
#else
static void
yy_reduce_print (yyvsp, yyrule)
    YYSTYPE *yyvsp;
    int yyrule;
#endif
{
  int yynrhs = yyr2[yyrule];
  int yyi;
  unsigned long int yylno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
	     yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
		       &(yyvsp[(yyi + 1) - (yynrhs)])
		       		       );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (yyvsp, Rule); \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif



#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static YYSIZE_T
yystrlen (const char *yystr)
#else
static YYSIZE_T
yystrlen (yystr)
    const char *yystr;
#endif
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static char *
yystpcpy (char *yydest, const char *yysrc)
#else
static char *
yystpcpy (yydest, yysrc)
    char *yydest;
    const char *yysrc;
#endif
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
	switch (*++yyp)
	  {
	  case '\'':
	  case ',':
	    goto do_not_strip_quotes;

	  case '\\':
	    if (*++yyp != '\\')
	      goto do_not_strip_quotes;
	    /* Fall through.  */
	  default:
	    if (yyres)
	      yyres[yyn] = *yyp;
	    yyn++;
	    break;

	  case '"':
	    if (yyres)
	      yyres[yyn] = '\0';
	    return yyn;
	  }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into YYRESULT an error message about the unexpected token
   YYCHAR while in state YYSTATE.  Return the number of bytes copied,
   including the terminating null byte.  If YYRESULT is null, do not
   copy anything; just return the number of bytes that would be
   copied.  As a special case, return 0 if an ordinary "syntax error"
   message will do.  Return YYSIZE_MAXIMUM if overflow occurs during
   size calculation.  */
static YYSIZE_T
yysyntax_error (char *yyresult, int yystate, int yychar)
{
  int yyn = yypact[yystate];

  if (! (YYPACT_NINF < yyn && yyn <= YYLAST))
    return 0;
  else
    {
      int yytype = YYTRANSLATE (yychar);
      YYSIZE_T yysize0 = yytnamerr (0, yytname[yytype]);
      YYSIZE_T yysize = yysize0;
      YYSIZE_T yysize1;
      int yysize_overflow = 0;
      enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
      char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
      int yyx;

# if 0
      /* This is so xgettext sees the translatable formats that are
	 constructed on the fly.  */
      YY_("syntax error, unexpected %s");
      YY_("syntax error, unexpected %s, expecting %s");
      YY_("syntax error, unexpected %s, expecting %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s");
# endif
      char *yyfmt;
      char const *yyf;
      static char const yyunexpected[] = "syntax error, unexpected %s";
      static char const yyexpecting[] = ", expecting %s";
      static char const yyor[] = " or %s";
      char yyformat[sizeof yyunexpected
		    + sizeof yyexpecting - 1
		    + ((YYERROR_VERBOSE_ARGS_MAXIMUM - 2)
		       * (sizeof yyor - 1))];
      char const *yyprefix = yyexpecting;

      /* Start YYX at -YYN if negative to avoid negative indexes in
	 YYCHECK.  */
      int yyxbegin = yyn < 0 ? -yyn : 0;

      /* Stay within bounds of both yycheck and yytname.  */
      int yychecklim = YYLAST - yyn + 1;
      int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
      int yycount = 1;

      yyarg[0] = yytname[yytype];
      yyfmt = yystpcpy (yyformat, yyunexpected);

      for (yyx = yyxbegin; yyx < yyxend; ++yyx)
	if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR)
	  {
	    if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
	      {
		yycount = 1;
		yysize = yysize0;
		yyformat[sizeof yyunexpected - 1] = '\0';
		break;
	      }
	    yyarg[yycount++] = yytname[yyx];
	    yysize1 = yysize + yytnamerr (0, yytname[yyx]);
	    yysize_overflow |= (yysize1 < yysize);
	    yysize = yysize1;
	    yyfmt = yystpcpy (yyfmt, yyprefix);
	    yyprefix = yyor;
	  }

      yyf = YY_(yyformat);
      yysize1 = yysize + yystrlen (yyf);
      yysize_overflow |= (yysize1 < yysize);
      yysize = yysize1;

      if (yysize_overflow)
	return YYSIZE_MAXIMUM;

      if (yyresult)
	{
	  /* Avoid sprintf, as that infringes on the user's name space.
	     Don't have undefined behavior even if the translation
	     produced a string with the wrong number of "%s"s.  */
	  char *yyp = yyresult;
	  int yyi = 0;
	  while ((*yyp = *yyf) != '\0')
	    {
	      if (*yyp == '%' && yyf[1] == 's' && yyi < yycount)
		{
		  yyp += yytnamerr (yyp, yyarg[yyi++]);
		  yyf += 2;
		}
	      else
		{
		  yyp++;
		  yyf++;
		}
	    }
	}
      return yysize;
    }
}
#endif /* YYERROR_VERBOSE */


/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
#else
static void
yydestruct (yymsg, yytype, yyvaluep)
    const char *yymsg;
    int yytype;
    YYSTYPE *yyvaluep;
#endif
{
  YYUSE (yyvaluep);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  switch (yytype)
    {

      default:
	break;
    }
}

/* Prevent warnings from -Wmissing-prototypes.  */
#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */


/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;

/* Number of syntax errors so far.  */
int yynerrs;



/*-------------------------.
| yyparse or yypush_parse.  |
`-------------------------*/

#ifdef YYPARSE_PARAM
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void *YYPARSE_PARAM)
#else
int
yyparse (YYPARSE_PARAM)
    void *YYPARSE_PARAM;
#endif
#else /* ! YYPARSE_PARAM */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void)
#else
int
yyparse ()

#endif
#endif
{


    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       `yyss': related to states.
       `yyvs': related to semantic values.

       Refer to the stacks thru separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yytoken = 0;
  yyss = yyssa;
  yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */
  yyssp = yyss;
  yyvsp = yyvs;

  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack.  Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	yytype_int16 *yyss1 = yyss;

	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow (YY_("memory exhausted"),
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),
		    &yystacksize);

	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	yytype_int16 *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyexhaustedlab;
	YYSTACK_RELOCATE (yyss_alloc, yyss);
	YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yyn == YYPACT_NINF)
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yyn == 0 || yyn == YYTABLE_NINF)
	goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  *++yyvsp = yylval;

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:

/* Line 1464 of yacc.c  */
#line 44 "parser.y"
    {
		list = (yyvsp[(2) - (4)].statement_val);
		/*struct statementList *curr = list;
		while(curr)
		{
			fprintf(stderr, "Line=%d\tType=%d\n", curr->root->linenumber, curr->root->type);
			curr = curr->next;
		}*/
	}
    break;

  case 3:

/* Line 1464 of yacc.c  */
#line 57 "parser.y"
    {	
		struct statementList *curr = (yyvsp[(1) - (2)].statement_val);
		while(curr && curr->next != NULL)
		{	
			curr = curr->next;
		}
		curr->next = (yyvsp[(2) - (2)].statement_val);
		(yyval.statement_val) = (yyvsp[(1) - (2)].statement_val);
	}
    break;

  case 4:

/* Line 1464 of yacc.c  */
#line 67 "parser.y"
    {	
		(yyval.statement_val) = (yyvsp[(1) - (1)].statement_val);
	}
    break;

  case 5:

/* Line 1464 of yacc.c  */
#line 74 "parser.y"
    {
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)
			newItem->root = makeNode(Print, NULL, NULL, (yyvsp[(5) - (7)].print_val), linenumber-1);
		else
			newItem->root = makeNode(Print, NULL, NULL, (yyvsp[(5) - (7)].print_val), linenumber);
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
		
		(yyval.statement_val) = newItem;
	}
    break;

  case 6:

/* Line 1464 of yacc.c  */
#line 100 "parser.y"
    {	
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)
			newItem->linenumber = linenumber-1;
		else
			newItem->linenumber = linenumber;
		newItem->root = (yyvsp[(1) - (2)].tree_node_val);
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
		(yyval.statement_val) = newItem;
	}
    break;

  case 7:

/* Line 1464 of yacc.c  */
#line 126 "parser.y"
    {
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)		
			newItem->linenumber = linenumber-1;
		else
			newItem->linenumber = linenumber;
		
		newItem->root = (yyvsp[(1) - (2)].tree_node_val);
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
		(yyval.statement_val) = newItem;
	}
    break;

  case 8:

/* Line 1464 of yacc.c  */
#line 152 "parser.y"
    {
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		newItem->linenumber = linenumber;
		newItem->root = (yyvsp[(1) - (1)].tree_node_val);
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
		(yyval.statement_val) = newItem;
	}
    break;

  case 9:

/* Line 1464 of yacc.c  */
#line 173 "parser.y"
    {
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		newItem->root = (yyvsp[(1) - (1)].tree_node_val);
		newItem->next = NULL;
		newItem->errorReported = 0;
		(yyval.statement_val) = newItem;
	}
    break;

  case 10:

/* Line 1464 of yacc.c  */
#line 181 "parser.y"
    { 
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		newItem->root = makeNode(Newline, NULL, NULL, NULL, linenumber);
		newItem->next = NULL;
		linenumber++;  
		
		(yyval.statement_val) = newItem;
	}
    break;

  case 11:

/* Line 1464 of yacc.c  */
#line 190 "parser.y"
    {
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)
			newItem->root = makeNode(Break, NULL, NULL, NULL, linenumber-1);
		else
			newItem->root = makeNode(Break, NULL, NULL, NULL, linenumber);

		newItem->next = NULL;
		newItem->errorReported = 0;
		fromNL = 0;
		(yyval.statement_val) = newItem;
	}
    break;

  case 12:

/* Line 1464 of yacc.c  */
#line 203 "parser.y"
    {
		struct statementList *newItem = (struct statementList *)malloc(sizeof(struct statementList));
		if(fromNL)
			newItem->root = makeNode(Continue, NULL, NULL, NULL, linenumber-1);
		else
			newItem->root = makeNode(Continue, NULL, NULL, NULL, linenumber);
         	
		newItem->next = NULL;
		newItem->errorReported = 0;
		fromNL = 0;
		(yyval.statement_val) = newItem;
	}
    break;

  case 13:

/* Line 1464 of yacc.c  */
#line 219 "parser.y"
    {	
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup((yyvsp[(2) - (2)].string_val)), linenumber);

		(yyval.tree_node_val) = makeNode(Declare, treeNode, NULL, NULL, linenumber);
	}
    break;

  case 14:

/* Line 1464 of yacc.c  */
#line 225 "parser.y"
    {	
		ASTNode *tree1 = makeNode(Variable, NULL, NULL, strdup((yyvsp[(2) - (4)].string_val)), linenumber);		
		ASTNode *treeNode = makeNode(Declare, tree1, (yyvsp[(4) - (4)].tree_node_val), NULL, linenumber);
		(yyval.tree_node_val) = treeNode;
	}
    break;

  case 15:

/* Line 1464 of yacc.c  */
#line 231 "parser.y"
    {	
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup((yyvsp[(2) - (4)].string_val)), linenumber);
		ASTNode *fields = makeNode(Object, NULL, NULL, (yyvsp[(4) - (4)].object_val), linenumber);
		headObjectList = NULL;
		(yyval.tree_node_val) = makeNode(Declare, treeNode, fields, NULL, linenumber);
	}
    break;

  case 16:

/* Line 1464 of yacc.c  */
#line 238 "parser.y"
    {
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup((yyvsp[(2) - (4)].string_val)), linenumber);
		ASTNode *fields = makeNode(Array, NULL, NULL, (yyvsp[(4) - (4)].array_val), linenumber);
		headArrayList = NULL;
		lastIndex = 0;
		(yyval.tree_node_val) = makeNode(Declare, treeNode, fields, NULL, linenumber);
	}
    break;

  case 17:

/* Line 1464 of yacc.c  */
#line 249 "parser.y"
    {	
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup((yyvsp[(1) - (3)].string_val)), linenumber);
		(yyval.tree_node_val) = makeNode(Assign, treeNode, (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 18:

/* Line 1464 of yacc.c  */
#line 254 "parser.y"
    {
		char *str = (char *)malloc(strlen((yyvsp[(1) - (5)].string_val)) + strlen((yyvsp[(3) - (5)].string_val)) + 2);
		strcpy(str, (yyvsp[(1) - (5)].string_val));
		strcat(str, ".");
		strcat(str, (yyvsp[(3) - (5)].string_val));
		
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup(str), linenumber);
		free(str);
		(yyval.tree_node_val) = makeNode(Assign, treeNode, (yyvsp[(5) - (5)].tree_node_val), NULL, linenumber);
	}
    break;

  case 19:

/* Line 1464 of yacc.c  */
#line 265 "parser.y"
    {	
		ASTNode *treeNode = makeNode(ArrayAssign, (yyvsp[(3) - (6)].tree_node_val), (yyvsp[(6) - (6)].tree_node_val), strdup((yyvsp[(1) - (6)].string_val)), linenumber);
		(yyval.tree_node_val) = treeNode;
	}
    break;

  case 20:

/* Line 1464 of yacc.c  */
#line 272 "parser.y"
    {linenumber++;}
    break;

  case 21:

/* Line 1464 of yacc.c  */
#line 273 "parser.y"
    {	
		struct statementList *curr = (yyvsp[(8) - (10)].statement_val);
		int sList = 0;
		while(curr)
		{
			if(curr->root->type != Newline)
				sList++;
			curr = curr->next;

		}
		(yyval.tree_node_val) = makeNode(If, (yyvsp[(3) - (10)].tree_node_val), (yyvsp[(10) - (10)].tree_node_val), (yyvsp[(8) - (10)].statement_val), linenumber);
	}
    break;

  case 22:

/* Line 1464 of yacc.c  */
#line 285 "parser.y"
    {linenumber++;}
    break;

  case 23:

/* Line 1464 of yacc.c  */
#line 286 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(If, (yyvsp[(3) - (9)].tree_node_val), (yyvsp[(9) - (9)].tree_node_val), NULL, linenumber);
	}
    break;

  case 24:

/* Line 1464 of yacc.c  */
#line 292 "parser.y"
    {
		(yyval.tree_node_val) = (yyvsp[(2) - (2)].tree_node_val);
	}
    break;

  case 25:

/* Line 1464 of yacc.c  */
#line 295 "parser.y"
    {linenumber++;}
    break;

  case 26:

/* Line 1464 of yacc.c  */
#line 296 "parser.y"
    {
		linenumber++;
		struct statementList *curr = (yyvsp[(5) - (7)].statement_val);
		int sList = 0;
		while(curr)
		{
			if(curr->root->type != Newline)
				sList++;
			curr = curr->next;
		}
		(yyval.tree_node_val) = makeNode(Else, NULL, NULL, (yyvsp[(5) - (7)].statement_val), linenumber);
	}
    break;

  case 27:

/* Line 1464 of yacc.c  */
#line 308 "parser.y"
    {linenumber++;}
    break;

  case 28:

/* Line 1464 of yacc.c  */
#line 309 "parser.y"
    {
		linenumber++;
		(yyval.tree_node_val) = makeNode(Else, NULL, NULL, NULL, linenumber);
	}
    break;

  case 29:

/* Line 1464 of yacc.c  */
#line 314 "parser.y"
    {
		(yyval.tree_node_val) = NULL;
	}
    break;

  case 30:

/* Line 1464 of yacc.c  */
#line 320 "parser.y"
    {linenumber++;}
    break;

  case 31:

/* Line 1464 of yacc.c  */
#line 321 "parser.y"
    {
		linenumber++;
		struct statementList *curr = (yyvsp[(8) - (10)].statement_val);
		int sList = 0;
		while(curr)
		{
			if(curr->root->type != Newline)
				sList++;
			curr = curr->next;
		}
		(yyval.tree_node_val) = makeNode(While, (yyvsp[(3) - (10)].tree_node_val), NULL, (yyvsp[(8) - (10)].statement_val), linenumber);
	}
    break;

  case 32:

/* Line 1464 of yacc.c  */
#line 333 "parser.y"
    {linenumber++;}
    break;

  case 33:

/* Line 1464 of yacc.c  */
#line 334 "parser.y"
    {
		linenumber++;
		(yyval.tree_node_val) = makeNode(While, (yyvsp[(3) - (9)].tree_node_val), NULL, NULL, linenumber);
	}
    break;

  case 34:

/* Line 1464 of yacc.c  */
#line 338 "parser.y"
    {linenumber++;}
    break;

  case 35:

/* Line 1464 of yacc.c  */
#line 338 "parser.y"
    {linenumber++;}
    break;

  case 36:

/* Line 1464 of yacc.c  */
#line 339 "parser.y"
    {
		struct statementList *curr = (yyvsp[(5) - (13)].statement_val);
		int sList = 0;
		while(curr)
		{
			if(curr->root->type != Newline)
				sList++;
			curr = curr->next;
		}
		if(fromNL)	
			(yyval.tree_node_val) = makeNode(DoWhile, (yyvsp[(11) - (13)].tree_node_val), NULL, (yyvsp[(5) - (13)].statement_val), linenumber-1);
		else
			(yyval.tree_node_val) = makeNode(DoWhile, (yyvsp[(11) - (13)].tree_node_val), NULL, (yyvsp[(5) - (13)].statement_val), linenumber);

		fromNL = 0;
	}
    break;

  case 37:

/* Line 1464 of yacc.c  */
#line 355 "parser.y"
    {linenumber++;}
    break;

  case 38:

/* Line 1464 of yacc.c  */
#line 355 "parser.y"
    {linenumber++;}
    break;

  case 39:

/* Line 1464 of yacc.c  */
#line 356 "parser.y"
    {
		
		if(fromNL)
			(yyval.tree_node_val) = makeNode(DoWhile, (yyvsp[(10) - (12)].tree_node_val), NULL, NULL, linenumber-1);
		else
			(yyval.tree_node_val) = makeNode(DoWhile, (yyvsp[(10) - (12)].tree_node_val), NULL, NULL, linenumber);

		fromNL = 0;
	}
    break;

  case 40:

/* Line 1464 of yacc.c  */
#line 369 "parser.y"
    {
		(yyval.object_val) = (yyvsp[(2) - (3)].object_val);
	}
    break;

  case 41:

/* Line 1464 of yacc.c  */
#line 376 "parser.y"
    {
		(yyval.array_val) = (yyvsp[(2) - (3)].array_val);
	}
    break;

  case 42:

/* Line 1464 of yacc.c  */
#line 383 "parser.y"
    {
		struct arrayNode *node = (struct arrayNode *)malloc(sizeof(struct arrayNode));
		node->index = lastIndex++;
		node->data = (yyvsp[(4) - (5)].tree_node_val);
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

		(yyval.array_val) = headArrayList;
	}
    break;

  case 43:

/* Line 1464 of yacc.c  */
#line 407 "parser.y"
    {
		struct arrayNode *node = (struct arrayNode *)malloc(sizeof(struct arrayNode));
		node->index = lastIndex++;
		node->data = (yyvsp[(2) - (3)].tree_node_val);
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

		(yyval.array_val) = headArrayList;
	}
    break;

  case 44:

/* Line 1464 of yacc.c  */
#line 431 "parser.y"
    {
		headArrayList = NULL;
		(yyval.array_val) = headArrayList;
	}
    break;

  case 45:

/* Line 1464 of yacc.c  */
#line 439 "parser.y"
    {
		if(headObjectList == NULL)
		{
			headObjectList = (yyvsp[(4) - (5)].object_val);
		}
		else
		{
			struct objectNode *p = headObjectList;
			while(p && p->next != NULL)
			{
				p = p->next;
			}
			p->next = (yyvsp[(4) - (5)].object_val);
		}
		(yyval.object_val) = headObjectList;
	}
    break;

  case 46:

/* Line 1464 of yacc.c  */
#line 456 "parser.y"
    {
		if(headObjectList == NULL)
		{
			headObjectList = (yyvsp[(2) - (3)].object_val);
		}
		else
		{
			struct objectNode *p = headObjectList;
			while(p && p->next != NULL)
			{
				p = p->next;
			}
			p->next = (yyvsp[(2) - (3)].object_val);
		}
		(yyval.object_val) = headObjectList;
	}
    break;

  case 47:

/* Line 1464 of yacc.c  */
#line 473 "parser.y"
    {
		headObjectList = NULL;
		(yyval.object_val) = headObjectList;
	}
    break;

  case 48:

/* Line 1464 of yacc.c  */
#line 481 "parser.y"
    {
		struct objectNode *field = (struct objectNode *)malloc(sizeof(struct objectNode));
		field->fieldName = strdup((yyvsp[(1) - (3)].string_val));
		field->data = (yyvsp[(3) - (3)].tree_node_val);
		field->next = NULL;
		(yyval.object_val) = field;	
	}
    break;

  case 49:

/* Line 1464 of yacc.c  */
#line 491 "parser.y"
    { linenumber++;}
    break;

  case 51:

/* Line 1464 of yacc.c  */
#line 497 "parser.y"
    {
		if(printList == NULL)
		{	
			printList = (yyvsp[(3) - (3)].print_val);
		}
		else
		{	
			argumentList *p = printList;
			while(p && p->next != NULL)
				p = p->next;		
			
			p->next = (yyvsp[(3) - (3)].print_val);		
		}
		
		(yyval.print_val) = printList;		
	}
    break;

  case 52:

/* Line 1464 of yacc.c  */
#line 514 "parser.y"
    {
		if(printList == NULL)
		{	
			printList = (yyvsp[(1) - (1)].print_val);
		}
		else
		{	
			argumentList *p = printList;
			while(p && p->next != NULL)
				p = p->next;		
			
			p->next = (yyvsp[(1) - (1)].print_val);		
		}
		
		(yyval.print_val) = printList;	
	}
    break;

  case 53:

/* Line 1464 of yacc.c  */
#line 531 "parser.y"
    {
		printList = NULL;
		(yyval.print_val) = printList;	
	}
    break;

  case 54:

/* Line 1464 of yacc.c  */
#line 539 "parser.y"
    {
		argumentList *arg = (argumentList *)malloc(sizeof(argumentList));
		arg->tree = (yyvsp[(1) - (1)].tree_node_val);
		arg->next = NULL;		
		(yyval.print_val) = arg;
	}
    break;

  case 55:

/* Line 1464 of yacc.c  */
#line 553 "parser.y"
    {
		(yyval.tree_node_val) = (yyvsp[(1) - (1)].tree_node_val);
	}
    break;

  case 56:

/* Line 1464 of yacc.c  */
#line 560 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(AndAnd, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 57:

/* Line 1464 of yacc.c  */
#line 564 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(OrOr, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 58:

/* Line 1464 of yacc.c  */
#line 568 "parser.y"
    {
		(yyval.tree_node_val) = (yyvsp[(1) - (1)].tree_node_val);
	}
    break;

  case 59:

/* Line 1464 of yacc.c  */
#line 574 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(Less, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 60:

/* Line 1464 of yacc.c  */
#line 578 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(LessEqual, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 61:

/* Line 1464 of yacc.c  */
#line 582 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(Greater, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 62:

/* Line 1464 of yacc.c  */
#line 586 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(GreaterEqual, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 63:

/* Line 1464 of yacc.c  */
#line 590 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(EqualEqual, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 64:

/* Line 1464 of yacc.c  */
#line 594 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(NotEqual, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 65:

/* Line 1464 of yacc.c  */
#line 598 "parser.y"
    {	
		(yyval.tree_node_val) = (yyvsp[(1) - (1)].tree_node_val);
	}
    break;

  case 66:

/* Line 1464 of yacc.c  */
#line 604 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(Add, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 67:

/* Line 1464 of yacc.c  */
#line 608 "parser.y"
    {

		(yyval.tree_node_val) = makeNode(Subtract, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 68:

/* Line 1464 of yacc.c  */
#line 613 "parser.y"
    {
		(yyval.tree_node_val) = (yyvsp[(1) - (1)].tree_node_val);
	}
    break;

  case 69:

/* Line 1464 of yacc.c  */
#line 620 "parser.y"
    {
		ASTNode *treeNode = makeNode(Multiply, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
		(yyval.tree_node_val) = treeNode;
	}
    break;

  case 70:

/* Line 1464 of yacc.c  */
#line 625 "parser.y"
    {

		(yyval.tree_node_val) = makeNode(Divide, (yyvsp[(1) - (3)].tree_node_val), (yyvsp[(3) - (3)].tree_node_val), NULL, linenumber);
	}
    break;

  case 71:

/* Line 1464 of yacc.c  */
#line 630 "parser.y"
    {
		(yyval.tree_node_val) = (yyvsp[(1) - (1)].tree_node_val);
	}
    break;

  case 72:

/* Line 1464 of yacc.c  */
#line 637 "parser.y"
    {	
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup((yyvsp[(1) - (1)].string_val)), linenumber);		
		(yyval.tree_node_val) = treeNode;
	}
    break;

  case 73:

/* Line 1464 of yacc.c  */
#line 642 "parser.y"
    {
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = NUM_NODE;
		node->intValue = (yyvsp[(1) - (1)].number_val);
		
		ASTNode *treeNode = makeNode(Integer, NULL, NULL, node, linenumber);		
		
		(yyval.tree_node_val) = treeNode;
	}
    break;

  case 74:

/* Line 1464 of yacc.c  */
#line 652 "parser.y"
    {
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = STRING_NODE;
		node->strValue = strdup((yyvsp[(1) - (1)].string_val));
		
		ASTNode *treeNode = makeNode(String, NULL, NULL, node, linenumber);		
		(yyval.tree_node_val) = treeNode;
	}
    break;

  case 75:

/* Line 1464 of yacc.c  */
#line 661 "parser.y"
    {
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = STRING_NODE;
		node->strValue = strdup((yyvsp[(1) - (1)].string_val));
		
		ASTNode *treeNode = makeNode(String, NULL, NULL, node, linenumber);		
		(yyval.tree_node_val) = treeNode;
	}
    break;

  case 76:

/* Line 1464 of yacc.c  */
#line 670 "parser.y"
    {
		(yyval.tree_node_val) = (yyvsp[(2) - (3)].tree_node_val);
	}
    break;

  case 77:

/* Line 1464 of yacc.c  */
#line 674 "parser.y"
    {
		char *var = (char *)malloc(strlen((yyvsp[(1) - (3)].string_val)) + strlen((yyvsp[(3) - (3)].string_val)) + 2);
		strcpy(var, (yyvsp[(1) - (3)].string_val));
		strcat(var, ".");
		strcat(var, (yyvsp[(3) - (3)].string_val));
		
		ASTNode *treeNode = makeNode(ObjectVariable, NULL, NULL, strdup(var), linenumber);		
		free(var);		
		(yyval.tree_node_val) = treeNode;
	}
    break;

  case 78:

/* Line 1464 of yacc.c  */
#line 685 "parser.y"
    {	
		ASTNode *treeNode = makeNode(ArrayVariable, (yyvsp[(3) - (4)].tree_node_val), NULL, strdup((yyvsp[(1) - (4)].string_val)), linenumber);
		(yyval.tree_node_val) = treeNode;
	}
    break;

  case 79:

/* Line 1464 of yacc.c  */
#line 690 "parser.y"
    {
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = BOOLEAN_NODE;
		node->booleanValue = 1;
		
		(yyval.tree_node_val) = makeNode(Boolean, NULL, NULL, node, linenumber);
	}
    break;

  case 80:

/* Line 1464 of yacc.c  */
#line 698 "parser.y"
    {
		struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
		node->type = BOOLEAN_NODE;
		node->booleanValue = 0;

		(yyval.tree_node_val) = makeNode(Boolean, NULL, NULL, node, linenumber);
	}
    break;

  case 81:

/* Line 1464 of yacc.c  */
#line 706 "parser.y"
    {
		ASTNode *treeNode = makeNode(Variable, NULL, NULL, strdup((yyvsp[(2) - (2)].string_val)), linenumber);
		(yyval.tree_node_val) = makeNode(Not, NULL, NULL, treeNode, linenumber);
	}
    break;

  case 82:

/* Line 1464 of yacc.c  */
#line 711 "parser.y"
    {
		(yyval.tree_node_val) = makeNode(Not, NULL, NULL, (yyvsp[(3) - (4)].tree_node_val), linenumber);
	}
    break;

  case 83:

/* Line 1464 of yacc.c  */
#line 718 "parser.y"
    {
		linenumber++;
		fromNL = 1;
	}
    break;



/* Line 1464 of yacc.c  */
#line 2641 "y.tab.c"
      default: break;
    }
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
      {
	YYSIZE_T yysize = yysyntax_error (0, yystate, yychar);
	if (yymsg_alloc < yysize && yymsg_alloc < YYSTACK_ALLOC_MAXIMUM)
	  {
	    YYSIZE_T yyalloc = 2 * yysize;
	    if (! (yysize <= yyalloc && yyalloc <= YYSTACK_ALLOC_MAXIMUM))
	      yyalloc = YYSTACK_ALLOC_MAXIMUM;
	    if (yymsg != yymsgbuf)
	      YYSTACK_FREE (yymsg);
	    yymsg = (char *) YYSTACK_ALLOC (yyalloc);
	    if (yymsg)
	      yymsg_alloc = yyalloc;
	    else
	      {
		yymsg = yymsgbuf;
		yymsg_alloc = sizeof yymsgbuf;
	      }
	  }

	if (0 < yysize && yysize <= yymsg_alloc)
	  {
	    (void) yysyntax_error (yymsg, yystate, yychar);
	    yyerror (yymsg);
	  }
	else
	  {
	    yyerror (YY_("syntax error"));
	    if (yysize != 0)
	      goto yyexhaustedlab;
	  }
      }
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
	 error, discard it.  */

      if (yychar <= YYEOF)
	{
	  /* Return failure if at end of input.  */
	  if (yychar == YYEOF)
	    YYABORT;
	}
      else
	{
	  yydestruct ("Error: discarding",
		      yytoken, &yylval);
	  yychar = YYEMPTY;
	}
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule which action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (yyn != YYPACT_NINF)
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;


      yydestruct ("Error: popping",
		  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  *++yyvsp = yylval;


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined(yyoverflow) || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
     yydestruct ("Cleanup: discarding lookahead",
		 yytoken, &yylval);
  /* Do not reclaim the symbols of the rule which action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
		  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  /* Make sure YYID is used.  */
  return YYID (yyresult);
}



/* Line 1684 of yacc.c  */
#line 725 "parser.y"


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
		HashTable hashTable;
		linenumber = 1;
		list = NULL;
		yyparse();
		
		struct statementList *curr = list;
		while(curr)
		{
			walkAST(curr->root);
			curr = curr->next;
		}
	}
	else
	{
		fprintf(stderr, "Format: ./parser <filename>\n");
	}
}
