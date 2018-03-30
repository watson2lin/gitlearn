/*
 * Copyright (c) 2017, YouCash and/or its affiliates. All rights reserved.
 * YouCash PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.youcash.uc.service.impl;


import com.youcash.uc.service.impl.emp.EmpServiceImpl;
import com.youcash.uc.service.impl.emp.UserServiceImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * @author Bruce
 */
@Configuration
@ApplicationPath("service")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(EmpServiceImpl.class);
        register(UserServiceImpl.class);
    }
}
