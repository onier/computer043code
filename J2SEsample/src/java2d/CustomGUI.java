/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Polygon;
import java.awt.image.VolatileImage;

public class CustomGUI extends Canvas {

    public static int lineX;
    private VolatileImage volatileImg;
    Polygon p = new Polygon();
    // ...

    CustomGUI() {
        p.addPoint(10, 0);
        p.addPoint(0, 10);
        p.addPoint(10, 20);
        p.addPoint(20, 10);
        p.addPoint(80, 70);
        p.addPoint(90, 50);
        p.addPoint(70, 80);
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
//        g2D.fill(p);
//         创建硬件加速图像
        createBackBuffer();

        // 主渲染循环。VolatileImage对象可能失去内容。
        // 这个循环会不断渲染（如果需要的话并制造）VolatileImage对象
        // 直到渲染过程圆满完成。
        do {

            // 为该控件的Graphics配置验证VolatileImage的有效性。
            // 如果VolatileImage对象不能匹配GraphicsConfiguration
            // （换句话说，硬件加速不能应用在新设备上）
            // 那我们就重建它。
            GraphicsConfiguration gc = this.getGraphicsConfiguration();
            int valCode = volatileImg.validate(gc);

            // 以下说明设备不匹配这个硬件加速Image对象。
            if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
                createBackBuffer(); // 重建硬件加速Image对象。
            }

            Graphics offscreenGraphics = volatileImg.getGraphics();
            doPaint(offscreenGraphics); // 调用核心paint方法。

            // 把缓冲画回主Graphics对象
            g.drawImage(volatileImg, 0, 0, this);
        // 检查内容是否丢失
        } while (volatileImg.contentsLost());
    }

    // 封装了渲染逻辑的新方法。
    private void doPaint(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawLine(lineX, 0, lineX + 10, 0); // 画图
    }

    // 以下创建新的VolatileImage对象
    private void createBackBuffer() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        volatileImg = gc.createCompatibleVolatileImage(getWidth(), getHeight());
    }

    public void update(Graphics g) {
        paint(g);
    }
}
