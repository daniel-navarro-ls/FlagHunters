package com.example.retosflags.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.retosflags.service.ComentarioService;
import com.example.retosflags.service.RetoService;

@RestController
@RequestMapping("/api/retos")
public class RetoApiController {
    @Autowired
    private RetoService retoService;
    @Autowired
    private ComentarioService comentarioService;
}
