package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.dao.MiaoshaUserMapper;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.IMiaoshaUserService;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUidUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserServiceImpl implements IMiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoshaUserMapper miaoshaUserMapper;
    @Override
    public Result login(HttpServletResponse response,LoginVo loginVo) {

        if(loginVo==null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        //这里是查手机，如果是查用户名呢？
        MiaoshaUser user = miaoshaUserMapper.selectByPrimaryKey(Long.valueOf(mobile));
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);

        }
        //密码比较

        if(!MD5Util.formPassToDBPass(loginVo.getPassword(),user.getSalt())
                .equals(user.getPassword())){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //利用UUid作为token
        String token = UUidUtil.uuid();
        addCookie(response,token,user);

        return Result.success(user.getNickname());




    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token))return null;

        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);

        //重新设置cookie
        if(user != null){
            addCookie(response,token,user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response,String token, MiaoshaUser user){
        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
