#define NPIPE 		10
#define PIPE_SIZE	256

#define PIPE_FREE	1
#define PIPE_USED	2
#define PIPE_CONNECTED	3

#define isbadpipid(pipeid) ((pipeid)<0 || pipeid>=NPIPE)

struct pipe
{
	uint16	pipeState;		/* State of the pipe		*/
	sid32	producerSem;		/* Producer Semaphore ID 	*/
	sid32	consumerSem;		/* Consumer Semaphore ID	*/
	pipid32	pipeId;			/* Pipe ID			*/
	pid32	ownerPID;		/* Owner Process ID		*/
	pid32	end1;			/* End1 Process ID		*/
	pid32	end2;			/* End2 Process ID		*/
	char	buffer[PIPE_SIZE];	/* Buffer			*/
	int32	producerIndex;
	int32	consumerIndex;
};

extern struct pipe pipelist[];		/* List of pipes		*/
extern pipid32 nextpipeid;;		/* Next pipe ID to look for 	*/
