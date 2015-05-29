rightasoc: y.tab.c lex.yy.c 
	gcc y.tab.c lex.yy.c -o rightasoc -lfl 
y.tab.c : rightasoc.y 
	bison -y -d -g --verbose rightasoc.y 
lex.yy.c: calc1.l 
	lex calc1.l 
clean: 
	rm -f lex.yy.c y.tab.c rightasoc

