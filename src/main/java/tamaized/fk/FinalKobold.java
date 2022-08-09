package tamaized.fk;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraftforge.fml.common.Mod;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFEntities;
import twilightforest.entity.monster.Kobold;
import twilightforest.world.components.structures.finalcastle.FinalCastleBossGazeboComponent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@Mod(value = "fk")
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class FinalKobold {
	public static boolean asm(boolean b, BlockPos pos, ServerLevelAccessor world) {
		Kobold myCreature = TFEntities.KOBOLD.get().create(world.getLevel());
		if (myCreature == null) return false;

		myCreature.moveTo(pos, world.getLevel().random.nextFloat() * 360.0F, 0.0F);
		myCreature.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.SPAWNER, null, null);

		myCreature.setCustomName(new TextComponent("Final Kobold"));
		Objects.requireNonNull(myCreature.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(1024);
		myCreature.setHealth(myCreature.getMaxHealth());
		Objects.requireNonNull(myCreature.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(2048);
		Objects.requireNonNull(myCreature.getAttribute(Attributes.ARMOR)).setBaseValue(30);
		Objects.requireNonNull(myCreature.getAttribute(Attributes.ARMOR_TOUGHNESS)).setBaseValue(20);
		Objects.requireNonNull(myCreature.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).setBaseValue(1);
		myCreature.hurt(DamageSource.GENERIC, 1F);

		return world.addFreshEntity(myCreature);
	}

	public static int red(int o, LivingEntity entity, float partialTicks) {
		return entity instanceof Kobold && entity.getAttributeBaseValue(Attributes.MAX_HEALTH) >= 1024 ? 1 : o;
	}

	public static void gazebo(StructurePiece structurePiece, WorldGenLevel level) {
		structurePiece.placeBlock(level, TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get().defaultBlockState(), 10, 1, 10, structurePiece.getBoundingBox());
	}
}
