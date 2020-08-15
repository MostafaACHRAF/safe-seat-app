package com.sqli.safeSeat.repositories;

import com.sqli.safeSeat.models.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Integer> { }
