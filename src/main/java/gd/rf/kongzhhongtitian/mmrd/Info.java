package gd.rf.kongzhongtitian.mmrd;

public class Info {
	private final String fileName;
	private final long fileSize;
	private final String modId;
	private final String name;
	private final String version;
	private final String description;

	public Info(String fileName, long fileSize, String modId, String name,
				   String version, String description) {
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.modId = modId;
		this.name = name;
		this.version = version;
		this.description = description;
	}

	// Getters
	public String getFileName() { return fileName; }
	public long getFileSize() { return fileSize; }
	public String getModId() { return modId != null ? modId : "N/A"; }
	public String getName() { return name != null ? name : "N/A"; }
	public String getVersion() { return version != null ? version : "N/A"; }
	public String getDescription() { return description != null ? description : ""; }

	public String getFileSizeFormatted() {
		if (fileSize < 1024) return fileSize + " B";
		if (fileSize < 1024 * 1024) return String.format("%.1f KB", fileSize / 1024.0);
		return String.format("%.1f MB", fileSize / (1024.0 * 1024.0));
	}
}
