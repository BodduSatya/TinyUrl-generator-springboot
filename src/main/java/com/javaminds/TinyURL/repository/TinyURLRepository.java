package com.javaminds.TinyURL.repository;

import com.javaminds.TinyURL.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TinyURLRepository extends JpaRepository<Url,String> {

}