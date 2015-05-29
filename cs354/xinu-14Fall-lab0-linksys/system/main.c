/*  main.c  - main */

#include <xinu.h>
#include <stdio.h>

/************************************************************************/
/*									*/
/* main - main program for testing Xinu					*/
/*									*/
/************************************************************************/


int main(int argc, char **argv)
{
	print_proc_info();
	my_message();
	return OK;
}

