public class InputOptions {
	
	public String termsPath;
	public String outPath;
	public InnerOptions options;
	
	public InputOptions(String termsPath, String outPath, InnerOptions options) {
		
		this.termsPath = termsPath;
		this.outPath = outPath;
		this.options = options;
	}
	
	public class InnerOptions{
	
		public boolean reverse;
		public boolean natLang;
		public InnerOptions(boolean reverse, boolean natLang) {
			
			this.reverse=reverse;
			this.natLang=natLang;
		}
	}
}
