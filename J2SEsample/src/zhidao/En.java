/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.event.PrintJobEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.Book;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import javax.print.event.PrintJobAdapter;

public class En implements Printable {

    private static final long serialVersionUID = -5124405479024666344L;
    private JPanel contentPane;
    private JTable table;
    private JFrame f;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    En fr = new En();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public En() {
        f = new JFrame();
        f.setTitle("\u6253\u5370\u6D4B\u8BD5\u7A97\u53E3");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        f.setContentPane(contentPane);

        final JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel label = new JLabel("New label");
        label.setBounds(330, 11, 73, 15);
        panel.add(label);

        JButton button = new JButton("New button");
        button.setBounds(144, 7, 138, 23);
        panel.add(button);

        JButton button_3 = new JButton("\u6253\u5370");
        button_3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Toolkit kit = Toolkit.getDefaultToolkit();
                Paper paper;
                if (kit != null) {
                    PrinterJob printerJob = PrinterJob.getPrinterJob();
                    Book book = new Book();
                    for (int i = 0; i < 3; i++) {
                        book.append(En.this, new PageFormat());
                    }
                    printerJob.setPageable(book);
                    try {
                        printerJob.printDialog();
                        printerJob.getPrintService().createPrintJob().addPrintJobListener(new PrintJobAdapter() {

                            @Override
                            public void printJobCanceled(PrintJobEvent pje) {
                                System.out.println("printJobCanceled");
                            }

                            @Override
                            public void printJobCompleted(PrintJobEvent pje) {
                                System.out.println("printJobCompleted");
                            }

                            @Override
                            public void printJobFailed(PrintJobEvent pje) {
                                System.out.println("printJobFailed");
                            }
                        });
                        printerJob.print();
                    } catch (PrinterException ex) {
                        Logger.getLogger(En.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        button_3.setBounds(340, 36, 93, 39);
        panel.add(button_3);

        JButton button_2 = new JButton("New button");
        button_2.setBounds(21, 2, 93, 30);
        panel.add(button_2);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(33, 61, 285, 168);
        panel.add(scrollPane);

        table = new JTable(9, 5);
        scrollPane.setViewportView(table);
        f.setVisible(true);
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        f.paint(graphics);
        int w = (int) pageFormat.getImageableWidth();
        int h = (int) pageFormat.getImageableHeight();
        int x = (int) pageFormat.getImageableX();
        int y = (int) pageFormat.getImageableY();
        graphics.setColor(Color.RED);
        graphics.fillRect(x, y, w, h);
        return Printable.PAGE_EXISTS;
    }
}
