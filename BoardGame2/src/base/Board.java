package base;

public interface Board {

	BoardImpl INSTANCE = new BoardImpl();
	
	static void add(ImagePane ip) {
		INSTANCE.add(ip);
	}
	
	static void updateImageLayoutCoords(ImagePane ip) {
		INSTANCE.updateImageLayoutCoords(ip);
	}
	
	static void updateImageSize(ImagePane ip) {
		INSTANCE.updateImageSize(ip);
	}
	
}
