import java.util.ArrayList;

public class ArrayDef {
	
	ArrayList<Definitions>array;
		
		public ArrayDef(ArrayList<Definitions>array) {
			this.array=array;
		}
		
		public class Definitions{
			public String word;
			public String def;
			public String imageUrl;
			
			public Definitions(String word, String def, String imageUrl) {
				this.word=word;
				this.def=def;
				this.imageUrl=imageUrl;
			}
		}
	}

