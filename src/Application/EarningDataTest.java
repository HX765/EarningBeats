package Application;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Junit test for EarningData class
 */

class EarningDataTest {
	
	EarningData earningData;
	String stock;
	TreeMap<String, Double[]> outputTM;
	String errorMessage;
	String dataIncompleteMessage;
	String noEstimates;
	String noActualEPS;
	String noConsensusEPS;
	

	//Test purpose: tests basic functionality and check if API response is returned a correct stock ticker
	@Test
	public void testAPIResponse1() throws IOException, JSONException {
		stock = "MSFT"; //Microsoft
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		assertNotNull(outputTM, "Test failed. API not respond with valid data");
	}
	
	//Test purpose: tests API response accuracy 
	@Test
	public void testAPIResponse2() throws IOException, JSONException {
		stock = "GOOG"; //Google
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		int actualCount = 0;
		for (Map.Entry<String, Double[]> element : outputTM.entrySet()) {
			actualCount++;
		}
		assertEquals(4,actualCount, "API did not properly return all expected earning data");
	}
	
	//Test purpose: tests API response accuracy 
	@Test
	public void testAPIResponse3() throws IOException, JSONException {
		stock = "BBIO"; //Bridgebio Pharma Inc, IPO date:6/27/19
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		int actualCount = 0;
		for (Map.Entry<String, Double[]> element : outputTM.entrySet()) {
			actualCount++;
		}
		assertEquals(1,actualCount, "API did not properly return all expected earning data");
	}
	
	//Test purpose: tests API response accuracy 
	@Test
	public void testAPIResponse4() throws IOException, JSONException {
		stock = "BBIO"; //Bridgebio Pharma Inc, IPO date:6/27/19
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		noConsensusEPS = earningData.getNoConsensusEPS();
		assertFalse(noConsensusEPS.isEmpty());
	}

//	
	//Test purpose: tests information accuracy
	@Test
	public void testInfoAccuracy1() throws IOException, JSONException {
		stock = "BBIO"; //Bridgebio Pharma Inc, IPO date:6/27/19
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		dataIncompleteMessage = earningData.getDataIncompleteMessage();
		String expectedMessage = "No consensus EPS data. " + "No number of estimates data. ";
		assertEquals(expectedMessage, dataIncompleteMessage);
	}
	
	//Test purpose: tests information accuracy
	@Test
	public void testInfoAccuracy2() throws IOException, JSONException {
		stock = "BBIO"; //Bridgebio Pharma Inc, IPO date:6/27/19
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		Double[] outputData = new Double[3];
		for (Map.Entry<String, Double[]> element : outputTM.entrySet()) {
			outputData = (Double[]) element.getValue();
		}
		assertTrue(outputData[1] <= Integer.MIN_VALUE);
	}
	
	//Test purpose: tests information accuracy
	@Test
	public void testInfoAccuracy3() throws IOException, JSONException {
		stock = "ZM"; //ZOOM Communication, IPO date:4/18/19
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		Double[] outputData = new Double[3];
		String Q1data = "Q1 2019";
		Double Q1actual = outputTM.get(Q1data)[0];
		double zero = 0;
		assertTrue(Q1actual == zero); //2019 Q1 EPS = 0; not flag value
	}
	
	//Test purpose: tests information accuracy
	@Test
	public void testInfoAccuracy4() throws IOException, JSONException {
		stock = "ZM"; //ZOOM Communication, IPO date:4/18/19
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		Double[] outputData = new Double[3];
		String Q1data = "Q1 2019";
		Double Q1consensus = outputTM.get(Q1data)[1];
		assertTrue(Q1consensus <= Integer.MIN_VALUE); //2019 Q1 no consensus data
	}

	
	//Test purpose: tests program with invalid URL
	@Test
	public void testURLErrorHandler1() throws IOException, JSONException {
		stock = "."; //Invalid stick ticker
		earningData = new EarningData(stock);
		earningData.getEarning();
		errorMessage = earningData.getErrorMessage();
		String expectedErrorMessage = "Please enter a valid input";
		assertEquals(expectedErrorMessage, errorMessage);
	}

	//Test purpose: tests program with invalid URL
	@Test
	public void testURLErrorHandler2() throws IOException, JSONException {
		stock = "asdfafg"; //Invalid stick ticker
		earningData = new EarningData(stock);
		earningData.getEarning();
		errorMessage = earningData.getErrorMessage();
		String expectedErrorMessage = "Please enter a valid input";
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
    
	//Test purpose: tests the comparator function to sort the treemap based on fiscal period
	@Test
	public void testTreeMapSorting1() throws IOException, JSONException {
		stock = "FB"; //Facebook
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		String[] benchmark = {"Q3 2018", "Q4 2018", "Q1 2019", "Q2 2019"};
		int i = -1;

		for(String s: outputTM.keySet()) {
			assertEquals(benchmark[++i],s);
		}

	}

	//Test purpose: tests the comparator function to sort the treemap based on fiscal period
	@Test
	public void testTreeMapSorting2() throws IOException, JSONException {
		stock = "BBIO"; //Bridgebio Pharma Inc, IPO date:6/27/19
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		String[] benchmark = {"Q1 2019"};
		int i = -1;

		for(String s: outputTM.keySet()) {
			assertEquals(benchmark[++i],s);
		}

	}
	
	
	//Test purpose: tests the data quality of the API 
	@Test
	public void testDataQualityTest1() throws IOException, JSONException {
		stock = "BRK.A"; //Berkshire Hathaway Cl A: EPS is about 17,000
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		Double[] benchmark = new Double[4];

		for(Map.Entry<String, Double[]> data: outputTM.entrySet()) {
			assertTrue(data.getValue()[1] > 1000); //Berkshire Hathaway Inc. has the highest EPS
		}

	}
	
	//Test purpose: tests the data coverage of the API (foreign stock market)
	@Test
	public void testDataCoverageTest1() throws IOException, JSONException {
		stock = "0158.HK"; //Melbourne Enterprises Ltd: issued on HKSE
		earningData = new EarningData(stock);
		earningData.getEarning();
		outputTM = earningData.getOutputTM();
		Double[] benchmark = new Double[4];

		for(Map.Entry<String, Double[]> data: outputTM.entrySet()) {
			assertTrue(data.getValue()[1] > 50); //latest reporting EPS is 55.73
		}

	}
	
    //Test purpose: test JSONException is thrown with parseJSON method when JSON response is invalid
    @Test
    public void testJSONException1() {
    	stock = "";
		earningData = new EarningData(stock);
		TreeMap<String, Double[]> testTM = new TreeMap<>();
    	assertThrows(JSONException.class, ()-> {earningData.parseJSON(testTM, "");});
    	
    }

}