#include <stdlib.h>
#include <string.h>

typedef enum ASTNodeType ASTNodeType;

enum ASTNodeType
{
	Integer,
	Variable,
	String,
	Assign,
	Declare,
	Add,
	Subtract,
	Divide,
	Multiply,
	Object,
	ObjectVariable,
	Array,
	ArrayVariable,
	Print,
	Undefined,
	Uninitialized,
	Boolean,
	Not,
	AndAnd,
	OrOr,
	Less,
	LessEqual,
	Greater,
	GreaterEqual,
	EqualEqual,
	NotEqual,
	ArrayAssign,
	If,
	Else,
	ElseIf,
	CloseBrace,
	Newline,
	While,
	DoWhile,
	Break,
	Continue
};

typedef struct ASTNode ASTNode;

struct ASTNode
{
	ASTNode *left;
	ASTNode *right;
	ASTNodeType type;
	void *data;
	int linenumber;
};

typedef struct dataNode dataNode;

enum NodeType
{
	NUM_NODE,
	STRING_NODE,
	OBJECT_NODE,
	ARRAY_NODE,
	UNINITIALIZED_NODE,
	UNDEFINED_NODE,
	BOOLEAN_NODE
};

struct dataNode
{
	enum NodeType type;
	char *strValue;
	int intValue;
	int booleanValue;
};

struct objectNode
{	
	char *fieldName;
	ASTNode *data;
	struct objectNode *next;
};

struct arrayNode
{
	int index;
	ASTNode *data;
	struct arrayNode *next;
};

typedef struct argumentList argumentList;

struct argumentList
{
	ASTNode *tree;
	argumentList *next;
};

struct statementList
{
	int linenumber;
	ASTNode *root;
	struct statementList *next;
	int errorReported;
};

typedef enum ErrorType ErrorType;

enum ErrorType
{
	TypeViolation,
	UninitializedViolation,
	ConditionViolation,
	UndeclaredViolation
};

struct statementList *list;
ASTNode* makeNode(ASTNodeType type, ASTNode *left, ASTNode *right, void *data, int linenumber);
ASTNode* walkAST(ASTNode *root);
void printAST(ASTNode *root);
void reportError(int linenumber, ErrorType type, char *var);
void reportErrorList(int linenumber, ErrorType type, char *var, struct statementList *sList);
int countLines(struct statementList *sList);
