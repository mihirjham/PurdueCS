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
	#Adding argument a to the stack
	#Adding argument left to the stack
	#Adding argument right to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
	movq %rdx, 16(%rsp)
	#Creating space for local variable i
	movq	8(%rsp),%rbx
	#Moving data into local variable
	movq	%rbx, 24(%rsp)
start_loop_0:
	movq	24(%rsp),%rbx
	movq	16(%rsp),%r10

	#check if it is less than equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmovge %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_0
	jmp for_statement_0
for_increment_0:
	movq	24(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into local variable
	movq	%r10, 24(%rsp)
	jmp start_loop_0
for_statement_0:
	#top=0

	# push string "%d\n" top=0
	movq $string0, %rbx
	movq	24(%rsp),%r10
	movq 0(%rsp), %rax
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
.globl print
print:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument s to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	#top=0

	# push string "==%s==\n" top=0
	movq $string1, %rbx
	movq	0(%rsp),%r10
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
	.text
.globl quicksortsubrange
quicksortsubrange:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument a to the stack
	#Adding argument left to the stack
	#Adding argument right to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
	movq %rdx, 16(%rsp)
	movq	8(%rsp),%rbx
	movq	16(%rsp),%r10

	#check if it is greater than equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je else_1

	# push 0
	movq $0,%r10
	movq %rbx, %rax
	addq $256, %rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	ret
	jmp endif_1
else_1:
endif_1:
	#Creating space for local variable x
	movq	16(%rsp),%rbx
	movq 0(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq (%rax), %rbx
	#Moving data into local variable
	movq	%rbx, 24(%rsp)
	#Creating space for local variable l
	movq	8(%rsp),%rbx
	#Moving data into local variable
	movq	%rbx, 32(%rsp)
	#Creating space for local variable g
	movq	16(%rsp),%rbx

	# push 1
	movq $1,%r10

	# -
	subq %r10, %rbx
	#Moving data into local variable
	movq	%rbx, 40(%rsp)
start_loop_2:
	movq	32(%rsp),%rbx
	movq	40(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_2
start_loop_3:
	movq	32(%rsp),%rbx
	movq	40(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	movq	32(%rsp),%r10
	movq 0(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10
	movq	24(%rsp),%r13

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $0, %rdx
	movq $1, %r10
	cmovle %rdx, %r10

	#check &&
	andq %r10, %rbx
	cmpq $0, %rbx
	je end_loop_3
	movq	32(%rsp),%rbx

	# push 1
	movq $1,%r10

	# +
	addq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 32(%rsp)
	jmp start_loop_3
end_loop_3:
start_loop_4:
	movq	32(%rsp),%rbx
	movq	40(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	movq	40(%rsp),%r10
	movq 0(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10
	movq	24(%rsp),%r13

	#Check if it is greater than
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $0, %rdx
	movq $1, %r10
	cmovge %rdx, %r10

	#check &&
	andq %r10, %rbx
	cmpq $0, %rbx
	je end_loop_4
	movq	40(%rsp),%rbx

	# push 1
	movq $1,%r10

	# -
	subq %r10, %rbx
	#Moving data into local variable
	movq	%rbx, 40(%rsp)
	jmp start_loop_4
end_loop_4:
	movq	32(%rsp),%rbx
	movq	40(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je else_5
	#Creating space for local variable tmp
	movq	32(%rsp),%r10
	movq 0(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10
	#Moving data into local variable
	movq	%r10, 48(%rsp)
	movq	32(%rsp),%r10
	movq	40(%rsp),%r13
	movq 0(%rsp), %rax
	imulq $8, %r13
	addq %r13, %rax
	movq (%rax), %r13
	movq 0(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq %r13, (%rax)
	movq	40(%rsp),%r10
	movq	48(%rsp),%r13
	movq 0(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq %r13, (%rax)
	jmp endif_5
else_5:
endif_5:
	jmp start_loop_2
end_loop_2:
	movq	16(%rsp),%r10
	movq	32(%rsp),%r13
	movq 0(%rsp), %rax
	imulq $8, %r13
	addq %r13, %rax
	movq (%rax), %r13
	movq 0(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq %r13, (%rax)
	movq	32(%rsp),%r10
	movq	24(%rsp),%r13
	movq 0(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq %r13, (%rax)
	movq	0(%rsp),%r10
	movq	8(%rsp),%r13
	movq	32(%rsp),%r14

	# push 1
	movq $1,%r15

	# -
	subq %r15, %r14
     # func=quicksortsubrange nargs=3
     # Move values from reg stack to reg args
	movq %r14, %rdx
	movq %r13, %rsi
	movq %r10, %rdi
	call quicksortsubrange
	movq %rax, %r10
	movq	0(%rsp),%rbx
	movq	40(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	movq	16(%rsp),%r13
     # func=quicksortsubrange nargs=3
     # Move values from reg stack to reg args
	movq %r13, %rdx
	movq %r10, %rsi
	movq %rbx, %rdi
	call quicksortsubrange
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
	.text
.globl quicksort
quicksort:
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
	movq	0(%rsp),%rbx

	# push 0
	movq $0,%r10
	movq	8(%rsp),%r13

	# push 1
	movq $1,%r14

	# -
	subq %r14, %r13
     # func=quicksortsubrange nargs=3
     # Move values from reg stack to reg args
	movq %r13, %rdx
	movq %r10, %rsi
	movq %rbx, %rdi
	call quicksortsubrange
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
	#top=0

	# push string "Hello" top=0
	movq $string2, %rbx
     # func=print nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call print
	movq %rax, %rbx
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

	# push string "-------- Before -------\n" top=0
	movq $string3, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	movq	8(%rsp),%rbx

	# push 0
	movq $0,%r10
	movq	0(%rsp),%r13

	# push 1
	movq $1,%r14

	# -
	subq %r14, %r13
     # func=printArray nargs=3
     # Move values from reg stack to reg args
	movq %r13, %rdx
	movq %r10, %rsi
	movq %rbx, %rdi
	call printArray
	movq %rax, %rbx
	movq	8(%rsp),%rbx
	movq	0(%rsp),%r10
     # func=quicksort nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	call quicksort
	movq %rax, %rbx
	#top=0

	# push string "-------- After -------\n" top=0
	movq $string4, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	movq	8(%rsp),%rbx

	# push 0
	movq $0,%r10
	movq	0(%rsp),%r13

	# push 1
	movq $1,%r14

	# -
	subq %r14, %r13
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
	.string "%d\n"

string1:
	.string "==%s==\n"

string2:
	.string "Hello"

string3:
	.string "-------- Before -------\n"

string4:
	.string "-------- After -------\n"

