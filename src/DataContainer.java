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

    private ArrayList<Integer> cd;


    DataContainer() {
        cdinfo = new File("cdinfo.txt");
        cd = new ArrayList<>();
    }

    public void write_cdinfo() {
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
}
