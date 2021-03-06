package com.sweetcompany.deary.config;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.sweetcompany.deary.config.modules.AndroidModule;
import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyInjector implements InjectorSource {
    private final Logger log = LoggerFactory.getLogger(MyInjector.class.getName());

    @Override
    public Injector getInjector() {
        log.debug("Preparing Android Module...");
        AbstractModule platformModule = new AndroidModule();
        return Guice.createInjector(Stage.PRODUCTION, CucumberModules.createScenarioModule(), platformModule);
    }
}
