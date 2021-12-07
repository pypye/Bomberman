package cores;

import java.io.*;
import java.util.Scanner;

public class Config {
    public static final String PATH = "data/game.cfg";
    public static int WIDTH;
    public static int HEIGHT;
    public static int FULLSCREEN;
    public static float BGM_VOLUME;
    public static float SFX_VOLUME;
    public static int SCORE;
    public static int LEVEL_PLAYED;
    public static int TIME_PLAYED;

    public static int TOTAL_LEVEL1_BOT_KILLED;
    public static int TOTAL_LEVEL2_BOT_KILLED;
    public static int TOTAL_LEVEL3_BOT_KILLED;
    public static int TOTAL_BOMB_PLACED;
    public static int TOTAL_BUFF_COLLECTED;

    public static void importConfig() {
        try {
            File file = new File(PATH);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] split = line.split("=");
                if (split.length == 2) {
                    switch (split[0]) {
                        case "Width":
                            WIDTH = Integer.parseInt(split[1]);
                            break;
                        case "Height":
                            HEIGHT = Integer.parseInt(split[1]);
                            break;
                        case "Fullscreen":
                            FULLSCREEN = Integer.parseInt(split[1]);
                            break;
                        case "BGMVolume":
                            BGM_VOLUME = Float.parseFloat(split[1]);
                            break;
                        case "SFXVolume":
                            SFX_VOLUME = Float.parseFloat(split[1]);
                            break;
                        case "Score":
                            SCORE = Integer.parseInt(split[1]);
                            break;
                        case "LevelPlayed":
                            LEVEL_PLAYED = Integer.parseInt(split[1]);
                            break;
                        case "TimePlayed":
                            TIME_PLAYED = Integer.parseInt(split[1]);
                            break;
                        case "TotalLevel1BotKilled":
                            TOTAL_LEVEL1_BOT_KILLED = Integer.parseInt(split[1]);
                            break;
                        case "TotalLevel2BotKilled":
                            TOTAL_LEVEL2_BOT_KILLED = Integer.parseInt(split[1]);
                            break;
                        case "TotalLevel3BotKilled":
                            TOTAL_LEVEL3_BOT_KILLED = Integer.parseInt(split[1]);
                            break;
                        case "TotalBombPlaced":
                            TOTAL_BOMB_PLACED = Integer.parseInt(split[1]);
                            break;
                        case "TotalBuffCollected":
                            TOTAL_BUFF_COLLECTED = Integer.parseInt(split[1]);
                            break;
                        default:
                            break;
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void exportConfig() {
        try {
            FileWriter fileWriter = new FileWriter(PATH, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Width=" + WIDTH + "\n");
            bufferedWriter.write("Height=" + HEIGHT + "\n");
            bufferedWriter.write("Fullscreen=" + FULLSCREEN + "\n");
            bufferedWriter.write("BGMVolume=" + BGM_VOLUME + "\n");
            bufferedWriter.write("SFXVolume=" + SFX_VOLUME + "\n");
            bufferedWriter.write("Score=" + SCORE + "\n");
            bufferedWriter.write("LevelPlayed=" + LEVEL_PLAYED + "\n");
            bufferedWriter.write("TimePlayed=" + TIME_PLAYED + "\n");
            bufferedWriter.write("TotalLevel1BotKilled=" + TOTAL_LEVEL1_BOT_KILLED + "\n");
            bufferedWriter.write("TotalLevel2BotKilled=" + TOTAL_LEVEL2_BOT_KILLED + "\n");
            bufferedWriter.write("TotalLevel3BotKilled=" + TOTAL_LEVEL3_BOT_KILLED + "\n");
            bufferedWriter.write("TotalBombPlaced=" + TOTAL_BOMB_PLACED + "\n");
            bufferedWriter.write("TotalBuffCollected=" + TOTAL_BUFF_COLLECTED + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
