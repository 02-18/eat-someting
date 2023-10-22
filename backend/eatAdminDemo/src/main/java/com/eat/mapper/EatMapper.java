package com.eat.mapper;

import com.eat.pojo.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EatMapper {



    @Insert("insert into ingredient (id,name,image) values (#{id},#{name},#{image})")
    int addIngredient(Ingredient insertIngredient);

    @Select("select * from ingredient")
    List<Ingredient> listIngredient();

    @Delete("delete from ingredient where id = #{id}")
    int deleteIngredient(String id);

    @Insert("insert into menu (id,name,image,type) values (#{id},#{name},#{image},#{type})")
    int addMenu(FoodMenu menu);

    @Select("select * from menu")
    List<FoodMenu> listFoodMenu();

    @Select("select * from menu where name like #{name}")
    List<FoodMenu> searchFoodMenu(String name);

    @Delete("delete from menu where id = #{id}")
    int deleteFoodMenu(String id);


    @Select("select * from menu where id = #{id}")
    FoodMenu findMenuInfoById(String id);

    @Insert("insert into enshrine (id,menu_id,username,enshrine_name,enshrine_image,enshrine_type) values (#{id},#{menuId},#{username},#{enshrineName},#{enshrineImage},#{enshrineType})")
    int addEnshrine(Enshrine insertEnshrine);

    @Delete("delete from enshrine where menu_id = #{menuId} and username = #{username}")
    int deleteEnshrine(String menuId,String username);

    @Select("select * from enshrine where menu_id = #{menuId} and username = #{username}")
    Enshrine findEnshrineById(String menuId, String username);

    @Select("select * from enshrine where username = #{username}")
    List<Enshrine> listEnshrineByUserName(String username);

    @Insert("insert into menu_ingredient (id,menu_id,ingredient_id) values (#{id},#{menuId},#{ingredientId})")
    int addIngredientMenu(IngredientMenu ingredientMenu);

    @Select("select * from menu_ingredient where ingredient_id = #{id}")
    List<IngredientMenu> selectMenuIdByIngredientId(String id);

    @Select("select  * from step where menu_id = #{menuId} order by orderNum")
    List<Step> findMenuStepById(String menuId);

    @Insert("insert into step (id,step_name,menu_id,menu_name,orderNum) values (#{id},#{stepName},#{menuId},#{menuName},#{orderNum})")
    int addStep(Step addStep);

    @Delete("delete from enshrine where menu_id = #{id}")
    int deleteEnshrineByMenuId(String id);

    @Delete("delete from menu_ingredient where menu_id = #{id}")
    int deleteMenuIngredient(String id);

    @Delete("delete from step where menu_id = #{id}")
    int deleteMenuStep(String id);
}
