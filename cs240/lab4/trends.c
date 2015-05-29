#include <stdio.h>
#include <string.h>
#include "list.h"
#include <ctype.h>

#define MAXWORD 140
#define MAX 100

struct lnode *createList(struct lnode*, char*, int);
void listPrint(struct lnode*);
int getWord(char*, int);
int lineNumber = 1;
int listLength = 0;
void changeLineNumber(int);
void changeListLength(int);

int main()
{
	struct lnode *root = NULL;
	struct lnode *head = NULL;
	char word[MAXWORD];
	int cond;

	while(getWord(word, MAXWORD) != EOF)
	{
		printf("entering while loop\n");
		if(isalpha(word[0]))
		{
			if(root == NULL)
			{
				root = newNode(word, lineNumber);
				pushNode(&head, root);
				changeListLength(listLength+1);
			}	

			if((cond = strcmp(word, nodeGetWord(root)) == 0))
				nodeSetCount(root, nodeGetCount(root)+1);
		
			root = nodeGetNext(root);
		}
	}

	listPrint(head);

	return 0;
}

int getWord(char *word, int lim)
{
	int c;
	int limit = lim;
	while ((c=getchar())!=EOF && !isalpha(c)) {
		if(c == '\n')
		{
			changeLineNumber(lineNumber + 1);
		}
	}
	if (c==EOF) {
		return EOF;
	}
	*word = c;
	word += 1;
	limit -= 1;
	while (limit>0 && (c=getchar())!=EOF && isalpha(c)) {
		*word = c;
		word += 1;
		limit -= 1;
	}
	*word = '\0';
	return word[0];
}

void changeLineNumber(int line)
{
	lineNumber = line;
}

void listPrint(struct lnode* root)
{
	if(root != NULL)
	{
		printf("%s %d\n", nodeGetWord(root), nodeGetCount(root));
		listPrint(nodeGetNext(root));
	}
}

void changeListLength(int length)
{
	listLength = length;
}
