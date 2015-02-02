package iEat.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import iEat.model.Dish;
import iEat.service.DishesService;
import iEat.vo.DishListVO;

import java.util.Locale;

@Controller
@RequestMapping(value = "/protected/dishes")
public class DishesController {

    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private DishesService dishesService;

    @Autowired
    private MessageSource messageSource;

    @Value("5")
    private int maxResults;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("dishesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
        return createListAllResponse(page, locale);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@ModelAttribute("dish") Dish dish,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        dishesService.save(dish);

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.create.success");
        }

        return createListAllResponse(page, locale, "message.create.success");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") int contactId,
                                    @RequestBody Dish dish,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        if (contactId != dish.getId()) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }

        dishesService.save(dish);

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.update.success");
        }

        return createListAllResponse(page, locale, "message.update.success");
    }

    @RequestMapping(value = "/{dishId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("dishId") int dishId,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {


        try {
            dishesService.delete(dishId);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        }

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.delete.success");
        }

        return createListAllResponse(page, locale, "message.delete.success");
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@PathVariable("name") String name,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        return search(name, page, locale, null);
    }

    private ResponseEntity<?> search(String name, int page, Locale locale, String actionMessageKey) {
        DishListVO dishListVO = dishesService.findByNameLike(page, maxResults, name);

        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToVO(dishListVO, locale, actionMessageKey, null);
        }

        Object[] args = {name};

        addSearchMessageToVO(dishListVO, locale, "message.search.for.active", args);

        return new ResponseEntity<DishListVO>(dishListVO, HttpStatus.OK);
    }

    private DishListVO listAll(int page) {
        return dishesService.findAll(page, maxResults);
    }

    private ResponseEntity<DishListVO> returnListToUser(DishListVO dishesList) {
        return new ResponseEntity<DishListVO>(dishesList, HttpStatus.OK);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale) {
        return createListAllResponse(page, locale, null);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey) {
        DishListVO dishesListVO = listAll(page);

        addActionMessageToVO(dishesListVO, locale, messageKey, null);

        return returnListToUser(dishesListVO);
    }

    private DishListVO addActionMessageToVO(DishListVO dishesListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return dishesListVO;
        }

        dishesListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return dishesListVO;
    }

    private DishListVO addSearchMessageToVO(DishListVO dishesListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return dishesListVO;
        }

        dishesListVO.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return dishesListVO;
    }

    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }
}