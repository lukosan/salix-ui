package org.lukosan.salix.ui;

import org.lukosan.salix.MapUtils;
import org.lukosan.salix.SalixConfiguration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/salix/ui/configuration")
public class ConfigurationController extends ScopedController {

	@RequestMapping("/")
	public String index(Authentication authentication, Model model) {
		model.addAttribute("configurations", filter(authentication, salixService.allConfigurations()));		
		return "salix/ui/configuration/index";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/", method=RequestMethod.GET)
	public String templates(@PathVariable String scope, Model model) {
		model.addAttribute("configurations", salixService.configurationsIn(scope));
		model.addAttribute("scope", scope);
		return "salix/ui/configuration/index";
	}

	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/", method=RequestMethod.POST)
	public String create(@PathVariable String scope, @RequestParam String target) {
		return "redirect:/salix/ui/configuration/" + scope + "/" + target + "/";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{target}/", method=RequestMethod.GET)
	public String edit(@PathVariable String scope, @PathVariable String target, Model model) {
		SalixConfiguration configuration = salixService.configuration(scope, target);
		if(null == configuration)
			configuration = new UiSalixConfiguration(target, scope);
		model.addAttribute("configuration", configuration);
		return "salix/ui/configuration/edit";
	}
	
	@PreAuthorize("hasPermission(#scope, 'SALIX_USER')")
	@RequestMapping(value="/{scope}/{target}/", method=RequestMethod.POST)
	public String edit(@PathVariable String scope, @PathVariable String target, @RequestParam String map) {
		salixService.save(scope, target, MapUtils.fromString(map));
		return "redirect:/salix/ui/configuration/" + scope + "/";
	}
}
