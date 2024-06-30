package one.empty3.library1.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControlledInstructions extends Instruction {
    public String controlExpression;

    public ControlledInstructions(String controlExpression) {
        super(null);
        this.controlExpression = controlExpression;
    }

    public String getControlExpression() {
        return controlExpression;
    }

    public void setControlExpression(String controlExpression) {
        this.controlExpression = controlExpression;
    }

    public List<InstructionBlock> getInstructionsList() {
        return instructionList;
    }

    public void setInstructionsList(ArrayList<InstructionBlock> instructions) {
        this.instructionList = instructions;
    }

    public static class If extends ControlledInstructions {
        public InstructionBlock instructionsIf = new InstructionBlock();
        public InstructionBlock instructionsElse = new InstructionBlock();

        public If(String controlExpression) {
            super(controlExpression);
        }

        @Override
        public String toLangStringJava(boolean debug) {
            return super.toLangStringJava(debug);
        }
    }

    public static class For extends ControlledInstructions {
        public boolean forEachType = false;
        private Instruction loopInstruction = new Instruction();
        private Instruction firstForInstruction = new Instruction();

        public For(String controlExpression) {
            super(controlExpression);
            //this.firstForInstruction.add(initInstruction);
            //this.loopInstruction.add(loopInstruction);
            forEachType = false;
        }

        /***
         * For (Type variableName : expression)
         *     ==================  ===========
         *     loopInstruction     controlExpression
         * @param firstForInstruction
         * @param controlExpression
         */
        public For(Instruction firstForInstruction, String controlExpression) {
            super(controlExpression);
            this.firstForInstruction = firstForInstruction;
            this.controlExpression = controlExpression;
            forEachType = true;
        }

        public boolean isForEachType() {
            return forEachType;
        }

        public void setForEachType(boolean forEachType) {
            this.forEachType = forEachType;
        }

        public Instruction getLoopInstruction() {
            return loopInstruction == null ? new Instruction() : loopInstruction;
        }

        public void setLoopInstruction(Instruction loopInstruction) {
            this.loopInstruction = loopInstruction;
        }

        public Instruction getFirstForInstruction() {
            return firstForInstruction == null ? new Instruction() : firstForInstruction;
        }

        public void setFirstForInstruction(Instruction firstForInstruction) {
            this.firstForInstruction = firstForInstruction;
        }

        @Override
        public String toLangStringJava(boolean debug) {
            return super.toLangStringJava(debug);
        }
    }

    public static class While extends ControlledInstructions {
        public While(String controlExpression) {
            super(controlExpression);
        }

        @Override
        public String toLangStringJava(boolean debug) {
            return super.toLangStringJava(debug);
        }
    }

    public static class DoWhile extends ControlledInstructions {
        public DoWhile(String controlExpression) {
            super(controlExpression);
        }

        @Override
        public String toLangStringJava(boolean debug) {
            return super.toLangStringJava(debug);
        }

    }

    @Override
    public String toLangStringJava(boolean debug) {
        return super.toLangStringJava(debug);
    }

}
