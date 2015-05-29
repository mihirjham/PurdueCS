	.text
.globl mysort
mysort:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument n to the stack
	#Adding argument a to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
	#Creating space for local variable i
	#Creating space for local variable j
	#Creating space for local variable tmp
	movq	0(%rsp),%rbx

	# push 1
	movq $1,%r10

	# -
	subq %r10, %rbx
	#Moving data into local variable
	movq	%rbx, 16(%rsp)
start_loop_0:
	movq	16(%rsp),%rbx

	# push 0
	movq $0,%r10

	#Check if it is greater than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovge %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_0
	jmp for_statement_0
for_increment_0:
	movq	16(%rsp),%r10

	# push 1
	movq $1,%r13

	# -
	subq %r13, %r10
	#Moving data into local variable
	movq	%r10, 16(%rsp)
	jmp start_loop_0
for_statement_0:

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 24(%rsp)
start_loop_1:
	movq	24(%rsp),%rbx
	movq	16(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_1
	jmp for_statement_1
for_increment_1:
	movq	24(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into local variable
	movq	%r10, 24(%rsp)
	jmp start_loop_1
for_statement_1:
	movq	24(%rsp),%rbx
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq (%rax), %rbx
	movq	24(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	movq 8(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10

	#Check if it is greater than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovge %rdx, %rbx
	cmpq $0, %rbx
	je else_2
	movq	24(%rsp),%r10
	movq 8(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10
	#Moving data into local variable
	movq	%r10, 32(%rsp)
	movq	24(%rsp),%r10
	movq	24(%rsp),%r13

	# push 1
	movq $1,%r14

	# +
	addq %r14,%r13
	movq 8(%rsp), %rax
	imulq $8, %r13
	addq %r13, %rax
	movq (%rax), %r13
	movq 8(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq %r13, (%rax)
	movq	24(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	movq	32(%rsp),%r13
	movq 8(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq %r13, (%rax)
	jmp endif_2
else_2:
endif_2:
	jmp for_increment_1
end_loop_1:
	jmp for_increment_0
end_loop_0:
	addq	$256, %rsp
# Restore registers
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	#Restoring space for local variables
	ret
	.text
.globl printArray
printArray:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument s to the stack
	#Adding argument n to the stack
	#Adding argument a to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
	movq %rdx, 16(%rsp)
	#Creating space for local variable i
	#top=0

	# push string "----------- %s -----------\n" top=0
	movq $string0, %rbx
	movq	0(%rsp),%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 24(%rsp)
start_loop_3:
	movq	24(%rsp),%rbx
	movq	8(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_3
	jmp for_statement_3
for_increment_3:
	movq	24(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into local variable
	movq	%r10, 24(%rsp)
	jmp start_loop_3
for_statement_3:
	#top=0

	# push string "%d\n" top=0
	movq $string1, %rbx
	movq	24(%rsp),%r10
	movq 16(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	jmp for_increment_3
end_loop_3:
	addq	$256, %rsp
# Restore registers
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	#Restoring space for local variables
	ret
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
	#Creating space for local variable n

	# push 10
	movq $10,%rbx
	#Moving data into local variable
	movq	%rbx, 0(%rsp)
	#Creating space for local variable a
	movq	0(%rsp),%rbx

	# push 8
	movq $8,%r10

	# *
	imulq %r10,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call malloc
	movq %rax, %rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)

	# push 0
	movq $0,%rbx

	# push 8
	movq $8,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 1
	movq $1,%rbx

	# push 7
	movq $7,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 2
	movq $2,%rbx

	# push 1
	movq $1,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 3
	movq $3,%rbx

	# push 9
	movq $9,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 4
	movq $4,%rbx

	# push 11
	movq $11,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 5
	movq $5,%rbx

	# push 83
	movq $83,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 6
	movq $6,%rbx

	# push 7
	movq $7,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 7
	movq $7,%rbx

	# push 13
	movq $13,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 8
	movq $8,%rbx

	# push 94
	movq $94,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 9
	movq $9,%rbx

	# push 1
	movq $1,%r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)
	#top=0

	# push string "Before" top=0
	movq $string2, %rbx
	movq	0(%rsp),%r10
	movq	8(%rsp),%r13
     # func=printArray nargs=3
     # Move values from reg stack to reg args
	movq %r13, %rdx
	movq %r10, %rsi
	movq %rbx, %rdi
	call printArray
	movq %rax, %rbx
	movq	0(%rsp),%rbx
	movq	8(%rsp),%r10
     # func=mysort nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	call mysort
	movq %rax, %rbx
	#top=0

	# push string "After" top=0
	movq $string3, %rbx
	movq	0(%rsp),%r10
	movq	8(%rsp),%r13
     # func=printArray nargs=3
     # Move values from reg stack to reg args
	movq %r13, %rdx
	movq %r10, %rsi
	movq %rbx, %rdi
	call printArray
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
	.string "----------- %s -----------\n"

string1:
	.string "%d\n"

string2:
	.string "Before"

string3:
	.string "After"

