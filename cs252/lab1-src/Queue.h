#include "List.h"

class Queue : public List {
public:
	List queue;

	void enqueue(int val);
	int dequeue();
	int peek();
	int isEmpty();
	void print();
	int lookup(int val);
	Queue();
	~Queue();
};
