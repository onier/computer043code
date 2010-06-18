/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2d;

import java.awt.Canvas;
import javax.swing.JFrame;

public class MyGame {

    private static final int FRAME_DELAY = 20; // 20ms。对应于50FPS (1000/20) = 50
    private static Canvas gui;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        gui = new CustomGUI(); // 创建我们自己的改造过渲染逻辑的Canvas对象
        frame.getContentPane().add(gui);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(3);
        Thread gameThread = new Thread(new GameLoop());
        gameThread.setPriority(Thread.MIN_PRIORITY);
        gameThread.start(); // 开始游戏过程
        frame.setVisible(true); // 开始AWT绘制
    }

    private static void updateGameState() {
        CustomGUI.lineX++;
    }

    private static class GameLoop implements Runnable {

        boolean isRunning = true;

        public void run() {
            long cycleTime = System.currentTimeMillis();
            while (isRunning) {
                updateGameState();

                MyGame.gui.repaint(); // 引发“paint”以在AWT线程中调用CustomGUI对象。（引发了帧的渲染）

                cycleTime = cycleTime + MyGame.FRAME_DELAY;
                long difference = cycleTime - System.currentTimeMillis();

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }
}
