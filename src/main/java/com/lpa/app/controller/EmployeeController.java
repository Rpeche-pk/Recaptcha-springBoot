package com.lpa.app.controller;

import com.lpa.app.dto.EmployeeDto;
import com.lpa.app.entities.EmployeeEntity;
import com.lpa.app.service.EmployeeService;
import com.lpa.app.service.RecaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RecaptchaService recaptchaService;
    @GetMapping(path = {"/","/all"})
    public String showAll(Model model){

        List<EmployeeEntity> employeeEntityList=employeeService.findAll();
        model.addAttribute("employees",employeeEntityList);
        return "index";
    }

    @GetMapping("/create/form")
    public String createForm(Model model){
        model.addAttribute("employee",new EmployeeEntity());
        return "form";
    }

    @PostMapping("/create/process")
    public String createProcess(@ModelAttribute(name = "employee") EmployeeDto employeeDto
    , @RequestParam(name = "g-recaptcha-response") String captcha , Model model){

        boolean captchaValid= recaptchaService.validateRecaptcha(captcha);
        if (captchaValid) {
            EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .name(employeeDto.getName())
                    .lastName(employeeDto.getLastName())
                    .dateOfBirth(employeeDto.getDateOfBirth())
                    .build();
            employeeService.createEmployee(employeeEntity);
            return "redirect:/all";
        }else {
            model.addAttribute("message","Captcha invalido rey");
            return "error";
        }

    }
}
