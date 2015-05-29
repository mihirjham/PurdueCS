#include <stdio.h>
#include "Stack.h"

void
Stack::push(int val) 
{
	stack.prepend(val);
}

void
Stack::print()
{
	stack.print();
}

int
Stack::pop()
{
	int head = stack._head->_value;
	stack.remove(head);
	return head;
}

int
Stack:: peek()
{
	int head = stack._head->_value;
	return head;
}

int
Stack:: isEmpty()
{
	return stack._head == NULL;
}

int
Stack:: lookup(int val)
{
	return stack.lookup(val);
}

Stack::Stack()
{}

Stack::~Stack()
{
	stack.~List();
}
