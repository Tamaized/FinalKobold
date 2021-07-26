// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {

    var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
    var Opcodes = Java.type('org.objectweb.asm.Opcodes');

    return {
        'boss': {
            'target': {
                'type': 'METHOD',
                'class': 'twilightforest.block.BossSpawnerBlock',
                'methodName': 'createTileEntity',
                'methodDesc': '(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;)Lnet/minecraft/tileentity/TileEntity;'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.ARETURN),
                    ASM.listOf(
                        new org.objectweb.asm.tree.MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'tamaized/fk/FinalKobold',
                            'asm',
                            '(Ltwilightforest/tileentity/spawner/BossSpawnerTileEntity;)Ltwilightforest/tileentity/spawner/BossSpawnerTileEntity;',
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
                'class': 'net.minecraft.client.renderer.entity.LivingRenderer',
                'methodName': ASM.mapMethod('func_229117_c_'),
                'methodDesc': '(Lnet/minecraft/entity/LivingEntity;F)I'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.IRETURN),
                    ASM.listOf(
                        new org.objectweb.asm.tree.VarInsnNode(Opcodes.ALOAD, 0),
                        new org.objectweb.asm.tree.VarInsnNode(Opcodes.FLOAD, 1),
                        new org.objectweb.asm.tree.MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'tamaized/fk/FinalKobold',
                            'red',
                            '(ILnet/minecraft/entity/LivingEntity;F)I',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        }
    }
}
