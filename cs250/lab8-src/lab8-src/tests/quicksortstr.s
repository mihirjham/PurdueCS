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

	# push string "%d: %s\n" top=0
	movq $string0, %rbx
	movq	24(%rsp),%r10
	movq	24(%rsp),%r13
	movq 0(%rsp), %rax
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
.globl mystrcmp
mystrcmp:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument s1 to the stack
	#Adding argument s2 to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
start_loop_1:

	# push 0
	movq $0,%rbx
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb (%rax), %bl

	# push 0
	movq $0,%r10
	movq 8(%rsp), %rax
	imulq $1, %r10
	addq %r10, %rax
	movb (%rax), %r10b

	#check &&
	andq %r10, %rbx

	# push 0
	movq $0,%r10
	movq 0(%rsp), %rax
	imulq $1, %r10
	addq %r10, %rax
	movb (%rax), %r10b

	# push 0
	movq $0,%r13
	movq 8(%rsp), %rax
	imulq $1, %r13
	addq %r13, %rax
	movb (%rax), %r13b

	#check if equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmove %rdx, %r10

	#check &&
	andq %r10, %rbx
	cmpq $0, %rbx
	je end_loop_1
	movq	0(%rsp),%rbx

	# push 1
	movq $1,%r10

	# +
	addq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 0(%rsp)
	movq	8(%rsp),%rbx

	# push 1
	movq $1,%r10

	# +
	addq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
	jmp start_loop_1
end_loop_1:

	# push 0
	movq $0,%rbx
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb (%rax), %bl

	# push 0
	movq $0,%r10

	#check if equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmove %rdx, %rbx

	# push 0
	movq $0,%r10
	movq 8(%rsp), %rax
	imulq $1, %r10
	addq %r10, %rax
	movb (%rax), %r10b

	# push 0
	movq $0,%r13

	#check if equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmove %rdx, %r10

	#check &&
	andq %r10, %rbx
	cmpq $0, %rbx
	je else_2

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
	jmp endif_2
else_2:
endif_2:

	# push 0
	movq $0,%rbx
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb (%rax), %bl

	# push 0
	movq $0,%r10

	#check if equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmove %rdx, %rbx

	# push 0
	movq $0,%r10
	movq 8(%rsp), %rax
	imulq $1, %r10
	addq %r10, %rax
	movb (%rax), %r10b

	# push 0
	movq $0,%r13

	#check if not equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmovne %rdx, %r10

	#check &&
	andq %r10, %rbx
	cmpq $0, %rbx
	je else_3

	# push -1
	movq $-1,%r10
	movq %rbx, %rax
	addq $256, %rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	ret
	jmp endif_3
else_3:
endif_3:

	# push 0
	movq $0,%rbx
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb (%rax), %bl

	# push 0
	movq $0,%r10

	#check if not equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmovne %rdx, %rbx

	# push 0
	movq $0,%r10
	movq 8(%rsp), %rax
	imulq $1, %r10
	addq %r10, %rax
	movb (%rax), %r10b

	# push 0
	movq $0,%r13

	#check if equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmove %rdx, %r10

	#check &&
	andq %r10, %rbx
	cmpq $0, %rbx
	je else_4

	# push 1
	movq $1,%r10
	movq %rbx, %rax
	addq $256, %rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	ret
	jmp endif_4
else_4:
endif_4:

	# push 0
	movq $0,%rbx
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb (%rax), %bl

	# push 0
	movq $0,%r10
	movq 8(%rsp), %rax
	imulq $1, %r10
	addq %r10, %rax
	movb (%rax), %r10b

	#Check if it is greater than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovge %rdx, %rbx
	cmpq $0, %rbx
	je else_5

	# push 1
	movq $1,%r10
	movq %rbx, %rax
	addq $256, %rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	ret
	jmp endif_5
else_5:
endif_5:

	# push -1
	movq $-1,%rbx
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
	je else_6

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
	jmp endif_6
else_6:
endif_6:
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
start_loop_7:
	movq	32(%rsp),%rbx
	movq	40(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_7
start_loop_8:
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
     # func=mystrcmp nargs=2
     # Move values from reg stack to reg args
	movq %r13, %rsi
	movq %r10, %rdi
	call mystrcmp
	movq %rax, %r10

	# push 0
	movq $0,%r13

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $0, %rdx
	movq $1, %r10
	cmovle %rdx, %r10

	#check &&
	andq %r10, %rbx
	cmpq $0, %rbx
	je end_loop_8
	movq	32(%rsp),%rbx

	# push 1
	movq $1,%r10

	# +
	addq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 32(%rsp)
	jmp start_loop_8
end_loop_8:
start_loop_9:
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
     # func=mystrcmp nargs=2
     # Move values from reg stack to reg args
	movq %r13, %rsi
	movq %r10, %rdi
	call mystrcmp
	movq %rax, %r10

	# push 0
	movq $0,%r13

	#check if it is greater than equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmovle %rdx, %r10

	#check &&
	andq %r10, %rbx
	cmpq $0, %rbx
	je end_loop_9
	movq	40(%rsp),%rbx

	# push 1
	movq $1,%r10

	# -
	subq %r10, %rbx
	#Moving data into local variable
	movq	%rbx, 40(%rsp)
	jmp start_loop_9
end_loop_9:
	movq	32(%rsp),%rbx
	movq	40(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je else_10
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
	jmp endif_10
else_10:
endif_10:
	jmp start_loop_7
end_loop_7:
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
	#Creating space for local variable n

	# push 6
	movq $6,%rbx
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
	#top=1

	# push string "Rachael" top=1
	movq $string2, %r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 1
	movq $1,%rbx
	#top=1

	# push string "Monica" top=1
	movq $string3, %r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 2
	movq $2,%rbx
	#top=1

	# push string "Phoebe" top=1
	movq $string4, %r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 3
	movq $3,%rbx
	#top=1

	# push string "Joey" top=1
	movq $string5, %r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 4
	movq $4,%rbx
	#top=1

	# push string "Ross" top=1
	movq $string6, %r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 5
	movq $5,%rbx
	#top=1

	# push string "Chandler" top=1
	movq $string7, %r10
	movq 8(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)
	#top=0

	# push string "-------- Before -------\n" top=0
	movq $string8, %rbx
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
	movq $string9, %rbx
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
	.string "%d: %s\n"

string1:
	.string "==%s==\n"

string2:
	.string "Rachael"

string3:
	.string "Monica"

string4:
	.string "Phoebe"

string5:
	.string "Joey"

string6:
	.string "Ross"

string7:
	.string "Chandler"

string8:
	.string "-------- Before -------\n"

string9:
	.string "-------- After -------\n"

