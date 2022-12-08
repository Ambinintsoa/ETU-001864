
package configuration;
import java.io.InputStream;
import java.io.OutputStream;

public class SyncPipe implements Runnable {
    private final OutputStream ostrm_;
    private final InputStream istrm_;

    public SyncPipe(InputStream istrm, OutputStream ostrm) {
        istrm_ = istrm;
        ostrm_ = ostrm;
    }

    public void run() {
        try {
            final byte[] buffer = new byte[1024];
            for (int index = 0; (index = istrm_.read(buffer)) != -1;) {
                ostrm_.write(buffer, 0, index);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}