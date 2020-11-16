package com.example.springAOP;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * 测试Spring的AOP
 * @Author: HYX
 * @Date: 2020/11/2 17:14
 */
@RestController
@RequestMapping(value = "aop")
public class AopController {

    @RequestMapping(value = "post")
    public String postMethod()
    {
        String result = "POST{message:SUCCESS,code:200}";
        System.out.println(result);
        return result;
    }

    @GetMapping(value = "get")
    public String getMethod()
    {
        String result = "GET{message:SUCCESS,code:200}";
        System.out.println(result);
        return result;
    }

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    @PermissionsAnnotation
    public String getList(@RequestBody JSONObject request)
    {
        return "请求成功！";
    }
}
