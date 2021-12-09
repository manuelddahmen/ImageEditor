package one.empty3.feature.gui;/*package one.empty3.feature.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

import java.io.File;

public class ExploreResults extends JFrame {
    File directory;

    public ExploreResults(File directory) {
        this.directory = directory;
    }

    private void tree2MouseClicked(MouseEvent e) {
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        scrollPane3 = new JScrollPane();
        tree2 = new JTree();
        panel2 = new JPanel();
        label2 = new JLabel();
        comboBox1 = new JComboBox();
        button6 = new JButton();
        scrollPane2 = new JScrollPane();
        table1 = new JTable();
        panel3 = new JPanel();
        button8 = new JButton();
        button7 = new JButton();
        panel1 = new JPanel();
        button9 = new JButton();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //======== scrollPane3 ========
        {

            //---- tree2 ----
            tree2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tree2MouseClicked(e);
                }
            });
            scrollPane3.setViewportView(tree2);
        }
        contentPane.add(scrollPane3, "cell 0 0");

        //======== panel2 ========
        {
            panel2.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));

            //---- label2 ----
            label2.setText("text");
            panel2.add(label2, "cell 0 0");
            panel2.add(comboBox1, "cell 1 0");

            //---- button6 ----
            button6.setText("add");
            panel2.add(button6, "cell 2 0");

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(table1);
            }
            panel2.add(scrollPane2, "cell 0 1 2 1");

            //======== panel3 ========
            {
                panel3.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]"));

                //---- button8 ----
                button8.setText("edit");
                panel3.add(button8, "cell 1 0");

                //---- button7 ----
                button7.setText("delete");
                panel3.add(button7, "cell 1 1");
            }
            panel2.add(panel3, "cell 2 1");
        }
        contentPane.add(panel2, "cell 1 0");

        //======== panel1 ========
        {
            panel1.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));
        }
        contentPane.add(panel1, "cell 3 0");

        //---- button9 ----
        button9.setText("Apply");
        contentPane.add(button9, "cell 1 1");

        //---- button1 ----
        button1.setText("<<<");
        contentPane.add(button1, "cell 3 1");

        //---- button2 ----
        button2.setText("^^^");
        contentPane.add(button2, "cell 3 1");

        //---- button3 ----
        button3.setText(">>>");
        contentPane.add(button3, "cell 3 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        tree2.setModel(new FileSystemViewModel(directory));

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JScrollPane scrollPane3;
    private JTree tree2;
    private JPanel panel2;
    private JLabel label2;
    private JComboBox comboBox1;
    private JButton button6;
    private JScrollPane scrollPane2;
    private JTable table1;
    private JPanel panel3;
    private JButton button8;
    private JButton button7;
    private JPanel panel1;
    private JButton button9;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String [] args) {
        ExploreResults results = new ExploreResults(new File("."));
        results.setVisible(true);
    }
}
*/