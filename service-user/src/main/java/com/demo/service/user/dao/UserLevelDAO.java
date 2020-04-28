package com.demo.service.user.dao;

import com.demo.service.user.model.UserLevel;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserLevelDAO extends CrudRepository<UserLevel, Integer> {

    /**
     * 给用户增加等级
     */
    @Modifying
    @Query("UPDATE tb_user_level SET `level` = `level` + 1 WHERE username = :username")
    int updateLevelByUsername(@Param("username") String username);

}
