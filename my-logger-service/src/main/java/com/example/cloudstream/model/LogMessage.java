package com.example.cloudstream.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogMessage implements Serializable {

    private static final long serialVersionUID = -5705286130244263203L;

    private String message;

    @Override
    public String toString() {
        return this.message;
    }
}
