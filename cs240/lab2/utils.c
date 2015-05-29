#include <stdlib.h>

/**
 * Test if {ch} is a letter (a - z or A - Z).
 * Return 0 for letter, 1 for digit, 2 for whitespace and 3 for any other character.
 */
int getCharType(char ch) {
  
  if((ch>='A' && ch<='Z') || (ch>='a' && ch<='z'))
  	return 0;
  else if(ch>='0' && ch<='9')
  	return 1;
  else if((ch == ' ') || (ch == '\t') || (ch == '\n'))
  	return 2;
  else
  	return 3;
  //abort();
}

/**
 * Return the lower case equivalent of {c} if it 
 * is in upper case. Return {c} if it is already 
 * in lower case.
 */
char lowerCaseOf(char c){
  if(c >= 'A' && c<= 'Z')
  	return c + 'a' - 'A';
  else
  	return c;
  //abort();
}

/**
 * Return the length of the zero terminated string {s}.
 * A null terminated string is an array of characters 
 * with a \0 character at the end.
 * The \0 character is not counted, e.g. the length of "abc" is 3.
 */
int strLen(char s[]) {
  int nc;

  for(nc=0; s[nc] != '\0'; ++nc);

  return nc;
  //abort();
}

/**
 * Compare the first {n} characters of string {s1} and {s2}.
 * If identical, return 1. Otherwise return 0.
 */
int strNCmp(char s1[], char s2[], int n) {
  
  int i;

  for(i=0; s1[i] == s2[i] && i<n; i++);
  
  if(i == n)
  	return 1;
  else
  	return 0;

 // abort();
}

/**
 * Replace every character of {s} with {c}.
 * {len} indicates the length of {s}.
 */
void replaceChar(char s[], char c, int len) {
  
  int i;

  for(i=0;i<len;i++)
  {
  	s[i] = c;
  }

 // abort();
}
