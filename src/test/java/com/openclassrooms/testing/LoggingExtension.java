package com.openclassrooms.testing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class LoggingExtension implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testIntance,
                                        ExtensionContext extensionContext) throws Exception {
        Logger logger = LogManager.getLogger(testIntance.getClass());
        testIntance.getClass()
                .getMethod("setLogger", Logger.class)
                .invoke(testIntance, logger);
    }
}

