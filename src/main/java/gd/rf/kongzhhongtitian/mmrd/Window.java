package gd.rf.kongzhongtitian.mmrd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Window {
	private static JFrame frame;

	public static void showWindow() {
		if (frame != null && frame.isVisible()) {
			frame.toFront();
			frame.requestFocus();
			return;
		}

		frame = new JFrame("Mod Menu for RD");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 400);
		frame.setLocationRelativeTo(null);

		// Table model
		String[] columnNames = {"File Name", "Size", "Mod ID", "Name", "Version", "Description"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		List<Info> mods = Scanner.scanModsFolder();
		for (Info mod : mods) {
			model.addRow(new Object[]{
				mod.getFileName(),
				mod.getFileSizeFormatted(),
				mod.getModId(),
				mod.getName(),
				mod.getVersion(),
				mod.getDescription()
			});
		}

		JTable table = new JTable(model);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(300);

		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(scrollPane, BorderLayout.CENTER);

		// Refresh button
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(e -> {
			model.setRowCount(0);
			List<Info> refreshed = Scanner.scanModsFolder();
			for (Info mod : refreshed) {
				model.addRow(new Object[]{
					mod.getFileName(),
					mod.getFileSizeFormatted(),
					mod.getModId(),
					mod.getName(),
					mod.getVersion(),
					mod.getDescription()
				});
			}
		});
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(refreshButton);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}
}
