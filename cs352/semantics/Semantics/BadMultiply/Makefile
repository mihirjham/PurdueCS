badmultiply: y.tab.c lex.yy.c 
	gcc y.tab.c lex.yy.c -o badmultiply -lfl 
y.tab.c : badmultiply.y 
	bison -y -d -g --verbose badmultiply.y 
lex.yy.c: calc1.l 
	lex calc1.l 
clean: 
	rm -f lex.yy.c y.tab.c badmultiply

