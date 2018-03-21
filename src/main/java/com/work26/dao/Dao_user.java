package com.work26.dao;

import com.work26.model.Userinformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChenJY on 2018/3/17.
 */
@Repository
public interface Dao_user extends JpaRepository<Userinformation,Integer> {
    //根据用户名密码查询
    List<Userinformation> findByPhoneAndPassword(String telephone, String password);

    //查询用户名是否存在
    List<Userinformation> findByPhone(String telephone);
}
