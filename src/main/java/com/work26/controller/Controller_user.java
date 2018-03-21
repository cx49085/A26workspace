package com.work26.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.work26.service.Service_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.work26.service.SmsDemo.sendSms;

/**
 * Created by ChenJY on 2018/3/17..
 */
@Controller
@CrossOrigin
@RequestMapping(value = "user", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
public class Controller_user {
    @Autowired
    private Service_user service_user;

    @Autowired
    private HttpSession session;


    @RequestMapping(value = "login")
    @ResponseBody
    public String Login(@RequestBody HashMap<String, String> Input_map) {
        System.out.println(Input_map.toString());
        String telephone = (String) Input_map.get("phone");
        String pass = (String) Input_map.get("password");
        int id = service_user.login(telephone, pass);
        Map<String, Object> Output_map = new HashMap<>();
        if (service_user.login(telephone, pass) > 0) {
            Output_map.put("info", "登陆成功");
            Output_map.put("id", id);
            session.setAttribute("user_id", id);
        } else {
            System.out.println("登陆失败返回");
            Output_map.put("info", "登录失败");
        }
        return JSON.toJSONString(Output_map);

    }

    @RequestMapping(value = "register")
    @ResponseBody
    public String register(@RequestBody HashMap<String, String> Input_map) {
        String phone = (String) Input_map.get("phone");
        String nickname = (String) Input_map.get("nickname");
        String password = (String) Input_map.get("password");
        String gender = (String) Input_map.get("gender");
        int age = Integer.valueOf(Input_map.get("age"));
        String company = (String) Input_map.get("company");
        String work_id = (String) Input_map.get("work_id");
        String work_type = (String) Input_map.get("work_type");
        String code = (String) Input_map.get("code");
        System.out.println(phone + "---" + code + "---" + password + "---");
        String phone_code = (String) session.getAttribute("phone_code");
        Map<String, Object> Outputmap = new HashMap<>();
        if (phone_code == null) {
            Outputmap.put("info", "验证码已经失效");
        } else if (!phone_code.equals(code)) {
            Outputmap.put("info", "验证码错误");
        } else if (service_user.register(phone,
                nickname,
                password,
                gender,
                age,
                company,
                work_id,
                work_type) > 0) {
            Outputmap.put("info", "注册成功");
        } else {
            Outputmap.put("info", "未知错误");
        }
        return JSON.toJSONString(Outputmap);
    }

    @RequestMapping(value = "/getcode")
    @ResponseBody
    public String getcode(@RequestBody HashMap<String, String> Input_map) throws
            ClientException, InterruptedException {
        //发短信
        //生成6位随机数
        String telephone = Input_map.get("phone");
        Map<String, Object> Output_map = new HashMap<>();
        if (service_user.isExittelephone(telephone)) {
            Output_map.put("info", "手机号已存在");
        } else {
            String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
            SendSmsResponse response = sendSms(telephone, code);
            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());
            Thread.sleep(3000L);
            //存入session用于异步验证
            session.setAttribute("phone_code", code);
            System.out.println("phone_code:" + code);
            Output_map.put("info", telephone);
        }

        return JSON.toJSONString(Output_map);
    }
}
