package benTho.horizonMinecraftJump;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HighscoreManager {
    public HashMap<String, Integer> highscoreMap = new HashMap();

    public HighscoreManager() {
        highscoreMap.put("o1", 0);
        highscoreMap.put("o2", 0);
        highscoreMap.put("o3", 0);
        highscoreMap.put("n1", 0);
        highscoreMap.put("n2", 0);
        highscoreMap.put("n3", 0);
        highscoreMap.put("e1", 0);
        highscoreMap.put("e2", 0);
        highscoreMap.put("e3", 0);
    }

    private File _myFile = null;

    // Loads or create a .txt file that contains all files.
    // Fixed location at %APPDATA%/local/ben.tho/HorizonMinecraftJump/Highscores.txt
    public void loadOrCreateHighscoreFile() throws IOException {
        Files.createDirectories(Paths.get(System.getenv("LocalAppData") + "/ben.tho/HorizonMinecraftJump/"));
        Path highscoreFilePath = Paths.get(System.getenv("LocalAppData") + "/ben.tho/HorizonMinecraftJump/Highscores.txt");

        File highscoreFile = highscoreFilePath.toFile();
        highscoreFile.createNewFile(); // If it already exists if wont be voerwritten

        System.out.println(highscoreFile.getAbsolutePath());

        if (highscoreFile.length() != 0) readDataFromFile(highscoreFile);
        _myFile = highscoreFile;
    }

    public void save() {
        try {
            saveDataToFile(_myFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readDataFromFile(File highscoreFile) throws FileNotFoundException {
        Scanner sc = new Scanner(highscoreFile);
        while(sc.hasNext()) {
            highscoreMap.put(sc.next(), sc.nextInt()); // Saved in milliseconds/int
        }
        sc.close();
    }

    private void saveDataToFile(File highscoreFile) throws IOException {
        FileWriter writer = new FileWriter(highscoreFile);
        for (Map.Entry<String, Integer> element : highscoreMap.entrySet()) {
            writer.write(element.getKey());
            writer.write(" ");
            writer.write(element.getValue().toString());
            writer.write("\n");
        }
        writer.close();
    }

    public static String toMM_SS_String(int ms) {
        if (ms <= 0) return "--:--";

        int minutes = (ms / 1000 / 60);
        int seconds = ms / 1000 - minutes * 60;

        return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

}
