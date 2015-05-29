	.data
	
	.comm a,8
	.comm b,8

	.text
printA:
	.string "a="
printB:
	.string "b="
printMax:
	.string "max=%d\n"
printMin:
	.string "min=%d\n"
printAvg:
	.string "avg=%d\n"
readInt:
	.string "%d"

	.globl main
main:
	movq $printA, %rdi
	movq $0, %rax
	call printf

	movq $readInt, %rdi
	movq $a, %rsi
	movq $0, %rax
	call scanf

	movq $printB, %rdi
	movq $0, %rax
	call printf

	movq $readInt, %rdi
	movq $b, %rsi
	movq $0, %rax
	call scanf

	movq $a, %rbx
	movq (%rbx), %rbx
	movq $b, %rdx
	movq (%rdx), %rdx

	cmpq %rbx, %rdx
	jg   maxB
	
	movq $printMax, %rdi
	movq $a, %rsi
	movq (%rsi), %rsi
	movq $0, %rax
	call printf
	
	movq $printMin ,%rdi
	movq $b, %rsi
	movq (%rsi), %rsi
	movq $0, %rax
	call printf
	jmp  end

maxB:
	movq $printMax, %rdi
	movq $b, %rsi
	movq (%rsi), %rsi
	movq $0, %rax
	call printf

	movq $printMin, %rdi
	movq $a, %rsi
	movq (%rsi), %rsi
	movq $0, %rax
	call printf
end:
	movq $a, %rbx
	movq (%rbx), %rbx
	movq $b, %rdx
	movq (%rdx), %rdx

	addq %rdx, %rbx
	
	xorq %rdx, %rdx
	movq %rbx, %rax
	movq $2, %rbx
	idivq %rbx
	movq %rax, %rbx

	movq $printAvg, %rdi
	movq %rbx, %rsi
	movq $0, %rax
	call printf
	
	ret
