import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Files {
	public List<String> getDistinctWordList(String fileName) {
		BufferedReader br = null;
		List<String> wordList = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(fileName));
			// Another way to stream data but from standard input
			//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			String line = null;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, " ,.;:\"");
				while (st.hasMoreTokens()) {
					String tmp = st.nextToken().toLowerCase();
					if (!wordList.contains(tmp)) {
						wordList.add(tmp);
					}
				}
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try{if(br != null) br.close();}catch(Exception ex){}
		}
		return wordList;
	}

	public static void main(String a[]) {
		Files distFw = new Files();
		List<String> wordList = distFw.getDistinctWordList("/home/timmay/Documents/test");//"/media/timmay/60CEFB61CEFB2E42/test");// "/home/timmay/Documents/test");//"C:/test");
		//foreach equiv
		for(String str:wordList) {
			System.out.println(str);
		}
	}
}