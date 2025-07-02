import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class project extends JFrame implements MouseListener, ActionListener {

    JLabel nameLabel, ageLabel, treatmentLabel, daysadmittedLabel, roomLabel, dateLabel;
    JTextField nameTF, ageTF,extraNoteTF, dateTF;
    JComboBox<String> treatmentCombo, daysadmittedCombo, roomCombo;
    JButton billBtn, recordBtn, logoutBtn;
    JLabel billResultLabel;
    JLabel imgLabel, imggLabel;
    ImageIcon img, imgg;
    JPanel panel;
	Color myColor;
	Font myFont;
    FileWriter writer;

    public project() {
        super("AIUB Hospital");
        this.setSize(900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        //panel.setBackground(new Color(230, 240, 255));

        JLabel title = new JLabel("AIUB Hospital");
        title.setBounds(360, 10, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(750, 20, 100, 30);
        logoutBtn.addMouseListener(this);
        logoutBtn.addActionListener(this);
        panel.add(logoutBtn);

        dateLabel = new JLabel("Date:");
        dateLabel.setBounds(750, 60, 120, 25);
        panel.add(dateLabel);

        dateTF = new JTextField();
        dateTF.setBounds(750, 90, 100, 25);
        panel.add(dateTF);
		
	    extraNoteTF = new JTextField();
        extraNoteTF.setBounds(50, 320, 710, 100);
        panel.add(extraNoteTF);

        nameLabel = new JLabel("Patient Name:");
        nameLabel.setBounds(50, 60, 120, 25);
        panel.add(nameLabel);

        

        ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 100, 100, 25);
        panel.add(ageLabel);

        ageTF = new JTextField();
        ageTF.setBounds(180, 100, 100, 25);
        panel.add(ageTF);

        treatmentLabel = new JLabel("Treatment Entry:");
        treatmentLabel.setBounds(50, 140, 120, 25);
        panel.add(treatmentLabel);

        String[] treatments = {"General Checkup", "Surgery", "X-Ray", "ECG","ICU","CCU"};
        treatmentCombo = new JComboBox<>(treatments);
        treatmentCombo.setBounds(180, 140, 200, 25);
        panel.add(treatmentCombo);

        daysadmittedLabel = new JLabel("Days:");
        daysadmittedLabel.setBounds(50, 180, 120, 25);
        panel.add(daysadmittedLabel);

        String[] daysadmitted = {"1", "2", "3", "4", "5", "6", "7"};
        daysadmittedCombo = new JComboBox<>(daysadmitted);
        daysadmittedCombo.setBounds(180, 180, 100, 25);
        panel.add(daysadmittedCombo);

        roomLabel = new JLabel("Room:");
        roomLabel.setBounds(50, 220, 120, 25);
        panel.add(roomLabel);

        String[] roomOptions = {"101", "102", "201", "202", "301"};
        roomCombo = new JComboBox<>(roomOptions);
        roomCombo.setBounds(180, 220, 100, 25);
        panel.add(roomCombo);

        billBtn = new JButton("Bill ");
        billBtn.setBounds(360, 260, 150, 35);
        billBtn.addActionListener(this);
        billBtn.addMouseListener(this);
        panel.add(billBtn);

        recordBtn = new JButton("Previous Record ");
        recordBtn.setBounds(540, 260, 220, 35);
        recordBtn.addActionListener(this);
        recordBtn.addMouseListener(this);
        panel.add(recordBtn);

        billResultLabel = new JLabel("");
        billResultLabel.setBounds(360, 305, 300, 25);
        panel.add(billResultLabel);

        
        img = new ImageIcon("Images/amit.jpg");
        imgLabel = new JLabel(img);
        imgLabel.setBounds(50, 300, 736, 572);
        panel.add(imgLabel);

        imgg = new ImageIcon("Images/rudra.png");
        imggLabel = new JLabel(imgg);
        imggLabel.setBounds(0, 0, 900, 1590);  
        panel.add(imggLabel);
		
		JTextField largeTextField = new JTextField();
        largeTextField.setBounds(50, 320, 710, 50); 
        panel.add(largeTextField);

        this.add(panel);
        setupFileWriter();
    }

    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();
            btn.setBackground(Color.BLUE);
            btn.setForeground(Color.WHITE);
        }
    }

    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();
            btn.setBackground(null);
            btn.setForeground(null);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutBtn) {
            JOptionPane.showMessageDialog(this, "Logging out...");
            System.exit(0);
        }
		else if (e.getSource() == billBtn) {
			String date = dateTF.getText();
            String name = nameTF.getText();
            String age = ageTF.getText();
            String treatment = (String) treatmentCombo.getSelectedItem();
            int days = Integer.parseInt((String) daysadmittedCombo.getSelectedItem());

            int treatmentCost = 0;
            switch (treatment) {
                case "General Checkup": treatmentCost = 10000; break;
                case "Surgery":         treatmentCost = 15000; break;
                case "X-Ray":           treatmentCost = 1500; break;
                case "ECG":             treatmentCost = 2200; break;
				case "ICU":             treatmentCost = 40000; break;
				case "CCU":             treatmentCost = 45000; break;
            }

            //int roomCostPerDay = 1000;
            int totalBill = treatmentCost * days;

            JOptionPane.showMessageDialog(this,"Date:" + date + "\n"
			                                    +"Patient Name: " + name + "\n"
                                                + "Age: " + age + "\n"
                                                + "Treatment: " + treatment + "\n"
                                                + "Total Bill: " + totalBill + " BDT",
												"Bill", JOptionPane.INFORMATION_MESSAGE);


            String BILL = "Date:" + date + ",Name: " + name + ", Age: " + age + ", Bill: " + totalBill + " BDT";
            logTransaction(BILL);

        }
		else if (e.getSource() == recordBtn) {
            try {
                File file = new File("History.txt");
                if (file.exists()) {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line, allText = "";
                    while ((line = reader.readLine()) != null) {
                        allText += line + "\n";
                    }
                    reader.close();
                    JOptionPane.showMessageDialog(this, allText, "Previous Records", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No record found.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void setupFileWriter() {
        try {
            File file = new File("History.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logTransaction(String message) {
        try {
            writer.write(message + "\n"); 
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
