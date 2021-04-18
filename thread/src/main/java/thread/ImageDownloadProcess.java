package thread;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

class ImageDownloadProcess {
    public void downloadImage(String url, String fileName) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("下载异常");
        }
    }

}
