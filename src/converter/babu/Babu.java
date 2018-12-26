import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Babu {

	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {		
		Gson gson = new Gson();
		InputOptions bool = gson.fromJson(new FileReader(args[0]), InputOptions.class);
		
		boolean reversal= bool.options.reverse;
		boolean natLang = bool.options.natLang;
		String inputPath = bool.termsPath;
		String outPath = bool.outPath;

		String[][] wordsToDef = extract1(inputPath,reversal);
		
		//write(wordsToDef, "wordsDefs.txt");

		//System.out.println(Arrays.deepToString(wordsToDef).replace("], ", "]\n")
		// .replace("[[", "[").replace("]]", "]"));
		// System.out.println();
		 String[][] generated;
		 if(natLang) {
			 generated = generateWithNatLang(wordsToDef, outPath);

		 }else{
			 generated = regularGenerate(wordsToDef, outPath);
		 }

		 if (generated != null) {

			 System.out.println();
			 System.out.println(Arrays.deepToString(generated).replace("], ", "]\n").replace("[[", "[")
				.replace("]]", "]"));
		 }

	  

	

		//write(generated, "generated.txt");

	}
	//No Longer Needed, Advent of Json Deserializaiton has rendered this method obsolete
	//caveman age over
	
	/*private static boolean reverseCheck(File n)  {
		FileReader babu;
		try {
			babu = new FileReader(n.toString());
			BufferedReader br = new BufferedReader(babu);
			String input;
			while ((input = br.readLine()) != null) {

				List<String> items=Arrays.asList(input.split("\\:"));

				//System.out.println(items.toString());

				

				if(items.contains("Reverse")&&items.contains("1")) {
					br.close();
					return true;
				}else if(items.contains("Reverse")&&items.contains("0")) {
					br.close();
					return false;
				}
				// System.out.println(Arrays.toString(words));
				
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	*/
	//Might be used in future, currently not utilized
	private static void write(String[][] array, String path)
	{
		try (PrintStream out = new PrintStream(new FileOutputStream(path))){
		
			for (int i = 0; i < array.length; i++)
			{
				String joined = String.join("|", array[i]);
				out.println(joined);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[][] extract1(String inputPath,boolean stat) throws JsonSyntaxException, JsonIOException, FileNotFoundException {

			Gson gson = new Gson();
			ArrayDef bool2nd = gson.fromJson(new FileReader(inputPath), ArrayDef.class);
			String[][] defsTerms = new String[2][bool2nd.array.size()];
			
			for(int i=0;i<bool2nd.array.size();i++) {
				if(stat==false) {
					defsTerms[1][i] = bool2nd.array.get(i).word;
					defsTerms[0][i] = bool2nd.array.get(i).def;
				}else if(stat==true) {
					defsTerms[0][i] = bool2nd.array.get(i).word;
					defsTerms[1][i] = bool2nd.array.get(i).def;
				}
				
			}
			return defsTerms;
			
			
}

	public static String[][] regularGenerate(String[][] wordsToDef, String outPath){
		Random rand = new Random();		
		int amount = wordsToDef[0].length;
		String[][] randOrder = new String[5][amount];
		
		for (int p = 0; p < amount; p++)
		{
			randOrder[0][p] = wordsToDef[1][p];
		}
		
		for (int i = 0; i < amount; i++)
		{
			ArrayList<Integer> empty = new ArrayList<>();

			for(int z = 0; z < amount; z++) {
				empty.add(z);
			}
			Collections.shuffle(empty);

			if (empty.indexOf(i) > 3)
			{
				int answerIdx = rand.nextInt(4);
				empty.set(answerIdx, i);
			}

			for (int j = 1; j < 5; j++)
			{
				randOrder[j][i]=wordsToDef[0][empty.get(j-1)];
				
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		List<finalPrint> why = new ArrayList<>();
		
		for(int h=0;h<randOrder[0].length;h++){
		
			List<defBool> boolMan = new ArrayList<>();
		
			for(int v = 1; v <=4; v++) {
			
				if(randOrder[v][h].equals(wordsToDef[0][h])) {
					
					boolMan.add(new defBool(randOrder[v][h],true));
				
				}else if(!randOrder[v][h].equals(wordsToDef[0][h])) {
				
					boolMan.add(new defBool(randOrder[v][h],false));
			}
			
		}
		
			finalPrint bool = new finalPrint(randOrder[0][h],boolMan);
		
		
		
		why.add(bool);
		
		}
		String printStuff = gson.toJson(why);
		System.out.println(printStuff);
		
		try(FileWriter file = new FileWriter(outPath)){
			file.write(printStuff);
			file.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return randOrder;
	}

	
	public static String[][] generateWithNatLang(String[][] wordsToDef, String outPath)
	{
		HashMap<Integer,String> hmap= new HashMap<Integer,String>();
		for(int i=0;i<wordsToDef[0].length;i++) {
			try {
				hmap.put(i, NatLang.analyzeEntitiesText(wordsToDef[0][i]));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int [] entityTrack = {0,0,0,0,0,0};
		
		for(Integer name : hmap.keySet()) {
			String key = name.toString();
			String value = hmap.get(name).toString();
			if(value.equals("PERSON")) {
				entityTrack[0]+=1;
			}else if(value.equals("LOCATION")) {
				entityTrack[1]+=1;
			}else if(value.equals("ORGANIZATION")) {
				entityTrack[2]+=1;
			}else if(value.equals("EVENT")) {
				entityTrack[3]+=1;
			}else if(value.equals("WORK_OF_ART")) {
				entityTrack[4]+=1;
			}else if(value.equals("CONSUMER_GOOD")) {
				entityTrack[5]+=1;
			}
			System.out.println(key + ": "+ value);
		}
		//System.out.println(Arrays.toString(entityTrack));
		Random rand = new Random();		
		int amount = wordsToDef[0].length;
		String[][] randOrder = new String[5][amount];
		
		for (int p = 0; p < amount; p++)
		{
			randOrder[0][p] = wordsToDef[1][p];
		}
		
		for (int i = 0; i < amount; i++)
		{
			String runner = hmap.get(i);
			int count = 0;
			if(runner.equals("PERSON")&&entityTrack[0]>=4) {
				count=entityTrack[0];
			}else if(runner.equals("LOCATION")&&entityTrack[1]>=4) {
				count=entityTrack[1];
			}else if(runner.equals("ORGANIZATION")&&entityTrack[2]>=4) {
				count=entityTrack[2];
			}else if(runner.equals("EVENT")&&entityTrack[3]>=4) {
				count=entityTrack[3];
			}else if(runner.equals("WORK_OF_ART")&&entityTrack[4]>=4) {
				count=entityTrack[4];
			}else if(runner.equals("CONSUMER_GOOD")&&entityTrack[5]>=4) {
				count=entityTrack[5];
			}else if(count<4){
				ArrayList<Integer> empty = new ArrayList<>();

			for(int z = 0; z < amount; z++) {
				empty.add(z);
			}
			Collections.shuffle(empty);

			if (empty.indexOf(i) > 3)
			{
				int answerIdx = rand.nextInt(4);
				empty.set(answerIdx, i);
			}

			for (int j = 1; j < 5; j++)
			{
				randOrder[j][i]=wordsToDef[0][empty.get(j-1)];
				
			}
			}
			if(count>=4) {
				
				ArrayList<Integer> empty = new ArrayList<>();
				int check=0;
				Map<String, ArrayList<Integer>> reverseMap = new HashMap<>(
					    hmap.entrySet().stream()
					        .collect(Collectors.groupingBy(Map.Entry::getValue)).values().stream()
					        .collect(Collectors.toMap(
					                item -> item.get(0).getValue(),
					                item -> new ArrayList<>(
					                    item.stream()
					                        .map(Map.Entry::getKey)
					                        .collect(Collectors.toList())
					                ))
					        ));
				

				while(check<3) {
					Object[]temp=reverseMap.get(runner).toArray();
					//System.out.println(Arrays.toString(temp));
					int[]bob=new int[temp.length];
					for(int k=0;k<temp.length;k++) {
						bob[k]=(int)temp[k];
					}
					
					int gamble = rand.nextInt(bob.length);
					if(hmap.get(bob[gamble]).equals(runner)&&!empty.contains(bob[gamble]) && bob[gamble]!=i) {
						empty.add(bob[gamble]);
						check++;
					}
				}
				empty.add(i);
				Collections.shuffle(empty);
				//System.out.println(empty.toString());
				for (int j = 1; j < 5; j++)
				{
					randOrder[j][i]=wordsToDef[0][empty.get(j-1)];
					
				}
			}
			
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		List<finalPrint> why = new ArrayList<>();
		
		for(int h=0;h<randOrder[0].length;h++){
		
			List<defBool> boolMan = new ArrayList<>();
		
			for(int v = 1; v <=4; v++) {
			
				if(randOrder[v][h].equals(wordsToDef[0][h])) {
					
					boolMan.add(new defBool(randOrder[v][h],true));
				
				}else if(!randOrder[v][h].equals(wordsToDef[0][h])) {
				
					boolMan.add(new defBool(randOrder[v][h],false));
			}
			
		}
		
			finalPrint bool = new finalPrint(randOrder[0][h],boolMan);
		
		
		
		why.add(bool);
		
		}
		String printStuff = gson.toJson(why);
		System.out.println(printStuff);
		
		try(FileWriter file = new FileWriter(outPath)){
			file.write(printStuff);
			file.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return randOrder;
		}
	}

