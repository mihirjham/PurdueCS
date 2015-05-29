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

	# push string "9==8%d\n" top=0
	movq $string0, %rbx

	# push 9
	movq $9,%r10

	# push 8
	movq $8,%r13

	#check if equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmove %rdx, %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	#top=0

	# push string "9==9=%d\n" top=0
	movq $string1, %rbx

	# push 9
	movq $9,%r10

	# push 9
	movq $9,%r13

	#check if equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmove %rdx, %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	#top=0

	# push string "9!=8%d\n" top=0
	movq $string2, %rbx

	# push 9
	movq $9,%r10

	# push 8
	movq $8,%r13

	#check if not equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmovne %rdx, %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	#top=0

	# push string "9!=9=%d\n" top=0
	movq $string3, %rbx

	# push 9
	movq $9,%r10

	# push 9
	movq $9,%r13

	#check if not equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmovne %rdx, %r10
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
	.string "9==8%d\n"

string1:
	.string "9==9=%d\n"

string2:
	.string "9!=8%d\n"

string3:
	.string "9!=9=%d\n"

