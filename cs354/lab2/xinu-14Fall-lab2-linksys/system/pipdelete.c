#include <xinu.h>

syscall pipdelete(pipid32 pip)
{
	intmask mask;
	
	mask = disable();

	if(isbadpipid(pip))
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
	
	pipptr->pipeState = PIPE_FREE;
	pipptr->ownerPID = 0;
	pipptr->producerSem = semdelete(pipptr->producerSem);
	pipptr->consumerSem = semdelete(pipptr->consumerSem);
	pipptr->end1 = 0;
	pipptr->end2 = 0;
	pipptr->producerIndex = 0;
	pipptr->consumerIndex = 0;

	restore(mask);
	return OK;
}
