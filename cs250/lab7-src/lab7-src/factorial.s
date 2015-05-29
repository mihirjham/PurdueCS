	.text
.globl fact
	.type	fact, @function
fact:
	cvtsi2sd 	%edi, %xmm1	#(double) n
	movq	 	$1, %rsi
	cvtsi2sdq	%rsi, %xmm0	#fact = 1, fact: xmm0
	
	movq		$0, %rdi	#if n = 0
	cvtsi2sdq	%rdi, %xmm2
	ucomisd		%xmm2, %xmm1
	je		endZero		#return 1

while:
	movq		$1, %rsi	#while(n<1)
	cvtsi2sdq	%rsi, %xmm2
	ucomisd		%xmm2, %xmm1
	je		end

	mulsd		%xmm1, %xmm0	#fact = fact * n
	subsd		%xmm2 ,%xmm1
	jmp		while
end:
	ret				#return fact
endZero:
	cvtsi2sdq	%rsi, %xmm0	#return	1
	ret
