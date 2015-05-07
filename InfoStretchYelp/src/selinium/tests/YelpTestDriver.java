package selinium.tests;

public class YelpTestDriver {
	public static final String site = "http://www.yelp.com";	
	
	public static void main(String[] args) {
		//Test case #1
		YelpTests.yelpSearchScenario(YelpDom.Search.Suggestions.DataSuggestions.RESTAURANTS, 
									"Pizza",
									YelpDom.Results.Filters.FilterNames.PRICE,
									YelpDom.Results.Filters.Price.ONEDOLLAR);
	}
}
