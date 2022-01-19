import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener; // ActionListener wasn't recognized desipte above statement??
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableColumn;

public class ComparisonRunnerGUI
{
    private JFrame frame;
    private JPanel mainPanel, gridPanel, inputPanel, buttonPanel;
    private JLabel titleLabel, listLengthLabel, trialsLabel, ratioLabel, buttonsInfoLabel;
    private JTextField listLengthField, trialsField, ratioField;
    private JButton listLengthButton, trialsButton, ratioButton, runButton;
    private JSlider listLengthSlider, trialsSlider, ratioSlider;
    private JTable data;

    private JButton[] sortButtons;

    public ComparisonRunnerGUI (String[] names) {
        frame = new JFrame("Sort Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        gridPanel = new JPanel(); // Default BorderLayout

        inputPanel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(inputPanel);
        inputPanel.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        buttonPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 1);
        buttonPanel.setLayout(gridLayout);

        // Labels
        titleLabel = new JLabel("Sort Comparison GUI");
        titleLabel.setSize(60, 60);

        listLengthLabel = new JLabel("List Length: ");

        trialsLabel = new JLabel("Trials: ");

        ratioLabel = new JLabel("Ratio: ");

        buttonsInfoLabel = new JLabel("Use the buttons on the right to toggle lists to be tested. White is on; gray is off.");

        // Text fields
        listLengthField = new JTextField("10");
        listLengthField.setPreferredSize(new Dimension(50, 20));

        trialsField = new JTextField("10");
        trialsField.setPreferredSize(new Dimension(50, 20));

        ratioField = new JTextField("80");
        ratioField.setPreferredSize(new Dimension(50, 20));

        // Buttons - will add sort toggle buttons later in constructor
        listLengthButton = new JButton("Enter");
        listLengthButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listLengthLabel.setText("List length: " + listLengthField.getText());
                listLengthSlider.setValue((Integer.parseInt(listLengthField.getText())));
            }
        });

        trialsButton = new JButton("Enter");
        trialsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trialsLabel.setText("Trials: " + trialsField.getText());
                trialsSlider.setValue((Integer.parseInt(trialsField.getText())));
            }
        });

        ratioButton = new JButton("Enter");
        ratioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ratioLabel.setText("Ratio " + ratioField.getText() + "%");
                ratioSlider.setValue((Integer.parseInt(ratioField.getText())));
            }
        });

        runButton = new JButton("Run test");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Find required parameters

                int listLength = Integer.parseInt(listLengthField.getText());
                int trials = Integer.parseInt(trialsField.getText());
                double ratio = ((double)Integer.parseInt(ratioField.getText()))/100;
                boolean[] selectedLists = new boolean[sortButtons.length];
                for(int i = 0; i < sortButtons.length; i++)
                    if(sortButtons[i].getBackground() == Color.white)
                        selectedLists[i] = true;
                    else
                        selectedLists[i] = false;

                // Sort

                long[][] inOrderData = ComparisonRunner.groupSortTester(0, listLength, trials, ratio, selectedLists);
                long[][] reverseOrderData = ComparisonRunner.groupSortTester(1, listLength, trials, ratio, selectedLists);
                long[][] almostOrderData = ComparisonRunner.groupSortTester(2, listLength, trials, ratio, selectedLists);
                long[][] randomOrderData = ComparisonRunner.groupSortTester(3, listLength, trials, ratio, selectedLists);

                double[] inOrderWinnerData = ComparisonRunner.getWinningSort(inOrderData);
                long inOrderWinnerTime = inOrderData[(int)inOrderWinnerData[6]][0];

                double[] reverseOrderWinnerData = ComparisonRunner.getWinningSort(reverseOrderData);
                long reverseOrderWinnerTime = reverseOrderData[(int)reverseOrderWinnerData[6]][0];

                double[] almostOrderWinnerData = ComparisonRunner.getWinningSort(almostOrderData);
                long almostOrderWinnerTime = almostOrderData[(int)almostOrderWinnerData[6]][0];

                double[] randomOrderWinnerData = ComparisonRunner.getWinningSort(randomOrderData);
                long randomOrderWinnerTime = randomOrderData[(int)randomOrderWinnerData[6]][0];

                // Display output on JTable

                long[][] currentData = new long[0][0];
                double[] currentWinnerData = new double[0];
                long currentWinnerTime = 0;
                int gap = 0; // For spacing out rows
                for(int i = 0; i < 4; i++) {
                    switch(i) {
                        case(0) : currentData = inOrderData; currentWinnerData = inOrderWinnerData; currentWinnerTime = inOrderWinnerTime; break;
                        case(1) : currentData = reverseOrderData; currentWinnerData = reverseOrderWinnerData; currentWinnerTime = reverseOrderWinnerTime; break;
                        case(2) : currentData = almostOrderData; currentWinnerData = almostOrderWinnerData; currentWinnerTime = almostOrderWinnerTime; break;
                        case(3) : currentData = randomOrderData; currentWinnerData = randomOrderWinnerData; currentWinnerTime = randomOrderWinnerTime; break;
                    }
                    for(int r = 1; r < names.length+1; r++) {
                        for(int c = 1; c < 5; c++) {
                            if(c < 4)
                                data.setValueAt((int)currentData[r-1][c-1], ((names.length*i)+gap)+r+1, c);
                            else if(c == 4 && currentWinnerData[r-1] != -200.0)
                                data.setValueAt(currentWinnerData[r-1] + "%", ((names.length*i)+gap)+r+1, c);
                            else
                                data.setValueAt("N/A", ((names.length*i)+gap)+r+1, c);
                        }
                    }
                    gap += 2;
                }
            }
        });

        // Sliders
        listLengthSlider = new JSlider(0, 10000, 10);
        listLengthSlider.setPaintTrack(true);
        listLengthSlider.setPaintTicks(true);
        listLengthSlider.setPaintLabels(true);
        listLengthSlider.setMajorTickSpacing(2000);
        listLengthSlider.setMinorTickSpacing(1000);
        listLengthSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                listLengthLabel.setText("List length: " + listLengthSlider.getValue());
                listLengthField.setText(listLengthSlider.getValue() + "");
            }
        });

        trialsSlider = new JSlider(0, 50, 10);
        trialsSlider.setPaintTrack(true);
        trialsSlider.setPaintTicks(true);
        trialsSlider.setPaintLabels(true);
        trialsSlider.setMajorTickSpacing(10);
        trialsSlider.setMinorTickSpacing(5);
        trialsSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                trialsLabel.setText("Trials: " + trialsSlider.getValue());
                trialsField.setText(trialsSlider.getValue() + "");
            }
        });

        ratioSlider = new JSlider(0, 100, 80);
        ratioSlider.setPaintTrack(true);
        ratioSlider.setPaintTicks(true);
        ratioSlider.setPaintLabels(true);
        ratioSlider.setMajorTickSpacing(20);
        ratioSlider.setMinorTickSpacing(10);
        ratioSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                ratioLabel.setText("Ratio: " + ratioSlider.getValue() + "%");
                ratioField.setText(ratioSlider.getValue() + "");
            }
        });

        // Table
        data = new JTable((names.length+2)*4, 5);
        String[] columnNames = {"", "Time (ns)", "Comparisons", "Movements","Winner % diff"};
        for(int i = 0; i < columnNames.length; i++)
            data.setValueAt(columnNames[i], 0, i);
        data.setValueAt("InOrder", 1, 0);
        data.setValueAt("ReverseOrder", (names.length+3), 0);
        data.setValueAt("AlmostOrder", ((names.length+3)*2)-1, 0);
        data.setValueAt("RandomOrder", ((names.length+3)*3)-2, 0);
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < names.length; j++)
                data.setValueAt(names[j], (i*(names.length+2))+j+2, 0);
        TableColumn column;
        for(int i = 0; i < columnNames.length; i++) {
            column = data.getColumnModel().getColumn(i);
            column.setPreferredWidth(85);
        }

        // Add components
        gridPanel.add(data, BorderLayout.CENTER);

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(titleLabel)
                .addComponent(listLengthLabel)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(listLengthSlider)
                        .addComponent(listLengthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(listLengthButton))
                .addComponent(trialsLabel)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(trialsSlider)
                        .addComponent(trialsField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(trialsButton))
                .addComponent(ratioLabel)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(ratioSlider)
                        .addComponent(ratioField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(ratioButton))
                .addComponent(buttonsInfoLabel)
                .addComponent(runButton)
        );

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(titleLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(listLengthLabel)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(listLengthSlider)
                        .addComponent(listLengthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(listLengthButton))
                .addComponent(trialsLabel)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(trialsSlider)
                        .addComponent(trialsField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(trialsButton))
                .addComponent(ratioLabel)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(ratioSlider)
                        .addComponent(ratioField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(ratioButton))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonsInfoLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(runButton)
        );

        sortButtons = new JButton[names.length];
        JButton button;
        for(int i = 0; i < sortButtons.length; i++) {
            JButton x = new JButton(names[i]);
            sortButtons[i] = x;
            x.setBackground(Color.white);
            x.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(x.getBackground() == Color.white)
                        x.setBackground(Color.GRAY);
                    else
                        x.setBackground(Color.white);
                }
            });
            buttonPanel.add(x);
        }

        mainPanel.add(gridPanel);
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);

        // Set visibles

        frame.setVisible(true);
    }
}