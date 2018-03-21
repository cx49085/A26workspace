package com.work26.service;

import com.work26.dao.Dao_user;
import com.work26.model.Userinformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ChenJY on 2018/3/17.
 */
@Service
public class Service_user {
    @Autowired
    Dao_user dao_user;


    public boolean isExittelephone(String telephone) {
        if (dao_user.findByPhone(telephone).size() > 0) return true;
        else return false;
    }

    public int login(String telephone, String pass) {
        List<Userinformation> list = dao_user.findByPhoneAndPassword(telephone, pass);
        if (list.size() > 0) return list.get(0).getId();
        else return 0;
    }

    public int register(String phone,
                        String nickname,
                        String password,
                        String gender,
                        int age,
                        String company,
                        String work_id,
                        String work_type) {

        Userinformation user = new Userinformation();
        user.setPhone(phone);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setGender(gender);
        user.setAge(age);
        user.setCompany(company);
        user.setWorkId(work_id);
        user.setWorkType(work_type);
        dao_user.save(user);
        if (dao_user.findByPhone(phone).get(0).getId() == 0) return 0;
        else return dao_user.findByPhone(phone).get(0).getId();
    }
}

