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
 * @date 2020/7/22 11:27
 * @email 2641007740@qq.com
 */
@WebServlet(value = "/my-async-servlet",asyncSupported = true)
public class MyAsyncServlet extends HttpServlet {

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
                    Thread.sleep(new Random().nextInt(2000));
                    writer.append("你要的结果");
                    writer.flush();
                    writer.close();
                } catch (InterruptedException e) {
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
