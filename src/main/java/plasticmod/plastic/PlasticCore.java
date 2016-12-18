package plasticmod.plastic;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import plasticmod.plastic.block.NaphthaBlock;
import plasticmod.plastic.block.NaphthaOil;
import plasticmod.plastic.block.PlasticBlock;
import plasticmod.plastic.block.PlasticBlockCraftbox;
import plasticmod.plastic.block.PlasticChestGuiBlock;
import plasticmod.plastic.block.PlasticDoorBlock;
import plasticmod.plastic.block.PlasticFence;
import plasticmod.plastic.block.PlasticFenceGate;
import plasticmod.plastic.block.PlasticGlass;
import plasticmod.plastic.block.PlasticLightDownBlock;
import plasticmod.plastic.block.PlasticLightUpBlock;
import plasticmod.plastic.block.PlasticPortalBlock;
import plasticmod.plastic.block.PlasticPressurePlate;
import plasticmod.plastic.block.PlasticSheet;
import plasticmod.plastic.block.PlasticStairs;
import plasticmod.plastic.block.PlasticTNT;
import plasticmod.plastic.block.PlasticTrapDoor;
import plasticmod.plastic.block.PlasticWall;
import plasticmod.plastic.block.PlasticWirelessTNT;
import plasticmod.plastic.block.RareNaphthaBlock;
import plasticmod.plastic.block.RarePlasticBlock;
import plasticmod.plastic.block.RarePlasticFenceGate;
import plasticmod.plastic.block.RarePlasticTrapDoor;
import plasticmod.plastic.block.RarePlasticWall;
import plasticmod.plastic.block.StrawberryBlock;
import plasticmod.plastic.block.TomatoBlock;
import plasticmod.plastic.colorblock.*;
import plasticmod.plastic.colorcarpetblock.*;
import plasticmod.plastic.config.PlasticConfig;
import plasticmod.plastic.dispenser.PlasticDispenser;
import plasticmod.plastic.drop.MonsterDrop;
import plasticmod.plastic.entity.EntityPlasticArrow;
import plasticmod.plastic.entity.EntityPlasticBullet;
import plasticmod.plastic.entity.EntityPlasticCreeper;
import plasticmod.plastic.entity.EntityPlasticSkeleton;
import plasticmod.plastic.entity.EntityPlasticTNTPrimed;
import plasticmod.plastic.entity.EntityRarePlasticArrow;
import plasticmod.plastic.gui.GuiHandler;
import plasticmod.plastic.handler.PlasticBucketHandler;
import plasticmod.plastic.handler.PlasticEventHandler;
import plasticmod.plastic.item.DrinkCore;
import plasticmod.plastic.item.MagicStick;
import plasticmod.plastic.item.PlasticArmor;
import plasticmod.plastic.item.PlasticAxe;
import plasticmod.plastic.item.PlasticBoal;
import plasticmod.plastic.item.PlasticBow;
import plasticmod.plastic.item.PlasticChestItem;
import plasticmod.plastic.item.PlasticDoor;
import plasticmod.plastic.item.PlasticFluidBucket;
import plasticmod.plastic.item.PlasticGun;
import plasticmod.plastic.item.PlasticGun2;
import plasticmod.plastic.item.PlasticKnife;
import plasticmod.plastic.item.PlasticPickaxe;
import plasticmod.plastic.item.PlasticShears;
import plasticmod.plastic.item.PlasticSpawnEgg;
import plasticmod.plastic.item.RarePlasticBoal;
import plasticmod.plastic.item.RarePlasticBow;
import plasticmod.plastic.item.RarePlasticGun;
import plasticmod.plastic.item.RarePlasticGun2;
import plasticmod.plastic.item.RarePlasticShears;
import plasticmod.plastic.item.TNTController;
import plasticmod.plastic.proxy.CommonProxy;
import plasticmod.plastic.render.RenderPlasticTNTPrimed;
import plasticmod.plastic.tileentity.TileEntityPlasticChest;
import plasticmod.plastic.villager.ComponentVillagePlasticHouse;
import plasticmod.plastic.villager.PlasticVillage;
import plasticmod.plastic.villager.VillageCreationHandlePlasticHouse;
import plasticmod.plastic.world.PlasticWorld;
import plasticmod.plastic.world.PlasticWorldProvider;
import plasticmod.plastic.world.biome.PlasticBiome;
import plasticmod.plastic.world.biome.PlasticBiome2;

@Mod(modid=PlasticCore.modid, name=PlasticCore.name, version=PlasticCore.version)
public class PlasticCore
{
	public static int tntx;
	public static int tnty;
	public static int tntz;
	//サウンドタイプ
	public static final Block.SoundType soundTypeMetal = new Block.SoundType("stone", 1.0F, 1.5F);
	public static final Block.SoundType soundTypePiston = new Block.SoundType("stone", 1.0F, 1.0F);
	//MOD情報
	public static final String modid = "plasticmod";
	public static final String name = "plasticmod";
	public static final String version = "2.6.7";
	//バイオームID バイオーム詳細
	public static int plasticbiomeID = PlasticConfig.plasticbiomeID;
	public static int plasticbiome2ID = PlasticConfig.rareplasticbiomeID;
	public static BiomeGenBase plasticbiome;
	public static BiomeGenBase plasticbiome2;
	//ロガー生成
	public static Logger loggers = LogManager.getLogger("Plasticmod");
	@Mod.Metadata(modid)
	public static ModMetadata metadata;
	//GUI詳細
	@Mod.Instance("plasticmod")
	public static PlasticCore INSTANCE;
	public static Block sampleGuiBlock;
	public static Item sampleGuiItem;
	public static final int ChestBlockGUI_ID = 0;
	public static final int ChestItemGUI_ID = 1;
	//ダメージ値付きナイフ
	public static PlasticKnife knife;
	//アイテム
	public static Item itemplasticFluid;
	public static Item tomatospecies;
	public static Item tomato;
	public static Item strawberryspecies;
	public static Item strawberry;
	public static Item plastichelmet;
	public static Item plasticchestplate;
	public static Item plasticleggings;
	public static Item plasticboots;
	public static Item rareplastichelmet;
	public static Item rareplasticchestplate;
	public static Item rareplasticleggings;
	public static Item rareplasticboots;
	public static Item cutpumpkin;
	public static Item cutcake;
	public static Item naphtha;
	public static Item rarenaphtha;
	public static Item rarenaphtha1;
	public static Item plasticingot;
	public static Item rareplasticingot;
	public static Item plasticnugget;
	public static Item rareplasticnugget;
	public static Item plasticbow;
	public static Item plasticsword;
	public static Item plastichoe;
	public static Item plasticspade;
	public static Item plasticaxe;
	public static Item plastipickaxe;
	public static Item rareplasticbow;
	public static Item rareplasticsword;
	public static Item rareplastichoe;
	public static Item rareplasticspade;
	public static Item rareplasticaxe;
	public static Item rareplastipickaxe;
	public static Item pet;
	public static Item pett;
	public static Item pets;
	public static Item strawberrymilk;
	public static Item plastic;
	public static Item rareplastic;
	public static Item itemSpawnEgg;
	public static Item plasticarrow;
	public static Item rareplasticarrow;
	public static Item plasticstick;
	public static Item rareplasticstick;
	public static Item flintAndPlasticSteel;
	public static Item flintAndRarePlasticSteel;
	public static Item plasticshears;
	public static Item rareplasticshears;
	public static Item magicstick;
	public static Item plasticChestItem;
	public static Item plasticpowder;
	public static Item PlasticDoor;
	public static Item RarePlasticDoor;
	public static Item TNTController;
	//ブロック
	public static Block naphthablock;
	public static Block rareplasticblock;
	public static Block plasticblock;
	public static Block plasticlight;
	public static Block plasticlightup;
	public static Block naphthaoil;
	public static Block rarenaphthablock;
	public static Block strawberryblock;
	public static Block tomatoblock;
	public static Block testblock;
	public static Block PlasticTNT;
	public static Block PlasticGlass;
	public static Block PlasticDoorBlock;
	public static Block RarePlasticDoorBlock;
	public static Block PlasticWirelessTNT;
	public static Block PlasticTrapDoor;
	public static Block RarePlasticTrapDoor;
	public static Block PlasticFence;
	public static Block RarePlasticFence;
	public static Block PlasticFenceGate;
	public static Block RarePlasticFenceGate;
	public static Block PlasticPressurePlateBlock;
	public static Block RarePlasticPressurePlateBlock;
	public static Block PlasticWall;
	public static Block RarePlasticWall;
	public static Block PlasticBlockStairs ;
	public static Block RarePlasticBlockStairs ;
	public static Block NaphthaBlockStairs ;
	public static Block PlasticSheet;
	//装飾ブロック
	public static Block plasticblockblack;
	public static Block plasticblocknavy;
	public static Block plasticblockdarkblue;
	public static Block plasticblockmediumblue;
	public static Block plasticblockblue;
	public static Block plasticblockdarkgreen;
	public static Block plasticblockgreen;
	public static Block plasticblockteal;
	public static Block plasticblockdarkcyan;
	public static Block plasticblockdeepskyblue;
	public static Block plasticblockdarkturquoise;
	public static Block plasticblockmediumspringgreen;
	public static Block plasticblockspringgreen;
	public static Block plasticblocklime;
	public static Block plasticblockaqua;
	public static Block plasticblockcyan;
	public static Block plasticblockmidnightblue;
	public static Block plasticblockdodgerblue;
	public static Block plasticblocklightseagreen;
	public static Block plasticblockforestgreen;
	public static Block plasticblockseagreen;
	public static Block plasticblockdarkslategray;
	public static Block plasticblocklimegreen;
	public static Block plasticblockmediumseagreen;
	public static Block plasticblockturquoise;
	public static Block plasticblockroyalblue;
	public static Block plasticblocksteelblue;
	public static Block plasticblockdarkslateblue;
	public static Block plasticblockmediumturquoise;
	public static Block plasticblockindigo;
	public static Block plasticblockdarkolivegreen;
	public static Block plasticblockcadetblue;
	public static Block plasticblockcornflowerblue;
	public static Block plasticblockmediumaquamarine;
	public static Block plasticblockdimgray;
	public static Block plasticblockslateblue;
	public static Block plasticblockolivedrab;
	public static Block plasticblockslategray;
	public static Block plasticblocklightslategray;
	public static Block plasticblockmediumslateblue;
	public static Block plasticblocklawngreen;
	public static Block plasticblockchartreuse;
	public static Block plasticblockaquamarine;
	public static Block plasticblockmaroon;
	public static Block plasticblockpurple;
	public static Block plasticblockolive;
	public static Block plasticblockgray;
	public static Block plasticblockskyblue;
	public static Block plasticblocklightskyblue;
	public static Block plasticblockblueviolet;
	public static Block plasticblockdarkred;
	public static Block plasticblockdarkmagenta;
	public static Block plasticblocksaddlebrown;
	public static Block plasticblockdarkseagreen;
	public static Block plasticblocklightgreen;
	public static Block plasticblockmediumpurple;
	public static Block plasticblockdarkviolet;
	public static Block plasticblockpalegreen;
	public static Block plasticblockdarkorchid;
	public static Block plasticblockyellowgreen;
	public static Block plasticblocksienna;
	public static Block plasticblockbrown;
	public static Block plasticblockdarkgray;
	public static Block plasticblocklightblue;
	public static Block plasticblockgreenyellow;
	public static Block plasticblockpaleturquoise;
	public static Block plasticblocklightsteelblue;
	public static Block plasticblockpowderblue;
	public static Block plasticblockfirebrick;
	public static Block plasticblockdarkgoldenrod;
	public static Block plasticblockmediumorchid;
	public static Block plasticblockrosybrown;
	public static Block plasticblockdarkkhaki;
	public static Block plasticblocksilver;
	public static Block plasticblockmediumvioletred;
	public static Block plasticblockindianred;
	public static Block plasticblockperu;
	public static Block plasticblockchocolate;
	public static Block plasticblocktan;
	public static Block plasticblocklightgrey;
	public static Block plasticblockthistle;
	public static Block plasticblockorchid;
	public static Block plasticblockgoldenrod;
	public static Block plasticblockpalevioletred;
	public static Block plasticblockcrimson;
	public static Block plasticblockgainsboro;
	public static Block plasticblockplum;
	public static Block plasticblockburlywood;
	public static Block plasticblocklightcyan;
	public static Block plasticblocklavender;
	public static Block plasticblockdarksalmon;
	public static Block plasticblockviolet;
	public static Block plasticblockpalegoldenrod;
	public static Block plasticblocklightcoral;
	public static Block plasticblockkhaki;
	public static Block plasticblockaliceblue;
	public static Block plasticblockhoneydew;
	public static Block plasticblockazure;
	public static Block plasticblocksandybrown;
	public static Block plasticblockwheat;
	public static Block plasticblockbeige;
	public static Block plasticblockwhitesmoke;
	public static Block plasticblockmintcream;
	public static Block plasticblockghostwhite;
	public static Block plasticblocksalmon;
	public static Block plasticblockantiquewhite;
	public static Block plasticblocklinen;
	public static Block plasticblocklightgoldenrodyellow;
	public static Block plasticblockoldlace;
	public static Block plasticblockred;
	public static Block plasticblockfuchsia;
	public static Block plasticblockmagenta;
	public static Block plasticblockdeeppink;
	public static Block plasticblockorangered;
	public static Block plasticblocktomato;
	public static Block plasticblockhotpink;
	public static Block plasticblockcoral;
	public static Block plasticblockdarkorange;
	public static Block plasticblocklightsalmon;
	public static Block plasticblockorange;
	public static Block plasticblocklightpink;
	public static Block plasticblockpink;
	public static Block plasticblockgold;
	public static Block plasticblockpeachpuff;
	public static Block plasticblocknavajowhite;
	public static Block plasticblockmoccasin;
	public static Block plasticblockbisque;
	public static Block plasticblockmistyrose;
	public static Block plasticblockblanchedalmond;
	public static Block plasticblockpapayawhip;
	public static Block plasticblocklavenderblush;
	public static Block plasticblockseashell;
	public static Block plasticblockcornsilk;
	public static Block plasticblocklemonchiffon;
	public static Block plasticblockfloralwhite;
	public static Block plasticblocksnow;
	public static Block plasticblockyellow;
	public static Block plasticblocklightyellow;
	public static Block plasticblockivory;
	public static Block plasticblockwhite;
	//装飾ブロック2
	public static Block plasticblockaliceblueCarpet;
	public static Block plasticblockantiquewhiteCarpet;
	public static Block plasticblockaquaCarpet;
	public static Block plasticblockaquamarineCarpet;
	public static Block plasticblockazureCarpet;
	public static Block plasticblockbeigeCarpet;
	public static Block plasticblockbisqueCarpet;
	public static Block plasticblockblackCarpet;
	public static Block plasticblockblanchedalmondCarpet;
	public static Block plasticblockblueCarpet;
	public static Block plasticblockbluevioletCarpet;
	public static Block plasticblockbrownCarpet;
	public static Block plasticblockburlywoodCarpet;
	public static Block plasticblockcadetblueCarpet;
	public static Block plasticblockchartreuseCarpet;
	public static Block plasticblockchocolateCarpet;
	public static Block plasticblockcoralCarpet;
	public static Block plasticblockcornflowerblueCarpet;
	public static Block plasticblockcornsilkCarpet;
	public static Block plasticblockcrimsonCarpet;
	public static Block plasticblockcyanCarpet;
	public static Block plasticblockdarkblueCarpet;
	public static Block plasticblockdarkcyanCarpet;
	public static Block plasticblockdarkgoldenrodCarpet;
	public static Block plasticblockdarkgrayCarpet;
	public static Block plasticblockdarkgreenCarpet;
	public static Block plasticblockdarkkhakiCarpet;
	public static Block plasticblockdarkmagentaCarpet;
	public static Block plasticblockdarkolivegreenCarpet;
	public static Block plasticblockdarkorangeCarpet;
	public static Block plasticblockdarkorchidCarpet;
	public static Block plasticblockdarkredCarpet;
	public static Block plasticblockdarksalmonCarpet;
	public static Block plasticblockdarkseagreenCarpet;
	public static Block plasticblockdarkslateblueCarpet;
	public static Block plasticblockdarkslategrayCarpet;
	public static Block plasticblockdarkturquoiseCarpet;
	public static Block plasticblockdarkvioletCarpet;
	public static Block plasticblockdeeppinkCarpet;
	public static Block plasticblockdeepskyblueCarpet;
	public static Block plasticblockdimgrayCarpet;
	public static Block plasticblockdodgerblueCarpet;
	public static Block plasticblockfirebrickCarpet;
	public static Block plasticblockfloralwhiteCarpet;
	public static Block plasticblockforestgreenCarpet;
	public static Block plasticblockfuchsiaCarpet;
	public static Block plasticblockgainsboroCarpet;
	public static Block plasticblockghostwhiteCarpet;
	public static Block plasticblockgoldCarpet;
	public static Block plasticblockgoldenrodCarpet;
	public static Block plasticblockgrayCarpet;
	public static Block plasticblockgreenCarpet;
	public static Block plasticblockgreenyellowCarpet;
	public static Block plasticblockhoneydewCarpet;
	public static Block plasticblockhotpinkCarpet;
	public static Block plasticblockindianredCarpet;
	public static Block plasticblockindigoCarpet;
	public static Block plasticblockivoryCarpet;
	public static Block plasticblockkhakiCarpet;
	public static Block plasticblocklavenderCarpet;
	public static Block plasticblocklavenderblushCarpet;
	public static Block plasticblocklawngreenCarpet;
	public static Block plasticblocklemonchiffonCarpet;
	public static Block plasticblocklightblueCarpet;
	public static Block plasticblocklightcoralCarpet;
	public static Block plasticblocklightcyanCarpet;
	public static Block plasticblocklightgoldenrodyellowCarpet;
	public static Block plasticblocklightgreenCarpet;
	public static Block plasticblocklightgreyCarpet;
	public static Block plasticblocklightpinkCarpet;
	public static Block plasticblocklightsalmonCarpet;
	public static Block plasticblocklightseagreenCarpet;
	public static Block plasticblocklightskyblueCarpet;
	public static Block plasticblocklightslategrayCarpet;
	public static Block plasticblocklightsteelblueCarpet;
	public static Block plasticblocklightyellowCarpet;
	public static Block plasticblocklimeCarpet;
	public static Block plasticblocklimegreenCarpet;
	public static Block plasticblocklinenCarpet;
	public static Block plasticblockmagentaCarpet;
	public static Block plasticblockmaroonCarpet;
	public static Block plasticblockmediumaquamarineCarpet;
	public static Block plasticblockmediumblueCarpet;
	public static Block plasticblockmediumorchidCarpet;
	public static Block plasticblockmediumpurpleCarpet;
	public static Block plasticblockmediumseagreenCarpet;
	public static Block plasticblockmediumslateblueCarpet;
	public static Block plasticblockmediumspringgreenCarpet;
	public static Block plasticblockmediumturquoiseCarpet;
	public static Block plasticblockmediumvioletredCarpet;
	public static Block plasticblockmidnightblueCarpet;
	public static Block plasticblockmintcreamCarpet;
	public static Block plasticblockmistyroseCarpet;
	public static Block plasticblockmoccasinCarpet;
	public static Block plasticblocknavajowhiteCarpet;
	public static Block plasticblocknavyCarpet;
	public static Block plasticblockoldlaceCarpet;
	public static Block plasticblockoliveCarpet;
	public static Block plasticblockolivedrabCarpet;
	public static Block plasticblockorangeCarpet;
	public static Block plasticblockorangeredCarpet;
	public static Block plasticblockorchidCarpet;
	public static Block plasticblockpalegoldenrodCarpet;
	public static Block plasticblockpalegreenCarpet;
	public static Block plasticblockpaleturquoiseCarpet;
	public static Block plasticblockpalevioletredCarpet;
	public static Block plasticblockpapayawhipCarpet;
	public static Block plasticblockpeachpuffCarpet;
	public static Block plasticblockperuCarpet;
	public static Block plasticblockpinkCarpet;
	public static Block plasticblockplumCarpet;
	public static Block plasticblockpowderblueCarpet;
	public static Block plasticblockpurpleCarpet;
	public static Block plasticblockredCarpet;
	public static Block plasticblockrosybrownCarpet;
	public static Block plasticblockroyalblueCarpet;
	public static Block plasticblocksaddlebrownCarpet;
	public static Block plasticblocksalmonCarpet;
	public static Block plasticblocksandybrownCarpet;
	public static Block plasticblockseagreenCarpet;
	public static Block plasticblockseashellCarpet;
	public static Block plasticblocksiennaCarpet;
	public static Block plasticblocksilverCarpet;
	public static Block plasticblockskyblueCarpet;
	public static Block plasticblockslateblueCarpet;
	public static Block plasticblockslategrayCarpet;
	public static Block plasticblocksnowCarpet;
	public static Block plasticblockspringgreenCarpet;
	public static Block plasticblocksteelblueCarpet;
	public static Block plasticblocktanCarpet;
	public static Block plasticblocktealCarpet;
	public static Block plasticblockthistleCarpet;
	public static Block plasticblocktomatoCarpet;
	public static Block plasticblockturquoiseCarpet;
	public static Block plasticblockvioletCarpet;
	public static Block plasticblockwheatCarpet;
	public static Block plasticblockwhiteCarpet;
	public static Block plasticblockwhitesmokeCarpet;
	public static Block plasticblockyellowCarpet;
	public static Block plasticblockyellowgreenCarpet;
	//液体プラスチック
	public static BlockFluidClassic PlasticFluidBlock;
	//液体プラスチックのバケツ
	public static Item PlasticFluid_Bucket;
	//流体クラス
	public static Fluid PlasticFluid ;
	//GUIテスト 将来はクラフトボックスになる予定
	public static Block Plasticblockcraftbox;
	public static final Material plasticmate = (new Material(MapColor.woodColor));
	public static final Material rareplasticmate = (new Material(MapColor.ironColor));
	//ツールマテリアル
	public static ToolMaterial plastictool;
	public static ToolMaterial plastictool2;
	public static ToolMaterial plastictool3;
	//クリエイティブタブ
	public static CreativeTabs plasticTab;
	public static CreativeTabs plasticTabBlock;
	public static CreativeTabs plasticTabBlock2;
	//銃
	public static Item rareplasticBullet;
	public static Item rareplasticBullet2;
	public static Item plasticBullet;
	public static Item plasticBullet2;
	public static Item plasticgun;
	public static Item plasticgun2;
	public static Item rareplasticgun;
	public static Item rareplasticgun2;
	//ゲート
	public static Item plasticgate;
	public static Block plasticportalblock;
	@SidedProxy(clientSide = "plasticmod.plastic.proxy.client.ClientProxy", serverSide = "plasticmod.plastic.proxy.server.ServerProxy")
	public static CommonProxy proxy;
	public static int RenderNewTNTPrimed;
	public static int PlasticArrowRenderId1;
	public static int PlasticArrowRenderId2;
	public static int PlasticskeletonRender;
	public static int PlasticCreeperRender;
	public static int PlasticRenderID;

