package net.watashiwa.hendra.redirectservice.controller;

import net.watashiwa.hendra.redirectservice.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class GreetingController
{
    private static final Logger logger = LogManager.getLogger();

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model)
    {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value = "/student-form", method = RequestMethod.POST)
    public ModelAndView studentController(
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "idNumber") String idNumber)
//            , @RequestBody Student student)
    {
        logger.debug("userName: {}, idNumber: {}", userName, idNumber);
//        logger.debug("userName: {}, idNumber: {}", student.getUserName(), student.getIdNumber());

        return new ModelAndView("successView", "message", "SUCCESS !!!");
    }
}
