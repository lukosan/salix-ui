package org.lukosan.salix.ui;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salix/ui")
public class DashboardController extends ScopedController {

	// TODO At the moment we're using the defaultTemplateResolver but this
	// can be reconfigured to look for different file extensions by Spring
	// i.e. we probably need another one which _definitely_ looks in the place
	// we've put our template files
	@RequestMapping("/")
	public String index() {
		return "salix/ui/index";
	}
	
	@RequestMapping("/scopes/")
	public String scopes(Authentication authentication, Model model) {
		Set<String> scopes = salixScopeRegistry.all();
		if(null != permissionEvaluator)
			scopes = scopes.stream().filter(s -> permissionEvaluator.hasPermission(authentication, s, "SALIX_USER")).collect(Collectors.toSet());
		model.addAttribute("scopes", scopes);
		return "salix/ui/scopes";
	}
	
}
