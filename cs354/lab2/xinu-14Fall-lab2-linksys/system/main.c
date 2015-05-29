/*  main.c  - main */

#include <xinu.h>
#include <stdio.h>

/************************************************************************/
/*									*/
/* main - main program for testing Xinu					*/
/*									*/
/************************************************************************/

void read_checker(pipid32 rpip){
	char buf[1000];
	sleep(1);
	while(1){
		kprintf("Process try to read from pip %d\n\r", rpip);

		int len = pipread_block(rpip, buf, 500);
		kprintf("read %d bytes\n\r", len);
	
	}

	while(1){
		sleep(1);
	}
}

void write_checker(pipid32 wpip){
	char buf[1000];
	int i;
	for(i=0; i<1000; i++) {
		buf[i] = 'a';
	}
	sleep(1);
	int wlen = 0;
	while(wlen<1000){
		kprintf("Process try to write to pip %d\n\r", wpip);

		int len = pipwrite_block(wpip, &buf[wlen], 100);
		kprintf("write %d bytes\n\r", len);
		wlen += len;
	}

	while(1){
		sleep(1);
	}
}

int main(int argc, char **argv)
{
	//umsg32 retval;

	/* Creating a shell process */

	/*resume(create(shell, 4096, 1, "shell", 1, CONSOLE));

	retval = recvclr();
	while (TRUE) {
		retval = receive();
		kprintf("\n\n\rMain process recreating shell\n\n\r");
		resume(create(shell, 4096, 1, "shell", 1, CONSOLE));
	}*/
	
	pipid32 pip1 = pipcreate();

	pid32 pr01 = create( write_checker, 2000, 30, "Pipe writer", 1, pip1);
	pid32 pr02 = create( read_checker, 2000, 30, "Pipe reader", 1, pip1);

	pipconnect(pip1, pr01, pr02);

	resume(pr01);
	resume(pr02);

	sleep(10);

	kill(pr01);
	kill(pr02);
	
	pipdisconnect(pip1);
	pipdelete(pip1);
	
	return OK;
}
