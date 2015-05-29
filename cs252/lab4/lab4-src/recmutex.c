#include <stdio.h>
#include <pthread.h>
#include <errno.h>
#include "recmutex.h"
#include <stdlib.h>

int recursive_mutex_init (recursive_mutex_t *mu)
{
	//mu = (recursive_mutex *)malloc(sizeof(recursive_mutex));
	
	//if(mu == NULL)
	//	return -1;
	//else
	//{
		int errMutex = pthread_mutex_init(&mu->mutex, NULL);
		if(errMutex)
			return -1;
		
		int errCond = pthread_cond_init(&mu->cond, NULL);
		if(errCond)
			return -1;

		mu->owner = -1;
		mu->count = 0;
		//Never really use wait_count
		mu->wait_count = 0;
		return 0;
	//}
}

int recursive_mutex_destroy (recursive_mutex_t *mu)
{
	int errMutex = pthread_mutex_destroy(&mu->mutex);
	if(errMutex)
		return -1;
	
	int errCond = pthread_cond_destroy(&mu->cond);
	if(errCond)
		return -1;
	
	
	return 0;
}

int recursive_mutex_lock (recursive_mutex_t *mu)
{
	pthread_mutex_lock(&mu->mutex);

	if(mu->count < 0)
	{
		pthread_mutex_unlock(&mu->mutex);
		return -1;
	}

	if(mu->count == 0)
	{
		mu->owner = pthread_self();
		mu->count = mu->count + 1;
	}
	else if(mu->count > 0 && pthread_equal(mu->owner, pthread_self()) != 0)
	{	
		mu->count = mu->count+1;
		
	}
	else if(mu->count > 0 && pthread_equal(mu->owner, pthread_self()) == 0)
	{
		while(mu->count != 0)
		{
			pthread_cond_wait(&mu->cond, &mu->mutex);
		}
		
		mu->owner = pthread_self();
		mu->count = mu->count + 1;
	}
	
	pthread_mutex_unlock(&mu->mutex);
	return 0;
}

int recursive_mutex_unlock (recursive_mutex_t *mu)
{
	pthread_mutex_lock(&mu->mutex);

	if(mu->count <= 0)
	{
		pthread_mutex_unlock(&mu->mutex);
		return -1;
	}

	mu->count = mu->count - 1;
	pthread_cond_signal(&mu->cond);
	pthread_mutex_unlock(&mu->mutex);
	return 0;
}
