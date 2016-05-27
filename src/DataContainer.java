import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by RyotoTomioka on 2016/05/27.
 */
public class DataContainer {
    private File cdinfo, datacsv;
    private FileWriter cdinfo_writer, datacsv_writer;

    // 計測条件情報
    private int light_no, light_num, signal, defsignal, sensor_num, interval;
    private int mode;   // mode 0 = white, mode 1 = 暖色

    // 計測情報
    private int position = 0;


    DataContainer() {
        cdinfo = new File("cdinfo.txt");
    }

    public void write_cdinfo() {
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
            cdinfo_writer = new FileWriter(cdinfo, false);
            while(!cd.isEmpty()) {
                cdinfo_writer.write(cd.remove(0));
                cdinfo_writer.write(", 0, ");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void closeFiles() {
        try {
            cdinfo_writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
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

    public int getPosition() {
        return position;
    }
}
