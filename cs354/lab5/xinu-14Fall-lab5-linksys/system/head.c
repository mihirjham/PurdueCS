#include <xinu.h>

int head(int file_descriptor, char *buff)
{
	seek(file_descriptor,0);
	int ret = read(file_descriptor, buff, 100);
	
	if(ret == EOF)
		ret = 0;
	return ret;
}
