import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by RyotoTomioka on 2016/05/27.
 */
public class GUI extends JFrame implements ActionListener {
    DataContainer dataContainer;
    JPanel info;
    JLabel label_position;
    JTextField fld_lightno, fld_sensorno, fld_interval;
    JComboBox com_signal, com_defsignal, com_mode;

    GUI(DataContainer dataContainer) {
        this.dataContainer = dataContainer;

        this.setTitle("影響度測定支援ツール");
        this.setBounds(100, 100, 300, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        setConfigPanel();

        this.setVisible(true);
    }

    private void setConfigPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(7, 2));
        JLabel lbl_mode = new JLabel("照明色温度");
        JLabel lbl_lightno = new JLabel("照明番号");
        JLabel lbl_sensorno = new JLabel("センサの数");
        JLabel lbl_interval = new JLabel("センサ間隔（cm）");
        JLabel lbl_defsignal = new JLabel("初期化信号値");
        JLabel lbl_signal = new JLabel("信号値");
        JButton btn_ok = new JButton("OK");
        btn_ok.addActionListener(this);
        btn_ok.setActionCommand("config_ok");
        JButton btn_exit = new JButton("Exit");
        btn_exit.addActionListener(this);
        btn_exit.setActionCommand("config_exit");
        fld_lightno = new JTextField();
        fld_sensorno = new JTextField();
        fld_interval = new JTextField();

        Vector<Integer> combodata = new Vector<>();
        combodata.add(50);
        combodata.add(70);
        combodata.add(90);
        com_signal = new JComboBox(combodata);
        com_signal.setSelectedIndex(2);
        com_defsignal = new JComboBox(combodata);
        com_defsignal.setSelectedIndex(1);

        Vector<String> modedata = new Vector<>();
        modedata.add("昼光色");
        modedata.add("電球色");
        com_mode = new JComboBox(modedata);

        p.add(lbl_mode);
        p.add(com_mode);
        p.add(lbl_defsignal);
        p.add(com_defsignal);
        p.add(lbl_signal);
        p.add(com_signal);
        p.add(lbl_lightno);
        p.add(fld_lightno);
        p.add(lbl_sensorno);
        p.add(fld_sensorno);
        p.add(lbl_interval);
        p.add(fld_interval);
        p.add(btn_exit);
        p.add(btn_ok);

        this.setContentPane(p);
    }

    private void setMeasurementPanel() {
        info = new JPanel();
        info.setLayout(new FlowLayout());

        JPanel buttonPanel = new JPanel();

        JPanel p = new JPanel();
        GridLayout gridLayout = new GridLayout(3, 1);
        p.setLayout(gridLayout);
        p.setBackground(Color.cyan);
        p.setPreferredSize(new Dimension(getWidth(), 125));

        JLabel label_light = new JLabel("Light : " + fld_lightno.getText());
        JLabel label_signal = new JLabel("Signal : " + com_signal.getSelectedItem());
        label_signal.setFont(new Font("Arial", Font.PLAIN, 25));
        label_light.setFont(new Font("Arial", Font.PLAIN, 25));
        label_position = new JLabel("Position : " + Integer.toString(dataContainer.getPosition()) + " cm");
        label_position.setFont(new Font("Arial", Font.PLAIN, 25));

        JButton btn_measurement = new JButton("データ書き込み");
        btn_measurement.addActionListener(this);
        btn_measurement.setActionCommand("measurement");
        btn_measurement.setPreferredSize(new Dimension(getWidth()-10, 35));
        btn_measurement.setFont(new Font("Arial", Font.PLAIN, 18));

        p.add(label_light);
        p.add(label_signal);
        p.add(label_position);

        buttonPanel.add(btn_measurement);

        info.add(p);
        info.add(buttonPanel);

        this.setContentPane(info);
        this.validate();
    }

    void addPane(JPanel p, int x, int y, int w, int h) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridheight = h;
        gbc.gridwidth = w;
        info.add(p);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if(cmd.equals("config_ok")) {
            try {
                dataContainer.setLight_no(Integer.parseInt(fld_lightno.getText()));
                dataContainer.setSensor_num(Integer.parseInt(fld_sensorno.getText()));
                dataContainer.setInterval(Integer.parseInt(fld_interval.getText()));
                dataContainer.setSignal((int) com_signal.getSelectedItem());
                dataContainer.setDefsignal((int) com_defsignal.getSelectedItem());
                dataContainer.setMode(com_signal.getSelectedIndex());
            } catch (Exception ex) {
                return;
            }
            setMeasurementPanel();
        } else if(cmd.equals("config_exit")) {
            this.dispose();
            System.exit(0);
        } else if(cmd.equals("measurement")) {
            dataContainer.writeAndNext();
            label_position.setText("Position : " + Integer.toString(dataContainer.getPosition()) + " cm");
        }
    }
}
