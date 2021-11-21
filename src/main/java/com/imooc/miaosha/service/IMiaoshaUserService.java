package com.imooc.miaosha.service;


import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface IMiaoshaUserService {
    Result login(HttpServletResponse response, LoginVo loginVo);

    MiaoshaUser getByToken(HttpServletResponse response, String token);
}
