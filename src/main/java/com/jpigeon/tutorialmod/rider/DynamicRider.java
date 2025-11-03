package com.jpigeon.tutorialmod.rider;

import com.jpigeon.ridebattlelib.core.system.form.DynamicFormConfig;
import com.jpigeon.ridebattlelib.core.system.henshin.RiderConfig;
import com.jpigeon.ridebattlelib.core.system.henshin.RiderRegistry;
import com.jpigeon.tutorialmod.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Items;

import java.util.List;

public class DynamicRider {
    // 动态骑士示例
    public static final ResourceLocation DYNAMIC_RIDER_ID = ResourceLocation.fromNamespaceAndPath(TutorialMod.MODID, "dynamic");

    public static final ResourceLocation DYNAMIC_SLOT_ONE = ResourceLocation.fromNamespaceAndPath(TutorialMod.MODID, "dynamic_slot_one");
    public static final ResourceLocation DYNAMIC_SLOT_TWO = ResourceLocation.fromNamespaceAndPath(TutorialMod.MODID, "dynamic_slot_two");

    // 动态骑士可参考内部类：ExampleDynamic
    // 速通riderConfig
    public static final RiderConfig DYNAMIC_RIDER =
            new RiderConfig(DYNAMIC_RIDER_ID)
                    .setAllowDynamicForms(true) // 这是最关键的一点，允许系统为此骑士生成动态形态
                    .setDriverItem(Items.BRICK, EquipmentSlot.OFFHAND) // 放在副手的驱动器（砖块）
                    .addDriverSlot(
                            DYNAMIC_SLOT_ONE,
                            List.of(Items.DIAMOND, Items.EMERALD),
                            true,
                            true
                            )
                    .addDriverSlot(
                            DYNAMIC_SLOT_TWO,
                            List.of(Items.GOLD_INGOT, Items.IRON_INGOT),
                            true,
                            true
                    )
                    // 接下来是骑士（在动态形态下）本身所有的效果，也就是基础属性，会在各个动态形态之间保存继承
                    .addBaseEffect(MobEffects.FIRE_RESISTANCE, 114514, 0, true) // 各个形态共有的抗火效果
                    .addBaseAttribute(
                            ResourceLocation.fromNamespaceAndPath("minecraft", "generic.max_health"),
                            20.0D,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    ) // up对属性的造诣不高，其它请自行摸索
            ;

    // 让我们不创建形态Config

    // 仅创建一个动态形态映射
    private static void registerDynamicMapping(){
        // == 盔甲映射 ==
        DynamicFormConfig.registerItemArmor(Items.DIAMOND, EquipmentSlot.HEAD, Items.DIAMOND_HELMET); // 钻石对应头盔->穿戴在头上
        DynamicFormConfig.registerItemArmor(Items.EMERALD, Items.TURTLE_HELMET); // 或者直接省略穿戴位置，系统自动推算（不100%稳定），但大多数都没问题

        DynamicFormConfig.registerItemArmor(Items.GOLD_INGOT, Items.GOLDEN_CHESTPLATE);
        DynamicFormConfig.registerItemArmor(Items.IRON_INGOT, Items.IRON_CHESTPLATE);
        // == 效果映射 ==
        DynamicFormConfig.registerItemEffect(Items.DIAMOND, MobEffects.DAMAGE_RESISTANCE, 114514, 2, true); // 钻石对应抗性3(因为等级0=抗性1，游戏特性)
        DynamicFormConfig.registerItemEffect(Items.EMERALD, MobEffects.CONDUIT_POWER); // 或者直接省略后面的时长等级和粒子，自动设置为114514, 1, true

        DynamicFormConfig.registerItemEffect(Items.GOLD_INGOT, MobEffects.GLOWING);
        DynamicFormConfig.registerItemEffect(Items.IRON_INGOT, MobEffects.DAMAGE_BOOST);

        // 接下来可以为骑士设置底衣：上方仅映射头部+胸部，但我们也许不希望光着腿脚...
        DynamicFormConfig.registerRiderUndersuit(
                DYNAMIC_RIDER_ID,
                Items.AIR, // 头胸无底衣：因为两个槽位皆为必要，所以不会出现少一个还会变身的情况，于是不需要考虑这俩的底衣
                Items.AIR,
                Items.NETHERITE_LEGGINGS,
                Items.NETHERITE_BOOTS
        );
    }

    private static void riderDynamic(){
        RiderRegistry.registerRider(DYNAMIC_RIDER);
        registerDynamicMapping();
    }

    public static void init(){
        riderDynamic();
    }
}
