#include <xinu.h>

local	pipid32	newpip(void);

syscall pipcreate(void)
{
	intmask mask;
	pipid32	pip;

	mask = disable();


	if((pip = newpip()) == SYSERR)
	{
		restore(mask);
		return SYSERR;
	}
	
	struct pipe *pipptr = &pipelist[pip];

	pipptr->pipeState = PIPE_USED;
	pipptr->producerSem = semcreate(PIPE_SIZE);
	pipptr->consumerSem = semcreate(0);
	pipptr->ownerPID = getpid();
	pipptr->producerIndex = 0;
	pipptr->consumerIndex = 0;

	restore(mask);
	return pip;
}

local pipid32 newpip(void)
{
	static pipid32 nextpip = 0;
	pipid32 pip;
	int32 i;

	for(i = 0; i < NPIPE; i++)
	{
		pip = nextpip++;

		if(nextpip >= NPIPE)
			nextpip = 0;

		if(pipelist[pip].pipeState == PIPE_FREE)
		{
			return pip;
		}
	}
	
	return SYSERR;
}
