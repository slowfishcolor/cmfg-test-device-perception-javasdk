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

    public void serializeTest(int round, Payload payload) throws JsonProcessingException {
        if (xmlMapper == null) {
            initXmlMapper();
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < round; i++) {
            JsonUtil.payLoadToJson(payload);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("json serialize time: " + (endTime - startTime) );

        startTime = System.currentTimeMillis();

        for (int i = 0; i < round; i++) {
            getXmlString(payload);
        }

        endTime = System.currentTimeMillis();

        System.out.println("xml serialize time: " + (endTime - startTime) );
    }

    public void deserializeTest(int round, String jsonStr, String xmlStr, Class clazz) throws IOException {
        if (objectMapper == null) {
            initObjectMapper();
        }
        if (xmlMapper == null) {
            initXmlMapper();
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < round; i++) {
            deserializeJsonPayload(jsonStr, clazz);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("json deserialize time: " + (endTime - startTime) );

        startTime = System.currentTimeMillis();

        for (int i = 0; i < round; i++) {
            deserializeXmlPayload(xmlStr, clazz);
        }

        endTime = System.currentTimeMillis();

        System.out.println("xml deserialize time: " + (endTime - startTime) );


    }

    @Test
    public void jsonVersusXml() throws IOException {

        for (int i = 0; i < 10; i++) {

            System.out.println("start serialize test");

            System.out.println("run " + i);
            int round = 100000;

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
            int round = 100000;

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
