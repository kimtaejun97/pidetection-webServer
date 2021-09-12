package com.pidetection.module.crawling;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface CrawlingRepository extends JpaRepository<Crawling, Long> {

    @Query("SELECT C.url FROM Crawling C WHERE C.hash like ?1")
    List<String> findUrlByHash(String hash);

}