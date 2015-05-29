#include "List.h"

class Stack : public List {
public:
	List stack;

	void push(int val);
	int pop();
	int peek();
	int isEmpty();
	void print();
	int lookup(int val);
	Stack();
	~Stack();
};

