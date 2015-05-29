/* insert.c - insert */

#include <xinu.h>
#include <string.h>

/*------------------------------------------------------------------------
 *  insert  -  Insert a process into a queue in descending key order
 *------------------------------------------------------------------------
 */
status	insert(
	  pid32		pid,		/* ID of process to insert	*/
	  qid16		q,		/* ID of queue to use		*/
	  int32		key		/* key for the inserted process	*/
	)
{
	int16	curr;			/* runs through items in a queue*/
	int16	prev;			/* holds previous node index	*/

	if (isbadqid(q) || isbadpid(pid)) {
		return SYSERR;
	}

	curr = firstid(q);
	if(!ROUND_ROBIN)
	{
		while (queuetab[curr].qkey >= key) {
			curr = queuetab[curr].qnext;
		}
	}
	else
	{
		struct procent *pr = &proctab[pid];
		struct procent *currPr = &proctab[curr];
		int32 isEnd = 0;
		
		while(strncmp(pr->groupname, currPr->groupname, strnlen(currPr->groupname, GROUPLEN) != 0))
		{
			curr = queuetab[curr].qnext;
			if(curr == lastid(q))
			{
				isEnd = 0;
				break;
			}
			currPr = &proctab[curr];
		}
		if(!isEnd)
		{
			while(strncmp(pr->groupname, currPr->groupname, strnlen(currPr->groupname, GROUPLEN)) == 0)
			{	
				curr = queuetab[curr].qnext;
				if(curr == lastid(q))
				{
					currPr = &proctab[curr];
					break;
				}
				currPr = &proctab[curr];
				
			}
		}
	}
	/* insert process between curr node and previous node */

	prev = queuetab[curr].qprev;	/* get index of previous node	*/
	queuetab[pid].qnext = curr;
	queuetab[pid].qprev = prev;
	queuetab[pid].qkey = key;
	queuetab[prev].qnext = pid;
	queuetab[curr].qprev = pid;
	return OK;
}
