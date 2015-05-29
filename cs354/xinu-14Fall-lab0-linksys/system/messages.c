#include <xinu.h>
#include <string.h>

syscall messages(void)
{
	kprintf("I want to get straight As this semester\n");
	return OK;
}
