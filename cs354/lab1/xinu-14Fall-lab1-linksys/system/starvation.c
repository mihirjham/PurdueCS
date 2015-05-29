/*  starvation.c  - starvation */

#include <xinu.h>
#include <stdio.h>

/************************************************************************/
/*									*/
/* starvation - program to present starvation in Xinu			*/
/*									*/
/************************************************************************/

void prA(char c)
{
	while(1)
	{
		kprintf("%c", c);
	}
}

void starve(void)
{
	pid32 processA = create(prA, 1024, 20, "group1", "Process A", 1, 'a');
	pid32 processB = create(prA, 1024, 25, "group2", "Process B", 1, 'b');
	pid32 processC = create(prA, 1024, 25, "group1", "Process C", 1, 'c');

	resume(processA);
	resume(processB);
	resume(processC);

	while(1)
		sleep(1);
}
