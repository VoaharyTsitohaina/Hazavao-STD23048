package com.examen.hazavao.endpoint.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.examen.hazavao.service.HazavaoService;

@RestController
public class HazavaoController {

    @Autowired
    private HazavaoService hazavaoService;

    @GetMapping("/hazavao")
    public String getDefinition(@RequestParam String teny) {
        return hazavaoService.getDefinition(teny);
    }
}