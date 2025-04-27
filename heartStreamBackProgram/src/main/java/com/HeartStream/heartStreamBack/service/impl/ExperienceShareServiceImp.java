package com.HeartStream.heartStreamBack.service.impl;

import com.HeartStream.heartStreamBack.mapper.ExperienceShareRepository;
import com.HeartStream.heartStreamBack.model.ExperienceShare;
import com.HeartStream.heartStreamBack.service.ExperienceShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExperienceShareServiceImp implements ExperienceShareService {
    @Autowired
    private ExperienceShareRepository experienceShareRepository;
    @Override
    public ExperienceShare saveExperienceShare(ExperienceShare experienceShare) {
        experienceShare.setShareTime(new Date());
        return experienceShareRepository.save(experienceShare);
    }
}
