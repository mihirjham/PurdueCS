#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

#include "Queue.h"

int a[] = {45, 23, 78, 12, 100, 1, 100, 34, 90, 78 };

main( int argc, char ** argv )
{
	Queue l;
	int i;

	int nelements = sizeof( a )/ sizeof( int );
	for ( i = 0; i < nelements; i++ ) {
		l.enqueue( a[ i ] );
	}
	l.print();
	printf("Peek:%d\n", l.peek());
	printf("Dequeue:%d\n", l.dequeue());
	printf("Dequeue:%d\n", l.dequeue());
	printf("isEmpty:%d\n", l.isEmpty());
	printf("Lookup 100:%d\n", l.lookup(100));

	for(i=0; i < nelements-2; i++)
	{
		l.dequeue();
	}

	printf("isEmpty:%d\n", l.isEmpty());
	l.print();

}
