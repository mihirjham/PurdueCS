/* resched.c - resched */

#include <xinu.h>
#include <string.h>

/*------------------------------------------------------------------------
 *  resched  -  Reschedule processor to highest priority eligible process
 *------------------------------------------------------------------------
 */
void	resched(void)		/* assumes interrupts are disabled	*/
{
	struct procent *ptold;	/* ptr to table entry for old process	*/
	struct procent *ptnew;	/* ptr to table entry for new process	*/

	/* If rescheduling is deferred, record attempt and return */

	if (Defer.ndefers > 0) {
		Defer.attempt = TRUE;
		return;
	}

	/* Point to process table entry for the current (old) process */

	ptold = &proctab[currpid];
	if(!ROUND_ROBIN)
	{
		if (ptold->prstate == PR_CURR) {  /* process remains running */
			if (ptold->prprio > firstkey(readylist)) {
				return;
			}

			/* Old process will no longer remain current */

			ptold->prstate = PR_READY;
			insert(currpid, readylist, ptold->prprio);
		}
		
		currpid = dequeue(readylist);
		ptnew = &proctab[currpid];
		ptnew->prstate = PR_CURR;
		preempt = QUANTUM;              /* reset time slice for process */
		ctxsw(&ptold->prstkptr, &ptnew->prstkptr);
	}
	else
	{
		pid32 old = currpid;
		ptold = &proctab[old];
		ptold->prstate = PR_READY;
		insert(currpid, readylist, ptold->prprio);

		currpid = dequeue(readylist);
		struct procent *prptr = &proctab[currpid];
		
		while(strncmp(ptold->groupname, prptr->groupname, strnlen(ptold->groupname, GROUPLEN)) == 0)
		{
			enqueue(currpid, readylist);
			currpid = dequeue(readylist);
			prptr = &proctab[currpid];

			if(old == currpid)
			{
				enqueue(currpid, readylist);
				currpid = dequeue(readylist);
				break;
			}

		}
		ptnew = &proctab[currpid];
		ptnew->prstate = PR_CURR;
		preempt = QUANTUM;              /* reset time slice for process */
		ctxsw(&ptold->prstkptr, &ptnew->prstkptr);
	}

	/* Force context switch to highest priority ready process */

	
	/* Old process returns here when resumed */

	return;
}
