package com.mist.cloudtestingplatform.protocol.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mist.cloudtestingplatform.protocol.model.*;
import com.mist.cloudtestingplatform.protocol.util.JsonUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Prophet on 2018/1/8.
 */
public class SerializationTest {

    XmlMapper xmlMapper;

    ObjectMapper objectMapper;

    public void initXmlMapper() {
        xmlMapper = new XmlMapper();
    }

    public void initObjectMapper() {
        objectMapper = new ObjectMapper();
    }

    public String getXmlString(Payload payload) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(payload);
    }

    public long[] serializeTest(int round, Payload payload) throws JsonProcessingException {
        if (xmlMapper == null) {
            initXmlMapper();
        }

        long[] result = new long[]{0, 0};

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < round; i++) {
            JsonUtil.payLoadToJson(payload);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("json serialize time: " + (endTime - startTime) );
        result[0] = endTime - startTime;

        startTime = System.currentTimeMillis();

        for (int i = 0; i < round; i++) {
            getXmlString(payload);
        }

        endTime = System.currentTimeMillis();

        System.out.println("xml serialize time: " + (endTime - startTime) );
        result[1] = endTime - startTime;

        return result;
    }

    public long[] deserializeTest(int round, String jsonStr, String xmlStr, Class clazz) throws IOException {
        if (objectMapper == null) {
            initObjectMapper();
        }
        if (xmlMapper == null) {
            initXmlMapper();
        }

        long[] result = new long[]{0, 0};

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < round; i++) {
            deserializeJsonPayload(jsonStr, clazz);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("json deserialize time: " + (endTime - startTime) );
        result[0] = endTime - startTime;

        startTime = System.currentTimeMillis();

        for (int i = 0; i < round; i++) {
            deserializeXmlPayload(xmlStr, clazz);
        }

        endTime = System.currentTimeMillis();

        System.out.println("xml deserialize time: " + (endTime - startTime) );
        result[1] = endTime - startTime;

        return result;
    }

    @Test
    public void jsonVersusXml() throws IOException {

        for (int i = 0; i < 10; i++) {

            System.out.println("start serialize test");

            System.out.println("run " + i);
            int round = 10000;

            System.out.println("analogSampleData");
            serializeTest(round, buildAnalogSampleDataPayload());

            System.out.println("controlData");
            serializeTest(round, buildControlDataPayload());

            System.out.println("instructionData");
            serializeTest(round, buildInstructionDataPayload());

            System.out.println("end serialize test");

        }

        for (int i = 0; i < 10; i++) {

            System.out.println("start deserialize test");
            System.out.println("run " + i);
            int round = 10000;

            System.out.println("analogSampleData");
            String jsonStr = JsonUtil.payLoadToJson(buildAnalogSampleDataPayload());
            String xmlStr = getXmlString(buildAnalogSampleDataPayload());
            deserializeTest(round, jsonStr, xmlStr, PayloadAnalogSampleData.class);

            System.out.println("controlData");
            jsonStr = JsonUtil.payLoadToJson(buildControlDataPayload());
            xmlStr = getXmlString(buildControlDataPayload());
            deserializeTest(round, jsonStr, xmlStr, PayloadControlData.class);

            System.out.println("controlData");
            jsonStr = JsonUtil.payLoadToJson(buildInstructionDataPayload());
            xmlStr = getXmlString(buildInstructionDataPayload());
            deserializeTest(round, jsonStr, xmlStr, PayloadInstructionData.class);

            System.out.println("end deserialize test");
        }

    }

    @Test
    public void jsonVersusXmlStatistic() throws IOException {
        performanceTest(10000);

    }


    public void performanceTest(int round) throws IOException {

        int count = 10;

        long jsonSeControlAvg = 0;
        long xmlSeControlAvg = 0;
        long jsonSeAnalogAvg = 0;
        long xmlSeAnalogAvg = 0;
        long jsonSeInsAvg = 0;
        long xmlSeInsAvg = 0;

        long jsonDeControlAvg = 0;
        long xmlDeControlAvg = 0;
        long jsonDeAnalogAvg = 0;
        long xmlDeAnalogAvg = 0;
        long jsonDeInsAvg = 0;
        long xmlDeInsAvg = 0;

        long jsonSerializeAvg = 0;
        long xmlSerializeAvg = 0;
        long jsonDeserializeAvg = 0;
        long xmlDeserializeAbg = 0;

        long res[];
        for (int i = 0; i < count; i++) {

            System.out.println("start serialize test");

            System.out.println("run " + i);

            System.out.println("analogSampleData");
            res= serializeTest(round, buildAnalogSampleDataPayload());
            jsonSeAnalogAvg += res[0];
            xmlSeAnalogAvg += res[1];

            System.out.println("controlData");
            res = serializeTest(round, buildControlDataPayload());
            jsonSeControlAvg += res[0];
            xmlSeControlAvg += res[1];

            System.out.println("instructionData");
            res = serializeTest(round, buildInstructionDataPayload());
            jsonSeInsAvg += res[0];
            xmlSeInsAvg += res[1];

            System.out.println("end serialize test");

        }

        jsonSeAnalogAvg /= count;
        xmlSeAnalogAvg /= count;
        jsonSeControlAvg /= count;
        xmlSeControlAvg /= count;
        jsonSeInsAvg /= count;
        xmlSeInsAvg /= count;

        for (int i = 0; i < count; i++) {

            System.out.println("start deserialize test");
            System.out.println("run " + i);

            System.out.println("analogSampleData");
            String jsonStr = JsonUtil.payLoadToJson(buildAnalogSampleDataPayload());
            String xmlStr = getXmlString(buildAnalogSampleDataPayload());
            res = deserializeTest(round, jsonStr, xmlStr, PayloadAnalogSampleData.class);
            jsonDeAnalogAvg += res[0];
            xmlDeAnalogAvg += res[1];

            System.out.println("controlData");
            jsonStr = JsonUtil.payLoadToJson(buildControlDataPayload());
            xmlStr = getXmlString(buildControlDataPayload());
            res = deserializeTest(round, jsonStr, xmlStr, PayloadControlData.class);
            jsonDeControlAvg += res[0];
            xmlDeControlAvg += res[1];

            System.out.println("controlData");
            jsonStr = JsonUtil.payLoadToJson(buildInstructionDataPayload());
            xmlStr = getXmlString(buildInstructionDataPayload());
            res = deserializeTest(round, jsonStr, xmlStr, PayloadInstructionData.class);
            jsonDeInsAvg += res[0];
            xmlDeInsAvg += res[1];

            System.out.println("end deserialize test");
        }

        jsonDeAnalogAvg /= count;
        xmlDeAnalogAvg /= count;
        jsonDeControlAvg /= count;
        xmlDeControlAvg /= count;
        jsonDeInsAvg /= count;
        xmlDeInsAvg /= count;

        jsonSerializeAvg = (jsonSeAnalogAvg + jsonSeControlAvg + jsonSeInsAvg) / 3;
        xmlSerializeAvg = (xmlSeAnalogAvg + xmlSeControlAvg + xmlSeInsAvg) / 3;
        jsonDeserializeAvg = (jsonDeAnalogAvg + jsonDeControlAvg + jsonDeInsAvg) / 3;
        xmlDeserializeAbg = (xmlDeAnalogAvg + xmlDeControlAvg + xmlDeInsAvg) / 3;

        System.out.println("");
        System.out.println("jsonSeAnalogAvg: " + jsonSeAnalogAvg);
        System.out.println("xmlSeAnalogAvg: " + xmlSeAnalogAvg);
        System.out.println("jsonSeControlAvg: " + jsonSeControlAvg);
        System.out.println("xmlSeControlAvg: " + xmlSeControlAvg );
        System.out.println("jsonSeInsAvg: " + jsonSeInsAvg);
        System.out.println("xmlSeInsAvg: " + xmlSeInsAvg);
        System.out.println("");
        System.out.println("jsonDeAnalogAvg: " + jsonDeAnalogAvg);
        System.out.println("xmlDeAnalogAvg: " + xmlDeAnalogAvg);
        System.out.println("jsonDeControlAvg: " + jsonDeControlAvg);
        System.out.println("xmlDeControlAvg: " + xmlDeControlAvg);
        System.out.println("jsonDeInsAvg: " + jsonDeInsAvg);
        System.out.println("xmlDeInsAvg: " + xmlDeInsAvg);
        System.out.println("");
        System.out.println("jsonSerializeAvg: " + jsonSerializeAvg);
        System.out.println("xmlSerializeAvg: " + xmlSerializeAvg);
        System.out.println("jsonDeserializeAvg: " + jsonDeserializeAvg);
        System.out.println("xmlDeserializeAbg: " + xmlDeserializeAbg);
    }



    public Payload buildAnalogSampleDataPayload() {
        Option option = new Option();
        option.setUsername("username");
        option.setPassword("password");
        option.setUrl("www.prophet-xu.com");
        option.setWill("will");

        AnalogSampleData analogSampleData = new AnalogSampleData();
        analogSampleData.setPort("port1");
        analogSampleData.setMethod("method1");
        analogSampleData.setValue(new double[]{1.2, 2.2});

        Payload payload = PayloadFactory.createPayload(option, analogSampleData);
        payload.setCode(new Random().nextInt());
        return payload;
    }

    public Payload buildControlDataPayload() {
        Option option = new Option();
        option.setUsername("username");
        option.setPassword("password");
        option.setUrl("www.prophet-xu.com");
        option.setWill("will");

        ControlData controlData = new ControlData();
        controlData.setCommand("test");
        controlData.setCommandCount(10);
        controlData.setCommands(new String[]{"test","test"});
        controlData.setCommandMap(new HashMap<String, String>());

        Payload payload = PayloadFactory.createPayload(option, controlData);
        payload.setCode(new Random().nextInt());
        return payload;
    }

    public Payload buildInstructionDataPayload() {
        Option option = new Option();
        option.setUsername("username");
        option.setPassword("password");
        option.setUrl("www.prophet-xu.com");
        option.setWill("will");

        Instruction instruction = new Instruction();
        instruction.setInterval(100);
        instruction.setMaxValue(10.0);
        instruction.setMinValue(-10.0);
        instruction.setInstruction("test");
        InstructionData instructionData = new InstructionData();
        instructionData.setInstruction(instruction);
        instructionData.setInstructionCount(10);
        Map<String ,Instruction> map = new HashMap<>();
        map.put("instruction", instruction);
        instructionData.setInstructionMap(map);
        instructionData.setInstructions(new Instruction[]{instruction});

        Payload payload = PayloadFactory.createPayload(option, instructionData);
        payload.setCode(new Random().nextInt());
        return payload;
    }

    public Object deserializeJsonPayload(String jsonStr, Class clazz) throws IOException {
        return objectMapper.readValue(jsonStr, clazz);
    }


    public Object deserializeXmlPayload(String xmlStr, Class clazz) throws IOException {
        return xmlMapper.readValue(xmlStr, clazz);
    }


}
