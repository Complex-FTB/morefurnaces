package cubex2.mods.morefurnaces;

import cubex2.mods.morefurnaces.blocks.BlockMoreFurnaces;
import cubex2.mods.morefurnaces.tileentity.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.oredict.ShapedOreRecipe;

public enum FurnaceType implements IStringSerializable
{
    IRON(2, 2, 2, 1, 150, 1.25f, 56, 17, 38, 17, 56, 53, 38, 53, 116, 35, 138, 39, 8, 84, "Iron Furnace", TileEntityIronFurnace.class, "III", "IPI", "III"),
    GOLD(4, 5, 4, 1, 80, 2.0f, 62, 17, 8, 17, 62, 53, 8, 53, 116, 35, 138, 39, 28, 84, "Gold Furnace", TileEntityGoldFurnace.class, "GGG", "GPG", "GGG"),
    DIAMOND(7, 9, 7, 1, 40, 4.0f, 62, 35, 8, 17, 62, 71, 8, 71, 116, 53, 138, 39, 28, 120, "Diamond Furnace", TileEntityDiamondFurnace.class, "DDD", "DPD", "DDD"),
    OBSIDIAN(2, 2, 2, 2, 150, 2.0f, new int[]{56, 56}, new int[]{17, 43}, new int[]{38, 38}, new int[]{17, 43}, 56, 83, 38, 83, new int[]{116, 116}, new int[]{18, 44}, new int[]{138, 138}, new int[]{22, 48}, 8, 114, "Obsidian Furnace", TileEntityObsidianFurnace.class, "OOO", "O1O", "OOO"),
    NETHERRACK(1, 1, 0, 1, 1800, 1.0f, 56, 17, -1, -1, -1, -1, -1, -1, 116, 35, -1, -1, 8, 84, "Netherrack Furnace", TileEntityNetherrackFurnace.class, "NNN", "NFN", "NNN");

    final int inputSlots;
    final int outputSlots;
    public final int fuelSlots;
    public final int parallelSmelting;

    public final int[] inputSlotIds;
    public final int[] outputSlotIds;
    public final int[] fuelSlotIds;

    public final int speed;
    public final float consumptionRate;
    public final int[] mainInputX;
    public final int[] mainInputY;
    public final int[] inputX;
    public final int[] inputY;
    public final int mainFuelX;
    public final int mainFuelY;
    public final int fuelX;
    public final int fuelY;
    public final int[] mainOutputX;
    public final int[] mainOutputY;
    public final int[] outputX;
    public final int[] outputY;
    public final int inventoryX;
    public final int inventoryY;
    public final String friendlyName;
    public final Class<? extends TileEntityIronFurnace> clazz;
    private final String[] recipe;

    private FurnaceType(int inputSlots, int outputSlots, int fuelSlots,
                        int parallelSmelting, int speed, float consumptionRate, int[] mainInputX,
                        int[] mainInputY, int[] inputX, int[] inputY, int mainFuelX,
                        int mainFuelY, int fuelX, int fuelY, int[] mainOutputX,
                        int[] mainOutputY, int[] outputX, int[] outputY, int inventoryX,
                        int inventoryY, String friendlyName,
                        Class<? extends TileEntityIronFurnace> clazz, String... recipe)
    {
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.fuelSlots = fuelSlots;
        this.parallelSmelting = parallelSmelting;

        inputSlotIds = new int[inputSlots * parallelSmelting];
        outputSlotIds = new int[outputSlots * parallelSmelting];
        fuelSlotIds = new int[fuelSlots];

        for (int i = 0; i < inputSlotIds.length; i++)
        {
            inputSlotIds[i] = i;
        }
        for (int i = 0; i < fuelSlotIds.length; i++)
        {
            fuelSlotIds[i] = i + inputSlotIds.length;
        }
        for (int i = 0; i < outputSlotIds.length; i++)
        {
            outputSlotIds[i] = i + inputSlotIds.length + fuelSlotIds.length;
        }

        this.speed = speed;
        this.consumptionRate = consumptionRate;
        this.mainInputX = mainInputX;
        this.mainInputY = mainInputY;
        this.inputX = inputX;
        this.inputY = inputY;
        this.mainFuelX = mainFuelX;
        this.mainFuelY = mainFuelY;
        this.fuelX = fuelX;
        this.fuelY = fuelY;
        this.mainOutputX = mainOutputX;
        this.mainOutputY = mainOutputY;
        this.outputX = outputX;
        this.outputY = outputY;
        this.inventoryX = inventoryX;
        this.inventoryY = inventoryY;
        this.friendlyName = friendlyName;
        this.clazz = clazz;
        this.recipe = recipe;
    }

