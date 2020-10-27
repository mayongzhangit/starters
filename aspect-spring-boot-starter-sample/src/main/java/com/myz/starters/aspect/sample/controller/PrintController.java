package com.myz.starters.aspect.sample.controller;

import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import com.myz.starters.aspect.sample.model.UserModel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/7 20:19
 * @email 2641007740@qq.com
 */
@ParamRetValPrinter
@RestController
@RequestMapping("/print")
public class PrintController {

    private Random random = new Random();

    /**
     * 测试泛型
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/no-param")
    public void noneParam() throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
    }

    /**
     * 测试泛型
     * curl http://localhost:8080/print/out?list=1,2,3
     * @param list
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/out")
    public List<String> get(@RequestParam List<String> list) throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
        return list;
    }

    /**
     * 测试 HttpServletRequest
     * @param request
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/out-request")
    public String getRequest(HttpServletRequest request) throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
        return "request";
    }

    /**
     *
     * @param request
     * @param map
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/out-request-map")
    public String getRequest(HttpServletRequest request, @RequestParam Map<String,String> map) throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
        return "request,map";
    }


    @GetMapping("/get")
    public UserModel get(HttpServletRequest request) throws InterruptedException {
        String name = request.getParameter("name");
        Thread.sleep(random.nextInt(1000));
        UserModel userModel = new UserModel();
        userModel.setName(name);
        return userModel;
    }

    @GetMapping("/get/{id}")
    public UserModel getById(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(random.nextInt(1000));

        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setName("name"+id);
        return userModel;
    }

    /**
     * curl -XPOST http://localhost:8080/print/json-save -d "{\"id\":2,\"name\":\"zhangsan\"}" -H "Content-type:application/json;"
     * @param userModel
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/json-save")
    public UserModel jsonSave(@RequestBody UserModel userModel) throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
        return userModel;
    }

    /**
     * curl -XPOST http://localhost:8080/print/form-save -d "id=2&name=zhangsan"
     * @param userModel
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/form-save")
    public UserModel formSave(UserModel userModel) throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
        return userModel;
    }

    /**
     * curl -XPUT http://localhost:8080/print/put-save -d "id=2&name=zhangsan"
     * @param id
     * @param name
     * @return
     * @throws InterruptedException
     */
    @PutMapping("/put-save")
    public UserModel putSave(Long id,String name) throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setName(name);
        return userModel;
    }
}