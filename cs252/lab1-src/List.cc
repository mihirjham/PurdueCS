//
// Implement the List class
//

#include <stdio.h>
#include "List.h"

//
// Inserts a new element with value "val" in
// ascending order.
//
void
List::insertSorted( int val )
{
  // Complete procedure 
	if(_head != NULL)
	{
		ListNode *node = new ListNode;
		node->_value = val;
		node->_next = NULL;

		if(node->_value < _head->_value)
		{
			ListNode *temp = _head;
			node->_next = temp;
			_head = node;
		}
		else
		{
			ListNode *prev = _head;
			ListNode *curr = _head->_next;

			while(curr && node->_value >= curr->_value)
			{
				prev = prev->_next;
				curr = curr->_next;
			}
			node->_next = curr;
			prev->_next = node;
		}
	}
	else
	{
		ListNode *node = new ListNode;
		node->_value = val;
		node->_next = NULL;
		_head = node;
	}
}
//
// Inserts a new element with value "val" at
// the end of the list.
//
void
List::append( int val )
{
  // Complete procedure 
	ListNode *node = new ListNode;
	node->_value = val;
	node->_next = NULL;
	
	if(_head !=NULL)
	{
		ListNode *temp = _head;
		
		while(temp->_next != NULL)
		{
			temp = temp->_next;
		}
		temp->_next = node;
	}
	else
	{
		_head = node;
	}
}

//
// Inserts a new element with value "val" at
// the beginning of the list.
//
void
List::prepend( int val )
{
  // Complete procedure 
	ListNode *node = new ListNode;
	node->_value = val;
	ListNode *temp = _head;
	node->_next = temp;

	_head = node;
}

// Removes an element with value "val" from List
// Returns 0 if succeeds or -1 if it fails
int 
List:: remove( int val )
{
  // Complete procedure
  if(_head != NULL)
  {
  	if(_head->_value == val)
  	{
  		ListNode *next = _head->_next;
		delete(_head);
		_head = next;
  	}
  	else
  	{
  		ListNode *curr = _head->_next;
		ListNode *prev = _head;

		while(curr && curr->_value != val)
		{
			prev = prev->_next;
			curr = curr->_next;
		}
  		if(curr)
		{
			ListNode *next = curr->_next;
			prev->_next = next;
			delete(curr);
		}
		else
			return -1;

  	}
  	return 0;
  }	
	return -1;
}

// Prints The elements in the list. 
void
List::print()
{
  // Complete procedure 
	if(_head != NULL)
	{
		ListNode *node = _head;
		while(node)
		{
			printf("%d\n", node->_value);
			node = node->_next;
		}
	}
	else
	{
		printf("Empty List!\n");
		return;
	}
}

//
// Returns 0 if "value" is in the list or -1 otherwise.
//
int
List::lookup(int val)
{
  // Complete procedure 
	if(_head != NULL)
	{
		ListNode *node = _head;

		while(node && node->_value != val)
		{
			node = node->_next;
		}
		if(node)
			return 0;
	}
  	return -1;
}

//
// List constructor
//
List::List()
{
  // Complete procedure 
	_head = NULL;
}

//
// List destructor: delete all list elements, if any.
//
List::~List()
{
  // Complete procedure 
	ListNode *node = _head;
	while(node != NULL)
	{
		ListNode *next = node->_next;
		delete(node);
		node = next;
	}
	_head = NULL;
}

