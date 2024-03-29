package Application;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.controlsfx.control.textfield.TextFields;
//import org.json.JSONException;
import org.json.JSONException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


/**
 * @author      Mengqing Shi <mengqing@seas.upenn.edu>
 * @author      Chao Qiu <chaoqiu@seas.upenn.edu>
 */

public class GUIController implements Initializable {

	@FXML
	private AnchorPane root;

	@FXML
	private Label lblStatus;

	@FXML
	private Label lblHeader;

	@FXML
	private TextField txtTicker;

	@FXML
	ScatterChart<String, Number> sc;

	private static String stock = "";

	/**
	 * @return the stock; this method is used in calling API
	 */
	public static String getStock() {
		return stock;
	}

	/**
	 * Auto-complete method which will auto-populate the ticker and company name in the text field.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		TextFields.bindAutoCompletion(getTxtTicker(), suggestedCompany);
	}

	/**
	 * Create a method to clear the content in the search box
	 * 
	 * @param e
	 */
	public void clearTicker(ActionEvent e) {
		getTxtTicker().clear();
		sc.getData().clear();
		sc.setTitle("");
		lblHeader.setText("Earnings vs. Estimates for");
		lblStatus.setText("Start by searching a ticker below!");
	}

	/**
	 * Create a method to search the ticker and populate the chart
	 * @param e
	 * @throws IOException
	 * @throws JSONException
	 */
	public void search(ActionEvent e) throws IOException, JSONException {

		sc.getData().clear();
		sc.setTitle("");

		if (getTxtTicker().getText().contains(" ")) {
			stock = getTxtTicker().getText().substring(0, getTxtTicker().getText().indexOf(" "));
		} else {
			stock = getTxtTicker().getText();
		}

		EarningData test = new EarningData(stock);
		test.getEarning(stock);

		if (test.getErrorMessage().isEmpty()) {
			
			lblStatus.setText("Search success!");
			lblHeader.setText("Earnings vs. Estimates for " + stock);
			Double[] EPS;

			sc.setAnimated(false);
			sc.setTitle(test.getDataIncompleteMessage());

			Series series1 = new Series();
			series1.setName("Expected EPS");

			Series series2 = new Series();
			series2.setName("Actual EPS");

			TreeMap<String, Double[]> testTM = test.getOutputTM();
			for (Map.Entry m : testTM.entrySet()) {
				EPS = (Double[]) m.getValue();
		
				if(test.getNoActualEPS().isEmpty()) {
					series2.getData().add(new XYChart.Data(m.getKey(), EPS[0]));
				}
								
				if(test.getNoConsensusEPS().isEmpty()) {
					series1.getData().add(new XYChart.Data(m.getKey(), EPS[1]));
				}				
			}
			sc.getData().addAll(series1, series2);

		} else {

			lblStatus.setText("Invalid stock code entered. Please enter a valid stock code.");
		}
	}

	/**
	 * @return the txtTicker
	 */
	public TextField getTxtTicker() {
		return txtTicker;
	}

	/**
	 * @param txtTicker the txtTicker to set
	 */
	public void setTxtTicker(TextField txtTicker) {
		this.txtTicker = txtTicker;
	}

