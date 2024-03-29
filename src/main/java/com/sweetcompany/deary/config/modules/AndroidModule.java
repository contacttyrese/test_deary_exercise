package com.sweetcompany.deary.config.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.sweetcompany.deary.config.Constants;
import com.sweetcompany.deary.contracts.Login;
import com.sweetcompany.deary.contracts.Tour;
import com.sweetcompany.deary.pages.LoginPage;
import com.sweetcompany.deary.pages.TourPage;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class AndroidModule extends AbstractModule {
    private AndroidDriver appiumDriver;
    private final Logger log = LogManager.getLogger(this.getClass().getName());

    private Map<String, Object> getAppiumOptions(Config config) {
        Map<String, Object> options = new HashMap<>();
        options.put("automationName", "UiAutomator2");
        options.put("app", config.getString("user.dir") + config.getString("env.alpha.app-path"));
        options.put("appPackage", "com.sweetcompany.deary");
        options.put("appWaitActivity", "com.sweetcompany.deary.tutorial.InitTutorialActivity");
        options.put("noReset", false);
        options.put("fullReset", false);
        options.put("deviceName", Constants.nexusEmulatorName);
        options.put("udid", Constants.nexusEmulatorId);
        options.put("newCommandTimeout", 9999);
        return options;
    }

    @Override
    public void configure() {
        log.debug("Wiring bindings..");
        bind(Login.class).to(LoginPage.class);
        bind(Tour.class).to(TourPage.class);
    }

    @Provides
    @Singleton
    public Config provideConfiguration() {
        log.debug("Providing configuration..");
        File configFile = new File(Constants.configPath);
        Config config = ConfigFactory.systemProperties()
                .withFallback(ConfigFactory.parseFile(configFile))
                .resolve();
        return config;
    }

    @Provides
    @Singleton
    public AppiumDriver provideAppiumDriver(Config config) {
        log.debug("Providing Appium Driver..");
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:options", getAppiumOptions(config));

        try {
            if (log.isInfoEnabled()) {
                log.info("Connecting to Appium Server...");
            }
            appiumDriver = new AndroidDriver(new URL("http://0.0.0.0:" + config.getString("env.alpha.appium-port")), caps);
            if (log.isInfoEnabled()) {
                log.info("Connected successfully!!!");
            }
        } catch (MalformedURLException exception) {
            if (log.isInfoEnabled()) {
                log.info("Failed to connect. Printing stack trace...");
            }
            exception.printStackTrace();
        }
        return appiumDriver;
    }

    @Provides
    @Singleton
    public FluentWait provideWebDriverWait(AppiumDriver appiumDriver) {
        log.debug("Providing Wait");
        FluentWait fluentWait = new FluentWait(appiumDriver)
                .pollingEvery(Duration.ofSeconds(3L))
                .withTimeout(Duration.ofSeconds(10L))
                .ignoring(NoSuchElementException.class)
                .withMessage(Constants.waitErrorMsg);
        return fluentWait;
    }

}
