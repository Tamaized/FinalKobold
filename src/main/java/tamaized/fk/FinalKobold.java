package tamaized.fk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFKobold;
import twilightforest.entity.TFEntities;
import twilightforest.tileentity.spawner.TileEntityTFBossSpawner;
import twilightforest.tileentity.spawner.TileEntityTFFinalBossSpawner;

@Mod(value = "fk")
public class FinalKobold {

	private static final DeferredRegister<TileEntityType<?>> tiles = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "fk");
	private static final RegistryObject<TileEntityType<TileEntityFKFinalBossSpawner>> spawner = tiles

			.register("fk", () -> TileEntityType.Builder.create(TileEntityFKFinalBossSpawner::new, TFBlocks.boss_spawner_final_boss.get()).build(null));

	public FinalKobold() {
		tiles.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static TileEntityTFBossSpawner<?> asm(TileEntityTFBossSpawner<?> o) {
		if (o instanceof TileEntityTFFinalBossSpawner)
			return spawner.get().create();
		return o;
	}

	public static int red(int o, LivingEntity entity, float partialTicks) {
		return entity instanceof EntityTFKobold && entity.getBaseAttributeValue(Attributes.MAX_HEALTH) >= 1024 ? 1 : o;
	}

	private static final class TileEntityFKFinalBossSpawner extends TileEntityTFFinalBossSpawner {

		@Override
		public void tick() {
			if (!this.spawnedBoss && this.anyPlayerInRange()) {
				if (this.world.isRemote) {
					double rx = (float) this.pos.getX() + this.world.rand.nextFloat();
					double ry = (float) this.pos.getY() + this.world.rand.nextFloat();
					double rz = (float) this.pos.getZ() + this.world.rand.nextFloat();
					this.world.addParticle(ParticleTypes.SMOKE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
					this.world.addParticle(ParticleTypes.FLAME, rx, ry, rz, 0.0D, 0.0D, 0.0D);
				} else if (this.world.getDifficulty() != Difficulty.PEACEFUL && this.spawnMyBoss((ServerWorld) this.world)) {
					this.world.destroyBlock(this.pos, false);
					this.spawnedBoss = true;
				}

			}
		}

		protected boolean spawnMyBoss(IServerWorld world) {
			EntityTFKobold myCreature = TFEntities.kobold.create(world.getWorld());
			myCreature.moveToBlockPosAndAngles(this.pos, world.getWorld().rand.nextFloat() * 360.0F, 0.0F);
			myCreature.onInitialSpawn(world, world.getDifficultyForLocation(this.pos), SpawnReason.SPAWNER, null, null);

			myCreature.setCustomName(new StringTextComponent("Final Kobold"));
			myCreature.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024);
			myCreature.setHealth(myCreature.getMaxHealth());
			myCreature.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2048);
			myCreature.getAttribute(Attributes.ARMOR).setBaseValue(30);
			myCreature.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(20);
			myCreature.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
			myCreature.attackEntityFrom(DamageSource.GENERIC, 1F);

			return world.addEntity(myCreature);
		}

	}

}
