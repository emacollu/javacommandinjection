package com.manydesigns.javacommandinjection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author: Emanuele Collura
 * Date: 03/01/22
 * Time: 17:38
 */
@RestController

public class CommandController {
    Logger logger = LoggerFactory.getLogger(CommandController.class);

    @GetMapping
    public ResponseEntity<String> execCommand(@RequestParam("command") String command) {
        logger.info("command {}", command);
        StringBuilder response = new StringBuilder("Response command is:<br>");
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();

            response.append(readResponseProcess(p));
        } catch (IOException e) {
            logger.error("Error executing command", e);
        } catch (InterruptedException e) {
            logger.error("Interrupt Exception", e);
            Thread.currentThread().interrupt();
        }
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @GetMapping("pb")
    public ResponseEntity<String> execCommandPB(@RequestParam("command") String command) {
        logger.info("pb command {}", command);
        StringBuilder response = new StringBuilder("Response command is:<br>");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            Process p = processBuilder.start();
            p.waitFor();

            response.append(readResponseProcess(p));
        } catch (IOException e) {
            logger.error("Error executing command", e);
        } catch (InterruptedException e) {
            logger.error("Interrupt Exception", e);
            Thread.currentThread().interrupt();
        }
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    String readResponseProcess(Process p) {
        StringBuilder response = new StringBuilder();
        try {
            String line;
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while((line = error.readLine()) != null){
                response.append(line).append("<br>");
                logger.debug(line);
            }
            error.close();

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((line=input.readLine()) != null){
                response.append(line).append("<br>");
                logger.debug(line);
            }
            input.close();
        } catch (IOException e) {
            logger.error("Error executing command", e);
        }
        return response.toString();
    }

}
