/* sendb.c - sendb */

#include <xinu.h>

/*------------------------------------------------------------------------
 *  senda  -  pass a message to a process and start recipient if waiting
 *------------------------------------------------------------------------
 */
syscall	sendb(
	  pid32		pid,		/* ID of recipient process	*/
	  umsg32	msg		/* contents of message		*/
	)
{
	intmask	mask;			/* saved interrupt mask		*/
	struct	procent *prptr;		/* ptr to process' table entry	*/

	mask = disable();
	if (isbadpid(pid)) {
		restore(mask);
		return SYSERR;
	}

	prptr = &proctab[pid];
	if ((prptr->prstate == PR_FREE)) {
		restore(mask);
		return SYSERR;
	}
	
	if(prptr->prhasmsg)
	{
		struct procent *sendptr = &proctab[currpid];
		sendptr->sndmsg = msg;
		sendptr->sndflag = TRUE;
		sendptr->prstate = PR_SND;

		enqueue(currpid, prptr->msgqueue);
		resched();
	}
	else
	{
		prptr->prmsg = msg;		/* deliver message		*/
		prptr->prhasmsg = TRUE;		/* indicate message is waiting	*/
	}
	/* If recipient waiting or in timed-wait make it ready */

	if (prptr->prstate == PR_RECV) {
		ready(pid, RESCHED_YES);
	} else if (prptr->prstate == PR_RECTIM) {
		unsleep(pid);
		ready(pid, RESCHED_YES);
	}
	restore(mask);		/* restore interrupts */
	return OK;
}
