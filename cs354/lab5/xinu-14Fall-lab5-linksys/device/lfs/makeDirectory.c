#include <xinu.h>

bool8 strcmp(char *, char *);
void strcpy(char *, char *);

int32 makeDirectory(char *name)
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

	struct lflcblk *parentCblk = &lfltab[Nlfl+1];
	struct lflcblk *grandParentCblk = &lfltab[Nlfl];
	struct dentry parentDevPtr;
	struct dentry grandParentDevPtr;

	wait(Lf_data.lf_mutex);

	if(Lf_data.lf_dirpresent == FALSE)
	{	
		kprintf("Initialize rootDir\r\n");
		struct lfdir rootDir;
		if(read(Lf_data.lf_dskdev, (char *)&rootDir, 0) == SYSERR)
		{
			kprintf("Failed to read root\r\n");
			signal(Lf_data.lf_mutex);
			return SYSERR;
		}

		Lf_data.lf_dir = rootDir;
		Lf_data.lf_dirpresent = TRUE;
		Lf_data.lf_dirdirty = FALSE;
	}

	signal(Lf_data.lf_mutex);

	kprintf("rootDir initialized\r\n");

	parentCblk->lfstate = LF_FREE;
	parentCblk->lfpos = 0;
	parentCblk->lfinum = LF_INULL;
	parentCblk->lfdnum = LF_DNULL;
	parentCblk->lfbyte = &parentCblk->lfdblock[LF_BLKSIZ];
	parentCblk->lfibdirty = FALSE;
	parentCblk->lfdbdirty = FALSE;
	parentCblk->size = -1;
	parentCblk->firstId = LF_INULL;
	memset((char *)parentCblk->path, NULLCH, LF_NUM_DIR_ENT*LF_NAME_LEN);
	parentCblk->depth = -1;

	grandParentCblk->lfstate = LF_FREE;
	grandParentCblk->lfpos = 0;
	grandParentCblk->lfinum = LF_INULL;
	grandParentCblk->lfdnum = LF_DNULL;
	grandParentCblk->lfbyte = &parentCblk->lfdblock[LF_BLKSIZ];
	grandParentCblk->lfibdirty = FALSE;
	grandParentCblk->lfdbdirty = FALSE;
	grandParentCblk->size = -1;
	grandParentCblk->firstId = LF_INULL;
	memset((char *)grandParentCblk->path, NULLCH, LF_NUM_DIR_ENT*LF_NAME_LEN);
	grandParentCblk->depth = -1;

	parentCblk->lfstate = LF_USED;
	parentCblk->size = Lf_data.lf_dir.lfd_size;
	parentCblk->firstId = Lf_data.lf_dir.lfd_ilist;

	int currentDepth = 0;

	struct ldentry currentEntry;
	struct ldentry *entry = &currentEntry;
	parentDevPtr.dvminor = Nlfl+1;
	grandParentDevPtr.dvminor = Nlfl;

	kprintf("going into loop1\r\n");
	while(currentDepth < depth-1 && lflRead(&parentDevPtr, (char *)entry, sizeof(struct ldentry)) == sizeof(struct ldentry))
	{
		kprintf("in loop1\r\n");
		if(strcmp(entry->ld_name, tokens[currentDepth]) && entry->isUsed)
		{
			if(entry->type != LF_TYPE_DIR)
			{
				kprintf("%s is a file, not a dir\r\n", entry->ld_name);
				return SYSERR;
			}
			
			memcpy(grandParentCblk, parentCblk, sizeof(struct lflcblk));

			parentCblk->lfstate = LF_FREE;
			parentCblk->lfpos = 0;
			parentCblk->lfinum = LF_INULL;
			parentCblk->lfdnum = LF_DNULL;
			parentCblk->lfbyte = &parentCblk->lfdblock[LF_BLKSIZ];
			parentCblk->lfibdirty = FALSE;
			parentCblk->lfdbdirty = FALSE;
			parentCblk->size = -1;
			parentCblk->firstId = LF_INULL;
			memset((char *)parentCblk->path, NULLCH, LF_NUM_DIR_ENT*LF_NAME_LEN);
			parentCblk->depth = -1;

			parentCblk->lfstate = LF_USED;
			parentCblk->size = entry->ld_size;
			parentCblk->firstId = entry->ld_ilist;
			currentDepth++;
		}
	}
	kprintf("exited loop\r\n");

	if(depth-1 != currentDepth)
	{
		return SYSERR;
	}

	parentCblk = &lfltab[Nlfl+1];
	grandParentCblk = &lfltab[Nlfl];
	uint32 replacePos = 0;
	bool8 isRPosInitialized = 0;
	parentDevPtr.dvminor = Nlfl+1;
	grandParentDevPtr.dvminor = Nlfl;

	kprintf("going into loop2\r\n");
	while(lflRead(&parentDevPtr, (char *)entry, sizeof(struct ldentry)) == sizeof(struct ldentry))
	{
		kprintf("in loop2\r\n");
		if(!entry->isUsed)
		{
			if(!isRPosInitialized)
			{
				replacePos = parentCblk->lfpos - sizeof(struct ldentry);
				isRPosInitialized = 1;
			}
			continue;
		}

		if(strcmp(entry->ld_name, tokens[depth-1]) && entry->isUsed)
		{
			if(entry->type == LF_TYPE_DIR)
			{
				parentCblk->lfstate = LF_FREE;
				grandParentCblk->lfstate = LF_FREE;
				return SYSERR;
			}
		}
	}
	kprintf("exited loop2\r\n");

	if(isRPosInitialized)
	{
		if(lflSeek(&parentDevPtr, replacePos) == SYSERR)
		{
			kprintf("Seek failed\r\n");
		}
	}

    entry->ld_size = 0;
   	entry->ld_ilist = LF_INULL;
    entry->type = LF_TYPE_DIR;
    entry->isUsed = (bool8)1;
    strcpy(entry->ld_name, tokens[depth-1]);

    if(lflWrite(&parentDevPtr, (char *)entry, sizeof(struct ldentry)) == SYSERR)
	{	
   		parentCblk->lfstate = LF_FREE;
        	grandParentCblk->lfstate = LF_FREE;
         	return SYSERR;
    	}

    if(lfflush(parentCblk) == SYSERR)
	{
		parentCblk->lfstate = LF_FREE;
		grandParentCblk->lfstate = LF_FREE;
		return SYSERR;
	}

	if(isRPosInitialized)
	{
		parentCblk->lfstate = LF_FREE;
		grandParentCblk->lfstate = LF_FREE;
		return OK;
	}

	if(grandParentCblk->lfstate == LF_FREE)
	{
		parentCblk->lfstate = LF_FREE;
		wait(Lf_data.lf_mutex);
		Lf_data.lf_dir.lfd_size += sizeof(struct ldentry);
		Lf_data.lf_dirdirty = TRUE;
		signal(Lf_data.lf_mutex);
		return OK;
	}

	struct ldentry grandParentDirEntry;

	lflSeek(&grandParentDevPtr, grandParentCblk->lfpos - sizeof(struct ldentry));

	if(lflRead(&grandParentDevPtr, (char *)&grandParentDirEntry, sizeof(struct ldentry)) == SYSERR)
	{
		parentCblk->lfstate = LF_FREE;
		grandParentCblk->lfstate = LF_FREE;
		return SYSERR;
	}

	grandParentDirEntry.ld_size += sizeof(struct ldentry);
	grandParentDirEntry.ld_ilist = parentCblk->firstId;

	lflSeek(&grandParentDevPtr, grandParentCblk->lfpos - sizeof(struct ldentry));

	if(lflWrite(&grandParentDevPtr,(char*)&grandParentDirEntry,sizeof(struct ldentry)) == SYSERR)
	{

		parentCblk->lfstate = LF_FREE;
		grandParentCblk->lfstate = LF_FREE;
		return SYSERR;
	}

	if(lfflush(grandParentCblk) == SYSERR)
	{
		parentCblk->lfstate = LF_FREE;
		grandParentCblk->lfstate = LF_FREE;
			
		return SYSERR;
	}

	parentCblk->lfstate = LF_FREE;
	grandParentCblk->lfstate = LF_FREE;
	
	kprintf("Exiting makeDir\r\n");
	return OK;
}

/*bool8 strcmp(char *str1, char *str2)
{
	while(*str1 != '\0' && *str1 == *str2)
	{
		str1++;
		str2++;
	}

	return (*str1 == *str2) && (*str1 == '\0');
}

void strcpy(char *str1, char *str2)
{
	while(*str2 != '\0')
	{
		*str1 = *str2;
		str1++;
		str2++;
	}

	*str1 = '\0';
}*/