    private FurnaceType(int inputSlots, int outputSlots, int fuelSlots,
                        int parallelSmelting, int speed, float consumptionRate, int mainInputX, int mainInputY,
                        int inputX, int inputY, int mainFuelX, int mainFuelY, int fuelX,
                        int fuelY, int mainOutputX, int mainOutputY, int outputX,
                        int outputY, int inventoryX, int inventoryY, String friendlyName,
                        Class<? extends TileEntityIronFurnace> clazz, String... recipe)
    {
        this(inputSlots, outputSlots, fuelSlots, parallelSmelting, speed, consumptionRate,
             new int[]{mainInputX}, new int[]{mainInputY},
             new int[]{inputX}, new int[]{inputY}, mainFuelX,
             mainFuelY, fuelX, fuelY, new int[]{mainOutputX},
             new int[]{mainOutputY}, new int[]{outputX},
             new int[]{outputY}, inventoryX, inventoryY, friendlyName,
             clazz, recipe);
    }


    public static TileEntityIronFurnace makeEntity(int meta)
    {
        try
        {
            TileEntityIronFurnace te = values()[meta].clazz.newInstance();
            return te;
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void generateRecipes(BlockMoreFurnaces blockResult)
    {
        ItemStack previous = new ItemStack(Blocks.furnace);
        for (FurnaceType typ : values())
        {
            ShapedOreRecipe recipe = new ShapedOreRecipe(new ItemStack(
                    blockResult, 1, typ.ordinal()), typ.recipe, 'I',
                                                         Items.iron_ingot, 'G', Items.gold_ingot, 'D', Items.diamond,
                                                         'O', Blocks.obsidian, 'N', Blocks.netherrack, 'F',
                                                         Blocks.furnace, '1', new ItemStack(
                    MoreFurnaces.blockFurnaces, 0), 'P', previous
            );
            CraftingManager.getInstance().getRecipeList().add(recipe);
            previous = new ItemStack(blockResult, 1, typ.ordinal());
        }
    }

    public int getSpeed()
    {
        return speed;
    }

    public boolean isInputSlot(int slotId)
    {
        return arrayContains(inputSlotIds, slotId);
    }

    public boolean isFuelSlot(int slotId)
    {
        return arrayContains(fuelSlotIds, slotId);
    }

    public boolean isOutputSlot(int slotId)
    {
        return arrayContains(outputSlotIds, slotId);
    }

    public static boolean arrayContains(int[] i, int i1)
    {
        for (int i2 : i)
            if (i2 == i1)
                return true;
        return false;
    }

    public int getFirstInputSlot(int id)
    {
        return 0 + id * inputSlots;
    }

    public int getLastInputSlot(int id)
    {
        return getFirstInputSlot(id) + inputSlots - 1;
    }

    public int getFirstFuelSlot()
    {
        return getLastInputSlot(parallelSmelting - 1) + 1;
    }

    public int getLastFuelSlot()
    {
        return getFirstFuelSlot() + fuelSlots - 1;
    }

    public int getFirstOutputSlot(int id)
    {
        return getLastFuelSlot() + 1 + id * outputSlots;
    }

    public int getLastOutputSlot(int id)
    {
        return getFirstOutputSlot(id) + outputSlots - 1;
    }

    public int getNumSlots()
    {
        return parallelSmelting * (inputSlots + outputSlots) + fuelSlots;
    }

    public int getNumInputRows()
    {
        int numSlots = inputSlots;
        if (numSlots <= 4)
            return 1;
        return (numSlots - 1) / 3;
    }

    public int getNumFuelRows()
    {
        return fuelSlots <= 4 ? 1 : (fuelSlots - 1) / 3;
    }

    public int getNumOutputRows()
    {
        int numSlots = outputSlots;
        if (numSlots <= 4)
            return 1;
        return (numSlots - 1) / 3;
    }

    public int getInputSlotsPerRow()
    {
        int numSlots = inputSlots;
        return (numSlots - 1) / getNumInputRows();
    }

    public int getFuelSlotsPerRow()
    {
        return (fuelSlots - 1) / getNumFuelRows();
    }

    public int getOutputSlotsPerRow()
    {
        int numSlots = outputSlots;
        return (numSlots - 1) / getNumOutputRows();
    }

    private static String[] postFixMap = new String[]{"Bottom", "Top", "Side", "FrontOn", "FrontOff"};

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
