package gd.rf.kongzhongtitian.mmrd;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Scanner {
	public static List<Info> scanModsFolder() {
		List<Info> mods = new ArrayList<>();
		Path gameDir = FabricLoader.getInstance().getGameDir();
		Path modsDir = gameDir.resolve("mods");

		if (!Files.exists(modsDir) || !Files.isDirectory(modsDir)) {
			MMRD.LOGGER.warn("Mods folder not found: " + modsDir);
			return mods;
		}

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(modsDir, "*.jar")) {
			for (Path jarPath : stream) {
				Info info = parseJar(jarPath);
				if (info != null) {
					mods.add(info);
				}
			}
		} catch (IOException e) {
			MMRD.LOGGER.error("Error scanning mods folder", e);
		}
		return mods;
	}

	private static Info parseJar(Path jarPath) {
		String fileName = jarPath.getFileName().toString();
		long size;
		try {
			size = Files.size(jarPath);
		} catch (IOException e) {
			size = -1;
		}

		String modId = null, name = null, version = null, description = null;

		try (JarFile jarFile = new JarFile(jarPath.toFile())) {
			JarEntry entry = jarFile.getJarEntry("fabric.mod.json");
			if (entry != null) {
				try (InputStreamReader reader = new InputStreamReader(jarFile.getInputStream(entry))) {
					JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
					if (json.has("id")) modId = json.get("id").getAsString();
					if (json.has("name")) name = json.get("name").getAsString();
					if (json.has("version")) version = json.get("version").getAsString();
					if (json.has("description")) description = json.get("description").getAsString();
				}
			}
		} catch (IOException e) {
			MMRD.LOGGER.warn("Failed to read jar: " + fileName, e);
		}

		return new Info(fileName, size, modId, name, version, description);
	}
}
