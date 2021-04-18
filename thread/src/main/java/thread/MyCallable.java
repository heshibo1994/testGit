package thread;

import java.util.concurrent.*;

public class MyCallable implements Callable<Boolean> {
    private String url;
    private String fileName;

    public MyCallable(String url, String fileName) {
        this.fileName = fileName;
        this.url = url;
    }

    @Override
    public Boolean call() throws Exception {
        ImageDownloadProcess imageDownloadProcess = new ImageDownloadProcess();
        imageDownloadProcess.downloadImage(url, fileName);
        System.out.println("下载图片：" + fileName);
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String url = "https://img-blog.csdnimg.cn/20190221093748315.png";
        String fileName = "C:\\Users\\hsb\\IdeaProjects\\thread\\src\\main\\resources\\image\\";
        MyCallable callable1 = new MyCallable(url, fileName + "1.png");
        MyCallable callable2 = new MyCallable(url, fileName + "2.png");
        MyCallable callable3 = new MyCallable(url, fileName + "3.png");
        // 创建执行服务：
        ExecutorService ser = Executors.newFixedThreadPool(3);

        // 提交执行
        Future<Boolean> r1 = ser.submit(callable1);
        Future<Boolean> r2 = ser.submit(callable2);
        Future<Boolean> r3 = ser.submit(callable3);
        // 获取结果
        Boolean rs1 = r1.get();
        Boolean rs2 = r2.get();
        Boolean rs3 = r3.get();
        System.out.println(rs1+":"+rs2+":"+rs3);
        // 关闭服务
        ser.shutdown();
    }


}






