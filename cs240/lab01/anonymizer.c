#include<stdio.h>

int isAlpha(int);
void anonymize(char[], int);

int main()
{
	int c;
	int printPos=0;
	int nc = 0;
	int i = 0;
	int x;
	int atPos[10];
	char msg[140];
	
	while(1)
	{
		c = getchar();
		msg[nc] = c;
		
		if(c == EOF || nc > 140)
			break;

		if(c == '@')
		{
			atPos[i++] = nc;
		}
						
		//putchar(msg[nc]);
		++nc;
	}
	for(x=0; x<i; x++)
	{
		anonymize(msg, atPos[x]);
	}
		
	/*if((isAlpha(msg[atPos-1])) && (isAlpha(msg[atPos+1])))
	{
		while(isAlpha(msg[--atPos]))
		{
			//userName[i] = msg[atPos];
			//putchar(msg[atPos]);
			msg[atPos] = 'X';
		}
	}*/
		
	msg[nc] = '\0';
	printf("%s", msg);
	/*while(!(msg[printPos] == EOF))
	{	
		putchar(msg[printPos]);		
		++printPos;
	}*/
	
	return 0;
}

int isAlpha(int c)
{
	return ((c>='A' && c<='Z') || (c>='a' && c<='z') || (c>='0' && c<='9'));
}

void anonymize(char msg[], int pos)
{
	if((isAlpha(msg[pos-1])) && (isAlpha(msg[pos+1])))
	{
		while(isAlpha(msg[--pos]))
		{
			msg[pos] = 'X';
		}
	}
}


		
