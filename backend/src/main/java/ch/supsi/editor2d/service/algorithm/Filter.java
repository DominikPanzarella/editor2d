package ch.supsi.editor2d.service.algorithm;

public abstract class  Filter implements Applicable
{
    private final String name;

    public Filter(final String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
