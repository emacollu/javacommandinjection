package com.manydesigns.javacommandinjection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Author: Emanuele Collura
 * Date: 20/03/22
 * Time: 11:06
 */
public class ExecCommand {
    static Logger log = LoggerFactory.getLogger(ExecCommand.class);

    public static Process execCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            return p;
        } catch (IOException e) {
            log.error("Error executing command", e);
        } catch (InterruptedException e) {
            log.error("Interrupt Exception", e);
            Thread.currentThread().interrupt();
        }
        return null;
    }

}
