package dtg.dogretriever.Bluetooth;

public class Beacon {
    private String deviceAddress;
    private String id;
    private int latestRssi;
    private long lastDetectedTimestamp;

    public Beacon(String address, int rssi, String identifier, long timestamp) {
        this.deviceAddress = address;
        this.latestRssi = rssi;
        this.id = identifier;
        this.lastDetectedTimestamp = timestamp;
    }

    public void update(String address, int rssi, long timestamp) {
        this.deviceAddress = address;
        this.latestRssi = rssi;
        this.lastDetectedTimestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s\n%ddBm", id, latestRssi);
    }

    // Parse the instance id out of a UID packet
    public static String getInstanceId(byte[] data) {
        StringBuilder sb = new StringBuilder();

        //UID packets are always 18 bytes in length
        //Parse out the last 6 bytes for the id
        int packetLength = 18;
        int offset = packetLength - 6;
        for (int i=offset; i < packetLength; i++) {
            sb.append(Integer.toHexString(data[i] & 0xFF));
        }
        return sb.toString();
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public String getId() {
        return id;
    }

    public int getLatestRssi() {
        return latestRssi;
    }

    public long getLastDetectedTimestamp() {
        return lastDetectedTimestamp;
    }
}
