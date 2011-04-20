/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.util.Random;

public class FloTest extends Applet
        implements Runnable {

    public FloTest() {
        m_mouseX = 0;
        m_mouseY = 0;
        m_sleepTime = 5;
        isError = false;
        isInitialized = false;
        rand = new Random();
        bits = 10000;
        bit_px = new double[bits];
        bit_py = new double[bits];
        bit_vx = new double[bits];
        bit_vy = new double[bits];
        bit_sx = new int[bits];
        bit_sy = new int[bits];
        bit_l = new int[bits];
        bit_f = new int[bits];
        bit_p = new int[bits];
        bit_c = new int[bits];
        ru = 50;
        rv = 50;
    }

    public void init() {
        String s = getParameter("para_bits");
        if (s != null) {
            bits = Integer.parseInt(s);
        }
        s = getParameter("para_max");
        if (s != null) {
            bit_max = Integer.parseInt(s);
        }
        s = getParameter("para_blendx");
        if (s != null) {
            ru = Integer.parseInt(s);
        }
        s = getParameter("para_blendy");
        if (s != null) {
            rv = Integer.parseInt(s);
        }
        s = getParameter("para_sound");
        if (s != null) {
            bit_sound = Integer.parseInt(s);
        }
        m_nAppX = size().width;
        m_nAppY = size().height;
        m_centerX = m_nAppX / 2;
        m_centerY = m_nAppY / 2;
        m_mouseX = m_centerX;
        m_mouseY = m_centerY;
        resize(m_nAppX, m_nAppY);
        pixls = m_nAppX * m_nAppY;
        pixls2 = pixls - m_nAppX * 2;
        pix0 = new int[pixls];
        offImage = new MemoryImageSource(m_nAppX, m_nAppY, pix0, 0, m_nAppX);
        offImage.setAnimated(true);
        dbImg = createImage(offImage);
        for (int i = 0; i < pixls; i++) {
            pix0[i] = 0xff000000;
        }

        sound1 = getAudioClip(getDocumentBase(), "firework.au");
        sound2 = getAudioClip(getDocumentBase(), "syu.au");
        for (int j = 0; j < bits; j++) {
            bit_f[j] = 0;
        }

        isInitialized = true;
        start();
    }

    public void run() {
        while (!isInitialized) {
            try {
                Thread.sleep(200L);
            } catch (InterruptedException interruptedexception) {
            }
        }
        do {
            for (int j = 0; j < pixls2; j++) {
                int k = pix0[j];
                int l = pix0[j + 1];
                int i1 = pix0[j + m_nAppX];
                int j1 = pix0[j + m_nAppX + 1];
                int i = (k & 0xff0000) >> 16;
                int k1 = ((((l & 0xff0000) >> 16) - i) * ru >> 8) + i;
                i = (k & 0xff00) >> 8;
                int l1 = ((((l & 0xff00) >> 8) - i) * ru >> 8) + i;
                i = k & 0xff;
                int i2 = (((l & 0xff) - i) * ru >> 8) + i;
                i = (i1 & 0xff0000) >> 16;
                int j2 = ((((j1 & 0xff0000) >> 16) - i) * ru >> 8) + i;
                i = (i1 & 0xff00) >> 8;
                int k2 = ((((j1 & 0xff00) >> 8) - i) * ru >> 8) + i;
                i = i1 & 0xff;
                int l2 = (((j1 & 0xff) - i) * ru >> 8) + i;
                int i3 = ((j2 - k1) * rv >> 8) + k1;
                int j3 = ((k2 - l1) * rv >> 8) + l1;
                int k3 = ((l2 - i2) * rv >> 8) + i2;
                pix0[j] = i3 << 16 | j3 << 8 | k3 | 0xff000000;
            }

            rend();
            offImage.newPixels(0, 0, m_nAppX, m_nAppY);
            try {
                Thread.sleep(m_sleepTime);
            } catch (InterruptedException interruptedexception1) {
            }
        } while (true);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        g.drawImage(dbImg, 0, 0, this);
    }

    public void start() {
        if (isError) {
            return;
        }
        isRunning = true;
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void stop() {
        if (runner != null) {
            runner.stop();
            runner = null;
        }
    }

    public boolean mouseMove(Event event, int i, int j) {
        m_mouseX = i;
        m_mouseY = j;
        return true;
    }

    public boolean mouseDown(Event event, int i, int j) {
        m_mouseX = i;
        m_mouseY = j;
        int k = (int) (rand.nextDouble() * 256D);
        int l = (int) (rand.nextDouble() * 256D);
        int i1 = (int) (rand.nextDouble() * 256D);
        int j1 = k << 16 | l << 8 | i1 | 0xff000000;
        int k1 = 0;
        for (int l1 = 0; l1 < bits; l1++) {
            if (bit_f[l1] != 0) {
                continue;
            }
            bit_px[l1] = m_mouseX;
            bit_py[l1] = m_mouseY;
            double d = rand.nextDouble() * 6.2800000000000002D;
            double d1 = rand.nextDouble();
            bit_vx[l1] = Math.sin(d) * d1;
            bit_vy[l1] = Math.cos(d) * d1;
            bit_l[l1] = (int) (rand.nextDouble() * 100D) + 100;
            bit_p[l1] = (int) (rand.nextDouble() * 3D);
            bit_c[l1] = j1;
            bit_sx[l1] = m_mouseX;
            bit_sy[l1] = m_nAppY - 5;
            bit_f[l1] = 2;
            if (++k1 == bit_max) {
                break;
            }
        }

        if (bit_sound > 1) {
            sound2.play();
        }
        return true;
    }

    public boolean mouseExit(Event event, int i, int j) {
        m_mouseX = i;
        m_mouseY = j;
        return true;
    }

    void rend() {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        for (int k = 0; k < bits; k++) {
            switch (bit_f[k]) {
                default:
                    break;

                case 1: // '\001'
                    bit_vy[k] += rand.nextDouble() / 50D;
                    bit_px[k] += bit_vx[k];
                    bit_py[k] += bit_vy[k];
                    bit_l[k]--;
                    if (bit_l[k] == 0 || bit_px[k] < 0.0D || bit_py[k] < 0.0D || bit_px[k] > (double) m_nAppX || bit_py[k] > (double) (m_nAppY - 3)) {
                        bit_c[k] = 0xff000000;
                        bit_f[k] = 0;
                    } else if (bit_p[k] == 0) {
                        if ((int) (rand.nextDouble() * 2D) == 0) {
                            bit_set((int) bit_px[k], (int) bit_py[k], -1);
                        }
                    } else {
                        bit_set((int) bit_px[k], (int) bit_py[k], bit_c[k]);
                    }
                    break;

                case 2: // '\002'
                    bit_sy[k] -= 5;
                    if ((double) bit_sy[k] <= bit_py[k]) {
                        bit_f[k] = 1;
                        flag2 = true;
                    }
                    if ((int) (rand.nextDouble() * 20D) == 0) {
                        int i = (int) (rand.nextDouble() * 2D);
                        int j = (int) (rand.nextDouble() * 5D);
                        bit_set(bit_sx[k] + i, bit_sy[k] + j, -1);
                    }
                    break;
            }
        }

        if (flag2 && bit_sound > 0) {
            sound1.play();
        }
    }

    void bit_set(int i, int j, int k) {
        int l = i + j * m_nAppX;
        pix0[l] = k;
    }
    private int m_nAppX;
    private int m_nAppY;
    private int m_centerX;
    private int m_centerY;
    private int m_mouseX;
    private int m_mouseY;
    private int m_sleepTime;
    private boolean isError;
    private boolean m_isPaintFinished;
    boolean isRunning;
    boolean isInitialized;
    Thread runner;
    int pix0[];
    MemoryImageSource offImage;
    Image dbImg;
    int pixls;
    int pixls2;
    Random rand;
    int bits;
    double bit_px[];
    double bit_py[];
    double bit_vx[];
    double bit_vy[];
    int bit_sx[];
    int bit_sy[];
    int bit_l[];
    int bit_f[];
    int bit_p[];
    int bit_c[];
    int bit_max;
    int bit_sound;
    int ru;
    int rv;
    AudioClip sound1;
    AudioClip sound2;
}
