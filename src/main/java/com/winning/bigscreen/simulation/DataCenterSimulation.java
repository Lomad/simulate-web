package com.winning.bigscreen.simulation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winning.bigscreen.simulation.Utils.JSONUtils;
import com.winning.bigscreen.simulation.data.IReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static com.winning.bigscreen.simulation.Utils.DateUtils.GetCurrentHour;

/**
 * @Author Lemod
 * @Version 2017/10/23
 */
@Controller
@RequestMapping(value = "/api/bigscreen/dataCenter/")
public class DataCenterSimulation {

    private static final String PATH_MAPPING = "dataCenter/";

    @Autowired
    private IReader reader;

    @RequestMapping(value = "commonHandle", method = RequestMethod.POST)
    @ResponseBody
    public String CommonHandle(@RequestParam String action,
                               @RequestParam(required = false) String msg) {
        Map<String, Object> params = new HashMap<>();
        params = JSONUtils.formatJsonString(msg, params);

        JSONObject result = reader.getSimulationData(PATH_MAPPING, action);

        if (action.equalsIgnoreCase("getIndexes"))
            result = dealSysIndexes((String) params.get("aid"), result);
        if (action.equalsIgnoreCase("getTrafficMonitor"))
            dealTrafficMonitor(result);

        return result.toString();
    }

    private JSONObject dealSysIndexes(String aid, JSONObject result) {
        JSONObject target = result.getJSONObject("datas").getJSONObject(aid);

        JSONObject jsonObject = new JSONObject();
        JSONObject sysObject = new JSONObject();
        sysObject.put(aid,target);
        jsonObject.put("datas", sysObject);
        jsonObject.put("success", true);

        return jsonObject;
    }

    private void dealTrafficMonitor(JSONObject result) {
        int currentHour = GetCurrentHour();

        JSONArray pointList = result.getJSONArray("pointList");
        for (int i = 23; i > currentHour; i--) {
            pointList.remove(i);
        }
    }
}
