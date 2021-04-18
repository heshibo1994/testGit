package thread;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class MyThreadDownloadImage extends Thread {
    private String url;
    private String fileName;

    public MyThreadDownloadImage(String url, String fileName) {
        this.fileName = fileName;
        this.url = url;
    }

    @Override
    public void run() {
        ImageDownloadProcess imageDownloadProcess = new ImageDownloadProcess();
        imageDownloadProcess.downloadImage(url, fileName);
        System.out.println("下载图片："+fileName);

    }

    public static void main(String[] args) {
        String url = "https://img-blog.csdnimg.cn/20190221093748315.png";
        String fileName = "C:\\Users\\hsb\\IdeaProjects\\thread\\src\\main\\resources\\image\\1.png";
        new MyThreadDownloadImage(url, fileName + "1.png").start();
        new MyThreadDownloadImage(url, fileName + "2.png").start();
        new MyThreadDownloadImage(url, fileName + "3.png").start();

    }
}