	// This is the list of tickers that are already exist in the tool;
	static String[] suggestedCompany = { "MMM  3M Company", "ABT  Abbott Laboratories", "ABBV  AbbVie Inc.",
			"ABMD  ABIOMED Inc", "ACN  Accenture plc", "ATVI  Activision Blizzard", "ADBE  Adobe Systems Inc",
			"AMD  Advanced Micro Devices Inc", "AAP  Advance Auto Parts", "AES  AES Corp",
			"AMG  Affiliated Managers Group Inc", "AFL  AFLAC Inc", "A  Agilent Technologies Inc",
			"APD  Air Products & Chemicals Inc", "AKAM  Akamai Technologies Inc", "ALK  Alaska Air Group Inc",
			"ALB  Albemarle Corp", "ARE  Alexandria Real Estate Equities", "ALXN  Alexion Pharmaceuticals",
			"ALGN  Align Technology", "ALLE  Allegion", "AGN  Allergan, Plc", "ADS  Alliance Data Systems",
			"LNT  Alliant Energy Corp", "ALL  Allstate Corp", "GOOGL  Alphabet Inc Class A",
			"GOOG  Alphabet Inc Class C", "MO  Altria Group Inc", "AMZN  Amazon.com Inc.", "AMCR  Amcor plc",
			"AEE  Ameren Corp", "AAL  American Airlines Group", "AEP  American Electric Power",
			"AXP  American Express Co", "AIG  American International Group", "AMT  American Tower Corp.",
			"AWK  American Water Works Company Inc", "AMP  Ameriprise Financial", "ABC  AmerisourceBergen Corp",
			"AME  AMETEK Inc.", "AMGN  Amgen Inc.", "APH  Amphenol Corp", "APC  Anadarko Petroleum Corp",
			"ADI  Analog Devices, Inc.", "ANSS  ANSYS", "ANTM  Anthem", "AON  Aon plc", "AOS  A.O. Smith Corp",
			"APA  Apache Corporation", "AIV  Apartment Investment & Management", "AAPL  Apple Inc.",
			"AMAT  Applied Materials Inc.", "APTV  Aptiv Plc", "ADM  Archer-Daniels-Midland Co", "ARNC  Arconic Inc.",
			"ANET  Arista Networks", "AJG  Arthur J. Gallagher & Co.", "AIZ  Assurant", "ATO  Atmos Energy Corp",
			"T  AT&T Inc.", "ADSK  Autodesk Inc.", "ADP  Automatic Data Processing", "AZO  AutoZone Inc",
			"AVB  AvalonBay Communities, Inc.", "AVY  Avery Dennison Corp", "BHGE  Baker Hughes, a GE Company",
			"BLL  Ball Corp", "BAC  Bank of America Corp", "BK  The Bank of New York Mellon Corp.",
			"BAX  Baxter International Inc.", "BBT  BB&T Corporation", "BDX  Becton Dickinson",
			"BRK.B  Berkshire Hathaway", "BBY  Best Buy Co. Inc.", "BIIB  Biogen Inc.", "BLK  BlackRock",
			"HRB  Block H&R", "BA  Boeing Company", "BKNG  Booking Holdings Inc", "BWA  BorgWarner",
			"BXP  Boston Properties", "BSX  Boston Scientific", "BMY  Bristol-Myers Squibb", "AVGO  Broadcom Inc.",
			"BR  Broadridge Financial Solutions", "BF.B  Brown-Forman Corp.", "CHRW  C. H. Robinson Worldwide",
			"COG  Cabot Oil & Gas", "CDNS  Cadence Design Systems", "CPB  Campbell Soup", "COF  Capital One Financial",
			"CPRI  Capri Holdings", "CAH  Cardinal Health Inc.", "KMX  Carmax Inc", "CCL  Carnival Corp.",
			"CAT  Caterpillar Inc.", "CBOE  Cboe Global Markets", "CBRE  CBRE Group", "CBS  CBS Corp.", "CE  Celanese",
			"CELG  Celgene Corp.", "CNC  Centene Corporation", "CNP  CenterPoint Energy", "CTL  CenturyLink Inc",
			"CERN  Cerner", "CF  CF Industries Holdings Inc", "SCHW  Charles Schwab Corporation",
			"CHTR  Charter Communications", "CVX  Chevron Corp.", "CMG  Chipotle Mexican Grill", "CB  Chubb Limited",
			"CHD  Church & Dwight", "CI  CIGNA Corp.", "XEC  Cimarex Energy", "CINF  Cincinnati Financial",
			"CTAS  Cintas Corporation", "CSCO  Cisco Systems", "C  Citigroup Inc.", "CFG  Citizens Financial Group",
			"CTXS  Citrix Systems", "CLX  The Clorox Company", "CME  CME Group Inc.", "CMS  CMS Energy",
			"KO  Coca-Cola Company", "CTSH  Cognizant Technology Solutions", "CL  Colgate-Palmolive",
			"CMCSA  Comcast Corp.", "CMA  Comerica Inc.", "CAG  Conagra Brands", "CXO  Concho Resources",
			"COP  ConocoPhillips", "ED  Consolidated Edison", "STZ  Constellation Brands", "COO  The Cooper Companies",
			"CPRT  Copart Inc", "GLW  Corning Inc.", "CTVA  Corteva", "COST  Costco Wholesale Corp.", "COTY  Coty, Inc",
			"CCI  Crown Castle International Corp.", "CSX  CSX Corp.", "CMI  Cummins Inc.", "CVS  CVS Health",
			"DHI  D. R. Horton", "DHR  Danaher Corp.", "DRI  Darden Restaurants", "DVA  DaVita Inc.", "DE  Deere & Co.",
			"DAL  Delta Air Lines Inc.", "XRAY  Dentsply Sirona", "DVN  Devon Energy", "FANG  Diamondback Energy",
			"DLR  Digital Realty Trust Inc", "DFS  Discover Financial Services", "DISCA  Discovery Inc. Class A",
			"DISCK  Discovery Inc. Class C", "DISH  Dish Network", "DG  Dollar General", "DLTR  Dollar Tree",
			"D  Dominion Energy", "DOV  Dover Corp.", "DOW  Dow Inc.", "DTE  DTE Energy Co.", "DUK  Duke Energy",
			"DRE  Duke Realty Corp", "DD  DuPont de Nemours Inc", "DXC  DXC Technology", "ETFC  E*Trade",
			"EMN  Eastman Chemical", "ETN  Eaton Corporation", "EBAY  eBay Inc.", "ECL  Ecolab Inc.",
			"EIX  Edison Int'l", "EW  Edwards Lifesciences", "EA  Electronic Arts", "EMR  Emerson Electric Company",
			"ETR  Entergy Corp.", "EOG  EOG Resources", "EFX  Equifax Inc.", "EQIX  Equinix", "EQR  Equity Residential",
			"ESS  Essex Property Trust, Inc.", "EL  Estee Lauder Cos.", "EVRG  Evergy", "ES  Eversource Energy",
			"RE  Everest Re Group Ltd.", "EXC  Exelon Corp.", "EXPE  Expedia Group", "EXPD  Expeditors",
			"EXR  Extra Space Storage", "XOM  Exxon Mobil Corp.", "FFIV  F5 Networks", "FB  Facebook, Inc.",
			"FAST  Fastenal Co", "FRT  Federal Realty Investment Trust", "FDX  FedEx Corporation",
			"FIS  Fidelity National Information Services", "FITB  Fifth Third Bancorp", "FE  FirstEnergy Corp",
			"FRC  First Republic Bank", "FISV  Fiserv Inc", "FLT  FleetCor Technologies Inc", "FLIR  FLIR Systems",
			"FLS  Flowserve Corporation", "FMC  FMC Corporation", "FL  Foot Locker Inc", "F  Ford Motor",
			"FTNT  Fortinet", "FTV  Fortive Corp", "FBHS  Fortune Brands Home & Security",
			"FOXA  Fox Corporation Class A", "FOX  Fox Corporation Class B", "BEN  Franklin Resources",
			"FCX  Freeport-McMoRan Inc.", "GPS  Gap Inc.", "GRMN  Garmin Ltd.", "IT  Gartner Inc",
			"GD  General Dynamics", "GE  General Electric", "GIS  General Mills", "GM  General Motors",
			"GPC  Genuine Parts", "GILD  Gilead Sciences", "GPN  Global Payments Inc.", "GS  Goldman Sachs Group",
			"GWW  Grainger (W.W.) Inc.", "HAL  Halliburton Co.", "HBI  Hanesbrands Inc", "HOG  Harley-Davidson",
			"HIG  Hartford Financial Svc.Gp.", "HAS  Hasbro Inc.", "HCA  HCA Healthcare", "HCP  HCP Inc.",
			"HP  Helmerich & Payne", "HSIC  Henry Schein", "HSY  The Hershey Company", "HES  Hess Corporation",
			"HPE  Hewlett Packard Enterprise", "HLT  Hilton Worldwide Holdings Inc", "HFC  HollyFrontier Corp",
			"HOLX  Hologic", "HD  Home Depot", "HON  Honeywell Int'l Inc.", "HRL  Hormel Foods Corp.",
			"HST  Host Hotels & Resorts", "HPQ  HP Inc.", "HUM  Humana Inc.", "HBAN  Huntington Bancshares",
			"HII  Huntington Ingalls Industries", "IDXX  IDEXX Laboratories", "INFO  IHS Markit Ltd.",
			"ITW  Illinois Tool Works", "ILMN  Illumina Inc", "IR  Ingersoll-Rand PLC", "INTC  Intel Corp.",
			"ICE  Intercontinental Exchange", "IBM  International Business Machines", "INCY  Incyte",
			"IP  International Paper", "IPG  Interpublic Group", "IFF  Intl Flavors & Fragrances", "INTU  Intuit Inc.",
			"ISRG  Intuitive Surgical Inc.", "IVZ  Invesco Ltd.", "IPGP  IPG Photonics Corp.",
			"IQV  IQVIA Holdings Inc.", "IRM  Iron Mountain Incorporated", "JKHY  Jack Henry & Associates",
			"JEC  Jacobs Engineering Group", "JBHT  J. B. Hunt Transport Services", "JEF  Jefferies Financial Group",
			"SJM  JM Smucker", "JNJ  Johnson & Johnson", "JCI  Johnson Controls International",
			"JPM  JPMorgan Chase & Co.", "JNPR  Juniper Networks", "KSU  Kansas City Southern", "K  Kellogg Co.",
			"KEY  KeyCorp", "KEYS  Keysight Technologies", "KMB  Kimberly-Clark", "KIM  Kimco Realty",
			"KMI  Kinder Morgan", "KLAC  KLA Corporation", "KSS  Kohl's Corp.", "KHC  Kraft Heinz Co", "KR  Kroger Co.",
			"LB  L Brands Inc.", "LHX  L3Harris Technologies", "LH  Laboratory Corp. of America Holding",
			"LRCX  Lam Research", "LW  Lamb Weston Holdings Inc", "LEG  Leggett & Platt", "LEN  Lennar Corp.",
			"LLY  Lilly (Eli) & Co.", "LNC  Lincoln National", "LIN  Linde plc", "LKQ  LKQ Corporation",
			"LMT  Lockheed Martin Corp.", "L  Loews Corp.", "LOW  Lowe's Cos.", "LYB  LyondellBasell",
			"MTB  M&T Bank Corp.", "MAC  Macerich", "M  Macy's Inc.", "MRO  Marathon Oil Corp.",
			"MPC  Marathon Petroleum", "MKTX  MarketAxess", "MAR  Marriott Int'l.", "MMC  Marsh & McLennan",
			"MLM  Martin Marietta Materials", "MAS  Masco Corp.", "MA  Mastercard Inc.", "MKC  McCormick & Co.",
			"MXIM  Maxim Integrated Products Inc", "MCD  McDonald's Corp.", "MCK  McKesson Corp.", "MDT  Medtronic plc",
			"MRK  Merck & Co.", "MET  MetLife Inc.", "MTD  Mettler Toledo", "MGM  MGM Resorts International",
			"MCHP  Microchip Technology", "MU  Micron Technology", "MSFT  Microsoft Corp.",
			"MAA  Mid-America Apartments", "MHK  Mohawk Industries", "TAP  Molson Coors Brewing Company",
			"MDLZ  Mondelez International", "MNST  Monster Beverage", "MCO  Moody's Corp", "MS  Morgan Stanley",
			"MOS  The Mosaic Company", "MSI  Motorola Solutions Inc.", "MSCI  MSCI Inc", "MYL  Mylan N.V.",
			"NDAQ  Nasdaq, Inc.", "NOV  National Oilwell Varco Inc.", "NKTR  Nektar Therapeutics", "NTAP  NetApp",
			"NFLX  Netflix Inc.", "NWL  Newell Brands", "NEM  Newmont Goldcorp", "NWSA  News Corp. Class A",
			"NWS  News Corp. Class B", "NEE  NextEra Energy", "NLSN  Nielsen Holdings", "NKE  Nike",
			"NI  NiSource Inc.", "NBL  Noble Energy Inc", "JWN  Nordstrom", "NSC  Norfolk Southern Corp.",
			"NTRS  Northern Trust Corp.", "NOC  Northrop Grumman", "NCLH  Norwegian Cruise Line Holdings",
			"NRG  NRG Energy", "NUE  Nucor Corp.", "NVDA  Nvidia Corporation", "ORLY  O'Reilly Automotive",
			"OXY  Occidental Petroleum", "OMC  Omnicom Group", "OKE  ONEOK", "ORCL  Oracle Corp.", "PCAR  PACCAR Inc.",
			"PKG  Packaging Corporation of America", "PH  Parker-Hannifin", "PAYX  Paychex Inc.", "PYPL  PayPal",
			"PNR  Pentair plc", "PBCT  People's United Financial", "PEP  PepsiCo Inc.", "PKI  PerkinElmer",
			"PRGO  Perrigo", "PFE  Pfizer Inc.", "PM  Philip Morris International", "PSX  Phillips 66",
			"PNW  Pinnacle West Capital", "PXD  Pioneer Natural Resources", "PNC  PNC Financial Services",
			"PPG  PPG Industries", "PPL  PPL Corp.", "PFG  Principal Financial Group", "PG  Procter & Gamble",
			"PGR  Progressive Corp.", "PLD  Prologis", "PRU  Prudential Financial", "PEG  Public Serv. Enterprise Inc.",
			"PSA  Public Storage", "PHM  Pulte Homes Inc.", "PVH  PVH Corp.", "QRVO  Qorvo",
			"PWR  Quanta Services Inc.", "QCOM  QUALCOMM Inc.", "DGX  Quest Diagnostics",
			"RL  Ralph Lauren Corporation", "RJF  Raymond James Financial Inc.", "RTN  Raytheon Co.",
			"O  Realty Income Corporation", "REG  Regency Centers Corporation", "REGN  Regeneron Pharmaceuticals",
			"RF  Regions Financial Corp.", "RSG  Republic Services Inc", "RMD  ResMed",
			"RHI  Robert Half International", "ROK  Rockwell Automation Inc.", "ROL  Rollins Inc.",
			"ROP  Roper Technologies", "ROST  Ross Stores", "RCL  Royal Caribbean Cruises Ltd", "CRM  Salesforce.com",
			"SBAC  SBA Communications", "SLB  Schlumberger Ltd.", "STX  Seagate Technology", "SEE  Sealed Air",
			"SRE  Sempra Energy", "SHW  Sherwin-Williams", "SPG  Simon Property Group Inc", "SWKS  Skyworks Solutions",
			"SLG  SL Green Realty", "SNA  Snap-on", "SO  Southern Co.", "LUV  Southwest Airlines",
			"SPGI  S&P Global, Inc.", "SWK  Stanley Black & Decker", "SBUX  Starbucks Corp.", "STT  State Street Corp.",
			"SYK  Stryker Corp.", "STI  SunTrust Banks", "SIVB  SVB Financial", "SYMC  Symantec Corp.",
			"SYF  Synchrony Financial", "SNPS  Synopsys Inc.", "SYY  Sysco Corp.", "TMUS  T-Mobile US",
			"TROW  T. Rowe Price Group", "TTWO  Take-Two Interactive", "TPR  Tapestry, Inc.", "TGT  Target Corp.",
			"TEL  TE Connectivity Ltd.", "FTI  TechnipFMC", "TFX  Teleflex", "TXN  Texas Instruments",
			"TXT  Textron Inc.", "TMO  Thermo Fisher Scientific", "TIF  Tiffany & Co.", "TWTR  Twitter, Inc.",
			"TJX  TJX Companies Inc.", "TMK  Torchmark Corp.", "TSS  Total System Services",
			"TSCO  Tractor Supply Company", "TDG  TransDigm Group", "TRV  The Travelers Companies Inc.",
			"TRIP  TripAdvisor", "TSN  Tyson Foods", "UDR  UDR, Inc.", "ULTA  Ulta Beauty", "USB  U.S. Bancorp",
			"UAA  Under Armour Class A", "UA  Under Armour Class C", "UNP  Union Pacific Corp",
			"UAL  United Airlines Holdings", "UNH  United Health Group Inc.", "UPS  United Parcel Service",
			"URI  United Rentals, Inc.", "UTX  United Technologies", "UHS  Universal Health Services, Inc.",
			"UNM  Unum Group", "VFC  V.F. Corp.", "VLO  Valero Energy", "VAR  Varian Medical Systems",
			"VTR  Ventas Inc", "VRSN  Verisign Inc.", "VRSK  Verisk Analytics", "VZ  Verizon Communications",
			"VRTX  Vertex Pharmaceuticals Inc", "VIAB  Viacom Inc.", "V  Visa Inc.", "VNO  Vornado Realty Trust",
			"VMC  Vulcan Materials", "WAB  Wabtec Corporation", "WMT  Walmart", "WBA  Walgreens Boots Alliance",
			"DIS  The Walt Disney Company", "WM  Waste Management Inc.", "WAT  Waters Corporation",
			"WEC  Wec Energy Group Inc", "WCG  WellCare", "WFC  Wells Fargo", "WELL  Welltower Inc.",
			"WDC  Western Digital", "WU  Western Union Co", "WRK  WestRock", "WY  Weyerhaeuser", "WHR  Whirlpool Corp.",
			"WMB  Williams Cos.", "WLTW  Willis Towers Watson", "WYNN  Wynn Resorts Ltd", "XEL  Xcel Energy Inc",
			"XRX  Xerox", "XLNX  Xilinx", "XYL  Xylem Inc.", "YUM  Yum! Brands Inc", "ZBH  Zimmer Biomet Holdings",
			"ZION  Zions Bancorp", "ZTS  Zoetis" };

}