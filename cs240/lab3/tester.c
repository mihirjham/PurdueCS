#include <stdio.h>
#include "words.h"

int main()
{
	char msg[140];

	int len = readMsg(msg);
	int wordIndex = getNextWordIndex(msg, len, 0);
	do
	{
		printf("%d\n", wordIndex);
		int wordEnd = wordIndex;
		int wordLength = wordLen(msg, len, wordIndex);
		wordEnd += wordLength;

		if(msg[wordEnd] != ' ')
			wordEnd++;

		wordIndex = getNextWordIndex(msg, len, wordEnd);
	}while(wordIndex != -1);

	msg[len] = '\0';
	printf("%s", msg);
	printf("%d\n", len);

	len = readMsg(msg);
	wordIndex = getNextWordIndex(msg,len,0);

	do
	{
		printf("%d\n", wordIndex);
		int wordEnd = wordIndex;
		int wordLength = wordLen(msg, len, wordIndex);
		wordEnd += wordLength;

		if(msg[wordEnd] != ' ')
			wordEnd++;

		wordIndex = getNextWordIndex(msg, len, wordEnd);
	}while(wordIndex != -1);


	msg[len] = '\0';
	printf("%s", msg);
	printf("%d\n", len);
	

	len = readMsg(msg);
	wordIndex = getNextWordIndex(msg,len,0);
	msg[len] = '\0';
	printf("%d\n",wordIndex);
	printf("%s", msg);
	printf("%d\n",len);
	return 0;
}
