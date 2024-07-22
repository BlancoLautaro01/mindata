package com.mindata.repositories;

import com.mindata.domain.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT " +
            " CASE WHEN EXISTS " +
            " (SELECT * FROM app_user WHERE email = :email) " +
            "    THEN 'TRUE' " +
            "    ELSE 'FALSE' " +
            "END", nativeQuery = true)
    Boolean emailExists(@Param("email") String email);
}
