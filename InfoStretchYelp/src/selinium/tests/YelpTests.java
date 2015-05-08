package selinium.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.*;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;

import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

import static org.junit.Assert.*;
//NOTE: not using TestNG as that was not part of the requirements
//import org.testng.annotations.Test;

//TODO: write better assertion output
//TODO: create logger

public class YelpTests {
	private static final Logger log = Logger.getLogger(YelpTests.class.getName());
	private static final String logFile = "./log.xml";
	
	public static void yelpSearchScenario(
			YelpDom.Search.Suggestions.DataSuggestions searchFilter, 
			String subSearch,
			YelpDom.Results.Filters.FilterNames resultsFilterName,
			YelpDom.Results.Filters.FilterType resultsFilterType) {
				
		initializeLogger();
		log.info(String.format("Starting test with the following args:\nsearchFilter: %s\nsubSearch: %s\nfilterName: %s\nfilterType: %s", 
				searchFilter, subSearch, resultsFilterName.getValue(), resultsFilterType.getValue()));
		
		// do some basic param checking		
		checkParams(searchFilter, subSearch, resultsFilterName, resultsFilterType);		
		
		//Step #1: open site
		WebDriver driver = openSite();
		
		//Step #2-3: set filter
		setSuggestion(searchFilter, driver);
		
		//TODO: validate the page loaded prior to moving on to the next steps
//		WebElement resultsHeader = driver.findElement(By.cssSelector(YelpDom.Results.Header.div_Css));
		//wait/assert that the results come up (I think the creation of the WebElement does this polling... 	
//		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                return resultsHeader.isDisplayed();
//            }
//        });
		
		//Validate that search results header contains "Restaurants"
		String resultsHeaderText = driver.findElement(By.cssSelector(YelpDom.Results.Header.h1_Css)).getText();//resultsHeaderContent.getText();//.getAttribute("textContent");
		if (!StringUtils.containsIgnoreCase(resultsHeaderText, searchFilter.getValue())) {
			//TODO: use assert
			fail(String.format("error: couldn't find the results header text, found: %s instead", resultsHeaderText)); 
		}
				
		//Step #4: Add a subSearch
		addSubSearch(subSearch, driver);
		
		//Validate the new content that it says the correct search now.  Need to poll as this content existed prior
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				String resultsHeaderText = driver.findElement(By.cssSelector(YelpDom.Results.Header.h1_Css)).getText();
				return !StringUtils.containsIgnoreCase(resultsHeaderText, String.format("%s %s", searchFilter.getValue(), subSearch));
			}
		});
		//TODO: need to assert this poll above
		//TODONOW: BUG reports previous results... Step #5: Report the # of search results
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//Step #5: Report result numbers
		reportResultNums(driver);		
		
		//Step #6-7: Add a result filter
		setFilters(resultsFilterName, resultsFilterType, driver);
		
		//Step #8: Report result numbers
		reportResultNums(driver);
		
		//Step #9: Report star ratings
		List<WebElement> resultsList = reportStars(driver);		

		//Step #10: Expand the first results
		log.finer("Starting: Expand the first results");
		resultsList.get(0).findElement(By.cssSelector(YelpDom.Results.List.Result.a_Css)).click();
		log.finer("Completed: Expand the first results");
		//TODO: validation that the page loaded, though inherently it will in the next step
		
		//Step #11: Log all info for the result (Address, Phone, web site details, First 3 reviews)
		reportDetailedInfo(driver);		
		
		driver.close();
	}

	private static void reportDetailedInfo(WebDriver driver) {
		log.finer("Starting Step #11: Log all info for the result (Address, Phone, web site details, First 3 reviews)");

		String name = "Not Found";
		String address = "Not Found";
		String phone = "Not Found";
		String website = "Not Found";

		try {
			name = driver.findElement(By.cssSelector(YelpDom.Details.name_Css)).getText();		
		} catch (NoSuchElementException e) {
			//no-op
		} try {
			address = driver.findElements(By.tagName(YelpDom.Details.Info.address_tagName)).get(1).getText();
		} catch (NoSuchElementException e) {
			//no-op
		} try {
			phone = driver.findElement(By.cssSelector(YelpDom.Details.Info.phone_Css)).getText();
		} catch (NoSuchElementException e) {
			//no-op
		}
		try {
			website = driver.findElement(By.cssSelector(YelpDom.Details.Info.website_Css)).getText();
		} catch (NoSuchElementException e) {
			//no-op
		}
		
		log.info(String.format("Name: %s", name));
		log.info(String.format("Address: %s", address));
		log.info(String.format("Phone: %s", phone));
		log.info(String.format("Website: %s", website));
		
		WebElement reviewsContent = driver.findElement(By.id(YelpDom.Details.Reviews.container_Id));
		List<WebElement> reviews = reviewsContent.findElements(By.cssSelector(YelpDom.Details.Reviews.list_Css));
		for (int i = 0; i < reviews.size() && i < 3; i++) {
			String review = reviews.get(i).findElement(By.cssSelector(YelpDom.Details.Reviews.review_Css)).getText();
			log.info(String.format("Review #%d:\n%s\n", i+1, review));
		}
		
		log.finer("Completed Step #11: Log all info for the result (Address, Phone, web site details, First 3 reviews)");
	}

	private static List<WebElement> reportStars(WebDriver driver) {
		log.finer("Starting: Report star ratings");
		
		WebElement resultsContent = driver.findElement(By.cssSelector(YelpDom.Results.List.results_Css));
		List<WebElement> resultsList = resultsContent.findElements(By.cssSelector(YelpDom.Results.List.Result.result_Css));
		List<WebElement> finalResultsList = new ArrayList<>();
		ArrayList<String> ratings = new ArrayList<>();
		for (WebElement result : resultsList) {
			try {
				WebElement possibleAd = result.findElement(By.cssSelector(YelpDom.Results.List.Result.ad_Css));				
				continue;
			} catch (NoSuchElementException e) {
				//Do nothing				
			}
			
			finalResultsList.add(result);
			WebElement ratingElement = result.findElement(By.cssSelector(YelpDom.Results.List.Result.i_Css));
			String ratingString = ratingElement.getAttribute(YelpDom.Results.List.Result.i_Attribute);
			try {
				Double rating = Double.parseDouble(ratingString.substring(0, ratingString.indexOf(" ")));
				ratings.add(rating.toString());
			} catch (NumberFormatException e) {
				ratings.add("Not Found");
			}			
		}
		log.info(String.format("Ratings for the results %s", ratings.toString()));
		log.finer("Completed: Report star ratings");
		return finalResultsList;
	}

	private static void reportResultNums(WebDriver driver) {
		log.finer("Starting: Report result numbers");
		
		final String resultsPrefix = "Showing 1-";
		final String resultsMid = " of ";
		
		//find the original amount of results prior to the filter
		WebElement resultsCountElem = driver.findElement(By.cssSelector(YelpDom.Results.Header.span_Css));
		//WebElement resultsCountElem = resultsHeaderElem.findElement(By.className(YelpDom.Results.Header.span_Css));
		String rawResultsCount = resultsCountElem.getText();
		int numResultsDisplayed = Integer.parseInt(rawResultsCount.substring(resultsPrefix.length(), resultsPrefix.length() + 2));
		int totalNumResults = Integer.parseInt(rawResultsCount.substring(rawResultsCount.lastIndexOf(resultsMid) + resultsMid.length(), rawResultsCount.length()));
				
		log.info(String.format("Results shown on this page: %d", numResultsDisplayed));
		log.info(String.format("Total number of results: %d" , totalNumResults));
		
		log.finer("Completed: Report result numbers");
	}

	private static void setFilters(
			YelpDom.Results.Filters.FilterNames resultsFilterName,
			YelpDom.Results.Filters.FilterType resultsFilterType,			
			WebDriver driver) {
		log.finer("Starting: Add a result filter");
		
		WebElement filterPanel = driver.findElement(By.cssSelector(YelpDom.Results.Filters.Panel_div_Css));
		
		//TODO: it appears that the filters are dynamically loaded so we need a way to wait for all of them to load before getting them
		//I'm not sure, but there has to be a better way to do this, and it still doesn't work as apparently yelp is loading/replacing elements rather than slow to load them
//		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//			public Boolean apply(WebDriver d) {
//				List<WebElement> filters = filterPanel.findElements(By.cssSelector(YelpDom.Results.Filters.div_Css));
//				return filters.size() == 6;
//			}
//		});
		//TODONOW: remove this terrible, terrible sleep
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		List<WebElement> filters = filterPanel.findElements(By.cssSelector(YelpDom.Results.Filters.div_Css));		
		WebElement filter = null;
		for (WebElement f : filters) {
			String filterName = f.findElement(By.tagName(YelpDom.Results.Filters.h4_TagName)).getText(); 
			if (filterName.equalsIgnoreCase(resultsFilterName.getValue())) {
				List<WebElement> options = f.findElements(By.tagName(YelpDom.Results.Filters.input_TagName));
				for (WebElement o : options) {
					//System.out.println(o.getAttribute(YelpDom.Results.Filters.input_Attribute));
					if (o.getAttribute(YelpDom.Results.Filters.input_Attribute).equalsIgnoreCase(resultsFilterType.getValue())) {
						filter = o;
					}
				}
			}
		}
		
		//Check that 
		if (resultsFilterName != null && filter == null) {
			fail(String.format("Couldn't find the option: %s for filter: %s", resultsFilterType.getValue(), resultsFilterName.getValue()));
		}
		
		//only click on the ones defined 
		if (resultsFilterName != null && !filter.isSelected()) {

			try {
				filter.click();
			}
			//Turns out that Webdriver has some issues with the way some of the links Yelp has constructed so when that happens we have to use a simulated click instead
			catch (ElementNotVisibleException e) {
				log.warning("Using an emulated click for the filter as it failed");
 
				JavascriptExecutor js = (JavascriptExecutor)driver; 
				js.executeScript("arguments[0].click();", filter);
				//JavascriptLibrary jsLib = new JavascriptLibrary();
				//jsLib.callEmbeddedSelenium(driver, "triggerMouseEventAt", filter, "click", ".5,.5");
			}
			
			
		}
				
		//TODONOW: remove this terrible, terrible sleep.  Need to check for lack of spinning icon?
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.finer("Completed: Add a result filter");
	}

	private static void addSubSearch(String subFilter, WebDriver driver) {
		log.finer("Starting: Add a subSearch");			
		
		WebElement search = driver.findElement(By.id(YelpDom.Search.input_Id));
		search.click();
		search.sendKeys(Keys.ARROW_RIGHT);
		search.sendKeys(Keys.SPACE);
		search.sendKeys(subFilter);
		search.sendKeys(Keys.ENTER);
		
		log.finer("Completed: Add a subSearch");
	}

	private static WebElement findSuggestion(
//	private static Integer findSuggestion(
			YelpDom.Search.Suggestions.DataSuggestions filter, WebDriver driver) {
		//Click on the search box
		WebElement search = driver.findElement(By.id(YelpDom.Search.input_Id));
		search.click();		
		WebElement suggestionsDiv = driver.findElement(By.cssSelector(YelpDom.Search.Suggestions.div_Css));

		//wait/assert that the "dropdown" comes up (I think the creation of the WebElement does this polling... 
//		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                return suggestionsDiv.isDisplayed();
//            }
//        });
		
		//Validate the suggestion options
		//Some hierarchical selection of the li's based on the parent div/ul
		WebElement suggestionUl = suggestionsDiv.findElement(By.cssSelector(YelpDom.Search.Suggestions.ul_Css));
		List<WebElement> suggestions = suggestionUl.findElements(By.cssSelector(YelpDom.Search.Suggestions.li_Css));

		//TODO: we could validate the suggestions and parameterize this as part of the inputs to check for, with the values to check for, but not a part of requirements
		
		//Mouse click code
		WebElement suggestion = null;
		for (WebElement e : suggestions) {
			//find the element we are looking for from the params
			if (e.getAttribute(YelpDom.Search.Suggestions.li_Attribute).equalsIgnoreCase(filter.getValue())) {
				suggestion = e;
			}
		}
		return suggestion;

//		//Keyboard code
//		Integer arrowKeysDown = null;
//		for (int i = 0; i < suggestions.size(); i++) {
//			//find the element we are looking for from the params
//			if (suggestions.get(i).getAttribute(YelpDom.Search.Suggestions.li_Attribute).equalsIgnoreCase(filter.getValue())) {
//				arrowKeysDown = i + 1;
//			}
//		}
//		return arrowKeysDown;
	}
	
	private static void setSuggestion(YelpDom.Search.Suggestions.DataSuggestions searchFilter, WebDriver driver) {
		log.finer("Step #2-3 Starting: Choose a suggestion");
		
		WebElement suggestion = findSuggestion(searchFilter, driver);
		if (suggestion != null) {
			suggestion.click();			
		}
		else {
			//TODO: write better assertion output
			fail(String.format("error: couldn't find the suggestion dropdown element provided: %s", searchFilter.getValue()));
		}
		
		log.finer("Step #2-3 Completed : Choose a suggestion");
	}

	private static WebDriver openSite() {
		log.finer("Step #1 Starting: Open up the test site");
		
		WebDriver driver = new FirefoxDriver();		
		driver.get(YelpTestDriver.site);
		
		log.finer("Step #1 Completed: Open up the test site");
		return driver;
	}

	private static void checkParams(
			YelpDom.Search.Suggestions.DataSuggestions searchFilter,
			String subFilter,
			YelpDom.Results.Filters.FilterNames resultsFilterName,
			YelpDom.Results.Filters.FilterType resultsFilterType) {
		if (searchFilter == null) {
			fail("You must supply an initial filter");
		}
		if (subFilter == null || subFilter.equals("")) {
			fail("You must supply a subFilter");
		}
		if (resultsFilterName != null && resultsFilterType == null) {
			fail("You must supply a filter type if you supply a filter name");
		}
	}

	private static void initializeLogger() {
		Handler fh = null;
		try {
			fh = new FileHandler(logFile);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		log.addHandler(fh);
		log.setLevel(Level.FINER);
	}	
}
