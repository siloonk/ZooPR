package me.sildev.zoopr.Pets;

import me.sildev.zoopr.utils.coloredString;

public enum PetTier {
    COMMON {
        @Override
        public String toString() {
            return coloredString.color("&aCOMMON");
        }

    },
    UNCOMMON{
        @Override
        public String toString() {
            return coloredString.color("&bUNCOMMON");
        }
    },
    RARE{
        @Override
        public String toString() {
            return coloredString.color("&dRARE");
        }
    },
    EPIC{
        @Override
        public String toString() {
            return coloredString.color("&5EPIC");
        }

    },
    LEGENDARY{
        @Override
        public String toString() {
            return coloredString.color("&6LEGENDARY");
        }
    };

    public String getColor() {
        switch (this) {
            case COMMON: return "&a";
            case UNCOMMON: return "&b";
            case RARE: return "&d";
            case EPIC: return "&5";
            case LEGENDARY: return "&6";
        }
        return null;
    }

    /**
     *
     * @return get the multiplier at level 1 of the pet! Will increase by 0.01 every level!
     */
    public double getMultiplier() {
        switch (this) {
            case COMMON: return 1;
            case UNCOMMON: return 1.25;
            case RARE: return 1.5;
            case EPIC: return 1.75;
            case LEGENDARY: return 2;
        }
        return 0d;
    }

}
