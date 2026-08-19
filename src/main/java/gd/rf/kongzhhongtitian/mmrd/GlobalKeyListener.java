package gd.rf.kongzhongtitian.mmrd;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalKeyListener implements NativeKeyListener {
	private static boolean initialized = false;
	private static GlobalKeyListener instance;

	public static synchronized void start() {
		if (initialized) return;
		Thread thread = new Thread(() -> {
			try {
				Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
				logger.setLevel(Level.WARNING);
				logger.setUseParentHandlers(false);

				GlobalScreen.registerNativeHook();
				instance = new GlobalKeyListener();
				GlobalScreen.addNativeKeyListener(instance);
				initialized = true;
				MMRD.LOGGER.info("Global key listener registered. Press M to show mod info, ESC to stop listener.");
			} catch (NativeHookException e) {
				MMRD.LOGGER.error("Failed to register global key listener", e);
			}
		}, "MMRD-KeyListener");
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_M) {
			SwingUtilities.invokeLater(Window::showWindow);
		} else if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			stop();
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {}

	private synchronized void stop() {
		if (!initialized) {
			return;
		}
		try {
			GlobalScreen.removeNativeKeyListener(this);
			GlobalScreen.unregisterNativeHook();
			initialized = false;
			MMRD.LOGGER.info("Global key listener stopped by ESC key.");
		} catch (NativeHookException ex) {
			MMRD.LOGGER.error("Failed to unregister native hook", ex);
		}
	}
}
