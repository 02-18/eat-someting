package com.eat.controller;

import com.eat.mapper.EatMapper;
import com.eat.mapper.UserMapper;
import com.eat.pojo.*;
import com.eat.untils.R;
import com.eat.untils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/api/eat")
public class EatController {

    @Autowired
    private EatMapper eatMapper;

    @Autowired
    private UserMapper userMapper;

    //增加配料
    @PostMapping("/add/ingredient")
    public R addCookBook(@RequestBody Ingredient ingredient) {
        String name = ingredient.getName();
        String image = ingredient.getImage();

        Ingredient insertIngredient = new Ingredient();
        insertIngredient.setId(StringUtil.getUUID());
        insertIngredient.setName(name);
        insertIngredient.setImage(image.substring(22));
        eatMapper.addIngredient(insertIngredient);
        return R.SUCCESS("Successfully added ingredients!");
    }

    @GetMapping("/list/ingredient")
    public R listIngredientPageHelper(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(currentPage, pageSize);
        List<Ingredient> listIngredient = eatMapper.listIngredient();
        PageInfo<Ingredient> pageInfo = new PageInfo<>(listIngredient);
        return R.SUCCESS("Successfully obtained ingredient list!").setData(pageInfo);
    }

    @GetMapping("/list/all/ingredient")
    public R listIngredientListAll() {
        List<Ingredient> listAllIngredient = eatMapper.listIngredient();
        PageInfo<Ingredient> pageInfo = new PageInfo<>(listAllIngredient);
        return R.SUCCESS("Successfully obtained ingredient list!").setData(pageInfo);
    }

    @DeleteMapping("/delete/ingredient")
    public R deleteIngredient(@RequestParam("id") String id) {
        if (StringUtil.isEmpty(id)) {
            return R.FAILED("The ingredient ID cannot be empty!");
        }
        eatMapper.deleteIngredient(id);
        return R.SUCCESS("Successfully deleted ingredients!");
    }

    @PostMapping("/add/menu")
    public R addMenu(@RequestBody FoodMenu foodMenu, @RequestParam("ingredients") String ingredients) {
        String name = foodMenu.getName();
        String image = foodMenu.getImage();
        String type = foodMenu.getType();

        FoodMenu menu = new FoodMenu();
        menu.setId(StringUtil.getUUID());
        menu.setName(name);
        menu.setImage(image.substring(22));
        menu.setType(type);

        String[] ingredientsArray = ingredients.split(",");
        for (String s : ingredientsArray) {
            IngredientMenu ingredientMenu = new IngredientMenu();
            ingredientMenu.setId(StringUtil.getUUID());
            ingredientMenu.setMenuId(menu.getId());
            ingredientMenu.setIngredientId(s);
            eatMapper.addIngredientMenu(ingredientMenu);
        }
        eatMapper.addMenu(menu);


        return R.SUCCESS("Successfully added recipe!");
    }

    @GetMapping("/list/menu")
    public R listFoodMenu(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(currentPage, pageSize);
        List<FoodMenu> listFoodMenu = eatMapper.listFoodMenu();
        PageInfo<FoodMenu> pageInfo = new PageInfo<>(listFoodMenu);
        return R.SUCCESS("Successfully obtained recipe list!").setData(pageInfo);
    }

    @GetMapping("/list/all/menu")
    public R listFoodMenuAll() {
        List<FoodMenu> listFoodMenu = eatMapper.listFoodMenu();
        PageInfo<FoodMenu> pageInfo = new PageInfo<>(listFoodMenu);
        return R.SUCCESS("Successfully obtained ingredient list!").setData(pageInfo);
    }

    @GetMapping("/search/menu")
    public R searchFoodMenu(@RequestParam("name") String name) {
        List<FoodMenu> listFoodMenu = eatMapper.searchFoodMenu("%" + name + "%");
        PageInfo<FoodMenu> pageInfo = new PageInfo<>(listFoodMenu);
        return R.SUCCESS("Successfully obtained ingredient list!").setData(pageInfo);
    }

    @DeleteMapping("/delete/menu")
    public R deleteFoodMenu(@RequestParam("id") String id) {
        if (StringUtil.isEmpty(id)) {
            return R.FAILED("The recipe ID cannot be empty!");
        }

        eatMapper.deleteFoodMenu(id);
        eatMapper.deleteEnshrineByMenuId(id);
        eatMapper.deleteMenuIngredient(id);
        eatMapper.deleteMenuStep(id);
        return R.SUCCESS("Successfully deleted recipe!");
    }


