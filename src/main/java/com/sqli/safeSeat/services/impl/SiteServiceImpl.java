package com.sqli.safeSeat.services.impl;

import com.sqli.safeSeat.models.Site;
import com.sqli.safeSeat.repositories.SiteRepository;
import com.sqli.safeSeat.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    private SiteRepository siteRepository;

    @Autowired
    public SiteServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override public List<Site> findAll() {
        return this.siteRepository.findAll();
    }
}
