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

import static com.winning.bigscreen.simulation.Utils.DateUtils.GetDayBefore;
import static com.winning.bigscreen.simulation.Utils.DateUtils.GetMonthBefore;

/**
 * @Author Lemod
 * @Version 2017/10/23
 */
@Controller
@RequestMapping(value = "/api/bigscreen/dataStandard/")
public class DataStandardSimulation {

    private static final String PATH_MAPPING = "dataStandard/";

    @Autowired
    private IReader reader;

    @RequestMapping(value = "commonHandle", method = RequestMethod.POST)
    @ResponseBody
    public String CommonHandle(@RequestParam String action,
                               @RequestParam(required = false) String msg) {
        Map<String, Object> params = new HashMap<>();
        params = JSONUtils.formatJsonString(msg, params);

        JSONObject result = reader.getSimulationData(PATH_MAPPING, action);

        if (action.equalsIgnoreCase("mdmCountBeforeMonth"))
            dealMDMCountBeforeMonth(result);
        if (action.equalsIgnoreCase("gxwdSdfx"))
            dealGXWDSdfx(result);

        return result.toString();
    }

    private void dealMDMCountBeforeMonth(JSONObject result) {
        JSONArray array = result.getJSONArray("datas");
        for (int i = 0; i < 12; i++) {
            String date = GetMonthBefore(i);
            JSONObject month = array.getJSONObject(i);
            month.remove("date");
            month.put("date", date);
        }
    }

    private void dealGXWDSdfx(JSONObject result) {
        JSONArray array = result.getJSONArray("datas");

        for (int i = 1; i < 7; i++) {
            String date = GetDayBefore(i);
            JSONObject day = array.getJSONObject(i);
            day.remove("xaxis");
            day.put("xaxis", date);
        }
    }
}
