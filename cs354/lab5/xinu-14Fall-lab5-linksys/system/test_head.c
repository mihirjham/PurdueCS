#include <xinu.h>

void test_head()
{
	kprintf("Opening filesystem\r\n");
	int diskId = open(RDISK, "XinuDisk", "rw");
	if(diskId == SYSERR)
	{
		kprintf("Error opening file system\r\n");
		return;
	}
	
	int i;
	for(i = 0; i < Nlfl; i++)
	{
		if(lfltab[i].lfstate == LF_USED)
		{
			kprintf("Files open on this system, cannot format\r\n");
			return;
		}
	}

	kprintf("Opened filesystem\r\nInitializing filesytem\r\n");	
	if(lfscreate(Lf_data.lf_dskdev, 100, 500*512) == SYSERR)
	{
		kprintf("Error initializijg file system\r\n");
		return;
	}
	
	kprintf("lfscreate passed\r\nCreating new file\r\n");
	char *fileName = "/HelloWorld";
	char *write_buff = "The story of a family in Waco, Texas in 1956. The eldest son witnesses the loss of innocence and struggles with his parents' conflicting teachings.";

	int fd = open(LFILESYS, fileName, "rwn");
	

	if(fd == SYSERR)
	{
		kprintf("Error opening new file!\r\n");
		return;
	}
	
	kprintf("File opened\r\n");

	if(write(fd, write_buff, 147) == SYSERR)
	{
		kprintf("Error writing to file!\r\n");
		return;
	}
	
	kprintf("Information written to file\r\n");
	
	char buf[101];
	int retval = head(fd, buf);
	
	kprintf("Information read into buf\r\n");


	if(retval == SYSERR)
	{
		kprintf("Error in head!\r\n");
		return;
	}
	buf[retval] = '\0';

	kprintf("First %d characters of the file: %s\r\n", retval, buf);
	
	char *dirname = "/dir1";
	if(control(LFILESYS, LF_CTL_MKDIR, (int)dirname, 0) == SYSERR)
	{
		kprintf("mkdir failed\r\n");
		return;
	}

 	fileName = "/dir1/Hello";
	fd = open(LFILESYS, fileName, "rwn");

	if(fd == SYSERR)
	{
		kprintf("Error opening new file\r\n");
		return;
	}
	
	if(write(fd, write_buff, 147) == SYSERR)
	{
		kprintf("error writing to file\r\n");
		return;
	}
	
	char buf2[101];
	retval = head(fd, buf2);
	buf2[retval] = '\0';
	
	kprintf("First %d character of file2: %s\r\n", retval, buf2);
}
