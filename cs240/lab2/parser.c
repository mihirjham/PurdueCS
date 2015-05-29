#include <stdio.h>
#include "words.h"

int main(int argc, char** argv) {
	char msg[SIZE];
	
	int len = readMsg(msg);
	if(len == EOF)
		msg[0] = '\0';

	while (len != EOF)
	{
		msg[len] = '\0';	

		int wordIndex = getNextWordIndex(msg, len, 0);
		
		do
		{
			int wordEnd = wordIndex;
			
			lowerStringCase(msg, len);	
			
			int wordLength = wordLen(msg, len, wordIndex);
			wordEnd += wordLength;

			if(msg[wordEnd] != ' ')
				wordEnd++;	
			int k;
			for(k=0; k<argc; k++)
			{
				matchAndEraseWord(msg, wordIndex, wordLength, argv[k]);
			}
			
			wordIndex = getNextWordIndex(msg, len, wordEnd);
		}while (wordIndex != -1); 
		
		printf("%s", msg);
		len = readMsg(msg);
		if(len == EOF)
			msg[0] = '\0';
	}
	return 0;
}
