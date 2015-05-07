package selinium.tests;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;

import static org.junit.Assert.*;
//NOTE: not using TestNG as that was not part of the requirements
//import org.testng.annotations.Test;

//TODO: write better assertion output
//TODO: create logger

public class YelpTests {
	public static void yelpSearchScenario(
			YelpDom.Search.Suggestions.DataSuggestions searchFilter, 
			String subFilter,
			YelpDom.Results.Filters.FilterNames resultsFilterName,
			YelpDom.Results.Filters.FilterType resultsFilterType) {
				
		// do some basic param checking
		checkParams(searchFilter, subFilter, resultsFilterName, resultsFilterType);		
		
		//Open up the test site
		WebDriver driver = new FirefoxDriver();		
		driver.get(YelpTestDriver.site);
		
		//mouse implementation of step #2-3
		WebElement suggestion = findSuggestion(searchFilter, driver);
		if (suggestion != null) {
			suggestion.click();
		}
		else {
			//TODO: write better assertion output
			fail(String.format("error: couldn't find the suggestion dropdown element provided: %s", searchFilter.getValue()));
		}
		
		//Keyboard implementation of step #2-3
//		Integer suggestionKeysDown = findSuggestion(filter, driver);
//		if (suggestionKeysDown != null) {
//			//hit the keyboard down arrow int number of times
//		}
//		else {
//			//TODO: write better assertion output
//			fail(String.format("error: couldn't find the suggestion dropdown element provided: %s", searchFilter.getValue()));
//		}
//		//now hit enter		
		
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
			
		//Now add the sub filter	
		addSubFilter(subFilter, driver);
		
		//Validate the new content that it says "Restaurants Pizza" now.  Need to poll as this content existed prior
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				String resultsHeaderText = driver.findElement(By.cssSelector(YelpDom.Results.Header.h1_Css)).getText();
				return !StringUtils.containsIgnoreCase(resultsHeaderText, String.format("%s %s", searchFilter.getValue(), subFilter));
			}
		});
		//TODO: need to assert this poll above

		setFilters(resultsFilterName, resultsFilterType, driver);
		
		reportResultNums(driver);
		
		driver.close();
	}

	private static void reportResultNums(WebDriver driver) {
		final String resultsPrefix = "Showing 1-";
		final String resultsMid = " of ";
		
		//find the original amount of results prior to the filter
		WebElement resultsCountElem = driver.findElement(By.cssSelector(YelpDom.Results.Header.span_Css));
		//WebElement resultsCountElem = resultsHeaderElem.findElement(By.className(YelpDom.Results.Header.span_Css));
		String rawResultsCount = resultsCountElem.getText();
		int numResultsDisplayed = Integer.parseInt(rawResultsCount.substring(resultsPrefix.length(), resultsPrefix.length() + 2));
		int totalNumResults = Integer.parseInt(rawResultsCount.substring(rawResultsCount.lastIndexOf(resultsMid) + resultsMid.length(), rawResultsCount.length()));
		
		//TODO: refactor this with a logger
		System.out.println(String.format("Displaying %d of %d results", numResultsDisplayed, totalNumResults));
	}

	private static void setFilters(
			YelpDom.Results.Filters.FilterNames resultsFilterName,
			YelpDom.Results.Filters.FilterType resultsFilterType,			
			WebDriver driver) {
		
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
			Thread.sleep(3000);
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
			filter.click();
		}
				
		//TODONOW: remove this terrible, terrible sleep.  Need to check for lack of spinning icon?
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void addSubFilter(String subFilter, WebDriver driver) {
		WebElement search = driver.findElement(By.id(YelpDom.Search.input_Id));
		search.click();
		search.sendKeys(Keys.ARROW_RIGHT);
		search.sendKeys(Keys.SPACE);
		search.sendKeys(subFilter);
		search.sendKeys(Keys.ENTER);
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
}
