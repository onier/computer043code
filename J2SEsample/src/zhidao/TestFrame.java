/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

class TestFrame {

    public static void main(String[] args) {
        byte i1 = 67;
        byte i2 = 89;
        byte i3 = (byte) (i1 + i2);
        byte[] temp = new Byte(i3).toString().getBytes();
        byte[] str = new Integer(156).toString().getBytes();
        System.out.print(i3);
    }
}
