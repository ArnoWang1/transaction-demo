package com.demo.service.user.dao;

import com.demo.service.user.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends CrudRepository<User, Integer> {

    /**
     * 给用户增加积分
     */
    @Modifying
    @Query("UPDATE tb_user SET score = score + 10 WHERE username = :username")
    int updateScoreByUsername(@Param("username") String username);

}
