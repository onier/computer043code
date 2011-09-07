/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.example.exloprer;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

/**
 *
 * @author Zhenhai.Xu
 */
public class Bean extends JPanel {

    private TestEnum en;
    private Font font;
    private Color color;
    private Boolean b;
    /**
     * bean name.
     */
    private String name;
    private int age;
    private double sex;
    private Object obj;
    private JPanel panel;

    public Bean() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the en
     */
    public TestEnum getEn() {
        return en;
    }

    /**
     * @param en the en to set
     */
    public void setEn(TestEnum en) {
        this.en = en;
    }

    /**
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the b
     */
    public Boolean getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(Boolean b) {
        this.b = b;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the sex
     */
    public double getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(double sex) {
        this.sex = sex;
    }

    /**
     * @return the obj
     */
    public Object getObj() {
        return obj;
    }

    /**
     * @param obj the obj to set
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }

    /**
     * @return the panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * @param panel the panel to set
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}
