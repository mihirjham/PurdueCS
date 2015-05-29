#include <xinu.h>

syscall pipdisconnect(pipid32 pip)
{
	intmask mask;

	mask = disable();

	if(isbadpipid(pip))
	{
		restore(mask);
		return SYSERR;
	}
	
	struct pipe *pipptr = &pipelist[pip];

	if(pipptr->pipeState != PIPE_CONNECTED || pipptr->ownerPID != getpid())
	{
		restore(mask);
		return SYSERR;
	}
	
	pipptr->pipeState = PIPE_USED;
	pipptr->end1 = 0;
	pipptr->end2 = 0;
	pipptr->producerSem = semdelete(pipptr->producerSem);
	pipptr->producerSem = semcreate(PIPE_SIZE);
	pipptr->consumerSem = semdelete(pipptr->consumerSem);
	pipptr->consumerSem = semcreate(0);
	pipptr->producerIndex = 0;
	pipptr->consumerIndex = 0;

	restore(mask);
	return OK;
}
