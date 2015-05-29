/*  main.c  - main */

#include <xinu.h>
#include <stdio.h>

/************************************************************************/
/*									*/
/* main - main program for testing Xinu					*/
/*									*/
/************************************************************************/

void createPr(char c)
{
	while(1)
	{
		kprintf("%c", c);
	}
}	

int ROUND_ROBIN = 1;

int main(int argc, char **argv)
{
	starve();
	
	return OK;
}
