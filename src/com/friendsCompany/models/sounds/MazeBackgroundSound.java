package com.friendsCompany.models.sounds;


import javax.sound.sampled.*;

import java.io.*;

public class MazeBackgroundSound implements Runnable {
    private final static String NOT_BIG_SHOT = "res/sounds/Get_up_on_your_feet_mixdown2.aiff";

    @Override
    public void run() {
        try {
            play(NOT_BIG_SHOT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public MazeBackgroundSound() throws IOException {
//        play(NOT_BIG_SHOT);
//    }

    public void play(String file) throws IOException {

        SourceDataLine line = null;

        AudioInputStream ais = null;

        byte[] b = new byte[2048];

        try {

            File f = new File(file);

            ais = AudioSystem.getAudioInputStream(f);

            AudioFormat af = ais.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);

            if (!AudioSystem.isLineSupported(info)) {
                System.err.println("Line is not supported");
                System.exit(0);
            }

            line = (SourceDataLine) AudioSystem.getLine(info);

            line.open(af);

            line.start();

            int num;

            while ((num = ais.read(b)) != -1)

                line.write(b, 0, num);

            line.drain();

            ais.close();

        } catch (Exception e) {
            System.err.println(e);
        }

        line.stop();

        line.close();
    }
}
