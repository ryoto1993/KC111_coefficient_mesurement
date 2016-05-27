import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by RyotoTomioka on 2016/05/27.
 */
public class DataContainer {
    private File cdinfo, datacsv, sensor;
    private PrintWriter cdinfo_writer, datacsv_writer;
    private FileReader sensor_fr;
    private BufferedReader sensor_br;

    // 計測条件情報
    private int light_no = 1, signal, defsignal = 70, sensor_num = 1, interval = 10;
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
            datacsv_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(datacsv),"UTF-8")));
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
        boolean flag = true;
        String line;
        StringTokenizer token;

        while(flag) {
            try {
                sensor_fr = new FileReader("sensor.txt");
                sensor_br = new BufferedReader(sensor_fr);

                line = sensor_br.readLine();

                if(line == "") {
                    sensor_br.close();
                    continue;
                } else {
                    token = new StringTokenizer(line, ",");
                    for(int i=0; i<sensor_num; i++) {
                        datacsv_writer.write(token.nextToken() + ", ");
                    }
                    sensor_br.close();
                    flag = false;
                }

            } catch (Exception e) {}

        }

        position += interval * sensor_num;
    }

    public void setInterval(int interval) {
        this.interval = interval;
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
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getLight_no() {
        return light_no;
    }

    public int getSignal() {
        return signal;
    }

    public int getDefsignal() {
        return defsignal;
    }

    public int getSensor_num() {
        return sensor_num;
    }

    public int getInterval() {
        return interval;
    }

    public int getMode() {
        return mode;
    }
}
