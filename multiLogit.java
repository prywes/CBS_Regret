import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;



public class multiLogit  {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		}
	//the number of inputs into the maxLike function should be the number of
	//variables you run the multi logit on plus one for the intercept
	public static double maxLike(double x1, double x2,double x3,double x4) throws NumberFormatException, IOException{
		
		double beta1 = x1;
		double beta2 = x2;
		double beta3 = x3;
		double alpha = x4;
		
		//In quotations should be the location of your actual file
		File file = new File("/user/user1/ep2505/share_rlg/eden_files/javaFile.csv");
		BufferedReader bufRdr  = new BufferedReader(new FileReader(file));
		String line = null;
		int col = 0;
		//The size of your listing array should be one plus the number of variables
		//you are running the multi logit on (the one plus is for the click data)
		double[] listing = new double[4];
		//you should pre-input the first reqId
		String reqId = "6ccf438384bc8c2e97ea6630fab82af3";
		double CLL = 0;
		//Check the max size of a widget and set that to be the size of widget prob
		//alternatively just make it really big (above 25 to be safe)
		double[] widgetProb = new double[18];
		String currentReq = reqId;
		boolean wasClick = false;
		int widgetIndex = 0;
		int clickIndex = 100;
		double sum = 0;
		while((line = bufRdr.readLine()) != null){
			
			StringTokenizer st = new StringTokenizer(line,",");
			while(st.hasMoreTokens()){
				//coll == length of your listing array
				if(col == 4){
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
				
				//make sure to change this line when you add or delete variables
				widgetProb[widgetIndex] = Math.exp(beta1*listing[1]+beta2*listing[2]+beta2*listing[3]+alpha);
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
				
				//make sure to change this line when you add or delete variables
				widgetProb[widgetIndex] = Math.exp(beta1*listing[1]+beta2*listing[2]+beta3*listing[3]+alpha);
				if(listing[0] == (double)1){
					wasClick = true;
					clickIndex = widgetIndex;
				}
				widgetIndex++;
				
			}
			
		}
		bufRdr.close();
		return CLL;
	}
}
