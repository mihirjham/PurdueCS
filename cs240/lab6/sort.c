#include <stdio.h>
#include <string.h>
#include "list.h"
#include "sort.h"
/*
 * Compares n1 and n2 based on the words stored in nodes.
 * Return -1 if word in n1 less than word in n2
 * Return  0 if word in n1 is equal to word in n2
 * Return  1 if word in n1 is greater than  word in n2
 */
int wordCmp (struct lnode* n1, struct lnode* n2) {
	/* FILL IN HERE */
	int result = strcmp(nodeGetWord(n1), nodeGetWord(n2));

	if(result < 0)
		return -1;
	else if(result > 0)
		return 1;
	else
		return 0;
		
}
/*
 * Compares n1 and n2 based on the lines stored in nodes.
 * Return -1 if line in n1 less than line in n2
 * Return  0 if line in n1 is equal to line in n2
 * Return  1 if line in n1 is greater than  line in n2
 */
int lineCmp (struct lnode* n1, struct lnode* n2) {
	/* FILL IN HERE */

	if(nodeGetLine(n1) < nodeGetLine(n2))
		return -1;
	else if(nodeGetLine(n1) > nodeGetLine(n2))
		return 1;
	else
		return 0;
}
/*
 * Compares n1 and n2 based on the counts stored in nodes.
 * Return -1 if count in n1 less than count in n2
 * Return  0 if count in n1 is equal to count in n2
 * Return  1 if count in n1 is greater than  count in n2
 */
int countCmp (struct lnode* n1, struct lnode* n2) {
	/* FILL IN HERE */

	if(nodeGetCount(n1) < nodeGetCount(n2))
		return -1;
	else if(nodeGetCount(n1) > nodeGetCount(n2))
		return 1;
	else
		return 0;
}
/*
 * Swap nodes n1 and n2 in the linked list. For eg, if the
 * list looks like: -- n1 -- n2 -- , then after swapping n1
 * with n2, it should look like this: -- n2 -- n1 --.
 * head is the pointer to the head pointer of the linked list.
 */
void swap (struct lnode** head, struct lnode* n1, struct lnode* n2) {
	/* FILL IN HERE */

	struct lnode* prev_node1 = NULL;
	struct lnode* prev_node2 = NULL;
	struct lnode* temp1 = NULL;
	struct lnode* temp2 = NULL;

	if((n1 == NULL) || (n2 == NULL))
		return;
	if(n1 == n2)
		return;
	if(*head == NULL)
		return;

	if((nodeGetNext(n1) == n2) && (nodeGetPrev(n2) == n1))
	{
		prev_node1 = nodeGetPrev(n1);
		prev_node2 = nodeGetPrev(n2);

		evictNode(head, n1);
		temp1 = newNode(nodeGetWord(n2), nodeGetCount(n2), nodeGetLine(n2));
		insertNode(head, prev_node1, temp1);

		evictNode(head, n2);
		temp2 = newNode(nodeGetWord(n1), nodeGetCount(n1), nodeGetLine(n1));
		insertNode(head, temp1, temp2 );
	}
	else if((nodeGetNext(n2) == n1) && (nodeGetPrev(n1) == n2))
	{
		prev_node1 = nodeGetPrev(n2);
		prev_node2 = nodeGetPrev(n1);

		evictNode(head, n2);
		temp1 = newNode(nodeGetWord(n1), nodeGetCount(n1), nodeGetLine(n1));
		insertNode(head, prev_node1, temp1);

		evictNode(head, n1);
		temp2 = newNode(nodeGetWord(n2), nodeGetCount(n2), nodeGetLine(n2));
		insertNode(head, temp1, temp2);
	}
	else if(n1 == *head)
	{
		prev_node1 = nodeGetPrev(n1);
		prev_node2 = nodeGetPrev(n2);

		evictNode(head, n2);
		temp1 = newNode(nodeGetWord(n1), nodeGetCount(n1), nodeGetLine(n1));
		insertNode(head, prev_node2, temp1);

		evictNode(head, n1);
		temp2 = newNode(nodeGetWord(n2), nodeGetCount(n2), nodeGetLine(n2));
		insertNode(head, prev_node1, temp2);
	}
	else if(n2 == *head)
	{
		prev_node1 = nodeGetPrev(n1);
		prev_node2 = nodeGetPrev(n2);

		evictNode(head, n1);
		temp1 = newNode(nodeGetWord(n2), nodeGetCount(n2), nodeGetLine(n2));
		insertNode(head, prev_node1, temp1);

		evictNode(head, n2);
		temp2 = newNode(nodeGetWord(n1), nodeGetCount(n1), nodeGetLine(n2));
		insertNode(head, prev_node2, temp2);
	}
	else if((nodeGetNext(n2) == NULL))
	{
		prev_node1 = nodeGetPrev(n1);
		prev_node2 = nodeGetPrev(n2);

		evictNode(head, n1);
		temp1 = newNode(nodeGetWord(n2), nodeGetCount(n2), nodeGetLine(n2));
		insertNode(head, prev_node1, temp1);

		evictNode(head, n2);
		temp2 = newNode(nodeGetWord(n1), nodeGetCount(n1), nodeGetLine(n1));
		insertNode(head, prev_node2, temp2);
	}
	else if((nodeGetNext(n1) == NULL))
	{
		prev_node1 = nodeGetPrev(n2);
		prev_node2 = nodeGetPrev(n1);

		evictNode(head, n2);
		temp1 = newNode(nodeGetWord(n1), nodeGetCount(n1), nodeGetLine(n1));
		insertNode(head, prev_node1, temp1);
		
		evictNode(head, n1);
		temp2 = newNode(nodeGetWord(n2), nodeGetCount(n2), nodeGetLine(n2));
		insertNode(head, prev_node2, temp2);
	}
	else
	{
		prev_node1 = nodeGetPrev(n1);
		prev_node2 = nodeGetPrev(n2);

		evictNode(head, n1);
		temp1 = newNode(nodeGetWord(n2), nodeGetCount(n2), nodeGetLine(n2));
		insertNode(head, prev_node1, temp1);

		evictNode(head, n2);
		temp2 = newNode(nodeGetWord(n1), nodeGetCount(n1), nodeGetLine(n1));
		insertNode(head, prev_node2, temp2);
	}
}
/*
 * Sorts the linked list whose head is (*head).
 * This should take 2 function pointers -
 * swapPtr - a pointer to the function swap, and
 * comparePtr - a pointer to either wordCmp, lineCmp or countCmp.
 * Based on this description, complete the function signature of sort
 */
void sort (struct lnode** head, void (*swapPtr) (struct lnode** head, struct lnode* n1, struct lnode* n2), int (*comparePtr) (void* v1, void* v2)) {
/* FILL IN HERE */
	struct lnode *temp1 = NULL;
	struct lnode *temp2 = NULL;
	struct lnode *next = NULL;
	
	int isSorted = 1;
	for(temp1 = *head; (temp1 != NULL) && (isSorted == 1); temp1 = nodeGetNext(temp1))
	{
		isSorted = 0;
		for(temp2 = nodeGetNext(temp1); temp2 != NULL; temp2 = nodeGetNext(temp2))
		{
			if(comparePtr((struct lnode*)temp1,(struct lnode*) temp2) > 0)
			{
				swapPtr(head, temp1, temp2);
				next = getNode(*head, nodeGetWord(temp1));
				temp1 = getNode(*head, nodeGetWord(temp2));
				temp2 = next;
			}
			isSorted = 1;
		}
	}		
}
