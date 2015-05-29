/*length.s
	Reads a string from input and prints of the length
*/

	.section	.rodata
promptStr:
	.ascii	"str: \000"
readStr:
	.ascii	"%s\000"
printLength:
	.ascii	"Length=%d\n\000"
printStr:
	.ascii	"%s\n\000"

	.section	.data
	.balign	4
str:	.skip	100				/* Assigning space for str* */
	
	.text

	.global	main				/* main() */
main:
	stmfd	sp!,{fp,lr}			/* Save fp,lr */
	
	ldr	r0, addrPromptStr		/* Read str from input */
	bl	printf
	ldr	r0, addrStr
	bl	gets
	
	ldr	r0, addrStr
	mov	r3, #0
count:						/* Length of str */
	ldrb	r1, [r0]
	mov	r2, #0				/* if char == NULL */
	cmp	r1,r2
	beq	end
	mov	r2, #1
	add	r0,r0,r2			/* *str++ */
	add	r3,r3,r2			/* length++ */
	bal	count

end:
	ldr	r0, addrPrintLength		/* print length */
	mov	r1,r3
	bl	printf
	
	ldmfd	sp!,{fp,pc}			/* return fp,pc */

addrPromptStr:		.word	promptStr
addrPrintLength:	.word	printLength
addrPrintStr:		.word	printStr
addrStr:		.word	str
addrReadStr:		.word	readStr
