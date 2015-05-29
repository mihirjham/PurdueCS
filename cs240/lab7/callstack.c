#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include "callstack.h"
#include "tweetIt.h"

static char *correctPassword = "ceriaslyserious";
char *message = NULL;

int validateSanity(char *password) {
    for(int i=0;i<strlen(password);i++)
        if(!isalpha(password[i]))
            return 0;
    unsigned int magic = 0x12345678;
    return badguy(password);
}

int validate(char *password) {
    printf("--Validating something\n", password);
    if (strlen(password) > 128) return 0;

    char *passwordCopy = malloc(strlen(password) + 1);
    strcpy(passwordCopy, password);
    return validateSanity(passwordCopy);
}

int check(char *password, char *expectedPassword) {
    return (strcmp(password, expectedPassword) == 0);
}

int main() {
    char *password = "wrongpassword";
    unsigned int magic = 0xABCDE;
    char *expectedPassword = correctPassword;
    if (!validate(password)) {
        printf("--Invalid password!\n");
        return 1;
    }
    if (check(password, expectedPassword)) {
        if (message == NULL) {
            printf("--No message!\n");
            return 1;
        } else {
            tweetIt(message, strlen(message));
            printf("--Message sent.\n");
        }
    } else {
        printf("--Incorrect password!\n");
    }
  return 0;
}
