package com.eat.controller;


import com.eat.mapper.UserMapper;
import com.eat.pojo.User;
import com.eat.untils.JwtUtil;
import com.eat.untils.R;
import com.eat.untils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public R register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String nickname = user.getNickname();
        String cookStyle = user.getCookStyle();
        String requirements = user.getRequirements();
        String allergy = user.getAllergy();
        if (StringUtil.isEmpty(username)) {
            return R.FAILED("The account cannot be empty!");
        }
        if (StringUtil.isEmpty(password)) {
            return R.FAILED("The password cannot be empty!");
        }
        if (StringUtil.isEmpty(nickname)) {
            return R.FAILED("Nicknames cannot be empty!");
        }
        if (StringUtil.isEmpty(cookStyle)) {
            return R.FAILED("The cooking style cannot be left empty!");
        }
        if (StringUtil.isEmpty(requirements)) {
            return R.FAILED("Dietary requirements cannot be empty!");
        }
        if (StringUtil.isEmpty(allergy)) {
            return R.FAILED("Allergy information cannot be empty!");
        }

        User userByUserName = userMapper.findUserByUserName(username);
        if (userByUserName != null) {
            return R.FAILED("The account has been registered!");
        }

        User registerUser = new User();
        registerUser.setId(StringUtil.getUUID());
        registerUser.setUsername(username);
        registerUser.setPassword(password);
        registerUser.setNickname(nickname);
        registerUser.setCookStyle(cookStyle);
        registerUser.setAvatar("/img//default_avatar.png");
        registerUser.setRequirements(requirements);
        registerUser.setAllergy(allergy);
        userMapper.registerUser(registerUser);
        return R.SUCCESS("Account registration successful!");
    }

    @PostMapping("/login")
    public R findUserByUserName(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtil.isEmpty(username)) {
            return R.FAILED("The account cannot be empty!");
        }
        if (StringUtil.isEmpty(password)) {
            return R.FAILED("The password cannot be empty!");
        }
        User userByUserName = userMapper.findUserByUserName(username);
        if (userByUserName == null) {
            return R.FAILED("The account does not exist!");
        }
        if (!userByUserName.getPassword().equals(password)) {
            return R.FAILED("Incorrect password!");
        }
        String token = JwtUtil.createToken(username);

        return R.SUCCESS("Successful landing!").setData(token);
    }

    @GetMapping("/info")
    public R getUserInfo(@RequestParam("username") String username) {
        if (StringUtil.isEmpty(username)) {
            return R.FAILED("The account cannot be empty!");
        }

        User userByUserName = userMapper.findUserByUserName(username);

        return R.SUCCESS("Obtaining personal information succeeded!").setData(userByUserName);
    }

    @GetMapping("/update")
    public R updateUserAvatar(@RequestParam("avatar") String avatar, @RequestParam("username") String username) {
        User userByUserName = userMapper.findUserByUserName(username);
        if (userByUserName == null) {
            return R.FAILED("The account does not exist!");
        }
        userMapper.updateAvatar(avatar, username);
        return R.SUCCESS("Profile picture modified successfully!");
    }
}
