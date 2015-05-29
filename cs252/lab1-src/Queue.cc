#include <stdio.h>
#include "Queue.h"

void
Queue::enqueue(int val)
{
	queue.append(val);
}

int
Queue::dequeue()
{
	int head = queue._head->_value;
	queue.remove(head);
	return head;
}

int
Queue::peek()
{
	int head = queue._head->_value;
	return head;
}

int
Queue::isEmpty()
{
	return queue._head == NULL;
}

void
Queue::print()
{
	queue.print();
}

int
Queue::lookup(int val)
{
	return queue.lookup(val);
}

Queue::Queue()
{}

Queue::~Queue()
{}
