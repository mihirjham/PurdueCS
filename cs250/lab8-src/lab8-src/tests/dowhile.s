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

	# push 0
	movq $0,%rbx
	#Moving data into global variable
	movq	%rbx, i
start_loop_0:
	#top=0

	# push string "i=%d\n" top=0
	movq $string0, %rbx
	movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	movq i,%rbx

	# push 1
	movq $1,%r10

	# +
	addq %r10,%rbx
	#Moving data into global variable
	movq	%rbx, i
	movq i,%rbx

	# push 11
	movq $11,%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_0
	jmp start_loop_0
end_loop_0:
	#top=1

	# push string "OK\n" top=1
	movq $string1, %r10
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %r10, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %r10
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
	.string "i=%d\n"

string1:
	.string "OK\n"

