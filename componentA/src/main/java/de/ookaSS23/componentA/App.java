package de.ookaSS23.componentA;

import de.ookaSS23.componentA.annotation.Start;
import de.ookaSS23.componentA.annotation.Stop;
import de.ookaSS23.componentA.parts.One;
import de.ookaSS23.componentA.parts.Two;

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
        System.out.println("Stopping one and two of ComponentA with ID: "+ one.uuid);
    }
}
