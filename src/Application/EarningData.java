package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Application.GUIController;

/**
 * @author      Han Xiao <hannyc@seas.upenn.edu>
 * @version     0.0.3
 */


public class EarningData {

	private String stock;
	private TreeMap<String, Double[]> outputTM;
	private String errorMessage;
	private String dataIncompleteMessage;
	private String noEstimates;
	private String noActualEPS;
	private String noConsensusEPS;

	//Constructor
	public EarningData(String stock) {
		this.stock = stock;
		this.outputTM = new TreeMap<>((String a, String b) -> {
	           String[] aa = a.split(" ");
	           String[] bb = b.split(" ");

	           int c = aa[1].compareTo(bb[1]);

	           if (c == 0)
	               c = aa[0].compareTo(bb[0]);

	           return c;
	       });
		this.errorMessage = "";
		this.dataIncompleteMessage = "";
		this.noEstimates = "";
		this.noActualEPS = "";
		this.noConsensusEPS = "";
	}


	public TreeMap<String, Double[]> getOutputTM() {
		return outputTM;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getDataIncompleteMessage() {
		return dataIncompleteMessage;
	}

	public String getNoEstimates() {
		return noEstimates;
	}


	public String getNoActualEPS() {
		return noActualEPS;
	}


	public String getNoConsensusEPS() {
		return noConsensusEPS;
	}


	/**
	 * Request IEXTrading API and parse JSON response
	 * Update the TreeMap which maps earnings data to the stock and fiscal period
	 * @param stock ticker
	 * @throws IOException
	 * @throws JSONException
	 */
    public void getEarning(String stock) throws JSONException {
        String dataString = "";
        URL iex;
		try {
			String token = ""; //Add your API token
			iex = new URL("https://cloud.iexapis.com/stable/stock/" + stock + "/earnings/" + 4 + "?token=" + token);
			URLConnection iexAPI = iex.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(iexAPI.getInputStream()));
	        String inputLine;

        	while ((inputLine = in.readLine()) != null) {
        		dataString += inputLine;
        	}
        	in.close();

        	outputTM.clear();
        	parseJSON(outputTM, dataString);


		} catch (IOException e) {
			outputTM.clear();
        	errorMessage = "Please enter a valid input";
		}


    }

    /**
	 * Helper function to call method to request IEXTrading API and parse JSON response
	 * @throws IOException
	 * @throws JSONException
	 */
    public void getEarning() throws IOException, JSONException {
    	getEarning(this.stock);
    }


    /**
     * Parse API response
     * @param dataMap A TreeMap that stores earnings data
     * @param jsonDataString API response
     * @param company ticker
     * @throws JSONException
     */
    public void parseJSON(TreeMap<String, Double[]> dataMap, String dataString) throws JSONException {
    	String jsonString = "";
    	if(dataString.isEmpty()) {
    		jsonString = dataString;
    	}else {
    		jsonString = dataString.substring(dataString.indexOf('['), dataString.length()-1);
    	}

    	JSONArray dataArray = new JSONArray(jsonString);

    	for (int i = 0; i < dataArray.length(); i ++) {
    		String fiscalPeriod = "not exist";
        	Double[] EPSdata = {0.0, 0.0, 0.0};
    		if(!dataArray.getJSONObject(i).equals(null)) {
    			JSONObject object = dataArray.getJSONObject(i);

    			if(object.has("fiscalPeriod")) {
    				fiscalPeriod = object.getString("fiscalPeriod");
    			}else {
    				dataIncompleteMessage += "No fiscal period data. ";
    			}

    			if(object.has("actualEPS")) {
    				EPSdata[0] = object.getDouble("actualEPS");
    			}else {
    				//set value to minimal integer  as a flag in case accidently passed to GUIController
    				EPSdata[0] = (double) Integer.MIN_VALUE;
    				this.noActualEPS = " No actual EPS data. ";
    				dataIncompleteMessage += noActualEPS;
    			}

    			if(object.has("consensusEPS")) {
    				EPSdata[1] = object.getDouble("consensusEPS");
    			}else {
    				EPSdata[1] = (double) Integer.MIN_VALUE;
    				noConsensusEPS = "No consensus EPS data. ";
    				dataIncompleteMessage += noConsensusEPS;
    			}

    			if(object.has("numberOfEstimates")) {
    				EPSdata[2] = object.getDouble("numberOfEstimates");
    			}else {
    				EPSdata[2] = (double) Integer.MIN_VALUE;
    				noEstimates = "No number of estimates data. ";
    				dataIncompleteMessage += noEstimates;
    			}

    		}else {
    			dataIncompleteMessage += " No data for this stock";
    		}


    		dataMap.put(fiscalPeriod, EPSdata);
    	}

    }


}
