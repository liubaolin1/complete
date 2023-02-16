package com.example.messagingstompwebsocket.socket;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅列表缓存
 *      dyList保存 已经订阅的url和 sessionId会话号
 *      url /user/002050/message 中 002050是id号
 *
 * 可以遍历订阅列表 给在状态的用户发送消息
 *
 */
@Component
public class WebSocketCache {
    private static List<SessionUrlVo> dyList = new ArrayList<>();

    public static void add(String sessionId,String url){
        if(StringUtils.isEmpty(sessionId) || "null".equals(sessionId)){
            return;
        }
        long count = dyList.stream().filter(s -> s.getUrl().equals(url)).count();
        if(count>0){
            return;
        }
        SessionUrlVo build = SessionUrlVo
                .builder()
                .sessionId(sessionId)
                .url(url)
                .build();
        dyList.add(build);
    }

    public static void remove(String sessionId){
        dyList.stream().findFirst().map(vo -> {
            if (vo.getSessionId().equals(sessionId)){
                dyList.remove(vo);
            }
            return vo;
        });
    }

    public static List<SessionUrlVo> getDyList(){
        return dyList;
    }

}
