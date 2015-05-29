/* lfsOpen.c  -  lfsOpen */

#include <xinu.h>

/*------------------------------------------------------------------------
 * lfsOpen - open a file and allocate a local file pseudo-device
 *------------------------------------------------------------------------
 */

bool8 strcmp(char *, char *);
void strcpy(char *, char *);

devcall	lfsOpen (
	 struct	dentry	*devptr,	/* entry in device switch table	*/
	 char	*name,			/* name of file to open		*/
	 char	*mode			/* mode chars: 'r' 'w' 'o' 'n'	*/
	)
{
	did32		lfnext;		/* minor number of an unused	*/
	struct	lflcblk	*lfptr;		/* ptr to open file table entry	*/
	int32	mbits;			/* mode bits			*/
	
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
	
	if(depth == 1 && tokens[0][0] == '/' && tokens[0][1] == '\0')
	{
		kprintf("Can't open root\r\n");
		return SYSERR;
	}
	
	/* Parse mode argument and convert to binary */

	mbits = lfgetmode(mode);
	if (mbits == SYSERR) {
		return SYSERR;
	}

	/* If named file is already open, return SYSERR */

	lfnext = SYSERR;
	
	for(i = 0; i < Nlfl; i++)
	{
		lfptr = &lfltab[i];
		if(lfptr->lfstate == LF_FREE)
		{
			if(lfnext == SYSERR)
			{
				lfnext = i;
			}
		}
		else
		{
			int j = 0;
			if(lfptr->depth != depth)
			{
				continue;
			}
			
			for(j = 0; j < depth; j++)
			{
				if(!strcmp(lfptr->path[j], tokens[j]))
					break;
			}
			if(j == depth)
			{
				kprintf("File is already open\r\n");
				return SYSERR;
			}
		}
	}

	if (lfnext == SYSERR) {	/* no slave file devices are available	*/
		return SYSERR;
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
		
		kprintf("rootDir size = %d\r\n", rootDir.lfd_size);
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
	
	kprintf("Entering loop\r\n");
	kprintf("sizeof(struct ldentry) = %d\r\n", sizeof(struct ldentry));
	while(currentDepth < depth-1 && lflRead(&parentDevPtr, (char *)entry, sizeof(struct ldentry)) == sizeof(struct ldentry))
	{
		kprintf("entry: %s\r\n", entry->ld_name);
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

	if(depth-1 != currentDepth)
	{
		kprintf("moveToDir failed\r\n");
		return SYSERR;
	}

	struct ldentry fileInfo;
	struct ldentry *currentFile = &fileInfo;

	parentCblk = &lfltab[Nlfl+1];
	grandParentCblk = &lfltab[Nlfl];
	parentDevPtr.dvminor = Nlfl+1;
	grandParentDevPtr.dvminor = Nlfl;

	uint32 replacePos = 0;
	bool8 isRPosInitialized = 0;
	bool8 fileNotFound = 1;
	
	kprintf("Reading keep on reading entries until match\r\n");
	while(lflRead(&parentDevPtr, (char *)currentFile, sizeof(struct ldentry)) == sizeof(struct ldentry))
	{
		kprintf("In loop\r\n");
		if(!currentFile->isUsed)
		{
			if(!isRPosInitialized)
			{
				replacePos = parentCblk->lfpos - sizeof(struct ldentry);
				isRPosInitialized = 1;
			}
			continue;
		}

		if(strcmp(currentFile->ld_name, tokens[depth-1]) && currentFile->isUsed)
		{
			if(currentFile->type == LF_TYPE_DIR)
			{
				parentCblk->lfstate = LF_FREE;
				grandParentCblk->lfstate = LF_FREE;
				return SYSERR;
			}

			if(mbits & LF_MODE_N)
			{
				parentCblk->lfstate = LF_FREE;
				grandParentCblk->lfstate = LF_FREE;
				return SYSERR;
			}

			parentCblk->lfstate = LF_FREE;
			grandParentCblk->lfstate = LF_FREE;
			fileNotFound = 0;
		}
	}

	if(fileNotFound)
	{
		kprintf("File not found\r\n");
		if(mbits & LF_MODE_O)
		{
			parentCblk->lfstate = LF_FREE;
			grandParentCblk->lfstate = LF_FREE;
			return SYSERR;
		}

		if(isRPosInitialized)
		{
			if(lflSeek(&parentDevPtr, replacePos) == SYSERR)
			{
				kprintf("Seek failed\r\n");
			}
		}
		
		kprintf("Creating a new file\r\n");
		currentFile->ld_size = 0;
		currentFile->ld_ilist = LF_INULL;
		currentFile->type = LF_TYPE_FILE;
		currentFile->isUsed = (bool8)1;
		strcpy(currentFile->ld_name, tokens[depth-1]);

		if(lflWrite(&parentDevPtr, (char *)currentFile, sizeof(struct ldentry)) == SYSERR)
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
			lfptr = &lfltab[lfnext];
			lfptr->lfstate = LF_USED;
			lfptr->lfmode = mbits & LF_MODE_RW;

			/* File starts at position 0 */

			lfptr->lfpos     = 0;

			/* Neither index block nor data block are initially valid	*/

			lfptr->lfinum    = LF_INULL;
			lfptr->lfdnum    = LF_DNULL;

			/* Initialize byte pointer to address beyond the end of the	*/
			/*	buffer (i.e., invalid pointer triggers setup)		*/

			lfptr->lfbyte = &lfptr->lfdblock[LF_BLKSIZ];
			lfptr->lfibdirty = FALSE;
			lfptr->lfdbdirty = FALSE;

			lfptr->size = fileInfo.ld_size;
			lfptr->firstId = fileInfo.ld_ilist;
			memcpy(lfptr->path,tokens,LF_NAME_LEN * LF_NUM_DIR_ENT);
			lfptr->depth = depth;

			return lfptr->lfdev;
		}
		else if(grandParentCblk->lfstate == LF_FREE)
		{
			parentCblk->lfstate = LF_FREE;
			wait(Lf_data.lf_mutex);
			Lf_data.lf_dir.lfd_size += sizeof(struct ldentry);
			Lf_data.lf_dirdirty = TRUE;
			signal(Lf_data.lf_mutex);

			lfptr = &lfltab[lfnext];
			lfptr->lfstate = LF_USED;
			lfptr->lfmode = mbits & LF_MODE_RW;

			/* File starts at position 0 */

			lfptr->lfpos     = 0;

			/* Neither index block nor data block are initially valid	*/

			lfptr->lfinum    = LF_INULL;
			lfptr->lfdnum    = LF_DNULL;

			/* Initialize byte pointer to address beyond the end of the	*/
			/*	buffer (i.e., invalid pointer triggers setup)		*/

			lfptr->lfbyte = &lfptr->lfdblock[LF_BLKSIZ];
			lfptr->lfibdirty = FALSE;
			lfptr->lfdbdirty = FALSE;

			lfptr->size = fileInfo.ld_size;
			lfptr->firstId = fileInfo.ld_ilist;
			memcpy(lfptr->path,tokens,LF_NAME_LEN * LF_NUM_DIR_ENT);
			lfptr->depth = depth;

			return lfptr->lfdev;
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

			lfptr = &lfltab[lfnext];
			lfptr->lfstate = LF_USED;
			lfptr->lfmode = mbits & LF_MODE_RW;

			/* File starts at position 0 */

			lfptr->lfpos     = 0;

			/* Neither index block nor data block are initially valid	*/

			lfptr->lfinum    = LF_INULL;
			lfptr->lfdnum    = LF_DNULL;

			/* Initialize byte pointer to address beyond the end of the	*/
			/*	buffer (i.e., invalid pointer triggers setup)		*/

			lfptr->lfbyte = &lfptr->lfdblock[LF_BLKSIZ];
			lfptr->lfibdirty = FALSE;
			lfptr->lfdbdirty = FALSE;

			lfptr->size = fileInfo.ld_size;
			lfptr->firstId = fileInfo.ld_ilist;
			memcpy(lfptr->path,tokens,LF_NAME_LEN * LF_NUM_DIR_ENT);
			lfptr->depth = depth;

			return lfptr->lfdev;

	}



	/* Initialize the local file pseudo-device */

	lfptr = &lfltab[lfnext];
	lfptr->lfstate = LF_USED;
	lfptr->lfmode = mbits & LF_MODE_RW;

	/* File starts at position 0 */

	lfptr->lfpos     = 0;

	/* Neither index block nor data block are initially valid	*/

	lfptr->lfinum    = LF_INULL;
	lfptr->lfdnum    = LF_DNULL;

	/* Initialize byte pointer to address beyond the end of the	*/
	/*	buffer (i.e., invalid pointer triggers setup)		*/

	lfptr->lfbyte = &lfptr->lfdblock[LF_BLKSIZ];
	lfptr->lfibdirty = FALSE;
	lfptr->lfdbdirty = FALSE;

	lfptr->size = fileInfo.ld_size;
	lfptr->firstId = fileInfo.ld_ilist;
	memcpy(lfptr->path,tokens,LF_NAME_LEN * LF_NUM_DIR_ENT);
	lfptr->depth = depth;


	return lfptr->lfdev;
}

bool8 strcmp(char *str1, char *str2)
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
}
