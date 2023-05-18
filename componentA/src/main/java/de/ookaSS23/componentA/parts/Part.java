package de.ookaSS23.componentA.parts;

import java.util.Objects;
import java.util.UUID;


public abstract class Part {
    public static UUID uuid;
    public Part(){
        if(Objects.isNull(uuid)) uuid = UUID.randomUUID();
    }

    public void whoAmI(){
        System.out.println("I am part"+ getNumber() +" of componentA with uuid "+uuid);
    }
    abstract public String getNumber();
}
