import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Discretization {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileWriter results = new FileWriter("discreteResults.txt");
		BufferedWriter out = new BufferedWriter(results);
		int index1 = 0;
		int index2 = 0;
		//double[] Regret = new double[0];
		int T = 0;
		double[] sumR = new double[20];
		double[] zhat;
		int k;
		double gm;
		double[] p;
		double[] P;
		double[] w;
		double Wsum;
		int choice;
		double choiceV;
		double sample;
		double result;
		//double oracleResult;
		double[] Result;
		//double[] OracleResult;
		double oracleRT;
		for(int i =0; i< sumR.length; i++){
			sumR[i] = 0;
		}
		for(int n = 1; n < 21; n++ ){
			T = n*10000;
			System.out.println(T);
			for(int test = 0; test < 100; test++){
				
				//k = (int) Math.round(Math.pow((double) T, 1.0/3.0));
				k = 3;
				//System.out.println(k);
				//gm = Math.pow((double) T, -1.0/3.0);
				gm = Math.min(1.0, Math.sqrt((3*Math.log(3))/((Math.exp(1.0)-1)*(double)T*100.0)));

				p = new double[k];
				P = new double [k];
				w = new double[k];
				zhat = new double[k];
				for(int j = 0; j < w.length; j++){
					w[j] = 1;
				}
				Wsum = w.length;
				
				choice = -1;
				choiceV = -1;
				result = 0;
				//oracleResult = 0;
				Result = new double[T];
				//OracleResult = new double[T];
				index1 = 0;
				index2 = 0;
				oracleRT = 0;
				for(int t = 1; t < (T + 1); t++){
					
					//gm = Math.pow((double) t, -1.0/3.0);
					
					
					//rescale
					for(int i = 0; i < w.length; i++){
						w[i] = w[i]/Wsum;
						
					}
					
					Wsum  = 0;
					for(int i = 0; i < w.length; i++){
						Wsum += w[i];
					}
					
					
					
					for(int j = 0; j< p.length; j++){
						p[j] = (1.0-gm)*(w[j]/Wsum) + (gm/(double)k);
						
					}
					
					P[0] = p[0];
					for(int j = 1; j < P.length; j++){
						P[j] = P[j-1] + p[j];
					}
					
					
					choice = -1;
					choiceV = -1;
					sample = Math.random();
					
					for(int j = 0; j < P.length; j++){
						if(sample < P[j]){
							choice = j;
							
							j = P.length;
							
						}
					}
					//choiceV = ((double)choice+1.0)/((double) k + 1.0);
					choiceV = ((double)choice)/((double) k - 1.0);
					sample = Math.random();
					if(sample < 0.5){
						result = f1(choiceV);
						//compute at end
						//oracleResult = f1(1.0);
						index1++;
						
					}
					else{
						result = f2(choiceV);
						//oracleResult = f2(1.0);
						index2++;
					}
					Result[t - 1] = result;
					//OracleResult[t-1] = oracleResult;
					for(int j = 0; j < zhat.length; j++){
						if(j == choice){
							zhat[j] = result/p[j];
						}
						else{
							zhat[j] = 0;

						}
					}
					
					for(int j = 0; j < w.length; j++){
						w[j] = w[j]*Math.exp(-zhat[j]*(gm/(double)k));
					}
					Wsum  = 0;
					for(int i = 0; i < w.length; i++){
						Wsum += w[i];
					}
					/*if(T == 10000 && test == 0){
						out.write(Double.toString(w[k-2]));
						out.newLine();
					}*/
					//attempt to debug:
					if(T == 10000 && test == 0){
						for(int j = 0; j < k; j++){
							out.write("prob "+ j + ":" + Double.toString(p[j]));
							out.newLine();
							
						}
						out.write("t = " + t);
						out.newLine();
						out.write("choice: " + Integer.toString(choice));
						out.newLine();
						out.write("result: " + Double.toString(result));
						out.newLine();
						for(int j = 0; j < k; j++){
							
							out.write("weight "+ j + ":" + Double.toString(w[j]/Wsum));
							out.newLine();
						}
						
						
					}
					
					
					
				}
				
				//find min x
				
				
				//calculate regret:
				//Regret = new double[T];
				//for(int i = 0; i< T; i++){
					
					//Regret[i] = Result[i] - OracleResult[i];
					
					
				//}
				//sumR[test] = 0;
				/*if(T == 9500){
					for(int j = 0; j < Regret.length; j++){
						out.write(Double.toString(Regret[j]));
						out.newLine();
						
					}
					for(int j = 0; j < p.length; j++){
						out.write(Double.toString(p[j]));
						out.newLine();
						
					}
				}*/
				if(T == 10000 && test == 0){
					/*for(int j = 0; j < Regret.length; j++){
						
						
						
					}*/
					for(int j = 0; j < p.length; j++){
						out.write(Double.toString(p[j]));
						out.newLine();
						
					}
				}
				if((2.0*index2 - 3.0*index1) > 0){
					oracleRT = index1*f1(0.0) + index2*f2(0.0);
					
				}
				else{

					oracleRT = index1*f1(1.0) + index2*f2(1.0);
				}
				for(int i = 0; i<T; i++){
					sumR[n-1] += Result[i];
				}
				sumR[n-1] = sumR[n-1] - oracleRT;	
					
				
				
				//System.out.println("T = " + T);
				/*
				for(int i = 0; i<k;i++){
					
					System.out.println(p[i]);

				}*/
				
				//System.out.println(sumR);
				
				//System.out.println(4*T);
				
			}
				
			
			sumR[n-1] = sumR[n-1]/100.0;
		}
		
		
		
		
		for(int i = 0; i < sumR.length; i++){
			System.out.println(sumR[i]);
		}
		
	
		out.close();
	}
	
	public static double f1(double c){
		
		//define the function
		double x = (.75 - 0.5*c)*100;
		return x;
	}
	
	public static double f2(double c){
		
		double x = ((1.0/3.0)+(1.0/3.0)*c)*100;
		//double x  = 0.5*100;
		//double x = (.75 - 0.5*c)*100;
		return x;
	}
	

}
