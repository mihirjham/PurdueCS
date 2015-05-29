struct groupQueueEntry	{
	char	groupName[GROUPLEN];	
	//qid16	groupId;
};

extern struct groupQueueEntry	groupQueue[];

extern	int32	groupQueueHead;
extern	int32	groupQueueTail;

void 	groupQueueEnqueue(char *);
char*	groupQueueDequeue(void);
char*	groupQueuePeek(void);
void	groupQueuePrint(void);
