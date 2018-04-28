package com.winning.bigscreen.simulation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winning.bigscreen.simulation.data.IReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

/**
 * @Author Lemod
 * @Version 2017/10/23
 */
@Controller
@RequestMapping(value = "/api/bigscreen/monitor-old")
public class MonitorSimulation {

    private static final String PATH_MAPPING = "monitor-old/";

    @Autowired
    private IReader reader;

    @RequestMapping(value = "commonHandle", method = RequestMethod.POST)
    @ResponseBody
    public String CommonHandle(@RequestParam String action, @RequestParam(required = false) String aid, @RequestParam(required = false) String sid) {
        JSONObject result = reader.getSimulationData(PATH_MAPPING, action);
        if (action.equalsIgnoreCase("getServiceByAid")) {
            dealServiceList(aid, result);
        }
        if (action.equalsIgnoreCase("getFlowBySid")) {
            dealFlow(sid, result);
        }

        return JSON.toJSONString(result);
    }

    private void dealServiceList(String aid, JSONObject result) {
        if (aid.equalsIgnoreCase("HIP0501")) {
            result.remove("consumeService");
        } else {
            JSONArray consumeServiceList = result.getJSONArray("consumeService");
            for (int i = 0; i < new Random().nextInt(10); i++) {
                int max = consumeServiceList.size();
                consumeServiceList.remove(new Random().nextInt(max - 1));
            }
        }
        JSONArray provideServiceList = result.getJSONArray("provideService");
        for (int i = 0; i < new Random().nextInt(10); i++) {
            int max = provideServiceList.size();
            provideServiceList.remove(new Random().nextInt(max - 1));
        }
    }

    private void dealFlow(String sid, JSONObject result) {
        JSONArray consumers = result.getJSONArray("consumer");
        int size = consumers.size();

        for (int i = 0; i < new Random().nextInt(size); i++) {
            consumers.remove(new Random().nextInt(size - 1));
        }
    }
}
