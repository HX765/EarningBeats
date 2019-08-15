# README

## EarningsBeats

### Project description

When you hear that a company has "missed estimates" or "beaten estimates," these are references to consensus estimates. Based on projections, models, sentiments and research, analysts strive to come up with an estimate of what the company will do in the future. Consensus estimates can be found in stock quotations or summaries in common places, such as the Wall Street Journal, Bloomberg, Morningstar, and Google Finance, among other locations.

Our tool is designed to compare public companies reported earnings and consensus estimate and present a direct visualization to user. Users of our tool can utilize the data points analysis to make investment decisions. This tool can also be used by financial statement preparer to verify the alignment of quarterly earnings.

The tool takes a ticker symbol selected by the user, extracts reported earnings data from IEX Cloud and consensus estimate of analysts from IEX Cloud, and visualize (quarter by default) the comparison of reported earnings and consensus estimate that company.

Presented by: Han Xiao, Chao (Winnie) Qiu, Mengqing Shi


### User manual
A user need to have an account of IEX cloud. You can get a free account to use our program.
A user can load the application, enter a stock ticket to call the IEX cloud API and our program will display the last 4 quarter actual EPS and consensus EPS data for this stock.

## Setup
Please ensure you have the following installed in IDE:
- [x] [Java 12](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html)
- [x] [Controls FX library](https://github.com/cit-591/final-project-summer-2019-earning-beats/blob/master/controlsfx-8.40.11.jar)
- [x] [JSON library](https://github.com/cit-591/final-project-summer-2019-earning-beats/blob/master/java-json.jar)


#### Addition setup for MacOS user
- [X] [JavaFX 12 library](https://openjfx.io) 
- [X] Add following VM arguments:
--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.graphics,javafx.fxml,javafx.base --add-exports=javafx.base/com.sun.javafx.event=ALL-UNNAMED



## API Key Setup
- API token is reserved for the grader. It will be removed when published. To use our software, please register and obtain an individual API token. Follow the steps below.
- Register an account on [IEX cloud](https://iexcloud.io).
- Replace the token placeholder with your private API token in the "EarningData.java" file.

Note: Free account has 500,000 message limit per month. Free account has access to one quarter data. You can use our tool with a free account. To optimize the full functionality of our tool, please upgrade your account to premium.

## Use the app
Clone the repository to local repository. Install all the required libraries (json and controlsfx). Navigate to source folder and run the **GUIMain.java** file. A window will pop up with default view.

Enter a stock ticker to the input textfield. We enabled auto complete function to help you find the right ticker. For example, enter "Go", the auto completion will return GOOG, which stands for Google. If no ticket is suggested for you, please enter the exact ticket of the company you would like to search. You can utilize [IEX Eligible Symbols](https://iextrading.com/trading/eligible-symbols/) to locate your search. This search is not case sensitive, but all letter has to be exact correct. Once enter the data, click the search button to start the program. For each search, our program will display the reported EPS and consensus EPS. A consensus EPS is the average of numbers of analysts' estimates. If there is no data returned for your search, there will be an error message displayed at the top of the chart.

Now, get ready to view the plot!

Once you are ready to move on to the next graph, clear the graph by clicking the clear button and you are ready to search the next stock earning.

Enjoy!

![image](/assets/image.png)
![image](/assets/image2.png)
