package com.mist.cloudtestingplatform.protocol.model;

import java.util.Map;

/**
 * Created by Prophet on 2018/1/5.
 */
public class InstructionData extends Data {

    private int instructionCount = 1;

    private Instruction instruction;

    private Instruction[] instructions;

    private Map<String, Instruction> instructionMap;

    public int getInstructionCount() {
        return instructionCount;
    }

    public void setInstructionCount(int instructionCount) {
        this.instructionCount = instructionCount;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    public void setInstructions(Instruction[] instructions) {
        this.instructions = instructions;
    }

    public Map<String, Instruction> getInstructionMap() {
        return instructionMap;
    }

    public void setInstructionMap(Map<String, Instruction> instructionMap) {
        this.instructionMap = instructionMap;
    }
}
