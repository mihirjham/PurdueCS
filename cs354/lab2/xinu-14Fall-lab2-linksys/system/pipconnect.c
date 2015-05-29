#include <xinu.h>

syscall pipconnect(pipid32 pip, pid32 end1, pid32 end2)
{
	intmask mask;

	mask = disable();

	if(isbadpipid(pip) || isbadpid(end1) || isbadpid(end2))
	{
		restore(mask);
		return SYSERR;
	}
	
	struct pipe *pipptr = &pipelist[pip];

	if(pipptr->pipeState != PIPE_USED || pipptr->ownerPID != getpid())
	{
		restore(mask);
		return SYSERR;
	}
	
	pipptr->pipeState = PIPE_CONNECTED;
	pipptr->end1 = end1;
	pipptr->end2 = end2;

	restore(mask);
	return OK;
}
