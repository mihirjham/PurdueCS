/* receiveb.c - receiveb */

#include <xinu.h>

/*------------------------------------------------------------------------
 *  receive  -  wait for a message and return the message to the caller
 *------------------------------------------------------------------------
 */
umsg32	receiveb(void)
{
	intmask	mask;			/* saved interrupt mask		*/
	struct	procent *prptr;		/* ptr to process' table entry	*/
	umsg32	msg;			/* message to return		*/

	mask = disable();
	prptr = &proctab[currpid];
	if (prptr->prhasmsg == FALSE) {
		
		if(!isempty(prptr->msgqueue))
		{
			pid32 senderpid = dequeue(prptr->msgqueue);
			struct procent *sender = &proctab[senderpid];

			msg = sender->sndmsg;
			sender->sndflag = FALSE;
			ready(senderpid, RESCHED_YES);
		}
		else
		{
			prptr->prstate = PR_RECV;
			resched();		/* block until message arrives	*/
		}
	}
	msg = prptr->prmsg;		/* retrieve message		*/
	prptr->prhasmsg = FALSE;	/* reset message flag		*/
	restore(mask);
	return msg;
}
