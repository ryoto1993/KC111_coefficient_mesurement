import java.io.*;
import java.util.ArrayList;

/**
 * Created by RyotoTomioka on 2016/05/27.
 */
public class DataContainer {
    private File cdinfo, datacsv;
    private PrintWriter cdinfo_writer, datacsv_writer;

    // 計測条件情報
    private int light_no, signal, defsignal, sensor_num, interval;
    private int mode;   // mode 0 = white, mode 1 = 暖色

    // 詳細設定
    private int light_num = 12;

    // 計測情報
    private int position = 0;


    DataContainer() {

    }

    public void writeCdinfo() {
        cdinfo = new File("cdinfo.txt");
        ArrayList<Integer> cd = new ArrayList<>();
        for(int i=0; i<light_num; i++) {
            if(mode == 0) {
                if (i == light_no - 1) {
                    cd.add(signal);
                    cd.add(0);
                } else {
                    cd.add(defsignal);
                    cd.add(0);
                }
            } else {
                if (i == light_no-1) {
                    cd.add(0);
                    cd.add(signal);
                } else {
                    cd.add(0);
                    cd.add(defsignal);
                }
            }
        }
        try {
            cdinfo_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cdinfo),"UTF-8")));
            while(!cd.isEmpty()) {
                cdinfo_writer.write(cd.remove(0).toString() + ", ");
            }
            cdinfo_writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void makeDataFile() {
        datacsv = new File("data/Light" + Integer.toString(light_no) + "_" + Integer.toString(signal) + ".csv");

        try {
            datacsv_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cdinfo),"UTF-8")));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void closeDataFile() {
        try {
            datacsv_writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void writeAndNext() {
        position += interval;
    }

    public void setInterval(int interval) {
        this.interval = interval * sensor_num;
    }

    public void setSensor_num(int sensor_num) {
        this.sensor_num = sensor_num;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public void setDefsignal(int defsignal) {
        this.defsignal = defsignal;
    }

    public void setLight_no(int light_no) {
        this.light_no = light_no;
    }

    public void setMode(int mode) {
        this.mode = mode;
        System.out.println(mode);
    }

    public int getPosition() {
        return position;
    }
}