	public static DamageSource causeTestArrowDamage(EntityRarePlasticArrow rareplasticArrowEntity, Entity par1Entity)
	{
		return (new EntityDamageSourceIndirect("RarePlasticArrowEntity", rareplasticArrowEntity, par1Entity)).setProjectile();
	}
	public static DamageSource causeTestArrowDamage(EntityPlasticArrow plasticArrowEntity, Entity par1Entity)
	{
		return (new EntityDamageSourceIndirect("PlasticArrowEntity", plasticArrowEntity, par1Entity)).setProjectile();
	}
	public static DamageSource causeTestArrowDamage(EntityPlasticBullet PlasticEntityBullet, Entity par1Entity)
	{
		return (new EntityDamageSourceIndirect("PlasticEntityBullet", PlasticEntityBullet, par1Entity)).setProjectile();
	}
	//langファイルから読み込み
	public static String getLocal(String str){
		return StatCollector.translateToLocal(str);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		PlasticConfig.preInit();
			logger("Loaded language(読み見込んだ言語):"+StatCollector.translateToLocal("title.lang.name"));
			logger(getLocal("logger"));
			logger("MODID:"+ modid +" "+getLocal("logger.version")+version);
			logger(getLocal("logger.config"));
			logger(getLocal("logger.plasticbiomeID")+PlasticConfig.plasticbiomeID);
			logger(getLocal("logger.rareplasticbiomeID")+PlasticConfig.rareplasticbiomeID);
			logger(getLocal("logger.dimensionID")+PlasticConfig.dimensionID);
			logger(getLocal("logger.providerType")+PlasticConfig.providerType);
			logger(getLocal("logger.gateeffect")+PlasticConfig.gateeffect);
			logger(getLocal("logger.plasticskeleton_spawn_hell")+PlasticConfig.plasticskeleton_spawn_hell);
			logger(getLocal("logger.plasticskeleton_spawn_sky")+PlasticConfig.plasticskeleton_spawn_sky);
			logger(getLocal("logger.plasticcarpetlight")+PlasticConfig.plasticcarpetlight);
			logger(getLocal("logger.rescuerecipes")+PlasticConfig.rescuerecipes);
			logger(getLocal("logger.plastic_Developer_options")+PlasticConfig.plastic_Developer_options);
			logger("------------------------------------------------");

		PlasticModData.load(metadata);
		//クリエイティブタブアイテム
		plasticTab = new CreativeTabs("plasticTab")
		{
			public Item getTabIconItem()
			{
				return PlasticCore.plasticingot;
			}
		};
		//クリエイティブタブブロック
		plasticTabBlock = new CreativeTabs("plasticTabBlock")
		{
			public Item getTabIconItem()
			{
				return Item.getItemFromBlock(PlasticCore.plasticblockred);
			}
		};
		plasticTabBlock2 = new CreativeTabs("plasticTabBlock2")
		{
			public Item getTabIconItem()
			{
				return Item.getItemFromBlock(PlasticCore.plasticblockblueCarpet);
			}
		};
		//建設ブロック
		plasticblockblack = new Plasticblockblack().setBlockName("plasticblockblack");
		plasticblocknavy = new Plasticblocknavy().setBlockName("plasticblocknavy");
		plasticblockdarkblue = new Plasticblockdarkblue().setBlockName("plasticblockdarkblue");
		plasticblockmediumblue = new Plasticblockmediumblue().setBlockName("plasticblockmediumblue");
		plasticblockblue = new Plasticblockblue().setBlockName("plasticblockblue");
		plasticblockdarkgreen = new Plasticblockdarkgreen().setBlockName("plasticblockdarkgreen");
		plasticblockgreen = new Plasticblockgreen().setBlockName("plasticblockgreen");
		plasticblockteal = new Plasticblockteal().setBlockName("plasticblockteal");
		plasticblockdarkcyan = new Plasticblockdarkcyan().setBlockName("plasticblockdarkcyan");
		plasticblockdeepskyblue = new Plasticblockdeepskyblue().setBlockName("plasticblockdeepskyblue");
		plasticblockdarkturquoise = new Plasticblockdarkturquoise().setBlockName("plasticblockdarkturquoise");
		plasticblockmediumspringgreen = new Plasticblockmediumspringgreen().setBlockName("plasticblockmediumspringgreen");
		plasticblockspringgreen = new Plasticblockspringgreen().setBlockName("plasticblockspringgreen");
		plasticblocklime = new Plasticblocklime().setBlockName("plasticblocklime");
		plasticblockaqua = new Plasticblockaqua().setBlockName("plasticblockaqua");
		plasticblockcyan = new Plasticblockcyan().setBlockName("plasticblockcyan");
		plasticblockmidnightblue = new Plasticblockmidnightblue().setBlockName("plasticblockmidnightbluea");
		plasticblockdodgerblue = new Plasticblockdodgerblue().setBlockName("plasticblockdodgerblue");
		plasticblocklightseagreen = new Plasticblocklightseagreen().setBlockName("plasticblocklightseagreen");
		plasticblockforestgreen = new Plasticblockforestgreen().setBlockName("plasticblockforestgreen");
		plasticblockseagreen = new Plasticblockseagreen().setBlockName("plasticblockseagreen");
		plasticblockdarkslategray = new Plasticblockdarkslategray().setBlockName("plasticblockdarkslategray");
		plasticblocklimegreen = new Plasticblocklimegreen().setBlockName("plasticblocklimegreen");
		plasticblockmediumseagreen = new Plasticblockmediumseagreen().setBlockName("plasticblockmediumseagreen");
		plasticblockturquoise = new Plasticblockturquoise().setBlockName("plasticblockturquoise");
		plasticblockroyalblue = new Plasticblockroyalblue().setBlockName("plasticblockroyalblue");
		plasticblocksteelblue = new Plasticblocksteelblue().setBlockName("plasticblocksteelblue");
		plasticblockdarkslateblue = new Plasticblockdarkslateblue().setBlockName("plasticblockdarkslateblue");
		plasticblockmediumturquoise = new Plasticblockmediumturquoise().setBlockName("plasticblockmediumturquoise");
		plasticblockindigo = new Plasticblockindigo().setBlockName("plasticblockindigo");
		plasticblockdarkolivegreen = new Plasticblockdarkolivegreen().setBlockName("plasticblockdarkolivegreen");
		plasticblockcadetblue = new Plasticblockcadetblue().setBlockName("plasticblockcadetblue");
		plasticblockcornflowerblue = new Plasticblockcornflowerblue().setBlockName("plasticblockcornflowerblue");
		plasticblockmediumaquamarine = new Plasticblockmediumaquamarine().setBlockName("plasticblockmediumaquamarine");
		plasticblockdimgray = new Plasticblockdimgray().setBlockName("plasticblockdimgray");
		plasticblockslateblue = new Plasticblockslateblue().setBlockName("plasticblockslateblue");
		plasticblockolivedrab = new Plasticblockolivedrab().setBlockName("plasticblockolivedrab");
		plasticblockslategray = new Plasticblockslategray().setBlockName("plasticblockslategray");
		plasticblocklightslategray = new Plasticblocklightslategray().setBlockName("plasticblocklightslategray");
		plasticblockmediumslateblue = new Plasticblockmediumslateblue().setBlockName("plasticblockmediumslateblue");
		plasticblocklawngreen = new Plasticblocklawngreen().setBlockName("plasticblocklawngreen");
		plasticblockchartreuse = new Plasticblockchartreuse().setBlockName("plasticblockchartreuse");
		plasticblockaquamarine = new Plasticblockaquamarine().setBlockName("plasticblockaquamarine");
		plasticblockmaroon = new Plasticblockmaroon().setBlockName("plasticblockmaroon");
		plasticblockpurple = new Plasticblockpurple().setBlockName("plasticblockpurple");
		plasticblockolive = new Plasticblockolive().setBlockName("plasticblockolive");
		plasticblockgray = new Plasticblockgray().setBlockName("plasticblockgray");
		plasticblockskyblue = new Plasticblockskyblue().setBlockName("plasticblockskyblue");
		plasticblocklightskyblue = new Plasticblocklightskyblue().setBlockName("plasticblocklightskyblue");
		plasticblockblueviolet = new Plasticblockblueviolet().setBlockName("plasticblockblueviolet");
		plasticblockdarkred = new Plasticblockdarkred().setBlockName("plasticblockdarkred");
		plasticblockdarkmagenta = new Plasticblockdarkmagenta().setBlockName("plasticblockdarkmagenta");
		plasticblocksaddlebrown = new Plasticblocksaddlebrown().setBlockName("plasticblocksaddlebrown");
		plasticblockdarkseagreen = new Plasticblockdarkseagreen().setBlockName("plasticblockdarkseagreen");
		plasticblocklightgreen = new Plasticblocklightgreen().setBlockName("plasticblocklightgreen");
		plasticblockmediumpurple = new Plasticblockmediumpurple().setBlockName("plasticblockmediumpurple");
		plasticblockdarkviolet = new Plasticblockdarkviolet().setBlockName("plasticblockdarkviolet");
		plasticblockpalegreen = new Plasticblockpalegreen().setBlockName("plasticblockpalegreen");
		plasticblockdarkorchid = new Plasticblockdarkorchid().setBlockName("plasticblockdarkorchid");
		plasticblockyellowgreen = new Plasticblockyellowgreen().setBlockName("plasticblockyellowgreen");
		plasticblocksienna = new Plasticblocksienna().setBlockName("plasticblocksienna");
		plasticblockbrown = new Plasticblockbrown().setBlockName("plasticblockbrown");
		plasticblockdarkgray = new Plasticblockdarkgray().setBlockName("plasticblockdarkgray");
		plasticblocklightblue = new Plasticblocklightblue().setBlockName("plasticblocklightblue");
		plasticblockgreenyellow = new Plasticblockgreenyellow().setBlockName("plasticblockgreenyellow");
		plasticblockpaleturquoise = new Plasticblockpaleturquoise().setBlockName("plasticblockpaleturquoise");
		plasticblocklightsteelblue = new Plasticblocklightsteelblue().setBlockName("plasticblocklightsteelblue");
		plasticblockpowderblue = new Plasticblockpowderblue().setBlockName("plasticblockpowderblue");
		plasticblockfirebrick = new Plasticblockfirebrick().setBlockName("plasticblockfirebrick");
		plasticblockdarkgoldenrod = new Plasticblockdarkgoldenrod().setBlockName("plasticblockdarkgoldenrod");
		plasticblockmediumorchid = new Plasticblockmediumorchid().setBlockName("plasticblockmediumorchid");
		plasticblockrosybrown = new Plasticblockrosybrown().setBlockName("plasticblockrosybrown");
		plasticblockdarkkhaki = new Plasticblockdarkkhaki().setBlockName("plasticblockdarkkhaki");
		plasticblocksilver = new Plasticblocksilver().setBlockName("plasticblocksilver");
		plasticblockmediumvioletred = new Plasticblockmediumvioletred().setBlockName("plasticblockmediumvioletred");
		plasticblockindianred = new Plasticblockindianred().setBlockName("plasticblockindianred");
		plasticblockperu = new Plasticblockperu().setBlockName("plasticblockperu");
		plasticblockchocolate = new Plasticblockchocolate().setBlockName("plasticblockchocolate");
		plasticblocktan = new Plasticblocktan().setBlockName("plasticblocktan");
		plasticblocklightgrey = new Plasticblocklightgrey().setBlockName("plasticblocklightgrey");
		plasticblockthistle = new Plasticblockthistle().setBlockName("plasticblockthistle");
		plasticblockorchid = new Plasticblockorchid().setBlockName("plasticblockorchid");
		plasticblockgoldenrod = new Plasticblockgoldenrod().setBlockName("plasticblockgoldenrod");
		plasticblockpalevioletred = new Plasticblockpalevioletred().setBlockName("plasticblockpalevioletred");
		plasticblockcrimson = new Plasticblockcrimson().setBlockName("plasticblockcrimson");
		plasticblockgainsboro = new Plasticblockgainsboro().setBlockName("plasticblockgainsboro");
		plasticblockplum = new Plasticblockplum().setBlockName("plasticblockplum");
		plasticblockburlywood = new Plasticblockburlywood().setBlockName("plasticblockburlywood");
		plasticblocklightcyan = new Plasticblocklightcyan().setBlockName("plasticblocklightcyan");
		plasticblocklavender = new Plasticblocklavender().setBlockName("plasticblocklavender");
		plasticblockdarksalmon = new Plasticblockdarksalmon().setBlockName("plasticblockdarksalmon");
		plasticblockviolet = new Plasticblockviolet().setBlockName("plasticblockviolet");
		plasticblockpalegoldenrod = new Plasticblockpalegoldenrod().setBlockName("plasticblockpalegoldenrod");
		plasticblocklightcoral = new Plasticblocklightcoral().setBlockName("plasticblocklightcoral");
		plasticblockkhaki = new Plasticblockkhaki().setBlockName("plasticblockkhaki");
		plasticblockaliceblue = new Plasticblockaliceblue().setBlockName("plasticblockaliceblue");
		plasticblockhoneydew = new Plasticblockhoneydew().setBlockName("plasticblockhoneydew");
		plasticblockazure = new Plasticblockazure().setBlockName("plasticblockazure");
		plasticblocksandybrown = new Plasticblocksandybrown().setBlockName("plasticblocksandybrown");
		plasticblockwheat = new Plasticblockwheat().setBlockName("plasticblockwheat");
		plasticblockbeige = new Plasticblockbeige().setBlockName("plasticblockbeige");
		plasticblockwhitesmoke = new Plasticblockwhitesmoke().setBlockName("plasticblockwhitesmoke");
		plasticblockmintcream = new Plasticblockmintcream().setBlockName("plasticblockmintcream");
		plasticblockghostwhite = new Plasticblockghostwhite().setBlockName("plasticblockghostwhite");
		plasticblocksalmon = new Plasticblocksalmon().setBlockName("plasticblocksalmon");
		plasticblockantiquewhite = new Plasticblockantiquewhite().setBlockName("plasticblockantiquewhite");
		plasticblocklinen = new Plasticblocklinen().setBlockName("plasticblocklinen");
		plasticblocklightgoldenrodyellow = new Plasticblocklightgoldenrodyellow().setBlockName("plasticblocklightgoldenrodyellow");
		plasticblockoldlace = new Plasticblockoldlace().setBlockName("plasticblockoldlace");
		plasticblockred = new Plasticblockred().setBlockName("plasticblockred");
		plasticblockfuchsia = new Plasticblockfuchsia().setBlockName("plasticblockfuchsia");
		plasticblockmagenta = new Plasticblockmagenta().setBlockName("plasticblockmagenta");
		plasticblockdeeppink = new Plasticblockdeeppink().setBlockName("plasticblockdeeppink");
		plasticblockorangered = new Plasticblockorangered().setBlockName("plasticblockorangered");
		plasticblocktomato = new Plasticblocktomato().setBlockName("plasticblocktomato");
		plasticblockhotpink = new Plasticblockhotpink().setBlockName("plasticblockhotpink");
		plasticblockcoral = new Plasticblockcoral().setBlockName("plasticblockcoral");
		plasticblockdarkorange = new Plasticblockdarkorange().setBlockName("plasticblockdarkorange");
		plasticblocklightsalmon = new Plasticblocklightsalmon().setBlockName("plasticblocklightsalmon");
		plasticblockorange = new Plasticblockorange().setBlockName("plasticblockorange");
		plasticblocklightpink = new Plasticblocklightpink().setBlockName("plasticblocklightpink");
		plasticblockpink = new Plasticblockpink().setBlockName("plasticblockpink");
		plasticblockgold = new Plasticblockgold().setBlockName("plasticblockgold");
		plasticblockpeachpuff = new Plasticblockpeachpuff().setBlockName("plasticblockpeachpuff");
		plasticblocknavajowhite = new Plasticblocknavajowhite().setBlockName("plasticblocknavajowhite");
		plasticblockmoccasin = new Plasticblockmoccasin().setBlockName("plasticblockmoccasin");
		plasticblockbisque = new Plasticblockbisque().setBlockName("plasticblockbisque");
		plasticblockmistyrose = new Plasticblockmistyrose().setBlockName("plasticblockmistyrose");
		plasticblockblanchedalmond = new Plasticblockblanchedalmond().setBlockName("plasticblockblanchedalmond");
		plasticblockpapayawhip = new Plasticblockpapayawhip().setBlockName("plasticblockpapayawhip");
		plasticblocklavenderblush = new Plasticblocklavenderblush().setBlockName("plasticblocklavenderblush");
		plasticblockseashell = new Plasticblockseashell().setBlockName("plasticblockseashell");
		plasticblockcornsilk = new Plasticblockcornsilk().setBlockName("plasticblockcornsilk");
		plasticblocklemonchiffon = new Plasticblocklemonchiffon().setBlockName("plasticblocklemonchiffon");
		plasticblockfloralwhite = new Plasticblockfloralwhite().setBlockName("plasticblockfloralwhite");
		plasticblocksnow = new Plasticblocksnow().setBlockName("plasticblocksnow");
		plasticblockyellow = new Plasticblockyellow().setBlockName("plasticblockyellow");
		plasticblocklightyellow = new Plasticblocklightyellow().setBlockName("plasticblocklightyellow");
		plasticblockivory = new Plasticblockivory().setBlockName("plasticblockivory");
		plasticblockwhite = new Plasticblockwhite().setBlockName("plasticblockwhite");
		GameRegistry.registerBlock(plasticblockblack, "plasticblockblack");
		GameRegistry.registerBlock(plasticblocknavy, "plasticblocknavy");
		GameRegistry.registerBlock(plasticblockdarkblue, "plasticblockdarkblue");
		GameRegistry.registerBlock(plasticblockmediumblue, "plasticblockmediumblue");
		GameRegistry.registerBlock(plasticblockblue, "plasticblockblue");
		GameRegistry.registerBlock(plasticblockdarkgreen, "plasticblockdarkgreen");
		GameRegistry.registerBlock(plasticblockgreen, "plasticblockgreen");
		GameRegistry.registerBlock(plasticblockteal, "plasticblockteal");
		GameRegistry.registerBlock(plasticblockdarkcyan, "plasticblockdarkcyan");
		GameRegistry.registerBlock(plasticblockdeepskyblue, "plasticblockdeepskyblue");
		GameRegistry.registerBlock(plasticblockdarkturquoise, "plasticblockdarkturquoise");
		GameRegistry.registerBlock(plasticblockmediumspringgreen, "plasticblockmediumspringgreen");
		GameRegistry.registerBlock(plasticblockspringgreen, "plasticblockspringgreen");
		GameRegistry.registerBlock(plasticblocklime, "plasticblocklime");
		GameRegistry.registerBlock(plasticblockaqua, "plasticblockaqua");
		GameRegistry.registerBlock(plasticblockcyan, "plasticblockcyan");
		GameRegistry.registerBlock(plasticblockmidnightblue, "plasticblockmidnightblue");
		GameRegistry.registerBlock(plasticblockdodgerblue, "plasticblockdodgerblue");
		GameRegistry.registerBlock(plasticblocklightseagreen, "plasticblocklightseagreen");
		GameRegistry.registerBlock(plasticblockforestgreen, "plasticblockforestgreen");
		GameRegistry.registerBlock(plasticblockseagreen, "plasticblockseagreen");
		GameRegistry.registerBlock(plasticblockdarkslategray, "plasticblockdarkslategray");
		GameRegistry.registerBlock(plasticblocklimegreen, "plasticblocklimegreen");
		GameRegistry.registerBlock(plasticblockmediumseagreen, "plasticblockmediumseagreen");
		GameRegistry.registerBlock(plasticblockturquoise, "plasticblockturquoise");
		GameRegistry.registerBlock(plasticblockroyalblue, "plasticblockroyalblue");
		GameRegistry.registerBlock(plasticblocksteelblue, "plasticblocksteelblue");
		GameRegistry.registerBlock(plasticblockdarkslateblue, "plasticblockdarkslateblue");
		GameRegistry.registerBlock(plasticblockmediumturquoise, "plasticblockmediumturquoise");
		GameRegistry.registerBlock(plasticblockindigo, "plasticblockindigo");
		GameRegistry.registerBlock(plasticblockdarkolivegreen, "plasticblockdarkolivegreen");
		GameRegistry.registerBlock(plasticblockcadetblue, "plasticblockcadetblue");
		GameRegistry.registerBlock(plasticblockcornflowerblue, "plasticblockcornflowerblue");
		GameRegistry.registerBlock(plasticblockmediumaquamarine, "plasticblockmediumaquamarine");
		GameRegistry.registerBlock(plasticblockdimgray, "plasticblockdimgray");
		GameRegistry.registerBlock(plasticblockslateblue, "plasticblockslateblue");
		GameRegistry.registerBlock(plasticblockolivedrab, "plasticblockolivedrab");
		GameRegistry.registerBlock(plasticblockslategray, "plasticblockslategray");
		GameRegistry.registerBlock(plasticblocklightslategray, "plasticblocklightslategray");
		GameRegistry.registerBlock(plasticblockmediumslateblue, "plasticblockmediumslateblue");
		GameRegistry.registerBlock(plasticblocklawngreen, "plasticblocklawngreen");
		GameRegistry.registerBlock(plasticblockchartreuse, "plasticblockchartreuse");
		GameRegistry.registerBlock(plasticblockaquamarine, "plasticblockaquamarine");
		GameRegistry.registerBlock(plasticblockmaroon, "plasticblockmaroon");
		GameRegistry.registerBlock(plasticblockpurple, "plasticblockpurple");
		GameRegistry.registerBlock(plasticblockolive, "plasticblockolive");
		GameRegistry.registerBlock(plasticblockgray, "plasticblockgray");
		GameRegistry.registerBlock(plasticblockskyblue, "plasticblockskyblue");
		GameRegistry.registerBlock(plasticblocklightskyblue, "plasticblocklightskyblue");
		GameRegistry.registerBlock(plasticblockblueviolet, "plasticblockblueviolet");
		GameRegistry.registerBlock(plasticblockdarkred, "plasticblockdarkred");
		GameRegistry.registerBlock(plasticblockdarkmagenta, "plasticblockdarkmagenta");
		GameRegistry.registerBlock(plasticblocksaddlebrown, "plasticblocksaddlebrown");
		GameRegistry.registerBlock(plasticblockdarkseagreen, "plasticblockdarkseagreen");
		GameRegistry.registerBlock(plasticblocklightgreen, "plasticblocklightgreen");
		GameRegistry.registerBlock(plasticblockmediumpurple, "plasticblockmediumpurple");
		GameRegistry.registerBlock(plasticblockdarkviolet, "plasticblockdarkviolet");
		GameRegistry.registerBlock(plasticblockpalegreen, "plasticblockpalegreen");
		GameRegistry.registerBlock(plasticblockdarkorchid, "plasticblockdarkorchid");
		GameRegistry.registerBlock(plasticblockyellowgreen, "plasticblockyellowgreen");
		GameRegistry.registerBlock(plasticblocksienna, "plasticblocksienna");
		GameRegistry.registerBlock(plasticblockbrown, "plasticblockbrown");
		GameRegistry.registerBlock(plasticblockdarkgray, "plasticblockdarkgray");
		GameRegistry.registerBlock(plasticblocklightblue, "plasticblocklightblue");
		GameRegistry.registerBlock(plasticblockgreenyellow, "plasticblockgreenyellow");
		GameRegistry.registerBlock(plasticblockpaleturquoise, "plasticblockpaleturquoise");
		GameRegistry.registerBlock(plasticblocklightsteelblue, "plasticblocklightsteelblue");
		GameRegistry.registerBlock(plasticblockpowderblue, "plasticblockpowderblue");
		GameRegistry.registerBlock(plasticblockfirebrick, "plasticblockfirebrick");
		GameRegistry.registerBlock(plasticblockdarkgoldenrod, "plasticblockdarkgoldenrod");
		GameRegistry.registerBlock(plasticblockmediumorchid, "plasticblockmediumorchid");
		GameRegistry.registerBlock(plasticblockrosybrown, "plasticblockrosybrown");
		GameRegistry.registerBlock(plasticblockdarkkhaki, "plasticblockdarkkhaki");
		GameRegistry.registerBlock(plasticblocksilver, "plasticblocksilver");
		GameRegistry.registerBlock(plasticblockmediumvioletred, "plasticblockmediumvioletred");
		GameRegistry.registerBlock(plasticblockindianred, "plasticblockindianred");
		GameRegistry.registerBlock(plasticblockperu, "plasticblockperu");
		GameRegistry.registerBlock(plasticblockchocolate, "plasticblockchocolate");
		GameRegistry.registerBlock(plasticblocktan, "plasticblocktan");
		GameRegistry.registerBlock(plasticblocklightgrey, "plasticblocklightgrey");
		GameRegistry.registerBlock(plasticblockthistle, "plasticblockthistle");
		GameRegistry.registerBlock(plasticblockorchid, "plasticblockorchid");
		GameRegistry.registerBlock(plasticblockgoldenrod, "plasticblockgoldenrod");
		GameRegistry.registerBlock(plasticblockpalevioletred, "plasticblockpalevioletred");
		GameRegistry.registerBlock(plasticblockcrimson, "plasticblockcrimson");
		GameRegistry.registerBlock(plasticblockgainsboro, "plasticblockgainsboro");
		GameRegistry.registerBlock(plasticblockplum, "plasticblockplum");
		GameRegistry.registerBlock(plasticblockburlywood, "plasticblockburlywood");
		GameRegistry.registerBlock(plasticblocklightcyan, "plasticblocklightcyan");
		GameRegistry.registerBlock(plasticblocklavender, "plasticblocklavender");
		GameRegistry.registerBlock(plasticblockdarksalmon, "plasticblockdarksalmon");
		GameRegistry.registerBlock(plasticblockviolet, "plasticblockviolet");
		GameRegistry.registerBlock(plasticblockpalegoldenrod, "plasticblockpalegoldenrod");
		GameRegistry.registerBlock(plasticblocklightcoral, "plasticblocklightcoral");
		GameRegistry.registerBlock(plasticblockkhaki, "plasticblockkhaki");
		GameRegistry.registerBlock(plasticblockaliceblue, "plasticblockaliceblue");
		GameRegistry.registerBlock(plasticblockhoneydew, "plasticblockhoneydew");
		GameRegistry.registerBlock(plasticblockazure, "plasticblockazure");
		GameRegistry.registerBlock(plasticblocksandybrown, "plasticblocksandybrown");
		GameRegistry.registerBlock(plasticblockwheat, "plasticblockwheat");
		GameRegistry.registerBlock(plasticblockbeige, "plasticblockbeige");
		GameRegistry.registerBlock(plasticblockwhitesmoke, "plasticblockwhitesmoke");
		GameRegistry.registerBlock(plasticblockmintcream, "plasticblockmintcream");
		GameRegistry.registerBlock(plasticblockghostwhite, "plasticblockghostwhite");
		GameRegistry.registerBlock(plasticblocksalmon, "plasticblocksalmon");
		GameRegistry.registerBlock(plasticblockantiquewhite, "plasticblockantiquewhite");
		GameRegistry.registerBlock(plasticblocklinen, "plasticblocklinen");
		GameRegistry.registerBlock(plasticblocklightgoldenrodyellow, "plasticblocklightgoldenrodyellow");
		GameRegistry.registerBlock(plasticblockoldlace, "plasticblockoldlace");
		GameRegistry.registerBlock(plasticblockred, "plasticblockred");
		GameRegistry.registerBlock(plasticblockfuchsia, "plasticblockfuchsia");
		GameRegistry.registerBlock(plasticblockmagenta, "plasticblockmagenta");
		GameRegistry.registerBlock(plasticblockdeeppink, "plasticblockdeeppink");
		GameRegistry.registerBlock(plasticblockorangered, "plasticblockorangered");
		GameRegistry.registerBlock(plasticblocktomato, "plasticblocktomato");
		GameRegistry.registerBlock(plasticblockhotpink, "plasticblockhotpink");
		GameRegistry.registerBlock(plasticblockcoral, "plasticblockcoral");
		GameRegistry.registerBlock(plasticblockdarkorange, "plasticblockdarkorange");
		GameRegistry.registerBlock(plasticblocklightsalmon, "plasticblocklightsalmon");
		GameRegistry.registerBlock(plasticblockorange, "plasticblockorange");
		GameRegistry.registerBlock(plasticblocklightpink, "plasticblocklightpink");
		GameRegistry.registerBlock(plasticblockpink, "plasticblockpink");
		GameRegistry.registerBlock(plasticblockgold, "plasticblockgold");
		GameRegistry.registerBlock(plasticblockpeachpuff, "plasticblockpeachpuff");
		GameRegistry.registerBlock(plasticblocknavajowhite, "plasticblocknavajowhite");
		GameRegistry.registerBlock(plasticblockmoccasin, "plasticblockmoccasin");
		GameRegistry.registerBlock(plasticblockbisque, "plasticblockbisque");
		GameRegistry.registerBlock(plasticblockmistyrose, "plasticblockmistyrose");
		GameRegistry.registerBlock(plasticblockblanchedalmond, "plasticblockblanchedalmond");
		GameRegistry.registerBlock(plasticblockpapayawhip, "plasticblockpapayawhip");
		GameRegistry.registerBlock(plasticblocklavenderblush, "plasticblocklavenderblush");
		GameRegistry.registerBlock(plasticblockseashell, "plasticblockseashell");
		GameRegistry.registerBlock(plasticblockcornsilk, "plasticblockcornsilk");
		GameRegistry.registerBlock(plasticblocklemonchiffon, "plasticblocklemonchiffon");
		GameRegistry.registerBlock(plasticblockfloralwhite, "plasticblockfloralwhite");
		GameRegistry.registerBlock(plasticblocksnow, "plasticblocksnow");
		GameRegistry.registerBlock(plasticblockyellow, "plasticblockyellow");
		GameRegistry.registerBlock(plasticblocklightyellow, "plasticblocklightyellow");
		GameRegistry.registerBlock(plasticblockivory, "plasticblockivory");
		GameRegistry.registerBlock(plasticblockwhite, "plasticblockwhite");
		//装飾ブロック2
		plasticblockaliceblueCarpet=new PlasticblockaliceblueCarpet().setBlockName("plasticblockaliceblueCarpet");
		plasticblockantiquewhiteCarpet=new PlasticblockantiquewhiteCarpet().setBlockName("plasticblockantiquewhiteCarpet");
		plasticblockaquaCarpet=new PlasticblockaquaCarpet().setBlockName("plasticblockaquaCarpet");
		plasticblockaquamarineCarpet=new PlasticblockaquamarineCarpet().setBlockName("plasticblockaquamarineCarpet");
		plasticblockazureCarpet=new PlasticblockazureCarpet().setBlockName("plasticblockazureCarpet");
		plasticblockbeigeCarpet=new PlasticblockbeigeCarpet().setBlockName("plasticblockbeigeCarpet");
		plasticblockbisqueCarpet=new PlasticblockbisqueCarpet().setBlockName("plasticblockbisqueCarpet");
		plasticblockblackCarpet=new PlasticblockblackCarpet().setBlockName("plasticblockblackCarpet");
		plasticblockblanchedalmondCarpet=new PlasticblockblanchedalmondCarpet().setBlockName("plasticblockblanchedalmondCarpet");
		plasticblockblueCarpet=new PlasticblockblueCarpet().setBlockName("plasticblockblueCarpet");
		plasticblockbluevioletCarpet=new PlasticblockbluevioletCarpet().setBlockName("plasticblockbluevioletCarpet");
		plasticblockbrownCarpet=new PlasticblockbrownCarpet().setBlockName("plasticblockbrownCarpet");
		plasticblockburlywoodCarpet=new PlasticblockburlywoodCarpet().setBlockName("plasticblockburlywoodCarpet");
		plasticblockcadetblueCarpet=new PlasticblockcadetblueCarpet().setBlockName("plasticblockcadetblueCarpet");
		plasticblockchartreuseCarpet=new PlasticblockchartreuseCarpet().setBlockName("plasticblockchartreuseCarpet");
		plasticblockchocolateCarpet=new PlasticblockchocolateCarpet().setBlockName("plasticblockchocolateCarpet");
		plasticblockcoralCarpet=new PlasticblockcoralCarpet().setBlockName("plasticblockcoralCarpet");
		plasticblockcornflowerblueCarpet=new PlasticblockcornflowerblueCarpet().setBlockName("plasticblockcornflowerblueCarpet");
		plasticblockcornsilkCarpet=new PlasticblockcornsilkCarpet().setBlockName("plasticblockcornsilkCarpet");
		plasticblockcrimsonCarpet=new PlasticblockcrimsonCarpet().setBlockName("plasticblockcrimsonCarpet");
		plasticblockcyanCarpet=new PlasticblockcyanCarpet().setBlockName("plasticblockcyanCarpet");
		plasticblockdarkblueCarpet=new PlasticblockdarkblueCarpet().setBlockName("plasticblockdarkblueCarpet");
		plasticblockdarkcyanCarpet=new PlasticblockdarkcyanCarpet().setBlockName("plasticblockdarkcyanCarpet");
		plasticblockdarkgoldenrodCarpet=new PlasticblockdarkgoldenrodCarpet().setBlockName("plasticblockdarkgoldenrodCarpet");
		plasticblockdarkgrayCarpet=new PlasticblockdarkgrayCarpet().setBlockName("plasticblockdarkgrayCarpet");
		plasticblockdarkgreenCarpet=new PlasticblockdarkgreenCarpet().setBlockName("plasticblockdarkgreenCarpet");
		plasticblockdarkkhakiCarpet=new PlasticblockdarkkhakiCarpet().setBlockName("plasticblockdarkkhakiCarpet");
		plasticblockdarkmagentaCarpet=new PlasticblockdarkmagentaCarpet().setBlockName("plasticblockdarkmagentaCarpet");
		plasticblockdarkolivegreenCarpet=new PlasticblockdarkolivegreenCarpet().setBlockName("plasticblockdarkolivegreenCarpet");
		plasticblockdarkorangeCarpet=new PlasticblockdarkorangeCarpet().setBlockName("plasticblockdarkorangeCarpet");
		plasticblockdarkorchidCarpet=new PlasticblockdarkorchidCarpet().setBlockName("plasticblockdarkorchidCarpet");
		plasticblockdarkredCarpet=new PlasticblockdarkredCarpet().setBlockName("plasticblockdarkredCarpet");
		plasticblockdarksalmonCarpet=new PlasticblockdarksalmonCarpet().setBlockName("plasticblockdarksalmonCarpet");
		plasticblockdarkseagreenCarpet=new PlasticblockdarkseagreenCarpet().setBlockName("plasticblockdarkseagreenCarpet");
		plasticblockdarkslateblueCarpet=new PlasticblockdarkslateblueCarpet().setBlockName("plasticblockdarkslateblueCarpet");
		plasticblockdarkslategrayCarpet=new PlasticblockdarkslategrayCarpet().setBlockName("plasticblockdarkslategrayCarpet");
		plasticblockdarkturquoiseCarpet=new PlasticblockdarkturquoiseCarpet().setBlockName("plasticblockdarkturquoiseCarpet");
		plasticblockdarkvioletCarpet=new PlasticblockdarkvioletCarpet().setBlockName("plasticblockdarkvioletCarpet");
		plasticblockdeeppinkCarpet=new PlasticblockdeeppinkCarpet().setBlockName("plasticblockdeeppinkCarpet");
		plasticblockdeepskyblueCarpet=new PlasticblockdeepskyblueCarpet().setBlockName("plasticblockdeepskyblueCarpet");
		plasticblockdimgrayCarpet=new PlasticblockdimgrayCarpet().setBlockName("plasticblockdimgrayCarpet");
		plasticblockdodgerblueCarpet=new PlasticblockdodgerblueCarpet().setBlockName("plasticblockdodgerblueCarpet");
		plasticblockfirebrickCarpet=new PlasticblockfirebrickCarpet().setBlockName("plasticblockfirebrickCarpet");
		plasticblockfloralwhiteCarpet=new PlasticblockfloralwhiteCarpet().setBlockName("plasticblockfloralwhiteCarpet");
		plasticblockforestgreenCarpet=new PlasticblockforestgreenCarpet().setBlockName("plasticblockforestgreenCarpet");
		plasticblockfuchsiaCarpet=new PlasticblockfuchsiaCarpet().setBlockName("plasticblockfuchsiaCarpet");
		plasticblockgainsboroCarpet=new PlasticblockgainsboroCarpet().setBlockName("plasticblockgainsboroCarpet");
		plasticblockghostwhiteCarpet=new PlasticblockghostwhiteCarpet().setBlockName("plasticblockghostwhiteCarpet");
		plasticblockgoldCarpet=new PlasticblockgoldCarpet().setBlockName("plasticblockgoldCarpet");
		plasticblockgoldenrodCarpet=new PlasticblockgoldenrodCarpet().setBlockName("plasticblockgoldenrodCarpet");
		plasticblockgrayCarpet=new PlasticblockgrayCarpet().setBlockName("plasticblockgrayCarpet");
		plasticblockgreenCarpet=new PlasticblockgreenCarpet().setBlockName("plasticblockgreenCarpet");
		plasticblockgreenyellowCarpet=new PlasticblockgreenyellowCarpet().setBlockName("plasticblockgreenyellowCarpet");
		plasticblockhoneydewCarpet=new PlasticblockhoneydewCarpet().setBlockName("plasticblockhoneydewCarpet");
		plasticblockhotpinkCarpet=new PlasticblockhotpinkCarpet().setBlockName("plasticblockhotpinkCarpet");
		plasticblockindianredCarpet=new PlasticblockindianredCarpet().setBlockName("plasticblockindianredCarpet");
		plasticblockindigoCarpet=new PlasticblockindigoCarpet().setBlockName("plasticblockindigoCarpet");
		plasticblockivoryCarpet=new PlasticblockivoryCarpet().setBlockName("plasticblockivoryCarpet");
		plasticblockkhakiCarpet=new PlasticblockkhakiCarpet().setBlockName("plasticblockkhakiCarpet");
		plasticblocklavenderCarpet=new PlasticblocklavenderCarpet().setBlockName("plasticblocklavenderCarpet");
		plasticblocklavenderblushCarpet=new PlasticblocklavenderblushCarpet().setBlockName("plasticblocklavenderblushCarpet");
		plasticblocklawngreenCarpet=new PlasticblocklawngreenCarpet().setBlockName("plasticblocklawngreenCarpet");
		plasticblocklemonchiffonCarpet=new PlasticblocklemonchiffonCarpet().setBlockName("plasticblocklemonchiffonCarpet");
		plasticblocklightblueCarpet=new PlasticblocklightblueCarpet().setBlockName("plasticblocklightblueCarpet");
		plasticblocklightcoralCarpet=new PlasticblocklightcoralCarpet().setBlockName("plasticblocklightcoralCarpet");
		plasticblocklightcyanCarpet=new PlasticblocklightcyanCarpet().setBlockName("plasticblocklightcyanCarpet");
		plasticblocklightgoldenrodyellowCarpet=new PlasticblocklightgoldenrodyellowCarpet().setBlockName("plasticblocklightgoldenrodyellowCarpet");
		plasticblocklightgreenCarpet=new PlasticblocklightgreenCarpet().setBlockName("plasticblocklightgreenCarpet");
		plasticblocklightgreyCarpet=new PlasticblocklightgreyCarpet().setBlockName("plasticblocklightgreyCarpet");
		plasticblocklightpinkCarpet=new PlasticblocklightpinkCarpet().setBlockName("plasticblocklightpinkCarpet");
		plasticblocklightsalmonCarpet=new PlasticblocklightsalmonCarpet().setBlockName("plasticblocklightsalmonCarpet");
		plasticblocklightseagreenCarpet=new PlasticblocklightseagreenCarpet().setBlockName("plasticblocklightseagreenCarpet");
		plasticblocklightskyblueCarpet=new PlasticblocklightskyblueCarpet().setBlockName("plasticblocklightskyblueCarpet");
		plasticblocklightslategrayCarpet=new PlasticblocklightslategrayCarpet().setBlockName("plasticblocklightslategrayCarpet");
		plasticblocklightsteelblueCarpet=new PlasticblocklightsteelblueCarpet().setBlockName("plasticblocklightsteelblueCarpet");
		plasticblocklightyellowCarpet=new PlasticblocklightyellowCarpet().setBlockName("plasticblocklightyellowCarpet");
		plasticblocklimeCarpet=new PlasticblocklimeCarpet().setBlockName("plasticblocklimeCarpet");
		plasticblocklimegreenCarpet=new PlasticblocklimegreenCarpet().setBlockName("plasticblocklimegreenCarpet");
		plasticblocklinenCarpet=new PlasticblocklinenCarpet().setBlockName("plasticblocklinenCarpet");
		plasticblockmagentaCarpet=new PlasticblockmagentaCarpet().setBlockName("plasticblockmagentaCarpet");
		plasticblockmaroonCarpet=new PlasticblockmaroonCarpet().setBlockName("plasticblockmaroonCarpet");
		plasticblockmediumaquamarineCarpet=new PlasticblockmediumaquamarineCarpet().setBlockName("plasticblockmediumaquamarineCarpet");
		plasticblockmediumblueCarpet=new PlasticblockmediumblueCarpet().setBlockName("plasticblockmediumblueCarpet");
		plasticblockmediumorchidCarpet=new PlasticblockmediumorchidCarpet().setBlockName("plasticblockmediumorchidCarpet");
		plasticblockmediumpurpleCarpet=new PlasticblockmediumpurpleCarpet().setBlockName("plasticblockmediumpurpleCarpet");
		plasticblockmediumseagreenCarpet=new PlasticblockmediumseagreenCarpet().setBlockName("plasticblockmediumseagreenCarpet");
		plasticblockmediumslateblueCarpet=new PlasticblockmediumslateblueCarpet().setBlockName("plasticblockmediumslateblueCarpet");
		plasticblockmediumspringgreenCarpet=new PlasticblockmediumspringgreenCarpet().setBlockName("plasticblockmediumspringgreenCarpet");
		plasticblockmediumturquoiseCarpet=new PlasticblockmediumturquoiseCarpet().setBlockName("plasticblockmediumturquoiseCarpet");
		plasticblockmediumvioletredCarpet=new PlasticblockmediumvioletredCarpet().setBlockName("plasticblockmediumvioletredCarpet");
		plasticblockmidnightblueCarpet=new PlasticblockmidnightblueCarpet().setBlockName("plasticblockmidnightblueCarpet");
		plasticblockmintcreamCarpet=new PlasticblockmintcreamCarpet().setBlockName("plasticblockmintcreamCarpet");
		plasticblockmistyroseCarpet=new PlasticblockmistyroseCarpet().setBlockName("plasticblockmistyroseCarpet");
		plasticblockmoccasinCarpet=new PlasticblockmoccasinCarpet().setBlockName("plasticblockmoccasinCarpet");
		plasticblocknavajowhiteCarpet=new PlasticblocknavajowhiteCarpet().setBlockName("plasticblocknavajowhiteCarpet");
		plasticblocknavyCarpet=new PlasticblocknavyCarpet().setBlockName("plasticblocknavyCarpet");
		plasticblockoldlaceCarpet=new PlasticblockoldlaceCarpet().setBlockName("plasticblockoldlaceCarpet");
		plasticblockoliveCarpet=new PlasticblockoliveCarpet().setBlockName("plasticblockoliveCarpet");
		plasticblockolivedrabCarpet=new PlasticblockolivedrabCarpet().setBlockName("plasticblockolivedrabCarpet");
		plasticblockorangeCarpet=new PlasticblockorangeCarpet().setBlockName("plasticblockorangeCarpet");
		plasticblockorangeredCarpet=new PlasticblockorangeredCarpet().setBlockName("plasticblockorangeredCarpet");
		plasticblockorchidCarpet=new PlasticblockorchidCarpet().setBlockName("plasticblockorchidCarpet");
		plasticblockpalegoldenrodCarpet=new PlasticblockpalegoldenrodCarpet().setBlockName("plasticblockpalegoldenrodCarpet");
		plasticblockpalegreenCarpet=new PlasticblockpalegreenCarpet().setBlockName("plasticblockpalegreenCarpet");
		plasticblockpaleturquoiseCarpet=new PlasticblockpaleturquoiseCarpet().setBlockName("plasticblockpaleturquoiseCarpet");
		plasticblockpalevioletredCarpet=new PlasticblockpalevioletredCarpet().setBlockName("plasticblockpalevioletredCarpet");
		plasticblockpapayawhipCarpet=new PlasticblockpapayawhipCarpet().setBlockName("plasticblockpapayawhipCarpet");
		plasticblockpeachpuffCarpet=new PlasticblockpeachpuffCarpet().setBlockName("plasticblockpeachpuffCarpet");
		plasticblockperuCarpet=new PlasticblockperuCarpet().setBlockName("plasticblockperuCarpet");
		plasticblockpinkCarpet=new PlasticblockpinkCarpet().setBlockName("plasticblockpinkCarpet");
		plasticblockplumCarpet=new PlasticblockplumCarpet().setBlockName("plasticblockplumCarpet");
		plasticblockpowderblueCarpet=new PlasticblockpowderblueCarpet().setBlockName("plasticblockpowderblueCarpet");
		plasticblockpurpleCarpet=new PlasticblockpurpleCarpet().setBlockName("plasticblockpurpleCarpet");
		plasticblockredCarpet=new PlasticblockredCarpet().setBlockName("plasticblockredCarpet");
		plasticblockrosybrownCarpet=new PlasticblockrosybrownCarpet().setBlockName("plasticblockrosybrownCarpet");
		plasticblockroyalblueCarpet=new PlasticblockroyalblueCarpet().setBlockName("plasticblockroyalblueCarpet");
		plasticblocksaddlebrownCarpet=new PlasticblocksaddlebrownCarpet().setBlockName("plasticblocksaddlebrownCarpet");
		plasticblocksalmonCarpet=new PlasticblocksalmonCarpet().setBlockName("plasticblocksalmonCarpet");
		plasticblocksandybrownCarpet=new PlasticblocksandybrownCarpet().setBlockName("plasticblocksandybrownCarpet");
		plasticblockseagreenCarpet=new PlasticblockseagreenCarpet().setBlockName("plasticblockseagreenCarpet");
		plasticblockseashellCarpet=new PlasticblockseashellCarpet().setBlockName("plasticblockseashellCarpet");
		plasticblocksiennaCarpet=new PlasticblocksiennaCarpet().setBlockName("plasticblocksiennaCarpet");
		plasticblocksilverCarpet=new PlasticblocksilverCarpet().setBlockName("plasticblocksilverCarpet");
		plasticblockskyblueCarpet=new PlasticblockskyblueCarpet().setBlockName("plasticblockskyblueCarpet");
		plasticblockslateblueCarpet=new PlasticblockslateblueCarpet().setBlockName("plasticblockslateblueCarpet");
		plasticblockslategrayCarpet=new PlasticblockslategrayCarpet().setBlockName("plasticblockslategrayCarpet");
		plasticblocksnowCarpet=new PlasticblocksnowCarpet().setBlockName("plasticblocksnowCarpet");
		plasticblockspringgreenCarpet=new PlasticblockspringgreenCarpet().setBlockName("plasticblockspringgreenCarpet");
		plasticblocksteelblueCarpet=new PlasticblocksteelblueCarpet().setBlockName("plasticblocksteelblueCarpet");
		plasticblocktanCarpet=new PlasticblocktanCarpet().setBlockName("plasticblocktanCarpet");
		plasticblocktealCarpet=new PlasticblocktealCarpet().setBlockName("plasticblocktealCarpet");
		plasticblockthistleCarpet=new PlasticblockthistleCarpet().setBlockName("plasticblockthistleCarpet");
		plasticblocktomatoCarpet=new PlasticblocktomatoCarpet().setBlockName("plasticblocktomatoCarpet");
		plasticblockturquoiseCarpet=new PlasticblockturquoiseCarpet().setBlockName("plasticblockturquoiseCarpet");
		plasticblockvioletCarpet=new PlasticblockvioletCarpet().setBlockName("plasticblockvioletCarpet");
		plasticblockwheatCarpet=new PlasticblockwheatCarpet().setBlockName("plasticblockwheatCarpet");
		plasticblockwhiteCarpet=new PlasticblockwhiteCarpet().setBlockName("plasticblockwhiteCarpet");
		plasticblockwhitesmokeCarpet=new PlasticblockwhitesmokeCarpet().setBlockName("plasticblockwhitesmokeCarpet");
		plasticblockyellowCarpet=new PlasticblockyellowCarpet().setBlockName("plasticblockyellowCarpet");
		plasticblockyellowgreenCarpet=new PlasticblockyellowgreenCarpet().setBlockName("plasticblockyellowgreenCarpet");
		GameRegistry.registerBlock(plasticblockaliceblueCarpet,"plasticblockaliceblueCarpet");
		GameRegistry.registerBlock(plasticblockantiquewhiteCarpet,"plasticblockantiquewhiteCarpet");
		GameRegistry.registerBlock(plasticblockaquaCarpet,"plasticblockaquaCarpet");
		GameRegistry.registerBlock(plasticblockaquamarineCarpet,"plasticblockaquamarineCarpet");
		GameRegistry.registerBlock(plasticblockazureCarpet,"plasticblockazureCarpet");
		GameRegistry.registerBlock(plasticblockbeigeCarpet,"plasticblockbeigeCarpet");
		GameRegistry.registerBlock(plasticblockbisqueCarpet,"plasticblockbisqueCarpet");
		GameRegistry.registerBlock(plasticblockblackCarpet,"plasticblockblackCarpet");
		GameRegistry.registerBlock(plasticblockblanchedalmondCarpet,"plasticblockblanchedalmondCarpet");
		GameRegistry.registerBlock(plasticblockblueCarpet,"plasticblockblueCarpet");
		GameRegistry.registerBlock(plasticblockbluevioletCarpet,"plasticblockbluevioletCarpet");
		GameRegistry.registerBlock(plasticblockbrownCarpet,"plasticblockbrownCarpet");
		GameRegistry.registerBlock(plasticblockburlywoodCarpet,"plasticblockburlywoodCarpet");
		GameRegistry.registerBlock(plasticblockcadetblueCarpet,"plasticblockcadetblueCarpet");
		GameRegistry.registerBlock(plasticblockchartreuseCarpet,"plasticblockchartreuseCarpet");
		GameRegistry.registerBlock(plasticblockchocolateCarpet,"plasticblockchocolateCarpet");
		GameRegistry.registerBlock(plasticblockcoralCarpet,"plasticblockcoralCarpet");
		GameRegistry.registerBlock(plasticblockcornflowerblueCarpet,"plasticblockcornflowerblueCarpet");
		GameRegistry.registerBlock(plasticblockcornsilkCarpet,"plasticblockcornsilkCarpet");
		GameRegistry.registerBlock(plasticblockcrimsonCarpet,"plasticblockcrimsonCarpet");
		GameRegistry.registerBlock(plasticblockcyanCarpet,"plasticblockcyanCarpet");
		GameRegistry.registerBlock(plasticblockdarkblueCarpet,"plasticblockdarkblueCarpet");
		GameRegistry.registerBlock(plasticblockdarkcyanCarpet,"plasticblockdarkcyanCarpet");
		GameRegistry.registerBlock(plasticblockdarkgoldenrodCarpet,"plasticblockdarkgoldenrodCarpet");
		GameRegistry.registerBlock(plasticblockdarkgrayCarpet,"plasticblockdarkgrayCarpet");
		GameRegistry.registerBlock(plasticblockdarkgreenCarpet,"plasticblockdarkgreenCarpet");
		GameRegistry.registerBlock(plasticblockdarkkhakiCarpet,"plasticblockdarkkhakiCarpet");
		GameRegistry.registerBlock(plasticblockdarkmagentaCarpet,"plasticblockdarkmagentaCarpet");
		GameRegistry.registerBlock(plasticblockdarkolivegreenCarpet,"plasticblockdarkolivegreenCarpet");
		GameRegistry.registerBlock(plasticblockdarkorangeCarpet,"plasticblockdarkorangeCarpet");
		GameRegistry.registerBlock(plasticblockdarkorchidCarpet,"plasticblockdarkorchidCarpet");
		GameRegistry.registerBlock(plasticblockdarkredCarpet,"plasticblockdarkredCarpet");
		GameRegistry.registerBlock(plasticblockdarksalmonCarpet,"plasticblockdarksalmonCarpet");
		GameRegistry.registerBlock(plasticblockdarkseagreenCarpet,"plasticblockdarkseagreenCarpet");
		GameRegistry.registerBlock(plasticblockdarkslateblueCarpet,"plasticblockdarkslateblueCarpet");
		GameRegistry.registerBlock(plasticblockdarkslategrayCarpet,"plasticblockdarkslategrayCarpet");
		GameRegistry.registerBlock(plasticblockdarkturquoiseCarpet,"plasticblockdarkturquoiseCarpet");
		GameRegistry.registerBlock(plasticblockdarkvioletCarpet,"plasticblockdarkvioletCarpet");
		GameRegistry.registerBlock(plasticblockdeeppinkCarpet,"plasticblockdeeppinkCarpet");
		GameRegistry.registerBlock(plasticblockdeepskyblueCarpet,"plasticblockdeepskyblueCarpet");
		GameRegistry.registerBlock(plasticblockdimgrayCarpet,"plasticblockdimgrayCarpet");
		GameRegistry.registerBlock(plasticblockdodgerblueCarpet,"plasticblockdodgerblueCarpet");
		GameRegistry.registerBlock(plasticblockfirebrickCarpet,"plasticblockfirebrickCarpet");
		GameRegistry.registerBlock(plasticblockfloralwhiteCarpet,"plasticblockfloralwhiteCarpet");
		GameRegistry.registerBlock(plasticblockforestgreenCarpet,"plasticblockforestgreenCarpet");
		GameRegistry.registerBlock(plasticblockfuchsiaCarpet,"plasticblockfuchsiaCarpet");
		GameRegistry.registerBlock(plasticblockgainsboroCarpet,"plasticblockgainsboroCarpet");
		GameRegistry.registerBlock(plasticblockghostwhiteCarpet,"plasticblockghostwhiteCarpet");
		GameRegistry.registerBlock(plasticblockgoldCarpet,"plasticblockgoldCarpet");
		GameRegistry.registerBlock(plasticblockgoldenrodCarpet,"plasticblockgoldenrodCarpet");
		GameRegistry.registerBlock(plasticblockgrayCarpet,"plasticblockgrayCarpet");
		GameRegistry.registerBlock(plasticblockgreenCarpet,"plasticblockgreenCarpet");
		GameRegistry.registerBlock(plasticblockgreenyellowCarpet,"plasticblockgreenyellowCarpet");
		GameRegistry.registerBlock(plasticblockhoneydewCarpet,"plasticblockhoneydewCarpet");
		GameRegistry.registerBlock(plasticblockhotpinkCarpet,"plasticblockhotpinkCarpet");
		GameRegistry.registerBlock(plasticblockindianredCarpet,"plasticblockindianredCarpet");
		GameRegistry.registerBlock(plasticblockindigoCarpet,"plasticblockindigoCarpet");
		GameRegistry.registerBlock(plasticblockivoryCarpet,"plasticblockivoryCarpet");
		GameRegistry.registerBlock(plasticblockkhakiCarpet,"plasticblockkhakiCarpet");
		GameRegistry.registerBlock(plasticblocklavenderCarpet,"plasticblocklavenderCarpet");
		GameRegistry.registerBlock(plasticblocklavenderblushCarpet,"plasticblocklavenderblushCarpet");
		GameRegistry.registerBlock(plasticblocklawngreenCarpet,"plasticblocklawngreenCarpet");
		GameRegistry.registerBlock(plasticblocklemonchiffonCarpet,"plasticblocklemonchiffonCarpet");
		GameRegistry.registerBlock(plasticblocklightblueCarpet,"plasticblocklightblueCarpet");
		GameRegistry.registerBlock(plasticblocklightcoralCarpet,"plasticblocklightcoralCarpet");
		GameRegistry.registerBlock(plasticblocklightcyanCarpet,"plasticblocklightcyanCarpet");
		GameRegistry.registerBlock(plasticblocklightgoldenrodyellowCarpet,"plasticblocklightgoldenrodyellowCarpet");
		GameRegistry.registerBlock(plasticblocklightgreenCarpet,"plasticblocklightgreenCarpet");
		GameRegistry.registerBlock(plasticblocklightgreyCarpet,"plasticblocklightgreyCarpet");
		GameRegistry.registerBlock(plasticblocklightpinkCarpet,"plasticblocklightpinkCarpet");
		GameRegistry.registerBlock(plasticblocklightsalmonCarpet,"plasticblocklightsalmonCarpet");
		GameRegistry.registerBlock(plasticblocklightseagreenCarpet,"plasticblocklightseagreenCarpet");
		GameRegistry.registerBlock(plasticblocklightskyblueCarpet,"plasticblocklightskyblueCarpet");
		GameRegistry.registerBlock(plasticblocklightslategrayCarpet,"plasticblocklightslategrayCarpet");
		GameRegistry.registerBlock(plasticblocklightsteelblueCarpet,"plasticblocklightsteelblueCarpet");
		GameRegistry.registerBlock(plasticblocklightyellowCarpet,"plasticblocklightyellowCarpet");
		GameRegistry.registerBlock(plasticblocklimeCarpet,"plasticblocklimeCarpet");
		GameRegistry.registerBlock(plasticblocklimegreenCarpet,"plasticblocklimegreenCarpet");
		GameRegistry.registerBlock(plasticblocklinenCarpet,"plasticblocklinenCarpet");
		GameRegistry.registerBlock(plasticblockmagentaCarpet,"plasticblockmagentaCarpet");
		GameRegistry.registerBlock(plasticblockmaroonCarpet,"plasticblockmaroonCarpet");
		GameRegistry.registerBlock(plasticblockmediumaquamarineCarpet,"plasticblockmediumaquamarineCarpet");
		GameRegistry.registerBlock(plasticblockmediumblueCarpet,"plasticblockmediumblueCarpet");
		GameRegistry.registerBlock(plasticblockmediumorchidCarpet,"plasticblockmediumorchidCarpet");
		GameRegistry.registerBlock(plasticblockmediumpurpleCarpet,"plasticblockmediumpurpleCarpet");
		GameRegistry.registerBlock(plasticblockmediumseagreenCarpet,"plasticblockmediumseagreenCarpet");
		GameRegistry.registerBlock(plasticblockmediumslateblueCarpet,"plasticblockmediumslateblueCarpet");
		GameRegistry.registerBlock(plasticblockmediumspringgreenCarpet,"plasticblockmediumspringgreenCarpet");
		GameRegistry.registerBlock(plasticblockmediumturquoiseCarpet,"plasticblockmediumturquoiseCarpet");
		GameRegistry.registerBlock(plasticblockmediumvioletredCarpet,"plasticblockmediumvioletredCarpet");
		GameRegistry.registerBlock(plasticblockmidnightblueCarpet,"plasticblockmidnightblueCarpet");
		GameRegistry.registerBlock(plasticblockmintcreamCarpet,"plasticblockmintcreamCarpet");
		GameRegistry.registerBlock(plasticblockmistyroseCarpet,"plasticblockmistyroseCarpet");
		GameRegistry.registerBlock(plasticblockmoccasinCarpet,"plasticblockmoccasinCarpet");
		GameRegistry.registerBlock(plasticblocknavajowhiteCarpet,"plasticblocknavajowhiteCarpet");
		GameRegistry.registerBlock(plasticblocknavyCarpet,"plasticblocknavyCarpet");
		GameRegistry.registerBlock(plasticblockoldlaceCarpet,"plasticblockoldlaceCarpet");
		GameRegistry.registerBlock(plasticblockoliveCarpet,"plasticblockoliveCarpet");
		GameRegistry.registerBlock(plasticblockolivedrabCarpet,"plasticblockolivedrabCarpet");
		GameRegistry.registerBlock(plasticblockorangeCarpet,"plasticblockorangeCarpet");
		GameRegistry.registerBlock(plasticblockorangeredCarpet,"plasticblockorangeredCarpet");
		GameRegistry.registerBlock(plasticblockorchidCarpet,"plasticblockorchidCarpet");
		GameRegistry.registerBlock(plasticblockpalegoldenrodCarpet,"plasticblockpalegoldenrodCarpet");
		GameRegistry.registerBlock(plasticblockpalegreenCarpet,"plasticblockpalegreenCarpet");
		GameRegistry.registerBlock(plasticblockpaleturquoiseCarpet,"plasticblockpaleturquoiseCarpet");
		GameRegistry.registerBlock(plasticblockpalevioletredCarpet,"plasticblockpalevioletredCarpet");
		GameRegistry.registerBlock(plasticblockpapayawhipCarpet,"plasticblockpapayawhipCarpet");
		GameRegistry.registerBlock(plasticblockpeachpuffCarpet,"plasticblockpeachpuffCarpet");
		GameRegistry.registerBlock(plasticblockperuCarpet,"plasticblockperuCarpet");
		GameRegistry.registerBlock(plasticblockpinkCarpet,"plasticblockpinkCarpet");
		GameRegistry.registerBlock(plasticblockplumCarpet,"plasticblockplumCarpet");
		GameRegistry.registerBlock(plasticblockpowderblueCarpet,"plasticblockpowderblueCarpet");
		GameRegistry.registerBlock(plasticblockpurpleCarpet,"plasticblockpurpleCarpet");
		GameRegistry.registerBlock(plasticblockredCarpet,"plasticblockredCarpet");
		GameRegistry.registerBlock(plasticblockrosybrownCarpet,"plasticblockrosybrownCarpet");
		GameRegistry.registerBlock(plasticblockroyalblueCarpet,"plasticblockroyalblueCarpet");
		GameRegistry.registerBlock(plasticblocksaddlebrownCarpet,"plasticblocksaddlebrownCarpet");
		GameRegistry.registerBlock(plasticblocksalmonCarpet,"plasticblocksalmonCarpet");
		GameRegistry.registerBlock(plasticblocksandybrownCarpet,"plasticblocksandybrownCarpet");
		GameRegistry.registerBlock(plasticblockseagreenCarpet,"plasticblockseagreenCarpet");
		GameRegistry.registerBlock(plasticblockseashellCarpet,"plasticblockseashellCarpet");
		GameRegistry.registerBlock(plasticblocksiennaCarpet,"plasticblocksiennaCarpet");
		GameRegistry.registerBlock(plasticblocksilverCarpet,"plasticblocksilverCarpet");
		GameRegistry.registerBlock(plasticblockskyblueCarpet,"plasticblockskyblueCarpet");
		GameRegistry.registerBlock(plasticblockslateblueCarpet,"plasticblockslateblueCarpet");
		GameRegistry.registerBlock(plasticblockslategrayCarpet,"plasticblockslategrayCarpet");
		GameRegistry.registerBlock(plasticblocksnowCarpet,"plasticblocksnowCarpet");
		GameRegistry.registerBlock(plasticblockspringgreenCarpet,"plasticblockspringgreenCarpet");
		GameRegistry.registerBlock(plasticblocksteelblueCarpet,"plasticblocksteelblueCarpet");
		GameRegistry.registerBlock(plasticblocktanCarpet,"plasticblocktanCarpet");
		GameRegistry.registerBlock(plasticblocktealCarpet,"plasticblocktealCarpet");
		GameRegistry.registerBlock(plasticblockthistleCarpet,"plasticblockthistleCarpet");
		GameRegistry.registerBlock(plasticblocktomatoCarpet,"plasticblocktomatoCarpet");
		GameRegistry.registerBlock(plasticblockturquoiseCarpet,"plasticblockturquoiseCarpet");
		GameRegistry.registerBlock(plasticblockvioletCarpet,"plasticblockvioletCarpet");
		GameRegistry.registerBlock(plasticblockwheatCarpet,"plasticblockwheatCarpet");
		GameRegistry.registerBlock(plasticblockwhiteCarpet,"plasticblockwhiteCarpet");
		GameRegistry.registerBlock(plasticblockwhitesmokeCarpet,"plasticblockwhitesmokeCarpet");
		GameRegistry.registerBlock(plasticblockyellowCarpet,"plasticblockyellowCarpet");
		GameRegistry.registerBlock(plasticblockyellowgreenCarpet,"plasticblockyellowgreenCarpet");
		//デベロッパーオプション（開発者向けオプション）がONの時に有効なブロック
		if (PlasticConfig.plastic_Developer_options)
		{
			//GUIブロック プラスチックcraftbox
			Plasticblockcraftbox = new PlasticBlockCraftbox(Material.wood)
					.setBlockTextureName("crafting_table_top")
					.setBlockName("plasticblockcraftbox")
					.setCreativeTab(PlasticCore.plasticTab);
			GameRegistry.registerBlock(Plasticblockcraftbox, "plasticblockcraftbox");
			PlasticWirelessTNT  = new PlasticWirelessTNT();
			GameRegistry.registerBlock(PlasticWirelessTNT, "PlasticWirelessTNT");
			TNTController = new TNTController()
					.setUnlocalizedName("TNTController")
					.setTextureName("plasticmod:tntcontroller_1")
					.setCreativeTab(plasticTab)
					.setMaxDamage(128)
					.setMaxStackSize(1);
			GameRegistry.registerItem(TNTController, "TNTController");
			//シート
			PlasticSheet = new PlasticSheet();
			GameRegistry.registerBlock(PlasticSheet, "PlasticSheet");
		}
		//ライト２
		plasticlightup = new PlasticLightUpBlock();
		GameRegistry.registerBlock(plasticlightup, "plasticlightup");
		//ライト
		plasticlight = new PlasticLightDownBlock();
		GameRegistry.registerBlock(plasticlight, "plasticlight");
		//プラスチックブロック追加
		naphthablock = new NaphthaBlock();
		GameRegistry.registerBlock(naphthablock, "naphthablock");
		//強化プラスチックブロックの追加
		rareplasticblock = new RarePlasticBlock();
		GameRegistry.registerBlock(rareplasticblock, "rareplasticblock");
		//ナフサオイル追加
		naphthaoil = new NaphthaOil();
		GameRegistry.registerBlock(naphthaoil, "naphthaoil");
		OreDictionary.registerOre("oreNaphtha", new ItemStack(PlasticCore.naphthaoil, 1, 0));
		//ナフサブロック追加
		plasticblock = new PlasticBlock();
		GameRegistry.registerBlock(plasticblock, "plasticblock");
		//人工ナフサブロックの追加
		rarenaphthablock = new RareNaphthaBlock();
		GameRegistry.registerBlock(rarenaphthablock, "rarenaphthablock");
		//PlasticGlass
		PlasticGlass = new PlasticGlass();
		GameRegistry.registerBlock(PlasticGlass, "PlasticGlass");
		//プラスチックのドア(ブロック)
		PlasticDoorBlock = new PlasticDoorBlock(plasticmate)
				.setHardness(3.0F)
				.setResistance(3.0F)
				.setStepSound(soundTypeMetal)
				.setBlockName("plasticdoorblock")
				.setBlockTextureName("plasticmod:plasticdoorblock");
		GameRegistry.registerBlock(PlasticDoorBlock, "PlasticDoorBlock");
		//強化プラスチックのドア(ブロック)
		RarePlasticDoorBlock= new PlasticDoorBlock(rareplasticmate)
				.setHardness(3.0F)
				.setResistance(5.0F)
				.setStepSound(soundTypeMetal)
				.setBlockName("rareplasticdoorblock")
				.setBlockTextureName("plasticmod:rareplasticdoorblock");
		GameRegistry.registerBlock(RarePlasticDoorBlock, "RarePlasticDoorBlock");
		//プラスチックトラップドア
		PlasticTrapDoor =new PlasticTrapDoor(Material.wood);
		GameRegistry.registerBlock(PlasticTrapDoor, "PlasticTrapDoor");
		//プラスチックトラップドア
		RarePlasticTrapDoor =new RarePlasticTrapDoor(Material.iron);
		GameRegistry.registerBlock(RarePlasticTrapDoor, "RarePlasticTrapDoor");
		//プラスチックフェンス
		PlasticFence =new PlasticFence ("plasticmod:plasticblock",  Material.rock)
				.setHardness(1.5F)
				.setResistance(3.0F)
				.setBlockName("plasticfence");
		GameRegistry.registerBlock(PlasticFence, "PlasticFence");
		//強化プラスチックフェンス
		RarePlasticFence =new PlasticFence ("plasticmod:rareplasticblock",  Material.rock)
				.setHardness(2.5F)
				.setResistance(5F)
				.setBlockName("rareplasticfence");
		GameRegistry.registerBlock(RarePlasticFence, "RarePlasticFence");
		//プラスチックフェンスゲート
		PlasticFenceGate =new PlasticFenceGate ()
				.setHardness(1.5F)
				.setResistance(3.0F)
				.setBlockName("plasticfencegate");
		GameRegistry.registerBlock(PlasticFenceGate, "PlasticFenceGate");
		//強化プラスチックフェンスゲート
		RarePlasticFenceGate =new RarePlasticFenceGate()
				.setHardness(2.5F)
				.setResistance(5F)
				.setBlockName("rareplasticfencegate");
		GameRegistry.registerBlock(RarePlasticFenceGate, "RarePlasticFenceGate");
		//プラスチックの感圧板
		PlasticPressurePlateBlock = new PlasticPressurePlate("plasticmod:plasticblock", Material.rock, PlasticPressurePlate.Sensitivity.animal)
				.setHardness(1.5F)
				.setResistance(3.0F)
				.setStepSound(soundTypePiston)
				.setBlockName("plasticpressureplate");
		GameRegistry.registerBlock(PlasticPressurePlateBlock, "PlasticPressurePlateBlock");
		//強化プラスチックの感圧板
		RarePlasticPressurePlateBlock = new PlasticPressurePlate("plasticmod:rareplasticblock", Material.rock, PlasticPressurePlate.Sensitivity.players)
				.setHardness(2.5F)
				.setResistance(5F)
				.setStepSound(soundTypePiston)
				.setBlockName("rareplasticpressureplate");
		GameRegistry.registerBlock(RarePlasticPressurePlateBlock, "RarePlasticPressurePlateBlock");
		//プラスチックの壁
		PlasticWall = new PlasticWall(PlasticCore.plasticblock)
				.setHardness(1.5F)
				.setResistance(3.0F)
				.setBlockName("plasticwall");
		GameRegistry.registerBlock(PlasticWall, "PlasticWall");
		//強化プラスチックの壁
		RarePlasticWall = new RarePlasticWall(PlasticCore.rareplasticblock)
				.setHardness(2.5F)
				.setResistance(5F)
				.setBlockName("rareplasticwall");
		GameRegistry.registerBlock(RarePlasticWall, "RarePlasticWall");
		//プラスチックの階段
		PlasticBlockStairs = new PlasticStairs(plasticblock, 0)
				.setBlockName("plasticblockstairs");
		GameRegistry.registerBlock(PlasticBlockStairs, "PlasticBlockStairs");
		//強化プラスチックの階段
		RarePlasticBlockStairs = new PlasticStairs(rareplasticblock, 0)
				.setBlockName("rareplasticblockstairs");
		GameRegistry.registerBlock(RarePlasticBlockStairs, "RarePlasticBlockStairs");
		//ナフサの階段
		NaphthaBlockStairs = new PlasticStairs(naphthablock, 0)
				.setBlockName("naphthablockstairs");
		GameRegistry.registerBlock(NaphthaBlockStairs, "NaphthaBlockStairs");
		OreDictionary.registerOre("stair", new ItemStack(PlasticCore.PlasticBlockStairs, 1, 0));
		OreDictionary.registerOre("stair", new ItemStack(PlasticCore.RarePlasticBlockStairs, 1, 0));
		OreDictionary.registerOre("stair", new ItemStack(PlasticCore.NaphthaBlockStairs, 1, 0));
		//BlockNewTNT
		PlasticTNT = new PlasticTNT();
		GameRegistry.registerBlock(PlasticTNT, "PlasticTNT");
		EntityRegistry.registerModEntity(EntityPlasticTNTPrimed.class, "NewTNTPrimed",2 /*EntityRegistry.findGlobalUniqueEntityId()*/, this, 90, 1, true);
		RenderNewTNTPrimed = proxy.getNewRenderType();
		//proxy.registerRenderes();
		//gui関係
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		//弾のエンティティ
		EntityRegistry.instance().registerModEntity(EntityPlasticBullet.class, "PlasticEntityBullet", 1/* Plasticcore. */ , this, 60, 1, true);
		//銃  装填数，射程距離(弓の最大1.0F)，攻撃力補正，攻撃後のWAIT，リロード後のWAIT，効果音，ノックバックの有無，炎上効果の有無
		plasticgun = new PlasticGun(6,1.0F, 2.0D, 25, 30, "plasticmod:gun1" ,false ,false)
				.setUnlocalizedName("plasticgun")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:plasticgun");
		GameRegistry.registerItem(plasticgun,"plasticgun");
		//弾のレンダー
		proxy.registerRenderes();
		//マシンガン 銃  装填数，射程距離(弓の最大1.0F)，攻撃力補正，攻撃後のWAIT，リロード後のWAIT，効果音，ノックバックの有無，炎上効果の有無
		plasticgun2 = new PlasticGun2(20,2.0F,2.0D, 10, 60, "plasticmod:gun1" ,false ,false)
				.setUnlocalizedName("plasticgun2")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:plasticgun2");
		GameRegistry.registerItem(plasticgun2,"plasticgun2");
		//銃  装填数，射程距離(弓の最大1.0F)，攻撃力補正，攻撃後のWAIT，リロード後のWAIT，効果音，ノックバックの有無，炎上効果の有無
		rareplasticgun = new RarePlasticGun(12, 2.0F, 2.5D, 15, 20, "plasticmod:gun1" ,false ,false)
				.setUnlocalizedName("rareplasticgun")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:rareplasticgun");
		GameRegistry.registerItem(rareplasticgun,"rareplasticgun");
		//マシンガン 銃  装填数，射程距離(弓の最大1.0F)，攻撃力補正，攻撃後のWAIT，リロード後のWAIT，効果音，ノックバックの有無，炎上効果の有無
		rareplasticgun2 = new RarePlasticGun2(40, 4.0F,4.0D, 1, 30, "plasticmod:gun1" ,false ,false)
				.setUnlocalizedName("rareplasticgun2")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:rareplasticgun2");
		GameRegistry.registerItem(rareplasticgun2,"rareplasticgun2");
		//マシンガン 弾のレンダー
		//proxy.registerRenderes();
		// スポーンエッグを追加
		itemSpawnEgg = new PlasticSpawnEgg(Color.BLUE.getRGB(), Color.WHITE.getRGB())
				.setUnlocalizedName("spawn_egg")
				.setTextureName("spawn_egg")
				.setCreativeTab(plasticTab);
		GameRegistry.registerItem(itemSpawnEgg, "itemSpawnEgg");
		//エンドナフサオイル
		//ネザーナフサオイル
		//火打石とプラスチックの打ち金
		flintAndPlasticSteel = new PlasticBoal()
				.setTextureName("plasticmod:flintandplasticsteel")
				.setUnlocalizedName("flintAndPlasticSteel")
				.setCreativeTab(plasticTab)
				.setMaxDamage(64*2);
		GameRegistry.registerItem(flintAndPlasticSteel, "flintAndPlasticSteel");
		//火打石と強化プラスチックの打ち金
		flintAndRarePlasticSteel = new RarePlasticBoal()
				.setTextureName("plasticmod:flintandrareplasticsteel")
				.setUnlocalizedName("flintAndRarePlasticSteel")
				.setCreativeTab(plasticTab)
				.setMaxDamage(64*3);
		GameRegistry.registerItem(flintAndRarePlasticSteel, "flintAndRarePlasticSteel");
		//ポータルブロック
		plasticportalblock = new PlasticPortalBlock(Material.portal)
				.setBlockName("plasticportalblock");
		GameRegistry.registerBlock(plasticportalblock, "plasticportalblock");
		//ナイフ
		knife = (PlasticKnife)(new PlasticKnife())
				.setUnlocalizedName("knife")
				.setTextureName("plasticmod:knife")
				.setCreativeTab(plasticTab);
		GameRegistry.registerItem(knife, "knife");
		//プラスチックの棒
		plasticstick = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("plasticstick")
				.setTextureName("plasticmod:plasticstick")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(plasticstick, "plasticstick");
		//強化プラスチックの棒
		rareplasticstick = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("rareplasticstick")
				.setTextureName("plasticmod:rareplasticstick")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(rareplasticstick, "rareplasticstick");
		//プラスチックのドア
		PlasticDoor = new PlasticDoor(plasticmate)
				.setUnlocalizedName("plasticdoor")
				.setTextureName("plasticmod:plasticdoor");
		GameRegistry.registerItem(PlasticDoor, "PlasticDoor");
		//強化プラスチックのドア
		RarePlasticDoor = new PlasticDoor(rareplasticmate)
				.setUnlocalizedName("rareplasticdoor")
				.setTextureName("plasticmod:rareplasticdoor");
		GameRegistry.registerItem(RarePlasticDoor, "RarePlasticDoor");
		//プラスチックChest持ち運び
		plasticChestItem = new PlasticChestItem()
				.setTextureName("plasticmod:plasticchest")
				.setCreativeTab(PlasticCore.plasticTab)
				.setUnlocalizedName("plasticChest");
		GameRegistry.registerItem(plasticChestItem, "plasticChest");
		//プラスチックパウダー
		plasticpowder = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("plasticpowder")
				.setTextureName("plasticmod:plasticpowder")
				.setMaxStackSize(64);
		GameRegistry.registerItem(plasticpowder,"plasticpowder");
		OreDictionary.registerOre("dustPlastic", new ItemStack(PlasticCore.plasticpowder, 1, 0));
		//魔法のステッキ
		magicstick = new MagicStick()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("magicstick")
				.setTextureName("plasticmod:magicstick")
				.setMaxDamage(128)
				.setMaxStackSize(1);
		GameRegistry.registerItem(magicstick, "magicstick");
		//矢
		plasticarrow = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("plasticarrow")
				.setTextureName("plasticmod:plasticarrow")
				.setMaxStackSize(64);
		GameRegistry.registerItem(plasticarrow, "plasticarrow");
		//矢
		rareplasticarrow = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("rareplasticarrow")
				.setTextureName("plasticmod:rareplasticarrow")
				.setMaxStackSize(64);
		GameRegistry.registerItem(rareplasticarrow, "rareplasticarrow");
		//切ったケーキ
		cutcake = new ItemFood(3, 2.0F, false)
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("cutcake")
				.setTextureName("plasticmod:cutcake")
				.setFull3D()
				.setMaxStackSize(64);
		GameRegistry.registerItem(cutcake, "cutcake");
		//切ったかぼちゃ
		cutpumpkin = new ItemFood(8, 2.0F, false)
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("cutpumpkin")
				.setTextureName("plasticmod:cutpumpkin")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(cutpumpkin, "cutpumpkin");
		OreDictionary.registerOre("cropPumpkin", new ItemStack(PlasticCore.cutpumpkin, 1, 0));
		//人工ナフサ
		rarenaphtha = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("rarenaphtha")
				.setTextureName("plasticmod:rarenaphtha")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(rarenaphtha, "rarenaphtha");
		//天然ナフサ
		rarenaphtha1 = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("rarenaphtha1")
				.setTextureName("plasticmod:rarenaphtha1")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(rarenaphtha1, "rarenaphtha1");
		//ナフサ
		naphtha = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("naphtha")
				.setTextureName("plasticmod:naphtha")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(naphtha, "naphtha");
		//プラスチックインゴット
		plasticingot = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("plasticingot")
				.setTextureName("plasticmod:plasticingot")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(plasticingot, "plasticingot");
		OreDictionary.registerOre("ingotPlastic", new ItemStack(PlasticCore.plasticingot, 1, 0));
		//強化プラスチックインゴット
		rareplasticingot = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("rareplasticingot")
				.setTextureName("plasticmod:rareplasticingot")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(rareplasticingot, "rareplasticingot");
		OreDictionary.registerOre("ingotStrongPlastic", new ItemStack(PlasticCore.rareplasticingot, 1, 0));
		//plasticのnugget
		plasticnugget= new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("plasticnugget")
				.setTextureName("plasticmod:plasticnugget")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(plasticnugget, "plasticnugget");
		//rare plasticのnugget
		rareplasticnugget= new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("rareplasticnugget")
				.setTextureName("plasticmod:rareplasticnugget")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(rareplasticnugget, "rareplasticnugget");
		//糸
		plastic= new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("plastic")
				.setTextureName("plasticmod:plastic")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(plastic, "plastic");
		//rareいと
		rareplastic= new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("rareplastic")
				.setTextureName("plasticmod:rareplastic")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(rareplastic, "rareplastic");
		//ペットボトル
		pet = new Item()
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("pet")
				.setTextureName("plasticmod:pet")
				.setFull3D()
				.setPotionEffect(null)
				.setMaxStackSize(64);
		GameRegistry.registerItem(pet, "pet");
		//トマトジュース
		pett = new DrinkCore(3, 2.0F, false)
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("pett")
				.setTextureName("plasticmod:pett")
				.setFull3D()
				.setMaxStackSize(1);
		GameRegistry.registerItem(pett, "pett");
		//イチゴジュース
		pets = new DrinkCore(3, 2.0F, false)
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("pets")
				.setTextureName("plasticmod:pets")
				.setFull3D()
				.setMaxStackSize(1);
		GameRegistry.registerItem(pets, "pets");
		//イチゴ牛乳
		strawberrymilk = new DrinkCore(6, 2.0F, false)
				.setCreativeTab(plasticTab)
				.setUnlocalizedName("strawberrymilk")
				.setTextureName("plasticmod:strawberrymilk")
				.setFull3D()
				.setMaxStackSize(1);
		GameRegistry.registerItem(strawberrymilk, "strawberrymilk");
		//隠しチェスト
		testblock = new PlasticChestGuiBlock(15)
				.setBlockTextureName("plasticmod:textures/entity/chest/plasticchest.png")
				.setBlockName("testblock")
				.setCreativeTab(plasticTab);
		GameRegistry.registerBlock(testblock, "testblock");
		//銃弾 ノーマル
		plasticBullet = new Item()
				.setUnlocalizedName("plasticBullet")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:plasticbullet")
				.setMaxStackSize(64);
		GameRegistry.registerItem(plasticBullet,"plasticBullet");
		//銃弾 マシンガン
		plasticBullet2 = new Item()
				.setUnlocalizedName("plasticBullet2")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:plasticbullet2")
				.setMaxStackSize(64);
		GameRegistry.registerItem(plasticBullet2,"plasticBullet2");
		//銃弾 ノーマル 強化版
		rareplasticBullet = new Item()
				.setUnlocalizedName("rareplasticBullet")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:rareplasticbullet")
				.setMaxStackSize(64);
		GameRegistry.registerItem(rareplasticBullet,"rareplasticBullet");
		//銃弾 マシンガン 強化版
		rareplasticBullet2 = new Item()
				.setUnlocalizedName("rareplasticBullet2")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:rareplasticbullet2")
				.setMaxStackSize(64);
		GameRegistry.registerItem(rareplasticBullet2,"rareplasticBullet2");
		//bow
		plasticbow = new PlasticBow();
		GameRegistry.registerItem(plasticbow, "plasticbow");
		//rare bow
		rareplasticbow = new RarePlasticBow();
		GameRegistry.registerItem(rareplasticbow, "rareplasticbow");
		//プラスチックはさみ
		plasticshears =new PlasticShears()
				.setTextureName("plasticmod:plasticshears")
				.setUnlocalizedName("plasticshears")
				.setCreativeTab(plasticTab);
		GameRegistry.registerItem(plasticshears, "plasticshears");
		//プラスチックはさみ強化
		rareplasticshears =new RarePlasticShears()
				.setTextureName("plasticmod:rareplasticshears")
				.setUnlocalizedName("rareplasticshears")
				.setCreativeTab(plasticTab);
		GameRegistry.registerItem(rareplasticshears, "rareplasticshears");
		//プラスチックのマテリアル
		plastictool = EnumHelper.addToolMaterial("plastictool", 3, 200, 5.0F, 1.5F, 10)
				.setRepairItem(new ItemStack(PlasticCore.plasticingot));
		//強化プラスチックのマテリアル
		plastictool2 = EnumHelper.addToolMaterial("plastictool2", 4, 500, 7.0F, 6.0F, 30)
				.setRepairItem(new ItemStack(PlasticCore.rareplasticingot));
		//強化プラスチックのマテリアル
		plastictool3 = EnumHelper.addToolMaterial("plastictool3", 4, 20, 7.0F, 6.0F, 30)
				.setRepairItem(new ItemStack(PlasticCore.rareplasticingot));
		//クワ new ItemHoe()に自作したツールマテリアルを設定
		plastichoe = new ItemHoe(plastictool)
				.setUnlocalizedName("plastichoe")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:plastichoe");
		GameRegistry.registerItem(plastichoe,"plastichoe");
		//スコップ
		plasticspade = new ItemSpade(plastictool)
				.setUnlocalizedName("plasticspade")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:plasticspade");
		GameRegistry.registerItem(plasticspade,"plasticspade");
		//剣
		plasticsword = new ItemSword(plastictool)
				.setUnlocalizedName("plasticsword")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:plasticsword");
		GameRegistry.registerItem(plasticsword,"plasticsword");
		//斧
		plasticaxe = new PlasticAxe(plastictool)
				.setUnlocalizedName("plasticaxe")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:plasticaxe");
		GameRegistry.registerItem(plasticaxe,"plasticaxe");
		//ピッケル
		plastipickaxe = new PlasticPickaxe(plastictool)
				.setUnlocalizedName("plastipickaxe")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:plastipickaxe");
		GameRegistry.registerItem(plastipickaxe,"plastipickaxe");
		//クワ 強化版
		rareplastichoe = new ItemHoe(plastictool2)
				.setUnlocalizedName("rareplastichoe")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:rareplastichoe");
		GameRegistry.registerItem(rareplastichoe,"rareplastichoe");
		//スコップ 強化版
		rareplasticspade = new ItemSpade(plastictool2)
				.setUnlocalizedName("rareplasticspade")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:rareplasticspade");
		GameRegistry.registerItem(rareplasticspade,"rareplasticspade");
		//剣 強化版
		rareplasticsword = new ItemSword(plastictool2)
				.setUnlocalizedName("rareplasticsword")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:rareplasticsword");
		GameRegistry.registerItem(rareplasticsword,"rareplasticsword");
		//強化斧
		rareplasticaxe = new PlasticAxe(plastictool2)
				.setUnlocalizedName("rareplasticaxe")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:rareplasticaxe");
		GameRegistry.registerItem(rareplasticaxe,"rareplasticaxe");
		//強化ピッケル
		rareplastipickaxe = new PlasticPickaxe(plastictool2)
				.setUnlocalizedName("rareplastipickaxe")
				.setCreativeTab(plasticTab)
				.setTextureName("plasticmod:rareplastipickaxe");
		GameRegistry.registerItem(rareplastipickaxe,"rareplastipickaxe");
		//追加鉱石ブロックのワールド生成
		PlasticWorld eventmanager = new PlasticWorld();
		GameRegistry.registerWorldGenerator(eventmanager, 0);
		//マテリアル追加（防具）
		ItemArmor.ArmorMaterial plasticarmor = EnumHelper.addArmorMaterial("plasticarmor", 20, new int[]{2,5,4,2}, 5);
		ItemArmor.ArmorMaterial plasticarmor1 = EnumHelper.addArmorMaterial("plasticarmor1", 20, new int[]{4,6,6,4}, 15);
		//修理用アイテムの設定
		plasticarmor.customCraftingMaterial = plasticingot;
		plasticarmor1.customCraftingMaterial =rareplasticingot;
		//ここから防具
		//プラスチックヘルメット
		plastichelmet = new PlasticArmor(plasticarmor,0,0,"plastic").setUnlocalizedName("plastichelmet").setCreativeTab(plasticTab).setTextureName("plasticmod:plastichelmet");
		GameRegistry.registerItem(plastichelmet,"plastichelmet");
		//プラスチックチェストプレート
		plasticchestplate = new PlasticArmor(plasticarmor,0,1,"plastic").setUnlocalizedName("plasticchestplate").setCreativeTab(plasticTab).setTextureName("plasticmod:plasticchestplate");
		GameRegistry.registerItem(plasticchestplate,"plasticchestplate");
		//プラスチックレギンス
		plasticleggings = new PlasticArmor(plasticarmor,0,2,"plastic").setUnlocalizedName("plasticleggings").setCreativeTab(plasticTab).setTextureName("plasticmod:plasticleggings");
		GameRegistry.registerItem(plasticleggings,"plasticleggings");
		//プラスチックブーツ
		plasticboots = new PlasticArmor(plasticarmor,0,3,"plastic").setUnlocalizedName("plasticboots").setCreativeTab(plasticTab).setTextureName("plasticmod:plasticboots");
		GameRegistry.registerItem(plasticboots,"plasticboots");
		//強化プラスチックヘルメット
		rareplastichelmet = new PlasticArmor(plasticarmor1,0,0,"plastic1").setUnlocalizedName("rareplastichelmet").setCreativeTab(plasticTab).setTextureName("plasticmod:rareplastichelmet");
		GameRegistry.registerItem(rareplastichelmet,"rareplastichelmet");
		//強化プラスチックチェストプレート
		rareplasticchestplate = new PlasticArmor(plasticarmor1,0,1,"plastic1").setUnlocalizedName("rareplasticchestplate").setCreativeTab(plasticTab).setTextureName("plasticmod:rareplasticchestplate");
		GameRegistry.registerItem(rareplasticchestplate,"rareplasticchestplate");
		//強化プラスチックレギンス
		rareplasticleggings = new PlasticArmor(plasticarmor1,0,2,"plastic1").setUnlocalizedName("rareplasticleggings").setCreativeTab(plasticTab).setTextureName("plasticmod:rareplasticleggings");
		GameRegistry.registerItem(rareplasticleggings,"rareplasticleggings");
		//強化プラスチックブーツ
		rareplasticboots = new PlasticArmor(plasticarmor1,0,3,"plastic1").setUnlocalizedName("rareplasticboots").setCreativeTab(plasticTab).setTextureName("plasticmod:rareplasticboots");
		GameRegistry.registerItem(rareplasticboots,"rareplasticboots");
		//とまと作物
		tomatoblock = new TomatoBlock().setBlockName("tomatoblock").setBlockTextureName("plasticmod:tomato_stage_0");
		GameRegistry.registerBlock(tomatoblock,"tomatoblock");
		//とまと
		tomatospecies = new ItemSeeds(tomatoblock, Blocks.farmland).setUnlocalizedName("tomatospecies").setCreativeTab(plasticTab).setTextureName("plasticmod:tomatospecies");
		GameRegistry.registerItem(tomatospecies,"tomatospecies");
		//とまと作物
		tomato = new ItemFood(2, 2.0F, false).setUnlocalizedName("tomato").setCreativeTab(plasticTab).setTextureName("plasticmod:tomato");
		GameRegistry.registerItem(tomato,"tomato");
		OreDictionary.registerOre("cropTomato", new ItemStack(PlasticCore.tomato, 1, 0));
		//いちご作物
		strawberryblock = new StrawberryBlock().setBlockName("strawberryblock").setBlockTextureName("plasticmod:strawberry_stage_0");
		GameRegistry.registerBlock(strawberryblock,"strawberryblock");
		OreDictionary.registerOre("cropStrawberry", new ItemStack(PlasticCore.strawberryblock, 1, 0));
		//いちご種 ItemSeeds（植えた時に作成されるブロック，植えることができる場所）
		strawberryspecies = new ItemSeeds(strawberryblock, Blocks.farmland).setUnlocalizedName("strawberryspecies").setCreativeTab(plasticTab).setTextureName("plasticmod:strawberryspecies");
		GameRegistry.registerItem(strawberryspecies,"strawberryspecies");
		//いちご作物
		strawberry = new ItemFood(2, 2.0F, false).setUnlocalizedName("strawberry").setCreativeTab(plasticTab).setTextureName("plasticmod:strawberry");
		GameRegistry.registerItem(strawberry,"strawberry");
		//Fluidクラスのインスタンスを生成
		PlasticFluid = new Fluid("PlasticFluid")
				.setTemperature(100)
				.setViscosity(10000)
				.setLuminosity(15)
				.setDensity(500);
		//流体レジストリに追加
		FluidRegistry.registerFluid(PlasticFluid);
		//Materialは別途実装しましょう。
		//ちなみにWaterを設定すると、水同様溶岩を固める能力を持ちます。
		PlasticFluidBlock = (BlockFluidClassic)new BlockFluidClassic(PlasticFluid, Material.water)
				.setBlockName("plasticfluidblock")
				//※一枚textureならこれでよいですが、side,meta(８面別、水位)で別にする場合getIconをオーバーライド実装下さい
				.setBlockTextureName("plasticmod:plasticfluidblock_flow")
				.setCreativeTab(PlasticCore.plasticTab);
		GameRegistry.registerBlock(PlasticFluidBlock, "plasticfluidblock");
		//TextureStitchEvent.Preイベントのためにイベントバスに登録します。
		MinecraftForge.EVENT_BUS.register(PlasticFluidBlock);
		//液体プラスチックのバケツ登録
		PlasticFluid_Bucket = new PlasticFluidBucket()
				.setUnlocalizedName("plasticfluid_bucket")
				.setCreativeTab(PlasticCore.plasticTab)
				.setMaxStackSize(1)
				.setTextureName("plasticmod:plasticfluid_bucket");
		GameRegistry.registerItem(PlasticFluid_Bucket, "plasticfluid_bucket");

		ItemStack filledContainer = new ItemStack(PlasticFluid_Bucket);
		FluidContainerRegistry.registerFluidContainer(PlasticFluid, filledContainer);
		PlasticBucketHandler.INSTANCE.buckets.put(PlasticFluidBlock, PlasticFluid_Bucket);
		MinecraftForge.EVENT_BUS.register(PlasticBucketHandler.INSTANCE);
	}
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//FML Eventの登録。PlayerRespawnEvent
		MinecraftForge.EVENT_BUS.register(new PlasticEventHandler());
		//ディスペンサー登録呼び出し
		PlasticDispenser.init();
		//プロキシ呼び出し
		proxy.init();
		//村人登録
		VillagerRegistry.instance().registerVillagerId(PlasticConfig.PlasticVillagerID);
		//村人取引
		VillagerRegistry.instance().registerVillageTradeHandler(PlasticConfig.PlasticVillagerID, new PlasticVillage());
		//村人 家
		VillagerRegistry.instance().registerVillageCreationHandler(new VillageCreationHandlePlasticHouse());
		//家をマップに登録
		MapGenStructureIO.registerStructure(ComponentVillagePlasticHouse.class, "VCHPH");
		MapGenStructureIO.func_143031_a(ComponentVillagePlasticHouse.class, "VCHPHP");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		//TileEntityの登録
		GameRegistry.registerTileEntity(TileEntityPlasticChest.class, "TileEntityPlasticChest");
		/**
		 * バイオーム登録
		 * プラスチックバイオーム
		 * 強化プラスチックバイオーム
		 */
		plasticbiome = (new PlasticBiome(plasticbiomeID)).setColor(0xff0000).setBiomeName("as");
		plasticbiome2 = (new PlasticBiome2(plasticbiome2ID)).setColor(0xff0000).setBiomeName("a");
		BiomeManager.desertBiomes.add(new BiomeManager.BiomeEntry(plasticbiome, 1));
		BiomeManager.desertBiomes.add(new BiomeManager.BiomeEntry(plasticbiome2,2));
		/**
		 * レンダラ―登録
		 * 矢
		 * 強化矢
		 */
		PlasticArrowRenderId1 = proxy.getNewRenderType();
		//proxy.registerRenderes();
		EntityRegistry.registerModEntity(EntityPlasticArrow.class, "PlasticArrowEntity",3/*PlasticConfig.PlasticArrowEntityID*/, this, 60, 5, true);
		PlasticArrowRenderId2 = proxy.getNewRenderType();
		//proxy.registerRenderes();
		EntityRegistry.registerModEntity(EntityRarePlasticArrow.class, "RarePlasticArrowEntity", 4 /*PlasticConfig.RarePlasticArrowEntityID */, this, 60, 5, true);
		//MOB追加
		/**
		 * MOBレンダラID取得
		 * スケルトン
		 * クリーパー
		 */
		PlasticskeletonRender = proxy.getNewRenderType();
		PlasticCreeperRender = proxy.getNewRenderType();
		//proxy.registerRenderes();
		/**
		 * MOB登録
		 * スケルトン
		 * クリーパー
		 */
		EntityRegistry.registerModEntity(EntityPlasticSkeleton.class, "PlasticSkeletonEntity",5 /* PlasticConfig.PlasticskeletonEntityID*/, this, 250, 1, false);
		EntityRegistry.registerModEntity(EntityPlasticCreeper.class, "PlasticCreeperEntity",6 /* PlasticConfig.PlasticskeletonEntityID*/, this, 250, 1, false);
		/**
		 * MOBスポーン登録
		 * スケルトン
		 * クリーパー
		 */
		EntityRegistry.addSpawn(EntityPlasticSkeleton.class, 100, 10, 20, EnumCreatureType.monster, new BiomeGenBase[]{plasticbiome});
		EntityRegistry.addSpawn(EntityPlasticSkeleton.class, 100, 10, 20, EnumCreatureType.monster, new BiomeGenBase[]{plasticbiome2});
		EntityRegistry.addSpawn(EntityVillager.class, 100, 20, 50, EnumCreatureType.creature, new BiomeGenBase[]{plasticbiome});
		EntityRegistry.addSpawn(EntityVillager.class, 100, 20, 50, EnumCreatureType.creature, new BiomeGenBase[]{plasticbiome2});
		if (PlasticConfig.plasticskeleton_spawn_sky == true){
			EntityRegistry.addSpawn(EntityPlasticSkeleton.class, 100, 10, 20, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.sky});
		}
		if (PlasticConfig.plasticskeleton_spawn_hell == true)
		{
			EntityRegistry.addSpawn(EntityPlasticSkeleton.class, 100, 4, 8, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.hell});
		}
		EntityRegistry.addSpawn(EntityPlasticCreeper.class, 100, 10, 20, EnumCreatureType.monster, new BiomeGenBase[]{plasticbiome});
		EntityRegistry.addSpawn(EntityPlasticCreeper.class, 100, 10, 20, EnumCreatureType.monster, new BiomeGenBase[]{plasticbiome2});
		/**
		 * モンスターdrop
		 */
		MinecraftForge.EVENT_BUS.register(new MonsterDrop());
		/**
		 * レシピ登録
		 * 鉱石辞書 使用
		 */
		//矢
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticarrow), "A","B","C",'A',"ingotPlastic",'B',plasticstick,'C',Items.feather));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticarrow), "A","B","C",'A', "ingotStrongPlastic",'B',rareplasticstick,'C',Items.feather));
		//BOW追加
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticbow), " AB","A B"," AB",'A', rareplasticnugget,'B',rareplastic));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticbow)," AB","A B"," AB",'A', plasticnugget,'B',plastic));
		//plasticライト↓
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(plasticlightup),plasticlight));
		//plasticライト↑
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(plasticlight),plasticlightup));
		//plasticナゲット
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(plasticnugget,9),"ingotPlastic"));
		//rare plasticナゲット
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(rareplasticnugget,9),"ingotStrongPlastic"));
		//人工ナフサレシピ
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(rarenaphtha),naphtha,rarenaphtha1));
		//いちご牛乳レシピ
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(strawberrymilk),pet,strawberry,Items.milk_bucket));
		//トマトジュースレシピ
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pett),pet,tomato));
		//いちごジュースレシピ
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pets),pet,strawberry));
		//隠しチェストレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(testblock),"AAA","A A","AAA",'A',"ingotPlastic"));
		//隠しチェストレシピ2
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(testblock),"AAA","A A","AAA",'A',"ingotStrongPlastic"));
		//ハンドガンレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticgun,1,6),"AAA","BBC","  C",'A',plasticblock,'B',"ingotPlastic",'C',plasticstick));
		//強化ハンドガンレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticgun,1,12),"AAA","BBC","  C",'A',rareplasticblock,'B',"ingotStrongPlastic",'C',rareplasticstick));
		//マシンガンレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticgun2,1,20),"CAB","  C","  C",'A',plasticblock,'B',plasticgun,'C',plasticstick));
		//強化マシンガンレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticgun2,1,40),"CAB","  C","  C",'A',rareplasticblock,'B',rareplasticgun,'C',rareplasticstick));
		//ハンドガン弾レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticBullet)," A ","BCB","BBB",'A',"ingotPlastic",'B',Items.gold_ingot,'C',Items.gunpowder));
		//強化ハンドガン弾レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticBullet)," A ","BCB","BBB",'A',"ingotStrongPlastic",'B',Items.gold_ingot,'C',Items.gunpowder));
		//マシンガン弾レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticBullet2)," A ","BDB","BCB",'A',"ingotPlastic",'B',Items.gold_ingot,'C',Items.gunpowder,'D',plasticBullet));
		//強化マシンガン弾レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticBullet2)," A ","BDB","BCB",'A',"ingotStrongPlastic",'B',Items.gold_ingot,'C',Items.gunpowder,'D',rareplasticBullet));
		//プラスチックの棒レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticstick,4),"A","A",'A',"ingotPlastic"));
		//強化プラスチックの棒レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticstick,4),"A","A",'A',"ingotStrongPlastic"));
		//プラスチックの糸レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plastic),"AAA",'A',plasticnugget));
		//rareプラスチックの糸レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplastic),"AAA",'A',rareplasticnugget));
		//プラスチックもどすレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticingot),"AAA","AAA","AAA",'A',plasticnugget));
		//rareプラスチックもどすレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticingot),"AAA","AAA","AAA",'A',rareplasticnugget));
		//プラスチックライト 上レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticlight),"AAA","ABA","AAA",'A', plasticnugget,'B', Items.glowstone_dust));
		//ナイフ追加レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(knife),"A  "," A ","  #",'#', plasticstick,'A',"ingotPlastic"));
		//プラスチックインゴット9個でプラスチックブロックレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticblock),"###","###","###",'#',"ingotPlastic"));
		//プラスチックインゴット5個と赤い染料でペットボトルレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pet)," M ","# #","###",'#',"ingotPlastic",'M',new ItemStack(Items.dye,1,1)));
		//強化プラスチックインゴット9個で強化プラスチックブロックレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticblock),"###","###","###",'#', "ingotStrongPlastic"));
		//ナフサ９個でナフサブロックレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(naphthablock),"###","###","###",'#', naphtha));
		//人工ナフサ９個で人工ナフサブロックレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rarenaphthablock),"###","###","###",'#', rarenaphtha));
		//剣レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticsword),"P","P","S",'P',"ingotPlastic",'S', plasticstick));
		//強化剣レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticsword),"P","P","S",'P', "ingotStrongPlastic",'S', rareplasticstick));
		//斧レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticaxe),"PP","SP", "S ",'P',"ingotPlastic",'S', plasticstick));
		//強化斧レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticaxe),"PP","SP","S ",'P', "ingotStrongPlastic",'S', rareplasticstick));
		//シャベルレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticspade),"P","S","S",'P',"ingotPlastic",'S', plasticstick));
		//強化シャベルレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticspade),"P","S","S", 'P', "ingotStrongPlastic",'S', rareplasticstick));
		//ピッケルレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plastipickaxe),"PPP"," S "," S ", 'P',"ingotPlastic",'S', plasticstick));
		//強化ピッケルレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplastipickaxe),"PPP"," S "," S ", 'P', "ingotStrongPlastic",'S', rareplasticstick));
		//クワレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plastichoe),"PP"," S"," S", 'P',"ingotPlastic",'S', plasticstick));
		//強化クワレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplastichoe),"PP"," S"," S", 'P', "ingotStrongPlastic",'S', rareplasticstick));
		//強化プラスチックのヘルメットレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplastichelmet),"PPP","P P", 'P', "ingotStrongPlastic"));
		//強化プラスチックのチェストプレートレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticchestplate),"P P","PPP","PPP", 'P', "ingotStrongPlastic"));
		//強化プラスチックのレギンスレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticleggings),"PPP","P P","P P", 'P', "ingotStrongPlastic"));
		//強化プラスチックのブーツレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticboots),"P P","P P", 'P', "ingotStrongPlastic"));
		//プラスチックのヘルメットレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plastichelmet),"PPP","P P", 'P',"ingotPlastic"));
		//プラスチックのチェストプレートレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticchestplate),"P P","PPP","PPP", 'P',"ingotPlastic"));
		//プラスチックのレギンスレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticleggings),"PPP","P P","P P", 'P',"ingotPlastic"));
		//プラスチックのブーツレシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticboots),"P P","P P", 'P',"ingotPlastic"));
		//火打石とプラスチックの打ち金レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(flintAndPlasticSteel),"A  "," P ", 'A',"ingotPlastic",'P', Items.flint));
		//火打石と強化プラスチックの打ち金レシピ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(flintAndRarePlasticSteel),"A  "," P ", 'A', "ingotStrongPlastic",'P', Items.flint));
		//はさみ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticshears),"A "," A",'A',"ingotPlastic"));
		//はさみ強化
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rareplasticshears),"A "," A",'A',"ingotStrongPlastic"));
		//魔法のステッキ
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magicstick), "  A"," S ","S  ",'A',rareplasticblock ,'S',rareplasticstick));
		//持ち運びチェスト
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plasticChestItem), "  A"," S ","S  ",'A',testblock ,'S',plasticstick));
		//強化ガラス(仮)
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlasticGlass),"XXX","XYX","XXX",'X',Blocks.glass,'Y', Blocks.obsidian));
		//プラスチックTNT
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlasticTNT),"XYX","YXY","XYX",'X',Items.gunpowder,'Y',"ingotPlastic"));
		//プラスチックドア
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlasticDoor,2),"XX","XX","XX",'X',"ingotPlastic"));
		//強化プラスチックドア
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RarePlasticDoor,2),"XX","XX","XX",'X',"ingotStrongPlastic"));
		//プラスチックトラップドア
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlasticTrapDoor,2),"XXX","XXX",'X',plasticblock));
		//強化プラスチックトラップドア
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RarePlasticTrapDoor,2),"XXX","XXX",'X',rareplasticblock));
		//プラスチックフェンス
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlasticFence,2),"XXX","XXX",'X',plasticstick));
		//強化プラスチックフェンス
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RarePlasticFence,2),"XXX","XXX",'X',rareplasticstick));
		//プラスチックフェンスゲート
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlasticFenceGate),"XYX","XYX",'X',plasticstick,'Y',plasticblock));
		//強化プラスチックフェンスゲート
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RarePlasticFenceGate),"XYX","XYX",'X',rareplasticstick,'Y',rareplasticblock));
		//プラスチック感圧板
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlasticPressurePlateBlock),"XX",'X',plasticblock));
		//強化プラスチック感圧板
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RarePlasticPressurePlateBlock),"XX",'X',rareplasticblock));
		//プラスチック壁
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlasticWall,6),"YXY","YXY",'X',plasticblock,'Y',plasticingot));
		//強化プラスチック壁
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RarePlasticWall,6),"YXY","YXY",'X',rareplasticblock,'Y',rareplasticingot));
		//プラスチックの階段
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlasticBlockStairs,4),"  X"," XX","XXX",'X',plasticblock));
		//強化プラスチックの階段
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RarePlasticBlockStairs,4),"  X"," XX","XXX",'X',rareplasticblock));
		//ナフサの階段
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NaphthaBlockStairs,4),"  X"," XX","XXX",'X',naphthablock));
		//救済レシピ ConfigでONの時のみ追加
		if(PlasticConfig.rescuerecipes){
			GameRegistry.addShapelessRecipe(new ItemStack(naphtha),Items.coal);
		}
		/**精錬レシピ  上から
		 * 人工ナフサブロック→強化プラスチックインゴット
		 * 石炭→ナフサ
		 * ナフサオイル→ナフサ
		 * ナフサブロック→プラスチックインゴット
		 **/
		GameRegistry.addSmelting(rarenaphthablock,new ItemStack(rareplasticingot),0.1f);
		GameRegistry.addSmelting(Items.coal,new ItemStack(naphtha),0.1f);
		GameRegistry.addSmelting(naphthaoil,new ItemStack(rarenaphtha1),0.1f);
		GameRegistry.addSmelting(naphthablock,new ItemStack(plasticingot),0.1f);
		//燃料登録 溶けたプラスチック入りバケツ
		GameRegistry.registerFuelHandler(new IFuelHandler(){
			@Override
			public int getBurnTime(ItemStack fuel) {
				if (fuel.getItem() == PlasticCore.PlasticFluid_Bucket) {
					return 1000;
				}
				return 0;
			}
		});
	}
	@EventHandler
	public void postInit (FMLPostInitializationEvent event)
	{
		if (PlasticConfig.plastic_logger)
		{
			logger(getLocal("logger.ic2check"));

		}
		if(Loader.isModLoaded("IC2"))
		{
				logger(getLocal("logger.ic2load"));
			try
			{
				ic2.api.recipe.RecipeInputItemStack input = new ic2.api.recipe.RecipeInputItemStack(new ItemStack(PlasticCore.plasticpowder,12 ,0), 12);
				NBTTagCompound metadata = new NBTTagCompound();
				metadata.setInteger("compressor", 2000);
				ItemStack outputs = new ItemStack(PlasticCore.plasticingot, 1, 0);
				ic2.api.recipe.Recipes.compressor.addRecipe(input, metadata, outputs);
				ic2.api.recipe.RecipeInputItemStack input2 = new ic2.api.recipe.RecipeInputItemStack(new ItemStack(PlasticCore.pet,1 ,0), 1);
				NBTTagCompound metadata2 = new NBTTagCompound();
				metadata.setInteger("macerator", 2000);
				ItemStack outputs2 = new ItemStack(PlasticCore.plasticpowder, 6, 0);
				ic2.api.recipe.Recipes.macerator.addRecipe(input2, metadata2, outputs2);
					logger(getLocal("logger.ic2finish"));
			}
			catch (Throwable t)
			{
					loggers.warn(getLocal("logger.ic2error"));
			}
			finally{
			}
		}else {
				logger(getLocal("logger.ic2not"));
		}
	}
	/**
	 * TNTレンダラの編集
	 * @param map
	 */
	public void addRenderer(Map map)
	{
		//EntityNewTNTPrimedの描画処理を登録
		map.put(EntityPlasticTNTPrimed.class, new RenderPlasticTNTPrimed());
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void inputKey(InputEvent.KeyInputEvent event)
	{
	}
	/**
	 * ナイフ関係のレシピ追加＆ナイフのインスタンス化
	 *   ディメンションの登録
	 * @param event
	 */
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		// 独自ディメンション用のワールドプロバイダを登録
		DimensionManager.registerProviderType(PlasticConfig.providerType, PlasticWorldProvider.class, false);
		// 独自ディメンションを登録
		DimensionManager.registerDimension(PlasticConfig.dimensionID, PlasticConfig.providerType);
		FMLCommonHandler.instance().bus().register(knife);
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone_slab, 2, 3),new ItemStack(knife,1,32767),Blocks.cobblestone);
		//切ったケーキレシピ
		GameRegistry.addShapelessRecipe(new ItemStack(cutcake,6),new ItemStack(knife,1,32767),Items.cake);
		//切ったかぼちゃレシピ
		GameRegistry.addShapelessRecipe(new ItemStack(cutpumpkin,6),new ItemStack(knife,1,32767),Blocks.pumpkin);
	}
	/**
	 * 特定の日のみの
	 * プラスチックのクリーパーのテクスチャ変更 (4月1日)
	 * @param world
	 * @param flg
	 * @return
	 */
	public static int EventDate(World world , boolean flg)
	{
		Calendar calendar = world.getCurrentDate();
		int month = calendar.get(2) + 1;
		int date = calendar.get(5);
		if (month == 4 && date  == 1)
		{
			return 4;
		}
		if(flg)
		{
			return 1;
		}
		return 0;
	}
	/**
	 * 流体のテクスチャ指定
	 * @param event
	 */
	@SubscribeEvent
	public void PreTextureStitchEvent(TextureStitchEvent.Pre event){
		if(event.map.getTextureType() == 0){
			Fluid f = PlasticFluid;
			if(f != null){
				f.setStillIcon(event.map.registerIcon("plasticmod:plasticfluidblock_still"));
				f.setFlowingIcon(event.map.registerIcon("plasticmod:plasticfluidblock_flow"));
				/*
				if(f.getID() != -1){
					//f.setIcons(f.getIcon());
					f.setStillIcon(event.map.registerIcon("plasticmod:plasticfluidblock_still"));
					f.setFlowingIcon(event.map.registerIcon("plasticmod:plasticfluidblock_flow"));
				}else{
					IIcon commonIcon = event.map.registerIcon("plasticmod:plasticfluid");
					f.setIcons(commonIcon);
				}*/
			}
		}
	}
	//ログ
	public static void logger(String str){
		if (PlasticConfig.plastic_logger){
		loggers.info(str);
		}
	}
}