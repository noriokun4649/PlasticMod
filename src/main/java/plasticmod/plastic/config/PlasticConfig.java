package plasticmod.plastic.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import plasticmod.plastic.PlasticCore;

public class PlasticConfig {
	public static boolean plasticskeleton_spawn_sky;
	public static boolean plasticskeleton_spawn_hell;
	public static boolean gateeffect;
	public static boolean rescuerecipes;
	public static boolean plastic_Developer_options;
	public static boolean plastic_logger;
	public static int dimensionID;
	public static int providerType;
	public static int plasticcarpetlight;
	public static int plasticbiomeID;
	public static int rareplasticbiomeID;
	public static String Plastic_ID = "plastic_id";
	public static String Plastic_MOB = "plastic_mob";
	public static String Plastic_Recipe = "plastic_recipe";
	public static String Plastic_Block = "plastic_block";
	public static String Plastic_GATE ="plastic_gate";
	public static String Plastic_Developer ="plastic_developer";
	/*
	public static int plasticTNTID;
	public static int plasticbulletID;
	public static int PlasticArrowEntityID;
	public static int RarePlasticArrowEntityID;
	public static int PlasticskeletonEntityID;
	 */
	public static int PlasticVillagerID;
	public static void preInit() {
		Configuration cfg = new Configuration(new File("config/plasticmod.cfg"),"1.0");
		try {
			cfg.load();
			if (PlasticCore.getLocal("title.lang.name").equals("Japanese")){
				PlasticCore.loggers.info(PlasticCore.getLocal("config.loading"));
				plastic_logger = cfg.get(Plastic_Developer, "Plastic_logger", false, "ログにプラスチックMODのログを表示するかどうか").getBoolean(false);
				PlasticVillagerID =cfg.get(Plastic_ID, "PlasticVillagerID", 17,"村人のID").getInt();
				rescuerecipes = cfg.get(Plastic_Recipe, "RescueRecipes", false, "救済レシピを使用するかどうか").getBoolean(false);
				plasticskeleton_spawn_hell = cfg.get(Plastic_MOB, "PlasticSkeletonNetherSpawn", true,"プラスチックスケルトンをネザーにスポーンさせるかどうか").getBoolean(true);
				plasticskeleton_spawn_sky = cfg.get(Plastic_MOB, "PlasticSkeletonEndSpawn", true,"プラスチックスケルトンをジ・エンドにスポーンさせるかどうか").getBoolean(true);
				gateeffect = cfg.get(Plastic_GATE, "PlasticPortalEffect", true,"プラスチックゲートにパーティクルと音をつけるかどうか").getBoolean(true);
				providerType = cfg.get(Plastic_ID, "ProviderType",2,"ディメンションIDと同じ数値を設定してください").getInt();
				dimensionID = cfg.get(Plastic_ID, "DimensionID",2,"ディメンションID").getInt();
				plasticbiomeID = cfg.get(Plastic_ID, "PlasticBiomeID",98,"プラスチックバイオームID").getInt();
				rareplasticbiomeID = cfg.get(Plastic_ID, "StrongPlasticBiomeID",99,"強化プラスチックバイオームID").getInt();
				plasticcarpetlight = cfg.get(Plastic_Block, "PlasticCarpetLight",15,"プラスチックカーペットが光を通すかどうか「0-15」",0,15).getInt();
				plastic_Developer_options= cfg.get(Plastic_Developer, "Plastic_Developer_Options", false,"開発者向けオプション").getBoolean(false);
				cfg.setCategoryComment(Plastic_GATE, "ゲート関連の設定です");
				cfg.setCategoryComment(Plastic_ID, "ID関連の設定です");
				cfg.setCategoryComment(Plastic_MOB, "MOB関連の設定です");
				cfg.setCategoryComment(Plastic_Block, "ブロック関連の設定です");
				cfg.setCategoryComment(Plastic_Recipe, "レシピ関連の設定です");
				cfg.setCategoryComment(Plastic_Developer, "開発者向けのオプションです。使用する際にはご注意ください。");
				PlasticCore.loggers.info("Configファイルを読み込み終了");
			}else{
				PlasticCore.loggers.info("Config loding now");
				plastic_logger = cfg.get(Plastic_Developer, "Plastic_logger", false, "Do you display log of PlasticMOD in log?").getBoolean(false);
				PlasticVillagerID =cfg.get(Plastic_ID, "PlasticVillagerID", 17,"PlasticVillagerID").getInt();
				rescuerecipes = cfg.get(Plastic_Recipe, "RescueRecipes", false, "Do you use a relief recipe?").getBoolean(false);
				plasticskeleton_spawn_hell = cfg.get(Plastic_MOB, "PlasticSkeletonNetherSpawn", true,"PlasticSkeleton spawn hell").getBoolean(true);
				plasticskeleton_spawn_sky = cfg.get(Plastic_MOB, "PlasticSkeletonEndSpawn", true,"PlasticSkeleton spawn sky").getBoolean(true);
				gateeffect = cfg.get(Plastic_GATE, "PlasticPortalEffect", true,"Do you leave a particle and a sound to the plastic gate?").getBoolean(true);
				providerType = cfg.get(Plastic_ID, "ProviderType",2,"Please set numerical value same as dimension ID").getInt();
				dimensionID = cfg.get(Plastic_ID, "DimensionID",2,"Dimension ID").getInt();
				plasticbiomeID = cfg.get(Plastic_ID, "PlasticBiomeID",98,"PlasticBiomeID").getInt();
				rareplasticbiomeID = cfg.get(Plastic_ID, "StrongPlasticBiomeID",99,"Reinforced plastic biome ID").getInt();
				plasticcarpetlight = cfg.get(Plastic_Block, "PlasticCarpetLight",15,"Does a plastic carpet let light go through?「0-15」").getInt();
				plastic_Developer_options= cfg.get(Plastic_Developer, "Plastic_Developer_Options", false,"Plastic Developer Options").getBoolean(false);
				cfg.setCategoryComment(Plastic_GATE, "It is gate-related setting");
				cfg.setCategoryComment(Plastic_ID, "It is ID-related setting");
				cfg.setCategoryComment(Plastic_MOB, "It is MOB-related setting");
				cfg.setCategoryComment(Plastic_Block, "It is block-related setting");
				cfg.setCategoryComment(Plastic_Recipe, "It is recipe-related setting");
				cfg.setCategoryComment(Plastic_Developer, "Is an option for developers. Please note that when you use.");
				PlasticCore.logger("Config Reads the file and exit");
			}
			cfg.save();
		} catch (Exception e) {
			PlasticCore.loggers.warn(PlasticCore.getLocal("logger.configerror"));
		} finally {
			cfg.save();
		}
	}
}