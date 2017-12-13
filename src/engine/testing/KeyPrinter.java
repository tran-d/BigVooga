package engine.testing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.scene.input.KeyCode;

public class KeyPrinter {
	public static void main(String[] args) {
		Properties prop = new Properties();
		for (KeyCode code : KeyCode.values()) {
			prop.put(code.getName(), "Words");
		}

		try {
			FileOutputStream out = new FileOutputStream("src/engine/testing/codes.properties");
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to Generate");
		}
	}
}
