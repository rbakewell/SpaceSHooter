package SpaceShooter;

import java.io.InputStream;

final public class ImageLoader {
	public InputStream load(String path) {
		InputStream input = ImageLoader.class.getResourceAsStream(path);
		if(input == null)
			ImageLoader.class.getResourceAsStream("/"+path);
		return input;
		
	}
}
