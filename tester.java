
public class tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int t = 500;
		double L = 1;
		double T = 10000;
		double k = 2;
		double K =  Math.min(1,Math.pow((2*L*(k+1)*Math.log((k+1)*T))/((Math.pow(Math.exp(1)-1,2)*T)),(1/(double)3)));
		
		System.out.print(K);
	}

}
