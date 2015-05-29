all:
	lex ex.l
	bison -y -d -g -t --debug ex.y
	gcc y.tab.c lex.yy.c -o ex -lfl

clean:
	rm *.c ex
