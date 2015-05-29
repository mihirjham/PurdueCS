#include <xinu.h>
syscall registercb(umsg32 *abuf, int (* func) (void))
{
	fptr = func;
	globalMsg = abuf;
	return OK;
}
