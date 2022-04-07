package com.manydesigns.javacommandinjection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Author: Emanuele Collura
 * Date: 07/04/22
 * Time: 17:53
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Command implements Serializable {

    private String value;

    @Override
    public String toString() {
        return "Command{" +
                "value='" + value + '\'' +
                '}';
    }
}
