package org.launchcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HelloController {

    @RequestMapping(value="")
    @ResponseBody
    public String index(HttpServletRequest request){

        String name = request.getParameter("name");

        if(name == null) {
            name = "World";
        }

        return "Hello " + name;
    }

    @RequestMapping(value="hello", method=RequestMethod.GET)
    @ResponseBody
    public String helloForm(HttpServletRequest request){

        String html = "<form method='post'>" +
                "<input type='text' name='name' />" +
                "<input type='submit' value='Greet Me!' />" +
                "<form/>";

        return html;
    }

    @RequestMapping(value="hello", method = RequestMethod.POST)
    @ResponseBody
    public String helloPost(HttpServletRequest request){

        String name = request.getParameter("name");

        return "Hello " + name;

    }

    @RequestMapping(value="hello-world", method=RequestMethod.GET)
    @ResponseBody
    public String helloWorldForm(HttpServletRequest request){

        String html = "<form method='post'>" +
                "<input type='text' name='name' />" +
                "<select name='language'>" +
                "<option value='en'>English</option>" +
                "<option value='es'>Spanish</option>" +
                "<option value='fr'>French</option>" +
                "<option value='de'>German</option>" +
                "<option value='it'>Italian</option>" +
                "<option value='ja'>Japanese</option>" +
                "</select>" +
                "<input type='submit' value='Greet Me!' />" +
                "<form/>";

        return html;
    }

    @RequestMapping(value="hello-world", method = RequestMethod.POST)
    @ResponseBody
    public String helloWorldPost(HttpServletRequest request, HttpServletResponse response){

        String name = request.getParameter("name");
        String language = request.getParameter("language");

        int greetingCount = 1;
        Cookie[] cookies = request.getCookies();
        Cookie newCookie;
        if(cookies != null){
            for (Cookie cookie: cookies) {
                if("greetingCount".equals(cookie.getName())) {
                    greetingCount += Integer.parseInt(cookie.getValue());
                    break;
                }

            }
        }

        String greeting = "Hello";
        if("es".equals(language)) greeting = "Hola";
        else if("fr".equals(language)) greeting = "Bonjour";
        else if("de".equals(language)) greeting = "Guten tag";
        else if("it".equals(language)) greeting = "Ciao";
        else if("ja".equals(language)) greeting = "Konichiwa";

        newCookie = new Cookie("greetingCount", String.valueOf(greetingCount));
        response.addCookie(newCookie);

        return "<div>" + greeting + " " + name + "</div>" +
                "<div>" + "Total greetings:" + greetingCount + "</div>";
    }

    @RequestMapping(value="hello/{name}")
    @ResponseBody
    public String helloUrlSegment(@PathVariable String name){
        return "Hello " + name;
    }

    @RequestMapping(value="goodbye")
    public String goodbye(){
        return "redirect:/";
    }

}
