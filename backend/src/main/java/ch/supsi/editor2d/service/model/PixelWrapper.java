package ch.supsi.editor2d.service.model;


import ch.supsi.editor2d.utils.annotations.ToDiscuss;
import ch.supsi.editor2d.utils.exceptions.InvalidColorValueException;

/**
 * PixelWrapper is a class representing the RGB colors using float values ranging from 0 to 1
 * Check out: https://www.tug.org/pracjourn/2007-4/walden/color.pdf
 */
public final class PixelWrapper
{
    private float red;
    private float green;
    private float blue;

    // Must force values from 0 to 1 --> [0.1]

    @ToDiscuss(author ="Dominik Panzarella",date="07/10/2024", description = "If the values are invalid, we wanna throw an exception or force the bound values?")
    public PixelWrapper(final float red, final float green, final float blue){
        setRedColor(red);
        setGreenColor(green);
        setBlueColor(blue);
    }

    public void setRedColor(final float red){
        if(red<0.0 || red>1.0f)
            throw new InvalidColorValueException();
        this.red = red;
    }

    public void setGreenColor(final float green){
        if(green<0.0 || green>1.0f)
            throw new InvalidColorValueException();
        this.green = green;
    }

    public void setBlueColor(final float blue){
        if(blue<0.0 || blue>1.0f)
            throw new InvalidColorValueException();
        this.blue = blue;
    }


    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float[] getRGB(){
        return new float[]{red, green, blue};
    }

    @Override
    public int hashCode(){
        return Float.hashCode(red) + Float.hashCode(green) + Float.hashCode(blue);
    }

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof PixelWrapper))   return false;
        if(o == this)   return true;
        PixelWrapper objectCasted = (PixelWrapper)o;
        return (
                Float.compare(red, objectCasted.red)==0 &&
                Float.compare(green, objectCasted.green)==0 &&
                Float.compare(blue, objectCasted.blue)==0
        );
    }




}
