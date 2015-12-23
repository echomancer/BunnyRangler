package models;

import info.dyndns.jasonperkins.bunnyrangler.R;

/**
 * Created by Jason on 12/22/2015.
 */
public class Bunny {
    public Long _id; // for cupboard
    public String name; // bunny name
    public Integer cuteValue; // bunny cuteness
    //public String furColor; // new String to be added for db version 2



    public Enum<cutenessType> cutenessTypeEnum;

    public Bunny() {
        this.name = String.valueOf(R.string.default_bunny_name); // Converting default name to use value from strings
        this.cuteValue = 0;
        this.cutenessTypeEnum = cutenessType.UGLY;
    }
    public Bunny(String name) {
        this.name = name;
        this.cuteValue = (int) (Math.random() * 100);

        if (cuteValue < 44) {
            cutenessTypeEnum = cutenessType.UGLY;
        }
        else  if (cuteValue < 66) {
            cutenessTypeEnum = cutenessType.CUTE;
        }
        else if (cuteValue < 88) {
            cutenessTypeEnum = cutenessType.VERYCUTE;
        }
        else {
            cutenessTypeEnum = cutenessType.SOCUTEICOULDDIE;
        }
        this.cuteValue = cutenessTypeEnum.ordinal(); // Let's actually set the cute value based on the enum - JTP 12/22/2015 2:17 pm
    }

    public static enum cutenessType {
        UGLY, CUTE, VERYCUTE, SOCUTEICOULDDIE;
        // Adding length so we know when we've gone too far - JTP 12/22/2015 1:24 pm
        public static int length = values().length-1;
    }

    // Function to make the bunny cuter - JTP 12/22/2015 1:54 pm
    public void makeCuter() {
        cuteValue++;
        setEnumByValue();
    }

    // Function to make the bunny uglier - JTP 12/22/2015 1:54 pm
    public void makeUglier() {
        cuteValue--;
        setEnumByValue();
    }

    // Function to set the enum based on the cutenessValue
    public void setEnumByValue(){
        if(cuteValue > cutenessType.length){    // Keep us from going too high
            cuteValue = cutenessType.length;
        }
        if(cuteValue < 0){                      // Keep us from going too low
            cuteValue = 0;
        }
        cutenessTypeEnum = cutenessType.values()[cuteValue];
    }

    public Enum<cutenessType> getCutenessTypeEnum() {
        return cutenessTypeEnum;
    }

    public void setCutenessTypeEnum(Enum<cutenessType> cutenessTypeEnum) {
        this.cutenessTypeEnum = cutenessTypeEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCuteValue() {
        return cuteValue;
    }

    public void setCuteValue(Integer cuteValue) {
        this.cuteValue = cuteValue;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
