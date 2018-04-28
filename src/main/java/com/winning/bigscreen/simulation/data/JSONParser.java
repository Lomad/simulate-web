package com.winning.bigscreen.simulation.data;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import static com.winning.bigscreen.simulation.Utils.FileUtils.getRootPath;
import static com.winning.bigscreen.simulation.Utils.FileUtils.readFile;

/**
 * @Author Lemod
 * @Version 2017/10/23
 */
@Service
public class JSONParser implements IReader {

    private static final Logger logger = LoggerFactory.getLogger(JSONParser.class);

    private static final String MAPPING_PATH = "/simulate-data/";

    private static final String SUFFIX = ".json";

    private String rootPath;

    @PostConstruct
    private void init() {
        rootPath = getRootPath();
    }

    @Override
    public JSONObject getSimulationData(String mapping, String type) {
        String path = rootPath + MAPPING_PATH + mapping + type + SUFFIX;
        logger.info(path);

        try {
            String jsonString = readFile(path);

            if (StringUtils.hasText(jsonString)) {
                return JSONObject.parseObject(jsonString);
            }
        } catch (Exception e) {
            logger.error("读取模拟数据失败..", e);
        }
        return null;
    }
}
