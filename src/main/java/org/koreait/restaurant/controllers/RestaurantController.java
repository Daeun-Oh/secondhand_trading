package org.koreait.restaurant.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.global.libs.Utils;
import org.koreait.restaurant.entities.Restaurant;
import org.koreait.restaurant.repositories.RestaurantRepository;
import org.koreait.restaurant.services.RestaurantInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final HttpServletRequest request;
    private final RestaurantRepository repository;
    private final RestaurantInfoService infoService;
    private final ObjectMapper om;
    private final Utils utils;

    @GetMapping({"", "/list"})
    public String list(@RequestParam(required = false) String search, Model model) {
        commonProcess("list", model);

        try {
            List<Restaurant> items;

            System.out.println("search: " + search);

            if (search != null && !search.isBlank()) {
                items = repository.findByNameContainingIgnoreCase(search); // 예: 이름으로 검색
                System.out.println("items: " + items);
            } else {
                items = repository.findAll().subList(0, 10);
                System.out.println("items(기본): " + items);
            }

            String json = om.writeValueAsString(items);
            model.addAttribute("json", json);
            model.addAttribute("search", search); // 검색어 유지
        } catch (Exception e) {
            model.addAttribute("json", "[]");
            e.printStackTrace();
        }

        return utils.tpl("restaurant/list");
    }

    @ResponseBody
    @GetMapping("/search")
    public List<Restaurant> search(@ModelAttribute RestaurantSearch search) {
        List<Restaurant> items = infoService.getNearest(search);
        return items;
    }

    @ResponseBody
    @RequestMapping("/train")
    public List<Restaurant> train() {
        return repository.findAll();
    }

    /**
     * 공통 처리 부분
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {

        mode = StringUtils.hasText(mode) ? mode : "list";

        String pageTitle = "";
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
//        List<String> addCommonScript = new ArrayList<>();

        if (mode.equals("list")) {
            pageTitle = utils.getMessage("오늘의 식당");
            addCss.add("restaurant/list");
            addScript.add("restaurant/list");
            addScript.add("restaurant/map");
        }

        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
//        model.addAttribute("addCommonScript", addCommonScript);
    }
}
