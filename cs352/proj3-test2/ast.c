#include "ast.h"
#include "HashTable.h"
#include <stdio.h>
#include <stdlib.h>

int lineNo = 1;

ASTNode* makeNode(ASTNodeType type, ASTNode* left, ASTNode* right, void *data, int linenumber)
{
	ASTNode *node = (ASTNode *)malloc(sizeof(ASTNode));
	node->type = type;
	node->left = left;
	node->right = right;
	node->data = data;
	node->linenumber = linenumber;

	return node;
}

void printAST(ASTNode *root)
{
	if(root)
	{
		if(root->type == Integer)
		{
			struct dataNode *data = root->data;
			printf("%d", data->intValue);
		}
		else if(root->type == String)
		{
			struct dataNode *data = root->data;
			if(!strcmp(data->strValue, "<br />"))
				printf("\n");
			else
				printf("%s", data->strValue);
		}
		else if(root->type == Boolean)
		{
			struct dataNode *data = root->data;
			if(data->booleanValue)
				printf("true");
			else
				printf("false");
		}
		else
		{
			printf("undefined");
		}
	}
}

ASTNode* walkAST(ASTNode *root)
{
	if(root)
	{
		if(root->type == Declare)
		{
			char *var = root->left->data;
			ASTNode *data = walkAST(root->right);
		   	
			if(isDefined(var))
				removeSubKey(var);
		
			struct dataNode *node;
			int ret = find(var, (void **)&node);
			if(ret && node->type == OBJECT_NODE)
			{
				removeSubKey(var);
			}
			
			if(data)
			{
				if(data->type == Object)
				{
					struct objectNode *p = data->data;
	
					while(p != NULL)
					{
						if(p->fieldName == NULL)
						{	
							p = p->next;
							continue;
						}
			
						char *str = (char *)malloc(strlen(var) + strlen(p->fieldName) + 2);
						strcpy(str, var);
						strcat(str, ".");
						strcat(str, p->fieldName);
							
						ASTNode *objectData = walkAST(p->data);
						insertItem(str, objectData->data);
						free(str);
						p = p->next;
					}
					
					node = (struct dataNode *)malloc(sizeof(struct dataNode));
					node->type = OBJECT_NODE;				
				}
				else if(data->type == Array)
				{
					struct arrayNode *p = data->data;

					while(p != NULL)
					{
						char *buff = (char *)malloc(sizeof(char) * 20);
						sprintf(buff, "%d", p->index);
						char *str = (char *)malloc(strlen(var) + strlen(buff) + 3);
						strcpy(str, var);
						strcat(str,"[");
						strcat(str, buff);
						strcat(str, "]");
						
						ASTNode *arrayData = walkAST(p->data);
						insertItem(str, arrayData->data);
						free(str);
						free(buff);
						p = p->next;
					}
					node = (struct dataNode *)malloc(sizeof(struct dataNode));
					node->type = ARRAY_NODE;
				}
				else				
					node = data->data;
			}			
			else
			{
				node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNINITIALIZED_NODE;
			}
			insertItem(var, node);
			setDefined(var);
		}
		else if(root->type == Assign)
		{
			char *var = root->left->data;
			if(strstr(var, ".") == NULL && strstr(var, "[") == NULL)
			{			
				struct dataNode *node;
				int ret = find(var, (void **)&node);
			
				if(ret && node->type == OBJECT_NODE || ret && node->type == ARRAY_NODE)
					removeSubKey(var);
				
				ASTNode *data = walkAST(root->right);
				if(!isDefined(var))
				{
					reportError(root->linenumber, UndeclaredViolation, var);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);			
				}
				//ASTNode *data = walkAST(root->right);
				node = data->data;
				insertItem(var, node);
				setDefined(var);
			}
			else if(strstr(var, ".") != NULL && strstr(var, "[") == NULL)
			{
				char *object = strdup(var);
				char *field;
				strtok_r(object, ".", &field);
				
				struct dataNode *node;
				int ret = find(object, (void **)&node);
				if(ret && node->type == OBJECT_NODE)
				{
					ASTNode *data = walkAST(root->right);
					node = data->data;
					insertItem(var, node);
				}
				else
				{
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
				}
				
				free(object);
			}
		}
		else if(root->type == ArrayAssign)
		{
			char *array = root->data;
			struct dataNode *node;
			
			int ret = find(array, (void **)&node);
			if(!ret)
			{
				reportError(root->linenumber, TypeViolation, NULL);
				//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
			}
			else
			{
				if(node->type == ARRAY_NODE)
				{
					ASTNode *index = walkAST(root->left);
					
					if(index->type == Integer)
					{
						struct dataNode *indexData = index->data;
						char *buff = (char *)malloc(sizeof(char) * 20);
						sprintf(buff, "%d", indexData->intValue);
						char *var = (char *)malloc(strlen(array) + strlen(buff) + 3);
						strcpy(var, array);
						strcat(var, "[");
						strcat(var, buff);
						strcat(var, "]");
						
						ASTNode *treeData = walkAST(root->right);
						node = treeData->data;

						insertItem(var, node);
						free(buff);
						free(var);
					}
					else
					{
						reportError(root->linenumber, TypeViolation, NULL);
						//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
					}
				}
			}
		}
		else if(root->type == Integer)
		{
			return root;
		}
		else if(root->type == String)
		{
			return root;
		}
		else if(root->type == Boolean)
		{
			return root;
		}
		else if(root->type == Print)
		{
			argumentList *args = root->data;
			while(args)
			{
				ASTNode *data = walkAST(args->tree);
				printAST(data);
				args = args->next;
			}
		}
		else if(root->type == Variable)
		{
			struct dataNode *node;
			int ret = find(root->data, (void **)&node);
			ASTNode *treeNode = NULL;
			
			if(!ret)
			{
				node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
				reportError(root->linenumber, UninitializedViolation, root->data);
				//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
			}
			else
			{
				/*if(!isDefined(root->data))
				{
					node->type = UNDEFINED_NODE;
					treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
				}*/
				//else
				//{
					if(node->type == NUM_NODE)
					{
						treeNode = makeNode(Integer, NULL, NULL, node, root->linenumber);
					}
					else if(node->type == STRING_NODE)
					{
						treeNode = makeNode(String, NULL, NULL, node, root->linenumber);
					}
					else if(node->type == UNINITIALIZED_NODE)
					{
						char *var = root->data;
						reportError(root->linenumber, UninitializedViolation, var);
						//fprintf(stderr, "Line %d, %s has no value\n", root->linenumber, var);
						treeNode = makeNode(Uninitialized, NULL, NULL, node, root->linenumber);
					}
					else if(node->type == OBJECT_NODE)
					{
						reportError(root->linenumber, TypeViolation, NULL);
						//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
						treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
					}
					else if(node->type == ARRAY_NODE)
					{
						reportError(root->linenumber, TypeViolation, NULL);
						//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
						treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);					
					}
					else if(node->type == UNDEFINED_NODE)
					{
						//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
						treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
					}
					else if(node->type == BOOLEAN_NODE)
					{
						treeNode = makeNode(Boolean, NULL, NULL, node, root->linenumber);
					}
				//}
			}
			return treeNode;
		}
		else if(root->type == ObjectVariable)
		{
			char *object = strdup(root->data);
			char *field;
			strtok_r(object, ".", &field);

			struct dataNode *node;
			int ret = find(object, (void **)&node);
			ASTNode *treeNode = NULL;

			if(!ret)
			{
				node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
				reportError(root->linenumber, UninitializedViolation, object);
				//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
			}
			else if(ret && node->type == OBJECT_NODE)
			{
				ret = find(root->data, (void **)&node);
				if(!ret)
				{
					node = (struct dataNode *)malloc(sizeof(struct dataNode));
					node->type = UNDEFINED_NODE;
					treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
					reportError(root->linenumber, UninitializedViolation, root->data);
				}
				else
				{
					if(node->type == UNINITIALIZED_NODE)
					{
						char *var = root->data;
						reportError(root->linenumber, UninitializedViolation, var);
						//fprintf(stderr, "Line %d, %s has no value\n", root->linenumber, var);
						treeNode = makeNode(Uninitialized, NULL, NULL, node, root->linenumber);
					}
					else if(node->type == NUM_NODE)
					{	
						treeNode = makeNode(Integer, NULL, NULL, node, root->linenumber);
					}
					else if(node->type == STRING_NODE)
					{
						treeNode = makeNode(String, NULL, NULL, node, root->linenumber);
					}
					else if(node->type == UNDEFINED_NODE)
					{
						treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
					}
					else if(node->type == BOOLEAN_NODE)
					{
						treeNode = makeNode(Boolean, NULL, NULL, node, root->linenumber);
					}
				}
			}
			else if(ret && node->type == UNINITIALIZED_NODE)
			{
				node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
				reportError(root->linenumber, UninitializedViolation, object);
			}
			else
			{
				node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
				reportError(root->linenumber, TypeViolation, NULL);
			}
			
			return treeNode;
		}
		else if(root->type == ArrayVariable)
		{
			struct dataNode *node;
			int ret = find(root->data, (void **)&node);
			ASTNode *treeNode = NULL;

			if(!ret)
			{
				node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
				reportError(root->linenumber, UninitializedViolation, root->data);
				//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
			}
			else
			{
				if(!isDefined(root->data))
				{
					node->type = UNDEFINED_NODE;
					treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
					reportError(root->linenumber, UninitializedViolation, root->data);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
				}
				else if(isDefined(root->data) && node->type == ARRAY_NODE)
				{
					ASTNode *index = walkAST(root->left);
					if(index->type == Integer)
					{
						struct dataNode *indexNode = index->data;
						
						char *array = strdup(root->data);
						char *buff = (char *)malloc(sizeof(char)*20);
						sprintf(buff, "%d", indexNode->intValue);
						char *var = (char *)malloc(strlen(array) + strlen(buff) + 3);
						strcpy(var, array);
						strcat(var , "[");
						strcat(var, buff);
						strcat(var, "]");

						int newRet = find(var, (void **)&node);
						if(!newRet)
						{
							node->type = UNDEFINED_NODE;
							treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
							reportError(root->linenumber, UninitializedViolation, var);
							//fprintf(stderr, "Line %d, %s has no value\n", root->linenumber, var);
						}
						else
						{
							if(node->type == UNINITIALIZED_NODE)
							{
								reportError(root->linenumber, UninitializedViolation, var);
								//fprintf(stderr, "Line %d, %s has no value\n", root->linenumber, var);
								treeNode = makeNode(Uninitialized, NULL, NULL, node, root->linenumber);
							}
							else if(node->type == NUM_NODE)
							{	
								treeNode = makeNode(Integer, NULL, NULL, node, root->linenumber);
							}
							else if(node->type == STRING_NODE)
							{
								treeNode = makeNode(String, NULL, NULL, node, root->linenumber);
							}
							else if(node->type == UNDEFINED_NODE)
							{
								treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
							}
							else if(node->type == BOOLEAN_NODE)
							{
								treeNode = makeNode(Boolean, NULL, NULL, node, root->linenumber);
							}
						}
						free(array);
						free(buff);
						free(var);
					}
					else
					{
						node->type = UNDEFINED_NODE;
						treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
						reportError(root->linenumber, TypeViolation, NULL);
						//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
					}
				}
				else if(isDefined(root->data) && node->type == UNINITIALIZED_NODE)
				{
					node->type = UNDEFINED_NODE;
					treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
					reportError(root->linenumber, UninitializedViolation, root->data);
				}
				else
				{
					node->type = UNDEFINED_NODE;
					treeNode = makeNode(Undefined, NULL, NULL, node, root->linenumber);
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
				}
			}
			
			return treeNode;
		}
		else if(root->type == Object)
		{
			return root;
		}
		else if(root->type == Array)
		{
			return root;
		}
		else if(root->type == Add)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);
			
			if(left->type == Integer && right->type == Integer)
			{	
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = NUM_NODE;
				newNode->intValue = leftData->intValue + rightData->intValue;
				
				return makeNode(Integer, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == String && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = STRING_NODE;
			
				char *str = (char *)malloc(strlen(leftData->strValue) + strlen(rightData->strValue) + 1);
				strcpy(str, leftData->strValue);
				strcat(str, rightData->strValue);
				node->strValue =  strdup(str);
				free(str);
				
				return makeNode(String, NULL, NULL, node, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE)) 
				{
					reportError(root->linenumber, TypeViolation, NULL);
				}	
				//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
	
				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}
		}
		else if(root->type == Subtract)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);

			if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = NUM_NODE;
				newNode->intValue = leftData->intValue - rightData->intValue;
				
				return makeNode(Integer, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
				
				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}
		}
		else if(root->type == Multiply)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);

			if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = NUM_NODE;
				newNode->intValue = leftData->intValue * rightData->intValue;
				
				return makeNode(Integer, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);

				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}
		}
		else if(root->type == Divide)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);

			if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = NUM_NODE;
				newNode->intValue = leftData->intValue / rightData->intValue;
				
				return makeNode(Integer, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);

				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}
		}
		else if(root->type == Less)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);
			
			if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->intValue < rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
				
				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}
		}
		else if(root->type == LessEqual)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);
			
			if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->intValue <= rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
				
				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}		
		}
		else if(root->type == Greater)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);
			
			if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->intValue > rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
				
				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}		
		}
		else if(root->type == GreaterEqual)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);
			
			if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->intValue >= rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
					//fprintf(stderr, "Line %d, type violation\n", root->linenumber);
				
				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}		
		}
		else if(root->type == AndAnd)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);
			
			if(left->type == Boolean && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->booleanValue && rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Boolean && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->booleanValue && rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Boolean && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->booleanValue && *rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->intValue && rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == String && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(*leftData->strValue && *rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Integer && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->intValue && *rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == String && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(*leftData->strValue && rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == String && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(*leftData->strValue && rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Integer && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->intValue && rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}
		}
		else if(root->type == OrOr)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);
			
			if(left->type == Boolean && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->booleanValue || rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Boolean && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->booleanValue || rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Boolean && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->booleanValue || *rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->intValue || rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == String && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(*leftData->strValue || *rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Integer && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->intValue || *rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == String && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(*leftData->strValue || rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == String && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(*leftData->strValue || rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Integer && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->intValue || rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;

				return makeNode(Undefined, NULL, NULL, node, root->linenumber);
			}
		}
		else if(root->type == EqualEqual)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);
			
			if(left->type == Boolean && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->booleanValue == rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->intValue == rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == String && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(!strcmp(leftData->strValue, rightData->strValue))
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);			
			}
			/*else if(left->type == Boolean && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if((leftData->booleanValue == 1) && rightData->intValue)
					newNode->booleanValue = 1;
				else if((leftData->booleanValue == 0) && !rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Boolean && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if((leftData->booleanValue == 1) && *rightData->strValue)
					newNode->booleanValue = 1;
				else if((leftData->booleanValue == 0) && !*rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}*/
			/*else if(left->type == Integer && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->intValue && *rightData->strValue)
					newNode->booleanValue = 1;
				else if(!leftData->intValue && !*rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}*/
			/*else if(left->type == Integer && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(leftData->intValue && rightData->booleanValue)
					newNode->booleanValue = 1;
				else if(!leftData->intValue && !rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);

			}*/
			/*else if(left->type == String && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(*leftData->strValue && rightData->intValue)
					newNode->booleanValue = 1;
				else if(!*leftData->strValue && !rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);

			}*/
			/*else if(left->type == String && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;

				if(*leftData->strValue && rightData->booleanValue)
					newNode->booleanValue = 1;
				else if(!*leftData->strValue && !rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);

			}*/
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
				return makeNode(Undefined, NULL, NULL, node, root->linenumber);			
			}
		}
		else if(root->type == NotEqual)
		{
			ASTNode *left = walkAST(root->left);
			ASTNode *right = walkAST(root->right);
			
			if(left->type == Boolean && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->booleanValue != rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == Integer && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->intValue != rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(left->type == String && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(strcmp(leftData->strValue, rightData->strValue))
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);			
			}
			/*else if(left->type == Boolean && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->booleanValue && !rightData->intValue)
					newNode->booleanValue = 1;
				else if(!leftData->booleanValue && rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);	
			}
			else if(left->type == Boolean && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->booleanValue && !*rightData->strValue)
					newNode->booleanValue = 1;
				else if(!leftData->booleanValue && *rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}*/
			/*else if(left->type == Integer && right->type == String)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->intValue && !*rightData->strValue)
					newNode->booleanValue = 1;
				else if(!leftData->intValue && *rightData->strValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}*/
			/*else if(left->type == Integer && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(leftData->intValue && !rightData->booleanValue)
					newNode->booleanValue = 1;
				else if(!leftData->intValue && rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}*/
			/*else if(left->type == String && right->type == Integer)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(*leftData->strValue && !rightData->intValue)
					newNode->booleanValue = 1;
				else if(!*leftData->strValue && rightData->intValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}*/
			/*else if(left->type == String && right->type == Boolean)
			{
				struct dataNode *leftData = left->data;
				struct dataNode *rightData = right->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				
				if(*leftData->strValue && !rightData->booleanValue)
					newNode->booleanValue = 1;
				else if(!*leftData->strValue && rightData->booleanValue)
					newNode->booleanValue = 1;
				else
					newNode->booleanValue = 0;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}*/
			else
			{
				struct dataNode *node = (struct dataNode *)malloc(sizeof(struct dataNode));
				node->type = UNDEFINED_NODE;
				if((left->type != UNDEFINED_NODE && left->type != UNINITIALIZED_NODE) && (right->type != UNDEFINED_NODE && right->type != UNINITIALIZED_NODE))
					reportError(root->linenumber, TypeViolation, NULL);
	
				return makeNode(Undefined, NULL, NULL, node, root->linenumber);			
			}
		}
		else if(root->type == Not)
		{
			ASTNode *data = walkAST(root->data);
			
			if(data->type == Boolean)
			{
				struct dataNode *node = data->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				newNode->booleanValue = !node->booleanValue;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(data->type == Integer)
			{
				struct dataNode *node = data->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				newNode->booleanValue = !node->intValue;

				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else if(data->type == String)
			{
				struct dataNode *node = data->data;
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = BOOLEAN_NODE;
				newNode->booleanValue = !*node->strValue;
				
				return makeNode(Boolean, NULL, NULL, newNode, root->linenumber);
			}
			else
			{
				struct dataNode *newNode = (struct dataNode *)malloc(sizeof(struct dataNode));
				newNode->type = UNDEFINED_NODE;
	
				return makeNode(Undefined, NULL, NULL, newNode, root->linenumber);
			}
		}
		else if(root->type == If)
		{
			ASTNode *left = walkAST(root->left);
			//lineNo++;
			if(left->type == Boolean || left->type == Integer || left->type == String)
			{
				lineNo++;
				struct dataNode *leftData = left->data;
				if(left->type == Integer)
				{
					if(leftData->intValue)
						leftData->booleanValue = 1;
					else
						leftData->booleanValue = 0;
				}
				else if(left->type == String)
				{
					if(*leftData->strValue)
						leftData->booleanValue = 1;
					else
						leftData->booleanValue = 0;
				}

				if(leftData->booleanValue)
				{
					struct statementList *curr = root->data;
					while(curr)
					{
						if(curr->root->type == Break || curr->root->type == Continue)
						{
							return curr->root;	
						}
						ASTNode *treeNode = walkAST(curr->root);
						if(treeNode && (treeNode->type == Break || treeNode->type == Continue))
							return treeNode;
						curr = curr->next;
					}
					
					lineNo = root->linenumber;
				}
				else
				{
					struct statementList *curr = root->data;
					while(curr)
					{
						if(curr->root->type == Newline)
						{
							walkAST(curr->root);
						}
						curr = curr->next;
					}
					walkAST(root->right);
				}
			}
			else
			{
				reportError(root->linenumber, ConditionViolation, NULL);
				lineNo = root->linenumber;
			}
		}
		else if(root->type == Else)
		{
			lineNo++;
			struct statementList *curr = root->data;
			while(curr)
			{
				if(curr->root->type == Break || curr->root->type == Continue)
					return curr->root;

				ASTNode *treeNode = walkAST(curr->root);
				if(treeNode && (treeNode->type == Break || treeNode->type == Continue))
					return treeNode;
				curr = curr->next;
			}
			lineNo++;
		}
		else if(root->type == While)
		{
			ASTNode *left = walkAST(root->left);
			//lineNo++;	
			if(left->type == Boolean || left->type == Integer || left->type == String)
			{	
				lineNo++;
				struct dataNode *leftData = left->data;
				
				if(left->type == Integer)
				{
					if(leftData->intValue)
						leftData->booleanValue = 1;
					else
						leftData->booleanValue = 0;
				}
				else if(left->type == String)
				{
					if(*leftData->strValue)
						leftData->booleanValue = 1;
					else
						leftData->booleanValue = 0;
				}

				while((left->type == Boolean || left->type == Integer || left->type == String) && leftData->booleanValue)
				{
					struct statementList *curr = root->data;
					int toBreak = 0;
					int toContinue = 0;
					while(curr)
					{
						if(curr->root->type == Break)
						{	
							toBreak = 1;
							break;
						}
						else if(curr->root->type == Continue)
						{
							toContinue = 1;
							break;
						}
						ASTNode *treeNode = walkAST(curr->root);
						if(treeNode && treeNode->type == Break)
						{
							toBreak = 1;
							break;
						}
						else if(treeNode && treeNode->type == Continue)
						{
							toContinue = 1;
							break;
						}
						curr = curr->next;
					}
					if(toBreak)
						break;
					
					left = walkAST(root->left);
					leftData = left->data;
					if(left->type == Integer)
					{
						if(leftData->intValue)
							leftData->booleanValue = 1;
						else
							leftData->booleanValue = 0;
					}
					else if(left->type == String)
					{
						if(*leftData->strValue)
							leftData->booleanValue = 1;
						else
							leftData->booleanValue = 0;
					}
				}
				lineNo = root->linenumber;
			}
			else
			{
				reportError(root->linenumber, ConditionViolation, NULL);
				lineNo = root->linenumber;	
			}
		}
		else if(root->type == Newline)
		{
			lineNo++;
		}
		else if(root->type == DoWhile)
		{
			ASTNode *left = walkAST(root->left);
			lineNo++;
			//if(left->type == Boolean || left->type == Integer || left->type == String)
			//{
				struct statementList *curr = root->data;
				int toBreak = 0;
				int toContinue = 0;
				while(curr)
				{
					if(curr->root->type == Break)
					{	
						toBreak = 1;
						break;
					}
					else if(curr->root->type == Continue)
					{
						toContinue = 1;
						break;
					}
					ASTNode *treeNode = walkAST(curr->root);
					if(treeNode && treeNode->type == Break)
					{
						toBreak = 1;
						break;
					}
					else if(treeNode && treeNode->type == Continue)
					{
						toContinue = 1;
						break;
					}
					curr = curr->next;
				}
				lineNo++;	
				if(toBreak)
					return NULL;
			if(left->type == Boolean || left->type == Integer || left->type == String)
			{
				left = walkAST(root->left);
				struct dataNode *leftData = left->data;
				
				if(left->type == Integer)
				{
					if(leftData->intValue)
						leftData->booleanValue = 1;
					else
						leftData->booleanValue = 0;
				}
				else if(left->type == String)
				{
					if(*leftData->strValue)
						leftData->booleanValue = 1;
					else
						leftData->booleanValue = 0;
				}

				while(leftData->booleanValue)
				{	
					curr = root->data;
					toBreak = 0;
					toContinue = 0;
					while(curr)
					{
						if(curr->root->type == Break)
						{	
							toBreak = 1;
							break;
						}
						else if(curr->root->type == Continue)
						{
							toContinue = 1;
							break;
						}
						ASTNode *treeNode = walkAST(curr->root);
						if(treeNode && treeNode->type == Break)
						{
							toBreak = 1;
							break;
						}
						else if(treeNode && treeNode->type == Continue)
						{
							toContinue = 1;
							break;
						}
						curr = curr->next;
					}
					if(toBreak)
						break;
					
					left = walkAST(root->left);
					leftData = left->data;
					
					if(left->type == Integer)
					{
						if(leftData->intValue)
							leftData->booleanValue = 1;
						else
							leftData->booleanValue = 0;
					}
					else if(left->type == String)
					{
						if(*leftData->strValue)
							leftData->booleanValue = 1;
						else
							leftData->booleanValue = 0;
					}
				}
				lineNo = root->linenumber;
			}
			else
			{
				lineNo = root->linenumber;
				reportError(root->linenumber, ConditionViolation, NULL);
			}
		}
	}	
	
	return NULL;
}

