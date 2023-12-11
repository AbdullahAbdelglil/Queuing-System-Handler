package com.example.QueuingSystem.controller;

import com.example.QueuingSystem.model.SystemInputs;
import com.example.QueuingSystem.services.SystemServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/queue")
public class SystemController {

    private final SystemServices systemServices;

    @Autowired
    public SystemController(SystemServices systemServices) {
        this.systemServices = systemServices;
    }


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor editor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class,editor);
    }

    @RequestMapping("/home")
    public String showMainPage(Model model) {

        model.addAttribute("systemCharacteristics", new SystemInputs());

        return "main-page";
    }

    @RequestMapping("/solveTheSystem")
    public String solveTheSystem(@Valid @ModelAttribute("systemCharacteristics") SystemInputs inputs,
                                 BindingResult result, Model model) {

        String systemModel;


        if (result.hasErrors()) {
            return "main-page";
        } else {
            if (inputs.getSystemCapacity() == null) inputs.setSystemCapacity(0.0);

            systemServices.setSystemInputs(inputs);

            if (inputs.getSystemCapacity() == 0 && inputs.getNumberOfServers() == 1) {
                systemServices.handle_MM1_Model();
                systemModel = "M / M / 1";
            } else if (inputs.getSystemCapacity() != 0 && inputs.getNumberOfServers() == 1) {
                systemServices.handle_MM1K_Model();
                systemModel = "M / M / 1 / K";
            } else if (inputs.getSystemCapacity() == 0 && inputs.getNumberOfServers() > 1) {
                systemServices.handle_MMC_Model();
                systemModel = "M / M / C";
            } else {
                systemServices.handle_MMCK_Model();
                systemModel = "M / M / C / K";

            }
        }

        model.addAttribute("result", systemServices);
        model.addAttribute("systemModel", systemModel);

        return "result-page";
    }
}
