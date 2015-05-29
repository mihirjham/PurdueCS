#include <xinu.h>

int32 listdirh(did32 diskdev, char *name)
{
	char tokens[LF_NUM_DIR_ENT][LF_NAME_LEN];
	int depth = 0;
	int32 i = 0;

	if(name[i] == '/' && name[i] == '\0')
	{
		tokens[0][0] = '/';
		tokens[0][1] = '\0';
		depth = 1;
	}
	else
	{	
		int nameIndex = 0;
		while(depth < LF_NUM_DIR_ENT)	
		{
			if(name[nameIndex] == '/')
			{
				nameIndex++;
			}
			
			int tokenIndex = 0;
			while(name[nameIndex] != '/' && name[nameIndex] != '\0')
			{
				tokens[depth][tokenIndex++] = name[nameIndex++];
			}
			tokens[depth][tokenIndex] = '\0';
			depth++;
			
			if(name[nameIndex] == '\0')
				break;

			if(name[nameIndex] == '/' && name[nameIndex+1] == '\0')
				break;
		}
	}

	
}