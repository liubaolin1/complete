package com.example.messagingstompwebsocket.socket;

import lombok.Builder;
import lombok.Data;

/**
 * 保存 会话id 和订阅的url
 */
@Data
@Builder
public class SessionUrlVo {
    private String url;
    private String sessionId;
}
