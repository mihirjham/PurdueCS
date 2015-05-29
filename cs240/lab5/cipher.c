#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include "cipher.h"

union Cipher {
	struct Message {
		unsigned int sender;
		unsigned int receiver;
		char content[119]; //118 characters + Null ('\0') terminator
	} input;

	/* FILL THIS IN */
	char outputMessage[140];
};

unsigned int setInts(char *str, int start)
{
	return strtoul(str+start, NULL, 0);
}

char* setContent(char *str, int start)
{
	int i =start;
	int j;
	char* s = (char*) malloc(119 * sizeof(char));
	
	for(j=0; *(str+start+j) != '\0' && j < 118; j++)
	{
		*(s+j) = *(str+start+j);
	}
	*(s+j) = '\0';

	return s;
}

char* intToHex(unsigned int decimal, int offset)
{
	char *s = (char*) malloc(10*sizeof(char));
	unsigned int quotient = decimal;
	int i = 0;

	while(quotient != 0)
	{	
		unsigned int temp =(quotient+offset) % 16;

		if(temp < 10)
			temp = temp + 48;
		else
			temp = temp + 55;

		*(s+ i++) = temp;
		quotient = quotient/ 16;
	}

	if(decimal == 0)
	{
		unsigned int temp = (quotient+offset)%16;
		*(s + i++) = temp + 48;
		*(s + i++) = temp + 48;
	}

	*(s+i) = '\0';


	return s;
}

void sortEndian(char *str)
{
	int i;
	char temp;

	for(i=0; *(str+i) != '\0' && i<140; i=i+2)
	{
		temp = *(str+i);
		*(str+i) = *(str+i+1);
		*(str+i+1) = temp;
	}
}

void hexToChar(char* str, int offset)
{
	//int temp = (c + 16 - offset) % 16;
	int i;
	printf("%s\n", str);
	for(i = 0; *(str+i) != EOF && i<280; i++)
	{	
		int temp = (*(str+i) + 16 - offset) % 16;
		
		if(temp < 10)
			temp = temp + 48;
		else
			temp = temp + 55;

		*(str+i) = (char)temp;
	}
}
	

int main()
{
	char msg[140];
	int nc = 0;
	int c;
	while(1)
	{
		c = getchar();
		if(c == EOF || nc > 140)
			break;
		msg[nc] = c;
		++nc;
	}

	msg[nc-1] = '\0';
	int offset = 0;

	unsigned int sender = setInts(msg, 0);
	unsigned int receiver = setInts(msg,11);
	char *content = setContent(msg, 22);
	//printf("%d\n%d\n", sender, receiver);
	//printf("%s\n", content);

	//char *senderHex = intToHex(1234567890, 1);
	//char *testerHex = intToHex('\0',0);

	//	printf("%s\n", senderHex);
	//	printf("%s\n", testerHex);

	char hexMessage[280];

	strcpy(hexMessage, intToHex(sender, offset));
	strcat(hexMessage, intToHex(receiver, offset));
	int i;
	for(i=22; msg[i] != '\0' && i < 140; i++)
	{
		strcat(hexMessage, intToHex(msg[i] , offset));
	//	printf("%s\n", hexMessage);
	}
	//printf("%s\n", hexMessage);
	strcat(hexMessage, intToHex(msg[i], offset));
	sortEndian(hexMessage);
	printf("%s\n",hexMessage);
	
//	char* encryptedTweet = (char*) malloc(280*sizeof(char));
//	encrypt(msg, offset, encryptedTweet);
//	printf("%s\n", encryptedTweet);
	
	
	return 0;
}


/* Union + Cesar Cipher function
 * ------------------------------
 *
 * encrypt function takes as input a tweet and an offset, and produces a
 * encrypted message. The input to the function is obtained from a tweet. Each
 * tweet is a message that is in the following format:
 *
 * SSSSSSSSSS RRRRRRRRRR MMMMM(...upto 118 characters)
 * [---10---] [---10---] [-----------118-------------]
 * [---------------------140-------------------------]
 *
 * It starts with the sender phone number, followed by a space, followed by the
 * receiver phone number, followed by a space, and upto 118 characters of
 * message. The maximum length of such a message will be 140 characters. The
 * phone numbers start with either 1, 2 or 3 (restricted so that the number
 * will fit in an unsigned int).
 *
 * Encryption is achieved through the union Cipher described above. The idea is
 * to fill in the input structure, and read from the output structure (to be
 * defined by you). This will produce a HEX equivalent of the input message.
 * Finally, the message is shifted (Cesar cipher) by (offset % 16) in the
 * Hex space.
 *
 * The function returns the result in the string encryptedTweet, which contains
 * the encrypted message.
 */

void encrypt (char* tweet, int offset, char* encryptedTweet) {
	union Cipher c;
	/* FILL THIS IN */
	c.input.sender = setInts(tweet, 0);
	
	if(c.input.sender == 0)
	{
	//	strncpy(c.input.content, setContent(tweet, 0), sizeof(c.input.content));
		//strcpy(c.input.content, '\0');
		strcpy(c.outputMessage, intToHex('\0', offset));
	}
	else
	{
		strcpy(c.outputMessage, intToHex(c.input.sender,offset));
		
		c.input.receiver = setInts(tweet, 11);
		strcat(c.outputMessage, intToHex(c.input.receiver, offset));

		strncpy(c.input.content, setContent(tweet, 22), sizeof(c.input.content));

		int i;

		for(i=22; *(tweet+i) != '\0' && i < 140; i++)
		{
			strcat(c.outputMessage, intToHex(*(tweet+i) , offset));
		}

		strcat(c.outputMessage, intToHex(*(tweet+i), offset));
	}

	sortEndian(c.outputMessage);
	
	strncpy(encryptedTweet, c.outputMessage, sizeof(c.outputMessage));
	//abort (); //DELETE THIS LINE
}


/* Decipher function
 * -----------------
 *
 * Decrypt function is given the encrypted tweet and the offset (used for
 * negative shift in the Cesar cipher). The function returns the result in the
 * tweet string.
 */

void decrypt (char* encryptedTweet, int offset, char* tweet) {
	union Cipher c;
	/* FILL THIS IN */
//	abort (); //DELETE THIS LINE
}
