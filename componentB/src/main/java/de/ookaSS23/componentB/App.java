package de.ookaSS23.componentB;

import de.ookaSS23.componentB.annotation.Start;
import de.ookaSS23.componentB.annotation.Stop;
import de.ookaSS23.componentB.parts.One;
import de.ookaSS23.componentB.parts.Two;

import java.util.Objects;

public class App {
    One one;
    Two two;
    @Start
    public void init(){
        if(Objects.isNull(one)) one = new One();
        if(Objects.isNull(two)) two = new Two();
        one.whoAmI();
        two.whoAmI();
    }

    @Stop
    public void close(){
        System.out.println("Stopping one and two of ComponentB with ID: "+ one.uuid);
    }
}
