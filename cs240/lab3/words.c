#include <stdlib.h>
#include <stdio.h>
#include "utils.h"
#include "words.h"

/**
 * Read one line from standard input into {buf}. \n will be put in {buf} too
 * if there is one. Returns the length of the message. Return EOF if there are
 * no more lines to read.
 */
int readMsg(char *buf) {
  /*  FILL IN HERE  */
 
 int c;
 int nc;
 
 for(nc=0; (((c = getchar()) != EOF) && (c != '\n')); ++nc)
 {	
 	*(buf+nc) =c;
 //	buf++;
 }
 
 if(c == '\n')
 {
 	*(buf+nc) =c;
	++nc;
 }
 
 if(c != EOF)
 	return nc;
 else
 	return EOF;
 
// abort(); // Please remove the abort() when you implement this function
}

/**
 * Starting from {prevEnd}, this function returns index of the 
 * first char of the next word in the {str} array. {len} is the 
 * length of the {str} array.  
 * When calling this function {prevEnd} should ideally contain the 
 * index of the first space following the previous word. 
 * If calling for the first time, {prevEnd} should be zero.
 *
 * If no word was found following {prevEnd}, this function returns -1.
 */
int getNextWordIndex(char *str, int len, int prevEnd) {
  /*  FILL IN HERE  */
 
 int wordIndex = prevEnd;

 while(((getCharType(*(str + wordIndex)) == 2) || (getCharType(*(str + wordIndex)) == 3)) && (wordIndex < len))
 {
	wordIndex++;
 }
 
 if((getCharType(*(str+wordIndex)) == 0) ||( getCharType(*(str+wordIndex)) == 1))
 	return wordIndex;
 else
 	return -1;


 // abort(); // Please remove the abort() when you implement this function
}

/**
 * Check if the word starting at index start of length wLen is the same 
 * as keyword. If yes, replace chars in that word with white spaces.
 */
void matchAndEraseWord(char *str, int start, int wLen, char *keyword) {
  /*  FILL IN HERE  */
  
   int i = 0;

   while(*(keyword + i) != '\0')
   {
   	i++;
   }

  if(i == wLen)
  {
  	if(strNCmp(str+start, keyword, wLen))
  		replaceChar(str+start, ' ', wLen);
  }
 // abort(); // Please remove the abort() when you implement this function
}

/*
 * Convert the alphabet in string {str} to lower case.
 * {len} is the length of the string.
 */
void lowerStringCase(char *str, int len) {
  /*  FILL IN HERE  */
  
  int i;

  for(i=0; i < len; i++)
  {
  	*(str+i) = lowerCaseOf(*(str+i));
  }
  //abort(); // Please remove the abort() when you implement this function
}
/**
 * Return the length of the word at {start} in {str} string.
 * {strLen} is the length of {str} array.
 */
int wordLen(char *str, int strLen, int start){
  /* FILL IN HERE */
  
  int wLen=0;
  
  while(((getCharType(*(str+start+wLen)) == 0) || (getCharType(*(str+start+wLen)) == 1)) && (*(str+start+wLen) != EOF))
  {  
  	++wLen;
  }

  return wLen;

  //abort(); // Please remove the abort() when you implement this function
}
