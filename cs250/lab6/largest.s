/*largest.s
	Read 2 numbers and find the max of them
*/

	.section	.rodata
promptA:
	.ascii	"a: \000"
promptB:
	.ascii	"b: \000"
readA:
	.ascii	"%d\000"
readB:
	.ascii	"%d\000"
printMax:
	.ascii "max=%d\n\000"
	
	.section	.data
	
	.align 2
	/*Defining variables of 4 bytes, each aligned to 4 bytes
		int a;
		int b;
		int max;
	*/
	.comm	a,4,4
	.comm	b,4,4
	.comm	max,4,4

	.text

	/*We need to store the addresses of a and b in .text
	to be able to access them in main*/

addrA:	.word	a
addrB:	.word	b
addrMax:	.word	max
addrPromptA:	.word	promptA
addrPromptB:	.word	promptB
addrReadA:	.word	readA
addrReadB:	.word	readB
addrPrintMax:	.word	printMax

	.global main	/*main() { */

main:
	stmfd	sp!, {fp,lr}	/*Save pc and lr*/
	ldr	r0, addrPromptA	/*PromptA*/
	bl	printf

	ldr	r0, addrReadA	/*ReadA*/
	ldr	r1, addrA	/*a*/
	bl	scanf

	ldr	r0, addrPromptB	/*PromptB*/
	bl	printf

	ldr	r0, addrReadB	/*ReadB*/
	ldr	r1, addrB	/*b*/
	bl	scanf

	ldr	r0, addrA	/*r0 <- a */
	ldr	r0, [r0]

	ldr	r1, addrB	/*r1 <- b */
	ldr	r1, [r1]
	
	ldr	r2, addrMax	/*r2 <- max */

	cmp	r0, r1		/*if(a < b)  max = b*/
	bgt	maxA		/*else go to maxA*/
	mov	r2, r1

	ldr	r0, addrPrintMax	/*PrintMax*/
	mov	r1, r2
	bl	printf

	ldmfd sp!,{fp,pc}	/*return from main*/

maxA:
	mov	r2, r0		/*max = a*/

	ldr	r0, addrPrintMax	/*PrintMax*/
	mov	r1, r2
	bl	printf

	ldmfd	sp!,{fp,pc}
