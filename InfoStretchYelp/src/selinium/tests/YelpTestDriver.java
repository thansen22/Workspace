package selinium.tests;

import java.util.logging.Logger;

public class YelpTestDriver {
	public static final String site = "http://www.yelp.com";	
	
	public static void main(String[] args) {
		//Test case #1
		YelpTests.yelpSearchScenario(
				YelpDom.Search.Suggestions.DataSuggestions.RESTAURANTS, 
				"Pizza",
				YelpDom.Results.Filters.FilterNames.PRICE,
				YelpDom.Results.Filters.Price.ONEDOLLAR);
		
		//Test case #2
		YelpTests.yelpSearchScenario(
				YelpDom.Search.Suggestions.DataSuggestions.BARS, 
				"Irish",
				YelpDom.Results.Filters.FilterNames.PRICE,
				YelpDom.Results.Filters.Price.TWODOLLAR);
		
		//Test case #3
		YelpTests.yelpSearchScenario(
				YelpDom.Search.Suggestions.DataSuggestions.COFFEE_TEA, 
				"Bubble",
				YelpDom.Results.Filters.FilterNames.SORTBY,
				YelpDom.Results.Filters.SortBy.MOSTREVIEWED);
	}
}
