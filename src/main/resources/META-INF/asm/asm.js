// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {

    var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
    var Opcodes = Java.type('org.objectweb.asm.Opcodes');

    var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
    var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');

    return {
        'boss': {
            'target': {
                'type': 'METHOD',
                'class': 'twilightforest/block/entity/spawner/FinalBossSpawnerBlockEntity',
                'methodName': 'spawnMyBoss',
                'methodDesc': '(Lnet/minecraft/world/level/ServerLevelAccessor;)Z'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.IRETURN),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL,
                            'net/minecraft/world/level/block/entity/BlockEntity',
                            ASM.mapMethod('m_58899_'), // getBlockPos
                            '()Lnet/minecraft/core/BlockPos;'
                            ),
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'tamaized/fk/FinalKobold',
                            'asm',
                            '(ZLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/ServerLevelAccessor;)Z',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        },
        'red': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.entity.LivingEntityRenderer',
                'methodName': ASM.mapMethod('m_115338_'),
                'methodDesc': '(Lnet/minecraft/world/entity/LivingEntity;F)I'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.IRETURN),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new VarInsnNode(Opcodes.FLOAD, 1),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'tamaized/fk/FinalKobold',
                            'red',
                            '(ILnet/minecraft/world/entity/LivingEntity;F)I',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        },
        'gazebo': {
            'target': {
                'type': 'METHOD',
                'class': 'twilightforest/world/components/structures/finalcastle/FinalCastleBossGazeboComponent',
                'methodName': ASM.mapMethod('m_183269_'), // postProcess
                'methodDesc': '(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureFeatureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/core/BlockPos;)V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.RETURN),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new VarInsnNode(Opcodes.ALOAD, 1),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'tamaized/fk/FinalKobold',
                            'gazebo',
                            '(Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/WorldGenLevel;)V',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        }
    }
}