    @PostMapping("/upload/picture")
    public R uploadPicture(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filePath = "";
        String fileSavePath = "F:/img/";
        request.setCharacterEncoding("utf-8");


        File dir = new File(fileSavePath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            Iterator<String> iterator = req.getFileNames();
            while (iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
                File file1 = new File(fileSavePath + "/" + newFileName);
                file.transferTo(file1);
                filePath = request.getScheme() + "://" +
                        request.getServerName() + ":"
                        + request.getServerPort()
                        + "/img/" + "/" + newFileName;
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return R.SUCCESS("Image uploaded successfully!").setData(filePath);
    }


    @GetMapping("/info/menu")
    public R getMenuInfoById(@RequestParam("id") String id) {
        FoodMenu menuInfoById = eatMapper.findMenuInfoById(id);
        if (menuInfoById == null) {
            return R.FAILED("This recipe does not exist!");
        }

        return R.SUCCESS("Recipe success!").setData(menuInfoById);
    }


    @PostMapping("/add/enshrine")
    public R addEnshrine(@RequestParam("menuId") String menuId, @RequestParam("username") String username, @RequestBody Enshrine enshrine) {
        Enshrine enshrineById = eatMapper.findEnshrineById(menuId, username);
        if (enshrineById != null) {
            return R.FAILED("This recipe has been saved by this account!");
        }

        FoodMenu menuInfoById = eatMapper.findMenuInfoById(menuId);
        if (menuInfoById == null) {
            return R.FAILED("This recipe does not exist!");
        }
        User userByUserName = userMapper.findUserByUserName(username);
        if (userByUserName == null) {
            return R.FAILED("The account does not exist!");
        }
        Enshrine insertEnshrine = new Enshrine();
        insertEnshrine.setId(StringUtil.getUUID());
        insertEnshrine.setMenuId(menuInfoById.getId());
        insertEnshrine.setUsername(userByUserName.getUsername());
        insertEnshrine.setEnshrineName(enshrine.getEnshrineName());
        insertEnshrine.setEnshrineImage(enshrine.getEnshrineImage());
        insertEnshrine.setEnshrineType(enshrine.getEnshrineType());

        eatMapper.addEnshrine(insertEnshrine);

        return R.SUCCESS("Successful collection!");

    }

    @GetMapping("/delete/enshrine")
    public R deleteEnshrine(@RequestParam("menuId") String menuId, @RequestParam("username") String username) {
        FoodMenu menuInfoById = eatMapper.findMenuInfoById(menuId);
        if (menuInfoById == null) {
            return R.FAILED("This recipe does not exist!");
        }
        eatMapper.deleteEnshrine(menuId, username);

        return R.SUCCESS("Uncollect successfully!");
    }


    @GetMapping("/status/enshrine")
    public R getEnshrineStatus(@RequestParam("menuId") String menuId, @RequestParam("username") String username) {
        User userByUserName = userMapper.findUserByUserName(username);
        if (userByUserName == null) {
            return R.FAILED("The account does not exist!");
        }
        FoodMenu menuInfoById = eatMapper.findMenuInfoById(menuId);
        if (menuInfoById == null) {
            return R.FAILED("This recipe does not exist!");
        }
        Enshrine enshrineById = eatMapper.findEnshrineById(menuId, username);
        if (enshrineById == null) {
            return R.SUCCESS("Get Favorites status").setData(0);
        }

        return R.SUCCESS("Get Favorites status!").setData(1);
    }

    @GetMapping("/list/enshrine")
    public R listEnshrine(@RequestParam("username") String username) {
        User userByUserName = userMapper.findUserByUserName(username);
        if (userByUserName == null) {
            return R.FAILED("The account does not exist!");
        }
        List<Enshrine> enshrines = eatMapper.listEnshrineByUserName(username);
        return R.SUCCESS("The favorites list is obtained successfully. Procedure!").setData(enshrines);
    }


    @GetMapping("/recommend")
    public R getRecommendByIngredientsId(@RequestParam("ingredientsId") String ingredientsId) {
        String[] ids = ingredientsId.split(",");
        Set<FoodMenu> list = new HashSet<>();
        for (String id : ids) {
            List<IngredientMenu> ingredientMenus = eatMapper.selectMenuIdByIngredientId(id);
            for (IngredientMenu ingredientMenu : ingredientMenus) {
                FoodMenu menuInfoById = eatMapper.findMenuInfoById(ingredientMenu.getMenuId());
                list.add(menuInfoById);
            }
        }
        return R.SUCCESS("Succeeded in obtaining the recommended recipe!").setData(list);
    }

    @GetMapping("/add/step")
    public R addMenuStep(@RequestParam("menuId") String menuId, @RequestParam("content") String content) {
        FoodMenu menuInfoById = eatMapper.findMenuInfoById(menuId);
        if (menuInfoById == null) {
            return R.FAILED("This recipe does not exist!");
        }

        List<Step> menuStepById = eatMapper.findMenuStepById(menuId);
        if (menuStepById.size() > 0) {
            return R.FAILED("The recipe step already exists!");
        }
        String[] step = content.split(",");
        int firstOrder = 1;
        for (String s : step) {
            Step addStep = new Step();
            addStep.setId(StringUtil.getUUID());
            addStep.setMenuId(menuId);
            addStep.setStepName(s);
            addStep.setMenuName(menuInfoById.getName());
            addStep.setOrderNum(firstOrder++);
            eatMapper.addStep(addStep);
        }

        return R.SUCCESS("Step add successfully!");
    }


    @GetMapping("/info/step")
    public R getMenuStepInfo(@RequestParam("menuId") String menuId) {
        FoodMenu menuInfoById = eatMapper.findMenuInfoById(menuId);
        if (menuInfoById == null) {
            return R.FAILED("This recipe does not exist!");
        }

        List<Step> menuStepById = eatMapper.findMenuStepById(menuId);
        if (menuStepById.size() == 0) {
            return R.FAILED("This recipe step does not exist!");
        }

        return R.SUCCESS("Obtaining the recipe step succeeded!").setData(menuStepById);
    }


}
