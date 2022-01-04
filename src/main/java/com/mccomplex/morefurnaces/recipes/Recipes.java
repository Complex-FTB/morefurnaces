package com.mccomplex.morefurnaces.recipes;

import com.mccomplex.morefurnaces.FurnaceType;
import com.mccomplex.morefurnaces.MFLog;
import com.mccomplex.morefurnaces.MoreFurnaces;
import com.mccomplex.morefurnaces.items.Upgrades;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Recipes {

    public static void registerFurnaceRecipes() {

        // Netherrack Furnace
        addShapedRecipe("netherrack_furnace", getFurnace(FurnaceType.NETHERRACK),
                "NNN", "NFN", "NNN",
                'N', Blocks.NETHERRACK,
                'F', Blocks.FURNACE);

        // Iron Furnace
        addShapedRecipe("iron_furnace", getFurnace(FurnaceType.IRON),
                "III", "IFI", "III",
                'I', "ingotIron",
                'F', Blocks.FURNACE);

        // Gold Furnace
        addShapedRecipe("gold_furnace", getFurnace(FurnaceType.GOLD),
                "GGG", "GFG", "GGG",
                'G', "ingotGold",
                'F', getFurnace(FurnaceType.IRON));

        // Diamond Furnace
        addShapedRecipe("diamond_furnace", getFurnace(FurnaceType.DIAMOND),
                "DDD", "DFD", "DDD",
                'D', Items.DIAMOND,
                'F', getFurnace(FurnaceType.GOLD));

        // Obsidian Furnace
        addShapedRecipe("obsidian_furnace", getFurnace(FurnaceType.OBSIDIAN),
                "OOO", "OFO", "OOO",
                'O', Blocks.OBSIDIAN,
                'F', getFurnace(FurnaceType.DIAMOND));
    }

    public static void registerUpgradeRecipes() {

        // Stone to Netherrack Upgrade
        addShapedRecipe("stone_to_netherrack", getUpgrade(Upgrades.STONE_TO_NETHERRACK),
                "NNN", "NSN", "NNN",
                'N', Blocks.NETHERRACK,
                'S', Blocks.STONE);

        // Stone to Iron Upgrade
        addShapedRecipe("stone_to_iron", getUpgrade(Upgrades.STONE_TO_IRON),
                "III", "ISI", "III",
                'I', "ingotIron",
                'S', Blocks.STONE);

        // Iron to Gold Upgrade
        addShapedRecipe("iron_to_gold", getUpgrade(Upgrades.IRON_TO_GOLD),
                "GGG", "GIG", "GGG",
                'G', "ingotGold",
                'I', "nuggetIron");

        // Iron to Obsidian Upgrade
        addShapedRecipe("iron_to_obsidian", getUpgrade(Upgrades.IRON_TO_OBSIDIAN),
                "OOO", "OIO", "OOO",
                'O', Blocks.OBSIDIAN,
                'I', "nuggetIron");

        // Gold to Diamond Upgrade
        addShapedRecipe("gold_to_diamond", getUpgrade(Upgrades.GOLD_TO_DIAMOND),
                "DDD", "DGD", "DDD",
                'D', Items.DIAMOND,
                'G', "nuggetGold");
    }

    private static void addShapedRecipe(String regName, ItemStack result, Object... recipe) {
        if (result.isEmpty()) {
            MFLog.logger.error("Result cannot be an empty ItemStack. Recipe: {}", regName);
            MFLog.logger.error("Stacktrace:", new IllegalArgumentException());
            return;
        }

        IRecipe shapedOreRecipe = new ShapedOreRecipe(null, result.copy(), recipe)
                .setMirrored(false)
                .setRegistryName(regName);
        ForgeRegistries.RECIPES.register(shapedOreRecipe);
    }

    private static ItemStack getUpgrade(Upgrades type) {
        return new ItemStack(MoreFurnaces.ITEM_UPGRADE, 1, type.ordinal());
    }

    private static ItemStack getFurnace(FurnaceType type) {
        return new ItemStack(MoreFurnaces.BLOCK_FURNACE, 1, type.ordinal());
    }
}
