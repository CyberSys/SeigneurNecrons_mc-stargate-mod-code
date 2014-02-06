package scripts;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<String> argList = Lists.newArrayList(args);
		
		if(argList.contains("--generateResourcePacks")) {
			new GenerateResourcePacks(args);
		}
		
		if(argList.contains("--generateModsAndSrcZips")) {
			new GenerateModsAndSrcZip(args);
		}
		
		if(argList.contains("--mcp")) {
			new CopyResourcePacksToMcp(args);
		}
		
		if(argList.contains("--dropbox")) {
			new CopyAllZipsToDropBox(args);
		}
		
		if(argList.contains("--minecraft")) {
			new CopyAllZipsToMinecraft(args);
		}
	}
	
}
