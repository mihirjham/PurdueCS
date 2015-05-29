#include <sys/types.h>

/*
 * Implement the following string procedures.
 *
 * Type "man strstr" to find what each of the functions should do.
 *
 * For example, mystrcpy should do the same as strcpy.
 *
 * IMPORTANT: DO NOT use predefined string functions.
 */

char *mystrcpy(char * s1, const char * s2)
{
  /* Complete procedure */
	while(*s2 != '\0')
	{
		*s1 = *s2;
		s1++;
		s2++;
	}
	*s1 = '\0';
	return s1;
}

size_t mystrlen(const char *s)
{
  /* Complete procedure */
	
	size_t len = 0;
	while(*s != '\0')
	{
		len++;
		s++;
	}
	
	return len;
}

char *mystrdup(const char *s1)
{
  /* Complete procedure */
	char *str = (char*)malloc(mystrlen(s1)+1);
	mystrcpy(str, s1);
	return str;
}

char *mystrcat(char * s1, const char * s2)
{
  /* Complete procedure */
	while(*s1 != '\0')
	{
		s1++;
	}
	while(*s2 != '\0')
	{
		*s1 = *s2;
		s1++;
		s2++;
	}
	*s1 = '\0';

	return s1;
}

char *mystrstr(char * s1, const char * s2)
{
  /* Complete procedure */
	if(!*s2)
		return s1;
	
	char *p1 = s1;
	while(*p1)
	{
		char *p1Begin = p1;
		char *p2 = s2;

		while(*p1 && *p2 && *p1 == *p2)
		{
			p1++;
			p2++;
		}
		
		if(!*p2)
			return p1Begin;
		
		p1 = p1Begin + 1;
	}
	
	return '\0';
}
int mystrcmp(const char *s1, const char *s2) 
{
  /* Complete procedure */
	int retval = 0;
	while(*s1 == *s2)
	{
		s1++;
		s2++;
		if(*s1 == '\0' && *s2 == '\0')
			break;
	}
	
	if(*s1 < *s2)
		retval = *s1 - *s2;
	
	if(*s1 > *s2)
		retval = *s1 - *s2;
	
	return retval;
}

