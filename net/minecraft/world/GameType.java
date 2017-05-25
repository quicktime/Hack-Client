package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;

public enum GameType
{
    NOT_SET(-1, "", ""),
    SURVIVAL(0, "survival", "s"),
    CREATIVE(1, "creative", "c"),
    ADVENTURE(2, "adventure", "a"),
    SPECTATOR(3, "spectator", "sp");

    int id;
    String name;
    String shortName;

    private GameType(int idIn, String nameIn, String shortNameIn)
    {
        this.id = idIn;
        this.name = nameIn;
        this.shortName = shortNameIn;
    }

    /**
     * Returns the ID of this game category
     */
    public int getID()
    {
        return this.id;
    }

    /**
     * Returns the name of this game category
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Configures the player capabilities based on the game category
     */
    public void configurePlayerCapabilities(PlayerCapabilities capabilities)
    {
        if (this == CREATIVE)
        {
            capabilities.allowFlying = true;
            capabilities.isCreativeMode = true;
            capabilities.disableDamage = true;
        }
        else if (this == SPECTATOR)
        {
            capabilities.allowFlying = true;
            capabilities.isCreativeMode = false;
            capabilities.disableDamage = true;
            capabilities.isFlying = true;
        }
        else
        {
            capabilities.allowFlying = false;
            capabilities.isCreativeMode = false;
            capabilities.disableDamage = false;
            capabilities.isFlying = false;
        }

        capabilities.allowEdit = !this.isAdventure();
    }

    /**
     * Returns true if this is the ADVENTURE game category
     */
    public boolean isAdventure()
    {
        return this == ADVENTURE || this == SPECTATOR;
    }

    /**
     * Returns true if this is the CREATIVE game category
     */
    public boolean isCreative()
    {
        return this == CREATIVE;
    }

    /**
     * Returns true if this is the SURVIVAL or ADVENTURE game category
     */
    public boolean isSurvivalOrAdventure()
    {
        return this == SURVIVAL || this == ADVENTURE;
    }

    /**
     * Gets the game category by it's ID. Will be survival if none was found.
     */
    public static GameType getByID(int idIn)
    {
        return parseGameTypeWithDefault(idIn, SURVIVAL);
    }

    public static GameType parseGameTypeWithDefault(int targetId, GameType fallback)
    {
        for (GameType gametype : values())
        {
            if (gametype.id == targetId)
            {
                return gametype;
            }
        }

        return fallback;
    }

    /**
     * Gets the game category registered with the specified name. If no matches were found, survival will be returned.
     */
    public static GameType getByName(String gamemodeName)
    {
        return parseGameTypeWithDefault(gamemodeName, SURVIVAL);
    }

    public static GameType parseGameTypeWithDefault(String targetName, GameType fallback)
    {
        for (GameType gametype : values())
        {
            if (gametype.name.equals(targetName) || gametype.shortName.equals(targetName))
            {
                return gametype;
            }
        }

        return fallback;
    }
}