void reportError(int linenumber, ErrorType type, char *var)
{
	reportErrorList(linenumber, type, var, list);
}	
void reportErrorList(int linenumber, ErrorType type, char *var, struct statementList *sList)
{	
	if(type == ConditionViolation)
	{
		fprintf(stderr, "Line %d, condition unknown\n", lineNo);
		return;
	}
	struct statementList *curr = sList;

	while(curr && curr->root->linenumber != linenumber)
	{
		if(curr->root->type == If || curr->root->type == While || curr->root->type == DoWhile || curr->root->type == Else)
		{
			reportErrorList(linenumber, type, var, curr->root->data);
		}
		curr = curr->next;
	}
	while(curr && curr->root->linenumber == linenumber && curr->errorReported)
	{
		if(curr->root->type == If || curr->root->type == While || curr->root->type == DoWhile || curr->root->type == Else)
			reportErrorList(linenumber, type, var, curr->root->data);
		curr = curr->next;
	}
	
	if(curr && curr->root->linenumber == linenumber && curr->root->type != Newline)
	{
		if(!curr->errorReported)
		{
			if(type == TypeViolation)
			{
				fprintf(stderr, "Line %d, type violation\n", linenumber);	
			}
			else if(type == UninitializedViolation)
			{
				fprintf(stderr, "Line %d, %s has no value\n", linenumber, var);
			}
			else if(type == ConditionViolation)
			{
				//fprintf(stderr, "lineNo = %d\n", lineNo);
				fprintf(stderr, "Line %d, condition unknown\n", lineNo);
			}
			else if(type == UndeclaredViolation)
			{
				fprintf(stderr, "Line %d, %s undeclared\n", linenumber, var);
			}
			curr->errorReported = 1;
		}
	}
}
