#include <xinu.h>

syscall pipread_nonblock(pipid32 pip, char *buf, uint32 len)
{
	intmask mask;

	mask = disable();

	if(isbadpipid(pip))
	{
		restore(mask);
		return SYSERR;
	}

	struct pipe *pipptr = &pipelist[pip];

	if(pipptr->pipeState != PIPE_CONNECTED || (pipptr->end2 != getpid()))
	{
		restore(mask);
		return SYSERR;
	}

	int i = 0;

	if(semcount(pipptr->consumerSem) >= PIPE_SIZE)
	{
		restore(mask);
		return 0;
	}
	
	while(i < len)
	{
		wait(pipptr->consumerSem);
		
		buf[i] = pipptr->buffer[pipptr->consumerIndex];
		pipptr->consumerIndex = (pipptr->consumerIndex + 1) % PIPE_SIZE;
		i++;

		signal(pipptr->producerSem);
	}

	restore(mask);
	return i;
}

syscall pipread_block(pipid32 pip, char *buf, uint32 len)
{
	intmask mask;

	mask = disable();

	if(isbadpipid(pip))
	{
		restore(mask);
		return SYSERR;
	}

	struct pipe *pipptr = &pipelist[pip];

	if(pipptr->pipeState != PIPE_CONNECTED || (pipptr->end2 != getpid()))
	{
		restore(mask);
		return SYSERR;
	}

	int i = 0;
	
	if(semcount(pipptr->consumerSem) >= PIPE_SIZE)
	{
		wait(pipptr->consumerSem);
	}
	
	while(i < len)
	{
		wait(pipptr->consumerSem);

		buf[i] = pipptr->buffer[pipptr->consumerIndex];
		pipptr->consumerIndex = (pipptr->consumerIndex + 1) % PIPE_SIZE;
		i++;

		signal(pipptr->producerSem);
	}

	restore(mask);
	return i;
}

