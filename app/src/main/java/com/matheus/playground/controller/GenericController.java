package com.matheus.playground.controller;

import com.matheus.playground.dto.BaseAbstract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generic")
public class GenericController {

    public ResponseEntity<BaseAbstract> post(
            @RequestBody BaseAbstract baseAbstract
    ) {
        System.out.println(baseAbstract);
        return null;
    }
}
