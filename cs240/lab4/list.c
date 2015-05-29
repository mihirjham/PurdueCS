#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include "list.h"

int mallocCounter = 0;
void changeCounter(int);

void changeCounter(int counter)
{
	mallocCounter = counter;
}

int strLen(char* s)
{
	int nc;

	for(nc=0; *(s+nc) != '\0'; ++nc);

	return nc;
}

struct lnode {
	/* FILL THIS IN */

	char* word;
	int line;
	int count;
	struct lnode* next; 
};

/**
 * Returns a new linked list node filled in with the given word and line, and
 * sets the count to be 1. Make sure to duplicate the word, as the original word
 * may be modified by the calling function.
 */
struct lnode* newNode (char* word, int line) {
	/* FILL THIS IN */

	struct lnode *newNode = NULL;

	newNode =(struct lnode*) malloc(sizeof(struct lnode));
	changeCounter(mallocCounter + 1);

	int wordLen = strLen(word);	

	if(newNode != NULL)
	{
		newNode->word = (char*) malloc((wordLen * sizeof(char))+1);
		changeCounter(mallocCounter + 1);
		if(newNode->word != NULL)
		{
			strncpy(newNode->word, word, wordLen);
			(newNode->word)[wordLen] = '\0';
			newNode->line = line;
			newNode->count = 1;
			newNode->next = NULL;
		}
	}

	return newNode;

	//abort(); // Please remove the abort() when you implement this function
}

/**
 * In a linked list with *head as the head pointer, adds the given node to the
 * front of the list.
 */
void pushNode (struct lnode** head, struct lnode* node) {
	/* FILL THIS IN */

	if(head)
	{
		if(node)
		{	
			node->next = *head;
			*head = node;
		}
	}
	else
	{
		node->next = NULL;
		*head = node;
	}

	//abort(); // Please remove the abort() when you implement this function
}


/**
 * Returns the pointer to the node containing the given word in the linked list
 * with head as the head pointer. If a node with the given word cannot be found,
 * the function returns NULL.
 */
struct lnode* getNode (struct lnode* head, char* word) {
	/* FILL THIS IN */

	struct lnode *iter = NULL;

	for(iter = head; strcmp(nodeGetWord(iter), word) != 0 && iter != NULL ; iter = iter->next);

	return iter;

	//abort(); // Please remove the abort() when you implement this function
}

/**
 * Removes the specified node from the list, and frees all memory the node is
 * using. Remember if *head is the node being deleted, it must be updated.
 */
void deleteNode (struct lnode** head, struct lnode* node){
	/* FILL THIS IN */


	if(head != NULL)
	{
		if(*head != NULL)
		{

			if(*head == node)
			{
				struct lnode *current= *head;
				struct lnode *next = current->next;

				if(next == NULL)
				{
					free(current->word);
					changeCounter(mallocCounter - 1);
					current = NULL;
					free(*head);
					changeCounter(mallocCounter -1);
					*head = NULL;
					return;
				}

				current->word = nodeGetWord(next);
				current->line = nodeGetLine(next);
				current->count = nodeGetCount(next);

				*head = next;
				current->next = next->next;

				if(node != NULL)
				{
					free(node->word);
					free(node);
					changeCounter(mallocCounter - 2);
					node = NULL;
					return;
				}
			}

			struct lnode *prev = *head;

			while(prev->next != NULL && prev->next != node)
			{
				prev = prev->next;
			}

			if(prev->next == NULL)
			{
				prev = NULL;
				free(node->word);
				free(node);
				changeCounter(mallocCounter-2);
				node = NULL;
				return;
			}

			prev->next = (prev->next)->next;

			if(node != NULL)
			{
				free(node->word);
				free(node);
				changeCounter(mallocCounter-2);
				node = NULL;
			}
			//abort(); // Please remove the abort() when you implement this function
		}
	}
}

/**
 * Simply returns the next node in the list, or NULL if there are no further nodes.
 */
struct lnode *nodeGetNext(struct lnode *node) {
	/* FILL THIS IN */

	struct lnode *nextNode = node->next;

	return nextNode;

	//abort(); // Please remove the abort() when you implement this function
}

/**
 * Simply returns the word in the given node.
 */
char *nodeGetWord(struct lnode *node) {
	/* FILL THIS IN */
	int wordLen = strLen(node->word);

	char *word;
	
	if(word)
	{
		free(word);
		changeCounter(mallocCounter -1);
	}
	
	word = (char*) malloc((wordLen * sizeof(char))+1);
	changeCounter(mallocCounter+1);
	if(word)
	{	
		strncpy(word, node->word, wordLen);
		word[wordLen] = '\0';
	}

	return word;

	//abort(); // Please remove the abort() when you implement this function
}

/**
 * Simply returns the line in the given node.
 */
int nodeGetLine(struct lnode *node) {
	/* FILL THIS IN */

	int line = node->line;

	return line;
	//abort(); // Please remove the abort() when you implement this function
}

/**
 * Simply returns the count in the given node.
 */
int nodeGetCount(struct lnode *node) {
	/* FILL THIS IN */

	int count = node->count;

	return count;
	//abort(); // Please remove the abort() when you implement this function
}

/**
 * Set the count in the node to be the given count.
 */
void nodeSetCount(struct lnode *node, int count) {
	/* FILL THIS IN */

	node->count = count;

	//abort(); // Please remove the abort() when you implement this function
}

/**
 * Set the line in the node to be the given line.
 */
void nodeSetLine(struct lnode *node, int line) {
	/* FILL THIS IN */

	node->line = line;
	//abort(); // Please remove the abort() when you implement this function
}


/**
 * Deletes every node in the list with *head as the head pointer. After calling
 * this function, all memory used by the list should be freed, and *head
 * should be NULL.
 */
void deleteList(struct lnode **head) {
	/* FILL THIS IN */

	if(head != NULL)
	{
		if(*head != NULL)
		{
			struct lnode *next;
			struct lnode *deleteMe;
			deleteMe = *head;

			while(deleteMe)
			{
				next = deleteMe->next;
				free(deleteMe->word);
				free(deleteMe);
				changeCounter(mallocCounter -2);
				deleteMe = next;
			}

			*head = NULL;
		}
	}
}
