package base;

import java.io.*;

import game.helper.HelperInfo;

public final class Memory {

	private Memory() {
		
	}

	public static final File
			DATA_FOLDER = new File(System.getProperty("user.dir"), String.format("%s Data", Main.TITLE)),
			DATA_FILE;
	
	public static final long HELPER_INFO;
	
	static {
		tryCreatingDirIfDoesNotExist(DATA_FOLDER);
		DATA_FILE = new File(DATA_FOLDER, "Data.txt");
		tryCreatingIfDoesNotExist(DATA_FILE);
		HELPER_INFO = getHelperInfo();
	}

	private static synchronized void tryCreatingDirIfDoesNotExist(File dir) {
		if(!dir.exists())
			dir.mkdir();
	}
	
	private static synchronized void tryCreatingIfDoesNotExist(File f) {
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static synchronized long getHelperInfo() {
		if(!memoryFilesExist())
			return 0L;
		try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
			String line = br.readLine();
			if(line == null)
				return 0L;
			return Long.parseLong(line.trim());
		} catch (IOException | NumberFormatException e) {
			return 0L;
		}
	}
	
	public static boolean memoryFilesExist() {
		return DATA_FOLDER.exists() && DATA_FILE.exists();
	}
	
	public static void save() {
		long info = HelperInfo.get().info();
		try(FileWriter fw = new FileWriter(DATA_FILE)){
			fw.write(String.valueOf(info));
		} catch (IOException e) {
			System.err.println("Error writing data.");
			e.printStackTrace();
		}
	}
	
}