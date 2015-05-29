/*sumarray.s
	Reads 5 numbers from stdin and outputs sum
*/

	.section	.rodata
promptA:
	.ascii	"Enter 5 numbers: \000"
readA:
	.ascii	"%d\000"
printSum:
	.ascii	"Sum=%d\n\000"
	
	.section	.data

	.balign	4
a:	.skip	20			/* Defining a[5] */

	.text
	
	.global	main			/*main()*/

main:
	stmfd	sp!,{fp,r4,lr}		/*Save fp,r4,lr*/

	ldr	r0, addrPromptA 	/*Reads 5 integers from input into array*/
	bl	printf
	ldr	r0, addrReadA
	ldr	r1, addrA
	bl	scanf

	ldr	r0, addrReadA
	ldr	r1, addrA
	mov	r2, #4			/*Increments array by jumping 4 bytes*/
	add	r1, r1, r2
	bl	scanf
		
	ldr	r0, addrReadA
	ldr	r1, addrA
	mov	r2, #8
	add	r1, r1, r2
	bl	scanf
	
	ldr	r0, addrReadA
	ldr	r1, addrA
	mov	r2, #12
	add	r1, r1, r2
	bl	scanf

	ldr	r0, addrReadA
	ldr	r1, addrA
	mov	r2, #16
	add	r1, r1, r2
	bl	scanf
	
	ldr	r0, addrA	
	ldr	r3, [r0]		/* r3:sum, sum = a[0] */
	mov	r2, #1			/* i:r2, i = 1 */
	b	while			/* branch to while */
while:
	cmp	r2, #5			/* while(i != 5)*/
	beq	end

	mov	r1,r2			/* r1 = a[i] */
	mov	r4,#4
	mul	r1,r1,r4
	add	r1,r0,r1
	ldr	r1,[r1]

	add	r3,r3,r1		/* r3 = r3 + r1, sum = sum + a[i] */

	mov	r4,#1			/* i++ */
	add	r2,r2,r4
	bal	while	
end:	
	ldr	r0, addrPrintSum	/* Print sum */
	mov	r1, r3	
	bl	printf
	ldmfd	sp!,{fp,r4,pc}		/* Returning fp, r4, pc */

	
addrA:		.word	a
addrPromptA:	.word	promptA
addrReadA:	.word	readA
addrPrintSum:	.word	printSum
