package com.zbfan.spring_order.utils;

import com.zbfan.spring_order.VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO res = new ResultVO();
        res.setCode(0);
        res.setMsg("success");
        res.setData(object);
        return res;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO res = new ResultVO();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }

    public static ResultVO error() {
        return error(1, "error");
    }
}
