#include<stdio.h>

void purify(char[], char[],int);
int isLower(int);
int isUpper(int);
int isLetter(int);

int main()
{
	int c;
	char msg[140];
	char keywords[5][5] = {"heck", "crap", "suck", "bull", "drat"};
	int nc = 0;

	while(1)
	{
		c = getchar();
		msg[nc] = c;

		if(c == EOF)
		{
			break;
		}
		
		++nc;
	}
	
	int i;

	for(i=0; i<5; i++)
	{
		purify(msg, keywords[i],nc);
	}
	
	msg[nc] = '\0';
	printf("%s",msg);
}

void purify(char msg[], char keywords[], int nc)
{
	int pos = 0;	
	do
	{
		int mPos = pos;
		int kPos=0;
		int tempPos;
		int tempPosStart;
		int breakStatus = 0;

		while(msg[mPos] != keywords[kPos])
		{
			breakStatus = 1;
			//++mPos;
			break;
		}

		while((msg[mPos] == keywords[kPos]) && !breakStatus)
		{
			mPos++;
			kPos++;
		}
		
		tempPosStart = mPos-4;
	
		if((isLower(msg[mPos]) || isUpper(msg[mPos])) && !breakStatus)
		{
			mPos++;
			kPos=0;
		}
		else
		{
			int i=0;
			tempPos = mPos;

			if((!(isLetter(msg[tempPos])) && !(isLetter(msg[tempPos-5]))) && !breakStatus)
			{
				while(kPos<4)
				{
					msg[--tempPos] = 'X';
					kPos++;
				}
			}
			kPos = 0;
			//pos = mPos;
		}
	}while(++pos <  nc);			
}

int isLower(int c)
{
	return (c >= 'a' && c <= 'z');
}

int isUpper(int c)
{
	return(c >= 'A' && c <= 'Z');
}

int isLetter(int c)
{
	return((c >= 'a' && c <= 'z') || (c>= 'A' && c <= 'Z'));
}

	
