package com.easy.nettyServer;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public class ClientMap {
  public  static  final Map<String , Channel>  clienMap = new HashMap<>();

  public static  void addClien(String clienId , Channel channel){
    clienMap.put(clienId,channel);
    log.info("用户  : {} 上线", clienId);
  }

  public static  Channel getClien(String clienId){
    return  clienMap.get(clienId);
  }

  //根据map的value获取map的key
  public static String removeClien(Channel value){
    String key="";
    for (Map.Entry<String, Channel> entry : clienMap.entrySet()) {
      if(value.equals(entry.getValue())){
        key=entry.getKey();
        clienMap.remove(key);
        log.info("用户  : {} 下线", key);
      }
    }
    return key;
  }
}
