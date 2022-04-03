package com.manydesigns.javacommandinjection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
@RestController @Slf4j
public class CommandController {

    @GetMapping
    public ResponseEntity<String> execCommand(@Nullable @RequestParam("command") String command) {
        log.info("command {}", command);
        if(command != null) {
            Process p = ExecCommand.execCommand(command);
            if (p != null) {
                String response = "Response command is:<br>" + readResponseProcess(p);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>("There is an error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("There is no command", HttpStatus.NO_CONTENT);
    }

    @GetMapping("pb")
    public ResponseEntity<String> execCommandPB(@RequestParam("command") String command) {
        log.info("pb command {}", command);
        StringBuilder response = new StringBuilder("Response command is:<br>");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            Process p = processBuilder.start();
            p.waitFor();

            response.append(readResponseProcess(p));
        } catch (IOException e) {
            log.error("Error executing command", e);
        } catch (InterruptedException e) {
            log.error("Interrupt Exception", e);
            Thread.currentThread().interrupt();
        }
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    String readResponseProcess(Process p) {
        StringBuilder response = new StringBuilder();
        try {
            String line;
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = error.readLine()) != null) {
                response.append(line).append("<br>");
                log.debug(line);
            }
            error.close();

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                response.append(line).append("<br>");
                log.debug(line);
            }
            input.close();
        } catch (IOException e) {
            log.error("Error executing command", e);
        }
        return response.toString();
    }

}
