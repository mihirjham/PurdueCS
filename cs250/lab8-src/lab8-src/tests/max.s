	.text
.globl max
max:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument a to the stack
	#Adding argument n to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
	#Creating space for local variable i
	#Creating space for local variable m

	# push 0
	movq $0,%rbx
	movq 0(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq (%rax), %rbx
	#Moving data into local variable
	movq	%rbx, 24(%rsp)

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 16(%rsp)
start_loop_0:
	movq	16(%rsp),%rbx
	movq	8(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_0
	jmp for_statement_0
for_increment_0:
	movq	16(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into local variable
	movq	%r10, 16(%rsp)
	jmp start_loop_0
for_statement_0:
	movq	24(%rsp),%rbx
	movq	16(%rsp),%r10
	movq 0(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je else_1
	movq	16(%rsp),%r10
	movq 0(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10
	#Moving data into local variable
	movq	%r10, 24(%rsp)
	jmp endif_1
else_1:
endif_1:
	jmp for_increment_0
end_loop_0:
	movq	24(%rsp),%rbx
	movq %rbx, %rax
	addq $256, %rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	ret
	addq	$256, %rsp
# Restore registers
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	#Restoring space for local variables
	ret
	#Creating space for global variables
	.data
a:
	.quad 0

	#Creating space for global variables
	.data
n:
	.quad 0

	#Creating space for global variables
	.data
i:
	.quad 0

	.text
.globl main
main:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Save Arguments

	# push 10
	movq $10,%rbx
	#Moving data into global variable
	movq	%rbx, n

	# push 10
	movq $10,%rbx

	# push 8
	movq $8,%r10

	# *
	imulq %r10,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call malloc
	movq %rax, %rbx
	#Moving data into global variable
	movq	%rbx, a

	# push 0
	movq $0,%rbx

	# push 8
	movq $8,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 1
	movq $1,%rbx

	# push 7
	movq $7,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 2
	movq $2,%rbx

	# push 1
	movq $1,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 3
	movq $3,%rbx

	# push 9
	movq $9,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 4
	movq $4,%rbx

	# push 11
	movq $11,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 5
	movq $5,%rbx

	# push 83
	movq $83,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 6
	movq $6,%rbx

	# push 7
	movq $7,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 7
	movq $7,%rbx

	# push 13
	movq $13,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 8
	movq $8,%rbx

	# push 94
	movq $94,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 9
	movq $9,%rbx

	# push 1
	movq $1,%r10
	movq a, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 0
	movq $0,%rbx
	#Moving data into global variable
	movq	%rbx, i
start_loop_2:
	movq i,%rbx
	movq n,%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_2
	jmp for_statement_2
for_increment_2:
	movq i,%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into global variable
	movq	%r10, i
	jmp start_loop_2
for_statement_2:
	#top=0

	# push string "%d: %d\n" top=0
	movq $string0, %rbx
	movq i,%r10
	movq i,%r13
	movq a, %rax
	imulq $8, %r13
	addq %r13, %rax
	movq (%rax), %r13
     # func=printf nargs=3
     # Move values from reg stack to reg args
	movq %r13, %rdx
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	jmp for_increment_2
end_loop_2:
	#top=0

	# push string "n=%d\n" top=0
	movq $string1, %rbx
	movq n,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	#top=0

	# push string "max=%d\n" top=0
	movq $string2, %rbx
	movq a,%r10
	movq n,%r13
     # func=max nargs=2
     # Move values from reg stack to reg args
	movq %r13, %rsi
	movq %r10, %rdi
	call max
	movq %rax, %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	addq	$256, %rsp
# Restore registers
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	#Restoring space for local variables
	ret
string0:
	.string "%d: %d\n"

string1:
	.string "n=%d\n"

string2:
	.string "max=%d\n"

