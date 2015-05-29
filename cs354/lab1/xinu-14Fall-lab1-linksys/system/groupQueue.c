#include <xinu.h>
#include <string.h>

struct groupQueueEntry groupQueue[NPROC];

void groupQueueEnqueue(char *groupName)
{
	if(groupQueueHead == -1 && groupQueueTail == -1)
	{
		groupQueueHead = 0;
		groupQueueTail = 0;
		int i;
		groupQueue[groupQueueTail].groupName[GROUPLEN-1] = NULLCH;
		for(i = 0; i < GROUPLEN-1 && (groupQueue[groupQueueTail].groupName[i]=groupName[i])!= NULLCH; i++)
			;
	//	groupQueue[groupQueueTail].groupId = newqueue();
	//	enqueue(process, groupQueue[groupQueueTail].groupId);
		return;
	}
	
	int j;
	for(j = groupQueueHead; j <= groupQueueTail; j++)
	{
		int length = strnlen(groupName, GROUPLEN);
		if(!strncmp(groupName, groupQueue[j].groupName, length))
			return;
	}
	
		if(groupQueueHead == 0 && groupQueueTail == NPROC-1)
		{
			kprintf("Group Queue overflow\r\n");
			return;
		}
	
		else if(groupQueueTail == NPROC-1 && groupQueueHead != 0)
		{		
			groupQueueTail = 0;
                	int i;
			groupQueue[groupQueueTail].groupName[GROUPLEN-1] = NULLCH;
			for(i = 0; i < GROUPLEN-1 && (groupQueue[groupQueueTail].groupName[i]=groupName[i])!= NULLCH; i++)
				;
			//groupQueue[groupQueueTail].groupId = newqueue();
			//enqueue(process, groupQueue[groupQueueTail].groupId);
		}
		else
		{
			groupQueueTail++;
			int i;
			groupQueue[groupQueueTail].groupName[GROUPLEN-1] = NULLCH;
			for(i = 0; i < GROUPLEN-1 && (groupQueue[groupQueueTail].groupName[i]=groupName[i])!= NULLCH; i++)
				;
			//groupQueue[groupQueueTail].groupId = newqueue();
			//enqueue(process, groupQueue[groupQueueTail].groupId);
		}
}

char* groupQueueDequeue(void)
{
	if(groupQueueHead == -1)
	{
		kprintf("Group Queue Underflow\r\n");
		return NULL;
	}

	if(groupQueueHead > groupQueueTail)
		groupQueueHead = 0;
	
	
	//qid16 group = groupQueue[groupQueueHead].groupId;
	
	//if(isempty(group))
//		kprintf("Dequeuing empty process group\r\n");
	
	//pid32 process = dequeue(group);
	
	char *groupName = groupQueue[groupQueueHead].groupName;
	groupQueueHead++;
	
	return groupName;;
}

char* groupQueuePeek(void)
{
	if(groupQueueHead == -1)
	{
		kprintf("Group Queue Underflow\r\n");
		return NULL;
	}
	
	return groupQueue[groupQueueHead].groupName;
}

void groupQueuePrint(void)
{
	if(groupQueueHead == -1 && groupQueueHead == -1)
	{
		kprintf("Group Queue is empty\r\n");
		kprintf("\r\n");
	}
	else if(groupQueueHead == 0 && groupQueueTail == 0)
	{
		kprintf("Group name : %s\r\n", groupQueue[groupQueueTail].groupName);
		kprintf("\r\n");
	}
	else
	{
		int i;
		for(i = groupQueueHead; i <= groupQueueTail; i++)
		{
			kprintf("Group name : %s\r\n", groupQueue[i].groupName);
		}
		kprintf("\r\n");
	}
}
