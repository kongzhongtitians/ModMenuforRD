package gd.rf.kongzhongtitian.mmrd;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MMRD implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("mmrd");

	@Override
	public void onInitialize() {
		LOGGER.info("Loading MMRD...");
		GlobalKeyListener.start();
	}
}
