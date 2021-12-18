package com.ww.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SwaggerRedirectController {

  @GetMapping("/")
  public ModelAndView swaggerRedirect(ModelMap modelMap) {
    return new ModelAndView("redirect:/swagger-ui.html", modelMap);
  }

}
