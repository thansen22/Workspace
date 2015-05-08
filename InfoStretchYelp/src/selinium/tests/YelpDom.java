package selinium.tests;

public class YelpDom {
	//TODO: May consider renaming String vars to contain an additional descriptor as some are less intuitive than others
	
	public static class Search {
		public static final String input_Id = "find_desc";
		
		public static class Suggestions {
			public static final String div_Css = "div.main-search_suggestions";
			public static final String ul_Css = "ul.suggestions-list";
			public static final String li_Css = "li.suggestion";
			public static final String li_Attribute = "data-suggestion";			
			public enum DataSuggestions {
				RESTAURANTS("Restaurants"), BARS("Bars"), COFFEE_TEA("Coffee & Tea"), DELIVERY("Delivery"), RESERVATIONS("Reservations")
				//For testing purposes
				, INVALID("Invalid")
				;
		        private String value;
		        private DataSuggestions(String value) {
		        	this.value = value;
		        }
		        public String getValue() {
		        	return this.value;
		        }
			};  
		}
	}
	
	public static class Results {
		
		public static class Header {
			public static final String h1_Css = "div.search-header > h1";			
			public static final String span_Css = "div.search-header > span";
		}
		
		public static class Filters {		
			public static final String Panel_div_Css = "div.filter-panel";
			public static final String div_Css = "div.filter-set";
			public static final String h4_TagName = "h4";
			public static final String input_TagName = "input";
			public static final String input_Attribute = "value";
			
			public enum FilterNames {
				SORTBY("Sort By"), NEIGHBORHOODS("Neighborhoods"), DISTANCE("Distance"), PRICE("Price"), FEATURES("Features"), CATEGORY("Category")
				//For testing purposes
				, INVALID("Invalid")
				;
				private String value;
		        private FilterNames(String value) {
		        	this.value = value;
		        }
		        public String getValue() {
		        	return this.value;
		        }
			}
			
			public interface FilterType { 
				String getValue();
				//TODO: could also create a validating function to ensure that the proper FilterType is used with the correct FilterName
			};
			public enum SortBy implements FilterType {
				BESTMATCH("best_match"), HIGHESTRATED("rating"), MOSTREVIEWED("review_count")
				//For testing purposes
				, INVALID("Invalid");
				private String value;
		        private SortBy(String value) {
		        	this.value = value;
		        }
		        public String getValue() {
		        	return this.value;
		        }
			}
			//TODO: skipping neighborhoods for now as it's dependent upon current location
			public enum Neighborhoods implements FilterType {
				INVALID("Invalid");
				private String value;
		        private Neighborhoods(String value) {
		        	this.value = value;
		        }
		        public String getValue() {
		        	return this.value;
		        }
			}
			//TODO: skipping neighborhoods for now as it's also dependent upon current location
			public enum Distance implements FilterType {
				INVALID("Invalid");
				private String value;
		        private Distance(String value) {
		        	this.value = value;
		        }
		        public String getValue() {
		        	return this.value;
		        }
			}
			public enum Price implements FilterType {
				ONEDOLLAR("RestaurantsPriceRange2.1"), TWODOLLAR("RestaurantsPriceRange2.2"), THREEDOLLAR("RestaurantsPriceRange2.3"), FOURDOLLAR("RestaurantsPriceRange2.4")
				//For testing purposes
				, INVALID("Invalid");
				private String value;
		        private Price(String value) {
		        	this.value = value;
		        }
		        public String getValue() {
		        	return this.value;
		        }
			}
			public enum Features implements FilterType {
				DELIVERY("PlatformDelivery"), RESERVATIONS("OnlineReservations"), DEAL("ActiveDeal"), OPENNOW("open_now")
				//For testing purposes
				, INVALID("Invalid");
				private String value;
		        private Features(String value) {
		        	this.value = value;
		        }
		        public String getValue() {
		        	return this.value;
		        }
			}			
		}
		
		public static class List {
			public static final String results_Css = "div.search-results-content"; 
			
			public static class Result {				
				public static final String result_Css = "div.search-result";
				public static final String i_Css = "i.star-img";
				public static final String i_Attribute = "title";				
				public static final String ad_Css = "span.yloca-tip";
				public static final String a_Css = "a.biz-name";
			}			
		}			
	}
	
	public static class Details {
		public static final String content_Css = "div.main-content-wrap";
		public static final String name_Css = "div.biz-page-header-left > h1";
		
		public static class Info {		
			public static final String address_Css = "div[class='media-story'] > address";
			public static final String address_tagName = "address";
			public static final String phone_Css = "span.biz-phone";
			public static final String website_Css = "div.biz-website > a";
		}
		
		public static class Reviews {
			public static final String container_Id = "super-container";
			//first 3 is ambiguous as there are 3 highlights, then the reviews
			//here is the highlights
			public static final String highlights_Css = "div.review-highlights > ul > li";
			//here is the reviews
			public static final String list_Css = "div.review-list > ul > li";
			public static final String review_Css = "div.review-content > p";
		}
	}
	

}
