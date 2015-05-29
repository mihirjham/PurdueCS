#include <xinu.h>

syscall pipwrite_nonblock(pipid32 pip, char *buf, uint32 len)
{
	intmask mask;

	mask = disable();

	if(isbadpipid(pip))
	{
		restore(mask);
		return SYSERR;
	}

	struct pipe *pipptr = &pipelist[pip];

	if(pipptr->pipeState != PIPE_CONNECTED || (pipptr->end1 != getpid()))
	{
		restore(mask);
		return SYSERR;
	}

	int i = 0;
	
	if(semcount(pipptr->producerSem) <= 0)
	{
		restore(mask);
		return 0;
	}

	while(i < len)
	{
		wait(pipptr->producerSem);

		pipptr->buffer[pipptr->producerIndex] = buf[i];
		pipptr->producerIndex = (pipptr->producerIndex + 1) % PIPE_SIZE;
		i++;

		signal(pipptr->consumerSem);
	}

	restore(mask);
	return i;
}

syscall pipwrite_block(pipid32 pip, char *buf, uint32 len)
{
	intmask mask;

	mask = disable();

	if(isbadpipid(pip))
	{
		restore(mask);
		return SYSERR;
	}

	struct pipe *pipptr = &pipelist[pip];

	if(pipptr->pipeState != PIPE_CONNECTED || (pipptr->end1 != getpid()))
	{
		restore(mask);
		return SYSERR;
	}

	int i = 0;

	if(semcount(pipptr->producerSem) <= 0)
	{
		wait(pipptr->producerSem);
	}
	
	while(i < len)
	{
		wait(pipptr->producerSem);

		pipptr->buffer[pipptr->producerIndex] = buf[i];
		pipptr->producerIndex = (pipptr->producerIndex + 1) % PIPE_SIZE;
		i++;

		signal(pipptr->consumerSem);
	}

	restore(mask);
	return i;
}

