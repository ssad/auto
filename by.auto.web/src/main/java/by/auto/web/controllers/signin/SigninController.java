package by.auto.web.controllers.signin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import by.auto.web.controllers.utils.ControllerUtils;
import by.auto.web.routes.Routes;
import by.auto.web.utils.AuthenticationUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SigninController {
    /**
     * Render the signin form to the account as HTML in their web browser.
     * Returns void and relies in request-to-view-name translation to kick-in to resolve the view template to render.
     */
    @RequestMapping(value = Routes.SIGNIN, method = RequestMethod.GET)
    public String signin(@RequestParam(required = false) final Integer error, final Model model, final HttpServletRequest request) {
        if (AuthenticationUtil.getPrincipal(request) != null) {
            return ControllerUtils.redirectTo(Routes.Profile.PROFILE);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "signin";
    }
}
