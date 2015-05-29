#include<stdio.h>
#include "tweetIt.h"

int main()
{
	char c;
	char msg[140];
	int nc = 0;
	
	while((c = getchar()) != EOF && nc < 140)
	{
		msg[nc] = c;
		++nc;
	}
	
	msg[nc] = '\0';
	
	tweetIt(msg, nc-1);
}
