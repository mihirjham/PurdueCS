#include<stdio.h>
#include "words.h"

int main()
{
	char msg[] = "hello my";
	printf("%s\n", msg);
	matchAndEraseWord(msg, 0, 5, "hello");
	printf("%s\n", msg);
	matchAndEraseWord(msg,6,2,"my");
	printf("%s\n",msg);
}
