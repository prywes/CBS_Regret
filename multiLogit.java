import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import edu.stanford.rsl.jpop.FunctionOptimizer;
import edu.stanford.rsl.jpop.OptimizableFunction;


public class multiLogit implements OptimizableFunction {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		OptimizableFunction logLike = null;
		FunctionOptimizer fn = new FunctionOptimizer();
		fn.optimizeFunction(logLike);
		}
	
	public double logLike(double x,double y,double z) throws NumberFormatException, IOException{
		
		double beta1 = x;
		double beta2 = y;
		double alpha = z;
		
		File file = new File("/user/user1/ep2505/share_rlg/eden_files/javaT.csv");
		BufferedReader bufRdr  = new BufferedReader(new FileReader(file));
		String line = null;
		int col = 0;
		double[] listing = new double[3];
		String reqId = "6ccf438384bc8c2e97ea6630fab82af3";
		double CLL = 0;
		double[] widgetProb = new double[18];
		String currentReq = reqId;
		boolean wasClick = false;
		int widgetIndex = 0;
		int clickIndex = 100;
		double sum = 0;
		while((line = bufRdr.readLine()) != null){
			
			StringTokenizer st = new StringTokenizer(line,",");
			while(st.hasMoreTokens()){
				
				if(col == 3){
					reqId = st.nextToken();
				}
				else{
					listing[col] = Double.valueOf(st.nextToken());
				}
				col++;
			}
			//System.out.println(reqId);
			
			col = 0;

			if(reqId.equals(currentReq)){
				
				
				widgetProb[widgetIndex] = Math.exp(beta1*listing[1]+beta2*listing[2]+alpha);
				if(listing[0] == (double)1){
					wasClick = true;
					clickIndex = widgetIndex;
				}
				widgetIndex++;
			}
			else{
				sum = 0;
				for(int j = 0; j < 10; j++){
					sum += widgetProb[j];
				}
				if(wasClick){
					CLL += Math.log(widgetProb[clickIndex]/(sum+1));
				}
				else{
					CLL += Math.log(1/(sum+1));
				}
				widgetProb = new double[18];
				currentReq = reqId;
				widgetIndex = 0;
				wasClick = false;
				clickIndex = 100;
				
				//System.out.println("ran");
				widgetProb[widgetIndex] = Math.exp(beta1*listing[1]+beta2*listing[2]+alpha);
				if(listing[0] == (double)1){
					wasClick = true;
					clickIndex = widgetIndex;
				}
				widgetIndex++;
				//System.out.println(reqId);
			}
			
		}
		bufRdr.close();
		return CLL;
	}

	@Override
	public void setNumberOfProcessingBlocks(int number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfProcessingBlocks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(double[] x, int block) {
		// TODO Auto-generated method stub
		double beta1 = x[0];
		double beta2 = x[1];
		double alpha = x[2];
		
		File file = new File("/user/user1/ep2505/share_rlg/eden_files/javaT.csv");
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = null;
		int col = 0;
		double[] listing = new double[3];
		String reqId = "6ccf438384bc8c2e97ea6630fab82af3";
		double CLL = 0;
		double[] widgetProb = new double[18];
		String currentReq = reqId;
		boolean wasClick = false;
		int widgetIndex = 0;
		int clickIndex = 100;
		double sum = 0;
		try {
			while((line = bufRdr.readLine()) != null){
				
				StringTokenizer st = new StringTokenizer(line,",");
				while(st.hasMoreTokens()){
					
					if(col == 3){
						reqId = st.nextToken();
					}
					else{
						listing[col] = Double.valueOf(st.nextToken());
					}
					col++;
				}
				//System.out.println(reqId);
				
				col = 0;

				if(reqId.equals(currentReq)){
					
					
					widgetProb[widgetIndex] = Math.exp(beta1*listing[1]+beta2*listing[2]+alpha);
					if(listing[0] == (double)1){
						wasClick = true;
						clickIndex = widgetIndex;
					}
					widgetIndex++;
				}
				else{
					sum = 0;
					for(int j = 0; j < 10; j++){
						sum += widgetProb[j];
					}
					if(wasClick){
						CLL += Math.log(widgetProb[clickIndex]/(sum+1));
					}
					else{
						CLL += Math.log(1/(sum+1));
					}
					widgetProb = new double[18];
					currentReq = reqId;
					widgetIndex = 0;
					wasClick = false;
					clickIndex = 100;
					
					//System.out.println("ran");
					widgetProb[widgetIndex] = Math.exp(beta1*listing[1]+beta2*listing[2]+alpha);
					if(listing[0] == (double)1){
						wasClick = true;
						clickIndex = widgetIndex;
					}
					widgetIndex++;
					//System.out.println(reqId);
				}
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bufRdr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CLL;
	}

	

}
