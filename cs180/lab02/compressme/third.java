public class third {
	public static void main(String[] args) {
		System.out.println(2);
		candloop:
		for (int i=3;;i+=2) {
			int s = (int)Math.sqrt(i);
			for (int j=3;j<i;j+=2)
				if (i%j==0) continue candloop;
			System.out.println(i);
		}
	}
}
