import de.ookaSS23.lzu.command.StartComponentOperation;
import de.ookaSS23.lzu.command.StopComponentOperation;
import de.ookaSS23.lzu.controller.ComponentAssembler;
import de.ookaSS23.lzu.controller.ComponentWrapper;
import org.junit.jupiter.api.Test;

public class AssemblerTests {

    @Test
    void testingStates() throws InterruptedException {
        ComponentAssembler componentAssembler = ComponentAssembler.getInstance();
        ComponentWrapper componentWrapper = componentAssembler.loadComponentFromJar(ComponentAssembler.Component.COMPONENT_A);
        Thread.sleep(500);
        org.junit.jupiter.api.Assertions.assertEquals(ComponentWrapper.State.DEPLOYED, componentWrapper.getState());

        StartComponentOperation startComponentOperation = new StartComponentOperation(componentWrapper);
        componentAssembler.addComponentOperation(startComponentOperation);
        componentAssembler.executeComponentOperations();
        Thread.sleep(500);
        org.junit.jupiter.api.Assertions.assertEquals(ComponentWrapper.State.RUNNING, componentWrapper.getState());

        StopComponentOperation stopComponentOperation = new StopComponentOperation(componentWrapper);
        componentAssembler.addComponentOperation(stopComponentOperation);
        componentAssembler.executeComponentOperations();
        Thread.sleep(500);
        org.junit.jupiter.api.Assertions.assertEquals(ComponentWrapper.State.STOPPED, componentWrapper.getState());

    }

    @Test
    void isolationTest() {
        ComponentAssembler componentAssembler = ComponentAssembler.getInstance();
        ComponentWrapper componentWrapper1 = componentAssembler.loadComponentFromJar(ComponentAssembler.Component.COMPONENT_B);
        ComponentWrapper componentWrapper2 = componentAssembler.loadComponentFromJar(ComponentAssembler.Component.COMPONENT_B);
        org.junit.jupiter.api.Assertions.assertTrue(componentWrapper1.getInvokeClass() != componentWrapper2.getInvokeClass());
    }
}
