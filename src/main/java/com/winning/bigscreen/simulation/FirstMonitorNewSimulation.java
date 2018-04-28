package com.winning.bigscreen.simulation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winning.bigscreen.simulation.Utils.JSONUtils;
import com.winning.bigscreen.simulation.data.IReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Lemod
 * @Version 2018/2/5
 */
@Controller
@RequestMapping(value = "/api/bigscreen/monitor")
public class FirstMonitorNewSimulation {

    private static final String PATH_MAPPING = "monitor/";

    @Autowired
    private IReader reader;

    @RequestMapping(value = "commonHandle", method = RequestMethod.POST)
    @ResponseBody
    public String CommonHandle(@RequestParam String action,
                               @RequestParam(required = false) String msg) {
        Map<String, Object> params = new HashMap<>();
        params = JSONUtils.formatJsonString(msg, params);

        String status = null;
        if (params != null) {
            status = (String) params.getOrDefault("status", "");
        }
        JSONObject result;
        if (StringUtils.hasText(status) && status.equals("Error")) {
            result = reader.getSimulationData(PATH_MAPPING, "getErrorServicesByIds");
        } else if (action.equals("getTpmOfServices")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(System.currentTimeMillis()));
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            result = reader.getSimulationData(PATH_MAPPING, action);
            JSONArray dataArray = result.getJSONArray("datas");
            for (int size = dataArray.size(); size > hour; size--) {
                dataArray.remove(size - 1);
            }
        }else {
            result = reader.getSimulationData(PATH_MAPPING, action);
        }

        return JSON.toJSONString(result);
    }
}
