package com.myz.starters.dynamic.refresh.sample.servlet;

import com.myz.starters.dynamic.refresh.sample.util.PrintUtil;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author yzMa
 * @desc
 * https://blog.csdn.net/boli1020/article/details/16113789
 * 首先告诉容器，这个Servlet支持异步处理?，对于每个请求，Servlet会取得其AsyncContext?，并释放容器所分配的线程，响应被延后。对于这些被延后响应的请求，创建一个实现Runnable接口的AsyncRequest对象，并将其调度一个线程池(Thread pool)?，线程池的线程数量是固定的，让这些必须长时间处理的请求，在这些有限数量的线程中完成，而不用每次请求都占用容器分配的线程。
 * @date 2020/7/22 11:27
 * @email 2641007740@qq.com
 */
@WebServlet(value = "/my-async-servlet",asyncSupported = true)
public class MyAsyncServlet extends HttpServlet {

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        PrintUtil.print("entry servlet");

        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(1000);

        asyncContext.start(new Runnable() { // 直接在此用tomcat线程，这个不太合理，应该自己开启一个线程
            @Override
            public void run() {
                PrintUtil.print("start task");
                try {
                    Thread.sleep(3000);
                    PrintWriter writer1 = asyncContext.getResponse().getWriter();
                    PrintUtil.print("write是否一致？"+writer.equals(writer1));
                    writer1.append("你要的结果:"+new Random().nextInt(100));
                    writer1.flush();
//                    writer1.close();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
                PrintUtil.print("end task");
            }
        });
        PrintUtil.print("exit servlet");
        writer.append("即将输出结果：");
        writer.flush();
//        writer.close(); // 流不关闭，就没事
    }
}
