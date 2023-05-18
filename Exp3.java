
public class Exp3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//main method runs Exp3.S for 20 K values 1000 times and outputs the average regret for each K value
		double[][] regret = new double[1000][20];
		double[] aveRegret = new double[20];
		double L = 1;
		double T = 100000;
		double alpha = 1/T;
		
		for(double k = 2; k <= 40; k = k+2){
			int sum = 0;
			double gamma = Math.min(1, Math.pow((2*L*(k+1)*Math.log((k+1)*T))/((Math.pow(Math.exp(1)-1,2)*T)),(1.0/3.0)));
			double delta = Math.pow((T/(k+1)),(2/(double)3));
			double epsilon = Math.pow(delta, (-0.5));
			
			for(int i = 0; i < 1000; i++){
				regret[i][(int)((k/2)-1)] = Exp3S(gamma,alpha,k,T,epsilon,delta);
				
			}
			for(int i = 0; i < 1000; i++){
				sum += regret[i][(int)((k/2)-1)];
			}
			aveRegret[(int)((k/2)-1)] = sum/1000;
		}
		for(int i = 0; i < 20; i++){
			System.out.println(aveRegret[i]);

		}
	}
	
	public static double Exp3S(double gamma, double alpha, double K, double T, double epsilon, double delta){
		
		int Kindex = (int)(K);
		int Tindex = (int)(T);
		
		double[] X = new double[Kindex];
		double[][] P = new double[Tindex][Kindex];
		double[] p = new double[Kindex];
		double[][] W = new double[Tindex+1][Kindex];
		for(int k = 0; k < Kindex; k++){
			W[0][k] = 1;
		}
		int[] I = new int[Tindex];
		double[] R = new double[Tindex];
		double[][] Y = new double[Tindex][Kindex];
		double[] Z = new double[Kindex];
		double prob = 0;
		double sample = 0;
		
		double prox = 0;
		double payout = 0;
		//Set probabilities here, currently it is set for worst case scenario
		for(int k = 0; k < (K-1); k++){
			X[k] = 0.5;
		}
		X[Kindex-1] = 0.5 + epsilon;
		int bestMachine = Kindex-1;
		int counter = 1;
		int Delta = (int)(delta);
		for(int t = 0; t < T; t++){
			int choice = 0;
			if((double)(t)%(double)Delta == 0){
				prob = X[counter];
				X[counter] = X[bestMachine];
				X[bestMachine] = prob;
				if(counter == (K-1)){
					counter = 1;
				}
				else{
					counter++;
				}
			}
			double sum1 = 0;
			double sum2 = 0;
			for(int i = 0; i < K; i++){
				sum1 += W[t][i];
				
				
			}
			for(int k = 0; k < K; k++){
				P[t][k] = (1-gamma)*(W[t][k]/sum1) + gamma/K;
				
			}
			for(int i = 0 ; i < K; i++){
				sum2 += P[t][i];
			}
			
			for(int k = 0; k < K; k++){
				p[k] = P[t][k]/sum2;
				
			}
			for(int k = 1; k < K; k++){
				p[k] += p[k-1];
				
			}
			sample = Math.random();
			for(int k = 0; k < Kindex; k++){
				
				if(sample < p[k] ){
					choice = k;
					k = Kindex;
				}
				
			}
			I[t] = choice;
			sample = Math.random();
			
			if(sample < X[I[t]]){
				R[t] = 1;
			}
			else{
				R[t] = 0;
			}
			
			double sum3 = 0;
			for(int k = 0; k < K; k++){
				if(k == choice){
					Y[t][k] = R[t]/P[t][k];
				}
				else{
					Y[t][k] = 0;
				}
				Z[k] = W[t][k]*Math.exp((gamma*Y[t][k])/K) + ((Math.exp(1)*alpha)/K)*sum1;
				sum3 += Z[k];
			}
			for(int k = 0; k < K; k++){
				W[t+1][k] = Z[k]/sum3;
			}
			payout += R[t];
			
		}
		double bestPay = (0.5+epsilon)*T;
		
		double regret = bestPay - payout;
		return regret;
		
	}
		

}
