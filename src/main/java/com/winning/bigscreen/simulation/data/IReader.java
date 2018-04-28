package com.winning.bigscreen.simulation.data;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author Lemod
 * @Version 2017/10/23
 */
public interface IReader {

    JSONObject getSimulationData(String mapping, String type);

}
