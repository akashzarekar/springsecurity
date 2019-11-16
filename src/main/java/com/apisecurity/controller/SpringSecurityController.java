package com.apisecurity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringSecurityController {

	@RequestMapping(value = { "/", "/home" })
	public String getHomePage(Model model) {
		model.addAttribute("user", getPrinciple());
		return "home";
	}

	@RequestMapping(value = "/admin")
	public String getAdminPage(Model model) {
		model.addAttribute("admin", getPrinciple());
		return "admin";
	}

	@RequestMapping(value = "/dba")
	public String getDBAPage(Model model) {
		model.addAttribute("dba", getPrinciple());
		return "dba";
	}

	@RequestMapping(value = "/user")
	public String getUserPage(Model model) {
		model.addAttribute("user", getPrinciple());
		return "user";
	}

	@RequestMapping(value = "/accessDenied")
	public String getaccessDeniedPage(Model model) {
		return "accessDenied";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "home";
	}

	private String getPrinciple() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (obj instanceof UserDetails) {
			return ((UserDetails) obj).getUsername();
		}
		return obj.toString();
	}
}
