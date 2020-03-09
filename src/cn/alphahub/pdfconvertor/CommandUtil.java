package cn.alphahub.pdfconvertor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandUtil {

    private List<String> stdOutList = new ArrayList<String>();  // 保存进程的输入流信息
    private List<String> errorOutList = new ArrayList<String>(); // 保存进程的错误流信息

    public void executeCommand(String command) {
        // 先清空
        stdOutList.clear();
        errorOutList.clear();
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            // 创建2个线程，分别读取输入流缓冲区和错误流缓冲区
            ThreadUtil stdOutUtil = new ThreadUtil(process.getInputStream(), stdOutList);
            ThreadUtil errorOutUtil = new ThreadUtil(process.getErrorStream(), errorOutList);
            //启动线程读取缓冲区数据
            stdOutUtil.start();
            errorOutUtil.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error message: " + e.getLocalizedMessage());
        }
    }

    public List<String> getStdOutList() {
        return stdOutList;
    }

    public List<String> getErrorOutList() {
        return errorOutList;
    }
}

class ThreadUtil implements Runnable {
    private List<String> list;
    private InputStream inputStream;

    public ThreadUtil(InputStream inputStream, List<String> list) {
        this.inputStream = inputStream;
        this.list = list;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setDaemon(true); //将其设置为守护线程
        thread.start();
    }

    public void run() {
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            if ((line = br.readLine()) != null) {
                do {
                    if (line != null) {
                        list.add(line);
                        System.out.println(line);
                    }
                } while ((line = br.readLine()) != null);
            }
        } catch (IOException e) {
            System.out.println("Error message: " + e.getStackTrace());
        } finally {
            try {
                inputStream.close(); // release resource.
                br.close();
            } catch (IOException e) {
                System.out.println("Error message: " + e.getStackTrace());
            }
        }
    }
}