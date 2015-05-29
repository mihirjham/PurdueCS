%{
    int nline;
    int commentlines;
%}

%%
\n         { nline++; }
"//"	{ commentlines = commentlines + 1; };
.          ;
%%

int yywrap(void){
  return 1;
}
int main(int argc, char **argv) {
    yyin = fopen(argv[1], "r");
    yylex();
    printf("%d\n", nline);
    printf("%d\n", commentlines);
    fclose(yyin);
    return 0;
}
